/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package com.orsoncharts.graphics3d.swing;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import com.orsoncharts.graphics3d.ViewPoint3D;

/**
 * Rotate up.
 */
public class RotateDownAction extends AbstractAction implements Action {

  private Panel3D panel;
  
  public RotateDownAction(Panel3D panel3D) {
    super("\uF063");
    this.panel = panel3D;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    ViewPoint3D viewPt = this.panel.getViewPoint();
    float valRho = viewPt.getRho();
    float valTheta = viewPt.getTheta();
    float valPhi = (float) (viewPt.getPhi() + (Math.PI / 30.0));
    panel.setViewPoint(new ViewPoint3D(valTheta, valPhi, valRho));
  }
    
}
