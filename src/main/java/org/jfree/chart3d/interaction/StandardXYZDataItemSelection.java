/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-present, by David Gilbert.  All rights reserved.
 * 
 * https://github.com/jfree/orson-charts
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
 * license is available to sponsors (higher tiers only) of the JFree projects.
 * For details, please see visit:
 *
 * https://github.com/sponsors/jfree
 * 
 */

package org.jfree.chart3d.interaction;

import java.util.Set;
import java.util.TreeSet;
import java.util.Collection;
import org.jfree.chart3d.data.xyz.XYZDataset;
import org.jfree.chart3d.data.xyz.XYZItemKey;
import org.jfree.chart3d.internal.Args;

/**
 * An object that tracks selected items from an {@link XYZDataset}.
 * 
 * @param <S> S
 * 
 * @since 1.3
 */
public class StandardXYZDataItemSelection<S> implements XYZDataItemSelection {
    
    /** The selected items. */
    Set<XYZItemKey> selectedItems;
    
    /**
     * Creates a new (empty) selection.
     */
    public StandardXYZDataItemSelection() {
        this.selectedItems = new TreeSet<>();    
    }
    
    /**
     * Adds an item to the selection, returns {@code true} if the item
     * was added and {@code false} if it already existed in the selection.
     * 
     * @param item  the item key ({@code null} not permitted).
     * 
     * @return A boolean.
     */
    public boolean add(XYZItemKey item) {
        Args.nullNotPermitted(item, "item");
        return this.selectedItems.add(item);
    }
    
    /**
     * Adds all the items from the specified collection, returning 
     * {@code true} if the selection is updated or {@code false} if
     * all the supplied keys are already present in the selection.
     * 
     * @param keys  the keys ({@code null} not permitted).
     * 
     * @return A boolean. 
     */
    public boolean addAll(Collection<XYZItemKey> keys) {
        Args.nullNotPermitted(keys, "keys");
        return this.selectedItems.addAll(keys);
    }
    
    /**
     * Removes an item from the selection, returning {@code true} if the
     * item was removed and {@code false} if it was not present in the
     * selection.
     * 
     * @param item  the item ({@code null} not permitted).
     * 
     * @return A boolean. 
     */
    public boolean remove(XYZItemKey item) {
        return this.selectedItems.remove(item);
    }
    
    /**
     * Returns {@code true} if the item is present in the selection,
     * and {@code false} otherwise.
     * 
     * @param item  the item {@code null} not permitted).
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
