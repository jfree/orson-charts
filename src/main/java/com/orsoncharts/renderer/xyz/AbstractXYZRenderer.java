/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.renderer.xyz;

import com.orsoncharts.Range;
import com.orsoncharts.data.DataUtils;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.renderer.AbstractRenderer3D;
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
        fireChangeEvent();
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
