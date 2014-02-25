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

package com.orsoncharts.marker;

import java.util.EventListener;

/**
 * A listener for marker change events.
 * 
 * @since 1.2
 */
public interface MarkerChangeListener extends EventListener {
    
    /**
     * Called to inform that an marker change event has occurred.
     * 
     * @param event  the event (<code>null</code> not permitted). 
     */
    public void markerChanged(MarkerChangeEvent event);

}
