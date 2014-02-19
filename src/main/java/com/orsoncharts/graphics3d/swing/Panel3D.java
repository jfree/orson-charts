/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * Redistribution of this source file is prohibited.
 * 
 */

package com.orsoncharts.graphics3d.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.awt.Rectangle;

import javax.swing.JPanel;

import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.graphics3d.Drawable3D;
import com.orsoncharts.graphics3d.Offset2D;
import com.orsoncharts.graphics3d.ViewPoint3D;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.util.ExportFormats;

/**
 * A panel that displays a set of 3D objects from a particular viewing point.
 * The view point is maintained by the {@link Drawable3D} but the panel
 * provides convenience methods to get/set it.
 */
public class Panel3D extends JPanel implements MouseListener, 
        MouseMotionListener, MouseWheelListener {
  
    /**
     * The object that is displayed in the panel.
     */
    private Drawable3D drawable;
    
    /** 
     * The minimum viewing distance (zooming in will not go closer than this).
     */
    private float minViewingDistance;

    /** 
     * The margin to leave around the edges of the chart when zooming to fit. 
     * This is expressed as a percentage (0.25 = 25 percent) of the width
     * and height.
     */
    private double margin;

    /** The angle increment for panning left and right (in radians). */
    private double panIncrement;
    
    /** The angle increment for rotating up and down (in radians). */
    private double rotateIncrement;
    
    /** The roll increment (in radians). */
    private double rollIncrement;
    
    /** 
     * The (screen) point of the last mouse click (will be <code>null</code> 
     * initially).  Used to calculate the mouse drag distance and direction.
     */
    private Point lastClickPoint;
    
    /**
     * The (screen) point of the last mouse move point that was handled.
     */
    private Point lastMovePoint;
    
    /**
     * Temporary state to track the 2D offset during an ALT-mouse-drag
     * operation.
     */
    private Offset2D offsetAtMousePressed;
    
    /**
     * Creates a new panel with the specified {@link Drawable3D} to
     * display.
     *
     * @param drawable  the content to display (<code>null</code> not 
     *     permitted).
     */
    public Panel3D(Drawable3D drawable) {
        super(new BorderLayout());
        ArgChecks.nullNotPermitted(drawable, "drawable");
        this.drawable = drawable;
        this.margin = 0.25;
        this.minViewingDistance 
                = (float) (drawable.getDimensions().getDiagonalLength());
        this.panIncrement = Math.PI / 60;
        this.rotateIncrement = Math.PI / 60;
        this.rollIncrement = Math.PI / 60;
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
    }

    /**
     * Returns the <code>Drawable3D</code> object that is displayed in this
     * panel.  This is specified via the panel constructor and there is no
     * setter method to change it.
     * 
     * @return The <code>Drawable3D</code> object (never <code>null</code>).
     */
    public Drawable3D getDrawable() {
        return this.drawable;
    }

    /** 
     * Returns the margin, expressed as a percentage, that controls the amount
     * of space to leave around the edges of the 3D content when the 
     * <code>zoomToFit()</code> method is called.  The default value is 
     * <code>0.25</code> (25 percent).
     * 
     * @return The margin. 
     */
    public double getMargin() {
        return this.margin;
    }
    
    /**
     * Sets the margin that controls the amount of space to leave around the
     * edges of the 3D content when the <code>zoomToFit()</code> method is 
     * called.
     *
     * @param margin  the margin (as a percentage, where 0.25 = 25 percent).
     */
    public void setMargin(double margin) {
        this.margin = margin;
    }
    
    /**
     * Returns the minimum viewing distance.  Zooming by mouse wheel or other
     * means will not move the viewing point closer than this.
     * 
     * @return The minimum viewing distance.
     */
    public float getMinViewingDistance() {
        return this.minViewingDistance;
    }

    /**
     * Returns the angle delta for each pan left or right.  The default
     * value is <code>Math.PI / 60</code>.
     * 
     * @return The angle delta (in radians).
     */
    public double getPanIncrement() {
        return panIncrement;
    }

    /**
     * Sets the standard increment for panning left and right (a rotation
     * specified in radians).
     * 
     * @param panIncrement  the increment (in radians).
     */
    public void setPanIncrement(double panIncrement) {
        this.panIncrement = panIncrement;
    }

    /**
     * Returns the angle delta for each rotate up or down.  The default
     * value is <code>Math.PI / 60</code>.
     * 
     * @return The angle delta (in radians).
     */
    public double getRotateIncrement() {
        return rotateIncrement;
    }

    /**
     * Sets the vertical (up and down) rotation increment (in radians).
     * 
     * @param rotateIncrement  the increment (in radians). 
     */
    public void setRotateIncrement(double rotateIncrement) {
        this.rotateIncrement = rotateIncrement;
    }

    /**
     * Returns the angle delta for each roll operation.  The default
     * value is <code>Math.PI / 60</code>.
     * 
     * @return The angle delta (in radians).
     */
    public double getRollIncrement() {
        return rollIncrement;
    }

    /**
     * Sets the roll increment in radians.
     * 
     * @param rollIncrement  the increment (in radians). 
     */
    public void setRollIncrement(double rollIncrement) {
        this.rollIncrement = rollIncrement;
    }
    
    /**
     * Returns the view point that is maintained by the {@link Drawable3D}
     * instance on display.
     *
     * @return  The view point (never <code>null</code>).
     */
    public ViewPoint3D getViewPoint() {
        return this.drawable.getViewPoint();
    }

    /**
     * Sets a new view point and repaints the panel.
     *
     * @param vp  the view point (<code>null</code> not permitted).
     */
    public void setViewPoint(ViewPoint3D vp) {
        ArgChecks.nullNotPermitted(vp, "vp");
        this.drawable.setViewPoint(vp);  // 
        repaint();
    }
    
    /**
     * Returns the last click point (possibly <code>null</code>).
     * 
     * @return The last click point (possibly <code>null</code>).
     */
    protected Point getLastClickPoint() {
        return this.lastClickPoint;
    }
    
    /**
     * Rotates the view point around from left to right by the specified
     * angle and repaints the 3D scene.  The direction relative to the
     * world coordinates depends on the orientation of the view point.
     * 
     * @param angle  the angle of rotation (in radians).
     */
    public void panLeftRight(double angle) {
        this.drawable.getViewPoint().panLeftRight(angle);
        repaint();
    }

    /**
     * Adjusts the viewing distance so that the chart fits the current panel
     * size.  A margin is left (see {@link #getMargin()} around the edges to 
     * leave room for labels etc.
     */
    public void zoomToFit() {
        zoomToFit(getSize());
    }
    
    /**
     * Adjusts the viewing distance so that the chart fits the specified
     * size.  A margin is left (see {@link #getMargin()} around the edges to 
     * leave room for labels etc.
     * 
     * @param size  the target size (<code>null</code> not permitted).
     */    
    public void zoomToFit(Dimension2D size) {
        int w = (int) (size.getWidth() * (1.0 - this.margin));
        int h = (int) (size.getHeight() * (1.0 - this.margin));
        Dimension2D target = new Dimension(w, h);
        Dimension3D d3d = this.drawable.getDimensions();
        float distance = this.drawable.getViewPoint().optimalDistance(target, 
                d3d, this.drawable.getProjDistance());
        this.drawable.getViewPoint().setRho(distance);
        repaint();        
    }

    /**
     * Paints the panel by asking the drawable to render a 2D projection of the 
     * objects it is managing.
     *
     * @param g  the graphics target (assumed to be an instance of
     *           <code>Graphics2D</code>).
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform saved = g2.getTransform();
        Dimension size = getSize();
        Rectangle drawArea = new Rectangle(size.width, size.height);
        this.drawable.draw(g2, drawArea);
        g2.setTransform(saved);
    }
  
    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        // nothing to do
    }

    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        // nothing to do
    }

    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseExited(MouseEvent e) {
        // nothing to do
    }

    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     */
    @Override
    public void mousePressed(MouseEvent e) {
        this.lastClickPoint = e.getPoint();
        this.lastMovePoint = this.lastClickPoint;
        this.offsetAtMousePressed = this.drawable.getTranslate2D();
    }

    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // nothing to do
    }

    /* (non-Javadoc)
     * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.isAltDown()) {
            Point currPt = e.getPoint();
            Offset2D offset = this.offsetAtMousePressed;
            Point lastPt = getLastClickPoint();
            double dx = offset.getDX() + (currPt.x - lastPt.x);
            double dy = offset.getDY() + (currPt.y - lastPt.y);
            this.drawable.setTranslate2D(new Offset2D(dx, dy));
        } else {
            Point currPt = e.getPoint();
            int dx = currPt.x - this.lastMovePoint.x;
            int dy = currPt.y - this.lastMovePoint.y;
            this.lastMovePoint = currPt;
            this.drawable.getViewPoint().panLeftRight(-dx * Math.PI / 120);
            this.drawable.getViewPoint().moveUpDown(-dy * Math.PI / 120);
            repaint();
        }
    }

    /* (non-Javadoc)
     * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        // nothing to do
    }

    /**
     * Receives notification of a mouse wheel movement and responds by moving
     * the viewpoint in or out (zooming).
     * 
     * @param mwe  the mouse wheel event. 
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent mwe) {
        float units = mwe.getUnitsToScroll();
        double valRho = Math.max(this.minViewingDistance, 
                this.drawable.getViewPoint().getRho() + units);
        this.drawable.getViewPoint().setRho(valRho);
        repaint();
    }
    
    /**
     * Writes the current content to the specified file in PDF format.  This 
     * will only work when the OrsonPDF library is found on the classpath.
     * Reflection is used to ensure there is no compile-time dependency on
     * OrsonPDF (which is non-free software).
     * 
     * @param file  the output file (<code>null</code> not permitted).
     * @param w  the chart width.
     * @param h  the chart height.
     */
    void writeAsPDF(File file, int w, int h) {
        if (!ExportFormats.isOrsonPDFAvailable()) {
            throw new IllegalStateException(
                    "OrsonPDF is not present on the classpath.");
        }
        ArgChecks.nullNotPermitted(file, "file");
        try {
            Class pdfDocClass = Class.forName("com.orsonpdf.PDFDocument");
            Object pdfDoc = pdfDocClass.newInstance();
            Method m = pdfDocClass.getMethod("createPage", Rectangle2D.class);
            Rectangle2D rect = new Rectangle(w, h);
            Object page = m.invoke(pdfDoc, rect);
            Method m2 = page.getClass().getMethod("getGraphics2D");
            Graphics2D g2 = (Graphics2D) m2.invoke(page);
            Rectangle2D drawArea = new Rectangle2D.Double(0, 0, w, h);
            this.drawable.draw(g2, drawArea);
            Method m3 = pdfDocClass.getMethod("writeToFile", File.class);
            m3.invoke(pdfDoc, file);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (InstantiationException ex) {
            throw new RuntimeException(ex);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        } catch (NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        } catch (SecurityException ex) {
            throw new RuntimeException(ex);
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException(ex);
        } catch (InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
    }
       
    /**
     * Writes the current content to the specified file in SVG format.  This 
     * will only work when the JFreeSVG library is found on the classpath.
     * Reflection is used to ensure there is no compile-time dependency on
     * JFreeSVG.
     * 
     * @param file  the output file (<code>null</code> not permitted).
     * @param w  the chart width.
     * @param h  the chart height.
     */
    void writeAsSVG(File file, int w, int h) {
        if (!ExportFormats.isJFreeSVGAvailable()) {
            throw new IllegalStateException(
                    "JFreeSVG is not present on the classpath.");
        }
        ArgChecks.nullNotPermitted(file, "file");
        try {
            Class svg2Class = Class.forName(
                    "org.jfree.graphics2d.svg.SVGGraphics2D");
            Constructor c1 = svg2Class.getConstructor(int.class, int.class);
            Graphics2D svg2 = (Graphics2D) c1.newInstance(w, h);
            Rectangle2D drawArea = new Rectangle2D.Double(0, 0, w, h);
            this.drawable.draw(svg2, drawArea);
            Class svgUtilsClass = Class.forName(
                    "org.jfree.graphics2d.svg.SVGUtils");
            Method m1 = svg2Class.getMethod("getSVGElement", (Class[]) null);
            String element = (String) m1.invoke(svg2, (Object[]) null);
            Method m2 = svgUtilsClass.getMethod("writeToSVG", File.class, String.class);
            m2.invoke(svgUtilsClass, file, element);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (InstantiationException ex) {
            throw new RuntimeException(ex);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        } catch (NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        } catch (SecurityException ex) {
            throw new RuntimeException(ex);
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException(ex);
        } catch (InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
    }
  
}
