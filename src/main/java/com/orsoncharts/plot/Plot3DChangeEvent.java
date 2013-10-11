/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.plot;

import com.orsoncharts.data.Dataset3DChangeEvent;
import com.orsoncharts.util.ArgChecks;
import java.util.EventObject;

/**
 * An event used to signal a change to a {@link Plot3D}.
 */
public class Plot3DChangeEvent extends EventObject {

    /** The plot. */
    private Plot3D plot;
  
    /**
     * Creates a new event.  The <code>source</code> of the event can be
     * either the plot instance or another event that was received by the
     * plot (for example, a {@link Dataset3DChangeEvent}).
     * 
     * @param source  the event source (<code>null</code> not permitted).
     * @param plot  the plot (<code>null</code> not permitted).
     */
    public Plot3DChangeEvent(Object source, Plot3D plot) {
        super(source);
        ArgChecks.nullNotPermitted(plot, "plot");
        this.plot = plot;
    }
 
    /**
     * Returns the plot from which the event came.
     * 
     * @return The plot (never <code>null</code>). 
     */
    public Plot3D getPlot() {
        return this.plot;
    }
}
