/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.graphics3d.swing;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * An action that handles saving the content of a panel to a PNG image.
 */
public class ExportToPNGAction extends AbstractAction {

    /** The panel to which this action applies. */
    private Panel3D panel;
  
    public ExportToPNGAction(Panel3D panel3D) {
        super("PNG...");
        this.panel = panel3D;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        //fileChooser.setCurrentDirectory(this.defaultDirectoryForSaveAs);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "PNG Image Files", "png");
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setFileFilter(filter);

        int option = fileChooser.showSaveDialog(this.panel);
        if (option == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser.getSelectedFile().getPath();
            if (!filename.endsWith(".png")) {
                filename = filename + ".png";
            }
        }
    }
    
}
