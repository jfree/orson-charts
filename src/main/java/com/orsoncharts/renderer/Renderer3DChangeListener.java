/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.orsoncharts.renderer;

import com.orsoncharts.renderer.Renderer3DChangeEvent;
import java.util.EventListener;

/**
 *
 * @author dgilbert
 */
public interface Renderer3DChangeListener extends EventListener {

    /**
     * Called to signal a change to a renderer.
     * 
     * @param event  information about the change.
     */
    public void rendererChanged(Renderer3DChangeEvent event);
    
}
