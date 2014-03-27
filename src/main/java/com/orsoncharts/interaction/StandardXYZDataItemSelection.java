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

import java.util.Set;
import java.util.TreeSet;
import java.util.Collection;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.data.xyz.XYZItemKey;
import com.orsoncharts.util.ArgChecks;

/**
 * An object that tracks selected items from an {@link XYZDataset}.
 * 
 * @since 1.3
 */
public class StandardXYZDataItemSelection implements XYZDataItemSelection {
    
    /** The selected items. */
    Set<XYZItemKey> selectedItems;
    
    /**
     * Creates a new (empty) selection.
     */
    public StandardXYZDataItemSelection() {
        this.selectedItems = new TreeSet<XYZItemKey>();    
    }
    
    /**
     * Adds an item to the selection, returns <code>true</code> if the item
     * was added and <code>false</code> if it already existed in the selection.
     * 
     * @param item  the item key (<code>null</code> not permitted).
     * 
     * @return A boolean.
     */
    public boolean add(XYZItemKey item) {
        ArgChecks.nullNotPermitted(item, "item");
        return this.selectedItems.add(item);
    }
    
    /**
     * Adds all the items from the specified collection, returning 
     * <code>true</code> if the selection is updated or <code>false</code> if
     * all the supplied keys are already present in the selection.
     * 
     * @param keys  the keys (<code>null</code> not permitted).
     * 
     * @return A boolean. 
     */
    public boolean addAll(Collection<XYZItemKey> keys) {
        ArgChecks.nullNotPermitted(keys, "keys");
        return this.selectedItems.addAll(keys);
    }
    
    /**
     * Removes an item from the selection, returning <code>true</code> if the
     * item was removed and <code>false</code> if it was not present in the
     * selection.
     * 
     * @param item  the item (<code>null</code> not permitted).
     * 
     * @return A boolean. 
     */
    public boolean remove(XYZItemKey item) {
        return this.selectedItems.remove(item);
    }
    
    /**
     * Returns <code>true</code> if the item is present in the selection,
     * and <code>false</code> otherwise.
     * 
     * @param item  the item <code>null</code> not permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean isSelected(XYZItemKey item) {
        return this.selectedItems.contains(item);
    }
    
    /**
     * Clears the selection.
     */
    public void clear() {
        this.selectedItems.clear();
    }
    
}
