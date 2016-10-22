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

package com.orsoncharts.plot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Stroke;
import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.orsoncharts.ChartElementVisitor;
import com.orsoncharts.axis.Axis3DChangeEvent;
import com.orsoncharts.axis.Axis3DChangeListener;
import com.orsoncharts.axis.ValueAxis3D;
import com.orsoncharts.data.Dataset3DChangeEvent;
import com.orsoncharts.data.Dataset3DChangeListener;
import com.orsoncharts.data.ItemKey;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.data.xyz.XYZItemKey;
import com.orsoncharts.renderer.xyz.XYZRenderer;
import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.World;
import com.orsoncharts.label.StandardXYZLabelGenerator;
import com.orsoncharts.label.StandardXYZItemLabelGenerator;
import com.orsoncharts.label.XYZLabelGenerator;
import com.orsoncharts.label.XYZItemLabelGenerator;
import com.orsoncharts.legend.LegendItemInfo;
import com.orsoncharts.legend.StandardLegendItemInfo;
import com.orsoncharts.renderer.ComposeType;
import com.orsoncharts.renderer.Renderer3DChangeEvent;
import com.orsoncharts.renderer.Renderer3DChangeListener;
import com.orsoncharts.util.ObjectUtils;
import com.orsoncharts.util.SerialUtils;

/**
 * A 3D plot with three numerical axes that displays data from an
 * {@link XYZDataset}.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. 
 */
@SuppressWarnings("serial")
public class XYZPlot extends AbstractPlot3D implements Dataset3DChangeListener, 
        Axis3DChangeListener, Renderer3DChangeListener, Serializable {

    private static Stroke DEFAULT_GRIDLINE_STROKE = new BasicStroke(0.5f, 
            BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1f, 
            new float[] { 3f, 3f }, 0f);

    /** The dataset. */
    private XYZDataset dataset;

    /** The renderer. */
    private XYZRenderer renderer;
  
    /** The x-axis. */
    private ValueAxis3D xAxis;

    /** The y-axis. */
    private ValueAxis3D yAxis;
  
    /** The z-axis. */
    private ValueAxis3D zAxis;
    
    /** Are gridlines visible for the x-axis? */
    private boolean gridlinesVisibleX;
    
    /** The paint for the x-axis gridlines. */
    private transient Paint gridlinePaintX;

    /** The stroke for the x-axis gridlines. */
    private transient Stroke gridlineStrokeX;
    
    /** Are gridlines visible for the y-axis? */
    private boolean gridlinesVisibleY;

    /** The paint for the y-axis gridlines. */
    private transient Paint gridlinePaintY;
    
    /** The stroke for the y-axis gridlines. */
    private transient Stroke gridlineStrokeY;
    
    /** Are gridlines visible for the z-axis? */
    private boolean gridlinesVisibleZ;

    /** The paint for the z-axis gridlines. */
    private transient Paint gridlinePaintZ;

    /** The stroke for the z-axis gridlines. */
    private transient Stroke gridlineStrokeZ;

    /** The legend label generator. */
    private XYZLabelGenerator legendLabelGenerator;
    
    /** The tool tip generator (if null there will be no tooltips). */
    private XYZItemLabelGenerator toolTipGenerator;
    
    /**
     * Creates a new plot with the specified axes.
     * 
     * @param dataset  the dataset ({@code null} not permitted).
     * @param renderer  the renderer ({@code null} not permitted).
     * @param xAxis  the x-axis ({@code null} not permitted).
     * @param yAxis  the y-axis ({@code null} not permitted).
     * @param zAxis  the z-axis ({@code null} not permitted).
     */
    public XYZPlot(XYZDataset dataset, XYZRenderer renderer, ValueAxis3D xAxis, 
            ValueAxis3D yAxis, ValueAxis3D zAxis) {
        ArgChecks.nullNotPermitted(dataset, "dataset");
        ArgChecks.nullNotPermitted(renderer, "renderer");
        ArgChecks.nullNotPermitted(xAxis, "xAxis");
        ArgChecks.nullNotPermitted(yAxis, "yAxis");
        ArgChecks.nullNotPermitted(zAxis, "zAxis");
        this.dimensions = new Dimension3D(10, 10, 10);
        this.dataset = dataset;
        this.dataset.addChangeListener(this);
        this.renderer = renderer;
        this.renderer.setPlot(this);
        this.renderer.addChangeListener(this);
        this.xAxis = xAxis;
        this.xAxis.addChangeListener(this);
        this.xAxis.configureAsXAxis(this);
        this.zAxis = zAxis;
        this.zAxis.addChangeListener(this);
        this.zAxis.configureAsZAxis(this);
        this.yAxis = yAxis;
        this.yAxis.addChangeListener(this);
        this.yAxis.configureAsYAxis(this);
        this.gridlinesVisibleX = true;
        this.gridlinePaintX = Color.WHITE;
        this.gridlineStrokeX = DEFAULT_GRIDLINE_STROKE;
        this.gridlinesVisibleY = true;
        this.gridlinePaintY = Color.WHITE;
        this.gridlineStrokeY = DEFAULT_GRIDLINE_STROKE;
        this.gridlinesVisibleZ = true;
        this.gridlinePaintZ = Color.WHITE;
        this.gridlineStrokeZ = DEFAULT_GRIDLINE_STROKE;
        this.legendLabelGenerator = new StandardXYZLabelGenerator();
        this.toolTipGenerator = new StandardXYZItemLabelGenerator();
    }
    
    /**
     * Sets the dimensions for the plot and notifies registered listeners that
     * the plot dimensions have been changed.
     * 
     * @param dim  the new dimensions ({@code null} not permitted).
     */
    public void setDimensions(Dimension3D dim) {
        ArgChecks.nullNotPermitted(dim, "dim");
        this.dimensions = dim;
        fireChangeEvent(true);
    }

    /**
     * Returns the dataset for the plot.
     * 
     * @return The dataset (never {@code null}). 
     */
    public XYZDataset getDataset() {
        return this.dataset;
    }

    /**
     * Sets the dataset and sends a change event notification to all registered
     * listeners.
     * 
     * @param dataset  the new dataset ({@code null} not permitted).
     */
    public void setDataset(XYZDataset dataset) {
        ArgChecks.nullNotPermitted(dataset, "dataset");
        this.dataset.removeChangeListener(this);
        this.dataset = dataset;
        this.dataset.addChangeListener(this);
        fireChangeEvent(true);
    }

    /**
     * Returns the x-axis.
     * 
     * @return The x-axis (never {@code null}). 
     */
    public ValueAxis3D getXAxis() {
        return this.xAxis;
    }

    /**
     * Sets the x-axis and sends a {@link Plot3DChangeEvent} to all registered
     * listeners.
     * 
     * @param xAxis  the x-axis ({@code null} not permitted). 
     */
    public void setXAxis(ValueAxis3D xAxis) {
        ArgChecks.nullNotPermitted(xAxis, "xAxis");
        this.xAxis.removeChangeListener(this);
        xAxis.configureAsXAxis(this);
        xAxis.addChangeListener(this);
        this.xAxis = xAxis;
        fireChangeEvent(true);
    }

    /**
     * Returns the y-axis.
     * 
     * @return The y-axis (never {@code null}). 
     */
    public ValueAxis3D getYAxis() {
        return this.yAxis;
    }

    /**
     * Sets the y-axis and sends a {@link Plot3DChangeEvent} to all registered
     * listeners.
     * 
     * @param yAxis  the y-axis ({@code null} not permitted). 
     */
    public void setYAxis(ValueAxis3D yAxis) {
        ArgChecks.nullNotPermitted(yAxis, "yAxis");
        this.yAxis.removeChangeListener(this);
        yAxis.configureAsYAxis(this);
        yAxis.addChangeListener(this);
        this.yAxis = yAxis;
        fireChangeEvent(true);
    }
    
    /**
     * Returns the z-axis.
     * 
     * @return The z-axis (never {@code null}). 
     */
    public ValueAxis3D getZAxis() {
        return this.zAxis;
    }

    /**
     * Sets the z-axis and sends a {@link Plot3DChangeEvent} to all registered
     * listeners.
     * 
     * @param zAxis  the z-axis ({@code null} not permitted). 
     */
    public void setZAxis(ValueAxis3D zAxis) {
        ArgChecks.nullNotPermitted(zAxis, "zAxis");
        this.zAxis.removeChangeListener(this);
        zAxis.configureAsZAxis(this);
        zAxis.addChangeListener(this);
        this.zAxis = zAxis;
        fireChangeEvent(true);
    }
    
    /**
     * Returns the renderer for the plot.
     * 
     * @return The renderer (possibly {@code null}).
     */
    public XYZRenderer getRenderer() {
        return this.renderer;
    }

    /**
     * Sets the renderer for the plot and sends a {@link Plot3DChangeEvent}
     * to all registered listeners.
     * 
     * @param renderer  the renderer ({@code null} not permitted). 
     */
    public void setRenderer(XYZRenderer renderer) {
        this.renderer.setPlot(null);
        this.renderer.removeChangeListener(this);
        this.renderer = renderer;
        this.renderer.setPlot(this);
        this.renderer.addChangeListener(this);
        fireChangeEvent(true);
    }

    /**
     * Returns the flag that controls whether or not gridlines are shown for
     * the x-axis.
     * 
     * @return A boolean. 
     */
    public boolean isGridlinesVisibleX() {
        return this.gridlinesVisibleX;
    }

    /**
     * Sets the flag that controls whether or not gridlines are shown for the
     * x-axis and sends a {@link Plot3DChangeEvent} to all registered
     * listeners.
     * 
     * @param visible  the new flag value.
     */
    public void setGridlinesVisibleX(boolean visible) {
        this.gridlinesVisibleX = visible;
        fireChangeEvent(false);
    }
    
    /**
     * Returns the paint used to draw the gridlines for the x-axis.
     * 
     * @return The paint ({@code null} not permitted). 
     */
    public Paint getGridlinePaintX() {
        return this.gridlinePaintX;
    }
    
    /**
     * Sets the paint used to draw the gridlines for the x-axis, and sends 
     * a {@link Plot3DChangeEvent} to all registered listeners.
     * 
     * @param paint  the paint ({@code null} not permitted).
     */
    public void setGridlinePaintX(Paint paint) {
        ArgChecks.nullNotPermitted(paint, "paint");
        this.gridlinePaintX = paint;
        fireChangeEvent(false);
    }
    
    /**
     * Returns the stroke used to draw the gridlines for the x-axis.
     * 
     * @return The stroke ({@code null} not permitted). 
     */
    public Stroke getGridlineStrokeX() {
        return this.gridlineStrokeX;
    }

    /**
     * Sets the stroke used to draw the gridlines for the x-axis, and sends 
     * a {@link Plot3DChangeEvent} to all registered listeners.
     * 
     * @param stroke  the stroke ({@code null} not permitted).
     */
    public void setGridlineStrokeX(Stroke stroke) {
        ArgChecks.nullNotPermitted(stroke, "stroke");
        this.gridlineStrokeX = stroke;
        fireChangeEvent(false);
    }

    /**
     * Returns the flag that controls whether or not gridlines are shown for
     * the y-axis.
     * 
     * @return A boolean. 
     */
    public boolean isGridlinesVisibleY() {
        return this.gridlinesVisibleY;
    }
    
    /**
     * Sets the flag that controls whether or not gridlines are shown for the
     * y-axis and sends a {@link Plot3DChangeEvent} to all registered
     * listeners.
     * 
     * @param visible  the new flag value.
     */
    public void setGridlinesVisibleY(boolean visible) {
        this.gridlinesVisibleY = visible;
        fireChangeEvent(false);
    }

    /**
     * Returns the paint used to draw the gridlines for the y-axis.
     * 
     * @return The paint ({@code null} not permitted). 
     */
    public Paint getGridlinePaintY() {
        return this.gridlinePaintY;
    }
    
    /**
     * Sets the paint used to draw the gridlines for the y-axis, and sends 
     * a {@link Plot3DChangeEvent} to all registered listeners.
     * 
     * @param paint  the paint ({@code null} not permitted).
     */
    public void setGridlinePaintY(Paint paint) {
        ArgChecks.nullNotPermitted(paint, "paint");
        this.gridlinePaintY = paint;
        fireChangeEvent(false);
    }

    /**
     * Returns the stroke used to draw the gridlines for the y-axis.
     * 
     * @return The stroke ({@code null} not permitted). 
     */
    public Stroke getGridlineStrokeY() {
        return this.gridlineStrokeY;
    }
    
    /**
     * Sets the stroke used to draw the gridlines for the y-axis, and sends 
     * a {@link Plot3DChangeEvent} to all registered listeners.
     * 
     * @param stroke  the stroke ({@code null} not permitted).
     */
    public void setGridlineStrokeY(Stroke stroke) {
        ArgChecks.nullNotPermitted(stroke, "stroke");
        this.gridlineStrokeY = stroke;
        fireChangeEvent(false);
    }

    /**
     * Returns the flag that controls whether or not gridlines are shown for
     * the z-axis.
     * 
     * @return A boolean. 
     */
    public boolean isGridlinesVisibleZ() {
        return this.gridlinesVisibleZ;
    }
    
    /**
     * Sets the flag that controls whether or not gridlines are shown for the
     * z-axis and sends a {@link Plot3DChangeEvent} to all registered
     * listeners.
     * 
     * @param visible  the new flag value.
     */
    public void setGridlinesVisibleZ(boolean visible) {
        this.gridlinesVisibleZ = visible;
        fireChangeEvent(false);
    }
    
    /**
     * Returns the paint used to draw the gridlines for the z-axis.
     * 
     * @return The paint ({@code null} not permitted). 
     */
    public Paint getGridlinePaintZ() {
        return this.gridlinePaintZ;
    }    
    
    /**
     * Sets the paint used to draw the gridlines for the z-axis, and sends 
     * a {@link Plot3DChangeEvent} to all registered listeners.
     * 
     * @param paint  the paint ({@code null} not permitted).
     */
    public void setGridlinePaintZ(Paint paint) {
        ArgChecks.nullNotPermitted(paint, "paint");
        this.gridlinePaintZ = paint;
        fireChangeEvent(false);
    }

    /**
     * Returns the stroke used to draw the gridlines for the z-axis.
     * 
     * @return The stroke ({@code null} not permitted). 
     */
    public Stroke getGridlineStrokeZ() {
        return this.gridlineStrokeZ;
    }
    
    /**
     * Sets the stroke used to draw the gridlines for the z-axis, and sends 
     * a {@link Plot3DChangeEvent} to all registered listeners.
     * 
     * @param stroke  the stroke ({@code null} not permitted).
     */
    public void setGridlineStrokeZ(Stroke stroke) {
        ArgChecks.nullNotPermitted(stroke, "stroke");
        this.gridlineStrokeZ = stroke;
        fireChangeEvent(false);
    }

    /**
     * Returns the legend label generator.  The default value is a default
     * instance of {@link StandardXYZLabelGenerator}.
     * 
     * @return The legend label generator (never {@code null}).
     * 
     * @since 1.2
     */
    public XYZLabelGenerator getLegendLabelGenerator() {
        return this.legendLabelGenerator;
    }
    
    /**
     * Sets the legend label generator and sends a {@link Plot3DChangeEvent}
     * to all registered listeners.
     * 
     * @param generator  the generator ({@code null} not permitted). 
     * 
     * @since 1.2
     */
    public void setLegendLabelGenerator(XYZLabelGenerator generator) {
        ArgChecks.nullNotPermitted(generator, "generator");
        this.legendLabelGenerator = generator;
        fireChangeEvent(false);
    }
    
    /**
     * Returns a list containing legend item info, typically one item for
     * each series in the chart.  This is intended for use in the construction
     * of a chart legend.
     * 
     * @return A list containing legend item info.
     */
    @Override
    @SuppressWarnings("unchecked") // we don't know the generic types of the dataset
    public List<LegendItemInfo> getLegendInfo() {
        List<LegendItemInfo> result = new ArrayList<LegendItemInfo>();
        List<Comparable<?>> keys = this.dataset.getSeriesKeys();
        for (Comparable key : keys) {
            String label = this.legendLabelGenerator.generateSeriesLabel(
                    this.dataset, (Comparable) key);
            int series = this.dataset.getSeriesIndex(key);
            Color color = this.renderer.getColorSource().getLegendColor(series);
            LegendItemInfo info = new StandardLegendItemInfo(key, label, color);
            result.add(info);
        }
        return result;
    }

    /**
     * Adds 3D objects representing the current data for the plot to the 
     * specified world.  After the world has been populated (or constructed) in
     * this way, it is ready for rendering.
     * 
     * @param world  the world ({@code null} not permitted).
     * @param xOffset  the x-offset.
     * @param yOffset  the y-offset.
     * @param zOffset  the z-offset.
     */
    @Override
    public void compose(World world, double xOffset, double yOffset, 
            double zOffset) {
        if (this.renderer.getComposeType() == ComposeType.ALL) {
            this.renderer.composeAll(this, world, this.dimensions, xOffset, 
                    yOffset, zOffset);
        } else if (this.renderer.getComposeType() == ComposeType.PER_ITEM) {
            // for each data point in the dataset figure out if the composed 
            // shape intersects with the visible 
            // subset of the world, and if so add the object
            int seriesCount = this.dataset.getSeriesCount();
            for (int series = 0; series < seriesCount; series++) {
                int itemCount = this.dataset.getItemCount(series);
                for (int item = 0; item < itemCount; item++) {
                    this.renderer.composeItem(this.dataset, series, item, world, 
                            this.dimensions, xOffset, yOffset, zOffset);
                }
            }
        } else {
            // if we get here, someone changed the ComposeType enum
            throw new IllegalStateException("ComposeType not expected: " 
                    + this.renderer.getComposeType());
        }
    }

    @Override
    public String generateToolTipText(ItemKey itemKey) {
        if (!(itemKey instanceof XYZItemKey)) {
            throw new IllegalArgumentException(
                    "The itemKey must be a XYZItemKey instance.");
        }
        if (this.toolTipGenerator == null) {
            return null;
        }
        XYZItemKey k = (XYZItemKey) itemKey;
        return this.toolTipGenerator.generateItemLabel(dataset, 
                k.getSeriesKey(), k.getItemIndex());
    }

    /**
     * Receives a visitor.  This is a general purpose mechanism, but the main
     * use is to apply chart style changes across all the elements of a 
     * chart.
     * 
     * @param visitor  the visitor ({@code null} not permitted).
     * 
     * @since 1.2
     */
    @Override
    public void receive(ChartElementVisitor visitor) {
        this.xAxis.receive(visitor);
        this.yAxis.receive(visitor);
        this.zAxis.receive(visitor);
        this.renderer.receive(visitor);
        visitor.visit(this);
    }

    /**
     * Tests this plot instance for equality with an arbitrary object.
     * 
     * @param obj  the object ({@code null} permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof XYZPlot)) {
            return false;
        }
        XYZPlot that = (XYZPlot) obj;
        if (!this.dimensions.equals(that.dimensions)) {
            return false;
        }
        if (this.gridlinesVisibleX != that.gridlinesVisibleX) {
            return false;
        }
        if (this.gridlinesVisibleY != that.gridlinesVisibleY) {
            return false;
        }
        if (this.gridlinesVisibleZ != that.gridlinesVisibleZ) {
            return false;
        }
        if (!ObjectUtils.equalsPaint(this.gridlinePaintX, 
                that.gridlinePaintX)) {
            return false;
        }
        if (!ObjectUtils.equalsPaint(this.gridlinePaintY, 
                that.gridlinePaintY)) {
            return false;
        }
        if (!ObjectUtils.equalsPaint(this.gridlinePaintZ, 
                that.gridlinePaintZ)) {
            return false;
        }
        if (!this.gridlineStrokeX.equals(that.gridlineStrokeX)) {
            return false;
        }
        if (!this.gridlineStrokeY.equals(that.gridlineStrokeY)) {
            return false;
        }
        if (!this.gridlineStrokeZ.equals(that.gridlineStrokeZ)) {
            return false;
        }
        if (!this.legendLabelGenerator.equals(that.legendLabelGenerator)) {
            return false;
        }
        return super.equals(obj);
    }

    /**
     * Receives notification that one of the plot's axes has changed, and 
     * responds by passing on a {@link Plot3DChangeEvent} to the plot's 
     * registered listeners (with the default set-up, this notifies the 
     * chart).
     * 
     * @param event  the event. 
     */
    @Override
    public void axisChanged(Axis3DChangeEvent event) {
        this.yAxis.configureAsYAxis(this);
        fireChangeEvent(event.requiresWorldUpdate());
    }

    /**
     * Receives notification that the plot's renderer has changed, and 
     * responds by passing on a {@link Plot3DChangeEvent} to the plot's 
     * registered listeners (with the default set-up, this notifies the 
     * chart).
     * 
     * @param event  the event. 
     */
    @Override
    public void rendererChanged(Renderer3DChangeEvent event) {
        fireChangeEvent(event.requiresWorldUpdate());
    }

    /**
     * Receives notification that the plot's dataset has changed, and 
     * responds by passing on a {@link Plot3DChangeEvent} to the plot's 
     * registered listeners (with the default set-up, this notifies the 
     * chart).
     * 
     * @param event  the event. 
     */
    @Override
    public void datasetChanged(Dataset3DChangeEvent event) {
        this.xAxis.configureAsXAxis(this);
        this.yAxis.configureAsYAxis(this);
        this.zAxis.configureAsZAxis(this);
        super.datasetChanged(event);
    }
    
    /**
     * Provides serialization support.
     *
     * @param stream  the output stream.
     *
     * @throws IOException  if there is an I/O error.
     */
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        SerialUtils.writePaint(this.gridlinePaintX, stream);
        SerialUtils.writePaint(this.gridlinePaintY, stream);
        SerialUtils.writePaint(this.gridlinePaintZ, stream);
        SerialUtils.writeStroke(this.gridlineStrokeX, stream);
        SerialUtils.writeStroke(this.gridlineStrokeY, stream);
        SerialUtils.writeStroke(this.gridlineStrokeZ, stream);
        
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
        this.gridlinePaintX = SerialUtils.readPaint(stream);
        this.gridlinePaintY = SerialUtils.readPaint(stream);
        this.gridlinePaintZ = SerialUtils.readPaint(stream);
        this.gridlineStrokeX = SerialUtils.readStroke(stream);
        this.gridlineStrokeY = SerialUtils.readStroke(stream);
        this.gridlineStrokeZ = SerialUtils.readStroke(stream);
    }

}
