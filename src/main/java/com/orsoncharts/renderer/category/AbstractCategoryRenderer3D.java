/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.renderer.category;

import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.Range;
import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.data.DataUtilities;
import com.orsoncharts.data.Values3D;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.World;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.renderer.AbstractRenderer3D;
import com.orsoncharts.renderer.Renderer3DChangeEvent;
import com.orsoncharts.renderer.RendererType;

/**
 * A base class that can be used to implement renderers for a 
 * {@link CategoryPlot3D}.
 */
public abstract class AbstractCategoryRenderer3D extends AbstractRenderer3D 
        implements CategoryRenderer3D {
    
    /** A reference to the plot that the renderer is currently assigned to. */
    private CategoryPlot3D plot;
   
    /** 
     * The paint source is used to determine the color for each item drawn
     * by the renderer (never <code>null</code>).
     */
    private Category3DPaintSource paintSource;
    
    /**
     * Default constructor.
     */
    public AbstractCategoryRenderer3D() {
        this.paintSource = new StandardCategory3DPaintSource();
    }
    
    /**
     * Returns the renderer type.
     * 
     * @return The renderer type (never <code>null</code>). 
     */
    @Override
    public RendererType getRendererType() {
        return RendererType.BY_ITEM;
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
     * Returns the paint source for the renderer.
     * 
     * @return The paint source (never <code>null</code>). 
     */
    @Override
    public Category3DPaintSource getPaintSource() {
        return this.paintSource;
    }
    
    /**
     * Sets the paint source for the renderer and sends a 
     * {@link Renderer3DChangeEvent} to all registered listeners.
     * 
     * @param paintSource  the paint source (<code>null</code> not permitted). 
     */
    public void setPaintSource(Category3DPaintSource paintSource) {
        ArgChecks.nullNotPermitted(paintSource, "paintSource");
        this.paintSource = paintSource;
        fireChangeEvent();
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
    public Range findValueRange(Values3D data) {
        return DataUtilities.findValueRange(data);
    }
    
    @Override
    public void composeSeries(World world, Dimension3D dimensions,
            CategoryDataset3D dataset, int series, 
            double xOffset, double yOffset, double zOffset) {
        throw new IllegalStateException(
                "The composeSeries() method is not implemented.");
    }

    @Override
    public void composeItem(World world, Dimension3D dimensions, 
            CategoryDataset3D dataset, int series, int row, int column, 
            double xOffset, double yOffset, double zOffset) {
        throw new IllegalStateException(
                "The composeSeries() method is not implemented.");
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
        if (!this.paintSource.equals(that.paintSource)) {
            return false;
        }
        return true;  // we don't call the super-class because it doesn't 
            // contain any fields that we check for equality
    }
    
}
