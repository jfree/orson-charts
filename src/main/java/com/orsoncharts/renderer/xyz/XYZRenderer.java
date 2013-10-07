/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.renderer.xyz;

import com.orsoncharts.Range;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.World;
import com.orsoncharts.renderer.Renderer3D;
import com.orsoncharts.renderer.category.Category3DPaintSource;

/**
 * A renderer that can display data from an {@link XYZDataset} on an
 * {@link XYZPlot}.
 */
public interface XYZRenderer extends Renderer3D {

    /**
     * Returns the plot that this renderer is assigned to.
     * 
     * @return The plot (possibly <code>null</code>). 
     */
    public XYZPlot getPlot();
  
    /**
     * Sets the plot that the renderer is assigned to.  Although this method
     * is part of the public API, client code should not need to call it.
     * 
     * @param plot  the plot (<code>null</code> permitted). 
     */
    public void setPlot(XYZPlot plot);

    public XYZPaintSource getPaintSource();

    /**
     * Returns the range that should be set on the x-axis in order for this 
     * renderer to be able to display all the data in the supplied dataset.
     * If the dataset contains no data, this method returns <code>null</code>.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted).
     * 
     * @return The range (possibly <code>null</code>). 
     */
    public Range findXRange(XYZDataset dataset);
    
    /**
     * Returns the range that should be set on the y-axis in order for this 
     * renderer to be able to display all the data in the supplied dataset.
     * If the dataset contains no data, this method returns <code>null</code>.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted).
     * 
     * @return The range. 
     */
    public Range findYRange(XYZDataset dataset);
    
    /**
     * Returns the range that should be set on the z-axis in order for this 
     * renderer to be able to display all the data in the supplied dataset.
     * If the dataset contains no data, this method returns <code>null</code>.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted).
     * 
     * @return The range. 
     */
    public Range findZRange(XYZDataset dataset);

    /**
     * Constructs and places one item from the specified dataset into the given 
     * world.
     * 
     * @param world
     * @param dataset
     * @param series
     * @param item
     * @param xOffset
     * @param yOffset
     * @param zOffset 
     */
    public void composeItem(XYZDataset dataset, int series, int item, 
            World world, Dimension3D dimensions, 
            double xOffset, double yOffset, double zOffset);

}
