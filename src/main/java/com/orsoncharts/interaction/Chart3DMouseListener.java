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

import java.util.EventListener;
import com.orsoncharts.Chart3DPanel;

/**
 * The interface that must be implemented by classes that wish to receive
 * {@link Chart3DMouseEvent} notifications from a {@link Chart3DPanel}.
 *
 * @see Chart3DPanel#addChartMouseListener(com.orsoncharts.interaction.Chart3DMouseListener) 
 * 
 * @since 1.3
 */
public interface Chart3DMouseListener extends EventListener {

    /**
     * Callback method for receiving notification of a mouse click on a chart.
     *
     * @param event  information about the event.
     */
    void chartMouseClicked(Chart3DMouseEvent event);

    /**
     * Callback method for receiving notification of a mouse movement on a
     * chart.
     *
     * @param event  information about the event.
     */
    void chartMouseMoved(Chart3DMouseEvent event);

}