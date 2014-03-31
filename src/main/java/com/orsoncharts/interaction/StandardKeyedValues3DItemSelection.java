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

package com.orsoncharts.interaction;

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
        implements KeyedValues3DItemSelection {
    
    /** The set of selected items. */
    Set<KeyedValues3DItemKey> selectedItems;

    /**
     * Creates a new item selection instance, initially with no selections.
     */
    public StandardKeyedValues3DItemSelection() {
        this.selectedItems = new TreeSet<KeyedValues3DItemKey>();
    }
    
    /**
     * Adds an item to the selection, returning <code>true</code> if the item
     * is added and <code>false</code> if the item already existed in the
     * selection.
     * 
     * @param itemKey  the item key (<code>null</code> not permitted).
     * 
     * @return A boolean.
     */
    public boolean add(KeyedValues3DItemKey itemKey) {
        ArgChecks.nullNotPermitted(itemKey, "itemKey");
        return this.selectedItems.add(itemKey);
    }
    
    /**
     * Adds a collection of items to the selection, returning <code>true</code>
     * if the selection is changed, and <code>false</code> if no changes were
     * made.
     * 
     * @param keys  the keys to add (<code>null</code> not permitted).
     * 
     * @return A boolean.
     */
    public boolean addAll(Collection<? extends KeyedValues3DItemKey> keys) {
        ArgChecks.nullNotPermitted(keys, "keys");
        return this.selectedItems.addAll(keys);
    }
    
    /**
     * Removes an item from the selection returning <code>true</code> if the
     * item was removed and <code>false</code> if it did not exist within the
     * selection.
     * 
     * @param itemKey  the item key (<code>null</code> not permitted).
     * 
     * @return A boolean. 
     */
    public boolean remove(KeyedValues3DItemKey itemKey) {
        return this.selectedItems.remove(itemKey);
    }
    
    /**
     * Returns <code>true</code> if the specified item is in the selection,
     * and <code>false</code> otherwise.
     * 
     * @param itemKey  the item key (<code>null</code> not permitted).
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

}
