/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package com.orsoncharts.title;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

/**
 *
 * @author dgilbert
 */
public class Title {

  private String text;
  
  private Font font;
  
  private Color foreground;
  
  private Color background;
  
  private Insets margin;
  
  private ReferencePoint2D pinPt;
  
  public Title(String text) {
    this(text, new Font("SanSerif", Font.BOLD, 24));    
  }
  
  public Title(String text, Font font) {
    this.text = text;
    this.font = font;
  }
  
//  public Dimension2D getDimensions() {
//    return new Dimension2D(1.0f, 2.0f);
//  }
//  
//  public void draw(Graphics2D g2, Rectangle2D area, Insets insets, ReferencePoint2D refPt) {
//    // figure out the dimensions of this title
//    g2.setFont(this.font);
//    FontMetrics fm = g2.getFontMetrics(this.font);
//    Rectangle2D bounds = fm.getStringBounds(this.text, g2);
//    if (this.background != null) {
//      g2.setColor(this.background);
//      g2.fillRect(0, 0, width, height);
//    }
//  }
}
