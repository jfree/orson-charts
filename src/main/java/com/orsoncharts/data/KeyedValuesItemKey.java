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

/**
 * An object that references one data item in a {@link KeyedValues3D} data
 * structure.
 * 
 * @since 1.3
 */
public class KeyedValuesItemKey implements ItemKey, Serializable {
    
    /** The key for the item. */
    Comparable<? extends Object> key;
    
    /**
     * Creates a new instance.
     * 
     * @param key  the key ({@code null} not permitted).
     */
    public KeyedValuesItemKey(Comparable<? extends Object> key) {
        ArgChecks.nullNotPermitted(key, "key");
        this.key = key;
    }
    
    /**
     * Returns the key.
     * 
     * @return The key (never {@code null}). 
     */
    public Comparable<?> getKey() {
        return this.key;
    }
    
    /**
     * Tests this instance for equality with an arbitrary object.
     * 
     * @param obj  the object ({@code null} not permitted).
     * 
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof KeyedValuesItemKey)) {
            return false;
        }
        KeyedValuesItemKey that = (KeyedValuesItemKey) obj;
        if (!this.key.equals(that.key)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toJSONString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"key\": \"").append(this.key.toString()).append("\"}");
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ValuesItemKey[");
        sb.append(this.key.toString());
        sb.append("]");
        return sb.toString();
    }
}