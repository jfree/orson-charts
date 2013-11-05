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
public class UpAction extends AbstractAction {

    private Panel3D panel;
  
    public UpAction(Panel3D panel3D) {
        super("\uF062");
        this.panel = panel3D;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.panel.getViewPoint().moveUpDown(Math.PI / 60);
        panel.repaint();
    }
    
}
