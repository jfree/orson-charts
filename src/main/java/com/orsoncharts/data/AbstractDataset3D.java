/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-2016, by Object Refinery Limited.  All rights reserved.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 * 
 * If you do not wish to be bound by the terms of the GPL, an alternative
 * commercial license can be purchased.  For details, please see visit the
 * Orson Charts home page:
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
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
     * of changes (defaults to {@code true}, but sometimes it is useful 
     * to disable this).
     */
    private boolean notify;

    /**
     * Default constructor - allocates storage for listeners that can
     * be registered with the dataset.
     */
    protected AbstractDataset3D() {
        this.listenerList = new EventListenerList();
        this.notify = true;
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
     * @see #removeChangeListener(Dataset3DChangeListener)
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
     * @see #addChangeListener(Dataset3DChangeListener)
     */
    @Override
    public void removeChangeListener(Dataset3DChangeListener listener) {
        this.listenerList.remove(Dataset3DChangeListener.class, listener);
    }

    /**
     * Returns {@code true} if the specified object is registered with
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
    @Override
    public boolean hasListener(EventListener listener) {
        List<?> list = Arrays.asList(this.listenerList.getListenerList());
        return list.contains(listener);
    }

    /**
     * Notifies all registered listeners that the dataset has changed.
     *
     * @see #addChangeListener(Dataset3DChangeListener)
     */
    protected void fireDatasetChanged() {
        notifyListeners(new Dataset3DChangeEvent(this, this));
    }

    /**
     * Notifies all registered listeners that the dataset has changed, unless
     * the {@code notify} flag is set to {@code false} in which 
     * case this method does nothing.
     *
     * @param event  contains information about the event that triggered the
     *               notification.
     *
     * @see #addChangeListener(Dataset3DChangeListener)
     * @see #removeChangeListener(Dataset3DChangeListener)
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
     * the {@code notify} flag is set to {@code false} in which 
     * case this method does nothing.
     */
    protected void fireChangeEvent() {
        notifyListeners(new Dataset3DChangeEvent(this, this));
    }

}
