/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-2016, by Object Refinery Limited.  All rights reserved.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 * 
 * If you do not wish to be bound by the terms of the GPL, an alternative
 * commercial license can be purchased.  For details, please see visit the
 * Orson Charts home page:
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
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
import com.orsoncharts.ChartElementVisitor;
import com.orsoncharts.data.DataUtils;
import com.orsoncharts.data.ItemKey;
import com.orsoncharts.data.KeyedValuesItemKey;
import com.orsoncharts.label.PieLabelGenerator;
import com.orsoncharts.label.StandardPieLabelGenerator;

/**
 * A plot for creating 3D pie charts.  To create a pie chart, you can use the 
 * {@code createPieChart()} method in the {@link Chart3DFactory} class.  
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
@SuppressWarnings("serial")
public class PiePlot3D extends AbstractPlot3D implements Serializable {
    
    /** The default font for section labels on the chart. */
    public static final Font DEFAULT_SECTION_LABEL_FONT 
            = new Font("Dialog", Font.PLAIN, 14);
    
    /** The dataset. */
    private PieDataset3D<? extends Comparable> dataset;

    /** The radius of the pie chart. */
    private double radius; 
  
    /** The depth of the pie chart. */
    private double depth;
  
    /** The section color source. */
    private ColorSource sectionColorSource;

    /** The section label generator. */
    private PieLabelGenerator sectionLabelGenerator;
    
    /** The font source used to determine the font for section labels. */
    private FontSource sectionLabelFontSource;

    /** 
     * The color source used to determine the foreground color for section 
     * labels. 
     */
    private ColorSource sectionLabelColorSource;
    
    /** The legend label generator. */
    private PieLabelGenerator legendLabelGenerator;
    
    /** 
     * The tool tip generator (can be null, in which case there will be no
     * tool tips. */
    private PieLabelGenerator toolTipGenerator;
    
    /** 
     * The number of segments used to render 360 degrees of the pie.  A higher
     * number will give better output but slower performance.
     */
    private int segments = 40;
  
    /**
     * Creates a new pie plot in 3D.
     * 
     * @param dataset  the dataset ({@code null} not permitted). 
     */
    public PiePlot3D(PieDataset3D<? extends Comparable> dataset) {
        ArgChecks.nullNotPermitted(dataset, "dataset");
        this.dataset = dataset;
        this.dataset.addChangeListener(this);
        this.radius = 4.0;    
        this.depth = 0.5;
        this.sectionColorSource = new StandardColorSource();
        this.sectionLabelGenerator = new StandardPieLabelGenerator(
                StandardPieLabelGenerator.KEY_ONLY_TEMPLATE);
        this.sectionLabelFontSource = new StandardFontSource(
                DEFAULT_SECTION_LABEL_FONT);
        this.sectionLabelColorSource = new StandardColorSource(Color.BLACK);
        this.legendLabelGenerator = new StandardPieLabelGenerator();
        this.toolTipGenerator = new StandardPieLabelGenerator(
                StandardPieLabelGenerator.PERCENT_TEMPLATE_2DP);
    }

    /**
     * Returns the dataset.
     * 
     * @return The dataset (never {@code null}). 
     */
    public PieDataset3D<? extends Comparable> getDataset() {
        return this.dataset;
    }

    /**
     * Sets the dataset and notifies registered listeners that the dataset has
     * been updated.
     * 
     * @param dataset  the dataset ({@code null} not permitted). 
     */
    public void setDataset(PieDataset3D<? extends Comparable> dataset) {
        ArgChecks.nullNotPermitted(dataset, "dataset");
        this.dataset.removeChangeListener(this);
        this.dataset = dataset;
        this.dataset.addChangeListener(this);
        fireChangeEvent(true);
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
        fireChangeEvent(true);
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
        fireChangeEvent(true);
    }
    
    /**
     * Returns the color source for section colors.
     * 
     * @return The color source (never {@code null}).
     */
    public ColorSource getSectionColorSource() {
        return this.sectionColorSource;
    }
    
    /**
     * Sets the color source and sends a {@link Plot3DChangeEvent} to all 
     * registered listeners.
     * 
     * @param source  the color source ({@code null} not permitted). 
     */
    public void setSectionColorSource(ColorSource source) {
        ArgChecks.nullNotPermitted(source, "source");
        this.sectionColorSource = source;
        fireChangeEvent(true);
    }
    
    /**
     * Sets a new color source for the plot using the specified colors and
     * sends a {@link Plot3DChangeEvent} to all registered listeners. This 
     * is a convenience method that is equivalent to 
     * {@code setSectionColorSource(new StandardColorSource(colors))}.
     * 
     * @param colors  one or more colors ({@code null} not permitted).
     * 
     * @since 1.2
     */
    public void setSectionColors(Color... colors) {
        setSectionColorSource(new StandardColorSource(colors));
    }

    /**
     * Returns the object that creates labels for each section of the pie
     * chart.
     * 
     * @return The section label generator (never {@code null}).
     * 
     * @since 1.2
     */
    public PieLabelGenerator getSectionLabelGenerator() {
        return this.sectionLabelGenerator;    
    }
    
    /**
     * Sets the object that creates labels for each section of the pie chart,
     * and sends a {@link Plot3DChangeEvent} to all registered listeners.
     * 
     * @param generator  the generator ({@code null} not permitted).
     * 
     * @since 1.2
     */
    public void setSectionLabelGenerator(PieLabelGenerator generator) {
        ArgChecks.nullNotPermitted(generator, "generator");
        this.sectionLabelGenerator = generator;
        fireChangeEvent(false);
    }
    
    /**
     * Returns the font source that is used to determine the font to use for 
     * the section labels.
     * 
     * @return The font source for the section labels (never {@code null}). 
     */
    public FontSource getSectionLabelFontSource() {
        return this.sectionLabelFontSource; 
    }
    
    /**
     * Sets the font source and sends a {@link Plot3DChangeEvent} to all
     * registered listeners.
     * 
     * @param source  the source ({@code null} not permitted). 
     */
    public void setSectionLabelFontSource(FontSource source) {
        ArgChecks.nullNotPermitted(source, "source");
        this.sectionLabelFontSource = source;
        fireChangeEvent(false);
    }

    /**
     * Returns the color source for section labels.  The default value is
     * an instance of {@link StandardColorSource} that always returns
     * {@code Color.BLACK}.
     * 
     * @return The color source (never {@code null}).
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
        fireChangeEvent(false);
    }
    
    /**
     * Returns the object that creates legend labels for each section of the pie
     * chart.
     * 
     * @return The legend label generator (never {@code null}).
     * 
     * @since 1.2
     */
    public PieLabelGenerator getLegendLabelGenerator() {
        return this.legendLabelGenerator;
    }
    
    /**
     * Sets the object that creates legend labels for each section of the pie 
     * chart, and sends a {@link Plot3DChangeEvent} to all registered 
     * listeners.
     * 
     * @param generator  the generator ({@code null} not permitted).
     * 
     * @since 1.2
     */
    public void setLegendLabelGenerator(PieLabelGenerator generator) {
        ArgChecks.nullNotPermitted(generator, "generator");
        this.legendLabelGenerator = generator;
        fireChangeEvent(false);
    }
    
    /**
     * Returns the tool tip generator.
     * 
     * @return The tool tip generator (possibly {@code null}).
     * 
     * @since 1.3
     */
    public PieLabelGenerator getToolTipGenerator() {
        return this.toolTipGenerator;
    }
    
    /**
     * Sets the tool tip generator and sends a change event to all registered
     * listeners.
     * 
     * @param generator  the generator ({@code null} permitted).
     * 
     * @since 1.3
     */
    public void setToolTipGenerator(PieLabelGenerator generator) {
        this.toolTipGenerator = generator;
        fireChangeEvent(false);
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
     * representing the pie chart.  The default value is {@code 40}.
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
        fireChangeEvent(true);
    }
    
    /**
     * Returns a list containing legend item info, typically one item for
     * each series in the chart.  This is intended for use in the construction
     * of a chart legend.
     * 
     * @return A list containing legend item info.
     */
    @Override @SuppressWarnings("unchecked")
    public List<LegendItemInfo> getLegendInfo() {
        List<LegendItemInfo> result = new ArrayList<LegendItemInfo>();
        for (Comparable<?> key : (List<Comparable<?>>)
                this.dataset.getKeys()) {
            String label = this.legendLabelGenerator.generateLabel(dataset, 
                    key);
            LegendItemInfo info = new StandardLegendItemInfo(key, 
                    label, this.sectionColorSource.getColor(key));
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
     * @param world  the world ({@code null} not permitted).
     * @param xOffset  the x-offset.
     * @param yOffset  the y-offset.
     * @param zOffset  the z-offset.
     */
    @Override
    @SuppressWarnings("unchecked")
    public void compose(World world, double xOffset, double yOffset, 
            double zOffset) {
        double total = DataUtils.total(this.dataset);
        double r = 0.0;
        int count = this.dataset.getItemCount();
        for (int i = 0; i < count; i++) {
            Comparable<?> key = this.dataset.getKey(i);
            Number n = (Number) this.dataset.getValue(i);
            if (n != null) {
                double angle = Math.PI * 2 * (n.doubleValue() / total);
                Color c = this.sectionColorSource.getColor(
                        this.dataset.getKey(i));
                Object3D segment = Object3D.createPieSegment(this.radius, 0.0, 
                        yOffset, this.depth, r, r + angle, 
                        Math.PI / this.segments, c);
                segment.setProperty(Object3D.ITEM_KEY, 
                        new KeyedValuesItemKey(key));
                world.add(segment);
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
            Number n = (Number) this.dataset.getValue(i);
            double angle = 0.0;
            if (n != null) {
                angle = Math.PI * 2 * (n.doubleValue() / total);
            }
            result.addAll(Object3D.createPieLabelMarkers(this.radius * 1.2,
                    0.0, yOffset - this.depth * 0.05, this.depth * 1.1, r, 
                    r + angle));
            r = r + angle;
        }
        return result;
    }

    @Override
    public String generateToolTipText(ItemKey itemKey) {
        if (!(itemKey instanceof KeyedValuesItemKey)) {
            throw new IllegalArgumentException(
                    "The itemKey must be a ValuesItemKey instance.");
        }
        KeyedValuesItemKey vik = (KeyedValuesItemKey) itemKey;
        return this.toolTipGenerator.generateLabel(this.dataset, vik.getKey());
    }

    /**
     * Receives a visitor.  This is a general purpose mechanism, but the main
     * use is to apply chart style changes across all the elements of a 
     * chart.
     * 
     * @param visitor  the visitor ({@code null} not permitted).
     * 
     * @since 1.2
     */
    @Override
    public void receive(ChartElementVisitor visitor) { 
        visitor.visit(this);
    }

    /**
     * Tests this plot for equality with an arbitrary object.  Note that the
     * plot's dataset is NOT considered in the equality test.
     * 
     * @param obj  the object ({@code null} not permitted).
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
        if (!this.sectionLabelGenerator.equals(that.sectionLabelGenerator)) {
            return false;
        }
        if (!this.sectionLabelFontSource.equals(that.sectionLabelFontSource)) {
            return false;
        }
        if (!this.sectionLabelColorSource.equals(
                that.sectionLabelColorSource)) {
            return false;
        }
        if (!this.legendLabelGenerator.equals(that.legendLabelGenerator)) {
            return false;
        }
        if (!this.toolTipGenerator.equals(that.toolTipGenerator)) {
            return false;
        }
        if (this.segments != that.segments) {
            return false;
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.radius) 
                ^ (Double.doubleToLongBits(this.radius) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.depth) 
                ^ (Double.doubleToLongBits(this.depth) >>> 32));
        hash = 97 * hash + this.sectionColorSource.hashCode();
        hash = 97 * hash + this.sectionLabelGenerator.hashCode();
        hash = 97 * hash + this.sectionLabelFontSource.hashCode();
        hash = 97 * hash + this.sectionLabelColorSource.hashCode();
        hash = 97 * hash + this.segments;
        return hash;
    }

}
