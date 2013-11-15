/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.data;

import java.util.EventListener;

/**
 * The interface through which dataset change events are passed.  The plot
 * classes implement this interface so they can receive notification of 
 * changes to the dataset they are managing.
 */
public interface Dataset3DChangeListener extends EventListener {

    /**
     * Called to notify the listener that the source dataset has been
     * changed.
     * 
     * @param event  the event details. 
     */
    public void datasetChanged(Dataset3DChangeEvent event);

}
