/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.graphics3d.swing;

import com.orsoncharts.util.ArgChecks;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * An action that handles saving the content of a panel to a Scalable Vector
 * Graphics (SVG) file using JFreeSVG.
 */
public class ExportToSVGAction extends AbstractAction {

    /** The panel to which this action applies. */
    private Panel3D panel;
  
    /** 
     * Creates a new action.
     * 
     * @param panel  the panel that the action applies to (<code>null</code> 
     *     not permitted). 
     */
    public ExportToSVGAction(Panel3D panel) {
        super("SVG...");
        ArgChecks.nullNotPermitted(panel, NAME);
        this.panel = panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "SVG Files", "svg");
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setFileFilter(filter);

        int option = fileChooser.showSaveDialog(this.panel);
        if (option == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser.getSelectedFile().getPath();
            if (!filename.endsWith(".svg")) {
                filename = filename + ".svg";
            }
            this.panel.writeAsSVG(new File(filename), this.panel.getWidth(), 
                    this.panel.getHeight());
        }
    }
    
}
