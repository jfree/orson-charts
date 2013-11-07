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
 * An action that handles rolling the 3D view counter-clockwise around an
 * axis from the viewing point to the origin.
 */
public class RollLeftAction extends AbstractAction {

    private Panel3D panel;

    /**
     * Creates a new action instance.
     * 
     * @param panel  the panel (<code>null</code> not permitted). 
     */
    public RollLeftAction(Panel3D panel) {
        super("\uF112");
        ArgChecks.nullNotPermitted(panel, "panel");
        this.panel = panel;
        putValue(Action.SHORT_DESCRIPTION, "Roll counter-clockwise");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.panel.getViewPoint().roll(- Math.PI / 72);  // FIXME : get this value from somewhere configurable
        this.panel.repaint();
    }
    
}
