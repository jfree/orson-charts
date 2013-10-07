/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package com.orsoncharts.graphics3d.swing;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

/**
 *
 * @author dgilbert
 */
public class DisplayPanel3D extends JPanel {
  
  Panel3D content;
  
  private static final int FONT_SIZE = 22;
  
  public DisplayPanel3D(Panel3D content, boolean controls) {
    super(new BorderLayout());
    this.content = content;
    add(this.content);
    if (controls) {
      JToolBar tb = new JToolBar();
      //tb.add(new ControlPanel(this.content));
      JButton zoomInButton = new JButton(new ZoomInAction(content));
      zoomInButton.setFont(Panel3D.getFontAwesomeFont(FONT_SIZE));
      JButton zoomOutButton = new JButton(new ZoomOutAction(content));
      zoomOutButton.setFont(Panel3D.getFontAwesomeFont(FONT_SIZE));
      JButton leftButton = new JButton(new LeftAction(content));
      leftButton.setFont(Panel3D.getFontAwesomeFont(FONT_SIZE));
      JButton rightButton = new JButton(new RightAction(content));
      rightButton.setFont(Panel3D.getFontAwesomeFont(FONT_SIZE));
      JButton upButton = new JButton(new RotateUpAction(content));
      upButton.setFont(Panel3D.getFontAwesomeFont(FONT_SIZE));
      JButton downButton = new JButton(new RotateDownAction(content));
      downButton.setFont(Panel3D.getFontAwesomeFont(FONT_SIZE));
      JButton rotateLeftButton = new JButton(new RollLeftAction(content));
      rotateLeftButton.setFont(Panel3D.getFontAwesomeFont(FONT_SIZE));
      JButton rotateRightButton = new JButton(new RollRightAction(content));
      rotateRightButton.setFont(Panel3D.getFontAwesomeFont(FONT_SIZE));
      JButton exportButton = new JButton(new ExportAction(content));
      exportButton.setFont(Panel3D.getFontAwesomeFont(FONT_SIZE));
      tb.add(zoomInButton);
      tb.add(zoomOutButton);
      tb.add(new JToolBar.Separator());
      tb.add(leftButton);
      tb.add(rightButton);
      tb.add(upButton);
      tb.add(downButton);
      tb.add(rotateLeftButton);
      tb.add(rotateRightButton);
      tb.add(new JToolBar.Separator());
      tb.add(exportButton);
      
      this.add(tb, BorderLayout.SOUTH);
    }
  }
  
  public Panel3D getContent() {
    return this.content;
  }
}
