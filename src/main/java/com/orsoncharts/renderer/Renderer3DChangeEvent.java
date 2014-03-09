/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * Redistribution of this source file is prohibited.
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
     * @param renderer  the renderer that changed (<code>null</code> not 
     *         permitted). 
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
