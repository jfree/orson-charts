/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package org.jfree.chart3d.plot;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jfree.chart3d.data.PieDataset3D;
import org.jfree.graphics3d.Dot3D;
import org.jfree.graphics3d.Object3D;
import org.jfree.graphics3d.World;

/**
 * A pie plot in 3D.
 */
public class PiePlot3D implements Plot3D {

  /** The dataset. */
  private PieDataset3D dataset;

  /** The radius of the pie chart. */
  private double radius; 
  
  /** The depth of the pie chart. */
  private double depth;
  
  private Map<Comparable, Color> sectionColors;
  
  private Color defaultSectionColor = Color.RED;

  private Map<Comparable, Font> sectionFonts;
  
  private Font defaultSectionFont = new Font("SanSerif", Font.PLAIN, 16);
  
  /** 
   * The number of segments used to render 360 degrees of the pie.  A higher
   * number will give better output but slower performance.
   */
  private int segments = 40;
  
  /**
   * Creates a new pie plot in 3D.
   * 
   * @param dataset  the dataset. 
   */
  public PiePlot3D(PieDataset3D dataset) {
    this.dataset = dataset;
    this.radius = 30.0;    
    this.depth = 6.0;
    this.sectionColors = new HashMap<Comparable, Color>();
  }

  /**
   * Returns the dataset.
   * 
   * @return The dataset. 
   */
  public PieDataset3D getDataset() {
    return this.dataset;
  }

  /**
   * Sets the dataset.
   * 
   * @param dataset  the dataset. 
   */
  public void setDataset(PieDataset3D dataset) {
    this.dataset = dataset;
    // TODO: fire change event
  }

  private Color lookupSectionColor(Comparable key) {
    Color c = this.sectionColors.get(key);
    if (c != null) {
      return c;
    } else {
      return this.defaultSectionColor;
    }
  }
  /**
   * Returns the section color for the specified key.
   * 
   * @param key  the key.
   * 
   * @return The section color (possibly <code>null</code>). 
   */
  public Color getSectionColor(Comparable key) {
    return this.sectionColors.get(key);
  }
  
  /**
   * Sets the section color for a given key.
   * 
   * @param key
   * @param color  the color (<code>null</code> permitted).
   */
  public void setSectionColor(Comparable key, Color color) {
    this.sectionColors.put(key, color);
    // TODO : fire a change event.
  }
  
  public double getDepth() {
      return this.depth;
  }
  
  public Font getDefaultSectionFont() {
    return this.defaultSectionFont;
  }
  
  public Font getSectionFont(Comparable key) {
    return this.sectionFonts.get(key); 
  }
  
  public void setSectionFont(Comparable key, Font font) {
    this.sectionFonts.put(key, font);
    // TODO: fire a change event.
  }
  
  private Font lookupSectionFont(Comparable key) {
    Font f = getSectionFont(key);
    if (f != null) {
      return f;
    } else {
      return this.defaultSectionFont;
    }
  }
  
  @Override
  public void composeToWorld(World world, double xOffset, double yOffset, double zOffset) {
    double total = calcTotal(this.dataset);
    double r = 0.0;
    int count = this.dataset.getItemCount();
    for (int i = 0; i < count; i++) {
      Number n = this.dataset.getValue(i);
      if (n != null) {
        double angle = Math.PI * 2 * (n.doubleValue() / total);
        Color c = this.lookupSectionColor(this.dataset.getKey(i));
        world.add(Object3D.createPieSegment(this.radius, 0.0, yOffset, 
            this.depth, r, r + angle, Math.PI / this.segments, c));
//        world.addAll(Object3D.createPieLabelMarkers(this.radius * 1.2, 0.0, yOffset - 0.5, 
//            this.depth + 0.5, r, r + angle));
        r = r + angle;
      }
    }

    
  }
  
  public List<Object3D> getLabelFaces(double xOffset, double yOffset, double zOffset) {
    double total = calcTotal(this.dataset);
    List<Object3D> result = new ArrayList<Object3D>();
    // this adds the centre points
    result.add(new Dot3D(0.0f, 0.0f, 0.0f, Color.RED));
    result.add(new Dot3D(0.0f, (float) yOffset, 0.0f, Color.RED));
    double r = 0.0;
    int count = this.dataset.getItemCount();
    for (int i = 0; i < count; i++) {
      Number n = this.dataset.getValue(i);
      if (n != null) {
        double angle = Math.PI * 2 * (n.doubleValue() / total);
        result.addAll(Object3D.createPieLabelMarkers(this.radius * 1.2, 0.0, yOffset - 0.5, 
            this.depth + 0.5, r, r + angle));
        r = r + angle;
      }
    }
    return result;
  }

  private double calcTotal(PieDataset3D dataset) {
    double result = 0.0;
    for (int i = 0; i < dataset.getItemCount(); i++) {
      Number n = dataset.getValue(i);
      if (n != null) {
        result = result + n.doubleValue();
      }
    }
    return result;
  }

}
