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
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. 
 */
@SuppressWarnings("serial")
public class Plot3DChangeEvent extends EventObject {

    /** The plot. */
    private Plot3D plot;
    
    /** Does the plot change require the world to be updated? */
    private boolean requiresWorldUpdate;
  
    /**
     * Creates a new event.  The {@code source} of the event can be
     * either the plot instance or another event that was received by the
     * plot (for example, a {@link Dataset3DChangeEvent}).
     * 
     * @param source  the event source ({@code null} not permitted).
     * @param plot  the plot ({@code null} not permitted).
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
     * @return The plot (never {@code null}). 
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
