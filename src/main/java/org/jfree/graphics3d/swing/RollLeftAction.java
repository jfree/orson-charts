/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package org.jfree.graphics3d.swing;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.jfree.graphics3d.ViewPoint3D;

/**
 * Rotate left.
 */
public class RollLeftAction extends AbstractAction implements Action {

  private Panel3D panel;
  
  public RollLeftAction(Panel3D panel3D) {
    super("\uF112");
    this.panel = panel3D;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    ViewPoint3D viewPt = this.panel.getViewPoint();
    float valRho = viewPt.getRho();
    float valTheta = (float) (viewPt.getTheta() - (Math.PI / 30.0));
    float valPhi = viewPt.getPhi();
    panel.setViewPoint(new ViewPoint3D(valTheta, valPhi, valRho));
  }
    
}
