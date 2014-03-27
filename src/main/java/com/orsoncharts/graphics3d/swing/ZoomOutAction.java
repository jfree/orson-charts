/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * Redistribution of this source file is prohibited.
 * 
 */

package com.orsoncharts.graphics3d.swing;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import com.orsoncharts.Resources;
import com.orsoncharts.graphics3d.ViewPoint3D;

/**
 * An action that performs a zoom out operation on the content in a 
 * {@link Panel3D}.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 * 
 * @see ZoomInAction
 */
@SuppressWarnings("serial")
public class ZoomOutAction extends AbstractAction {

    /** The panel that the action applies to. */
    private Panel3D panel;
  
    /** The multiplier used to calculate the new viewing distance. */
    private double zoomMultiplier;
    
    /**
     * Creates a new zoom-out action associated with the specified panel.
     * 
     * @param panel  the panel (<code>null</code> not permitted).
     * @param fontAwesome  use the FontAwesome icon text?
     */
    public ZoomOutAction(Panel3D panel, boolean fontAwesome) {
        super("\uf010");
        this.panel = panel;
        this.zoomMultiplier = 10.0 / 9.5; 
        if (!fontAwesome) {
            putValue(Action.NAME, Resources.localString("ZOOM_OUT"));
        }
        putValue(Action.ACTION_COMMAND_KEY, "ZOOM_OUT");
        putValue(Action.SHORT_DESCRIPTION, Resources.localString("ZOOM_OUT"));
    }

    /**
     * Returns the zoom multiplier.  The default value is 
     * <code>100 / 95</code> (the inverse of the multiplier in the 
     * {@link ZoomInAction}).
     * 
     * @return The zoom multiplier. 
     * 
     * @since 1.3
     */
    public double getZoomMultiplier() {
        return zoomMultiplier;
    }

    /**
     * Sets the zoom multiplier (the current viewing distance is multiplied 
     * by this factor to determine the new viewing distance).
     * 
     * @param multiplier  the new multiplier.
     * 
     * @since 1.3
     */
    public void setZoomMultiplier(double multiplier) {
        this.zoomMultiplier = multiplier;
    }

    /**
     * Performs the zoom out action.
     * 
     * @param e  the action event. 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        ViewPoint3D viewPt = this.panel.getViewPoint();
        double minDistance = this.panel.getMinViewingDistance();
        double maxDistance = minDistance 
                * this.panel.getMaxViewingDistanceMultiplier();
        double valRho = Math.max(minDistance, 
                Math.min(maxDistance, viewPt.getRho() * this.zoomMultiplier));
        this.panel.getViewPoint().setRho(valRho);
        this.panel.repaint();
    }
    
}
