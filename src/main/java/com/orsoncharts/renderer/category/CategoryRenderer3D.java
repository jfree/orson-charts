/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.renderer.category;

import com.orsoncharts.Range;
import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.data.Values3D;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.World;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.renderer.Renderer3D;
import com.orsoncharts.renderer.Renderer3DChangeEvent;

/**
 * Defines the methods that all renderers must support to work with a 
 * {@link CategoryPlot3D}.
 */
public interface CategoryRenderer3D extends Renderer3D {
    
    /**
     * Returns the plot that this renderer is assigned to.
     * 
     * @return The plot (possibly <code>null</code>). 
     */
    CategoryPlot3D getPlot();
  
    /**
     * Sets the plot that the renderer is assigned to.  Although this method
     * is part of the public API, client code should not need to call it.
     * 
     * @param plot  the plot (<code>null</code> permitted). 
     */
    void setPlot(CategoryPlot3D plot);
  
    /**
     * Returns the color source for the renderer, which is an object that
     * is responsible for providing the colors used by the renderer to draw
     * data (and legend) items.
     * 
     * @return The paint source (never <code>null</code>). 
     */
    CategoryColorSource getColorSource();
    
    /**
     * Sets the color source for the renderer and sends a 
     * {@link Renderer3DChangeEvent} to all registered listeners.
     * 
     * @param source  the color source (<code>null</code> not permitted).
     */
    void setColorSource(CategoryColorSource source);
    
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
    Range findValueRange(Values3D data);
    
    /**
     * Constructs and places one item from the specified dataset into the given 
     * world.  This method will be called by the {@link CategoryPlot3D} class
     * while iterating over the items in the dataset.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param series  the series index.
     * @param row  the row index.
     * @param column  the column index.
     * @param world  the world (<code>null</code> not permitted).
     * @param dimensions  the plot dimensions (<code>null</code> not permitted).
     * @param xOffset  the x-offset.
     * @param yOffset  the y-offset.
     * @param zOffset  the z-offset.
     */
    void composeItem(CategoryDataset3D dataset, int series, int row, int column,
            World world, Dimension3D dimensions, 
            double xOffset, double yOffset, double zOffset);
 
}
