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

package com.orsoncharts.plot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Stroke;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.orsoncharts.Chart3D;
import com.orsoncharts.ChartElementVisitor;
import com.orsoncharts.axis.Axis3DChangeEvent;
import com.orsoncharts.axis.Axis3DChangeListener;
import com.orsoncharts.axis.CategoryAxis3D;
import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.data.Dataset3DChangeEvent;
import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.axis.ValueAxis3D;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.World;
import com.orsoncharts.label.CategoryLabelGenerator;
import com.orsoncharts.label.StandardCategoryLabelGenerator;
import com.orsoncharts.legend.LegendItemInfo;
import com.orsoncharts.legend.StandardLegendItemInfo;
import com.orsoncharts.renderer.Renderer3DChangeEvent;
import com.orsoncharts.renderer.Renderer3DChangeListener;
import com.orsoncharts.renderer.category.CategoryRenderer3D;
import com.orsoncharts.util.ObjectUtils;
import com.orsoncharts.util.SerialUtils;

/**
 * A 3D plot with two category axes (x and z) and a numerical y-axis that can
 * display data from a {@link CategoryDataset3D}.
 * <br><br>
 * The plot implements several listener interfaces so that it can receive 
 * notification of changes to its dataset, axes and renderer.  When change
 * events are received, the plot passes on a {@link Plot3DChangeEvent} to the
 * {@link Chart3D} instance that owns the plot.  This event chain is the 
 * mechanism that ensures that charts are repainted whenever the dataset 
 * changes, or when changes are made to the configuration of any chart 
 * component.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. 
 */
public class CategoryPlot3D extends AbstractPlot3D 
        implements Axis3DChangeListener, Renderer3DChangeListener, 
        Serializable {

    private static Stroke DEFAULT_GRIDLINE_STROKE = new BasicStroke(0.5f, 
            BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1f, 
            new float[] { 3f, 3f }, 0f);

    /** The dataset. */
    private CategoryDataset3D dataset;
    
    /** The renderer (never <code>null</code>). */
    private CategoryRenderer3D renderer;

    /** The row axis. */
    private CategoryAxis3D rowAxis;
    
    /** The column axis. */
    private CategoryAxis3D columnAxis;
    
    /** The value axis. */
    private ValueAxis3D valueAxis;

    /** Are gridlines shown for the row (z) axis? */
    private boolean gridlinesVisibleForRows;
    
    /** The paint for the row axis gridlines (never <code>null</code>). */
    private transient Paint gridlinePaintForRows;
    
    /** The stroke for the row axis gridlines (never <code>null</code>). */
    private transient Stroke gridlineStrokeForRows;

    /** Are gridlines shown for the column (x) axis? */
    private boolean gridlinesVisibleForColumns;
    
    /** The paint for the column axis gridlines (never <code>null</code>). */
    private transient Paint gridlinePaintForColumns;
    
    /** The stroke for the column axis gridlines (never <code>null</code>). */
    private transient Stroke gridlineStrokeForColumns;

    /** Are gridlines shown for the value axis? */
    private boolean gridlinesVisibleForValues;
    
    /** The paint for the value axis gridlines (never <code>null</code>). */
    private transient Paint gridlinePaintForValues;

    /** The stroke for the value axis gridlines (never <code>null</code>). */
    private transient Stroke gridlineStrokeForValues;
    
    /** The legend label generator. */
    private CategoryLabelGenerator legendLabelGenerator;

    /** 
     * A special attribute to provide control over the y-dimension for the
     * plot when the plot dimensions are auto-calculated.  The default value
     * is <code>null</code>.
     * 
     * @since 1.2
     */
    private Double yDimensionOverride;
    
    /**
     * Creates a new plot.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted). 
     * @param renderer  the renderer (<code>null</code> not permitted).
     * @param rowAxis  the row axis (<code>null</code> not permitted).
     * @param columnAxis  the column axis (<code>null</code> not permitted).
     * @param valueAxis  the value axis (<code>null</code> not permitted).
     */
    public CategoryPlot3D(CategoryDataset3D dataset, 
            CategoryRenderer3D renderer, CategoryAxis3D rowAxis, 
            CategoryAxis3D columnAxis, ValueAxis3D valueAxis) {
        ArgChecks.nullNotPermitted(dataset, "dataset");
        ArgChecks.nullNotPermitted(renderer, "renderer");
        ArgChecks.nullNotPermitted(rowAxis, "rowAxis");
        ArgChecks.nullNotPermitted(columnAxis, "columnAxis");
        ArgChecks.nullNotPermitted(valueAxis, "valueAxis");
        this.dataset = dataset;
        this.dataset.addChangeListener(this);
        this.dimensions = calculateDimensions();
        this.renderer = renderer;
        this.renderer.setPlot(this);
        this.renderer.addChangeListener(this);
        this.rowAxis = rowAxis;
        this.rowAxis.addChangeListener(this);
        this.columnAxis = columnAxis;
        this.columnAxis.addChangeListener(this);
        this.valueAxis = valueAxis;
        this.valueAxis.addChangeListener(this);
        this.rowAxis.configureAsRowAxis(this);
        this.columnAxis.configureAsColumnAxis(this);
        this.valueAxis.configureAsValueAxis(this);
        this.gridlinesVisibleForValues = true;
        this.gridlinesVisibleForColumns = false;
        this.gridlinesVisibleForRows = false;
        this.gridlinePaintForRows = Color.WHITE;
        this.gridlinePaintForColumns = Color.WHITE;
        this.gridlinePaintForValues = Color.WHITE;
        this.gridlineStrokeForRows = DEFAULT_GRIDLINE_STROKE;
        this.gridlineStrokeForColumns = DEFAULT_GRIDLINE_STROKE;
        this.gridlineStrokeForValues = DEFAULT_GRIDLINE_STROKE;
        this.legendLabelGenerator = new StandardCategoryLabelGenerator();
        this.yDimensionOverride = null;
    }
    
    /**
     * Sets the flag that controls whether the plot's dimensions are 
     * automatically calculated and, if <code>true</code>, sends a change
     * event to all registered listeners.
     * 
     * @param auto  the new flag value.
     * 
     * @since 1.2
     */
    public void setAutoAdjustDimensions(boolean auto) {
        this.autoAdjustDimensions = auto;
        if (auto) {
            this.dimensions = calculateDimensions();
            fireChangeEvent(true);
        }
    }
    
    /**
     * Sets the dimensions (in 3D space) for the plot, resets the 
     * <code>autoAdjustDimensions</code> flag to <code>false</code>, and sends
     * a {@link Plot3DChangeEvent} to all registered listeners.
     * 
     * @param dimensions  the dimensions (<code>null</code> not permitted).
     * 
     * @see Plot3D#getDimensions() 
     */
    public void setDimensions(Dimension3D dimensions) {
        ArgChecks.nullNotPermitted(dimensions, "dimensions");
        this.dimensions = dimensions;
        this.autoAdjustDimensions = false;
        fireChangeEvent(true);
    }

    /**
     * Returns the dataset.
     * 
     * @return The dataset (never <code>null</code>). 
     */
    public CategoryDataset3D getDataset() {
        return this.dataset;
    }
    
    /**
     * Sets the dataset and sends a {@link Plot3DChangeEvent} to all registered
     * listeners.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted). 
     */
    public void setDataset(CategoryDataset3D dataset) {
        ArgChecks.nullNotPermitted(dataset, "dataset");
        this.dataset.removeChangeListener(this);
        this.dataset = dataset;
        this.dataset.addChangeListener(this);
        fireChangeEvent(true);
    }
    
    /**
     * Returns the renderer (very often you will need to cast this to a 
     * specific class to make customisations).
     * 
     * @return The renderer (never <code>null</code>). 
     */
    public CategoryRenderer3D getRenderer() {
        return this.renderer;
    }
    
    /**
     * Sets the renderer and sends a change event to all registered listeners.
     * 
     * @param renderer  the renderer (<code>null</code> not permitted).
     */
    public void setRenderer(CategoryRenderer3D renderer) {
        ArgChecks.nullNotPermitted(renderer, "renderer");
        this.renderer.removeChangeListener(this);
        this.renderer = renderer;
        this.renderer.addChangeListener(this);
        // a new renderer might mean the axis range needs changing...
        this.valueAxis.configureAsValueAxis(this);
        fireChangeEvent(true);
    }
    
    /**
     * Returns the row axis.
     * 
     * @return The row axis. 
     */
    public CategoryAxis3D getRowAxis() {
        return this.rowAxis;
    }
    
    /**
     * Sets the row axis and sends a {@link Plot3DChangeEvent} to all 
     * registered listeners.  The row axis is equivalent to the z-axis.
     * 
     * @param axis  the row axis (<code>null</code> not permitted).
     */
    public void setRowAxis(CategoryAxis3D axis) {
        ArgChecks.nullNotPermitted(axis, "axis");
        this.rowAxis.removeChangeListener(this);
        this.rowAxis = axis;
        this.rowAxis.addChangeListener(this);
        fireChangeEvent(true);
    }
    
    /**
     * Returns the column axis.
     * 
     * @return The column axis (never <code>null</code>).
     */
    public CategoryAxis3D getColumnAxis() {
        return this.columnAxis;
    }
    
    /**
     * Sets the column axis and sends a {@link Plot3DChangeEvent} to all 
     * registered listeners.
     * 
     * @param axis  the new axis (<code>null</code> not permitted).
     * 
     * @see #setRowAxis(com.orsoncharts.axis.CategoryAxis3D) 
     * @see #setValueAxis(com.orsoncharts.axis.ValueAxis3D) 
     * 
     */
    public void setColumnAxis(CategoryAxis3D axis) {
        ArgChecks.nullNotPermitted(axis, "axis");
        this.columnAxis.removeChangeListener(this);
        this.columnAxis = axis;
        this.columnAxis.addChangeListener(this);
        fireChangeEvent(true);
    }
    
    /**
     * Returns the value axis (the vertical axis in the plot).
     * 
     * @return The value axis (never <code>null</code>). 
     */
    public ValueAxis3D getValueAxis() {
        return this.valueAxis;
    }
    
    /**
     * Sets the value axis and sends a {@link Plot3DChangeEvent} to all 
     * registered listeners.
     * 
     * @param axis  the axis (<code>null</code> not permitted). 
     */
    public void setValueAxis(ValueAxis3D axis) {
        ArgChecks.nullNotPermitted(axis, "axis");
        this.valueAxis.removeChangeListener(this);
        this.valueAxis = axis;
        this.valueAxis.configureAsValueAxis(this);
        this.valueAxis.addChangeListener(this);
        fireChangeEvent(true);
    }
    
    /**
     * Returns <code>true</code> if gridlines are shown for the row axis
     * and <code>false</code> otherwise.  The default value is 
     * <code>false</code>
     * 
     * @return A boolean. 
     */
    public boolean getGridlinesVisibleForRows() {
        return this.gridlinesVisibleForRows;
    }

    /**
     * Sets the flag that controls whether or not gridlines are shown for the
     * row axis and sends a {@link Plot3DChangeEvent} to all registered 
     * listeners.
     * 
     * @param visible  the new flag value.
     */
    public void setGridlinesVisibleForRows(boolean visible) {
        this.gridlinesVisibleForRows = visible;
        fireChangeEvent(false);
    }

    /**
     * Returns the paint used to draw the gridlines for the row axis, if they
     * are visible.
     * 
     * @return The paint (never <code>null</code>). 
     */
    public Paint getGridlinePaintForRows() {
        return this.gridlinePaintForRows;
    }

    /**
     * Sets the paint used for the row axis gridlines and sends a 
     * {@link Plot3DChangeEvent} to all registered listeners.
     * 
     * @param paint  the paint (<code>null</code> not permitted). 
     */
    public void setGridlinePaintForRows(Paint paint) {
        ArgChecks.nullNotPermitted(paint, "paint");
        this.gridlinePaintForRows = paint;
        fireChangeEvent(false);
    }

    /**
     * Returns the stroke for the gridlines associated with the row axis.
     * The default value is <code>BasicStroke(0.5f, BasicStroke.CAP_ROUND, 
     * BasicStroke.JOIN_ROUND, 1f, new float[] { 3f, 3f }, 0f)</code>.
     * 
     * @return The stroke (never <code>null</code>).
     */
    public Stroke getGridlineStrokeForRows() {
        return this.gridlineStrokeForRows;
    }

    /**
     * Sets the stroke used to draw the gridlines for the row axis, if they
     * are visible, and sends a {@link Plot3DChangeEvent} to all 
     * registered listeners.
     * 
     * @param stroke  the stroke (<code>null</code> not permitted). 
     */
    public void setGridlineStrokeForRows(Stroke stroke) {
        ArgChecks.nullNotPermitted(stroke, "stroke");
        this.gridlineStrokeForRows = stroke;
        fireChangeEvent(false);
    }

    /**
     * Returns <code>true</code> if gridlines are shown for the column axis
     * and <code>false</code> otherwise.  The default value is 
     * <code>false</code>
     * 
     * @return A boolean. 
     */
    public boolean getGridlinesVisibleForColumns() {
        return this.gridlinesVisibleForColumns;
    }

    /**
     * Sets the flag that controls whether or not gridlines are shown for the
     * column axis and sends a {@link Plot3DChangeEvent} to all registered 
     * listeners.
     * 
     * @param visible  the new flag value.
     */
    public void setGridlinesVisibleForColumns(boolean visible) {
        this.gridlinesVisibleForColumns = visible;
        fireChangeEvent(false);
    }
    
    /**
     * Returns <code>true</code> if gridlines are shown for the value axis
     * and <code>false</code> otherwise.  The default value is <code>TRUE</code>
     * 
     * @return A boolean. 
     */
    public boolean getGridlinesVisibleForValues() {
        return this.gridlinesVisibleForValues;
    }
    
    /**
     * Sets the flag that controls whether or not gridlines are shown for the
     * value axis and sends a {@link Plot3DChangeEvent} to all registered 
     * listeners.
     * 
     * @param visible  the new flag value.
     */
    public void setGridlinesVisibleForValues(boolean visible) {
        this.gridlinesVisibleForValues = visible;
        fireChangeEvent(false);
    }

    /**
     * Returns the paint for the gridlines associated with the value axis. 
     * The default value is <code>Color:WHITE</code>.
     * 
     * @return The paint for value axis gridlines (never <code>null</code>). 
     */
    public Paint getGridlinePaintForValues() {
        return this.gridlinePaintForValues;
    }
    
    /**
     * Sets the paint used for the value axis gridlines and sends a 
     * {@link Plot3DChangeEvent} to all registered listeners.
     * 
     * @param paint  the paint (<code>null</code> not permitted). 
     */
    public void setGridlinePaintForValues(Paint paint) {
        ArgChecks.nullNotPermitted(paint, "paint");
        this.gridlinePaintForValues = paint;
        fireChangeEvent(false);
    }

    /**
     * Returns the stroke for the gridlines associated with the value axis.
     * The default value is <code>BasicStroke(0.5f, BasicStroke.CAP_ROUND, 
     * BasicStroke.JOIN_ROUND, 1f, new float[] { 3f, 3f }, 0f)</code>.
     * 
     * @return The stroke (never <code>null</code>).
     */
    public Stroke getGridlineStrokeForValues() {
        return this.gridlineStrokeForValues;
    }
    
    /**
     * Sets the stroke used to draw the grid lines for the value axis, if
     * they are visible, and sends a {@link Plot3DChangeEvent} to all
     * registered listeners.
     * 
     * @param stroke  the stroke (<code>null</code> not permitted).
     */
    public void setGridlineStrokeForValues(Stroke stroke) {
        ArgChecks.nullNotPermitted(stroke, "stroke");
        this.gridlineStrokeForValues = stroke;
        fireChangeEvent(false);
    }
    
    /**
     * Returns the paint used to draw the grid lines for the column axis, if
     * they are visible.
     * 
     * @return The paint (never <code>null</code>). 
     */
    public Paint getGridlinePaintForColumns() {
        return this.gridlinePaintForColumns;
    }
    
    /**
     * Sets the paint used to draw the grid lines for the column axis, if 
     * they are visible, and sends a {@link Plot3DChangeEvent} to all 
     * registered listeners.
     * 
     * @param paint  the paint (<code>null</code> not permitted). 
     */
    public void setGridlinePaintForColumns(Paint paint) {
        ArgChecks.nullNotPermitted(paint, "paint");
        this.gridlinePaintForColumns = paint;
        fireChangeEvent(false);
    }

    /**
     * Returns the stroke for the gridlines associated with the column axis.
     * The default value is <code>BasicStroke(0.5f, BasicStroke.CAP_ROUND, 
     * BasicStroke.JOIN_ROUND, 1f, new float[] { 3f, 3f }, 0f)</code>.
     * 
     * @return The stroke (never <code>null</code>).
     */
    public Stroke getGridlineStrokeForColumns() {
        return this.gridlineStrokeForColumns;
    }
    
    /**
     * Sets the stroke used to draw the grid lines for the column axis, if
     * they are visible, and sends a {@link Plot3DChangeEvent} to all
     * registered listeners.
     * 
     * @param stroke  the stroke (<code>null</code> not permitted).
     */
    public void setGridlineStrokeForColumns(Stroke stroke) {
        ArgChecks.nullNotPermitted(stroke, "stroke");
        this.gridlineStrokeForColumns = stroke;
        fireChangeEvent(false);
    }

    /**
     * Returns the legend label generator.
     * 
     * @return The legend label generator (never <code>null</code>).
     * 
     * @since 1.2
     */
    public CategoryLabelGenerator getLegendLabelGenerator() {
        return this.legendLabelGenerator;    
    }
    
    /**
     * Sets the legend label generator and sends a {@link Plot3DChangeEvent}
     * to all registered listeners.
     * 
     * @param generator  the generator (<code>null</code> not permitted).
     * 
     * @since 1.2
     */
    public void setLegendLabelGenerator(CategoryLabelGenerator generator) {
        ArgChecks.nullNotPermitted(generator, "generator");
        this.legendLabelGenerator = generator;
        fireChangeEvent(false);
    }
    
    /**
     * Returns the y-dimension override.  The default value is
     * <code>null</code>.
     * 
     * @return The y-dimension override (possibly <code>null</code>). 
     * 
     * @since 1.2
     */
    public Double getYDimensionOverride() {
        return this.yDimensionOverride;
    }
    
    /**
     * Sets the y-dimension override and, if the autoAdjustDimensions flag is
     * set, recalculates the dimensions and sends a change event to all 
     * registered listeners.
     * 
     * @param dim  the new y-dimension override (<code>null</code> permitted).
     * 
     * @since 1.2
     */
    public void setYDimensionOverride(Double dim) {
        this.yDimensionOverride = dim;
        if (this.autoAdjustDimensions) {
            this.dimensions = calculateDimensions();
            fireChangeEvent(true);
        }
    }
    
    /**
     * Returns a list containing legend item info, typically one item for
     * each series in the chart.  This is intended for use in the construction
     * of a chart legend.
     * 
     * @return A list containing legend item info.
     */
    @Override
    public List<LegendItemInfo> getLegendInfo() {
        List<LegendItemInfo> result = new ArrayList<LegendItemInfo>();
        List<Comparable<?>> keys = this.dataset.getSeriesKeys();
        for (Comparable<?> key : keys) {
            int series = this.dataset.getSeriesIndex(key);
            Paint paint = this.renderer.getColorSource().getLegendColor(series);
            String seriesLabel = this.legendLabelGenerator.generateSeriesLabel(
                    this.dataset, key);
            LegendItemInfo info = new StandardLegendItemInfo(key, 
                    seriesLabel, paint);
            result.add(info);
        }
        return result;
    }

    @Override
    public void compose(World world, double xOffset, double yOffset, 
            double zOffset) {
        for (int series = 0; series < this.dataset.getSeriesCount(); series++) {
            for (int row = 0; row < this.dataset.getRowCount(); row++) {
                for (int column = 0; column < this.dataset.getColumnCount(); 
                        column++) {
                    this.renderer.composeItem(this.dataset, series, row, column,
                            world, getDimensions(), xOffset, yOffset, zOffset);
                }
            }
        }
    }
    
    /**
     * Accepts a visitor.  This is a general purpose mechanism, but the main
     * use is to apply chart style changes across all the elements of a 
     * chart.
     * 
     * @param visitor  the visitor (<code>null</code> not permitted).
     * 
     * @since 1.2
     */
    @Override 
    public void receive(ChartElementVisitor visitor) {
        this.columnAxis.receive(visitor);
        this.rowAxis.receive(visitor);
        this.valueAxis.receive(visitor);
        this.renderer.receive(visitor);
        visitor.visit(this);
    }

    /**
     * Tests this plot for equality with an arbitrary object.
     * 
     * @param obj  the object (<code>null</code> permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }    
        if (!(obj instanceof CategoryPlot3D)) {
            return false;
        }
        CategoryPlot3D that = (CategoryPlot3D) obj;
        if (this.gridlinesVisibleForRows != that.gridlinesVisibleForRows) {
            return false;
        }
        if (!this.gridlineStrokeForRows.equals(that.gridlineStrokeForRows)) {
            return false;
        }
        if (!ObjectUtils.equalsPaint(this.gridlinePaintForRows, 
                that.gridlinePaintForRows)) {
            return false;
        }
        if (this.gridlinesVisibleForColumns 
                != that.gridlinesVisibleForColumns) {
            return false;
        }
        if (!this.gridlineStrokeForColumns.equals(
                that.gridlineStrokeForColumns)) {
            return false;
        }
        if (!ObjectUtils.equalsPaint(this.gridlinePaintForColumns, 
                that.gridlinePaintForColumns)) {
            return false;
        }
        if (this.gridlinesVisibleForValues != that.gridlinesVisibleForValues) {
            return false;
        }
        if (!this.gridlineStrokeForValues.equals(that.gridlineStrokeForValues)) {
            return false;
        }
        if (!ObjectUtils.equalsPaint(this.gridlinePaintForValues, 
                 that.gridlinePaintForValues)) {
            return false;
        }
        if (!this.legendLabelGenerator.equals(that.legendLabelGenerator)) {
            return false;
        }
        if (!ObjectUtils.equals(this.yDimensionOverride, 
                that.yDimensionOverride)) {
            return false;
        }
       return super.equals(obj);
    }
    
    /**
     * Receives notification of a change to the dataset. 
     * 
     * @param event  the change event. 
     */
    @Override
    public void datasetChanged(Dataset3DChangeEvent event) {
        // update the category axis labels 
        // and the value axis range
        if (this.autoAdjustDimensions) {
            this.dimensions = calculateDimensions();
        }
        this.columnAxis.configureAsColumnAxis(this);
        this.rowAxis.configureAsRowAxis(this);
        this.valueAxis.configureAsValueAxis(this);
        super.datasetChanged(event);  // propogates a plot change event
    }
    
    /**
     * Returns the dimensions for the plot that best suit the current data 
     * values.  The x-dimension is set to the number of columns in the 
     * dataset and the z-dimension is set to the number of rows in the dataset.
     * For the y-dimension, the code first checks the 
     * <code>yDimensionOverride</code> attribute to see if a specific value is 
     * requested...and if not, the minimum of the x and z dimensions will be
     * used.
     * 
     * @return The dimensions (never <code>null</code>). 
     */
    private Dimension3D calculateDimensions() {
        double depth = Math.max(1.0, this.dataset.getRowCount() + 1);
        double width = Math.max(1.0, this.dataset.getColumnCount() + 1);
        double height = Math.max(1.0, Math.min(width, depth));
        if (this.yDimensionOverride != null) {
            height = this.yDimensionOverride.doubleValue();
        }
        return new Dimension3D(width, height, depth);
    }
   
    /**
     * Receives notification that one of the axes has been changed.
     * 
     * @param event  the change event. 
     */
    @Override
    public void axisChanged(Axis3DChangeEvent event) {
        // for now we just fire a plot change event which will flow up the
        // chain and eventually trigger a chart repaint
        fireChangeEvent(event.requiresWorldUpdate());
    }

    /**
     * Receives notification that the renderer has been modified in some way.
     * 
     * @param event  information about the event. 
     */
    @Override
    public void rendererChanged(Renderer3DChangeEvent event) {
        // for now we just fire a plot change event which will flow up the
        // chain and eventually trigger a chart repaint
        fireChangeEvent(event.requiresWorldUpdate());
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
        SerialUtils.writePaint(this.gridlinePaintForRows, stream);
        SerialUtils.writePaint(this.gridlinePaintForColumns, stream);
        SerialUtils.writePaint(this.gridlinePaintForValues, stream);
        SerialUtils.writeStroke(this.gridlineStrokeForRows, stream);
        SerialUtils.writeStroke(this.gridlineStrokeForColumns, stream);
        SerialUtils.writeStroke(this.gridlineStrokeForValues, stream);
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
        this.gridlinePaintForRows = SerialUtils.readPaint(stream);
        this.gridlinePaintForColumns = SerialUtils.readPaint(stream);
        this.gridlinePaintForValues = SerialUtils.readPaint(stream);
        this.gridlineStrokeForRows = SerialUtils.readStroke(stream);
        this.gridlineStrokeForColumns = SerialUtils.readStroke(stream);
        this.gridlineStrokeForValues = SerialUtils.readStroke(stream);
    }

}
