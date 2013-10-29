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
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * An action that handles saving the content of a panel to a PDF file using
 * Orson PDF.
 */
public class ExportToPDFAction extends AbstractAction {

    /** The panel to which this action applies. */
    private Panel3D panel;
  
    /** 
     * Creates a new action.
     * 
     * @param panel  the panel that the action applies to (<code>null</code> 
     *     not permitted). 
     */
    public ExportToPDFAction(Panel3D panel) {
        super("PDF...");
        ArgChecks.nullNotPermitted(panel, NAME);
        this.panel = panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "PDF Files", "pdf");
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setFileFilter(filter);

        int option = fileChooser.showSaveDialog(this.panel);
        if (option == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser.getSelectedFile().getPath();
            if (!filename.endsWith(".pdf")) {
                filename = filename + ".pdf";
            }
            this.panel.writeAsPDF(new File(filename), this.panel.getWidth(), 
                    this.panel.getHeight());
        }
    }
    
}
