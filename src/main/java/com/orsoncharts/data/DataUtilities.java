/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package com.orsoncharts.data;

import com.orsoncharts.axis.Range;
import com.orsoncharts.graphics3d.ArgChecks;

/**
 * Some data utility methods.
 */
public class DataUtilities {
    
    /**
     * Returns the range of values in the specified data structure (a three
     * dimensional cube).  If there is no data, this method returns
     * <code>null</code>.
     * 
     * @param data  the data (<code>null</code> not permitted).
     * 
     * @return The range of data values (possibly <code>null</code>).
     */
    public static Range findValueRange(Values3D data) {
        return findValueRange(data, Double.NaN);
    }

    /**
     * Returns the range of values in the specified data cube, or 
     * <code>null</code> if there is no data.
     * 
     * @param data  the data (<code>null</code> not permitted).
     * @param base  a value that must be included in the range (often 0).
     * 
     * @return The range (possibly <code>null</code>). 
     */
    public static Range findValueRange(Values3D data, double base) {
        ArgChecks.nullNotPermitted(data, "data");
        double min = Double.POSITIVE_INFINITY;
        double max = Double.NEGATIVE_INFINITY;
        for (int series = 0; series < data.getSeriesCount(); series++) {
            for (int row = 0; row < data.getRowCount(); row++) {
                for (int col = 0; col < data.getColumnCount(); col++) {
                    double d = data.getDoubleValue(series, row, col);
                    if (!Double.isNaN(d)) {
                        min = Math.min(min, d);
                        max = Math.max(max, d);
                    }
                }
            }
        }
        // include the special value in the range
        if (!Double.isNaN(base)) {
             min = Math.min(min, base);
             max = Math.max(max, base);
        }
        if (min <= max) {
            return new Range(min, max);
        } else {
            return null;
        }
    }
    
    public static Range findStackedValueRange(Values3D data) {
        return findStackedValueRange(data, 0.0);
    }
    
    public static Range findStackedValueRange(Values3D data, double base) {
        ArgChecks.nullNotPermitted(data, "data");
        double min = base;
        double max = base;
        int seriesCount = data.getSeriesCount();
        for (int row = 0; row < data.getRowCount(); row++) {
            for (int col = 0; col < data.getColumnCount(); col++) {
                double[] total = stackSubTotal(data, base, seriesCount, row, 
                        col);
                min = Math.min(min, total[0]);
                max = Math.max(max, total[1]);
            }
        }
        if (min <= max) {
            return new Range(min, max);
        } else {
            return null;
        }        
    }
    
    /**
     * Returns the positive and negative subtotals of all the series 
     * preceding the specified series.  These values can be used as the base
     * value for bars in a stacked bar chart.
     * 
     * @param data  the data (<code>null</code> not permitted).
     * @param base
     * @param series
     * @param row
     * @param column
     * 
     * @return 
     */
    public static double[] stackSubTotal(Values3D data, double base, int series, 
            int row, int column) {
        double neg = base;
        double pos = base;
        for (int s = 0; s < series; s++) {
            double v = data.getDoubleValue(s, row, column);
            if (v > 0.0) {
                pos = pos + v;
            } else if (v < 0.0) {
                neg = neg - v;
            }
        }
        return new double[] { neg, pos };
    }
    
}
