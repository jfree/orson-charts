/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * Redistribution of these source files is prohibited.
 * 
 */

package com.orsoncharts.plot;

import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.orsoncharts.Chart3D;
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

/**
 * A plot for creating 3D pie charts.  To create a pie chart, you can use the 
 * <code>createPieChart()</code> method in the {@link Chart3DFactory} class.  
 * A typical pie chart will look like this:  
 * <div>
 * <object id="ABC" data="../../../doc-files/PieChart3DDemo1.svg"  
 * type="image/svg+xml" width="500" height="359"> 
 * </object>
 * </div>
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. 
 */
public class PiePlot3D extends AbstractPlot3D implements Serializable {

    /** The default font for section labels on the chart. */
    public static final Font DEFAULT_SECTION_LABEL_FONT 
            = new Font("Dialog", Font.PLAIN, 14);
    
    /** The dataset. */
    private PieDataset3D dataset;

    /** The radius of the pie chart. */
    private double radius; 
  
    /** The depth of the pie chart. */
    private double depth;
  
    /** The section color source. */
    private ColorSource sectionColorSource;

    /** The font source used to determine the font for section labels. */
    private FontSource sectionLabelFontSource;

    /** 
     * The color source used to determine the foreground color for section 
     * labels. 
     */
    private ColorSource sectionLabelColorSource;
    
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
        this.radius = 4.0;    
        this.depth = 0.5;
        this.sectionColorSource = new StandardColorSource();
        this.sectionLabelFontSource = new StandardFontSource(
                DEFAULT_SECTION_LABEL_FONT);
        this.sectionLabelColorSource = new StandardColorSource(Color.BLACK);
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
     * Returns the color source for section colors.
     * 
     * @return The color source (never <code>null</code>).
     */
    public ColorSource getSectionColorSource() {
        return this.sectionColorSource;
    }
    
    /**
     * Sets the color source and sends a {@link Plot3DChangeEvent} to all 
     * registered listeners.
     * 
     * @param source  the color source (<code>null</code> not permitted). 
     */
    public void setSectionColorSource(ColorSource source) {
        ArgChecks.nullNotPermitted(source, "source");
        this.sectionColorSource = source;
        fireChangeEvent();
    }

    /**
     * Returns the font source that is used to determine the font to use for 
     * the section labels.
     * 
     * @return The font source for the section labels (never <code>null</code>). 
     */
    public FontSource getSectionLabelFontSource() {
        return this.sectionLabelFontSource; 
    }
    
    /**
     * Sets the font source and sends a {@link Plot3DChangeEvent} to all
     * registered listeners.
     * 
     * @param source  the source (<code>null</code> not permitted). 
     */
    public void setSectionLabelFontSource(FontSource source) {
        ArgChecks.nullNotPermitted(source, "source");
        this.sectionLabelFontSource = source;
        fireChangeEvent();
    }

    /**
     * Returns the color source for section labels.  The default value is
     * an instance of {@link StandardColorSource} that always returns
     * <code>Color.BLACK</code>.
     * 
     * @return The color source (never <code>null</code>).
     * 
     * @see #setSectionLabelColorSource(ColorSource) 
     */
    public ColorSource getSectionLabelColorSource() {
        return this.sectionLabelColorSource;
    }
    
    /**
     * Sets the color source for the section labels and sends a 
     * {@link Plot3DChangeEvent} to all registered listeners.
     * 
     * @param source  the color source. 
     * 
     * @see #getSectionLabelColorSource() 
     */
    public void setSectionLabelColorSource(ColorSource source) {
        ArgChecks.nullNotPermitted(source, "source");
        this.sectionLabelColorSource = source;
        fireChangeEvent();
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
 
    /**
     * Returns the number of segments used when composing the 3D objects
     * representing the pie chart.  The default value is <code>40</code>.
     * 
     * @return The number of segments used to compose the pie chart. 
     */
    public int getSegmentCount() {
        return this.segments;
    }
    
    /**
     * Sets the number of segments used when composing the pie chart and 
     * sends a {@link Plot3DChangeEvent} to all registered listeners.  A higher
     * number will result in a more rounded pie chart, but will take longer
     * to render.
     * 
     * @param count  the count. 
     */
    public void setSegmentCount(int count) {
        this.segments = count;
        fireChangeEvent();
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
        for (Comparable<?> key : this.dataset.getKeys()) {
            LegendItemInfo info = new StandardLegendItemInfo(key, 
                    key.toString(), this.sectionColorSource.getColor(key));
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
                Color c = this.sectionColorSource.getColor(
                        this.dataset.getKey(i));
                world.add(Object3D.createPieSegment(this.radius, 0.0, yOffset, 
                        this.depth, r, r + angle, Math.PI / this.segments, c));
                r = r + angle;
            }
        }
    }
  
    /**
     * Returns a list of label faces for the plot.  These are non-visible 
     * objects added to the 3D model of the pie chart to track the positions 
     * for labels (which are added after the plot is projected and rendered).  
     * <br><br>
     * NOTE: This method is public so that it can be called by the 
     * {@link Chart3D} class - you won't normally call it directly.
     * 
     * @param xOffset  the x-offset.
     * @param yOffset  the y-offset.
     * @param zOffset  the z-offset.
     * 
     * @return A list of label faces.
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
                        0.0, yOffset - this.depth * 0.05, this.depth * 1.1, r, 
                        r + angle));
                r = r + angle;
            }
        }
        return result;
    }
  
    /**
     * Tests this plot for equality with an arbitrary object.  Note that the
     * plot's dataset is NOT considered in the equality test.
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
        if (!this.sectionColorSource.equals(that.sectionColorSource)) {
            return false;
        }
        if (!this.sectionLabelFontSource.equals(that.sectionLabelFontSource)) {
            return false;
        }
        if (!this.sectionLabelColorSource.equals(that.sectionLabelColorSource)) {
            return false;
        }
        if (this.segments != that.segments) {
            return false;
        }
        return super.equals(obj);
    }

}
