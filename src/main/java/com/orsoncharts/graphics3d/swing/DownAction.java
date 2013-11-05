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
 * Rotate down.
 */
public class DownAction extends AbstractAction {

    private Panel3D panel;
  
    public DownAction(Panel3D panel3D) {
        super("\uF063");
        this.panel = panel3D;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.panel.getViewPoint().moveUpDown(-Math.PI / 60);
        this.panel.repaint();
    }
    
}
