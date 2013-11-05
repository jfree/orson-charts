/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.graphics3d.swing;

import com.orsoncharts.Chart3D;
import com.orsoncharts.graphics3d.Drawable3D;
import com.orsoncharts.graphics3d.Point3D;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import com.orsoncharts.graphics3d.ViewPoint3D;

/**
 * Right
 */
public class RightAction extends AbstractAction {

    private Panel3D panel;
    
    //private Chart3D chart;
  
    public RightAction(Panel3D panel3D) {
        super("\uF061");
        this.panel = panel3D;
        //this.chart = chart;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Drawable3D drawable = this.panel.getDrawable();
        if (drawable instanceof Chart3D) {
            Chart3D chart = (Chart3D) drawable;
            //this.panel.setViewPoint(new ViewPoint3D(new Point3D(30, -30, 30), chart.getViewPoint().getAngle(), 0));
        }
        drawable.getViewPoint().moveLeftRight(-Math.PI / 60);
        this.panel.repaint();
    }
    
}
