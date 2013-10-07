/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.axis;

import com.orsoncharts.util.ArgChecks;
import java.util.EventObject;

/**
 * An event associated with a change to an {@link Axis3D}.
 */
public class Axis3DChangeEvent extends EventObject {
  
    /** The axis associated with this event. */
    private Axis3D axis;
  
    /**
     * Creates a new event.
     * 
     * @param axis  the axis (<code>null</code> not permitted). 
     */
    public Axis3DChangeEvent(Axis3D axis) {
        this(axis, axis);
    }
    
    /**
     * Creates a new event.
     * 
     * @param source  the event source.
     * @param axis  the axis (<code>null</code> not permitted).
     */
    public Axis3DChangeEvent(Object source, Axis3D axis) {
        super(source);
        ArgChecks.nullNotPermitted(axis, "axis");
        this.axis = axis;
    }
  
    /**
     * Returns the axis associated with this event.
     * 
     * @return The axis (never <code>null</code>). 
     */
    public Axis3D getAxis() {
        return this.axis;
    }
}
