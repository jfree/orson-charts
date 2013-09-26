/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.renderer;

/**
 * A base interface for renderers.
 */
public interface Renderer3D {
    
    /**
     * Registers a listener to receive notification of changes to the
     * renderer.
     * 
     * @param listener  the listener (<code>null</code> not permitted).
     */
    public void addChangeListener(Renderer3DChangeListener listener);
    
    /**
     * Deregisters a listener so that it no longer receives notification of
     * changes to the renderer.
     * 
     * @param listener  the listener (<code>null</code> not permitted).
     */
    public void removeChangeListener(Renderer3DChangeListener listener);

}
