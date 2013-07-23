/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package org.jfree.graphics3d.swing;

import java.awt.FlowLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/**
 * Presents the options for exporting to PNG, SVG or PDF format.
 */
public class ExportOptionsPanel extends JPanel {

  private ButtonGroup buttonGroup;
  
  public ExportOptionsPanel() {
      super();
      buttonGroup = new ButtonGroup();
      JToggleButton pngButton = new JToggleButton("PNG");
      JToggleButton svgButton = new JToggleButton("SVG");
      JToggleButton pdfButton = new JToggleButton("PDF");
      buttonGroup.add(pngButton);
      buttonGroup.add(svgButton);
      buttonGroup.add(pdfButton);
      setLayout(new FlowLayout());
      add(pngButton);
      add(svgButton);
      add(pdfButton);
  }
}
