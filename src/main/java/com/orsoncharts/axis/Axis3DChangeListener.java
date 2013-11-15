/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.axis;

import java.util.EventListener;

/**
 * A listener for axis change events.  The plot classes that have axes
 * ({@link CategoryPlot3D} and {@link XYZPlot}) implement this interface so 
 * that they can receive notification when the axes are modified.
 */
public interface Axis3DChangeListener extends EventListener {
    
    /**
     * Called to inform that an axis change event has occurred.
     * 
     * @param event  the event (<code>null</code> not permitted). 
     */
    public void axisChanged(Axis3DChangeEvent event);

}
