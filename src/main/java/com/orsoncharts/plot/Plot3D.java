/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package com.orsoncharts.plot;

import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.World;

/**
 * A plot for a 3D chart.  Implementations will include {@link PiePlot3D},
 * CategoryPlot3D and XYZPlot.
 */
public interface Plot3D {

    /**
     * Returns the dimensions for the plot in the 3D world in which it will 
     * be composed.
     * 
     * @return The dimensions (never <code>null</code>). 
     */
    public Dimension3D getDimensions();
  
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
    public void composeToWorld(World world, double xOffset, double yOffset, 
            double zOffset);

    /**
     * Registers a listener to receive notification of changes to the plot.
     * 
     * @param listener  the listener (<code>null</code> not permitted). 
     */
    public void addChangeListener(Plot3DChangeListener listener);
  
    /**
     * De-registers a listener so that it no longer receives notification of
     * changes to the plot.
     * 
     * @param listener  the listener (<code>null</code> not permitted).
     */
    public void removeChangeListener(Plot3DChangeListener listener);
  
}
