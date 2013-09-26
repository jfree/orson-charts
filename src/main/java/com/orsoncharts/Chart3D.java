/* =============
 * OrsonCharts3D
 * =============
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.event.EventListenerList;

import com.orsoncharts.axis.Axis3D;
import com.orsoncharts.axis.TextAnchor;
import com.orsoncharts.axis.TextUtils;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.Drawable3D;
import com.orsoncharts.graphics3d.Face;
import com.orsoncharts.graphics3d.Object3D;
import com.orsoncharts.graphics3d.Point3D;
import com.orsoncharts.graphics3d.Tools2D;
import com.orsoncharts.graphics3d.ViewPoint3D;
import com.orsoncharts.graphics3d.World;
import com.orsoncharts.graphics3d.ZOrderComparator;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.plot.PiePlot3D;
import com.orsoncharts.plot.Plot3DChangeEvent;
import com.orsoncharts.plot.Plot3DChangeListener;
import com.orsoncharts.plot.Plot3D;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.table.SimpleTextElement;
import com.orsoncharts.table.TableElement;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.geom.Rectangle2D;

/**
 * A chart object for 3D charts.
 */
public class Chart3D implements Drawable3D, Plot3DChangeListener {

    /** The chart title. */
    private TableElement title;
    
    /** A world to contain the 3D objects rendered by this chart. */
    private World world;

    private boolean worldNeedsRefreshing;
    
    /** The view point. */
    private ViewPoint3D viewPoint;

    /** 
     * The chart box (a frame of reference for the chart - not used in pie 
     * charts). 
     */
    private ChartBox3D chartBox;

    /** The plot. */
    private Plot3D plot;
    
    /** Storage for registered change listeners. */
    private transient EventListenerList listenerList;

    /**
     * A flag that controls whether or not the chart will notify listeners
     * of changes (defaults to true, but sometimes it is useful to disable
     * this).
     */
    private boolean notify;
  
    /**
     * Creates a 3D chart for the specified plot.
     * 
     * @param chartTitle  the chart title.
     * @param plot  the plot (<code>null</code> not permitted).
     */
    public Chart3D(String chartTitle, Plot3D plot) {
        ArgChecks.nullNotPermitted(plot, "plot");
        if (chartTitle != null) {
            SimpleTextElement ste = new SimpleTextElement(chartTitle);
            ste.setFont(new Font("Tahoma", Font.BOLD, 18));
            this.title = ste;
        }
        this.world = new World();
        this.worldNeedsRefreshing = true;
        this.viewPoint = new ViewPoint3D((float) (4.4 * Math.PI / 3), 
                (float) (7 * Math.PI / 6), 30.0f);
        this.plot = plot;
        this.notify = true;
        this.listenerList = new EventListenerList();
        this.plot.addChangeListener(this);
    }

    /**
     * Returns the view point.
     * 
     * @return The view point (never <code>null</code>). 
     */
    @Override
    public ViewPoint3D getViewPoint() {
        return this.viewPoint;
    }

    /**
     * Sets the view point.
     * 
     * @param viewPoint  the view point (<code>null</code> not permitted). 
     */
    @Override
    public void setViewPoint(ViewPoint3D viewPoint) {
        ArgChecks.nullNotPermitted(viewPoint, "viewPoint");
        this.viewPoint = viewPoint;
        fireChangeEvent();
    }    
    
    /**
     * Returns the plot.
     *
     * @return The plot (never <code>null</code>).
     */
    public Plot3D getPlot() {
        return this.plot;
    }

    /**
     * Refreshes the world of 3D objects.  Usually this is called when a 
     * plot change event is received.
     */
    private void refreshWorld() {
        this.world = new World();  
        // TODO: when we re-render the chart, should we
        // create a new world, or recycle the existing one?
    
        Dimension3D dim = this.plot.getDimensions();
        double w = dim.getWidth();
        double h = dim.getHeight();
        double d = dim.getDepth();
        if (!(this.plot instanceof PiePlot3D)) {
            this.chartBox = new ChartBox3D(w, h, d, -w / 2, -h / 2, -d / 2, 
                    Color.WHITE);
            world.add(chartBox.getObject3D());    
        }

        this.plot.composeToWorld(world, -w / 2, -h / 2, -d / 2);
        this.worldNeedsRefreshing = false;
    }
    
    /**
     * Draws the chart to the specified output target.
     * 
     * @param g2  the output target. 
     */
    @Override
    public void draw(Graphics2D g2, Rectangle bounds) {
         
        if (this.worldNeedsRefreshing) {
            refreshWorld();  
        }
        float strokeWidth = 0.0f;
        if (this.plot instanceof PiePlot3D) {
            strokeWidth = 1.5f;
        }
        g2.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND));
        g2.setPaint(Color.WHITE);
        g2.fill(bounds);
        AffineTransform saved = g2.getTransform();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.translate(bounds.width / 2, bounds.height / 2);

        Dimension3D dim3D = this.plot.getDimensions();
        double w = dim3D.getWidth();
        double h = dim3D.getHeight();
        double depth = dim3D.getDepth();
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
                        (int) (c.getBlue() * shade), c.getAlpha()));
                    g2.fill(p);
                    g2.draw(p);
                }
                f.setRendered(true);
            } else {
                f.setRendered(false);
            }
        }
   
        // if a PiePlot3D then there will be an overlay to track the pie label positions
        if (this.plot instanceof PiePlot3D) {
            PiePlot3D p = (PiePlot3D) this.plot;
            World labelOverlay = new World();
            List<Object3D> objs = p.getLabelFaces(-w / 2, -h / 2, -depth / 2);
            for (Object3D obj : objs) {
                labelOverlay.add(obj);
            }
            Point2D[] ppts = labelOverlay.calculateProjectedPoints(
                    this.viewPoint, 1000f);
            for (int i = 0; i < p.getDataset().getItemCount() * 2; i++) {
                Face f = labelOverlay.getFaces().get(i);
                if (Tools2D.area2(ppts[f.getVertexIndex(0)], 
                        ppts[f.getVertexIndex(1)], 
                        ppts[f.getVertexIndex(2)]) > 0) {
                    Comparable key = p.getDataset().getKey(i / 2);
                    g2.setColor(Color.BLACK);
                    g2.setFont(p.getDefaultSectionFont());
                    Point2D pt = Tools2D.centrePoint(ppts[f.getVertexIndex(0)], 
                            ppts[f.getVertexIndex(1)], ppts[f.getVertexIndex(2)],
                            ppts[f.getVertexIndex(3)]);
                    TextUtils.drawAlignedString(key.toString(), g2, 
                            (float) pt.getX(), (float) pt.getY(), 
                            TextAnchor.CENTER);
                }
            }
        }

    
        // if a CategoryPlot3D then there will be a ChartBox overlay

        // if an XYZPlot then there will be a ChartBox overlay
        if (this.plot instanceof XYZPlot 
                || this.plot instanceof CategoryPlot3D) {
            World labelOverlay = new World();
            ChartBox3D cb = new ChartBox3D(w, h, depth, -w / 2, -h / 2, 
                    -depth / 2, new Color(0, 0, 255, 200));
            labelOverlay.add(cb.getObject3D());
            Point2D[] axisPts2D = labelOverlay.calculateProjectedPoints(
                    this.viewPoint, 1000f);

            // vertices
            Point2D v0 = axisPts2D[0];
            Point2D v1 = axisPts2D[1];
            Point2D v2 = axisPts2D[2];
            Point2D v3 = axisPts2D[3];
            Point2D v4 = axisPts2D[4];
            Point2D v5 = axisPts2D[5];
            Point2D v6 = axisPts2D[6];
            Point2D v7 = axisPts2D[7];

            // faces
            boolean a = this.chartBox.faceA().isRendered();
            boolean b = this.chartBox.faceB().isRendered();
            boolean c = this.chartBox.faceC().isRendered();
            boolean d = this.chartBox.faceD().isRendered();
            boolean e = this.chartBox.faceE().isRendered();
            boolean f = this.chartBox.faceF().isRendered();

            Axis3D xAxis = null, yAxis = null, zAxis = null;
            if (this.plot instanceof XYZPlot) {
                XYZPlot xyzplot = (XYZPlot) this.plot;
                xAxis = xyzplot.getXAxis();
                yAxis = xyzplot.getYAxis();
                zAxis = xyzplot.getZAxis();
            } else if (this.plot instanceof CategoryPlot3D) {
                CategoryPlot3D catplot = (CategoryPlot3D) this.plot;
                xAxis = catplot.getColumnAxis();
                yAxis = catplot.getValueAxis();
                zAxis = catplot.getRowAxis();
            }
            double ab = (count(a, b) == 1 ? v0.distance(v1) : 0.0);
            double bc = (count(b, c) == 1 ? v3.distance(v2) : 0.0);
            double cd = (count(c, d) == 1 ? v4.distance(v7) : 0.0);
            double da = (count(d, a) == 1 ? v5.distance(v6) : 0.0);
            double be = (count(b, e) == 1 ? v0.distance(v3) : 0.0);
            double bf = (count(b, f) == 1 ? v1.distance(v2) : 0.0);
            double df = (count(d, f) == 1 ? v6.distance(v7) : 0.0);
            double de = (count(d, e) == 1 ? v5.distance(v4) : 0.0);
            double ae = (count(a, e) == 1 ? v0.distance(v5) : 0.0);
            double af = (count(a, f) == 1 ? v1.distance(v6) : 0.0);
            double cf = (count(c, f) == 1 ? v2.distance(v7) : 0.0);
            double ce = (count(c, e) == 1 ? v3.distance(v4) : 0.0);

            if (count(a, b) == 1 && longest(ab, bc, cd, da)) {
                xAxis.render(g2, v0, v1, v7, true);
            }
            if (count(b, c) == 1 && longest(bc, ab, cd, da)) {
                xAxis.render(g2, v3, v2, v6, true);
            }
            if (count(c, d) == 1 && longest(cd, ab, bc, da)) {
                xAxis.render(g2, v4, v7, v1, true);
            }
            if (count(d, a) == 1 && longest(da, ab, bc, cd)) {
                xAxis.render(g2, v5, v6, v3, true);
            }

            if (count(b, e) == 1 && longest(be, bf, df, de)) {
                yAxis.render(g2, v0, v3, v7, true);
            }
            if (count(b, f) == 1 && longest(bf, be, df, de)) {
                yAxis.render(g2, v1, v2, v4, true);
            }
            if (count(d, f) == 1 && longest(df, be, bf, de)) {
                yAxis.render(g2, v6, v7, v0, true);
            }
            if (count(d, e) == 1 && longest(de, be, bf, df)) {
                yAxis.render(g2, v5, v4, v1, true);
            }

            if (count(a, e) == 1 && longest(ae, af, cf, ce)) {
                zAxis.render(g2, v0, v5, v2, true);
            }
            if (count(a, f) == 1 && longest(af, ae, cf, ce)) {
                zAxis.render(g2, v1, v6, v3, true);
            }
            if (count(c, f) == 1 && longest(cf, ae, af, ce)) {
                zAxis.render(g2, v2, v7, v5, true);
            }
            if (count(c, e) == 1 && longest(ce, ae, af, cf)) {
                zAxis.render(g2, v3, v4, v6, true);
            }
        }    

        g2.setTransform(saved);
        if (this.title != null) {
            Rectangle2D titleArea = this.title.preferredSize(g2, bounds);
            this.title.render(g2, titleArea);
        }

    }

    private boolean longest(double x, double a, double b, double c) {
        return x >= a && x >= b && x >= c;
    }

    private int count(boolean a, boolean b) {
        int result = 0;
        if (a) {
            result++;
        }
        if (b) {
            result++;
        }
        return result;
    }

    @Override
    public void plotChanged(Plot3DChangeEvent event) {
        refreshWorld();
        notifyListeners(new Chart3DChangeEvent(event, this));
    }

    public void addChangeListener(Chart3DChangeListener listener) {
        this.listenerList.add(Chart3DChangeListener.class, listener);   
    }
  
    public void removeChangeListener(Chart3DChangeListener listener) {
        this.listenerList.remove(Chart3DChangeListener.class, listener);  
    }
  
    /**
     * Notifies all registered listeners that the chart has been modified.
     *
     * @param event  information about the change event.
     */
    public void notifyListeners(Chart3DChangeEvent event) {
        // if the 'notify' flag has been switched to false, we don't notify
        // the listeners
        if (!this.notify) {
            return;
        }
        Object[] listeners = this.listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == Chart3DChangeListener.class) { 
                ((Chart3DChangeListener) listeners[i + 1]).chartChanged(event);
            }
        }
    }
  
    /**
     * Returns a flag that controls whether or not change events are sent to
     * registered listeners.
     * 
     * @return A boolean.
     *
     * @see #setNotify(boolean)
     */
    public boolean isNotify() {
        return this.notify;
    }

    /**
     * Sets a flag that controls whether or not listeners receive
     * {@link Plot3DChangeEvent} notifications.
     *
     * @param notify  a boolean.
     *
     * @see #isNotify()
     */
    public void setNotify(boolean notify) {
        this.notify = notify;
        // if the flag is being set to true, there may be queued up changes...
        if (notify) {
            fireChangeEvent();
        }
    }
  
    /**
     * Sends a {@link Chart3DChangeEvent} to all registered listeners.
     */
    protected void fireChangeEvent() {
        notifyListeners(new Chart3DChangeEvent(this, this));
    }

}
