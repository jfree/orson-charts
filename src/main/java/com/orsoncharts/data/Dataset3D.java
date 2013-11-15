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
 * The base interface for datasets in Orson Charts.  All datasets must support
 * the change event notification mechanism.  The idea is that when a dataset
 * is changed, an event is passed to the plot.  The plot can react to this
 * event, then pass on the change event to the chart.  The chart in turn will
 * pass on the event and this can result in the chart being repainted (for
 * example, if the chart is displayed in a
 * {@link com.orsoncharts.ChartPanel3D}).
 */
public interface Dataset3D {

    /**
     * Registers a change listener to receive notification of changes to the
     * dataset.
     * 
     * @param listener  the listener (<code>null</code> not permitted). 
     */
    void addChangeListener(Dataset3DChangeListener listener);  
  
    /**
     * De-registers a change listener so that it no longer receives notification
     * of changes to the dataset.
     * 
     * @param listener  the listener (<code>null</code> not permitted). 
     */
    void removeChangeListener(Dataset3DChangeListener listener);  

    /**
     * Returns <code>true</code> if the specified listener is registered with
     * the dataset, and <code>false</code> otherwise.  This method is used
     * for unit testing to confirm that listeners are removed correctly 
     * following dataset switches.
     * 
     * @param listener  the listener.
     * 
     * @return A boolean. 
     */
    boolean hasListener(EventListener listener);
}
