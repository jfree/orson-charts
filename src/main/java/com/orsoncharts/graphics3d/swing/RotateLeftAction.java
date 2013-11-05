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
 * Rotate left.
 */
public class RotateLeftAction extends AbstractAction {

    private Panel3D panel;
  
    public RotateLeftAction(Panel3D panel3D) {
        super("\uF112");
        this.panel = panel3D;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ViewPoint3D viewPt = this.panel.getDrawable().getViewPoint();
        viewPt.rotate(- Math.PI / 72);
        this.panel.repaint();
    }
    
}
