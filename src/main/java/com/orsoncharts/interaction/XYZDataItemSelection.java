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

import com.orsoncharts.data.xyz.XYZItemKey;

/**
 * An object representing a selection of items from an {@link XYZDataset}.
 */
public interface XYZDataItemSelection {
   
    /**
     * Returns <code>true</code> if the specified item is present in the 
     * selection, and <code>false</code> otherwise.
     * 
     * @param item  the item key (<code>null</code> not permitted).
     * 
     * @return A boolean. 
     */
    boolean isSelected(XYZItemKey item);

}
