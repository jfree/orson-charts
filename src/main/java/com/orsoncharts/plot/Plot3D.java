/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package com.orsoncharts.plot;

import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.World;
import com.orsoncharts.legend.LegendItemInfo;
import java.util.List;

/**
 * A plot for a 3D chart.  Built-in implementations include {@link PiePlot3D},
 * {@link CategoryPlot3D} and {@link XYZPlot}.
 */
public interface Plot3D {

    /**
     * Returns the dimensions for the plot in the 3D world in which it will 
     * be composed.
     * 
     * @return The dimensions (never <code>null</code>). 
     */
    Dimension3D getDimensions();
  
    /**
     * Adds 3D objects representing the current data for the plot to the 
     * specified world.  After the world has been populated (or constructed) in
     * this way, it is ready for rendering.
     * 
     * @param world  the world (<code>null</code> not permitted).
     * @param xOffset  the x-offset.
     * @param yOffset  the y-offset.
     * @param zOffset  the z-offset.
     */
    void composeToWorld(World world, double xOffset, double yOffset, 
            double zOffset);

    /**
     * Returns a list containing legend item info, typically one item for
     * each series in the chart.  This is intended for use in the construction
     * of a chart legend.  
     * <br><br>
     * If you are implementing a new plot type that does not require a legend, 
     * return an empty list.
     * 
     * @return A list containing legend item info (never <code>null</code>).
     */
    List<LegendItemInfo> getLegendInfo();
    
    /**
     * Registers a listener to receive notification of changes to the plot.
     * 
     * @param listener  the listener (<code>null</code> not permitted). 
     */
    void addChangeListener(Plot3DChangeListener listener);
  
    /**
     * De-registers a listener so that it no longer receives notification of
     * changes to the plot.
     * 
     * @param listener  the listener (<code>null</code> not permitted).
     */
    void removeChangeListener(Plot3DChangeListener listener);
  
}
