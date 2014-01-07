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

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import com.orsoncharts.util.ArgChecks;

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

    /**
     * Rotates the orientation of the view point and repaints the panel.
     * 
     * @param e  the action event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.panel.getViewPoint().roll(-this.panel.getRollIncrement());
        this.panel.repaint();
    }
    
}
