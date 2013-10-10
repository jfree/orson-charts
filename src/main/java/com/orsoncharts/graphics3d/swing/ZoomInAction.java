/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.graphics3d.swing;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import com.orsoncharts.graphics3d.ViewPoint3D;

/**
 * Zoom in.
 */
public class ZoomInAction extends AbstractAction {

    private Panel3D panel;
  
    public ZoomInAction(Panel3D panel3D) {
        super("\uf00e");
        this.panel = panel3D;
        putValue(Action.ACTION_COMMAND_KEY, "ZOOM_IN");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ViewPoint3D viewPt = this.panel.getViewPoint();
        float valRho = Math.max(10.0f, viewPt.getRho() - 5.0f);
        float valTheta = viewPt.getTheta();
        float valPhi = viewPt.getPhi();
        panel.setViewPoint(new ViewPoint3D(valTheta, valPhi, valRho));
    }
    
}
