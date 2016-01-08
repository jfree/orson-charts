/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-2016, by Object Refinery Limited.  All rights reserved.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 * 
 * If you do not wish to be bound by the terms of the GPL, an alternative
 * commercial license can be purchased.  For details, please see visit the
 * Orson Charts home page:
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 */

package com.orsoncharts.data;

import java.io.Serializable;

import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.util.ObjectUtils;

/**
 * An object that references one data item in a {@link KeyedValues3D} data
 * structure.  Instances of this class are immutable (subject to the caller
 * using series, row and column keys that are immutable).
 * 
 * @since 1.3
 */
public class KeyedValues3DItemKey<S extends Comparable<S>, 
        R extends Comparable<R>, C extends Comparable<C>> 
        implements ItemKey, Comparable<KeyedValues3DItemKey<S, R, C>>, 
        Serializable {
    
    /** The series key. */
    S seriesKey;
    
    /** The row key. */
    R rowKey;
    
    /** The column key. */
    C columnKey;
    
    /**
     * Creates a new instance.
     * 
     * @param seriesKey  the series key ({@code null} not permitted).
     * @param rowKey  the row key ({@code null} not permitted).
     * @param columnKey  the column key ({@code null} not permitted).
     */
    public KeyedValues3DItemKey(S seriesKey, R rowKey, C columnKey) {
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
     * @return The series key (never {@code null}). 
     */
    public S getSeriesKey() {
        return this.seriesKey;
    }
    
    /**
     * Returns the row key.
     * 
     * @return The row key (never {@code null}).
     */
    public R getRowKey() {
        return this.rowKey;
    }
    
    /**
     * Returns the column key.
     * 
     * @return The column key (never {@code null}). 
     */
    public C getColumnKey() {
        return this.columnKey;
    }
    
    @Override
    public int compareTo(KeyedValues3DItemKey<S, R, C> key) {
        int result = this.seriesKey.compareTo(key.getSeriesKey());
        if (result == 0) {
            result = this.rowKey.compareTo(key.rowKey);
            if (result == 0) {
                result = this.columnKey.compareTo(key.columnKey);
            }
        }
        return result;
    }
    
    /**
     * Tests this key for equality with an arbitrary object.
     * 
     * @param obj  the object ({@code null} permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof KeyedValues3DItemKey)) {
            return false;
        }
        KeyedValues3DItemKey that = (KeyedValues3DItemKey) obj;
        if (!this.seriesKey.equals(that.seriesKey)) {
            return false;
        }
        if (!this.rowKey.equals(that.rowKey)) {
            return false;
        }
        if (!this.columnKey.equals(that.columnKey)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + ObjectUtils.hashCode(this.seriesKey);
        hash = 17 * hash + ObjectUtils.hashCode(this.rowKey);
        hash = 17 * hash + ObjectUtils.hashCode(this.columnKey);
        return hash;
    }

    @Override
    public String toJSONString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"seriesKey\": \"").append(this.seriesKey.toString());
        sb.append("\", ");
        sb.append("\"rowKey\": \"").append(this.rowKey.toString());
        sb.append("\", ");
        sb.append("\"columnKey\": \"").append(this.columnKey.toString());
        sb.append("\"}");
        return sb.toString();
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
