/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.graphics3d.swing;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;

/**
 * Export charts to PNG, SVG or PDF format.
 */
public class ExportAction extends AbstractAction {

    private Panel3D panel;
  
    public ExportAction(Panel3D panel3D) {
        super("\uF0C7");
        this.panel = panel3D;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ExportController controller = new ExportController(this.panel);
        try {
            controller.showExportDialog();
        } catch (IOException ex) {
            Logger.getLogger(ExportAction.class.getName()).log(Level.SEVERE, 
                    null, ex);
        }    
    }

}
