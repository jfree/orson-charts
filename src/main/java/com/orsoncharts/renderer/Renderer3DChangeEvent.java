/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.renderer;

import java.util.EventObject;

/**
 * An event containing information about a change to a {@link Renderer3D}.
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
