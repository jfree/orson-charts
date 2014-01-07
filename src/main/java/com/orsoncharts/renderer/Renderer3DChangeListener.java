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

import java.util.EventListener;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.plot.XYZPlot;

/**
 * An interface through which notification of changes to a {@link Renderer3D}
 * can be received.  By default, a {@link CategoryPlot3D} or an {@link XYZPlot}
 * will register as a listener on the renderer that it is using (if the
 * renderer changes, the plot passes on a change event to the chart).
 */
public interface Renderer3DChangeListener extends EventListener {

    /**
     * Called to signal a change to a renderer.
     * 
     * @param event  information about the change.
     */
    void rendererChanged(Renderer3DChangeEvent event);
    
}
