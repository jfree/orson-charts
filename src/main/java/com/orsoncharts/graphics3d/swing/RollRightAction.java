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
import javax.swing.Action;

import com.orsoncharts.Resources;
import com.orsoncharts.util.ArgChecks;

/**
 * An action that handles rolling the 3D view clockwise around an
 * axis from the viewing point to the origin.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. 
 */
@SuppressWarnings("serial")
public class RollRightAction extends AbstractAction {

    private Panel3D panel;
  
    /**
     * Creates a new action instance.
     * 
     * @param panel  the panel (<code>null</code> not permitted). 
     */
    public RollRightAction(Panel3D panel) {
        super("\uF064");
        ArgChecks.nullNotPermitted(panel, "panel");
        this.panel = panel;
        putValue(Action.SHORT_DESCRIPTION, 
                Resources.localString("ROLL_RIGHT_ACTION_SHORT_DESCRIPTION"));
    }

    /**
     * Rotates the orientation of the view point and repaints the panel.
     * 
     * @param e  the action event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.panel.getViewPoint().roll(this.panel.getRollIncrement());
        this.panel.repaint();
    }
    
}
