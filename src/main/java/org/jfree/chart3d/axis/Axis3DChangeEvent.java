/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-present, by David Gilbert.  All rights reserved.
 * 
 * https://github.com/jfree/orson-charts
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
 * license is available to sponsors (higher tiers only) of the JFree projects.
 * For details, please see visit:
 *
 * https://github.com/sponsors/jfree
 * 
 */

package org.jfree.chart3d.axis;

import java.util.EventObject;

import org.jfree.chart3d.internal.Args;

/**
 * An event associated with a change to an {@link Axis3D}.  These change 
 * events will be generated by an axis and broadcast to the plot that owns the
 * axis (in the standard setup, the plot will then trigger its own change event
 * to notify the chart that a subcomponent of the plot has changed). 
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. 
 */
@SuppressWarnings("serial")
public class Axis3DChangeEvent extends EventObject {
  
    /** The axis associated with this event. */
    private Axis3D axis;
  
    /** 
     * A flag indicating whether the change requires the 3D world to 
     * be updated.
     */
    private boolean requiresWorldUpdate;
    
    /**
     * Creates a new event.
     * 
     * @param axis  the axis ({@code null} not permitted).
     * @param requiresWorldUpdate  a flag indicating whether this change
     *     requires the 3D world to be updated.
     */
    public Axis3DChangeEvent(Axis3D axis, boolean requiresWorldUpdate) {
        this(axis, axis, requiresWorldUpdate);
    }
    
    /**
     * Creates a new event.
     * 
     * @param source  the event source.
     * @param axis  the axis ({@code null} not permitted).
     * @param requiresWorldUpdate  a flag indicating whether this change
     *     requires the 3D world to be updated.
     */
    public Axis3DChangeEvent(Object source, Axis3D axis, 
            boolean requiresWorldUpdate) {
        super(source);
        Args.nullNotPermitted(axis, "axis");
        this.axis = axis;
        this.requiresWorldUpdate = requiresWorldUpdate;
    }
  
    /**
     * Returns the axis associated with this event.
     * 
     * @return The axis (never {@code null}). 
     */
    public Axis3D getAxis() {
        return this.axis;
    }

    /**
     * Returns the flag that indicates whether this change will require
     * the 3D world to be updated.
     * 
     * @return A boolean.
     * 
     * @since 1.2
     */
    public boolean requiresWorldUpdate() {
        return this.requiresWorldUpdate;
    }
}
