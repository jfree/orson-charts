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

package com.orsoncharts.data.xyz;

import com.orsoncharts.Range;
import com.orsoncharts.data.function.Function3D;

/**
 * A collection of utility methods related to {@link XYZDataset}.
 */
public class XYZDatasetUtils {
    
    /**
     * Creates an {@link XYZDataset} by sampling a {@link Function3D} over
     * a specified range.
     * 
     * @param f  the function (<code>null</code> not permitted).
     * @param key  the series key (<code>null</code> not permitted).
     * @param xrange  the range of x values (<code>null</code> not permitted).
     * @param xcount  the number of x samples (must be at least 2).
     * @param zrange  the range of z values (<code>null</code> not permitted).
     * @param zcount  the number of z-samples (must be at least 2).
     * 
     * @return A dataset containing sampled values from the function.
     */
    public static XYZDataset sampleFunction(Function3D f, String key, 
            Range xrange, double xcount, Range zrange, double zcount) {
        return sampleFunction(f, key, xrange.getMin(), xrange.getMax(),
                xcount, zrange.getMin(), zrange.getMax(), zcount);
    }
    
    /**
     * Creates an {@link XYZDataset} by sampling a {@link Function3D} over
     * a specified range.
     * 
     * @param f  the function (<code>null</code> not permitted).
     * @param key  the series key (<code>null</code> not permitted).
     * @param xmin  the lower bound of the x-range.
     * @param xmax  the upper bound of the x-range.
     * @param xcount  the number of x samples (must be at least 2).
     * @param zmin  the lower bound of the z-range.
     * @param zmax  the upper bound of the z-range.
     * @param zcount  the number of z-samples (must be at least 2).
     * 
     * @return A dataset containing sampled values from the function.
     */
    public static XYZDataset sampleFunction(Function3D f, String key, 
            double xmin, double xmax, double xcount, double zmin, double zmax, 
            double zcount) {
        XYZSeries s = new XYZSeries(key);
        for (double x = xmin; x <= xmax; x += (xmax - xmin) / xcount) {
            for (double z = zmin; z <= zmax; z += (zmax - zmin) / zcount) {
                s.add(x, f.getValue(x, z), z);
            }
        }
        XYZSeriesCollection dataset = new XYZSeriesCollection();
        dataset.add(s);
        return dataset;
    }
    
}
