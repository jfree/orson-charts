/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-2016, by Object Refinery Limited.  All rights reserved.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 * 
 * If you do not wish to be bound by the terms of the GPL, an alternative
 * commercial license can be purchased.  For details, please see visit the
 * Orson Charts home page:
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 */

package com.orsoncharts.graphics3d.swing;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import com.orsoncharts.Resources;
import com.orsoncharts.graphics3d.ViewPoint3D;
import com.orsoncharts.util.ArgChecks;

/**
 * An action that performs a zoom-in operation.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 * 
 * @see ZoomOutAction
 */
@SuppressWarnings("serial")
public class ZoomInAction extends AbstractAction {

    /** The panel that the action applies to. */
    private Panel3D panel;
  
    private double zoomMultiplier;
    
    /**
     * Creates a new zoom-in action associated with the specified panel.
     * 
     * @param panel  the panel ({@code null} not permitted).
     * @param fontAwesome  if {@code true} an icon from Font Awesome is 
     *     used for the action label, otherwise a regular text label is used.
     */
    public ZoomInAction(Panel3D panel, boolean fontAwesome) {
        super("\uf00e");
        ArgChecks.nullNotPermitted(panel, "panel");
        this.panel = panel;
        this.zoomMultiplier = 0.95; 
        if (!fontAwesome) {
            putValue(Action.NAME, Resources.localString("ZOOM_IN"));
        }
        putValue(Action.ACTION_COMMAND_KEY, "ZOOM_IN");
        putValue(Action.SHORT_DESCRIPTION, Resources.localString("ZOOM_IN"));
    }

    /**
     * Returns the zoom multiplier.  The default value is {@code 95 / 100} 
     * (the inverse of the multiplier in the {@link ZoomOutAction}).
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
     * Performs the zoom in action.
     * 
     * @param e the action event.
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
