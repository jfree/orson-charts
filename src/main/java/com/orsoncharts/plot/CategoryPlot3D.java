/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.plot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Stroke;
import com.orsoncharts.axis.Axis3DChangeEvent;
import com.orsoncharts.axis.Axis3DChangeListener;
import com.orsoncharts.axis.CategoryAxis3D;
import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.data.Dataset3DChangeEvent;
import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.axis.ValueAxis3D;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.World;
import com.orsoncharts.legend.LegendItemInfo;
import com.orsoncharts.legend.StandardLegendItemInfo;
import com.orsoncharts.renderer.Renderer3DChangeEvent;
import com.orsoncharts.renderer.Renderer3DChangeListener;
import com.orsoncharts.renderer.category.CategoryRenderer3D;
import com.orsoncharts.renderer.RendererType;
import com.orsoncharts.util.ObjectUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * A category plot in 3D.
 */
public class CategoryPlot3D extends AbstractPlot3D 
        implements Axis3DChangeListener, Renderer3DChangeListener {

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
    private Paint gridlinePaintForRows;
    
    /** The stroke for the row axis gridlines (never <code>null</code>). */
    private Stroke gridlineStrokeForRows;

    /** Are gridlines shown for the column (x) axis? */
    private boolean gridlinesVisibleForColumns;
    
    /** The paint for the column axis gridlines (never <code>null</code>). */
    private Paint gridlinePaintForColumns;
    
    /** The stroke for the column axis gridlines (never <code>null</code>). */
    private Stroke gridlineStrokeForColumns;

    /** Are gridlines shown for the value axis? */
    private boolean gridlinesVisibleForValues;
    
    /** The paint for the value axis gridlines (never <code>null</code>). */
    private Paint gridlinePaintForValues;

    /** The stroke for the value axis gridlines (never <code>null</code>). */
    private Stroke gridlineStrokeForValues;
    
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
        fireChangeEvent();
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
        fireChangeEvent();
    }
    
    /**
     * Returns the renderer.
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
        fireChangeEvent();
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
        fireChangeEvent();
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
     * @see #setValueAxis(com.orsoncharts.axis.CategoryAxis3D) 
     * 
     */
    public void setColumnAxis(CategoryAxis3D axis) {
        ArgChecks.nullNotPermitted(axis, "axis");
        this.columnAxis.removeChangeListener(this);
        this.columnAxis = axis;
        this.columnAxis.addChangeListener(this);
        fireChangeEvent();
    }
    
    /**
     * Returns the value axis.
     * 
     * @return The value axis. 
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
        this.valueAxis.addChangeListener(this);
        fireChangeEvent();
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
        fireChangeEvent();
    }

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

    public void setGridlineStrokeForRows(Stroke stroke) {
        ArgChecks.nullNotPermitted(stroke, "stroke");
        this.gridlineStrokeForRows = stroke;
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
        fireChangeEvent();
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
        fireChangeEvent();
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
    
    public void setGridlineStrokeForValues(Stroke stroke) {
        ArgChecks.nullNotPermitted(stroke, "stroke");
        this.gridlineStrokeForValues = stroke;
    }
    
    public Paint getGridlinePaintForColumns() {
        return this.gridlinePaintForColumns;
    }
    
    public void setGridlinePaintForColumns(Paint paint) {
        ArgChecks.nullNotPermitted(paint, "paint");
        this.gridlinePaintForColumns = paint;
        fireChangeEvent();
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
    
    public void setGridlineStrokeForColumns(Stroke stroke) {
        ArgChecks.nullNotPermitted(stroke, "stroke");
        this.gridlineStrokeForColumns = stroke;
        fireChangeEvent();
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
        List<Comparable> keys = this.dataset.getSeriesKeys();
        for (Comparable key : keys) {
            int series = this.dataset.getSeriesIndex(key);
            Paint paint = this.renderer.getPaintSource().getLegendPaint(series);
            LegendItemInfo info = new StandardLegendItemInfo(key, 
                    key.toString(), paint);
            result.add(info);
        }
        return result;
    }

    @Override
    public void composeToWorld(World world, double xOffset, double yOffset, 
            double zOffset) {
        for (int series = 0; series < this.dataset.getSeriesCount(); series++) {
            if (this.renderer.getRendererType().equals(RendererType.BY_SERIES)) {
                
            } else {
                for (int row = 0; row < this.dataset.getRowCount(); row++) {
                    for (int column = 0; column < this.dataset.getColumnCount(); 
                            column++) {
                        this.renderer.composeItem(world, getDimensions(), 
                                this.dataset, series, row, column, 
                                xOffset, yOffset, zOffset);
                    }
                }
            }
            
        }
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
        if (!ObjectUtils.equalsPaint(this.gridlinePaintForRows, that.gridlinePaintForRows)) {
            return false;
        }
        if (this.gridlinesVisibleForColumns != that.gridlinesVisibleForColumns) {
            return false;
        }
        if (!this.gridlineStrokeForColumns.equals(that.gridlineStrokeForColumns)) {
            return false;
        }
        if (!ObjectUtils.equalsPaint(this.gridlinePaintForColumns, that.gridlinePaintForColumns)) {
            return false;
        }
        if (this.gridlinesVisibleForValues != that.gridlinesVisibleForValues) {
            return false;
        }
        if (!this.gridlineStrokeForValues.equals(that.gridlineStrokeForValues)) {
            return false;
        }
         if (!ObjectUtils.equalsPaint(this.gridlinePaintForValues, that.gridlinePaintForValues)) {
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
     * Returns the dimensions that best suit the current data values.
     * 
     * @return The dimensions (never <code>null</code>). 
     */
    private Dimension3D calculateDimensions() {
        double depth = Math.max(1.0, this.dataset.getRowCount() + 1);
        double width = Math.max(1.0, this.dataset.getColumnCount() + 1);
        double height = Math.max(1.0, Math.min(width, depth));
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
        fireChangeEvent();
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
        fireChangeEvent();
    }

}
