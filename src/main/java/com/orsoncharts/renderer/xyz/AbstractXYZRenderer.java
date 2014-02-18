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

package com.orsoncharts.renderer.xyz;

import java.awt.Color;
import com.orsoncharts.Range;
import com.orsoncharts.data.DataUtils;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.World;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.renderer.AbstractRenderer3D;
import com.orsoncharts.renderer.ComposeType;
import com.orsoncharts.renderer.Renderer3DChangeEvent;
import com.orsoncharts.util.ArgChecks;

/**
 * An abstract base class that can be used to create new {@link XYZRenderer}
 * subclasses.
 */
public class AbstractXYZRenderer extends AbstractRenderer3D {

    private XYZPlot plot;
  
    private XYZColorSource colorSource;
  
    /**
     * Creates a new default instance.
     */
    protected AbstractXYZRenderer() {
        this.colorSource = new StandardXYZColorSource();
    }
  
    /**
     * Returns the plot that the renderer is assigned to, if any.
     * 
     * @return The plot (possibly <code>null</code>). 
     */
    public XYZPlot getPlot() {
        return this.plot;
    }
  
    /**
     * Sets the plot that the renderer is assigned to.
     * 
     * @param plot  the plot (<code>null</code> permitted). 
     */
    public void setPlot(XYZPlot plot) {
        this.plot = plot;
    }

    /**
     * Returns the type of composition performed by this renderer.  The default
     * is <code>PER_ITEM</code> which means the plot will ask the renderer
     * to compose one data item at a time into the 3D model.  Some renderers
     * will override this method to return <code>ALL</code>, which means the
     * renderer will compose all of the data items in one go (the plot calls
     * the {@link #composeAll(XYZPlot, World, Dimension3D, double, double, double)} 
     * method to trigger this).
     * 
     * @return The compose type (never <code>null</code>).
     * 
     * @since 1.1
     */
    public ComposeType getComposeType() {
        return ComposeType.PER_ITEM;
    }
    
    /**
     * Adds objects to the <code>world</code> to represent all the data items
     * that this renderer is responsible for.  This method is only called for
     * renderers that return {@link ComposeType#ALL} from the 
     * {@link #getComposeType()} method.
     * 
     * @param plot  the plot.
     * @param world  the 3D model.
     * @param dimensions  the dimensions of the plot.
     * @param xOffset  the x-offset.
     * @param yOffset  the y-offset.
     * @param zOffset  the z-offset.
     */
    public void composeAll(XYZPlot plot, World world, Dimension3D dimensions, 
            double xOffset, double yOffset, double zOffset) {
        throw new UnsupportedOperationException();
    }
    
    /**
     * Returns the object that provides the color instances for items drawn
     * by the renderer.
     * 
     * @return The color source (never <code>null</code>). 
     */
    public XYZColorSource getColorSource() {
        return this.colorSource;
    }
    
    /**
     * Sets the color source and sends a {@link Renderer3DChangeEvent} to all
     * registered listeners.
     * 
     * @param colorSource  the color source (<code>null</code> not permitted). 
     */
    public void setColorSource(XYZColorSource colorSource) {
        ArgChecks.nullNotPermitted(colorSource, "colorSource");
        this.colorSource = colorSource;
        fireChangeEvent(true);
    }
  
    
    /**
     * Sets a new color source for the renderer using the specified colors and
     * sends a {@link Renderer3DChangeEvent} to all registered listeners. This 
     * is a convenience method that is equivalent to 
     * <code>setColorSource(new StandardXYZColorSource(colors))</code>.
     * 
     * @param colors  one or more colors (<code>null</code> not permitted).
     * 
     * @since 1.1
     */
    public void setColors(Color... colors) {
        setColorSource(new StandardXYZColorSource(colors));
    }
    
    /**
     * Returns the range that is required on the x-axis for this renderer
     * to display all the items in the specified dataset.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted).
     * 
     * @return The x-range. 
     */
    public Range findXRange(XYZDataset dataset) {
        return DataUtils.findXRange(dataset);
    }
    
    /**
     * Returns the range that is required on the y-axis for this renderer
     * to display all the items in the specified dataset.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted).
     * 
     * @return The y-range. 
     */
    public Range findYRange(XYZDataset dataset) {
        return DataUtils.findYRange(dataset);
    }
    
    /**
     * Returns the range that is required on the z-axis for this renderer
     * to display all the items in the specified dataset.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted).
     * 
     * @return The z-range. 
     */
    public Range findZRange(XYZDataset dataset) {
        return DataUtils.findZRange(dataset);
    }
    
    /**
     * Tests this renderer for equality with an arbitrary object.
     * 
     * @param obj  the object (<code>null</code> permitted).
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AbstractXYZRenderer)) {
            return false;
        }
        AbstractXYZRenderer that = (AbstractXYZRenderer) obj;
        if (!this.colorSource.equals(that.colorSource)) {
            return false;
        }
        return true;
    }

}
