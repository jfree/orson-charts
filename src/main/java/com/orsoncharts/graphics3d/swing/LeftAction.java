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

package com.orsoncharts.graphics3d.swing;

import com.orsoncharts.util.ArgChecks;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * An action that handles rotating the 3D view towards the left of the screen.
 * 
 * @see RightAction
 */
public class LeftAction extends AbstractAction {

    private Panel3D panel;
  
    /**
     * Creates a new action instance.
     * 
     * @param panel  the panel (<code>null</code> not permitted).
     */
    public LeftAction(Panel3D panel) {
        super("\uF060");
        ArgChecks.nullNotPermitted(panel, "panel");
        this.panel = panel;
        putValue(Action.SHORT_DESCRIPTION, "Rotate to the left");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.panel.panLeftRight(this.panel.getPanIncrement());
    }
    
}
