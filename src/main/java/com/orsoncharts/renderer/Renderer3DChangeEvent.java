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

package com.orsoncharts.renderer;

import java.util.EventObject;

/**
 * An event containing information about a change to a {@link Renderer3D}.
 * Any object that implements the {@link Renderer3DChangeListener} interface
 * can register with a renderer to receive change event notifications.  By 
 * default, the plot classes register with the renderer they manage in order
 * to monitor changes to the renderer.
 */
public class Renderer3DChangeEvent extends EventObject {

    private Renderer3D renderer;
  
    /**
     * Creates a new change event.
     * 
     * @param renderer  the renderer that changed (<code>null</code> not 
     *         permitted). 
     */
    public Renderer3DChangeEvent(Renderer3D renderer) {
        this(renderer, renderer);
    }
  
    /**
     * Creates a new change event.
     * 
     * @param source  the source.
     * @param renderer  the renderer.
     */
    public Renderer3DChangeEvent(Object source, Renderer3D renderer) {
        super(source);
        this.renderer = renderer;
    }
 
    /**
     * Returns the renderer that the event relates to.
     * 
     * @return The renderer. 
     */
    public Renderer3D getRenderer() {
        return this.renderer;
    }
}
