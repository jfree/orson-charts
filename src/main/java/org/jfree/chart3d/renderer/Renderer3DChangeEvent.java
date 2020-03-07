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

package com.orsoncharts.renderer;

import java.util.EventObject;

/**
 * An event containing information about a change to a {@link Renderer3D}.
 * Any object that implements the {@link Renderer3DChangeListener} interface
 * can register with a renderer to receive change event notifications.  By 
 * default, the plot classes register with the renderer they manage in order
 * to monitor changes to the renderer.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. 
 */
@SuppressWarnings("serial")
public class Renderer3DChangeEvent extends EventObject {

    private Renderer3D renderer;
    
    private boolean requiresWorldUpdate;
  
    /**
     * Creates a new change event.
     * 
     * @param renderer  the renderer that changed ({@code null} not permitted). 
     * @param requiresWorldUpdate  a flag indicating whether or not the change
     *     requires the 3D world to be updated.
     */
    public Renderer3DChangeEvent(Renderer3D renderer, 
            boolean requiresWorldUpdate) {
        this(renderer, renderer, requiresWorldUpdate);
    }
  
    /**
     * Creates a new change event.
     * 
     * @param source  the source.
     * @param renderer  the renderer.
     * @param requiresWorldUpdate  a flag indicating whether or not the change
     *     requires the 3D world to be updated.
     */
    public Renderer3DChangeEvent(Object source, Renderer3D renderer,
            boolean requiresWorldUpdate) {
        super(source);
        this.renderer = renderer;
        this.requiresWorldUpdate = requiresWorldUpdate;
    }
 
    /**
     * Returns the renderer that the event relates to.
     * 
     * @return The renderer. 
     */
    public Renderer3D getRenderer() {
        return this.renderer;
    }

    /**
     * Returns the flag that indicates whether or not this change will require
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
