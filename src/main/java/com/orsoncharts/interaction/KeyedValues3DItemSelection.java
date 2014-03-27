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

import com.orsoncharts.data.Values3DItemKey;

/**
 * An object representing a select of selected items for a {@linkKeyedValues3D}
 * dataset.
 * 
 * @since 1.3
 */
public interface KeyedValues3DItemSelection {
    
    /**
     * Returns <code>true</code> if the specified key exists in the selection,
     * and <code>false</code> otherwise.
     * 
     * @param itemKey  the item key (<code>null</code> not permitted).
     * 
     * @return A boolean. 
     */
    boolean isSelected(Values3DItemKey itemKey);

}
