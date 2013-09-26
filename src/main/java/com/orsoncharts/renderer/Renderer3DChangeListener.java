/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.renderer;

import java.util.EventListener;

/**
 * A change listener.
 */
public interface Renderer3DChangeListener extends EventListener {

    /**
     * Called to signal a change to a renderer.
     * 
     * @param event  information about the change.
     */
    public void rendererChanged(Renderer3DChangeEvent event);
    
}
