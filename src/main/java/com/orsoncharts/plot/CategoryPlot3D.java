/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.plot;

import com.orsoncharts.axis.Axis3D;
import com.orsoncharts.axis.Axis3DChangeEvent;
import com.orsoncharts.axis.Axis3DChangeListener;
import com.orsoncharts.axis.CategoryAxis3D;
import com.orsoncharts.axis.Range;
import com.orsoncharts.data.CategoryDataset3D;
import com.orsoncharts.data.Dataset3DChangeEvent;
import com.orsoncharts.graphics3d.ArgChecks;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.World;
import com.orsoncharts.renderer.Renderer3DChangeEvent;
import com.orsoncharts.renderer.Renderer3DChangeListener;
import com.orsoncharts.renderer.category.CategoryRenderer3D;
import com.orsoncharts.renderer.RendererType;

/**
 * A category plot in 3D.
 */
public class CategoryPlot3D extends AbstractPlot3D 
        implements Axis3DChangeListener, Renderer3DChangeListener {

    /** The dataset. */
    private CategoryDataset3D dataset;
    
    /** The renderer. */
    private CategoryRenderer3D renderer;

    /** The row axis. */
    private CategoryAxis3D rowAxis;
    
    /** The column axis. */
    private CategoryAxis3D columnAxis;
    
    /** The value axis. */
    private Axis3D valueAxis;

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
            CategoryAxis3D columnAxis, Axis3D valueAxis) {
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
        updateRowAxis();
        updateColumnAxis();
        updateValueAxis();
    }
    
    /**
     * Returns the dataset.
     * 
     * @return The dataset. 
     */
    public CategoryDataset3D getDataset() {
        return this.dataset;
    }
    
    /**
     * Sets the dataset and fires a plot change event.
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
        this.renderer = renderer;
        // a new renderer might mean the axis range needs changing...
        updateValueAxis();
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
    
    public void setRowAxis(Axis3D axis) {
        
    }
    
    /**
     * Returns the column axis.
     * 
     * @return The column axis. 
     */
    public CategoryAxis3D getColumnAxis() {
        return this.columnAxis;
    }
    
    /**
     * Returns the value axis.
     * 
     * @return The value axis. 
     */
    public Axis3D getValueAxis() {
        return this.valueAxis;
    }
    
    /**
     * Sets the dimensions for the plot and sets the 
     * <code>autoAdjustDimensions</code> flag to <code>false</code>.
     * 
     * @param dimensions  the dimensions (<code>null</code> not permitted).
     */
    public void setDimensions(Dimension3D dimensions) {
        ArgChecks.nullNotPermitted(dimensions, "dimensions");
        this.dimensions = dimensions;
        this.autoAdjustDimensions = false;
        fireChangeEvent();
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
        updateValueAxis();
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
     * Updates the row axis.
     */
    private void updateRowAxis() {
        this.rowAxis.configureAsRowAxis(this);
    }

    /**
     * Updates the column axis.
     */
    private void updateColumnAxis() {
        this.columnAxis.configureAsColumnAxis(this);
    }

    /**
     * Updates the value axis (for example, when a dataset change event is 
     * received, we may need to refresh the axis range).
     */
    private void updateValueAxis() {
        Range valueRange = null;
        if (this.renderer != null) {
            valueRange = this.renderer.findValueRange(getDataset());
        }
        if (valueRange == null) {
            valueRange = new Range(0, 1);
        }  
        this.valueAxis.setRange(valueRange);
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
