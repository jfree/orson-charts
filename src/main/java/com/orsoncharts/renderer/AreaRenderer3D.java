/**
 * OrsonCharts
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 */
package com.orsoncharts.renderer;

import com.orsoncharts.data.CategoryDataset3D;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.World;

/**
 * An area renderer for 3D charts.
 */
public class AreaRenderer3D extends AbstractCategoryRenderer3D {
    
    /**
     * Default constructor.
     */
    public AreaRenderer3D() {
        
    }

    @Override
    public RendererType getRendererType() {
        return RendererType.BY_SERIES;
    }
 
    @Override
    public void composeSeries(World world, Dimension3D dimensions,
            CategoryDataset3D dataset, int series, double xOffset, 
            double yOffset, double zOffset) {
        
        // each row will be represented by one area object 
        // (or objects if the data is disjointed)
        for (int row = 0; row < dataset.getRowCount(); row++) {
            
            
            for (int column = 0; column < dataset.getColumnCount(); column++) {
                
            }
        }
    }

}
