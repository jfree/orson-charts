/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.plot;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.orsoncharts.data.PieDataset3D;
import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.Dot3D;
import com.orsoncharts.graphics3d.Object3D;
import com.orsoncharts.graphics3d.World;
import com.orsoncharts.legend.LegendItemInfo;
import com.orsoncharts.legend.StandardLegendItemInfo;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.data.DataUtils;
import java.io.Serializable;

/**
 * A pie plot in 3D.  To create a pie chart, you can use the 
 * <code>createPieChart()</code> method in the {@link Chart3DFactory} class.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. */
public class PiePlot3D extends AbstractPlot3D implements Serializable {

    /** The dataset. */
    private PieDataset3D<Number> dataset;

    /** The radius of the pie chart. */
    private double radius; 
  
    /** The depth of the pie chart. */
    private double depth;
  
    /** The paint source. */
    private Pie3DPaintSource paintSource;

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
    public PiePlot3D(PieDataset3D<Number> dataset) {
        ArgChecks.nullNotPermitted(dataset, "dataset");
        this.dataset = dataset;
        this.dataset.addChangeListener(this);
        this.radius = 4.0;    
        this.depth = 0.5;
        this.paintSource = new StandardPie3DPaintSource(
                new Color[] {new Color(0x1A9641), new Color(0xA6D96A), 
                    new Color(0xFDAE61), new Color(0xFFFFBF)});
    }

    /**
     * Returns the dataset.
     * 
     * @return The dataset (never <code>null</code>). 
     */
    public PieDataset3D<Number> getDataset() {
        return this.dataset;
    }

    /**
     * Sets the dataset and notifies registered listeners that the dataset has
     * been updated.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted). 
     */
    public void setDataset(PieDataset3D<Number> dataset) {
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
     * Sets the radius of the pie chart and sends a change event to all 
     * registered listeners.
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
     * Sets the depth of the pie chart and sends a change event to all 
     * registered listeners.
     * 
     * @param depth  the depth. 
     */
    public void setDepth(double depth) {
        this.depth = depth;
        fireChangeEvent();
    }
    
    /**
     * Returns the paint source.
     * 
     * @return The paint source (never <code>null</code>).
     */
    public Pie3DPaintSource getPaintSource() {
        return this.paintSource;
    }
    
    /**
     * Sets the paint source and sends a {@link Plot3DChangeEvent} to all 
     * registered listeners.
     * 
     * @param paintSource  the paint source. 
     */
    public void setPaintSource(Pie3DPaintSource paintSource) {
        ArgChecks.nullNotPermitted(paintSource, "paintSource");
        this.paintSource = paintSource;
        fireChangeEvent();
    }
  
    private Color lookupSectionColor(Comparable key) {
        int index = this.dataset.getIndex(key);
        return this.paintSource.getPaint(index);
    }
    
//    /**
//     * Returns the section color for the specified key.
//     * 
//     * @param key  the key.
//     * 
//     * @return The section color (possibly <code>null</code>). 
//     */
//    public Color getSectionColor(Comparable key) {
//        return this.sectionColors.get(key);
//    }
//  
//    /**
//     * Sets the section color for a given key.
//     * 
//     * @param key
//     * @param color  the color (<code>null</code> permitted).
//     */
//    public void setSectionColor(Comparable key, Color color) {
//        this.sectionColors.put(key, color);
//        fireChangeEvent();
//    }
//  
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
  
    /**
     * Returns the default section font.  This font will be used for 
     * section labels when the getSectionFont(Comparable) method returns
     * <code>null</code>.
     * 
     * @return The default section font (never <code>null</code>). 
     */
    public Font getDefaultSectionFont() {
        return this.defaultSectionFont;
    }
    
    /**
     * Sets the default section font and sends a {@link Plot3DChangeEvent}
     * to all registered listeners.
     * 
     * @param font 
     */
    public void setDefaultSectionFont(Font font) {
        ArgChecks.nullNotPermitted(font, "font");
        this.defaultSectionFont = font;
        fireChangeEvent();
    }
  
    public Font getSectionFont(Comparable key) {
        return this.sectionFonts.get(key); 
    }
  
    public void setSectionFont(Comparable key, Font font) {
        this.sectionFonts.put(key, font);
        fireChangeEvent();
    }
  
    private Font lookupSectionFont(Comparable key) {
        Font f = getSectionFont(key);
        if (f != null) {
            return f;
        } else {
            return this.defaultSectionFont;
        }
    }
  
    /**
     * Returns a list containing legend item info, typically one item for
     * each series in the chart.  This is intended for use in the construction
     * of a chart legend.
     * 
     * @return A list containing legend item info.
     */
    @Override
    public List<LegendItemInfo> getLegendInfo() {
        List<LegendItemInfo> result = new ArrayList<LegendItemInfo>();
        for (Comparable key : this.dataset.getKeys()) {
            int index = this.dataset.getIndex(key);
            LegendItemInfo info = new StandardLegendItemInfo(key, 
                    key.toString(), this.paintSource.getLegendPaint(index));
            result.add(info);
        }
        return result;
    }
    
    /**
     * Adds 3D objects representing the current data for the plot to the 
     * specified world.  After the world has been populated (or constructed) in
     * this way, it is ready for rendering.  This method is called by the
     * {@link Chart3D} class, you won't normally call it directly.
     * 
     * @param world  the world (<code>null</code> not permitted).
     * @param xOffset  the x-offset.
     * @param yOffset  the y-offset.
     * @param zOffset  the z-offset.
     */
    @Override
    public void compose(World world, double xOffset, double yOffset, 
            double zOffset) {
        double total = DataUtils.total(this.dataset);
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
  
    /**
     * Returns...
     * 
     * @param xOffset
     * @param yOffset
     * @param zOffset
     * @return 
     */
    public List<Object3D> getLabelFaces(double xOffset, double yOffset, 
            double zOffset) {
        double total = DataUtils.total(this.dataset);
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
                result.addAll(Object3D.createPieLabelMarkers(this.radius * 1.2,
                        0.0, yOffset - 0.5, this.depth + 0.5, r, r + angle));
                r = r + angle;
            }
        }
        return result;
    }
  
    /**
     * Tests this plot for equality with an arbitrary object.
     * 
     * @param obj  the object (<code>null</code> not permitted).
     * 
     * @return A boolean. 
     */
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
        if (this.depth != that.depth) {
            return false;
        }
        return super.equals(obj);
    }

}
