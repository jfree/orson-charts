/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * Redistribution of this source file is prohibited.
 * 
 */

package com.orsoncharts.data.xyz;

import com.orsoncharts.data.ItemKey;
import com.orsoncharts.util.ArgChecks;

/**
 * An object that references one data item in an {@link XYZDataset}.  This is 
 * used internally to track the data item that a 3D object is related to, if
 * any (and later that link is used for chart interaction).
 * 
 * @since 1.3
 */
public class XYZItemKey implements ItemKey {
    
    /** A key identifying a series in the dataset. */
    private Comparable<?> seriesKey;
    
    /** The index of an item within a series. */
    private int itemIndex;
    
    /**
     * Creates a new instance.
     * 
     * @param seriesKey  the series key.
     * @param itemIndex  the item index.
     */
    public XYZItemKey(Comparable<?> seriesKey, int itemIndex) {
        ArgChecks.nullNotPermitted(seriesKey, "seriesKey");
        this.seriesKey = seriesKey;
        this.itemIndex = itemIndex;
    }
    
    /**
     * Returns the series key.
     * 
     * @return The series key (never <code>null</code>). 
     */
    public Comparable<?> getSeriesKey() {
        return this.seriesKey;
    }
    
    /**
     * Returns the item index.
     * 
     * @return The item index.
     */
    public int getItemIndex() {
        return this.itemIndex;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("XYZItemKey[seriesKey=");
        sb.append(this.seriesKey.toString()).append(",item=");
        sb.append(itemIndex);
        sb.append("]");
        return sb.toString();
    }
}
