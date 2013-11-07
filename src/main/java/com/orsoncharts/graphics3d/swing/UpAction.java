/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.graphics3d.swing;

import com.orsoncharts.util.ArgChecks;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * An action that handles rotating the 3D view towards the top of the screen.
 */
public class UpAction extends AbstractAction {

    /** The panel containing the 3D content. */
    private Panel3D panel;
  
    /**
     * Creates a new action associated with the specified panel. 
     * 
     * @param panel  the panel (<code>null</code> not permitted).
     */
    public UpAction(Panel3D panel) {
        super("\uF062");
        ArgChecks.nullNotPermitted(panel, "panel");
        this.panel = panel;
        putValue(Action.SHORT_DESCRIPTION, "Rotate up");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        double delta = this.panel.getRotateIncrement();
        this.panel.getViewPoint().moveUpDown(delta);
        this.panel.repaint();
    }
    
}
