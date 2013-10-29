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
import com.orsoncharts.graphics3d.ViewPoint3D;
import com.orsoncharts.util.ArgChecks;

/**
 * An action that performs a zoom-in operation.
 */
public class ZoomInAction extends AbstractAction {

    /** The panel that the action applies to. */
    private Panel3D panel;
  
    /**
     * Creates a new zoom-in action associated with the specified panel.
     * 
     * @param panel  the panel (<code>null</code> not permitted).
     */
    public ZoomInAction(Panel3D panel, boolean fontAwesome) {
        super("\uf00e");
        ArgChecks.nullNotPermitted(panel, "panel");
        this.panel = panel;
        if (!fontAwesome) {
            putValue(Action.NAME, "Zoom In");
        }
        putValue(Action.ACTION_COMMAND_KEY, "ZOOM_IN");
        putValue(Action.SHORT_DESCRIPTION, "Zoom in");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        float delta = 5.0f;
        ViewPoint3D viewPt = this.panel.getViewPoint();
        float valRho = Math.max(this.panel.getMinViewingDistance(), 
                viewPt.getRho() - delta);
        float valTheta = viewPt.getTheta();
        float valPhi = viewPt.getPhi();
        panel.setViewPoint(new ViewPoint3D(valTheta, valPhi, valRho));
    }
    
}
