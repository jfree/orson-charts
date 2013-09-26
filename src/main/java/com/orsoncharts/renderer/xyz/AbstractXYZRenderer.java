/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.renderer.xyz;

import com.orsoncharts.axis.Range;
import com.orsoncharts.data.DataUtilities;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.renderer.AbstractRenderer3D;

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
        this.paintSource = new DefaultXYZPaintSource();
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

    public XYZPaintSource getPaintSource() {
        return this.paintSource;
    }
  
    public Range findXRange(XYZDataset dataset) {
        return DataUtilities.findXRange(dataset);
    }
    
    public Range findYRange(XYZDataset dataset) {
        return DataUtilities.findYRange(dataset);
    }
    
    public Range findZRange(XYZDataset dataset) {
        return DataUtilities.findZRange(dataset);
    }

}
