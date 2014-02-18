/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * Redistribution of these source files is prohibited.
 * 
 */

package com.orsoncharts.plot;

import java.util.EventObject;
import com.orsoncharts.Chart3D;
import com.orsoncharts.data.Dataset3DChangeEvent;
import com.orsoncharts.util.ArgChecks;

/**
 * An event used to signal a change to a {@link Plot3D}.  Any object that
 * implements the {@link Plot3DChangeListener} interface can register with a 
 * plot to receive change notifications.  By default, the {@link Chart3D}
 * object will register with the plot it manages to monitor changes to the plot
 * and its subcomponents.
 */
public class Plot3DChangeEvent extends EventObject {

    /** The plot. */
    private Plot3D plot;
    
    /** Does the plot change require the world to be updated? */
    private boolean requiresWorldUpdate;
  
    /**
     * Creates a new event.  The <code>source</code> of the event can be
     * either the plot instance or another event that was received by the
     * plot (for example, a {@link Dataset3DChangeEvent}).
     * 
     * @param source  the event source (<code>null</code> not permitted).
     * @param plot  the plot (<code>null</code> not permitted).
     * @param requiresWorldUpdate  a flag that indicates whether or not the 
     *     world requires updating because of this change.
     */
    public Plot3DChangeEvent(Object source, Plot3D plot, 
            boolean requiresWorldUpdate) {
        super(source);
        ArgChecks.nullNotPermitted(plot, "plot");
        this.plot = plot;
        this.requiresWorldUpdate = requiresWorldUpdate;
    }
 
    /**
     * Returns the plot from which the event came.
     * 
     * @return The plot (never <code>null</code>). 
     */
    public Plot3D getPlot() {
        return this.plot;
    }
    
    /**
     * Returns the flag indicating whether or not this change event
     * requires the world to be updated/recreated.
     * 
     * @return A boolean.
     * 
     * @since 1.2
     */
    public boolean requiresWorldUpdate() {
        return this.requiresWorldUpdate;
    }
    
}
