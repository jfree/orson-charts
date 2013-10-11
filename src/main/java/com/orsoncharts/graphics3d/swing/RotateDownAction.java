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
import com.orsoncharts.graphics3d.ViewPoint3D;

/**
 * Rotate up.
 */
public class RotateDownAction extends AbstractAction {

    private Panel3D panel;
  
    public RotateDownAction(Panel3D panel3D) {
        super("\uF063");
        this.panel = panel3D;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ViewPoint3D viewPt = this.panel.getViewPoint();
        //double cos = Math.cos(viewPt.getRotate());
        //double sin = Math.sin(viewPt.getRotate());
        float valRho = viewPt.getRho();
        float valTheta = (float) (viewPt.getTheta() + (0 * Math.PI / 30.0));
        float valPhi = (float) (viewPt.getPhi() + (1 * Math.PI / 30.0));
        viewPt.setTheta(valTheta);
        viewPt.setPhi(valPhi);
        panel.setViewPoint(viewPt);
//        panel.setViewPoint(new ViewPoint3D(valTheta, valPhi, valRho));
    }
    
}
