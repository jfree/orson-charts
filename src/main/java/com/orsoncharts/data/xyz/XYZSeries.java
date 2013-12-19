/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.data.xyz;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import com.orsoncharts.util.ArgChecks;

/**
 * A data series containing a sequence of <code>(x, y, z)</code> data items.  
 * The series has a key to identify it, and can be added to an 
 * {@link XYZSeriesCollection} to create a dataset.
 */
public class XYZSeries implements Serializable {

    /** The series key (never <code>null</code>). */
    private Comparable<?> key;

    /** The data items in the series. */
    private List<XYZDataItem> items;

    /**
     * Creates a new series with the specified key.
     * 
     * @param key  the key (<code>null</code> not permitted). 
     */
    public XYZSeries(Comparable<?> key) {
        ArgChecks.nullNotPermitted(key, "key");
        this.key = key;
        this.items = new ArrayList<XYZDataItem>();
    }

    /**
     * Returns the series key.
     * 
     * @return The series key (never <code>null</code>). 
     */
    public Comparable<?> getKey() {
        return this.key;
    }
    
    /**
     * Returns the number of items in the series.
     * 
     * @return The number of items in the series. 
     */
    public int getItemCount() {
        return this.items.size();
    }
    
    /**
     * Returns the x-value for the specified item.
     * 
     * @param itemIndex  the item index.
     * 
     * @return The x-value. 
     */
    public double getXValue(int itemIndex) {
        return this.items.get(itemIndex).getX();
    }
  
    /**
     * Returns the y-value for the specified item.
     * 
     * @param itemIndex  the item index.
     * 
     * @return The y-value. 
     */
    public double getYValue(int itemIndex) {
        return this.items.get(itemIndex).getY();
    }

    /**
     * Returns the z-value for the specified item.
     * 
     * @param itemIndex  the item index.
     * 
     * @return The z-value. 
     */
    public double getZValue(int itemIndex) {
        return this.items.get(itemIndex).getZ();
    }

    /**
     * Adds a new data item to the series.
     * 
     * @param x  the x-value.
     * @param y  the y-value.
     * @param z  the z-value.
     */
    public void add(double x, double y, double z) {
        add(new XYZDataItem(x, y, z));   
    }

    /**
     * Adds a new data item to the series.
     * 
     * @param item  the data item (<code>null</code> not permitted).
     */
    public void add(XYZDataItem item) {
        ArgChecks.nullNotPermitted(item, "item");
        this.items.add(item);
    }

    /**
     * Tests this series for equality with an arbitrary object.
     * 
     * @param obj  the object to test (<code>null</code> permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof XYZSeries)) {
            return false;
        }
        XYZSeries that = (XYZSeries) obj;
        if (!this.key.equals(that.key)) {
            return false;
        }
        if (!this.items.equals(that.items)) {
            return false;
        }
        return true;
    }
}
