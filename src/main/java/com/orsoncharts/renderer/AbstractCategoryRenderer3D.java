/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package com.orsoncharts.renderer;

import com.orsoncharts.axis.Range;
import com.orsoncharts.data.CategoryDataset3D;
import com.orsoncharts.data.DataUtilities;
import com.orsoncharts.data.Values3D;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.World;
import com.orsoncharts.plot.CategoryPlot3D;

/**
 * A base class that can be used to implement renderers for a 
 * {@link CategoryPlot3D}.
 */
public abstract class AbstractCategoryRenderer3D implements CategoryRenderer3D {
    
    private CategoryPlot3D plot;
   
    private Category3DPaintSource paintSource;
    
    /**
     * Default constructor.
     */
    public AbstractCategoryRenderer3D() {
        this.paintSource = new DefaultCategory3DPaintSource();
    }
    
    /**
     * Returns the renderer type.
     * 
     * @return The renderer type (never <code>null</code>). 
     */
    @Override
    public RendererType getRendererType() {
        return RendererType.BY_ITEM;
    }
    
    @Override
    public CategoryPlot3D getPlot() {
        return this.plot;
    }
    
    @Override
    public void setPlot(CategoryPlot3D plot) {
        this.plot = plot;
    }

    public Category3DPaintSource getPaintSource() {
        return this.paintSource;
    }
    
    /**
     * Returns the range of values that will be required on the value axis
     * to see all the data from the dataset.
     * 
     * @param data  the data (<code>null</code> not permitted).
     * 
     * @return The range (possibly <code>null</code>) 
     */
    @Override
    public Range findValueRange(Values3D data) {
        return DataUtilities.findValueRange(data);
    }
    
    @Override
    public void composeSeries(World world, Dimension3D dimensions,
            CategoryDataset3D dataset, int series, 
            double xOffset, double yOffset, double zOffset) {
        throw new IllegalStateException(
                "The composeSeries() method is not implemented.");
    }

    @Override
    public void composeItem(World world, Dimension3D dimensions, 
            CategoryDataset3D dataset, int series, int row, int column, 
            double xOffset, double yOffset, double zOffset) {
        throw new IllegalStateException(
                "The composeSeries() method is not implemented.");
    }
    
    
    
}
