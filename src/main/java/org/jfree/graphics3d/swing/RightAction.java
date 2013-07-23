/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package org.jfree.graphics3d.swing;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.jfree.graphics3d.Panel3D;
import org.jfree.graphics3d.ViewPoint3D;

/**
 * Right
 */
public class RightAction extends AbstractAction implements Action {

  private Panel3D panel;
  
  public RightAction(Panel3D panel3D) {
    super("\uF061");
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
