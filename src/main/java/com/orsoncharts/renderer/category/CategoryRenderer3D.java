/**
 * ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */
package com.orsoncharts.renderer.category;

import com.orsoncharts.axis.Range;
import com.orsoncharts.data.CategoryDataset3D;
import com.orsoncharts.data.Values3D;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.World;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.renderer.RendererType;

/**
 * Defines the methods that all renderers must support to work with a 
 * {@link CategoryPlot3D}.
 */
public interface CategoryRenderer3D {
 
    /**
     * Returns the renderer type.
     * 
     * @return The renderer type (never <code>null</code>). 
     */
    public RendererType getRendererType();
    
    /**
     * Returns the plot that this renderer is assigned to.
     * 
     * @return The plot (possibly <code>null</code>). 
     */
    public CategoryPlot3D getPlot();
  
    /**
     * Sets the plot that the renderer is assigned to.
     * 
     * @param plot  the plot (<code>null</code> permitted). 
     */
    public void setPlot(CategoryPlot3D plot);
  
    /**
     * Returns the range that should be used on the value axis to display all 
     * the specified data using this renderer.  Normally this will return the
     * minimum and maximum values in the dataset, but some renderers require 
     * a larger range (for example, the stacked bar renderer).
     * 
     * @param data  the data values (<code>null</code> not permitted).
     * 
     * @return The data range. 
     */
    public Range findValueRange(Values3D data);
  
    public void composeSeries(World world, Dimension3D dimensions,
            CategoryDataset3D dataset, int series, double xOffset, 
            double yOffset, double zOffset);
    
    /**
     * Constructs and places one item from the specified dataset into the given 
     * world.
     * 
     * @param world
     * @param dimensions
     * @param dataset
     * @param series
     * @param row
     * @param column
     * @param xOffset
     * @param yOffset
     * @param zOffset 
     */
    public void composeItem(World world, Dimension3D dimensions, 
            CategoryDataset3D dataset, int series, int row, int column,
            double xOffset, double yOffset, double zOffset);
 
}
