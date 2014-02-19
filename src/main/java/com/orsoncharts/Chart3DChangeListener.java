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

package com.orsoncharts;

import java.util.EventListener;

/**
 * An interface for receiving notification of changes to a {@link Chart3D}
 * instance.
 * <br><br>
 * The {@link ChartPanel3D} class implements this interface so that it can
 * receive notification of changes to the chart being displayed in the
 * panel (whenever the chart changes, the panel is repainted).
 */
public interface Chart3DChangeListener extends EventListener {
  
    /**
     * Called to inform that a chart change event has occurred.
     * 
     * @param event  the event. 
     */
    public void chartChanged(Chart3DChangeEvent event);

}
