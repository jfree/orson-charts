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

import com.orsoncharts.Resources;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import com.orsoncharts.util.ArgChecks;

/**
 * An action that performs a zoom-to-fit operation.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. 
 */
@SuppressWarnings("serial")
public class ZoomToFitAction extends AbstractAction {

    /** The panel that the action applies to. */
    private Panel3D panel;
  
    /**
     * Creates a new action associated with the specified panel.  
     * 
     * @param panel  the panel ({@code null} not permitted).
     * @param fontAwesome  use icon? 
     */
    public ZoomToFitAction(Panel3D panel, boolean fontAwesome) {
        super("\uf065");
        ArgChecks.nullNotPermitted(panel, "panel");
        this.panel = panel;
        if (!fontAwesome) {
            putValue(Action.NAME, Resources.localString("ZOOM_TO_FIT"));
        }
        putValue(Action.ACTION_COMMAND_KEY, "ZOOM_TO_FIT");
        putValue(Action.SHORT_DESCRIPTION, Resources.localString("ZOOM_TO_FIT"));
    }

    /**
     * Performs the zoom to fit action.
     * 
     * @param e  the action event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.panel.zoomToFit();
    }
    
}

