/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package org.jfree.graphics3d.swing;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import org.jfree.graphics2d.demo.SVGBarChartDemo1;
import org.jfree.graphics2d.pdf.PDFDocument;
import org.jfree.graphics2d.pdf.PDFGraphics2D;
import org.jfree.graphics2d.pdf.Page;
import org.jfree.graphics3d.Panel3D;

/**
 * Export charts to PNG, SVG or PDF format.
 */
public class ExportAction extends AbstractAction implements Action {

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
      //    JFileChooser jfc = new JFileChooser();
      //    jfc.setFileFilter(new SVGFileFilter());
      //    int option = jfc.showSaveDialog(this.panel);
      //    if (option == JFileChooser.APPROVE_OPTION) {
      //        System.out.println("You chose " + jfc.getSelectedFile());
      //        PDFDocument pdfDoc = new PDFDocument();
      //        Page page = pdfDoc.createPage(new Rectangle(0, 0, this.panel.getWidth(), this.panel.getHeight()));
      //        PDFGraphics2D g2 = page.getGraphics2D();
      ////        SVGGraphics2D g2 = new SVGGraphics2D(this.panel.getWidth(), this.panel.getHeight());
      ////        g2.setGeometryDP(0);
      ////        g2.setTransformDP(0);
      //        panel.drawContent(g2);
      //        pdfDoc.writeToFile(jfc.getSelectedFile());
      ////        try {
      ////            writeToHTML(jfc.getSelectedFile(), g2.getSVG());
      ////        } catch (IOException ex) {
      ////            Logger.getLogger(ExportAction.class.getName()).log(Level.SEVERE, null, ex);
      //    }
      //    }
      } catch (IOException ex) {
          Logger.getLogger(ExportAction.class.getName()).log(Level.SEVERE, null, ex);
      }
    
  }
  


}
