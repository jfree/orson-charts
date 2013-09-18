/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package org.jfree.graphics3d.swing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Controls for a 3D display.
 */
public class ControlPanel extends JPanel {
    
  private static final int FONT_SIZE = 18;
  
  public ControlPanel(Panel3D parent) {
    super(new BorderLayout());
    JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 2));
    JButton zoomInButton = new JButton(new ZoomInAction(parent));
    zoomInButton.setFont(Panel3D.getFontAwesomeFont(FONT_SIZE));
    JButton zoomOutButton = new JButton(new ZoomOutAction(parent));
    zoomOutButton.setFont(Panel3D.getFontAwesomeFont(FONT_SIZE));
    JButton leftButton = new JButton(new LeftAction(parent));
    leftButton.setFont(Panel3D.getFontAwesomeFont(FONT_SIZE));
    JButton rightButton = new JButton(new RightAction(parent));
    rightButton.setFont(Panel3D.getFontAwesomeFont(FONT_SIZE));
    JButton upButton = new JButton(new RotateUpAction(parent));
    upButton.setFont(Panel3D.getFontAwesomeFont(FONT_SIZE));
    JButton downButton = new JButton(new RotateDownAction(parent));
    downButton.setFont(Panel3D.getFontAwesomeFont(FONT_SIZE));
    JButton rotateLeftButton = new JButton(new RollLeftAction(parent));
    rotateLeftButton.setFont(Panel3D.getFontAwesomeFont(FONT_SIZE));
    JButton rotateRightButton = new JButton(new RollRightAction(parent));
    rotateRightButton.setFont(Panel3D.getFontAwesomeFont(FONT_SIZE));
    JButton exportButton = new JButton(new ExportAction(parent));
    exportButton.setFont(Panel3D.getFontAwesomeFont(FONT_SIZE));
    leftPanel.add(zoomInButton);
    leftPanel.add(zoomOutButton);
    leftPanel.add(leftButton);
    leftPanel.add(rightButton);
    leftPanel.add(upButton);
    leftPanel.add(downButton);
    leftPanel.add(rotateLeftButton);
    leftPanel.add(rotateRightButton);
    
    JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 2, 2));
    rightPanel.add(exportButton);
    
    add(leftPanel, BorderLayout.WEST);
    add(rightPanel, BorderLayout.EAST);

  }
}
