/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.graphics3d.swing;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import org.jfree.graphics2d.svg.SVGGraphics2D;

/**
 * A controller class for the export option.
 */
public class ExportController implements ActionListener {
  
    /** The panel containing the content. */
    private Panel3D panel;
  
    private ExportOptionsPanel optionsPanel;
  
    public ExportController(Panel3D panel) {
        this.panel = panel;
    }
 
    public void showExportDialog() throws IOException {
        this.optionsPanel = new ExportOptionsPanel();
        this.optionsPanel.getPNGButton().addActionListener(this);
        this.optionsPanel.getPDFButton().addActionListener(this);
        this.optionsPanel.getSVGButton().addActionListener(this);
        int response = JOptionPane.showConfirmDialog(this.panel, 
                this.optionsPanel, "Export...", JOptionPane.OK_CANCEL_OPTION);
        if (response == JOptionPane.OK_OPTION) {
            String fileName = this.optionsPanel.getFileNameTextField()
                    .getText();
            if (this.optionsPanel.getPNGButton().isSelected()) {
                exportToPNG(fileName);
            }
            if (this.optionsPanel.getPDFButton().isSelected()) {
                exportToPDF(fileName);
            }
            if (this.optionsPanel.getSVGButton().isSelected()) {
                exportToSVG(fileName);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String fileName = this.optionsPanel.getFileNameTextField().getText();
        if (fileName.endsWith(".pdf") || fileName.endsWith(".png") 
                || fileName.endsWith(".svg")) {
            fileName = fileName.substring(0, fileName.length() - 4);
        }
        if (e.getActionCommand().equals("SVG_CMD")) {
            fileName = fileName + ".svg";
        }
        if (e.getActionCommand().equals("PNG_CMD")) {
            fileName = fileName + ".png";
        }
        if (e.getActionCommand().equals("PDF_CMD")) {
            fileName = fileName + ".pdf";
        }
        this.optionsPanel.getFileNameTextField().setText(fileName);
    }
  
    private void exportToPNG(String fileName) throws IOException {
        int width = this.panel.getWidth();
        int height = this.panel.getHeight();
        Rectangle imageBounds = new Rectangle(width, height);
        BufferedImage img = new BufferedImage(width, height, 
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        this.panel.getDrawable().draw(g2, imageBounds);
        ImageIO.write(img, "png", new File(fileName));
    }
  
    private void exportToPDF(String fileName) {
        int width = this.panel.getWidth();
        int height = this.panel.getHeight();
//        Rectangle pageSize = new Rectangle(width, height);
//        PDFDocument pdfDoc = new PDFDocument();
//        Page page = pdfDoc.createPage(pageSize);
//        Graphics2D g2 = page.getGraphics2D();
//        this.panel.getDrawable().draw(g2, pageSize);
//        pdfDoc.writeToFile(new File(fileName)); 
    }

    private void exportToSVG(String fileName) throws IOException {
        int width = this.panel.getWidth();
        int height = this.panel.getHeight();
        Rectangle bounds = new Rectangle(width, height);
        SVGGraphics2D g2 = new SVGGraphics2D(width, height); 
        this.panel.getDrawable().draw(g2, bounds);
        writeToSVGFile(new File(fileName), g2.getSVGElement());
    }
  
    public static void writeToHTML(File f, String svg) throws IOException {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(f));
            writer.write("<!DOCTYPE html>\n");
            writer.write("<html>\n");
            writer.write("<head>\n");
            writer.write("<title>Export Test</title>\n");
            writer.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n"); 
            writer.write("</head>\n");
            writer.write("<body>\n");
            writer.write(svg + "\n");
            writer.write("</body>\n");
            writer.write("</html>\n");
            writer.flush();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } 
    }
    
    public static void writeToSVGFile(File f, String svgElement) 
            throws IOException {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(f));
            writer.write("<?xml version=\"1.0\"?>\n");
            writer.write("<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.0//EN\" ");
            writer.write("\"http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd\">\n");
            writer.write(svgElement + "\n");
            writer.flush();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } 
    }
}
