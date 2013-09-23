/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package com.orsoncharts;

import javax.swing.event.EventListenerList;
import com.orsoncharts.event.Chart3DChangeEvent;
import com.orsoncharts.event.Chart3DChangeListener;
import com.orsoncharts.event.Plot3DChangeEvent;
import com.orsoncharts.event.Plot3DChangeListener;
import com.orsoncharts.plot.Plot3D;
import com.orsoncharts.graphics3d.ArgChecks;

/**
 * A chart object for 3D charts.
 */
public class Chart3D implements Plot3DChangeListener {

  Plot3D plot;

  /** Storage for registered change listeners. */
  private transient EventListenerList listenerList;

  /**
   * A flag that controls whether or not the chart will notify listeners
   * of changes (defaults to true, but sometimes it is useful to disable
   * this).
   */
  private boolean notify;
  
  /**
   * Creates a 3D chart for the specified plot.
   * 
   * @param plot  the plot (<code>null</code> not permitted).
   */
  public Chart3D(Plot3D plot) {
    ArgChecks.nullNotPermitted(plot, "plot");
    this.plot = plot;
    this.notify = true;
    this.listenerList = new EventListenerList();
    this.plot.addChangeListener(this);
  }

  /**
   * Returns the plot.
   *
   * @return The plot (never <code>null</code>).
   */
  public Plot3D getPlot() {
    return this.plot;
  }

  @Override
  public void plotChanged(Plot3DChangeEvent event) {
    notifyListeners(new Chart3DChangeEvent(event, this));
  }
  
  public void addChangeListener(Chart3DChangeListener listener) {
    this.listenerList.add(Chart3DChangeListener.class, listener);   
  }
  
  public void removeChangeListener(Chart3DChangeListener listener) {
    this.listenerList.remove(Chart3DChangeListener.class, listener);  
  }
  
  /**
   * Notifies all registered listeners that the chart has been modified.
   *
   * @param event  information about the change event.
   */
  public void notifyListeners(Chart3DChangeEvent event) {
    // if the 'notify' flag has been switched to false, we don't notify
    // the listeners
    if (!this.notify) {
      return;
    }
    Object[] listeners = this.listenerList.getListenerList();
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == Chart3DChangeListener.class) { 
        ((Chart3DChangeListener) listeners[i + 1]).chartChanged(event);
      }
    }
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
   * {@link PlotChangeEvent} notifications.
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
   * Sends a {@link Chart3DChangeEvent} to all registered listeners.
   */
  protected void fireChangeEvent() {
    notifyListeners(new Chart3DChangeEvent(this, this));
  }

}
