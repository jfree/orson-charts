/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package com.orsoncharts.graphics3d.swing;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import com.orsoncharts.graphics3d.ViewPoint3D;

/**
 * Rotate right.
 */
public class RollRightAction extends AbstractAction implements Action {

  private Panel3D panel;
  
  public RollRightAction(Panel3D panel3D) {
    super("\uF064");
    this.panel = panel3D;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    ViewPoint3D viewPt = this.panel.getViewPoint();
    float valRho = viewPt.getRho();
    float valTheta = (float) (viewPt.getTheta() + (Math.PI / 30.0));
    float valPhi = viewPt.getPhi();
    panel.setViewPoint(new ViewPoint3D(valTheta, valPhi, valRho));
  }
    
}
