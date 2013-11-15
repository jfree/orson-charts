/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts;

import java.awt.BasicStroke;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.event.EventListenerList;

import com.orsoncharts.ChartBox3D.CBFace;
import com.orsoncharts.axis.Axis3D;
import com.orsoncharts.axis.TickData;
import com.orsoncharts.axis.ValueAxis3D;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.Drawable3D;
import com.orsoncharts.graphics3d.Face;
import com.orsoncharts.graphics3d.Object3D;
import com.orsoncharts.graphics3d.Point3D;
import com.orsoncharts.graphics3d.Utils2D;
import com.orsoncharts.graphics3d.ViewPoint3D;
import com.orsoncharts.graphics3d.World;
import com.orsoncharts.graphics3d.ZOrderComparator;
import com.orsoncharts.legend.LegendAnchor;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.plot.PiePlot3D;
import com.orsoncharts.plot.Plot3DChangeEvent;
import com.orsoncharts.plot.Plot3DChangeListener;
import com.orsoncharts.plot.Plot3D;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.table.TextElement;
import com.orsoncharts.table.TableElement;
import com.orsoncharts.util.Anchor2D;
import com.orsoncharts.graphics3d.Offset2D;
import com.orsoncharts.util.RefPt2D;
import com.orsoncharts.util.TextUtils;
import com.orsoncharts.util.TextAnchor;
import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.legend.LegendBuilder;
import com.orsoncharts.legend.StandardLegendBuilder;
import com.orsoncharts.table.GridElement;
import com.orsoncharts.table.HAlign;
import com.orsoncharts.util.ObjectUtils;

/**
 * A chart object for 3D charts (this is the umbrella object that manages all
 * the components of the chart).  The {@link Chart3DFactory} class provides 
 * some factory methods to construct common types of charts.
 * <br><br>
 * All rendering is done via the Java2D API, so this object is able to draw to 
 * any implementation of the Graphics2D API (including 
 * <a href="http://www.jfree.org/jfreesvg/" target="JFreeSVG">JFreeSVG</a> for 
 * SVG output, and 
 * <a href="http://www.object-refinery.com/pdf/" target="OrsonPDF">OrsonPDF</a> 
 * for PDF output).
 * <br><br>
 * In the step prior to rendering, a chart is composed in a 3D model that is
 * referred to as the "world".  The dimensions of this 3D model are measured
 * in "world units" and the overall size is controlled by the plot.  You will 
 * see some attributes in the API that are specified in "world units", and these
 * can be used to modify how objects are composed within the 3D world model.  
 * Once the objects (for example, bars in a bar chart) within the world have 
 * been composed, they are projected onto a 2D plane and rendered to the 
 * <code>Graphics2D</code> target (such as the screen, image, SVG file or 
 * PDF file).  
 * <br><br>
 * Charts can have simple titles or composite titles (anything that can be
 * constructed as a {@link TableElement} instance.  The {@link TitleUtils}
 * class contains methods to create a common title/subtitle composite title. 
 * This is illustrated in some of the demo applications.  The chart title
 * and legend (and also the axis labels) are not part of the 3D world model,
 * they are overlaid on the output after the 3D components have been
 * rendered.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 * 
 * @see Chart3DFactory
 * @see ChartPanel3D
 */

public class Chart3D implements Drawable3D, Plot3DChangeListener, Serializable {
    
    /** A background rectangle painter, if any. */
    private RectanglePainter background;
    
    /** The chart title (can be <code>null</code>). */
    private TableElement title;
    
    /** The anchor point for the title (never <code>null</code>). */
    private Anchor2D titleAnchor;
    
    /** A builder for the chart legend (can be <code>null</code>). */
    private LegendBuilder legendBuilder;
    
    /** The anchor point for the legend (never <code>null</code>). */
    private Anchor2D legendAnchor;
    
    /** The plot. */
    private Plot3D plot;
    
    /** The view point. */
    private ViewPoint3D viewPoint;

    /** The chart box color (never <code>null</code>). */
    private Color chartBoxColor;

    /** 
     * A translation factor applied to the chart when drawing.  We use this
     * to allow the user (optionally) to drag the chart from its center 
     * location to better align it with the chart title and legend.
     */
    private Offset2D translate2D;
    
    /** Storage for registered change listeners. */
    private transient EventListenerList listenerList;

    /**
     * A flag that controls whether or not the chart will notify listeners
     * of changes (defaults to <code>true</code>, but sometimes it is useful 
     * to disable this).
     */
    private boolean notify;
  
    /**
     * Creates a 3D chart for the specified plot.
     * 
     * @param title  the chart title (<code>null</code> permitted).
     * @param subtitle  the chart subtitle (<code>null</code> permitted).
     * @param plot  the plot (<code>null</code> not permitted).
     * 
     * @see Chart3DFactory
     */
    public Chart3D(String title, String subtitle, Plot3D plot) {
        ArgChecks.nullNotPermitted(plot, "plot");
        this.background = new StandardRectanglePainter(Color.WHITE);
        if (title != null) {
            this.title = TitleUtils.createTitle(title, subtitle);
        }
        this.titleAnchor = TitleAnchor.TOP_LEFT;
        this.legendBuilder = new StandardLegendBuilder();
        this.legendAnchor = LegendAnchor.BOTTOM_RIGHT;
        this.plot = plot;
        this.plot.addChangeListener(this);
        Dimension3D dim = this.plot.getDimensions();
        float distance = (float) dim.getDiagonalLength() * 3.0f;
        this.viewPoint = ViewPoint3D.createAboveViewPoint(distance);
        this.chartBoxColor = Color.WHITE;
        this.translate2D = new Offset2D();
        this.notify = true;
        this.listenerList = new EventListenerList();
    }

    /**
     * Returns the background painter (an object that is responsible for filling
     * the background area before charts are rendered).  The default value
     * is an instance of {@link StandardRectanglePainter} that paints the
     * background white.
     * 
     * @return The background painter (possibly <code>null</code>).
     * 
     * @see #setBackground(com.orsoncharts.RectanglePainter) 
     */
    public RectanglePainter getBackground() {
        return this.background;
    }
    
    /**
     * Sets the background painter and sends a {@link Chart3DChangeEvent} to
     * all registered listeners.  A background painter is used to fill in the
     * background of the chart before the 3D rendering takes place.  To fill
     * the background with a color or image, you can use 
     * {@link StandardRectanglePainter}.  To fill the background with a 
     * gradient paint, use {@link GradientRectanglePainter}.
     * 
     * @param background  the background painter (<code>null</code> permitted).
     * 
     * @see #getBackground() 
     */
    public void setBackground(RectanglePainter background) {
        this.background = background;
        fireChangeEvent();
    }
    
    /**
     * Returns the chart title.  A {@link TableElement} is used for the title,
     * since it allows a lot of flexibility in the types of title that can
     * be displayed.
     * 
     * @return The chart title (possibly <code>null</code>). 
     */
    public TableElement getTitle() {
        return this.title;
    }
    
    /**
     * Sets the chart title and sends a {@link Chart3DChangeEvent} to all 
     * registered listeners.  This is a convenience method that constructs
     * the required {@link TableElement} under-the-hood.
     * 
     * @param title  the title (<code>null</code> permitted). 
     */
    public void setTitle(String title) {
        if (title == null) {
            setTitle((TableElement) null);
        } else {
            setTitle(title, TitleUtils.DEFAULT_TITLE_FONT, 
                    TitleUtils.DEFAULT_TITLE_PAINT);
        }
    }
    
    /**
     * Sets the chart title and sends a {@link Chart3DChangeEvent} to all 
     * registered listeners.  This is a convenience method that constructs
     * the required {@link TableElement} under-the-hood.
     * 
     * @param title  the title (<code>null</code> not permitted). 
     * @param font  the font (<code>null</code> not permitted).
     * @param paint  the foreground paint (<code>null</code> not permitted).
     */
    public void setTitle(String title, Font font, Paint paint) {
        // defer 'title' null check
        ArgChecks.nullNotPermitted(font, "font");
        ArgChecks.nullNotPermitted(paint, "paint");
        TextElement te = new TextElement(title);
        te.setFont(font);
        te.setForegroundPaint(paint);
        setTitle(te);
    }
    
    /**
     * Sets the chart title and sends a {@link Chart3DChangeEvent} to all 
     * registered listeners.  You can set the title to <code>null</code>, in
     * which case there will be no chart title.
     * 
     * @param title  the title (<code>null</code> permitted).
     */
    public void setTitle(TableElement title) {
        this.title = title;
        fireChangeEvent();
    }

    /**
     * Returns the title anchor.  This controls the position of the title
     * in the chart area. 
     * 
     * @return The title anchor (never <code>null</code>).
     * 
     * @see #setTitleAnchor(com.orsoncharts.util.Anchor2D) 
     */
    public Anchor2D getTitleAnchor() {
        return this.titleAnchor;
    }
    
    /**
     * Sets the title anchor and sends a {@link Chart3DChangeEvent} to all
     * registered listeners.  There is a {@link TitleAnchor} class providing
     * some useful default anchors.
     * 
     * @param anchor  the anchor (<code>null</code> not permitted).
     * 
     * @see #getTitleAnchor() 
     */
    public void setTitleAnchor(Anchor2D anchor) {
        ArgChecks.nullNotPermitted(anchor, "anchor");
        this.titleAnchor = anchor;
        fireChangeEvent();
    }

    /**
     * Returns the plot, which manages the dataset, the axes (if any), the 
     * renderer (if any) and other attributes related to plotting data.  The
     * plot is specified via the constructor...there is no method to set a 
     * new plot for the chart, instead you need to create a new chart instance.
     *
     * @return The plot (never <code>null</code>).
     */
    public Plot3D getPlot() {
        return this.plot;
    }
    
    /**
     * Returns the chart box color (the chart box is the visible, open-sided 
     * box inside which data is plotted for all charts except pie charts). 
     * The default value is <code>Color.WHITE</code>.
     * 
     * @return The chart box color (never <code>null</code>). 
     * 
     * @see #setChartBoxColor(java.awt.Color) 
     */
    public Color getChartBoxColor() {
        return this.chartBoxColor;
    }
    
    /**
     * Sets the chart box color and sends a {@link Chart3DChangeEvent} to all 
     * registered listeners.  Bear in mind that {@link PiePlot3D} does not
     * display a chart box, so this attribute will be ignored for pie charts.
     * 
     * @param color  the color (<code>null</code> not permitted).
     * 
     * @see #getChartBoxColor()
     */
    public void setChartBoxColor(Color color) {
        ArgChecks.nullNotPermitted(color, "color");
        this.chartBoxColor = color;
        fireChangeEvent();
    }
    
    /**
     * Returns the dimensions of the 3D object.
     * 
     * @return The dimensions. 
     */
    @Override
    public Dimension3D getDimensions() {
        return this.plot.getDimensions();
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
     * Sets the offset in 2D-space for the rendering of the chart.  The 
     * default value is <code>(0, 0)</code> but the user can modify it via
     * ALT-mouse-drag in the chart panel, providing an easy way to get improved
     * chart alignment in the panels (especially prior to export to PNG, SVG or
     * PDF).
     * 
     * @return The offset (never <code>null</code>). 
     */
    @Override
    public Offset2D getTranslate2D() {
        return this.translate2D;    
    }
    
    /**
     * Sets the world offset and sends a {@link Chart3DChangeEvent} to all 
     * registered listeners.
     * 
     * @param offset  the new offset (<code>null</code> not permitted).
     */
    @Override
    public void setTranslate2D(Offset2D offset) {
        ArgChecks.nullNotPermitted(offset, "offset");
        this.translate2D = offset;
        fireChangeEvent();
    }
    
    /**
     * Returns the legend builder.  The default value is an instance of
     * {@link StandardLegendBuilder}.  If the legend builder is 
     * <code>null</code>, no legend will be displayed for the chart.
     * 
     * @return The legend builder (possibly <code>null</code>).
     * 
     * @see #setLegendBuilder(com.orsoncharts.legend.LegendBuilder) 
     * @see #setLegendAnchor(com.orsoncharts.util.Anchor2D) 
     */
    public LegendBuilder getLegendBuilder() {
        return this.legendBuilder;
    }
    
    /**
     * Sets the legend builder and sends a {@link Chart3DChangeEvent} to all
     * registered listeners.  When the legend builder is <code>null</code>, no
     * legend will be displayed on the chart.
     * 
     * @param legendBuilder  the legend builder (<code>null</code> permitted).
     * 
     * @see #setLegendAnchor(com.orsoncharts.util.Anchor2D) 
     */
    public void setLegendBuilder(LegendBuilder legendBuilder) {
        this.legendBuilder = legendBuilder;
        fireChangeEvent();
    }
    
    /**
     * Returns the legend anchor.
     * 
     * @return The legend anchor (never <code>null</code>).
     * 
     * @see #setLegendAnchor(com.orsoncharts.util.Anchor2D) 
     */
    public Anchor2D getLegendAnchor() {
        return this.legendAnchor;
    }
    
    /**
     * Sets the legend anchor and sends a {@link Chart3DChangeEvent} to all
     * registered listeners.  There is a {@link LegendAnchor} class providing
     * some useful default anchors.
     * 
     * @param anchor  the anchor (<code>null</code> not permitted).
     * 
     * @see #getLegendAnchor() 
     */
    public void setLegendAnchor(Anchor2D anchor) {
        ArgChecks.nullNotPermitted(anchor, "anchor");
        this.legendAnchor = anchor;
        fireChangeEvent();
    }

    /**
     * Creates a world containing the chart and the supplied chart box.
     * 
     * @param chartBox  the chart box (<code>null</code> permitted).
     */
    private World createWorld(ChartBox3D chartBox) {
        World world = new World();      
        Dimension3D dim = this.plot.getDimensions();
        double w = dim.getWidth();
        double h = dim.getHeight();
        double d = dim.getDepth();
        if (chartBox != null) {
            world.add(chartBox.getObject3D());
        }
        this.plot.compose(world, -w / 2, -h / 2, -d / 2);
        return world;
    }
    
    /**
     * Draws the chart to the specified output target.
     * 
     * @param g2  the output target. 
     */
    @Override
    public void draw(Graphics2D g2, Rectangle2D bounds) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(1.2f, BasicStroke.CAP_ROUND, 
                BasicStroke.JOIN_ROUND, 1f));
        Dimension3D dim3D = this.plot.getDimensions();
        double w = dim3D.getWidth();
        double h = dim3D.getHeight();
        double depth = dim3D.getDepth();
        ChartBox3D chartBox = null;
        if (this.plot instanceof XYZPlot 
                || this.plot instanceof CategoryPlot3D) {
            chartBox = new ChartBox3D(w, h, depth, -w / 2, -h / 2, -depth / 2, 
                    this.chartBoxColor);
            double[] tickUnits = findAxisTickUnits(g2, w, h, depth);
            List<TickData> xTicks = fetchXTickData(this.plot, tickUnits[0]);
            List<TickData> yTicks = fetchYTickData(this.plot, tickUnits[1]);
            List<TickData> zTicks = fetchZTickData(this.plot, tickUnits[2]);
            chartBox.configureTicks(xTicks, yTicks, zTicks);
        }
        World world = createWorld(chartBox);
        if (this.background != null) {
            this.background.fill(g2, bounds);
        }
        AffineTransform saved = g2.getTransform();
        g2.translate(bounds.getWidth() / 2.0 + this.translate2D.getDX(), 
                bounds.getHeight() / 2.0 + this.translate2D.getDY());
        Point3D[] eyePts = world.calculateEyeCoordinates(this.viewPoint);
        Point2D[] pts = world.calculateProjectedPoints(this.viewPoint, 1000f);
        List<Face> facesInPaintOrder = new ArrayList<Face>(world.getFaces());

        // sort faces by z-order
        Collections.sort(facesInPaintOrder, new ZOrderComparator(eyePts));

        for (Face f : facesInPaintOrder) {
            boolean drawOutline = f.getOutline();
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
            double inprod = plane[0] * world.getSunX() + plane[1]
                    * world.getSunY() + plane[2] * world.getSunZ();
            double shade = (inprod + 1) / 2.0;
            if (Utils2D.area2(pts[f.getVertexIndex(0)],
                    pts[f.getVertexIndex(1)], pts[f.getVertexIndex(2)]) > 0) {
                Color c = f.getColor();
                if (c != null) {
                    g2.setPaint(new Color((int) (c.getRed() * shade),
                        (int) (c.getGreen() * shade),
                        (int) (c.getBlue() * shade), c.getAlpha()));
                    g2.fill(p);
                    if (drawOutline) {
                        g2.draw(p);
                    }
                }
                
                if (f instanceof CBFace && (this.plot instanceof CategoryPlot3D 
                        || this.plot instanceof XYZPlot)) {
                    Stroke savedStroke = g2.getStroke();
                    CBFace cbf = (CBFace) f;
                    drawGridlines(g2, cbf, pts);
                    g2.setStroke(savedStroke);
                }
            } 
        }
   
        // handle labels on pie plots...
        if (this.plot instanceof PiePlot3D) {
            drawPieLabels(g2, w, h, depth);
        }

        // handle axis labelling on non-pie plots...
        if (this.plot instanceof XYZPlot || this.plot instanceof 
                CategoryPlot3D) {
            drawAxes(g2, chartBox, pts);
        }    

        g2.setTransform(saved);
        
        // generate and draw the legend...
        if (this.legendBuilder != null) {
            TableElement legend = this.legendBuilder.createLegend(this.plot);
            if (true) { // eval
                GridElement legend2 = new GridElement();
                legend2.setElement(legend, "R1", "C1");
                TextElement te = new TextElement("Orson Charts (c) 2013, by Object Refinery Limited", new Font("Dialog", Font.PLAIN, 10));
                te.setHorizontalAligment(HAlign.RIGHT);
                legend2.setElement(te, "R2", "C1");
                legend = legend2;         
            }
            Dimension2D legendSize = legend.preferredSize(g2, bounds);
            Rectangle2D legendArea = calculateDrawArea(legendSize, 
                    this.legendAnchor, bounds);
            legend.draw(g2, legendArea);
        }

        // draw the title...
        if (this.title != null) {
            Dimension2D titleSize = this.title.preferredSize(g2, bounds);
            Rectangle2D titleArea = calculateDrawArea(titleSize, 
                    this.titleAnchor, bounds);
            this.title.draw(g2, titleArea);
        }

    }
    
    /**
     * An implementation method that fetches x-axis tick data from the plot,
     * assuming it is either a {@link CategoryPlot3D} or an {@link XYZPlot}.
     * On a category plot, the x-axis is the column axis (and the tickUnit is
     * ignored).
     * 
     * @param plot  the plot.
     * @param tickUnit  the tick unit.
     * 
     * @return A list of tick data instances representing the tick marks and
     *     values along the x-axis.
     */
    private List<TickData> fetchXTickData(Plot3D plot, double tickUnit) {
        if (plot instanceof CategoryPlot3D) {
            CategoryPlot3D cp = (CategoryPlot3D) plot;
            return cp.getColumnAxis().generateTickData();
        }
        if (plot instanceof XYZPlot) {
            XYZPlot xp = (XYZPlot) plot;
            return xp.getXAxis().generateTickData(tickUnit);
        }
        return new ArrayList<TickData>(); 
    }

    /**
     * An implementation method that fetches y-axis tick data from the plot,
     * assuming it is either a {@link CategoryPlot3D} or an {@link XYZPlot}.
     * On a category plot, the y-axis is the value axis.
     * 
     * @param plot  the plot.
     * @param tickUnit  the tick unit.
     * 
     * @return A list of tick data instances representing the tick marks and
     *     values along the y-axis.
     */
    private List<TickData> fetchYTickData(Plot3D plot, double tickUnit) {
        if (plot instanceof CategoryPlot3D) {
            CategoryPlot3D cp = (CategoryPlot3D) plot;
            return cp.getValueAxis().generateTickData(tickUnit);
        }
        if (plot instanceof XYZPlot) {
            XYZPlot xp = (XYZPlot) plot;
            return xp.getYAxis().generateTickData(tickUnit);
        }
        return new ArrayList<TickData>(); 
    }

    /**
     * An implementation method that fetches z-axis tick data from the plot,
     * assuming it is either a {@link CategoryPlot3D} or an {@link XYZPlot}.
     * On a category plot, the z-axis is the row axis (and the tickUnit is
     * ignored).
     * 
     * @param plot  the plot.
     * @param tickUnit  the tick unit.
     * 
     * @return A list of tick data instances representing the tick marks and
     *     values along the y-axis.
     */
    private List<TickData> fetchZTickData(Plot3D plot, double tickUnit) {
        if (plot instanceof CategoryPlot3D) {
            CategoryPlot3D cp = (CategoryPlot3D) plot;
            return cp.getRowAxis().generateTickData();
        }
        if (plot instanceof XYZPlot) {
            XYZPlot xp = (XYZPlot) plot;
            return xp.getZAxis().generateTickData(tickUnit);
        }
        return new ArrayList<TickData>(); 
    }
    
    /**
     * Draw the gridlines for one chart box face.
     * 
     * @param g2  the graphics target.
     * @param face  the face.
     * @param pts  the projection points.
     */
    private void drawGridlines(Graphics2D g2, CBFace face, Point2D[] pts) {
        if (isGridlinesVisibleForX(this.plot)) {
            List<TickData> xA = face.getXTicksA();
            List<TickData> xB = face.getXTicksB();
            for (int i = 0; i < xA.size(); i++) {
                Line2D line = new Line2D.Double(
                        pts[face.getOffset() + xA.get(i).getVertexIndex()], 
                        pts[face.getOffset() + xB.get(i).getVertexIndex()]);
                g2.setPaint(fetchGridlinePaintX(this.plot));
                g2.setStroke(fetchGridlineStrokeX(this.plot));
                g2.draw(line);
            }
        }
                    
        if (isGridlinesVisibleForY(this.plot)) {
            List<TickData> yA = face.getYTicksA();
            List<TickData> yB = face.getYTicksB();
            for (int i = 0; i < yA.size(); i++) {
                Line2D line = new Line2D.Double(
                        pts[face.getOffset() + yA.get(i).getVertexIndex()], 
                        pts[face.getOffset() + yB.get(i).getVertexIndex()]);
                g2.setPaint(fetchGridlinePaintY(this.plot));
                g2.setStroke(fetchGridlineStrokeY(this.plot));
                g2.draw(line);
            }
        }
                    
        if (isGridlinesVisibleForZ(this.plot)) {
            List<TickData> zA = face.getZTicksA();
            List<TickData> zB = face.getZTicksB();
            for (int i = 0; i < zA.size(); i++) {
                Line2D line = new Line2D.Double(
                        pts[face.getOffset() + zA.get(i).getVertexIndex()], 
                        pts[face.getOffset() + zB.get(i).getVertexIndex()]);
                g2.setPaint(fetchGridlinePaintZ(this.plot));
                g2.setStroke(fetchGridlineStrokeZ(this.plot));
                g2.draw(line);
            }
        }
    }

    /**
     * Returns <code>true</code> if gridlines are visible for the x-axis
     * (column axis in the case of a {@link CategoryPlot3D}) and 
     * <code>false</code> otherwise.  For pie charts, this method will always
     * return <code>false>.
     * 
     * @param plot  the plot.
     * 
     * @return A boolean. 
     */
    private boolean isGridlinesVisibleForX(Plot3D plot) {
        if (plot instanceof CategoryPlot3D) {
            CategoryPlot3D cp = (CategoryPlot3D) plot;
            return cp.getGridlinesVisibleForColumns();
        }
        if (plot instanceof XYZPlot) {
            XYZPlot xp = (XYZPlot) plot;
            return xp.isGridlinesVisibleX();
        }
        return false;
    }
    
    /**
     * Returns <code>true</code> if gridlines are visible for the y-axis
     * (value axis in the case of a {@link CategoryPlot3D}) and 
     * <code>false</code> otherwise.
     * 
     * @param plot  the plot.
     * 
     * @return A boolean. 
     */
    private boolean isGridlinesVisibleForY(Plot3D plot) {
        if (plot instanceof CategoryPlot3D) {
            CategoryPlot3D cp = (CategoryPlot3D) plot;
            return cp.getGridlinesVisibleForValues();
        }
        if (plot instanceof XYZPlot) {
            XYZPlot xp = (XYZPlot) plot;
            return xp.isGridlinesVisibleY();
        }
        return false;
    }
    
    /**
     * Returns <code>true</code> if gridlines are visible for the z-axis
     * (row axis in the case of a {@link CategoryPlot3D}) and 
     * <code>false</code> otherwise.
     * 
     * @param plot  the plot.
     * 
     * @return A boolean. 
     */
    private boolean isGridlinesVisibleForZ(Plot3D plot) {
        if (plot instanceof CategoryPlot3D) {
            CategoryPlot3D cp = (CategoryPlot3D) plot;
            return cp.getGridlinesVisibleForRows();
        }
        if (plot instanceof XYZPlot) {
            XYZPlot xp = (XYZPlot) plot;
            return xp.isGridlinesVisibleZ();
        }
        return false;
    }
    
    /**
     * Returns the paint used to draw gridlines on the x-axis (or column axis
     * in the case of {@link CategoryPlot3D}).
     * 
     * @param plot  the plot.
     * 
     * @return The paint. 
     */
    private Paint fetchGridlinePaintX(Plot3D plot) {
        if (plot instanceof CategoryPlot3D) {
            CategoryPlot3D cp = (CategoryPlot3D) plot;
            return cp.getGridlinePaintForColumns();
        }
        if (plot instanceof XYZPlot) {
            XYZPlot xp = (XYZPlot) plot;
            return xp.getGridlinePaintX();
        }
        return null;
    }
    
    /**
     * Returns the paint used to draw gridlines on the y-axis (or value axis
     * in the case of {@link CategoryPlot3D}).
     * 
     * @param plot  the plot.
     * 
     * @return The paint. 
     */
    private Paint fetchGridlinePaintY(Plot3D plot) {
        if (plot instanceof CategoryPlot3D) {
            CategoryPlot3D cp = (CategoryPlot3D) plot;
            return cp.getGridlinePaintForValues();
        }
        if (plot instanceof XYZPlot) {
            XYZPlot xp = (XYZPlot) plot;
            return xp.getGridlinePaintY();
        }
        return null;
    }
    
    /**
     * Returns the paint used to draw gridlines on the z-axis (or row axis
     * in the case of {@link CategoryPlot3D}).
     * 
     * @param plot  the plot.
     * 
     * @return The paint. 
     */
    private Paint fetchGridlinePaintZ(Plot3D plot) {
        if (plot instanceof CategoryPlot3D) {
            CategoryPlot3D cp = (CategoryPlot3D) plot;
            return cp.getGridlinePaintForRows();
        }
        if (plot instanceof XYZPlot) {
            XYZPlot xp = (XYZPlot) plot;
            return xp.getGridlinePaintZ();
        }
        return null;
    }
    
    /**
     * Returns the stroke used to draw gridlines on the x-axis (or column axis
     * in the case of {@link CategoryPlot3D}).
     * 
     * @param plot  the plot.
     * 
     * @return The stroke. 
     */
    private Stroke fetchGridlineStrokeX(Plot3D plot) {
        if (plot instanceof CategoryPlot3D) {
            CategoryPlot3D cp = (CategoryPlot3D) plot;
            return cp.getGridlineStrokeForColumns();
        }
        if (plot instanceof XYZPlot) {
            XYZPlot xp = (XYZPlot) plot;
            return xp.getGridlineStrokeX();
        }
        return null;
    }
    
    /**
     * Returns the stroke used to draw gridlines on the y-axis (or value axis
     * in the case of {@link CategoryPlot3D}).
     * 
     * @param plot  the plot.
     * 
     * @return The stroke. 
     */
    private Stroke fetchGridlineStrokeY(Plot3D plot) {
        if (plot instanceof CategoryPlot3D) {
            CategoryPlot3D cp = (CategoryPlot3D) plot;
            return cp.getGridlineStrokeForValues();
        }
        if (plot instanceof XYZPlot) {
            XYZPlot xp = (XYZPlot) plot;
            return xp.getGridlineStrokeY();
        }
        return null;
    }
    
    /**
     * Returns the stroke used to draw gridlines on the z-axis (or row axis
     * in the case of {@link CategoryPlot3D}).
     * 
     * @param plot  the plot.
     * 
     * @return The stroke. 
     */
    private Stroke fetchGridlineStrokeZ(Plot3D plot) {
        if (plot instanceof CategoryPlot3D) {
            CategoryPlot3D cp = (CategoryPlot3D) plot;
            return cp.getGridlineStrokeForRows();
        }
        if (plot instanceof XYZPlot) {
            XYZPlot xp = (XYZPlot) plot;
            return xp.getGridlineStrokeZ();
        }
        return null;
    }
    
    /**
     * Draws the pie labels for a {@link PiePlot3D} in 2D-space by creating a 
     * temporary world with vertices at anchor points for the labels, then 
     * projecting the points to 2D-space.
     * 
     * @param g2  the graphics target.
     * @param w  the width.
     * @param h  the height.
     * @param depth  the depth.
     */
    private void drawPieLabels(Graphics2D g2, double w, double h, 
            double depth) {
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
            if (Utils2D.area2(ppts[f.getVertexIndex(0)], 
                    ppts[f.getVertexIndex(1)], 
                    ppts[f.getVertexIndex(2)]) > 0) {
                Comparable key = p.getDataset().getKey(i / 2);
                g2.setColor(p.getSectionLabelColorSource().getColor(key));
                g2.setFont(p.getSectionLabelFontSource().getFont(key));
                Point2D pt = Utils2D.centerPoint(ppts[f.getVertexIndex(0)], 
                        ppts[f.getVertexIndex(1)], ppts[f.getVertexIndex(2)],
                        ppts[f.getVertexIndex(3)]);
                TextUtils.drawAlignedString(key.toString(), g2, 
                        (float) pt.getX(), (float) pt.getY(), 
                        TextAnchor.CENTER);
            }
        }
    }
    
    /**
     * Determines appropriate tick units for the axes in the chart.
     * 
     * @param g2  the graphics target.
     * @param w  the width.
     * @param h  the height.
     * @param depth  the depth.
     * 
     * @return The tick sizes. 
     */
    private double[] findAxisTickUnits(Graphics2D g2, double w, double h, 
            double depth) {
        World axisOverlay = new World();
        ChartBox3D chartBox = new ChartBox3D(w, h, depth, -w / 2.0, -h / 2.0, 
                -depth / 2.0, Color.WHITE);
        axisOverlay.add(chartBox.getObject3D());
        Point2D[] axisPts2D = axisOverlay.calculateProjectedPoints(
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
        boolean a = chartBox.faceA().isFrontFacing(axisPts2D);
        boolean b = chartBox.faceB().isFrontFacing(axisPts2D);
        boolean c = chartBox.faceC().isFrontFacing(axisPts2D);
        boolean d = chartBox.faceD().isFrontFacing(axisPts2D);
        boolean e = chartBox.faceE().isFrontFacing(axisPts2D);
        boolean f = chartBox.faceF().isFrontFacing(axisPts2D);

        double xtick = 0, ytick = 0, ztick = 0;
        Axis3D xAxis = null;
        ValueAxis3D yAxis = null;
        Axis3D zAxis = null;
        if (this.plot instanceof XYZPlot) {
            XYZPlot pp = (XYZPlot) this.plot;
            xAxis = pp.getXAxis();
            yAxis = pp.getYAxis();
            zAxis = pp.getZAxis();
        } else if (this.plot instanceof CategoryPlot3D) {
            CategoryPlot3D pp = (CategoryPlot3D) this.plot;
            xAxis = pp.getColumnAxis();
            yAxis = pp.getValueAxis();
            zAxis = pp.getRowAxis();
        }
            
        if (xAxis != null && yAxis != null && zAxis != null) {
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
                if (xAxis instanceof ValueAxis3D) {
                    xtick = ((ValueAxis3D) xAxis).selectTick(g2, v0, v1, v7);
                }
            }
            if (count(b, c) == 1 && longest(bc, ab, cd, da)) {
                if (xAxis instanceof ValueAxis3D) {
                    xtick = ((ValueAxis3D) xAxis).selectTick(g2, v3, v2, v6);
                }
            }
            if (count(c, d) == 1 && longest(cd, ab, bc, da)) {
                if (xAxis instanceof ValueAxis3D) {
                    xtick = ((ValueAxis3D) xAxis).selectTick(g2, v4, v7, v1);
                }
            }
            if (count(d, a) == 1 && longest(da, ab, bc, cd)) {
                if (xAxis instanceof ValueAxis3D) {
                    xtick = ((ValueAxis3D) xAxis).selectTick(g2, v5, v6, v3);
                }
            }

            if (count(b, e) == 1 && longest(be, bf, df, de)) {
                ytick = ((ValueAxis3D) yAxis).selectTick(g2, v0, v3, v7);
            }
            if (count(b, f) == 1 && longest(bf, be, df, de)) {
                ytick = ((ValueAxis3D) yAxis).selectTick(g2, v1, v2, v4);
            }
            if (count(d, f) == 1 && longest(df, be, bf, de)) {
                ytick = ((ValueAxis3D) yAxis).selectTick(g2, v6, v7, v0);
            }
            if (count(d, e) == 1 && longest(de, be, bf, df)) {
                ytick = ((ValueAxis3D) yAxis).selectTick(g2, v5, v4, v1);
            }

            if (count(a, e) == 1 && longest(ae, af, cf, ce)) {
                if (zAxis instanceof ValueAxis3D) {
                    ztick = ((ValueAxis3D) zAxis).selectTick(g2, v0, v5, v2);
                }
            }
            if (count(a, f) == 1 && longest(af, ae, cf, ce)) {
                if (zAxis instanceof ValueAxis3D) {
                    ztick = ((ValueAxis3D) zAxis).selectTick(g2, v1, v6, v3);
                }
            }
            if (count(c, f) == 1 && longest(cf, ae, af, ce)) {
                if (zAxis instanceof ValueAxis3D) {
                    ztick = ((ValueAxis3D) zAxis).selectTick(g2, v2, v7, v5);
                }
            }
            if (count(c, e) == 1 && longest(ce, ae, af, cf)) {
                if (zAxis instanceof ValueAxis3D) {
                    ztick = ((ValueAxis3D) zAxis).selectTick(g2, v3, v4, v6);
                }
            }
        }
        return new double[] { xtick, ytick, ztick };
    }
    
    private void populateAnchorPoints(List<TickData> tickData, Point2D[] pts) {
        for (TickData t : tickData) {
            t.setAnchorPt(pts[t.getVertexIndex()]);
        }    
    }
    
    private void drawAxes(Graphics2D g2, ChartBox3D chartBox, Point2D[] pts) {

        // vertices
        Point2D v0 = pts[0];
        Point2D v1 = pts[1];
        Point2D v2 = pts[2];
        Point2D v3 = pts[3];
        Point2D v4 = pts[4];
        Point2D v5 = pts[5];
        Point2D v6 = pts[6];
        Point2D v7 = pts[7];

        // faces
        boolean a = chartBox.faceA().isFrontFacing(pts);
        boolean b = chartBox.faceB().isFrontFacing(pts);
        boolean c = chartBox.faceC().isFrontFacing(pts);
        boolean d = chartBox.faceD().isFrontFacing(pts);
        boolean e = chartBox.faceE().isFrontFacing(pts);
        boolean f = chartBox.faceF().isFrontFacing(pts);

        Axis3D xAxis = null, yAxis = null, zAxis = null;
        if (this.plot instanceof XYZPlot) {
            XYZPlot pp = (XYZPlot) this.plot;
            xAxis = pp.getXAxis();
            yAxis = pp.getYAxis();
            zAxis = pp.getZAxis();
        } else if (this.plot instanceof CategoryPlot3D) {
            CategoryPlot3D pp = (CategoryPlot3D) this.plot;
            xAxis = pp.getColumnAxis();
            yAxis = pp.getValueAxis();
            zAxis = pp.getRowAxis();
        }
            
        if (xAxis != null && yAxis != null && zAxis != null) {
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

            List<TickData> ticks; 
            if (count(a, b) == 1 && longest(ab, bc, cd, da)) {
                ticks = chartBox.faceA().getXTicksA();
                populateAnchorPoints(ticks, pts);
                xAxis.draw(g2, v0, v1, v7, true, ticks);
            }
            if (count(b, c) == 1 && longest(bc, ab, cd, da)) {
                ticks = chartBox.faceB().getXTicksB();
                populateAnchorPoints(ticks, pts);
                xAxis.draw(g2, v3, v2, v6, true, ticks);
            }
            if (count(c, d) == 1 && longest(cd, ab, bc, da)) {
                ticks = chartBox.faceC().getXTicksB();
                populateAnchorPoints(ticks, pts);
                xAxis.draw(g2, v4, v7, v1, true, ticks);
            }
            if (count(d, a) == 1 && longest(da, ab, bc, cd)) {
                ticks = chartBox.faceA().getXTicksB();
                populateAnchorPoints(ticks, pts);
                xAxis.draw(g2, v5, v6, v3, true, ticks);
            }

            if (count(b, e) == 1 && longest(be, bf, df, de)) {
                ticks = chartBox.faceB().getYTicksA();
                populateAnchorPoints(ticks, pts);
                yAxis.draw(g2, v0, v3, v7, true, ticks);
            }
            if (count(b, f) == 1 && longest(bf, be, df, de)) {
                ticks = chartBox.faceB().getYTicksB();
                populateAnchorPoints(ticks, pts);
                yAxis.draw(g2, v1, v2, v4, true, ticks);
            }
            if (count(d, f) == 1 && longest(df, be, bf, de)) {
                ticks = chartBox.faceD().getYTicksA();
                populateAnchorPoints(ticks, pts);
                yAxis.draw(g2, v6, v7, v0, true, ticks);
            }
            if (count(d, e) == 1 && longest(de, be, bf, df)) {
                ticks = chartBox.faceD().getYTicksB();
                populateAnchorPoints(ticks, pts);
                yAxis.draw(g2, v5, v4, v1, true, ticks);
            }

            if (count(a, e) == 1 && longest(ae, af, cf, ce)) {
                ticks = chartBox.faceA().getZTicksA();
                populateAnchorPoints(ticks, pts);
                zAxis.draw(g2, v0, v5, v2, true, ticks);
            }
            if (count(a, f) == 1 && longest(af, ae, cf, ce)) {
                ticks = chartBox.faceA().getZTicksB();
                populateAnchorPoints(ticks, pts);
                zAxis.draw(g2, v1, v6, v3, true, ticks);
            }
            if (count(c, f) == 1 && longest(cf, ae, af, ce)) {
                ticks = chartBox.faceC().getZTicksB();
                populateAnchorPoints(ticks, pts);
                zAxis.draw(g2, v2, v7, v5, true, ticks);
            }
            if (count(c, e) == 1 && longest(ce, ae, af, cf)) {
                ticks = chartBox.faceC().getZTicksA();
                populateAnchorPoints(ticks, pts);
                zAxis.draw(g2, v3, v4, v6, true, ticks);
            }
        }
    }

    /**
     * Tests this chart for equality with an arbitrary object.
     * 
     * @param obj  the object (<code>null</code> not permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Chart3D)) {
            return false;
        }
        Chart3D that = (Chart3D) obj;
        if (!ObjectUtils.equals(this.background, that.background)) {
            return false;
        }
        if (!ObjectUtils.equals(this.title, that.title)) {
            return false;
        }
        if (!this.titleAnchor.equals(that.titleAnchor)) {
            return false;
        }
        if (!ObjectUtils.equalsPaint(this.chartBoxColor, that.chartBoxColor)) {
            return false;
        }
        if (!ObjectUtils.equals(this.legendBuilder, that.legendBuilder)) {
            return false;
        }
        return true;
    }

    /**
     * A utility method that calculates a drawing area based on a bounding area
     * and an anchor.
     * 
     * @param dim  the dimensions for the drawing area (<code>null</code> not 
     *     permitted).
     * @param anchor  the anchor (<code>null</code> not permitted).
     * @param bounds  the bounds (<code>null</code> not permitted).
     * 
     * @return A drawing area. 
     */
    private Rectangle2D calculateDrawArea(Dimension2D dim, Anchor2D anchor, 
            Rectangle2D bounds) {
        ArgChecks.nullNotPermitted(dim, "dim");
        ArgChecks.nullNotPermitted(anchor, "anchor");
        ArgChecks.nullNotPermitted(bounds, "bounds");
        double x, y;
        double w = Math.min(dim.getWidth(), bounds.getWidth());
        double h = Math.min(dim.getHeight(), bounds.getHeight());
        if (anchor.getRefPt().equals(RefPt2D.CENTER)) {
            x = bounds.getCenterX() - w / 2.0;
            y = bounds.getCenterY() - h / 2.0;
        } else if (anchor.getRefPt().equals(RefPt2D.CENTER_LEFT)) {
            x = bounds.getX() + anchor.getOffset().getDX();
            y = bounds.getCenterY() - h / 2.0;
        } else if (anchor.getRefPt().equals(RefPt2D.CENTER_RIGHT)) {
            x = bounds.getMaxX() - anchor.getOffset().getDX() - dim.getWidth();
            y = bounds.getCenterY() - h / 2.0;
        } else if (anchor.getRefPt().equals(RefPt2D.TOP_CENTER)) {
            x = bounds.getCenterX() - w / 2.0;
            y = bounds.getY() + anchor.getOffset().getDY();
        } else if (anchor.getRefPt().equals(RefPt2D.TOP_LEFT)) {
            x = bounds.getX() + anchor.getOffset().getDX();
            y = bounds.getY() + anchor.getOffset().getDY();
        } else if (anchor.getRefPt().equals(RefPt2D.TOP_RIGHT)) {
            x = bounds.getMaxX() - anchor.getOffset().getDX() - dim.getWidth();
            y = bounds.getY() + anchor.getOffset().getDY();
        } else if (anchor.getRefPt().equals(RefPt2D.BOTTOM_CENTER)) {
            x = bounds.getCenterX() - w / 2.0;
            y = bounds.getMaxY() - anchor.getOffset().getDY() - dim.getHeight();
        } else if (anchor.getRefPt().equals(RefPt2D.BOTTOM_RIGHT)) {
            x = bounds.getMaxX() - anchor.getOffset().getDX() - dim.getWidth();
            y = bounds.getMaxY() - anchor.getOffset().getDY() - dim.getHeight();
        } else if (anchor.getRefPt().equals(RefPt2D.BOTTOM_LEFT)) {
            x = bounds.getX() + anchor.getOffset().getDX();
            y = bounds.getMaxY() - anchor.getOffset().getDY() - dim.getHeight();
        } else {
            x = 0.0;
            y = 0.0;
        }
        return new Rectangle2D.Double(x, y, w, h);
    }

    /**
     * Returns <code>true</code> if x is the longest of the four lengths,
     * and <code>false</code> otherwise.
     * 
     * @param x  the x-length.
     * @param a  length 1.
     * @param b  length 2.
     * @param c  length 3.
     * 
     * @return A boolean. 
     */
    private boolean longest(double x, double a, double b, double c) {
        return x >= a && x >= b && x >= c;
    }

    /**
     * Returns the number (0, 1 or 2) arguments that have the value 
     * <code>true</code>.  We use this to examine the visibility of
     * adjacent walls of the chart box...where only one wall is visible, there
     * is an opportunity to display the axis along that edge.
     * 
     * @param a  boolean argument 1.
     * @param b  boolean argument 2.
     * 
     * @return 0, 1, or 2.
     */
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
    
    /**
     * Receives notification of a plot change event, refreshes the 3D model 
     * (world) and passes the event on, wrapped in a {@link Chart3DChangeEvent},
     * to all registered listeners.
     * 
     * @param event  the plot change event. 
     */
    @Override
    public void plotChanged(Plot3DChangeEvent event) {
        notifyListeners(new Chart3DChangeEvent(event, this));
    }

    /**
     * Registers a listener to receive notification of changes to the chart.
     * 
     * @param listener  the listener. 
     */
    public void addChangeListener(Chart3DChangeListener listener) {
        this.listenerList.add(Chart3DChangeListener.class, listener);   
    }
  
    /**
     * Deregisters a listener so that it no longer receives notification of
     * changes to the chart.
     * 
     * @param listener  the listener. 
     */
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
