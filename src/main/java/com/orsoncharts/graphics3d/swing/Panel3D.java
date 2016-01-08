/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-2016, by Object Refinery Limited.  All rights reserved.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 * 
 * If you do not wish to be bound by the terms of the GPL, an alternative
 * commercial license can be purchased.  For details, please see visit the
 * Orson Charts home page:
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 */

package com.orsoncharts.graphics3d.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Dimension2D;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.ToolTipManager;

import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.graphics3d.Drawable3D;
import com.orsoncharts.graphics3d.Offset2D;
import com.orsoncharts.graphics3d.ViewPoint3D;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.ExportUtils;
import com.orsoncharts.graphics3d.RenderingInfo;

/**
 * A panel that displays a set of 3D objects from a particular viewing point.
 * The view point is maintained by the {@link Drawable3D} but the panel
 * provides convenience methods to get/set it.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. 
 */
@SuppressWarnings("serial")
public class Panel3D extends JPanel implements MouseListener, 
        MouseMotionListener, MouseWheelListener {
  
    /**
     * The object that is displayed in the panel.
     */
    private Drawable3D drawable;
    
    /** 
     * The minimum viewing distance (zooming in will not go closer than this).
     */
    private double minViewingDistance;

    private double maxViewingDistanceMultiplier;

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
     * The (screen) point of the last mouse click (will be {@code null} 
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
    
    private RenderingInfo renderingInfo;
    
    /**
     * Creates a new panel with the specified {@link Drawable3D} to
     * display.
     *
     * @param drawable  the content to display ({@code null} not 
     *     permitted).
     */
    public Panel3D(Drawable3D drawable) {
        super(new BorderLayout());
        ArgChecks.nullNotPermitted(drawable, "drawable");
        this.drawable = drawable;
        this.margin = 0.25;
        this.minViewingDistance 
                = drawable.getDimensions().getDiagonalLength();
        this.maxViewingDistanceMultiplier = 8.0;
        this.panIncrement = Math.PI / 60;
        this.rotateIncrement = Math.PI / 60;
        this.rollIncrement = Math.PI / 60;
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
    }

    /**
     * Returns the {@code Drawable3D} object that is displayed in this
     * panel.  This is specified via the panel constructor and there is no
     * setter method to change it.
     * 
     * @return The {@code Drawable3D} object (never {@code null}).
     */
    public Drawable3D getDrawable() {
        return this.drawable;
    }

    /** 
     * Returns the margin, expressed as a percentage, that controls the amount
     * of space to leave around the edges of the 3D content when the 
     * {@code zoomToFit()} method is called.  The default value is 
     * {@code 0.25} (25 percent).
     * 
     * @return The margin. 
     */
    public double getMargin() {
        return this.margin;
    }
    
    /**
     * Sets the margin that controls the amount of space to leave around the
     * edges of the 3D content when the {@code zoomToFit()} method is 
     * called.
     *
     * @param margin  the margin (as a percentage, where 0.25 = 25 percent).
     */
    public void setMargin(double margin) {
        this.margin = margin;
    }
    
    /**
     * Returns the minimum viewing distance.  Zooming by mouse wheel or other
     * means will not move the viewing point closer than this.  The value
     * is computed in the constructor from the dimensions of the drawable 
     * object.
     * 
     * @return The minimum viewing distance.
     */
    public double getMinViewingDistance() {
        return this.minViewingDistance;
    }
    
    /**
     * Returns the multiplier for the maximum viewing distance (a multiple of
     * the minimum viewing distance).  The default value is {@code 8.0}.
     * 
     * @return The multiplier.
     * 
     * @since 1.3
     */
    public double getMaxViewingDistanceMultiplier() {
        return this.maxViewingDistanceMultiplier;
    }

    /**
     * Sets the multiplier used to calculate the maximum viewing distance.
     * 
     * @param multiplier  the new multiplier. 
     * 
     * @since 1.3
     */
    public void setMaxViewingDistanceMultiplier(double multiplier) {
        this.maxViewingDistanceMultiplier = multiplier;
    }
    
    /**
     * Returns the angle delta for each pan left or right.  The default
     * value is {@code Math.PI / 60}.
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
     * value is {@code Math.PI / 60}.
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
     * value is {@code Math.PI / 60}.
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
     * @return  The view point (never {@code null}).
     */
    public ViewPoint3D getViewPoint() {
        return this.drawable.getViewPoint();
    }

    /**
     * Sets a new view point and repaints the panel.
     *
     * @param vp  the view point ({@code null} not permitted).
     */
    public void setViewPoint(ViewPoint3D vp) {
        ArgChecks.nullNotPermitted(vp, "vp");
        this.drawable.setViewPoint(vp);  // 
        repaint();
    }
    
    /**
     * Returns the last click point (possibly {@code null}).
     * 
     * @return The last click point (possibly {@code null}).
     */
    protected Point getLastClickPoint() {
        return this.lastClickPoint;
    }
    
    /**
     * Returns the rendering info from the previous call to
     * draw().
     * 
     * @return The rendering info (possibly {@code null}).
     */
    protected RenderingInfo getRenderingInfo() {
        return this.renderingInfo;
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
     * @param size  the target size ({@code null} not permitted).
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
     * @param g  the graphics target ({@code null} not permitted, assumed to be
     *     an instance of {@code Graphics2D}).
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform saved = g2.getTransform();
        Dimension size = getSize();
        Insets insets = getInsets();
        Rectangle drawArea = new Rectangle(insets.left, insets.top, 
                size.width - insets.left - insets.right, 
                size.height - insets.top - insets.bottom);
        this.renderingInfo = this.drawable.draw(g2, drawArea);
        g2.setTransform(saved);
    }
  
    /**
     * Registers this component with the tool tip manager.
     * 
     * @since 1.3
     */
    public void registerForTooltips() {
        ToolTipManager.sharedInstance().registerComponent(this);
    }
    
    /**
     * Unregisters this component with the tool tip manager.
     * 
     * @since 1.3
     */
    public void unregisterForTooltips() {
        ToolTipManager.sharedInstance().unregisterComponent(this);
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
        double maxViewingDistance = this.maxViewingDistanceMultiplier 
                * this.minViewingDistance;
        double valRho = Math.max(this.minViewingDistance, 
                Math.min(maxViewingDistance, 
                this.drawable.getViewPoint().getRho() + units));
        this.drawable.getViewPoint().setRho(valRho);
        repaint();
    }
    
    /**
     * Writes the current content to the specified file in PDF format.  This 
     * will only work when the OrsonPDF library is found on the classpath.
     * Reflection is used to ensure there is no compile-time dependency on
     * OrsonPDF (which is non-free software).
     * 
     * @param file  the output file ({@code null} not permitted).
     * @param w  the chart width.
     * @param h  the chart height.
     * 
     * @deprecated Use ExportUtils.writeAsPDF() directly.
     */
    void writeAsPDF(File file, int w, int h) {
        ExportUtils.writeAsPDF(drawable, w, h, file);
    }
       
    /**
     * Writes the current content to the specified file in SVG format.  This 
     * will only work when the JFreeSVG library is found on the classpath.
     * Reflection is used to ensure there is no compile-time dependency on
     * JFreeSVG.
     * 
     * @param file  the output file ({@code null} not permitted).
     * @param w  the chart width.
     * @param h  the chart height.
     * 
     * @deprecated Use ExportUtils.writeAsPDF() directly.
     */
    void writeAsSVG(File file, int w, int h) {
        ExportUtils.writeAsSVG(this.drawable, w, h, file);
    }
  
}
