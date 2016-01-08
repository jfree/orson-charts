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

package com.orsoncharts.interaction;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import com.orsoncharts.data.KeyedValues3D;
import com.orsoncharts.data.KeyedValues3DItemKey;
import com.orsoncharts.util.ArgChecks;

/**
 * An object that tracks selected items from a {@link KeyedValues3D} dataset.
 * 
 * @since 1.3
 */
public class StandardKeyedValues3DItemSelection 
        implements KeyedValues3DItemSelection, Serializable {
    
    /** The set of selected items. */
    Set<KeyedValues3DItemKey> selectedItems;

    /**
     * Creates a new item selection instance, initially with no selections.
     */
    public StandardKeyedValues3DItemSelection() {
        this.selectedItems = new TreeSet<KeyedValues3DItemKey>();
    }
    
    /**
     * Adds an item to the selection, returning {@code true} if the item
     * is added and {@code false} if the item already existed in the
     * selection.
     * 
     * @param itemKey  the item key ({@code null} not permitted).
     * 
     * @return A boolean.
     */
    public boolean add(KeyedValues3DItemKey itemKey) {
        ArgChecks.nullNotPermitted(itemKey, "itemKey");
        return this.selectedItems.add(itemKey);
    }
    
    /**
     * Adds a collection of items to the selection, returning {@code true}
     * if the selection is changed, and {@code false} if no changes were
     * made.
     * 
     * @param keys  the keys to add ({@code null} not permitted).
     * 
     * @return A boolean.
     */
    public boolean addAll(Collection<? extends KeyedValues3DItemKey> keys) {
        ArgChecks.nullNotPermitted(keys, "keys");
        return this.selectedItems.addAll(keys);
    }
    
    /**
     * Removes an item from the selection returning {@code true} if the
     * item was removed and {@code false} if it did not exist within the
     * selection.
     * 
     * @param itemKey  the item key ({@code null} not permitted).
     * 
     * @return A boolean. 
     */
    public boolean remove(KeyedValues3DItemKey itemKey) {
        return this.selectedItems.remove(itemKey);
    }
    
    /**
     * Returns {@code true} if the specified item is in the selection,
     * and {@code false} otherwise.
     * 
     * @param itemKey  the item key ({@code null} not permitted).
     * 
     * @return A boolean.
     */
    @Override
    public boolean isSelected(KeyedValues3DItemKey itemKey) {
        return this.selectedItems.contains(itemKey);
    }
    
    /**
     * Clears the item selection (that is, removes all items contained in the
     * selection).
     */
    public void clear() {
        this.selectedItems.clear();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof StandardKeyedValues3DItemSelection)) {
            return false;
        }
        StandardKeyedValues3DItemSelection that 
                = (StandardKeyedValues3DItemSelection) obj;
        if (!this.selectedItems.equals(that.selectedItems)) {
            return false;
        }
        return true;
    }
}
