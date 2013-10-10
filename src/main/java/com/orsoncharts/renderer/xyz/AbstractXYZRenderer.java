/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.renderer.xyz;

import com.orsoncharts.Range;
import com.orsoncharts.data.DataUtilities;
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
  
    private XYZPaintSource paintSource;
  
    /**
     * Creates a new default instance.
     */
    protected AbstractXYZRenderer() {
        this.paintSource = new StandardXYZPaintSource();
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
     * Returns the object that provides the paint instances for items drawn
     * by the renderer.
     * 
     * @return The paint source (never <code>null</code>). 
     */
    public XYZPaintSource getPaintSource() {
        return this.paintSource;
    }
    
    /**
     * Sets the paint source and sends a {@link Renderer3DChangeEvent} to all
     * registered listeners.
     * 
     * @param paintSource  the paint source (<code>null</code> not permitted). 
     */
    public void setPaintSource(XYZPaintSource paintSource) {
        ArgChecks.nullNotPermitted(paintSource, "paintSource");
        this.paintSource = paintSource;
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
        return DataUtilities.findXRange(dataset);
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
        return DataUtilities.findYRange(dataset);
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
        return DataUtilities.findZRange(dataset);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AbstractXYZRenderer)) {
            return false;
        }
        
        return true;
    }

}
