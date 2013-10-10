/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.data;

import java.util.EventObject;

/**
 * A dataset change event.
 */
public class Dataset3DChangeEvent extends EventObject {

    private Object dataset;
  
    /**
     * Creates a new dataset change event.  The source can be the same as the
     * dataset, but this is not required.
     * 
     * @param source  the source.
     * @param dataset  the dataset.
     */
    public Dataset3DChangeEvent(Object source, Object dataset) {
        super(source);
        this.dataset = dataset;
    }
  
    /**
     * Returns the dataset that this event is associated with.  This will
     * normally be an instance of {@link PieDataset3D}, 
     * {@link com.orsoncharts.data.category.CategoryDataset3D} or 
     * {@link com.orsoncharts.data.xyz.XYZDataset}.
     * 
     * @return The dataset. 
     */
    public Object getDataset() {
        return this.dataset;
    }
}
