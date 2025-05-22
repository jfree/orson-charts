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

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jfree.chart3d.Resources;
import org.jfree.chart3d.export.ExportUtils;
import org.jfree.chart3d.internal.Args;

/**
 * An action that handles saving the content of a panel to a Scalable Vector
 * Graphics (SVG) file using JFreeSVG.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. 
 */
@SuppressWarnings("serial")
public class ExportToSVGAction extends AbstractAction {

    /** The panel to which this action applies. */
    private final Panel3D panel;
  
    /** 
     * Creates a new action.
     * 
     * @param panel  the panel that the action applies to ({@code null} 
     *     not permitted). 
     */
    public ExportToSVGAction(Panel3D panel) {
        super(Resources.localString("SVG_MENU_LABEL"));
        Args.nullNotPermitted(panel, NAME);
        this.panel = panel;
    }

    /**
     * Writes the content of the panel to an SVG file, using JFreeSVG.
     * 
     * @param e  the event. 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                Resources.localString("SVG_FILE_FILTER_DESCRIPTION"), "svg");
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setFileFilter(filter);

        int option = fileChooser.showSaveDialog(this.panel);
        if (option == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser.getSelectedFile().getPath();
            if (!filename.endsWith(".svg")) {
                filename = filename + ".svg";
            }
            ExportUtils.writeAsSVG(this.panel.getDrawable(), this.panel.getWidth(),
                    this.panel.getHeight(), new File(filename));
        }
    }
    
}
