/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package org.jfree.graphics3d;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;

import javax.swing.JPanel;

/**
 * A panel that displays a set of 3D objects from some viewing point.
 */
public class Panel3D extends JPanel implements MouseListener, 
        MouseMotionListener, MouseWheelListener {

  /** The world of 3D objects being displayed. */
  private World world;

  /** 
   * The current view point.  The objects in the World stay in one place,
   * but the viewer can move around.
   */
  private ViewPoint3D viewPoint;

  /** 
   * The (screen) point of the last mouse click (will be null initially).  
   * Used to calculate the mouse drag distance and direction.
   */
  private Point lastClickPoint;

  private ViewPoint3D lastViewPoint;

  private World overlayWorld;

  /** Tracks whether each face was visible in the previous rendering. */
  private boolean[] faceVisible;

  /**
   * Creates a new panel with the specified view point and objects to
   * display.
   *
   * @param world  the world (<code>null</code> not permitted).
   */
  public Panel3D(World world) {
    super(new BorderLayout());
    add(createButtonPanel(), BorderLayout.SOUTH);
    ArgChecks.nullNotPermitted(world, "world");
    this.world = world;
    this.viewPoint = new ViewPoint3D((float) (3 * Math.PI / 2.0), (float) Math.PI, 40.0f);
    this.lastViewPoint = this.viewPoint;
    addMouseListener(this);
    addMouseMotionListener(this);
    addMouseWheelListener(this);
//        this.faceVisible = new boolean[this.world.getFaces().size()];
    
  }
  
  private JPanel createButtonPanel() {
    JPanel panel = new JPanel(new FlowLayout());
    JButton button1 = new JButton("Export...");
    panel.add(button1);
    return panel;
  }

  /**
   * Returns a reference to the world of 3D objects being displayed by this
   * panel.
   *
   * @return The world (never <code>null</code>).
   */
  public World getWorld() {
    return this.world;
  }

  /**
   * Sets the world.
   *
   * @param world  the world (<code>null</code> not permitted).
   */
  public void setWorld(World world) {
    ArgChecks.nullNotPermitted(world, "world");
    this.world = world;
    // TODO: this should trigger a repaint
  }

  public void setOverlayWorld(World world) {
    this.overlayWorld = world;
  }

  /**
   * Returns the current world viewpoint.
   *
   * @return  The view point.
   */
  public ViewPoint3D getViewPoint() {
    return this.viewPoint;
  }

  /**
   * Sets the view point.
   *
   * @param vp  the view point.
   */
  public void setViewPoint(ViewPoint3D vp) {
    this.viewPoint = vp;
    //System.out.println(vp);
    repaint();
  }

  /**
   * Paints a 2D projection of the objects.
   *
   * @param g  the graphics target (assumed to be an instance of
   *           <code>Graphics2D</code>).
   */
  @Override
  public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    g2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND,
            BasicStroke.JOIN_ROUND));
    Dimension dim = getSize();
    g2.setPaint(Color.white);
    g2.fillRect(0, 0, dim.width, dim.height);
    g2.setPaint(Color.blue);
    AffineTransform saved = g2.getTransform();
    g2.translate(dim.width / 2, (dim.height - 40) / 2);
    Map hints = new HashMap();
    hints.put(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
    g2.addRenderingHints(hints);

    Point3D[] eyePts = this.world.calculateEyeCoordinates(this.viewPoint);

    Point2D[] pts = this.world.calculateProjectedPoints(this.viewPoint,
                1000f);
    List<Face> facesInPaintOrder = new ArrayList<Face>(this.world.getFaces());

    // sort faces by z-order
    Collections.sort(facesInPaintOrder, new ZOrderComparator(eyePts));

    for (Face f : facesInPaintOrder) {
      GeneralPath p = new GeneralPath();
      for (int v = 0; v < f.getVertexCount(); v++) {
        if (v == 0) {
          p.moveTo(pts[f.getVertexIndex(v)].getX(),
                   pts[f.getVertexIndex(v)].getY());
        }
        else {
          p.lineTo(pts[f.getVertexIndex(v)].getX(),
                        pts[f.getVertexIndex(v)].getY());
        }
      }
      p.closePath();

      double[] plane = f.calculateNormal(eyePts);
      double inprod = plane[0] * this.world.getSunX() + plane[1]
                    * this.world.getSunY() + plane[2] * this.world.getSunZ();
      double shade = (inprod + 1) / 2.0;
      if (Tools2D.area2(pts[f.getVertexIndex(0)],
          pts[f.getVertexIndex(1)], pts[f.getVertexIndex(2)]) > 0) {
        Color c = f.getColor();
        if (c != null) {
          g2.setPaint(new Color((int) (c.getRed() * shade),
                  (int) (c.getGreen() * shade),
                  (int) (c.getBlue() * shade)));
          g2.fill(p);
          g2.draw(p);
        }
        f.setRendered(true);
      } else {
        f.setRendered(false);
      }

    }
    g2.setTransform(saved);
  }

  /* (non-Javadoc)
   * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
   */
  @Override
  public void mouseClicked(MouseEvent e) {
    // TODO Auto-generated method stub
  }

  /* (non-Javadoc)
   * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
   */
  @Override
  public void mouseEntered(MouseEvent e) {
    // TODO Auto-generated method stub
  }

  /* (non-Javadoc)
   * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
   */
  @Override
  public void mouseExited(MouseEvent e) {
    // TODO Auto-generated method stub
  }

  /* (non-Javadoc)
   * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
   */
  @Override
  public void mousePressed(MouseEvent e) {
    this.lastClickPoint = e.getPoint();
    this.lastViewPoint = this.viewPoint;
  }

  /* (non-Javadoc)
   * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
   */
  @Override
  public void mouseReleased(MouseEvent e) {
  }

  /* (non-Javadoc)
   * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
   */
  @Override
  public void mouseDragged(MouseEvent e) {
    Point currPt = e.getPoint();
    int dx = currPt.x - this.lastClickPoint.x;
    int dy = currPt.y - this.lastClickPoint.y;

    float valTheta = this.lastViewPoint.getTheta() + (float) (dx * Math.PI / 100);
    float valRho = this.lastViewPoint.getRho();
    float valPhi = this.lastViewPoint.getPhi() + (float) (dy * Math.PI / 100);
    setViewPoint(new ViewPoint3D(valTheta, valPhi, valRho));
  }

  /* (non-Javadoc)
   * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
   */
  @Override
  public void mouseMoved(MouseEvent e) {
    // TODO Auto-generated method stub
  }

  @Override
  public void mouseWheelMoved(MouseWheelEvent mwe) {
    float units = mwe.getUnitsToScroll();
    float valRho = Math.max(10.0f, this.viewPoint.getRho() + units);
    System.out.println(valRho);
    float valTheta = this.viewPoint.getTheta();
    float valPhi = this.viewPoint.getPhi();
    setViewPoint(new ViewPoint3D(valTheta, valPhi, valRho));
  }

}
