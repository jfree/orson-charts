/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.renderer.xyz;

import com.orsoncharts.Range;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.World;
import com.orsoncharts.renderer.ComposeType;
import com.orsoncharts.renderer.Renderer3D;

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
    XYZPlot getPlot();
  
    /**
     * Sets the plot that the renderer is assigned to.  Although this method
     * is part of the public API, client code should not need to call it.
     * 
     * @param plot  the plot (<code>null</code> permitted). 
     */
    void setPlot(XYZPlot plot);
    
    /**
     * Returns the color source for the renderer, which is an object that
     * is responsible for providing the colors used by the renderer to draw
     * data (and legend) items.
     * 
     * @return The paint source (never <code>null</code>). 
     */
    XYZColorSource getColorSource();

    /**
     * Returns the range that should be set on the x-axis in order for this 
     * renderer to be able to display all the data in the supplied dataset.
     * If the dataset contains no data, this method returns <code>null</code>.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted).
     * 
     * @return The range (possibly <code>null</code>). 
     */
    Range findXRange(XYZDataset dataset);
    
    /**
     * Returns the range that should be set on the y-axis in order for this 
     * renderer to be able to display all the data in the supplied dataset.
     * If the dataset contains no data, this method returns <code>null</code>.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted).
     * 
     * @return The range. 
     */
    Range findYRange(XYZDataset dataset);
    
    /**
     * Returns the range that should be set on the z-axis in order for this 
     * renderer to be able to display all the data in the supplied dataset.
     * If the dataset contains no data, this method returns <code>null</code>.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted).
     * 
     * @return The range. 
     */
    Range findZRange(XYZDataset dataset);

    /**
     * Returns the type of composition performed by the renderer.  This
     * determines whether the plot will call the <code>composeItem()</code>
     * method (once for each data item) or just call the 
     * <code>composeAll()</code> method once.
     * 
     * @return The type of composition (never <code>null</code>).
     * 
     * @since 1.1
     */
    ComposeType getComposeType();
    
    /**
     * Constructs and places one item from the specified dataset into the given 
     * world.  The {@link XYZPlot} class will iterate over its dataset and
     * and call this method for each item (in other words, you don't need to 
     * call this method directly).
     * 
     * @param dataset the dataset (<code>null</code> not permitted).
     * @param series  the series index.
     * @param item  the item index.
     * @param world  the world (<code>null</code> not permitted).
     * @param dimensions  the dimensions (<code>null</code> not permitted).
     * @param xOffset  the x-offset.
     * @param yOffset  the y-offset.
     * @param zOffset  the z-offset.
     */
    void composeItem(XYZDataset dataset, int series, int item, 
            World world, Dimension3D dimensions, 
            double xOffset, double yOffset, double zOffset);

    /**
     * Composes all the 3D objects that this renderer needs to present.  This
     * method only needs to be implemented if the <code>getComposeType()</code>
     * method returns <code>ALL</code>, otherwise it can be left empty.
     * 
     * @param plot  the plot.
     * @param world  the world (<code>null</code> not permitted).
     * @param dimensions  the dimensions (<code>null</code> not permitted).
     * @param xOffset  the x-offset.
     * @param yOffset  the y-offset.
     * @param zOffset  the z-offset.
     * 
     * @since 1.1
     */
    void composeAll(XYZPlot plot, World world, Dimension3D dimensions, 
            double xOffset, double yOffset, double zOffset);

}
