/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * Redistribution of this source file is prohibited.
 * 
 */

package com.orsoncharts.renderer.category;

import java.awt.Color;
import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.Range;
import com.orsoncharts.data.DataUtils;
import com.orsoncharts.data.Values3D;
import com.orsoncharts.graphics3d.Offset3D;
import com.orsoncharts.label.CategoryItemLabelGenerator;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.renderer.AbstractRenderer3D;
import com.orsoncharts.util.ObjectUtils;

/**
 * A base class that can be used to implement renderers for a 
 * {@link CategoryPlot3D}.
 */
public abstract class AbstractCategoryRenderer3D extends AbstractRenderer3D 
        implements CategoryRenderer3D {
    
    /** A reference to the plot that the renderer is currently assigned to. */
    private CategoryPlot3D plot;
   
    /** 
     * The color source is used to determine the color for each item drawn
     * by the renderer (never <code>null</code>).
     */
    private CategoryColorSource colorSource;
    
    /** 
     * An object that generates item labels for the chart.  Can be null.
     */
    private CategoryItemLabelGenerator itemLabelGenerator;
    
    /** The item label offsets. */
    private Offset3D itemLabelOffsets;
    
    /**
     * Default constructor.
     */
    public AbstractCategoryRenderer3D() {
        this.colorSource = new StandardCategoryColorSource();
        this.itemLabelGenerator = null;
        this.itemLabelOffsets = new Offset3D(0.0, 0.05, 1.1);
    }
    
    /**
     * Returns the plot that the renderer is currently assigned to, if any.
     * 
     * @return The plot or <code>null</code>.
     */
    @Override
    public CategoryPlot3D getPlot() {
        return this.plot;
    }
    
    /**
     * Sets the plot that the renderer is assigned to.  You do not need to 
     * call this method yourself, the plot takes care of it when you call
     * the <code>setRenderer()</code> method on the plot.
     * 
     * @param plot  the plot (<code>null</code> permitted).
     */
    @Override
    public void setPlot(CategoryPlot3D plot) {
        this.plot = plot;
    }

    /**
     * Returns the color source for the renderer.  This is used to determine
     * the colors used for individual items in the chart, and the color to 
     * display for a series in the chart legend.
     * 
     * @return The color source (never <code>null</code>). 
     */
    @Override
    public CategoryColorSource getColorSource() {
        return this.colorSource;
    }
    
    /**
     * Sets the color source for the renderer and sends a 
     * {@link Renderer3DChangeEvent} to all registered listeners.
     * 
     * @param colorSource  the color source (<code>null</code> not permitted). 
     */
    @Override
    public void setColorSource(CategoryColorSource colorSource) {
        ArgChecks.nullNotPermitted(colorSource, "colorSource");
        this.colorSource = colorSource;
        fireChangeEvent(true);
    }
    
    /**
     * Sets a new color source for the renderer using the specified colors and
     * sends a {@link Renderer3DChangeEvent} to all registered listeners. This 
     * is a convenience method that is equivalent to 
     * <code>setColorSource(new StandardCategoryColorSource(colors))</code>.
     * 
     * @param colors  one or more colors (<code>null</code> not permitted).
     * 
     * @since 1.1
     */
    @Override
    public void setColors(Color... colors) {
        setColorSource(new StandardCategoryColorSource(colors));
    }

    /**
     * Returns the item label generator for the renderer (possibly 
     * <code>null</code>).
     * 
     * @return The item label generator (possibly <code>null</code>).
     * 
     * @since 1.3
     */
    public CategoryItemLabelGenerator getItemLabelGenerator() {
        return itemLabelGenerator;
    }

    /**
     * Sets the item label generator for the renderer and sends a change event
     * to all registered listeners.
     * 
     * @param generator  the generator (<code>null</code> permitted).
     * 
     * @since 1.3
     */
    public void setItemLabelGenerator(CategoryItemLabelGenerator generator) {
        this.itemLabelGenerator = generator;
        fireChangeEvent(true);
    }

    /**
     * Returns the item label offsets.
     * 
     * @return The item label offsets (never <code>null</code>).
     * 
     * @since 1.3
     */
    public Offset3D getItemLabelOffsets() {
        return this.itemLabelOffsets;
    }
    
    /**
     * Sets the item label offsets and sends a change event to all registered
     * listeners.
     * 
     * @param offsets  the offsets (<code>null</code> not permitted).
     * 
     * @since 1.3
     */
    public void setItemLabelOffsets(Offset3D offsets) {
        ArgChecks.nullNotPermitted(offsets, "offsets");
        this.itemLabelOffsets = offsets;
        fireChangeEvent(true);
    }
    
    /**
     * Returns the range of values that will be required on the value axis
     * to see all the data from the dataset.
     * 
     * @param data  the data (<code>null</code> not permitted).
     * 
     * @return The range (possibly <code>null</code>) 
     */
    @Override
    public Range findValueRange(Values3D<? extends Number> data) {
        return DataUtils.findValueRange(data);
    }
    
    /**
     * Tests this renderer for equality with an arbitrary object.
     * 
     * @param obj  the object (<code>null</code> permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } 
        if (!(obj instanceof AbstractCategoryRenderer3D)) {
            return false;
        }
        AbstractCategoryRenderer3D that = (AbstractCategoryRenderer3D) obj;
        if (!this.colorSource.equals(that.colorSource)) {
            return false;
        }
        if (!ObjectUtils.equals(this.itemLabelGenerator, 
                that.itemLabelGenerator)) {
            return false;
        }
        if (!this.itemLabelOffsets.equals(that.itemLabelOffsets)) {
            return false;
        }
        return super.equals(obj);
    }
    
}
