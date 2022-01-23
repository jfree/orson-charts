/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-2022, by David Gilbert.  All rights reserved.
 * 
 * https://github.com/jfree/orson-charts
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

package org.jfree.chart3d.graphics3d.swing;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import org.jfree.chart3d.Resources;
import org.jfree.chart3d.graphics3d.ViewPoint3D;

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
    private final Panel3D panel;
  
    /** The multiplier used to calculate the new viewing distance. */
    private double zoomMultiplier;
    
    /**
     * Creates a new zoom-out action associated with the specified panel.
     * 
     * @param panel  the panel ({@code null} not permitted).
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
     * {@code 100 / 95} (the inverse of the multiplier in the 
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
