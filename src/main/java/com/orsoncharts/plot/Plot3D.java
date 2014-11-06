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

import java.util.List;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.World;
import com.orsoncharts.legend.LegendItemInfo;
import com.orsoncharts.Chart3D;
import com.orsoncharts.ChartElement;
import com.orsoncharts.data.ItemKey;

/**
 * A plot for a {@link Chart3D}.  In Orson Charts, the {@code Chart3D} is
 * the umbrella object for all charts, but it is the {@code Plot3D}
 * instance that determines the real structure of the chart.  Built-in 
 * implementations include {@link PiePlot3D}, {@link CategoryPlot3D} and 
 * {@link XYZPlot}.
 */
public interface Plot3D extends ChartElement {

    /**
     * Returns the chart that the plot is assigned to, if any.
     * 
     * @return The chart (possibly {@code null}).
     * 
     * @since 1.2
     */
    Chart3D getChart();
    
    /**
     * Sets the chart that the plot is assigned to.  This method is intended
     * for use by the framework, you should not need to call it yourself.
     * 
     * @param chart  the chart ({@code null} permitted).
     * 
     * @since 1.2
     */
    void setChart(Chart3D chart);
    
    /**
     * Returns the dimensions for the plot in the 3D world in which it will 
     * be composed.
     * 
     * @return The dimensions (never {@code null}). 
     */
    Dimension3D getDimensions();
  
    /**
     * Adds 3D objects representing the current data for the plot to the 
     * specified world.  After the world has been populated (or constructed) in
     * this way, it is ready for rendering.
     * 
     * @param world  the world ({@code null} not permitted).
     * @param xOffset  the x-offset.
     * @param yOffset  the y-offset.
     * @param zOffset  the z-offset.
     */
    void compose(World world, double xOffset, double yOffset, double zOffset);

    /**
     * Returns a list containing legend item info, typically one item for
     * each series in the chart.  This is intended for use in the construction
     * of a chart legend.  
     * <br><br>
     * If you are implementing a new plot type that does not require a legend, 
     * return an empty list.
     * 
     * @return A list containing legend item info (never {@code null}).
     */
    List<LegendItemInfo> getLegendInfo();
    
    /**
     * Returns the tool tip text for the specified data item, or 
     * {@code null} if no tool tip is required.
     * 
     * @param itemKey  the item key ({@code null} not permitted).
     * 
     * @return The tool tip text (possibly {@code null}).
     * 
     * @since 1.3
     */
    String generateToolTipText(ItemKey itemKey);
    
    /**
     * Registers a listener to receive notification of changes to the plot.
     * 
     * @param listener  the listener ({@code null} not permitted). 
     */
    void addChangeListener(Plot3DChangeListener listener);
  
    /**
     * De-registers a listener so that it no longer receives notification of
     * changes to the plot.
     * 
     * @param listener  the listener ({@code null} not permitted).
     */
    void removeChangeListener(Plot3DChangeListener listener);
  
}
