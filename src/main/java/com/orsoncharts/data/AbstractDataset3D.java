/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.data;

import java.util.Arrays;
import java.util.EventListener;
import java.util.List;
import javax.swing.event.EventListenerList;

/**
 * A base class that can be used to create new dataset classes.
 */
public class AbstractDataset3D implements Dataset3D {
 
    /** Storage for registered change listeners. */
    private transient EventListenerList listenerList; 
  
    /**
     * A flag that controls whether or not the dataset will notify listeners
     * of changes (defaults to <code>true</code>, but sometimes it is useful 
     * to disable this).
     */
    private boolean notify;

    /**
     * Default constructor - allocates storage for listeners that can
     * be registered with the dataset.
     */
    protected AbstractDataset3D() {
        this.listenerList = new EventListenerList();  
    }
  
    /**
     * Returns a flag that controls whether or not change events are sent to
     * registered listeners.
     *
     * @return A boolean.
     *
     * @see #setNotify(boolean)
     */
    public boolean isNotify() {
        return this.notify;
    }

    /**
     * Sets a flag that controls whether or not listeners receive
     * {@link Dataset3DChangeEvent} notifications.
     *
     * @param notify  a boolean.
     *
     * @see #isNotify()
     */
    public void setNotify(boolean notify) {
        this.notify = notify;
        // if the flag is being set to true, there may be queued up changes...
        if (notify) {
            fireChangeEvent();
        }
    }

    /**
     * Registers an object to receive notification of changes to the dataset.
     *
     * @param listener  the object to register.
     *
     * @see #removeChangeListener(DatasetChangeListener)
     */
    @Override
    public void addChangeListener(Dataset3DChangeListener listener) {
        this.listenerList.add(Dataset3DChangeListener.class, listener);
    }

    /**
     * Deregisters an object so that it no longer receives notification of
     * changes to the dataset.
     *
     * @param listener  the object to deregister.
     *
     * @see #addChangeListener(DatasetChangeListener)
     */
    @Override
    public void removeChangeListener(Dataset3DChangeListener listener) {
        this.listenerList.remove(Dataset3DChangeListener.class, listener);
    }

    /**
     * Returns <code>true</code> if the specified object is registered with
     * the dataset as a listener.  Most applications won't need to call this
     * method, it exists mainly for use by unit testing code.
     *
     * @param listener  the listener.
     *
     * @return A boolean.
     *
     * @see #addChangeListener(Dataset3DChangeListener)
     * @see #removeChangeListener(Dataset3DChangeListener)
     */
    public boolean hasListener(EventListener listener) {
        List list = Arrays.asList(this.listenerList.getListenerList());
        return list.contains(listener);
    }

    /**
     * Notifies all registered listeners that the dataset has changed.
     *
     * @see #addChangeListener(DatasetChangeListener)
     */
    protected void fireDatasetChanged() {
        notifyListeners(new Dataset3DChangeEvent(this, this));
    }

    /**
     * Notifies all registered listeners that the dataset has changed, unless
     * the <code>notify</code> flag is set to <code>false</code> in which 
     * case this method does nothing.
     *
     * @param event  contains information about the event that triggered the
     *               notification.
     *
     * @see #addChangeListener(DatasetChangeListener)
     * @see #removeChangeListener(DatasetChangeListener)
     */
    protected void notifyListeners(Dataset3DChangeEvent event) {
        // if the 'notify' flag has been switched to false, we don't notify
        // the listeners
        if (!this.notify) {
            return;
        }
        Object[] listeners = this.listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == Dataset3DChangeListener.class) {
                ((Dataset3DChangeListener) listeners[i + 1])
                        .datasetChanged(event);
            }
        }
    }
    
    /**
     * Sends a {@link Dataset3DChangeEvent} to all registered listeners, unless
     * the <code>notify</code> flag is set to <code>false</code> in which 
     * case this method does nothing.
     */
    protected void fireChangeEvent() {
        notifyListeners(new Dataset3DChangeEvent(this, this));
    }

}
