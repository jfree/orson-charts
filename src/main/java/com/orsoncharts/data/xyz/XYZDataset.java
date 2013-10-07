/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.data.xyz;

import com.orsoncharts.data.Dataset3D;
import java.util.List;

/**
 * Defines the methods used to access data in the form of multiple series
 * containing (x, y, z) data items.
 */
public interface XYZDataset extends Dataset3D {

    /**
     * Returns the number of series in the dataset.
     * 
     * @return The number of series in the dataset.
     */
    public int getSeriesCount();

    /**
     * Returns a list of the series-keys for the dataset.  Modifying this
     * list will have no impact on the underlying dataset.
     * 
     * @return A list of the series-keys (possibly empty, but never 
     *     <code>null</code>). 
     */
    public List<Comparable> getSeriesKeys();
    
    public int getSeriesIndex(Comparable key);
    
    /**
     * Returns the number of items in a given series.
     * 
     * @param series  the series index.
     * 
     * @return The item count.
     */
    public int getItemCount(int series);

    /**
     * Returns the x-value for an item in a series.
     * 
     * @param series  the series index.
     * @param item  the item index.
     * 
     * @return The x-value. 
     */
    public double getX(int series, int item);

    /**
     * Returns the y-value for an item in a series.
     * 
     * @param series  the series index.
     * @param item  the item index.
     * 
     * @return The y-value. 
     */
    public double getY(int series, int item);

    /**
     * Returns the z-value for an item in a series.
     * 
     * @param series  the series index.
     * @param item  the item index.
     * 
     * @return The z-value. 
     */
    public double getZ(int series, int item);

}
