/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.graphics3d.swing;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import com.orsoncharts.util.ArgChecks;

/**
 * An action that performs a zoom-to-fit operation.
 */
public class ZoomToFitAction extends AbstractAction {

    /** The panel that the action applies to. */
    private Panel3D panel;
  
    /**
     * Creates a new action associated with the specified panel.  
     * 
     * @param panel  the panel (<code>null</code> not permitted).
     * @param fontAwesome  use icon? 
     */
    public ZoomToFitAction(Panel3D panel, boolean fontAwesome) {
        super("\uf065");
        ArgChecks.nullNotPermitted(panel, "panel");
        this.panel = panel;
        if (!fontAwesome) {
            putValue(Action.NAME, "Zoom To Fit");
        }
        putValue(Action.ACTION_COMMAND_KEY, "ZOOM_TO_FIT");
        putValue(Action.SHORT_DESCRIPTION, "Zoom to fit");
    }

    /**
     * Performs the zoom to fit action.
     * 
     * @param e  the action event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.panel.zoomToFit();
    }
    
}

