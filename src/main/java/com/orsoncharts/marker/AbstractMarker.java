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

import com.orsoncharts.Chart3DChangeListener;
import javax.swing.event.EventListenerList;

/**
 * A base class for implementing markers (includes the event notification 
 * mechanism).
 * 
 * @since 1.2
 */
public abstract class AbstractMarker implements Marker {
    
    /** Storage for registered change listeners. */
    private transient EventListenerList listenerList;
    
    /**
     * Default constructor.
     */
    AbstractMarker() {
        this.listenerList = new EventListenerList();
    }

    /**
     * Registers a listener to receive notification of changes to the marker.
     * 
     * @param listener  the listener (<code>null</code> not permitted). 
     */
    @Override
    public void addChangeListener(MarkerChangeListener listener) {
        this.listenerList.add(MarkerChangeListener.class, listener);
    }
    
    /**
     * Deregisters a listener so that it no longer receives notification of 
     * changes to the marker.
     * 
     * @param listener  the listener (<code>null</code> not permitted). 
     */
    @Override
    public void removeChangeListener(MarkerChangeListener listener) {
        this.listenerList.remove(MarkerChangeListener.class, listener);        
    }
    
    /**
     * Sends a {@link MarkerChangeEvent} to all registered listeners.
     */
    protected void fireChangeEvent() {
        Object[] listeners = this.listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == Chart3DChangeListener.class) { 
                ((MarkerChangeListener) listeners[i + 1]).markerChanged(
                        new MarkerChangeEvent(this, this));
            }
        }
    }
    
}
