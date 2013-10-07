/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package com.orsoncharts.data.xyz;

import java.util.ArrayList;
import java.util.List;
import com.orsoncharts.util.ArgChecks;

/**
 * A series of (x, y, z) data items.
 */
public class XYZSeries {

    private Comparable key;

    private List<XYZDataItem> items;

    /**
     * Creates a new series with the specified key.
     * 
     * @param key  the key (<code>null</code> not permitted). 
     */
    public XYZSeries(Comparable key) {
        ArgChecks.nullNotPermitted(key, "key");
        this.key = key;
        this.items = new ArrayList<XYZDataItem>();
    }

    public Comparable getKey() {
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

    public void add(double x, double y, double z) {
        add(new XYZDataItem(x, y, z));   
    }

    /**
     * Adds an item to the series.
     * 
     * @param item 
     */
    public void add(XYZDataItem item) {
        ArgChecks.nullNotPermitted(item, "item");
        this.items.add(item);
    }

    public double getXValue(int itemIndex) {
        return this.items.get(itemIndex).getX();
    }
  
    public double getYValue(int itemIndex) {
        return this.items.get(itemIndex).getY();
    }

    public double getZValue(int itemIndex) {
        return this.items.get(itemIndex).getZ();
    }
}
