/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-present, by David Gilbert.  All rights reserved.
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
 * license is available to sponsors (higher tiers only) of the JFree projects.
 * For details, please see visit:
 *
 * https://github.com/sponsors/jfree
 * 
 */

package org.jfree.chart3d.graphics3d.swing;

import org.jfree.chart3d.internal.Args;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import org.jfree.chart3d.Resources;

/**
 * An action that handles rotating the 3D view towards the top of the screen.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. 
 */
@SuppressWarnings("serial")
public class UpAction extends AbstractAction {

    /** The panel containing the 3D content. */
    private final Panel3D panel;
  
    /**
     * Creates a new action associated with the specified panel. 
     * 
     * @param panel  the panel ({@code null} not permitted).
     */
    public UpAction(Panel3D panel) {
        super("\uF062");
        Args.nullNotPermitted(panel, "panel");
        this.panel = panel;
        putValue(Action.SHORT_DESCRIPTION, 
                Resources.localString("UP_ACTION_SHORT_DESCRIPTION"));
    }

    /**
     * Performs the rotate up action.
     * 
     * @param e  the action event. 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        double delta = this.panel.getRotateIncrement();
        this.panel.getViewPoint().moveUpDown(delta);
        this.panel.repaint();
    }
    
}
