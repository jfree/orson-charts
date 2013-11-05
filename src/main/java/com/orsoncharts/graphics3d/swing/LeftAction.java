/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.graphics3d.swing;

import com.orsoncharts.Chart3D;
import com.orsoncharts.graphics3d.Drawable3D;
import com.orsoncharts.graphics3d.ViewPoint3D;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 * Left.
 */
public class LeftAction extends AbstractAction {

    private Panel3D panel;
    
    //private Chart3D chart;
  
    public LeftAction(Panel3D panel3D) {
        super("\uF060");
        this.panel = panel3D;
        //.chart = chart;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Drawable3D drawable = this.panel.getDrawable();
        if (drawable instanceof Chart3D) {
            Chart3D chart = (Chart3D) drawable;
        }
        drawable.getViewPoint().moveLeftRight(Math.PI / 60);
        this.panel.repaint();
//        ViewPoint3D currVP = this.panel.getViewPoint();
//        ViewPoint3D newVP = currVP.moveLeftRight(Math.PI / 300);
//        ViewPoint3D nextVP = new ViewPoint3D(newVP.getTheta(), newVP.getPhi(), newVP.getRho(), newVP.getAngle());
//        //this.chart.nextViewPoint = nextVP.getPoint();
//        //System.out.println("Next point left = " + this.chart.nextViewPoint);
//        this.panel.setViewPoint(newVP);
//        this.panel.repaint();
    }
    
}
