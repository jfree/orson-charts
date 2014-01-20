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

package com.orsoncharts.style;

import java.util.EventListener;
import com.orsoncharts.Chart3D;

/**
 * An interface for receiving notification of changes to a {@link ChartStyle}
 * instance.
 * <br><br>
 * The {@link Chart3D} class implements this interface so that it can
 * receive notification of changes to the chart style.
 * 
 * @since 1.2
 */
public interface ChartStyleChangeListener extends EventListener {
  
    /**
     * Called to inform that a chart change event has occurred.
     * 
     * @param event  the event. 
     */
    public void styleChanged(ChartStyleChangeEvent event);

}
