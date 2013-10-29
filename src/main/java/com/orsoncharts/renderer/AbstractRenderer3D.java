/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.renderer;

import javax.swing.event.EventListenerList;

import com.orsoncharts.plot.Plot3DChangeListener;

/**
 * A base class for 3D renderers.
 */
public abstract class AbstractRenderer3D implements Renderer3D {
    
    /** Storage for registered change listeners. */
    private transient EventListenerList listenerList;

    /**
     * A flag that controls whether or not the renderer will notify listeners
     * of changes (defaults to <code>true</code>, but sometimes it is useful 
     * to disable this).
     */
    private boolean notify;

    /**
     * Default constructor.
     */
    protected AbstractRenderer3D() {
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
     * {@link Renderer3DChangeEvent} notifications.
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
     * Registers an object for notification of changes to the renderer.
     *
     * @param listener  the object to be registered.
     *
     * @see #removeChangeListener(Renderer3DChangeListener)
     */
    @Override
    public void addChangeListener(Renderer3DChangeListener listener) {
        this.listenerList.add(Renderer3DChangeListener.class, listener);
    }

    /**
     * Unregisters an object for notification of changes to the renderer.
     *
     * @param listener  the object to be unregistered.
     *
     * @see #addChangeListener(Renderer3DChangeListener) 
     */
    @Override
    public void removeChangeListener(Renderer3DChangeListener listener) {
        this.listenerList.remove(Renderer3DChangeListener.class, listener);
    }

    /**
     * Notifies all registered listeners that the plot has been modified.
     *
     * @param event  information about the change event.
     */
    public void notifyListeners(Renderer3DChangeEvent event) {
        // if the 'notify' flag has been switched to false, we don't notify
        // the listeners
        if (!this.notify) {
            return;
        }
        Object[] listeners = this.listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == Plot3DChangeListener.class) { 
                ((Renderer3DChangeListener) listeners[i + 1]).rendererChanged(event);
            }
        }
    }

    /**
     * Sends a {@link Renderer3DChangeEvent} to all registered listeners.
     */
    protected void fireChangeEvent() {
        notifyListeners(new Renderer3DChangeEvent(this));
    }

    /**
     * Tests this renderer for equality with an arbitrary object.  The 
     * change listeners are NOT considered in the test, but the 
     * <code>notify</code> flag is taken into account.
     * 
     * @param obj  the object (<code>null</code> permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AbstractRenderer3D)) {
            return false;
        }
        AbstractRenderer3D that = (AbstractRenderer3D) obj;
        if (this.notify != that.notify) {
            return false;
        }
        return true;
    }
}
