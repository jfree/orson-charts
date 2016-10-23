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

package com.orsoncharts;

import java.awt.BasicStroke;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.EventListenerList;

import com.orsoncharts.ChartBox3D.ChartBoxFace;
import com.orsoncharts.axis.Axis3D;
import com.orsoncharts.axis.TickData;
import com.orsoncharts.axis.ValueAxis3D;
import com.orsoncharts.data.ItemKey;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.DoubleSidedFace;
import com.orsoncharts.graphics3d.Drawable3D;
import com.orsoncharts.graphics3d.Face;
import com.orsoncharts.graphics3d.FaceSorter;
import com.orsoncharts.graphics3d.LabelFace;
import com.orsoncharts.graphics3d.Object3D;
import com.orsoncharts.graphics3d.Point3D;
import com.orsoncharts.graphics3d.Utils2D;
import com.orsoncharts.graphics3d.ViewPoint3D;
import com.orsoncharts.graphics3d.World;
import com.orsoncharts.legend.LegendAnchor;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.plot.PiePlot3D;
import com.orsoncharts.plot.Plot3DChangeEvent;
import com.orsoncharts.plot.Plot3DChangeListener;
import com.orsoncharts.plot.Plot3D;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.graphics3d.Offset2D;
import com.orsoncharts.graphics3d.RenderingInfo;
import com.orsoncharts.graphics3d.StandardFaceSorter;
import com.orsoncharts.graphics3d.RenderedElement;
import com.orsoncharts.interaction.InteractiveElementType;
import com.orsoncharts.legend.LegendBuilder;
import com.orsoncharts.legend.StandardLegendBuilder;
import com.orsoncharts.marker.Marker;
import com.orsoncharts.marker.MarkerData;
import com.orsoncharts.style.ChartStyle;
import com.orsoncharts.style.ChartStyleChangeEvent;
import com.orsoncharts.style.ChartStyleChangeListener;
import com.orsoncharts.style.ChartStyler;
import com.orsoncharts.table.GradientRectanglePainter;
import com.orsoncharts.table.GridElement;
import com.orsoncharts.table.HAlign;
import com.orsoncharts.table.TableElement;
import com.orsoncharts.table.TextElement;
import com.orsoncharts.table.StandardRectanglePainter;
import com.orsoncharts.table.RectanglePainter;
import com.orsoncharts.util.Anchor2D;
import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.util.ObjectUtils;
import com.orsoncharts.util.Orientation;
import com.orsoncharts.util.RefPt2D;
import com.orsoncharts.util.TextUtils;
import com.orsoncharts.util.TextAnchor;

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
 * {@code Graphics2D} target (such as the screen, image, SVG file or 
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
 * @see Chart3DPanel
 */
@SuppressWarnings("serial")
public class Chart3D implements Drawable3D, ChartElement, 
        Plot3DChangeListener, ChartStyleChangeListener, Serializable {
    
    /** 
     * The default projection distance. 
     * 
     * @since 1.2
     */
    public static final double DEFAULT_PROJ_DIST = 1500.0;
    
    /**
     * The key for a property that stores the interactive element type.
     * 
     * @since 1.3
     */
    public static final String INTERACTIVE_ELEMENT_TYPE 
            = "interactive_element_type";
    
    /**
     * The key for a property that stores the series key.  This is used to
     * store the series key on the {@link TableElement} representing a legend 
     * item, and also on a corresponding {@link RenderedElement} after
     * chart rendering (in the {@link RenderingInfo}).
     * 
     * @since 1.3
     */
    public static final String SERIES_KEY = "series_key";
    
    /** The chart id. */
    private String id;
    
    /** A background rectangle painter, if any. */
    private RectanglePainter background;
    
    /** The chart title (can be {@code null}). */
    private TableElement title;
    
    /** The anchor point for the title (never {@code null}). */
    private Anchor2D titleAnchor;
    
    /** A builder for the chart legend (can be {@code null}). */
    private LegendBuilder legendBuilder;
    
    /** The anchor point for the legend (never {@code null}). */
    private Anchor2D legendAnchor;
    
    /** The orientation for the legend (never {@code null}). */
    private Orientation legendOrientation;
    
    /** The plot. */
    private Plot3D plot;
    
    /** The view point. */
    private ViewPoint3D viewPoint;

    /** The projection distance. */
    private double projDist;
    
    /** The chart box color (never {@code null}). */
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
     * of changes (defaults to {@code true}, but sometimes it is useful 
     * to disable this).
     */
    private boolean notify;

    /**
     * Rendering hints that will be used for chart drawing.  This can be
     * empty but it should never be {@code null}.
     * 
     * @since 1.1
     */
    private transient RenderingHints renderingHints;

    /** 
     * The chart style.
     * 
     * @since 1.2
     */
    private ChartStyle style;
    
    /** A 3D model of the world (represents the chart). */
    private transient World world;

    /** An object that sorts faces for rendering (painter's algorithm). */
    private FaceSorter faceSorter;

    /**
     * A flag that controls whether or not element hints are added to the
     * {@code Graphics2D} output.
     */
    private boolean elementHinting;
    
    /**
     * Creates a 3D chart for the specified plot using the default chart
     * style.  Note that a plot instance must be used in one chart instance
     * only.
     * 
     * @param title  the chart title ({@code null} permitted).
     * @param subtitle  the chart subtitle ({@code null} permitted).
     * @param plot  the plot ({@code null} not permitted).
     * 
     * @see Chart3DFactory
     */
    public Chart3D(String title, String subtitle, Plot3D plot) {
        this(title, subtitle, plot, Chart3DFactory.getDefaultChartStyle());
    }
    
    /**
     * Creates a 3D chart for the specified plot using the supplied style.
     * 
     * @param title  the chart title ({@code null} permitted).
     * @param subtitle  the chart subtitle ({@code null} permitted).
     * @param plot  the plot ({@code null} not permitted).
     * @param style  the chart style ({@code null} not permitted).
     * 
     * @since 1.2
     */
    public Chart3D(String title, String subtitle, Plot3D plot, 
            ChartStyle style) {
        ArgChecks.nullNotPermitted(plot, "plot");
        ArgChecks.nullNotPermitted(style, "style");
        plot.setChart(this);
        this.background = new StandardRectanglePainter(Color.WHITE);
        if (title != null) {
            this.title = TitleUtils.createTitle(title, subtitle);
        }
        this.titleAnchor = TitleAnchor.TOP_LEFT;
        this.legendBuilder = new StandardLegendBuilder();
        this.legendAnchor = LegendAnchor.BOTTOM_RIGHT;
        this.legendOrientation = Orientation.HORIZONTAL;
        this.plot = plot;
        this.plot.addChangeListener(this);
        Dimension3D dim = this.plot.getDimensions();
        float distance = (float) dim.getDiagonalLength() * 3.0f;
        this.viewPoint = ViewPoint3D.createAboveViewPoint(distance);
        this.projDist = DEFAULT_PROJ_DIST;
        this.chartBoxColor = new Color(255, 255, 255, 100);
        this.translate2D = new Offset2D();
        this.faceSorter = new StandardFaceSorter();
        this.renderingHints = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        this.renderingHints.put(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        this.elementHinting = false;
        this.notify = true;
        this.listenerList = new EventListenerList();
        this.style = style;
        this.style.addChangeListener(this);
        receive(new ChartStyler(this.style));
    }

    /**
     * Returns the chart id.
     * 
     * @return The chart id (possibly {@code null}).
     * 
     * @since 1.3
     */
    public String getID() {
        return this.id;
    }
    
    /**
     * Sets the chart id.
     * 
     * @param id  the id ({@code null} permitted).
     * 
     * @since 1.3
     */
    public void setID(String id) {
        this.id = id;
    }
    
    /**
     * Returns the background painter (an object that is responsible for filling
     * the background area before charts are rendered).  The default value
     * is an instance of {@link StandardRectanglePainter} that paints the
     * background white.
     * 
     * @return The background painter (possibly {@code null}).
     * 
     * @see #setBackground(com.orsoncharts.table.RectanglePainter) 
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
     * @param background  the background painter ({@code null} permitted).
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
     * @return The chart title (possibly {@code null}). 
     */
    public TableElement getTitle() {
        return this.title;
    }
    
    /**
     * Sets the chart title and sends a {@link Chart3DChangeEvent} to all 
     * registered listeners.  This is a convenience method that constructs
     * the required {@link TableElement} under-the-hood.
     * 
     * @param title  the title ({@code null} permitted). 
     */
    public void setTitle(String title) {
        if (title == null) {
            setTitle((TableElement) null);
        } else {
            setTitle(title, this.style.getTitleFont(), 
                    TitleUtils.DEFAULT_TITLE_COLOR);
        }
    }
    
    /**
     * Sets the chart title and sends a {@link Chart3DChangeEvent} to all 
     * registered listeners.  This is a convenience method that constructs
     * the required {@link TableElement} under-the-hood.
     * 
     * @param title  the title ({@code null} not permitted). 
     * @param font  the font ({@code null} not permitted).
     * @param color  the foreground color ({@code null} not permitted).
     */
    public void setTitle(String title, Font font, Color color) {
        // defer 'title' null check
        ArgChecks.nullNotPermitted(font, "font");
        ArgChecks.nullNotPermitted(color, "color");
        TextElement te = new TextElement(title);
        te.setTag("CHART_TITLE");
        te.setFont(font);
        te.setColor(color);
        setTitle(te);
    }
    
    /**
     * Sets the chart title and sends a {@link Chart3DChangeEvent} to all 
     * registered listeners.  You can set the title to {@code null}, in
     * which case there will be no chart title.
     * 
     * @param title  the title ({@code null} permitted).
     */
    public void setTitle(TableElement title) {
        this.title = title;
        fireChangeEvent();
    }

    /**
     * Returns the title anchor.  This controls the position of the title
     * in the chart area. 
     * 
     * @return The title anchor (never {@code null}).
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
     * @param anchor  the anchor ({@code null} not permitted).
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
     * @return The plot (never {@code null}).
     */
    public Plot3D getPlot() {
        return this.plot;
    }
    
    /**
     * Returns the chart box color (the chart box is the visible, open-sided 
     * box inside which data is plotted for all charts except pie charts). 
     * The default value is {@code Color.WHITE}.
     * 
     * @return The chart box color (never {@code null}). 
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
     * @param color  the color ({@code null} not permitted).
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
     * @return The dimensions (never {@code null}). 
     */
    @Override
    public Dimension3D getDimensions() {
        return this.plot.getDimensions();
    }
    
    /**
     * Returns the view point.
     * 
     * @return The view point (never {@code null}). 
     */
    @Override
    public ViewPoint3D getViewPoint() {
        return this.viewPoint;
    }

    /**
     * Sets the view point.
     * 
     * @param viewPoint  the view point ({@code null} not permitted). 
     */
    @Override
    public void setViewPoint(ViewPoint3D viewPoint) {
        ArgChecks.nullNotPermitted(viewPoint, "viewPoint");
        this.viewPoint = viewPoint;
        fireChangeEvent();
    }    

    /** 
     * Returns the projection distance.  The default value is 
     * {@link #DEFAULT_PROJ_DIST}, higher numbers flatten out the perspective 
     * and reduce distortion in the projected image.
     * 
     * @return The projection distance.
     * 
     * @since 1.2
     */
    @Override
    public double getProjDistance() {
        return this.projDist;
    }
    
    /**
     * Sets the projection distance and sends a change event to all registered
     * listeners.
     * 
     * @param dist  the distance.
     * 
     * @since 1.2
     */
    @Override
    public void setProjDistance(double dist) {
        this.projDist = dist;
        fireChangeEvent();
    }

    /**
     * Sets the offset in 2D-space for the rendering of the chart.  The 
     * default value is {@code (0, 0)} but the user can modify it via
     * ALT-mouse-drag in the chart panel, providing an easy way to get improved
     * chart alignment in the panels (especially prior to export to PNG, SVG or
     * PDF).
     * 
     * @return The offset (never {@code null}). 
     */
    @Override
    public Offset2D getTranslate2D() {
        return this.translate2D;    
    }
    
    /**
     * Sets the offset in 2D-space for the rendering of the chart and sends a
     * change event to all registered listeners.
     * 
     * @param offset  the new offset ({@code null} not permitted).
     */
    @Override
    public void setTranslate2D(Offset2D offset) {
        ArgChecks.nullNotPermitted(offset, "offset");
        this.translate2D = offset;
        fireChangeEvent();
    }
    
    /**
     * Returns the legend builder.  The default value is an instance of
     * {@link StandardLegendBuilder}.  If the legend builder is {@code null}, 
     * no legend will be displayed for the chart.
     * 
     * @return The legend builder (possibly {@code null}).
     * 
     * @see #setLegendBuilder(com.orsoncharts.legend.LegendBuilder) 
     * @see #setLegendAnchor(com.orsoncharts.util.Anchor2D) 
     */
    public LegendBuilder getLegendBuilder() {
        return this.legendBuilder;
    }
    
    /**
     * Sets the legend builder and sends a change event to all registered 
     * listeners.  When the legend builder is {@code null}, no legend 
     * will be displayed on the chart.
     * 
     * @param legendBuilder  the legend builder ({@code null} permitted).
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
     * @return The legend anchor (never {@code null}).
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
     * @param anchor  the anchor ({@code null} not permitted).
     * 
     * @see #getLegendAnchor() 
     */
    public void setLegendAnchor(Anchor2D anchor) {
        ArgChecks.nullNotPermitted(anchor, "anchor");
        this.legendAnchor = anchor;
        fireChangeEvent();
    }
    
    /**
     * Returns the orientation for the legend.
     * 
     * @return The orientation (never {@code null}). 
     * 
     * @since 1.1
     */
    public Orientation getLegendOrientation() {
        return this.legendOrientation;
    }

    /**
     * Sets the legend orientation and sends a {@link Chart3DChangeEvent}
     * to all registered listeners.
     * 
     * @param orientation  the orientation ({@code null} not permitted).
     * 
     * @since 1.1
     */
    public void setLegendOrientation(Orientation orientation) {
        ArgChecks.nullNotPermitted(orientation, "orientation");
        this.legendOrientation = orientation;
        fireChangeEvent();
    }
    
    /**
     * Sets the legend position (both the anchor point and the orientation) and
     * sends a {@link Chart3DChangeEvent} to all registered listeners. 
     * This is a convenience method that calls both the 
     * {@link #setLegendAnchor(com.orsoncharts.util.Anchor2D)} and 
     * {@link #setLegendOrientation(com.orsoncharts.util.Orientation)}
     * methods.
     * 
     * @param anchor  the anchor ({@code null} not permitted).
     * @param orientation  the orientation ({@code null} not permitted).
     * 
     * @since 1.1
     */
    public void setLegendPosition(Anchor2D anchor, Orientation orientation) {
        setNotify(false);
        setLegendAnchor(anchor);
        setLegendOrientation(orientation);
        setNotify(true);
    }
    
    /**
     * Returns the collection of rendering hints for the chart.
     *
     * @return The rendering hints for the chart (never {@code null}).
     *
     * @see #setRenderingHints(RenderingHints)
     * 
     * @since 1.1
     */
    public RenderingHints getRenderingHints() {
        return this.renderingHints;
    }

    /**
     * Sets the rendering hints for the chart.  These will be added (using the
     * {@code Graphics2D.addRenderingHints()} method) near the start of 
     * the chart rendering.  Note that calling this method will replace all
     * existing hints assigned to the chart.  If you simply wish to add an
     * additional hint, you can use {@code getRenderingHints().put(key, value)}.
     *
     * @param hints  the rendering hints ({@code null} not permitted).
     *
     * @see #getRenderingHints()
     * 
     * @since 1.1
     */
    public void setRenderingHints(RenderingHints hints) {
        ArgChecks.nullNotPermitted(hints, "hints");
        this.renderingHints = hints;
        fireChangeEvent();
    }

    /**
     * Returns a flag that indicates whether or not anti-aliasing is used when
     * the chart is drawn.
     *
     * @return The flag.
     *
     * @see #setAntiAlias(boolean)
     * @since 1.1
     */
    public boolean getAntiAlias() {
        Object val = this.renderingHints.get(RenderingHints.KEY_ANTIALIASING);
        return RenderingHints.VALUE_ANTIALIAS_ON.equals(val);
    }

    /**
     * Sets a flag that indicates whether or not anti-aliasing is used when the
     * chart is drawn.
     * <P>
     * Anti-aliasing usually improves the appearance of charts, but is slower.
     *
     * @param flag  the new value of the flag.
     *
     * @see #getAntiAlias()
     * @since 1.1
     */
    public void setAntiAlias(boolean flag) {
        if (flag) {
            this.renderingHints.put(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        } else {
            this.renderingHints.put(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_OFF);
        }
        fireChangeEvent();
    }
 
    /**
     * Returns the flag that controls whether or not element hints will be
     * added to the {@code Graphics2D} output when the chart is rendered.
     * The default value is {@code false}.
     * 
     * @return A boolean.
     * 
     * @since 1.3
     */
    public boolean getElementHinting() {
        return this.elementHinting;
    }
    
    /**
     * Sets the flag that controls whether or not element hints will be
     * added to the {@code Graphics2D} output when the chart is rendered
     * and sends a change event to all registered listeners.
     * 
     * @param hinting  the new flag value.
     * 
     * @since 1.3
     */
    public void setElementHinting(boolean hinting) {
        this.elementHinting = hinting;
        fireChangeEvent();
    }
    
    /**
     * Returns the chart style.
     * 
     * @return The chart style (never {@code null}).
     * 
     * @since 1.2
     */
    public ChartStyle getStyle() {
        return this.style;
    }
    
    /**
     * Sets (and applies) the specified chart style.
     * 
     * @param style  the chart style ({@code null} not permitted).
     * 
     * @since 1.2
     */
    public void setStyle(ChartStyle style) {
        ArgChecks.nullNotPermitted(style, "style");
        this.style.removeChangeListener(this);
        this.style = style;
        this.style.addChangeListener(this);
        setNotify(false);
        receive(new ChartStyler(this.style));
        setNotify(true);
    }

    /**
     * Creates a world containing the chart and the supplied chart box.
     * 
     * @param chartBox  the chart box ({@code null} permitted).
     */
    private World createWorld(ChartBox3D chartBox) {
        World result = new World();      
        Dimension3D dim = this.plot.getDimensions();
        double w = dim.getWidth();
        double h = dim.getHeight();
        double d = dim.getDepth();
        if (chartBox != null) {
            result.add("chartbox", chartBox.createObject3D());
        }
        this.plot.compose(result, -w / 2, -h / 2, -d / 2);
        return result;
    }
    
    /**
     * Draws the chart to the specified output target.
     * 
     * @param g2  the output target ({@code null} not permitted).
     * 
     * @return Information about the items rendered.
     */
    @Override
    public RenderingInfo draw(Graphics2D g2, Rectangle2D bounds) {
        beginElement(g2, this.id, "ORSON_CHART_TOP_LEVEL");
        Shape savedClip = g2.getClip();
        g2.clip(bounds);
        g2.addRenderingHints(this.renderingHints);
        g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, 
                BasicStroke.JOIN_ROUND, 1f));
        Dimension3D dim3D = this.plot.getDimensions();
        double w = dim3D.getWidth();
        double h = dim3D.getHeight();
        double depth = dim3D.getDepth();
        ChartBox3D chartBox = null;
        if (this.plot instanceof XYZPlot 
                || this.plot instanceof CategoryPlot3D) {
            double[] tickUnits = findAxisTickUnits(g2, w, h, depth);
            chartBox = new ChartBox3D(w, h, depth, -w / 2, -h / 2, -depth / 2, 
                    this.chartBoxColor);
            chartBox.setXTicks(fetchXTickData(this.plot, tickUnits[0]));
            chartBox.setYTicks(fetchYTickData(this.plot, tickUnits[1]));
            chartBox.setZTicks(fetchZTickData(this.plot, tickUnits[2]));
            chartBox.setXMarkers(fetchXMarkerData(this.plot));
            chartBox.setYMarkers(fetchYMarkerData(this.plot));
            chartBox.setZMarkers(fetchZMarkerData(this.plot));
        }
        if (this.world == null) {
            this.world = createWorld(chartBox);
        } else if (chartBox != null) {
            this.world.clear("chartbox");
            this.world.add("chartbox", chartBox.createObject3D());
        }
        if (this.background != null) {
            this.background.fill(g2, bounds);
        }
        AffineTransform saved = g2.getTransform();
        double dx = bounds.getX() + bounds.getWidth() / 2.0 
                + this.translate2D.getDX();
        double dy = bounds.getY() + bounds.getHeight() / 2.0 
                + this.translate2D.getDY();
        g2.translate(dx, dy);
        Point3D[] eyePts = this.world.calculateEyeCoordinates(this.viewPoint);
        Point2D[] pts = this.world.calculateProjectedPoints(this.viewPoint, 
                this.projDist);
        
        // sort faces by z-order
        List<Face> facesInPaintOrder = new ArrayList<Face>(world.getFaces());
        facesInPaintOrder = this.faceSorter.sort(facesInPaintOrder, eyePts);
        Line2D line = null;
        Stroke stroke = new BasicStroke(1.0f);
        for (Face f : facesInPaintOrder) {
            // check for the special case where the face is just a line
            if (f.getVertexCount() == 2) {
                g2.setPaint(f.getColor());
                if (line == null) {
                    line = new Line2D.Float();
                }
                int v0 = f.getVertexIndex(0);
                int v1 = f.getVertexIndex(1);
                line.setLine(pts[v0].getX(), pts[v0].getY(), pts[v1].getX(), 
                        pts[v1].getY());
                g2.setStroke(stroke);
                g2.draw(line);
                continue;
            }
            boolean drawOutline = f.getOutline();
            double[] plane = f.calculateNormal(eyePts);
            double inprod = plane[0] * world.getSunX() + plane[1]
                    * world.getSunY() + plane[2] * world.getSunZ();
            double shade = (inprod + 1) / 2.0;
            if (f instanceof DoubleSidedFace 
                    || Utils2D.area2(pts[f.getVertexIndex(0)],
                    pts[f.getVertexIndex(1)], pts[f.getVertexIndex(2)]) > 0.0) {
                Color c = f.getColor();
                Path2D p = f.createPath(pts);
                g2.setPaint(new Color((int) (c.getRed() * shade),
                        (int) (c.getGreen() * shade),
                        (int) (c.getBlue() * shade), c.getAlpha()));
                if (this.elementHinting) {
                    beginElementGroup(f, g2);
                }
                g2.fill(p);
                if (drawOutline) {
                    g2.draw(p);
                }
                if (this.elementHinting) {
                    endElementGroup(f, g2);
                }
                
                if (f instanceof ChartBoxFace 
                        && (this.plot instanceof CategoryPlot3D 
                        || this.plot instanceof XYZPlot)) {
                    Stroke savedStroke = g2.getStroke();
                    ChartBoxFace cbf = (ChartBoxFace) f;
                    drawGridlines(g2, cbf, pts);
                    drawMarkers(g2, cbf, pts);
                    g2.setStroke(savedStroke);
                }
            } else if (f instanceof LabelFace) {
                LabelFace lf = (LabelFace) f;
                Path2D p = lf.createPath(pts);
                Rectangle2D lb = p.getBounds2D();
                g2.setFont(lf.getFont());
                g2.setColor(lf.getBackgroundColor());
                Rectangle2D bb = TextUtils.calcAlignedStringBounds(
                        lf.getLabel(), g2, 
                        (float) lb.getCenterX(), (float) lb.getCenterY(), 
                        TextAnchor.CENTER);
                g2.fill(bb);
                g2.setColor(lf.getTextColor());
                Rectangle2D r = TextUtils.drawAlignedString(lf.getLabel(), g2, 
                        (float) lb.getCenterX(), (float) lb.getCenterY(), 
                        TextAnchor.CENTER);
                lf.getOwner().setProperty("labelBounds", r);
            } 
        }
        RenderingInfo info = new RenderingInfo(facesInPaintOrder, pts, dx, dy);
        OnDrawHandler onDrawHandler = new OnDrawHandler(info, 
                this.elementHinting);
   
        // handle labels on pie plots...
        if (this.plot instanceof PiePlot3D) {
            drawPieLabels(g2, w, h, depth, info);
        }

        // handle axis labelling on non-pie plots...
        if (this.plot instanceof XYZPlot || this.plot instanceof 
                CategoryPlot3D) {
            drawAxes(g2, chartBox, pts, info);
        }    

        g2.setTransform(saved);
        
        // generate and draw the legend...
        if (this.legendBuilder != null) {
            TableElement legend = this.legendBuilder.createLegend(this.plot,
                    this.legendAnchor, this.legendOrientation, this.style);
            if (legend != null) {
                Dimension2D legendSize = legend.preferredSize(g2, bounds);
                Rectangle2D legendArea = calculateDrawArea(legendSize, 
                        this.legendAnchor, bounds);
                legend.draw(g2, legendArea, onDrawHandler);
            }
        }

        // draw the title...
        if (this.title != null) {
            Dimension2D titleSize = this.title.preferredSize(g2, bounds);
            Rectangle2D titleArea = calculateDrawArea(titleSize, 
                    this.titleAnchor, bounds);
            this.title.draw(g2, titleArea, onDrawHandler);
        }
        g2.setClip(savedClip);
        endElement(g2);
        return info;
    }
    
    private void beginElementGroup(Face face, Graphics2D g2) {
        Object3D owner = face.getOwner();
        ItemKey itemKey = (ItemKey) owner.getProperty(Object3D.ITEM_KEY);
        if (itemKey != null) {
            Map<String, String> m = new HashMap<String, String>();
            m.put("ref", itemKey.toJSONString());
            g2.setRenderingHint(Chart3DHints.KEY_BEGIN_ELEMENT, m);
        }
    }
    
    private void endElementGroup(Face face, Graphics2D g2) {
        Object3D owner = face.getOwner();
        ItemKey itemKey = (ItemKey) owner.getProperty(Object3D.ITEM_KEY);
        if (itemKey != null) {
            g2.setRenderingHint(Chart3DHints.KEY_END_ELEMENT, Boolean.TRUE);
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
            return cp.getColumnAxis().generateTickDataForColumns(
                    cp.getDataset());
        }
        if (plot instanceof XYZPlot) {
            XYZPlot xp = (XYZPlot) plot;
            return xp.getXAxis().generateTickData(tickUnit);
        }
        return Collections.emptyList();
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
        return Collections.emptyList(); 
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
            return cp.getRowAxis().generateTickDataForRows(cp.getDataset());
        }
        if (plot instanceof XYZPlot) {
            XYZPlot xp = (XYZPlot) plot;
            return xp.getZAxis().generateTickData(tickUnit);
        }
        return Collections.emptyList(); 
    }
    
    /**
     * Fetches marker data for the plot's x-axis.
     * 
     * @param plot  the plot ({@code null} not permitted).
     * 
     * @return A list of marker data items (possibly empty but never 
     *         {@code null}).
     */
    private List<MarkerData> fetchXMarkerData(Plot3D plot) {
        if (plot instanceof CategoryPlot3D) {
            return ((CategoryPlot3D) plot).getColumnAxis().generateMarkerData();
        }
        if (plot instanceof XYZPlot) {
             return ((XYZPlot) plot).getXAxis().generateMarkerData();
        }
        return new ArrayList<MarkerData>(0);    
    }
    
    /**
     * Fetches marker data for the plot's x-axis.
     * 
     * @param plot  the plot ({@code null} not permitted).
     * 
     * @return A list of marker data items (possibly empty but never 
     *         {@code null}).
     */
    private List<MarkerData> fetchYMarkerData(Plot3D plot) {
        if (plot instanceof CategoryPlot3D) {
            return ((CategoryPlot3D) plot).getValueAxis().generateMarkerData();
        }
        if (plot instanceof XYZPlot) {
             return ((XYZPlot) plot).getYAxis().generateMarkerData();
        }
        return new ArrayList<MarkerData>(0);    
    }
    
    /**
     * Fetches marker data for the plot's x-axis.
     * 
     * @param plot  the plot ({@code null} not permitted).
     * 
     * @return A list of marker data items (possibly empty but never 
     *         {@code null}).
     */
    private List<MarkerData> fetchZMarkerData(Plot3D plot) {
        if (plot instanceof CategoryPlot3D) {
            return ((CategoryPlot3D) plot).getRowAxis().generateMarkerData();
        }
        if (plot instanceof XYZPlot) {
             return ((XYZPlot) plot).getZAxis().generateMarkerData();
        }
        return new ArrayList<MarkerData>(0);    
    }
    
    /**
     * Draw the gridlines for one chart box face.
     * 
     * @param g2  the graphics target.
     * @param face  the face.
     * @param pts  the projection points.
     */
    private void drawGridlines(Graphics2D g2, ChartBoxFace face, 
            Point2D[] pts) {
        if (isGridlinesVisibleForX(this.plot)) {
            g2.setPaint(fetchGridlinePaintX(this.plot));
            g2.setStroke(fetchGridlineStrokeX(this.plot));
            List<TickData> xA = face.getXTicksA();
            List<TickData> xB = face.getXTicksB();
            for (int i = 0; i < xA.size(); i++) {
                Line2D line = new Line2D.Double(
                        pts[face.getOffset() + xA.get(i).getVertexIndex()], 
                        pts[face.getOffset() + xB.get(i).getVertexIndex()]);
                g2.draw(line);
            }
        }
                    
        if (isGridlinesVisibleForY(this.plot)) {
            g2.setPaint(fetchGridlinePaintY(this.plot));
            g2.setStroke(fetchGridlineStrokeY(this.plot));
            List<TickData> yA = face.getYTicksA();
            List<TickData> yB = face.getYTicksB();
            for (int i = 0; i < yA.size(); i++) {
                Line2D line = new Line2D.Double(
                        pts[face.getOffset() + yA.get(i).getVertexIndex()], 
                        pts[face.getOffset() + yB.get(i).getVertexIndex()]);
                g2.draw(line);
            }
        }
                    
        if (isGridlinesVisibleForZ(this.plot)) {
            g2.setPaint(fetchGridlinePaintZ(this.plot));
            g2.setStroke(fetchGridlineStrokeZ(this.plot));
            List<TickData> zA = face.getZTicksA();
            List<TickData> zB = face.getZTicksB();
            for (int i = 0; i < zA.size(); i++) {
                Line2D line = new Line2D.Double(
                        pts[face.getOffset() + zA.get(i).getVertexIndex()], 
                        pts[face.getOffset() + zB.get(i).getVertexIndex()]);
                g2.draw(line);
            }
        }
    }

    /**
     * Returns {@code true} if gridlines are visible for the x-axis
     * (column axis in the case of a {@link CategoryPlot3D}) and 
     * {@code false} otherwise.  For pie charts, this method will always
     * return {@code false}.
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
     * Returns {@code true} if gridlines are visible for the y-axis
     * (value axis in the case of a {@link CategoryPlot3D}) and 
     * {@code false} otherwise.
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
     * Returns {@code true} if gridlines are visible for the z-axis
     * (row axis in the case of a {@link CategoryPlot3D}) and 
     * {@code false} otherwise.
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
     * @param info  the rendering info ({@code null} permitted).
     */
    @SuppressWarnings("unchecked")
    private void drawPieLabels(Graphics2D g2, double w, double h, 
            double depth, RenderingInfo info) {
        PiePlot3D p = (PiePlot3D) this.plot;
        World labelOverlay = new World();
        List<Object3D> objs = p.getLabelFaces(-w / 2, -h / 2, -depth / 2);
        for (Object3D obj : objs) {
            labelOverlay.add(obj);
        }
        Point2D[] ppts = labelOverlay.calculateProjectedPoints(
                this.viewPoint, this.projDist);
        for (int i = 0; i < p.getDataset().getItemCount() * 2; i++) {
            if (p.getDataset().getValue(i / 2) == null) {
                continue;
            }
            Face f = labelOverlay.getFaces().get(i);
            if (Utils2D.area2(ppts[f.getVertexIndex(0)], 
                    ppts[f.getVertexIndex(1)], 
                    ppts[f.getVertexIndex(2)]) > 0) {
                Comparable<?> key = p.getDataset().getKey(i / 2);
                g2.setColor(p.getSectionLabelColorSource().getColor(key));
                g2.setFont(p.getSectionLabelFontSource().getFont(key));
                Point2D pt = Utils2D.centerPoint(ppts[f.getVertexIndex(0)], 
                        ppts[f.getVertexIndex(1)], ppts[f.getVertexIndex(2)],
                        ppts[f.getVertexIndex(3)]);
                String label = p.getSectionLabelGenerator().generateLabel(
                        p.getDataset(), key);
                String ref = "{\"type\": \"sectionLabel\", \"key\": \"" 
                        + key.toString() + "\"}";
                beginElementWithRef(g2, ref);
                Rectangle2D bounds = TextUtils.drawAlignedString(label, g2, 
                        (float) pt.getX(), (float) pt.getY(), 
                        TextAnchor.CENTER);
                endElement(g2);
                
                if (info != null) {
                    RenderedElement pieLabelRE = new RenderedElement(
                            InteractiveElementType.SECTION_LABEL, bounds);
                    pieLabelRE.setProperty("key", key);
                    info.addOffsetElement(pieLabelRE);
                }
            }
        }
    }
    
    private void beginElementWithRef(Graphics2D g2, String ref) {
        beginElement(g2, null, ref);    
    }
    
    private void beginElement(Graphics2D g2, String id, String ref) {
        if (this.elementHinting) {
            Map<String, String> m = new HashMap<String, String>();
            if (id != null) {
                m.put("id", id);
            }
            m.put("ref", ref);            
            g2.setRenderingHint(Chart3DHints.KEY_BEGIN_ELEMENT, m);            
        }    
    }

    private void endElement(Graphics2D g2) {
        if (this.elementHinting) {
            g2.setRenderingHint(Chart3DHints.KEY_END_ELEMENT, Boolean.TRUE);
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
        World tempWorld = new World();
        ChartBox3D chartBox = new ChartBox3D(w, h, depth, -w / 2.0, -h / 2.0, 
                -depth / 2.0, Color.WHITE);
        tempWorld.add(chartBox.createObject3D());
        Point2D[] axisPts2D = tempWorld.calculateProjectedPoints(
                this.viewPoint, this.projDist);

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
                ytick = yAxis.selectTick(g2, v0, v3, v7);
            }
            if (count(b, f) == 1 && longest(bf, be, df, de)) {
                ytick = yAxis.selectTick(g2, v1, v2, v4);
            }
            if (count(d, f) == 1 && longest(df, be, bf, de)) {
                ytick = yAxis.selectTick(g2, v6, v7, v0);
            }
            if (count(d, e) == 1 && longest(de, be, bf, df)) {
                ytick = yAxis.selectTick(g2, v5, v4, v1);
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
    
    /**
     * Draws the axes for a chart.
     * 
     * @param g2  the graphics target ({@code null} not permitted).
     * @param chartBox  the chart box (this contains projected points for
     *     the tick marks and labels)
     * @param pts  the projected points.
     * @param info  an object to be populated with rendering info, if it is
     *     non-{@code null}.
     */
    private void drawAxes(Graphics2D g2, ChartBox3D chartBox, Point2D[] pts,
            RenderingInfo info) {

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
                xAxis.draw(g2, v0, v1, v7, ticks, info, this.elementHinting);
            }
            if (count(b, c) == 1 && longest(bc, ab, cd, da)) {
                ticks = chartBox.faceB().getXTicksB();
                populateAnchorPoints(ticks, pts);
                xAxis.draw(g2, v3, v2, v6, ticks, info, this.elementHinting);
            }
            if (count(c, d) == 1 && longest(cd, ab, bc, da)) {
                ticks = chartBox.faceC().getXTicksB();
                populateAnchorPoints(ticks, pts);
                xAxis.draw(g2, v4, v7, v1, ticks, info, this.elementHinting);
            }
            if (count(d, a) == 1 && longest(da, ab, bc, cd)) {
                ticks = chartBox.faceA().getXTicksB();
                populateAnchorPoints(ticks, pts);
                xAxis.draw(g2, v5, v6, v3, ticks, info, this.elementHinting);
            }

            if (count(b, e) == 1 && longest(be, bf, df, de)) {
                ticks = chartBox.faceB().getYTicksA();
                populateAnchorPoints(ticks, pts);
                yAxis.draw(g2, v0, v3, v7, ticks, info, this.elementHinting);
            }
            if (count(b, f) == 1 && longest(bf, be, df, de)) {
                ticks = chartBox.faceB().getYTicksB();
                populateAnchorPoints(ticks, pts);
                yAxis.draw(g2, v1, v2, v4, ticks, info, this.elementHinting);
            }
            if (count(d, f) == 1 && longest(df, be, bf, de)) {
                ticks = chartBox.faceD().getYTicksA();
                populateAnchorPoints(ticks, pts);
                yAxis.draw(g2, v6, v7, v0, ticks, info, this.elementHinting);
            }
            if (count(d, e) == 1 && longest(de, be, bf, df)) {
                ticks = chartBox.faceD().getYTicksB();
                populateAnchorPoints(ticks, pts);
                yAxis.draw(g2, v5, v4, v1, ticks, info, this.elementHinting);
            }

            if (count(a, e) == 1 && longest(ae, af, cf, ce)) {
                ticks = chartBox.faceA().getZTicksA();
                populateAnchorPoints(ticks, pts);
                zAxis.draw(g2, v0, v5, v2, ticks, info, this.elementHinting);
            }
            if (count(a, f) == 1 && longest(af, ae, cf, ce)) {
                ticks = chartBox.faceA().getZTicksB();
                populateAnchorPoints(ticks, pts);
                zAxis.draw(g2, v1, v6, v3, ticks, info, this.elementHinting);
            }
            if (count(c, f) == 1 && longest(cf, ae, af, ce)) {
                ticks = chartBox.faceC().getZTicksB();
                populateAnchorPoints(ticks, pts);
                zAxis.draw(g2, v2, v7, v5, ticks, info, this.elementHinting);
            }
            if (count(c, e) == 1 && longest(ce, ae, af, cf)) {
                ticks = chartBox.faceC().getZTicksA();
                populateAnchorPoints(ticks, pts);
                zAxis.draw(g2, v3, v4, v6, ticks, info, this.elementHinting);
            }
        }
    }

    /**
     * Draws the markers for one face on a chart box.  The {@code pts}
     * array contains all the projected points for all the vertices in the
     * world...the chart box face references the required points by index.
     * 
     * @param g2  the graphics target ({@code null} not permitted).
     * @param face  the face of the chart box ({@code null} not permitted).
     * @param pts  the projected points for the whole world.
     */
    private void drawMarkers(Graphics2D g2, ChartBoxFace face, Point2D[] pts) {
        // x markers
        List<MarkerData> xmarkers = face.getXMarkers();
        for (MarkerData m : xmarkers) {
            m.updateProjection(pts);
            Marker marker = fetchXMarker(this.plot, m.getMarkerKey());
            beginElementWithRef(g2, "{\"type\": \"xMarker\", \"key\": \"" 
                    + m.getMarkerKey() + "\"}");
            marker.draw(g2, m, true);
            endElement(g2);
        }
        
        // y markers
        List<MarkerData> ymarkers = face.getYMarkers();
        for (MarkerData m : ymarkers) {
            m.updateProjection(pts);
            Marker marker = fetchYMarker(this.plot, m.getMarkerKey());
            beginElementWithRef(g2, "{\"type\": \"yMarker\", \"key\": \"" 
                    + m.getMarkerKey() + "\"}");
            marker.draw(g2, m, false);                
            endElement(g2);
        }
        
        // z markers
        List<MarkerData> zmarkers = face.getZMarkers();
        for (MarkerData m : zmarkers) {
            m.updateProjection(pts);
            beginElementWithRef(g2, "{\"type\": \"zMarker\", \"key\": \"" 
                    + m.getMarkerKey() + "\"}");
            Marker marker = fetchZMarker(this.plot, m.getMarkerKey());
            marker.draw(g2, m, false);
            endElement(g2);
        }
    }
    
    /**
     * Returns the marker from the plot's x-axis that has the specified key,
     * or {@code null} if there is no marker with that key.
     * 
     * @param plot  the plot ({@code null} not permitted).
     * @param key  the marker key ({@code null} not permitted).
     * 
     * @return The marker (possibly {@code null}). 
     */
    private Marker fetchXMarker(Plot3D plot, String key) {
        if (plot instanceof CategoryPlot3D) {
            return ((CategoryPlot3D) plot).getColumnAxis().getMarker(key);
        } else if (plot instanceof XYZPlot) {
            return ((XYZPlot) plot).getXAxis().getMarker(key);
        }
        return null;
    }
    
    /**
     * Returns the marker from the plot's y-axis that has the specified key,
     * or {@code null} if there is no marker with that key.
     * 
     * @param plot  the plot ({@code null} not permitted).
     * @param key  the marker key ({@code null} not permitted).
     * 
     * @return The marker (possibly {@code null}). 
     */
    private Marker fetchYMarker(Plot3D plot, String key) {
        if (plot instanceof CategoryPlot3D) {
            return ((CategoryPlot3D) plot).getValueAxis().getMarker(key);
        } else if (plot instanceof XYZPlot) {
            return ((XYZPlot) plot).getYAxis().getMarker(key);
        }
        return null;
    }

    /**
     * Returns the marker from the plot's z-axis that has the specified key,
     * or {@code null} if there is no marker with that key.
     * 
     * @param plot  the plot ({@code null} not permitted).
     * @param key  the marker key ({@code null} not permitted).
     * 
     * @return The marker (possibly {@code null}). 
     */
    private Marker fetchZMarker(Plot3D plot, String key) {
        if (plot instanceof CategoryPlot3D) {
            return ((CategoryPlot3D) plot).getRowAxis().getMarker(key);
        } else if (plot instanceof XYZPlot) {
            return ((XYZPlot) plot).getZAxis().getMarker(key);
        }
        return null;
    }

    /**
     * Receives a visitor.  The visitor is first directed to the plot, then
     * the visit is completed for the chart.
     * 
     * @param visitor  the visitor.
     * 
     * @since 1.2
     */
    @Override
    public void receive(ChartElementVisitor visitor) {
        this.plot.receive(visitor);
        visitor.visit(this);
    }

    /**
     * Tests this chart for equality with an arbitrary object.
     * 
     * @param obj  the object ({@code null} not permitted).
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
        if (!this.legendAnchor.equals(that.legendAnchor)) {
            return false;
        }
        if (this.legendOrientation != that.legendOrientation) {
            return false;
        }
        if (!this.renderingHints.equals(that.renderingHints)) {
            return false;
        }
        if (this.projDist != that.projDist) {
            return false;
        }
        return true;
    }

    /**
     * A utility method that calculates a drawing area based on a bounding area
     * and an anchor.
     * 
     * @param dim  the dimensions for the drawing area ({@code null} not 
     *     permitted).
     * @param anchor  the anchor ({@code null} not permitted).
     * @param bounds  the bounds ({@code null} not permitted).
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
     * Returns {@code true} if x is the longest of the four lengths,
     * and {@code false} otherwise.
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
     * {@code true}.  We use this to examine the visibility of
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
        if (event.requiresWorldUpdate()) {
            this.world = null;
        }
        notifyListeners(new Chart3DChangeEvent(event, this));
    }

    @Override
    public void styleChanged(ChartStyleChangeEvent event) {
        ChartStyler styler = new ChartStyler(event.getChartStyle());
        receive(styler);
        // create a visitor that will visit all chart components and apply the
        // style
        notifyListeners(new Chart3DChangeEvent(event, this));
    }
    
    /**
     * Registers a listener to receive notification of changes to the chart.
     * 
     * @param listener  the listener ({@code null} not permitted). 
     */
    public void addChangeListener(Chart3DChangeListener listener) {
        this.listenerList.add(Chart3DChangeListener.class, listener);   
    }
  
    /**
     * Deregisters a listener so that it no longer receives notification of
     * changes to the chart.
     * 
     * @param listener  the listener ({@code null} not permitted). 
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
     * {@link Chart3DChangeEvent} notifications.
     *
     * @param notify  a boolean.
     *
     * @see #isNotify()
     */
    public void setNotify(boolean notify) {
        this.notify = notify;
        // if the flag is being set to true, there may be queued up changes...
        if (notify) {
            this.world = null;
            fireChangeEvent();
        }
    }
  
    /**
     * Sends a {@link Chart3DChangeEvent} to all registered listeners.
     */
    protected void fireChangeEvent() {
        notifyListeners(new Chart3DChangeEvent(this, this));
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the input stream.
     *
     * @throws IOException  if there is an I/O error.
     * @throws ClassNotFoundException  if there is a classpath problem.
     */
    private void readObject(ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        // recreate an empty listener list
        this.listenerList = new EventListenerList();
        this.plot.addChangeListener(this);
        // RenderingHints is not easily serialized, so we just put back the
        // defaults...
        this.renderingHints = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        this.renderingHints.put(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                
    }
    
    /**
     * Returns a string representing the {@code element}, primarily for
     * debugging purposes.
     * 
     * @param element  the element ({@code null} not permitted).
     * 
     * @return A string (never {@code null}).
     */
    public static String renderedElementToString(RenderedElement element) {
        Object type = element.getProperty(RenderedElement.TYPE);
        if (InteractiveElementType.SECTION_LABEL.equals(type)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Section label with key '");
            Object key = element.getProperty("key");
            sb.append(key.toString());
            sb.append("'");
            return sb.toString();
        } else if (InteractiveElementType.LEGEND_ITEM.equals(type)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Legend item with section key '");
            Object key = element.getProperty(Chart3D.SERIES_KEY);
            sb.append(key);
            sb.append("'");
            return sb.toString();
        } else if (InteractiveElementType.AXIS_LABEL.equals(type)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Axis label with the label '");
            sb.append(element.getProperty("label"));
            sb.append("'");
            return sb.toString();
        } else if (InteractiveElementType.CATEGORY_AXIS_TICK_LABEL.equals(type)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Axis tick label with the label '");
            sb.append(element.getProperty("label"));
            sb.append("'");
            return sb.toString();
        } else if (InteractiveElementType.VALUE_AXIS_TICK_LABEL.equals(type)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Axis tick label with the value '");
            sb.append(element.getProperty("value"));
            sb.append("'");
            return sb.toString();
        } else if ("obj3d".equals(type)) {
            StringBuilder sb = new StringBuilder();
            sb.append("An object in the 3D model");
            ItemKey itemKey = (ItemKey) element.getProperty(Object3D.ITEM_KEY);
            if (itemKey != null) {
                sb.append(" representing the data item [");
                sb.append(itemKey.toString());
                sb.append("]");
            }
            return sb.toString();
        } else {
            return element.toString();
        }
    }

}
