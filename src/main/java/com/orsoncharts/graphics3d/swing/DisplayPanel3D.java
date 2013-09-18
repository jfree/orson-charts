/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package com.orsoncharts.graphics3d.swing;

import java.awt.BorderLayout;
import javax.swing.JPanel;

/**
 *
 * @author dgilbert
 */
public class DisplayPanel3D extends JPanel {
  
  Panel3D content;
  
  public DisplayPanel3D(Panel3D content, boolean controls) {
    super(new BorderLayout());
    this.content = content;
    add(this.content);
    if (controls) {
      this.add(new ControlPanel(this.content), BorderLayout.SOUTH);
    }
  }
  
  public Panel3D getContent() {
    return this.content;
  }
}
