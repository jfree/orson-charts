/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package com.orsoncharts.plot;

import java.util.EventListener;

/**
 * An interface used to receive change events from {@link Plot3D} instances.
 */
public interface Plot3DChangeListener extends EventListener {

    public void plotChanged(Plot3DChangeEvent event);

}
