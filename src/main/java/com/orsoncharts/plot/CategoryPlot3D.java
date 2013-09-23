/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package com.orsoncharts.plot;

import com.orsoncharts.axis.Axis3D;
import com.orsoncharts.axis.Range;
import com.orsoncharts.data.CategoryDataset3D;
import com.orsoncharts.event.Dataset3DChangeEvent;
import com.orsoncharts.graphics3d.ArgChecks;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.World;
import com.orsoncharts.renderer.CategoryRenderer3D;
import com.orsoncharts.renderer.RendererType;

/**
 * A category plot in 3D.
 */
public class CategoryPlot3D extends AbstractPlot3D {

    private CategoryDataset3D dataset;
    
    private Axis3D rowAxis;
    
    private Axis3D columnAxis;
    
    private Axis3D valueAxis;

    private CategoryRenderer3D renderer;

    /**
     * Creates a new plot.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted). 
     */
    public CategoryPlot3D(CategoryDataset3D dataset, Axis3D rowAxis, 
            Axis3D columnAxis, Axis3D valueAxis) {
        ArgChecks.nullNotPermitted(dataset, "dataset");
        this.dataset = dataset;
        this.dataset.addChangeListener(this);
        this.rowAxis = rowAxis;
        this.columnAxis = columnAxis;
        this.valueAxis = valueAxis;
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
    
    public CategoryRenderer3D getRenderer() {
        return this.renderer;
    }
    
    public void setRenderer(CategoryRenderer3D renderer) {
        ArgChecks.nullNotPermitted(renderer, "renderer");
        this.renderer = renderer;
        // a new renderer might mean the axis range needs changing...
        updateValueAxis();
        fireChangeEvent();
    }
    
    public Axis3D getRowAxis() {
        return this.rowAxis;
    }
    
    public Axis3D getColumnAxis() {
        return this.columnAxis;
    }
    
    public Axis3D getValueAxis() {
        return this.valueAxis;
    }
    
    @Override
    public Dimension3D getDimensions() {
        double depth = Math.max(1.0, this.dataset.getRowCount() + 1);
        double width = Math.max(1.0, this.dataset.getColumnCount() + 1);
        double height = Math.max(1.0, Math.min(width, depth));
        return new Dimension3D(width, height, depth);
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
    
    @Override
    public void datasetChanged(Dataset3DChangeEvent event) {
        // update the category axis labels 
        // and the value axis range
        //this.columnAxis.getRange()
        updateValueAxis();

        super.datasetChanged(event);  // propogates a plot change event
    }
    
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

}
