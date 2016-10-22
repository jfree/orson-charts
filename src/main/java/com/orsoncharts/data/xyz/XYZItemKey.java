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

package com.orsoncharts.data.xyz;

import java.io.Serializable;

import com.orsoncharts.data.ItemKey;
import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.util.ObjectUtils;

/**
 * An object that references one data item in an {@link XYZDataset}.  This is 
 * used internally to track the data item that a 3D object is related to, if
 * any (and later that link is used for chart interaction).  Instances of
 * this class are immutable.
 * 
 * @param <S> The series key type.
 * 
 * @since 1.3
 */
public class XYZItemKey<S extends Comparable<S>> implements ItemKey, 
        Comparable<XYZItemKey<S>>, Serializable {
    
    /** A key identifying a series in the dataset. */
    private final S seriesKey;
    
    /** The index of an item within a series. */
    private final int itemIndex;
    
    /**
     * Creates a new instance.
     * 
     * @param seriesKey  the series key ({@code null} not permitted).
     * @param itemIndex  the item index.
     */
    public XYZItemKey(S seriesKey, int itemIndex) {
        ArgChecks.nullNotPermitted(seriesKey, "seriesKey");
        this.seriesKey = seriesKey;
        this.itemIndex = itemIndex;
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
     * Returns the item index.
     * 
     * @return The item index.
     */
    public int getItemIndex() {
        return this.itemIndex;
    }
    
    /**
     * Tests this instance for equality with an arbitrary object.
     * 
     * @param obj  the object to test ({@code null} permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof XYZItemKey)) {
            return false;
        }
        XYZItemKey that = (XYZItemKey) obj;
        if (!this.seriesKey.equals(that.seriesKey)) {
            return false;
        }
        if (this.itemIndex != that.itemIndex) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + ObjectUtils.hashCode(this.seriesKey);
        hash = 41 * hash + this.itemIndex;
        return hash;
    }

    @Override
    public String toJSONString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"seriesKey\": \"").append(this.seriesKey.toString());
        sb.append("\", ");
        sb.append("\"itemIndex\": ").append(this.itemIndex).append("}");
        return sb.toString();
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

    @Override
    public int compareTo(XYZItemKey<S> key) {
        int result = this.seriesKey.compareTo(key.seriesKey);
        if (result == 0) {
            result = this.itemIndex - key.itemIndex;
        }
        return result;
    }

}
