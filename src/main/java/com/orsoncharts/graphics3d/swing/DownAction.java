/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.graphics3d.swing;

import com.orsoncharts.util.ArgChecks;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 * An action that handles rotating the 3D view towards the bottom of the screen.
 */
public class DownAction extends AbstractAction {

    private Panel3D panel;
  
    /**
     * Creates a new action.
     * 
     * @param panel  the panel (<code>null</code> not permitted).
     */
    public DownAction(Panel3D panel) {
        super("\uF063");
        ArgChecks.nullNotPermitted(panel, "panel");
        this.panel = panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.panel.getViewPoint().moveUpDown(-Math.PI / 60);
        this.panel.repaint();
    }
    
}
