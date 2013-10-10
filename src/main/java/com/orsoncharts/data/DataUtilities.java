/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.data;

import com.orsoncharts.Range;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.util.ArgChecks;

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
     * @param base  a value that must be included in the range (often 0).  This
     *         argument is ignored if it is <code>Double.NaN</code>.
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
    
    /**
     * Finds the range of values in the dataset considering that each series
     * is stacked on top of the other.
     * 
     * @param data  the data (<code>null</code> not permitted).
     * 
     * @return The range.
     */
    public static Range findStackedValueRange(Values3D data) {
        return findStackedValueRange(data, 0.0);
    }
    
    /**
     * Finds the range of values in the dataset considering that each series
     * is stacked on top of the others, starting at the base value.
     * 
     * @param data  the data values (<code>null</code> not permitted).
     * @param base  the base value.
     * 
     * @return The range.
     */
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
     * @return The positive and negative subtotals.
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
    
    /**
     * Returns the range of x-values in the specified dataset.
     *
     * @param dataset  the dataset (<code>null</code> not permitted).
     * 
     * @return The range. 
     */
    public static Range findXRange(XYZDataset dataset) {
        return findXRange(dataset, Double.NaN);    
    }
    
    /**
     * Returns the range of x-values in the specified dataset plus the
     * special value <code>inc</code> (ignored if it is 
     * <code>Double.NaN</code>).
     *
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param inc  an additional x-value to include.
     * 
     * @return The range. 
     */
    public static Range findXRange(XYZDataset dataset, double inc) {
        ArgChecks.nullNotPermitted(dataset, "dataset");
        double min = Double.POSITIVE_INFINITY;
        double max = Double.NEGATIVE_INFINITY;
        for (int s = 0; s < dataset.getSeriesCount(); s++) {
            for (int i = 0; i < dataset.getItemCount(s); i++) {
                double x = dataset.getX(s, i);
                if (!Double.isNaN(x)) {
                    min = Math.min(x, min);
                    max = Math.max(x, max);
                }
            }
        }
        if (!Double.isNaN(inc)) {
            min = Math.min(inc, min);
            max = Math.max(inc, max);
        }
        if (min <= max) {
            return new Range(min, max);
        } else {
            return null;
        }        
    }
    
    /**
     * Returns the range of y-values in the specified dataset.
     *
     * @param dataset  the dataset (<code>null</code> not permitted).
     * 
     * @return The range. 
     */
    public static Range findYRange(XYZDataset dataset) {
        return findYRange(dataset, Double.NaN);
    }
    
    /**
     * Returns the range of y-values in the specified dataset plus the
     * special value <code>inc</code> (ignored if it is 
     * <code>Double.NaN</code>).
     *
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param inc  an additional x-value to include.
     * 
     * @return The range. 
     */
    public static Range findYRange(XYZDataset dataset, double inc) {
        ArgChecks.nullNotPermitted(dataset, "dataset");
        double min = Double.POSITIVE_INFINITY;
        double max = Double.NEGATIVE_INFINITY;
        for (int s = 0; s < dataset.getSeriesCount(); s++) {
            for (int i = 0; i < dataset.getItemCount(s); i++) {
                double y = dataset.getY(s, i);
                if (!Double.isNaN(y)) {
                    min = Math.min(y, min);
                    max = Math.max(y, max);
                }
            }
        }
        if (!Double.isNaN(inc)) {
            min = Math.min(inc, min);
            max = Math.max(inc, max);
        }
        if (min <= max) {
            return new Range(min, max);
        } else {
            return null;
        }        
    }
    
    /**
     * Returns the range of z-values in the specified dataset.
     *
     * @param dataset  the dataset (<code>null</code> not permitted).
     * 
     * @return The range. 
     */
    public static Range findZRange(XYZDataset dataset) {
        return findZRange(dataset, Double.NaN);
    }
    
    /**
     * Returns the range of z-values in the specified dataset plus the
     * special value <code>inc</code> (ignored if it is 
     * <code>Double.NaN</code>).
     *
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param inc  an additional x-value to include.
     * 
     * @return The range. 
     */
    public static Range findZRange(XYZDataset dataset, double inc) {
        ArgChecks.nullNotPermitted(dataset, "dataset");
        double min = Double.POSITIVE_INFINITY;
        double max = Double.NEGATIVE_INFINITY;
        for (int s = 0; s < dataset.getSeriesCount(); s++) {
            for (int i = 0; i < dataset.getItemCount(s); i++) {
                double z = dataset.getZ(s, i);
                if (!Double.isNaN(z)) {
                    min = Math.min(z, min);
                    max = Math.max(z, max);
                }
            }
        }
        if (!Double.isNaN(inc)) {
            min = Math.min(inc, min);
            max = Math.max(inc, max);
        }
        if (min <= max) {
            return new Range(min, max);
        } else {
            return null;
        }        
    }
 
}
