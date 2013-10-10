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
 * Left.
 */
public class LeftAction extends AbstractAction {

    private Panel3D panel;
  
    public LeftAction(Panel3D panel3D) {
        super("\uF060");
        this.panel = panel3D;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ViewPoint3D viewPt = this.panel.getViewPoint();
        float valRho = (float) (viewPt.getRho() - (Math.PI / 30.0));
        float valTheta = viewPt.getTheta();
        float valPhi = viewPt.getPhi();
        panel.setViewPoint(new ViewPoint3D(valTheta, valPhi, valRho));
    }
    
}
