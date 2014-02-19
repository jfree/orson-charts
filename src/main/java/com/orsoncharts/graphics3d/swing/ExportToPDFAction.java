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
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import com.orsoncharts.Resources;
import com.orsoncharts.util.ArgChecks;

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
        super(Resources.localString("PDF_MENU_LABEL"));
        ArgChecks.nullNotPermitted(panel, NAME);
        this.panel = panel;
    }

    /**
     * Writes the content of the panel to a PDF file, using Orson PDF.
     * 
     * @param e  the event. 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                Resources.localString("PDF_FILE_FILTER_DESCRIPTION"), "pdf");
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
