/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package com.orsoncharts.event;

import java.util.EventListener;

/**
 * The interface through which dataset change events are passed in OrsonCharts.
 */
public interface Dataset3DChangeListener extends EventListener {

  public void datasetChanged(Dataset3DChangeEvent event);
}
