/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * Redistribution of these source files is prohibited.
 * 
 */

package com.orsoncharts.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.orsoncharts.util.ArgChecks;

/**
 * Utility methods related to the {@link KeyedValues3DItemKey} class.
 * 
 * @since 1.3
 */
public class KeyedValues3DItemKeys {
    
    private KeyedValues3DItemKeys() {
        // no need to instantiate this
    }
 
    /**
     * Returns a collection containing all the item keys for the specified
     * series.
     * 
     * @param data  the data (<code>null</code> not permitted).
     * @param seriesKey  the series key (<code>null</code> not permitted).
     * 
     * @return A collection of item keys (never <code>null</code>).
     */
    public static Collection<KeyedValues3DItemKey> itemKeysForSeries(
            KeyedValues3D data, Comparable<?> seriesKey) {
        ArgChecks.nullNotPermitted(data, "data");
        ArgChecks.nullNotPermitted(seriesKey, "seriesKey");
        Collection<KeyedValues3DItemKey> result 
                = new ArrayList<KeyedValues3DItemKey>();
        if (!data.getSeriesKeys().contains(seriesKey)) {
            return result;
        }
        for (Comparable<?> rowKey: (List<Comparable<?>>) data.getRowKeys()) {
            for (Comparable columnKey: 
                    (List<Comparable<?>>) data.getColumnKeys()) {
                KeyedValues3DItemKey key = new KeyedValues3DItemKey(seriesKey, 
                        rowKey, columnKey);
                result.add(key);
            }
        }
        return result;
    }
    
    /**
     * Returns a collection containing all the item keys for the specified
     * row.
     * 
     * @param data  the data (<code>null</code> not permitted).
     * @param rowKey  the row key (<code>null</code> not permitted).
     * 
     * @return A collection of item keys (never <code>null</code>).
     */
    public static Collection<KeyedValues3DItemKey> itemKeysForRow(
            KeyedValues3D data, Comparable<?> rowKey) {
        ArgChecks.nullNotPermitted(data, "data");
        ArgChecks.nullNotPermitted(rowKey, "rowKey");
        Collection<KeyedValues3DItemKey> result 
                = new ArrayList<KeyedValues3DItemKey>();
        if (!data.getRowKeys().contains(rowKey)) {
            return result;
        }
        for (Comparable<?> seriesKey: 
                (List<Comparable<?>>) data.getSeriesKeys()) {
            for (Comparable columnKey: (List<Comparable<?>>) 
                    data.getColumnKeys()) {
                KeyedValues3DItemKey key = new KeyedValues3DItemKey(seriesKey, 
                        rowKey, columnKey);
                result.add(key);
            }
        }
        return result;
    }
    
    /**
     * Returns a collection containing all the item keys for the specified
     * column.
     * 
     * @param data  the data (<code>null</code> not permitted).
     * @param columnKey  the column key (<code>null</code> not permitted).
     * 
     * @return A collection of item keys (never <code>null</code>).
     */
    public static Collection<KeyedValues3DItemKey> itemKeysForColumn(
            KeyedValues3D data, Comparable<?> columnKey) {
        ArgChecks.nullNotPermitted(data, "data");
        ArgChecks.nullNotPermitted(columnKey, "columnKey");
        Collection<KeyedValues3DItemKey> result 
                = new ArrayList<KeyedValues3DItemKey>();
        if (!data.getColumnKeys().contains(columnKey)) {
            return result;
        }
        for (Comparable<?> seriesKey: 
                (List<Comparable<?>>) data.getSeriesKeys()) {
            for (Comparable rowKey: (List<Comparable<?>>) data.getRowKeys()) {
                KeyedValues3DItemKey key = new KeyedValues3DItemKey(seriesKey, 
                        rowKey, columnKey);
                result.add(key);
            }
        }
        return result;
    }
    
}
