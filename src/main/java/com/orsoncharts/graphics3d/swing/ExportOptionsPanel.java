/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.graphics3d.swing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

/**
 * Presents the options for exporting to PNG, SVG or PDF format.
 */
public class ExportOptionsPanel extends JPanel {

    private ButtonGroup buttonGroup;
  
    private JToggleButton pngButton;
    private JToggleButton svgButton;
    private JToggleButton pdfButton;
  
    private JTextField fileNameTextField;
  
    public ExportOptionsPanel() {
        super(new BorderLayout());
        JPanel typePanel = new JPanel(new FlowLayout());
        buttonGroup = new ButtonGroup();
        this.pngButton = new JToggleButton("PNG");
        this.pngButton.setActionCommand("PNG_CMD");
        this.svgButton = new JToggleButton("SVG");
        this.svgButton.setActionCommand("SVG_CMD");
        this.pdfButton = new JToggleButton("PDF");
        this.pdfButton.setActionCommand("PDF_CMD");
        buttonGroup.add(pngButton);
        buttonGroup.add(svgButton);
        buttonGroup.add(pdfButton);
        typePanel.add(pngButton);
        typePanel.add(svgButton);
        typePanel.add(pdfButton);
        pngButton.setSelected(true);
        add(typePanel, BorderLayout.NORTH);
        JPanel content = new JPanel(new BorderLayout());
        JPanel fileNamePanel = new JPanel(new FlowLayout());
        fileNamePanel.add(new JLabel("File name:"));
        this.fileNameTextField = new JTextField(25);
        this.fileNameTextField.setText("untitled.png");
        fileNamePanel.add(this.fileNameTextField);
        fileNamePanel.add(new JButton("..."));
        content.add(fileNamePanel, BorderLayout.NORTH);
        add(content);
    }
  
    public JToggleButton getPNGButton() {
        return this.pngButton;
    }
  
    public JToggleButton getSVGButton() {
        return this.svgButton;
    }
  
    public JToggleButton getPDFButton() {
        return this.pdfButton;
    }
  
    public JTextField getFileNameTextField() {
        return this.fileNameTextField;
    }
}
