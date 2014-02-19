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

package com.orsoncharts.graphics3d.swing;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import com.orsoncharts.util.ArgChecks;
import javax.swing.Action;

/**
 * An action that handles rotating the 3D view towards the bottom of the screen.
 */
public class DownAction extends AbstractAction {

    private Panel3D panel;
  
    /**
     * Creates a new action associated with the specified panel. 
     * 
     * @param panel  the panel (<code>null</code> not permitted).
     */
    public DownAction(Panel3D panel) {
        super("\uF063");
        ArgChecks.nullNotPermitted(panel, "panel");
        this.panel = panel;
        putValue(Action.SHORT_DESCRIPTION, "Rotate down");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        double delta = this.panel.getRotateIncrement();
        this.panel.getViewPoint().moveUpDown(-delta);
        this.panel.repaint();
    }
    
}
