/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package com.orsoncharts.plot;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.orsoncharts.data.PieDataset3D;
import com.orsoncharts.graphics3d.ArgChecks;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.Dot3D;
import com.orsoncharts.graphics3d.Object3D;
import com.orsoncharts.graphics3d.World;

/**
 * A pie plot in 3D.
 */
public class PiePlot3D extends AbstractPlot3D {

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
   * @param dataset  the dataset (<code>null</code> not permitted). 
   */
  public PiePlot3D(PieDataset3D dataset) {
    ArgChecks.nullNotPermitted(dataset, "dataset");
    this.dataset = dataset;
    this.dataset.addChangeListener(this);
    this.radius = 8.0;    
    this.depth = 1.0;
    this.sectionColors = new HashMap<Comparable, Color>();
   
  }

  /**
   * Returns the dataset.
   * 
   * @return The dataset (never <code>null</code>). 
   */
  public PieDataset3D getDataset() {
    return this.dataset;
  }

  /**
   * Sets the dataset and notifies registered listeners that the dataset has
   * been updated.
   * 
   * @param dataset  the dataset (<code>null</code> not permitted). 
   */
  public void setDataset(PieDataset3D dataset) {
    ArgChecks.nullNotPermitted(dataset, "dataset");
    this.dataset.removeChangeListener(this);
    this.dataset = dataset;
    this.dataset.addChangeListener(this);
    fireChangeEvent();
  }

  /**
   * Returns the radius of the pie (the default value is 8.0).
   * 
   * @return The radius of the pie. 
   */
  public double getRadius() {
    return this.radius;
  }
  
  /**
   * Sets the radius of the pie chart and sends a change event to all registered
   * listeners.
   * 
   * @param radius  the radius. 
   */
  public void setRadius(double radius) {
    this.radius = radius;
    fireChangeEvent();
  }
  
  /**
   * Returns the depth of the pie (the default value is 2.0).
   * 
   * @return The depth of the pie. 
   */
  public double getDepth() {
    return this.depth;
  }

  /**
   * Sets the depth of the pie chart and sends a change event to all registered
   * listeners.
   * 
   * @param depth  the depth. 
   */
  public void setDepth(double depth) {
    this.depth = depth;
    fireChangeEvent();
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
  
  /**
   * Returns the dimensions for the plot.  For the pie chart, it is more 
   * natural to specify the dimensions in terms of a radius and a depth, so
   * we use those values to calculate the dimensions here.
   * 
   * @return The dimensions for the plot. 
   */
  @Override
  public Dimension3D getDimensions() {
    return new Dimension3D(this.radius * 2, this.depth, this.radius * 2);
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
  
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof PiePlot3D)) {
      return false;
    }
    PiePlot3D that = (PiePlot3D) obj;
    if (this.radius != that.radius) {
      return false;
    }
    return true;
  }

}
