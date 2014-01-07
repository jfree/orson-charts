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
import com.orsoncharts.graphics3d.ViewPoint3D;

/**
 * An action that performs a zoom out operation on the content in a 
 * {@link Panel3D}.
 */
public class ZoomOutAction extends AbstractAction {

    /** The panel that the action applies to. */
    private Panel3D panel;
  
    /**
     * Creates a new zoom-out action associated with the specified panel.
     * 
     * @param panel  the panel (<code>null</code> not permitted).
     * @param fontAwesome  use the FontAwesome icon text?
     */
    public ZoomOutAction(Panel3D panel, boolean fontAwesome) {
        super("\uf010");
        this.panel = panel;
        if (!fontAwesome) {
            putValue(Action.NAME, "Zoom Out");
        }
        putValue(Action.ACTION_COMMAND_KEY, "ZOOM_OUT");
        putValue(Action.SHORT_DESCRIPTION, "Zoom out");
    }

    /**
     * Performs the zoom out action.
     * 
     * @param e  the action event. 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        ViewPoint3D viewPt = this.panel.getViewPoint();
        double valRho = Math.max(10.0f, viewPt.getRho() + 5.0f);
        this.panel.getViewPoint().setRho(valRho);
        this.panel.repaint();
    }
    
}
