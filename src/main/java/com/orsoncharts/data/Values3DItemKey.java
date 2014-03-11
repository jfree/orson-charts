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

package com.orsoncharts.data;

import com.orsoncharts.util.ArgChecks;

/**
 * An object that references one data item in a {@link KeyedValues3D} data
 * structure.
 * 
 * @since 1.3
 */
public class Values3DItemKey implements ItemKey {
    
    /** The series key. */
    Comparable<? extends Object> seriesKey;
    
    /** The row key. */
    Comparable<? extends Object> rowKey;
    
    /** The column key. */
    Comparable<? extends Object> columnKey;
    
    /**
     * Creates a new instance.
     * 
     * @param seriesKey  the series key (<code>null</code> not permitted).
     * @param rowKey  the row key (<code>null</code> not permitted).
     * @param columnKey  the column key (<code>null</code> not permitted).
     */
    public Values3DItemKey(Comparable<? extends Object> seriesKey,
            Comparable<? extends Object> rowKey,
            Comparable<? extends Object> columnKey) {
        ArgChecks.nullNotPermitted(seriesKey, "seriesKey");
        ArgChecks.nullNotPermitted(rowKey, "rowKey");
        ArgChecks.nullNotPermitted(columnKey, "columnKey");
        this.seriesKey = seriesKey;
        this.rowKey = rowKey;
        this.columnKey = columnKey;
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
     * Returns the row key.
     * 
     * @return The row key (never <code>null</code>).
     */
    public Comparable<?> getRowKey() {
        return this.rowKey;
    }
    
    /**
     * Returns the column key.
     * 
     * @return The column key (never <code>null</code>). 
     */
    public Comparable<?> getColumnKey() {
        return this.columnKey;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Values3DItemKey[series=");
        sb.append(seriesKey.toString()).append(",row=");
        sb.append(rowKey.toString()).append(",column=");
        sb.append(columnKey.toString());
        sb.append("]");
        return sb.toString();
    }

}
