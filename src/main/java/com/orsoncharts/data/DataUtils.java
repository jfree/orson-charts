/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-2016, by Object Refinery Limited.  All rights reserved.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 * 
 * If you do not wish to be bound by the terms of the GPL, an alternative
 * commercial license can be purchased.  For details, please see visit the
 * Orson Charts home page:
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 */

package com.orsoncharts.data;

import java.util.List;
import com.orsoncharts.Range;
import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.data.xyz.XYZSeries;
import com.orsoncharts.data.xyz.XYZSeriesCollection;
import com.orsoncharts.util.ArgChecks;

/**
 * Some utility methods for working with the various datasets and data
 * structures available in Orson Charts.
 */
public class DataUtils {
    
    private DataUtils() {
        // no need to create instances
    }
 
    /**
     * Returns the total of the values in the list.  Any {@code null}
     * values are ignored.
     * 
     * @param values  the values ({@code null} not permitted).
     * 
     * @return The total of the values in the list. 
     */
    public static double total(Values<Number> values) {
        double result = 0.0;
        for (int i = 0; i < values.getItemCount(); i++) {
            Number n = values.getValue(i);
            if (n != null) {
                result = result + n.doubleValue();
            }
        }
        return result;
    }
    
    /**
     * Returns the count of the non-{@code null} entries in the dataset
     * for the specified series.  An {@code IllegalArgumentException} is
     * thrown if the {@code seriesKey} is not present in the data.
     * 
     * @param <S>  the series key (must implement Comparable).
     * @param data  the dataset ({@code null} not permitted).
     * @param seriesKey  the series key ({@code null} not permitted).
     * 
     * @return The count.
     * 
     * @since 1.2
     */
    public static <S extends Comparable<S>> int count(
            KeyedValues3D<S,?,?,?> data, S seriesKey) {
        ArgChecks.nullNotPermitted(data, "data");
        ArgChecks.nullNotPermitted(seriesKey, "seriesKey");
        int seriesIndex = data.getSeriesIndex(seriesKey);
        if (seriesIndex < 0) {
            throw new IllegalArgumentException("Series not found: " 
                    + seriesKey);
        }
        int count = 0;
        int rowCount = data.getRowCount();
        int columnCount = data.getColumnCount();
        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < columnCount; c++) {
                Number n = (Number) data.getValue(seriesIndex, r, c);
                if (n != null) {
                    count++;
                }
            }
        }
        return count;
    }
        
    /**
     * Returns the count of the non-{@code null} entries in the dataset
     * for the specified row (all series).
     * 
     * @param <R>  the row key (must implement Comparable).
     * @param data  the dataset ({@code null} not permitted).
     * @param rowKey  the row key ({@code null} not permitted).
     * 
     * @return The count.
     * 
     * @since 1.2
     */
    public static <R extends Comparable<R>> int countForRow(
            KeyedValues3D<?, R, ?, ?> data, R rowKey) {
        ArgChecks.nullNotPermitted(data, "data");
        ArgChecks.nullNotPermitted(rowKey, "rowKey");
        int rowIndex = data.getRowIndex(rowKey);
        if (rowIndex < 0) {
            throw new IllegalArgumentException("Row not found: " + rowKey);
        }
        int count = 0;
        int seriesCount = data.getSeriesCount();
        int columnCount = data.getColumnCount();
        for (int s = 0; s < seriesCount; s++) {
            for (int c = 0; c < columnCount; c++) {
                Number n = (Number) data.getValue(s, rowIndex, c);
                if (n != null) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Returns the count of the non-{@code null} entries in the dataset
     * for the specified column (all series).
     * 
     * @param <C>  the column key (must implement Comparable).
     * @param data  the dataset ({@code null} not permitted).
     * @param columnKey  the column key ({@code null} not permitted).
     * 
     * @return The count.
     * 
     * @since 1.2
     */
    public static <C extends Comparable<C>> int countForColumn(
            KeyedValues3D<?, ?, C, ?> data, C columnKey) {
        ArgChecks.nullNotPermitted(data, "data");
        ArgChecks.nullNotPermitted(columnKey, "columnKey");
        int columnIndex = data.getColumnIndex(columnKey);
        if (columnIndex < 0) {
            throw new IllegalArgumentException("Column not found: " 
                    + columnKey);
        }
        int count = 0;
        int seriesCount = data.getSeriesCount();
        int rowCount = data.getRowCount();
        for (int s = 0; s < seriesCount; s++) {
            for (int r = 0; r < rowCount; r++) {
                Number n = (Number) data.getValue(s, r, columnIndex);
                if (n != null) {
                    count++;
                }
            }
        }
        return count;
    }
        
    /**
     * Returns the total of the non-{@code null} values in the dataset
     * for the specified series.  If there is no series with the specified 
     * key, this method will throw an {@code IllegalArgumentException}.
     * 
     * @param <S>  the series key (must implement Comparable).
     * @param data  the dataset ({@code null} not permitted).
     * @param seriesKey  the series key ({@code null} not permitted).
     * 
     * @return The total.
     * 
     * @since 1.2
     */
    public static <S extends Comparable<S>> double total(
            KeyedValues3D<S, ?, ?, ? extends Number> data, S seriesKey) {
        ArgChecks.nullNotPermitted(data, "data");
        ArgChecks.nullNotPermitted(seriesKey, "seriesKey");
        int seriesIndex = data.getSeriesIndex(seriesKey);
        if (seriesIndex < 0) {
            throw new IllegalArgumentException("Series not found: " 
                    + seriesKey);
        }
        double total = 0.0;
        int rowCount = data.getRowCount();
        int columnCount = data.getColumnCount();
        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < columnCount; c++) {
                Number n = (Number) data.getValue(seriesIndex, r, c);
                if (n != null) {
                    total += n.doubleValue();
                }
            }
        }
        return total;
    }

    /**
     * Returns the total of the non-{@code null} entries in the dataset
     * for the specified row (all series).
     * 
     * @param <R>  the row key (must implement Comparable).
     * @param data  the dataset ({@code null} not permitted).
     * @param rowKey  the row key ({@code null} not permitted).
     * 
     * @return The total.
     * 
     * @since 1.2
     */
    public static <R extends Comparable<R>> double totalForRow(
            KeyedValues3D<?, R, ?, ? extends Number> data, R rowKey) {
        ArgChecks.nullNotPermitted(data, "data");
        ArgChecks.nullNotPermitted(rowKey, "rowKey");
        int rowIndex = data.getRowIndex(rowKey);
        if (rowIndex < 0) {
            throw new IllegalArgumentException("Row not found: " + rowKey);
        }
        double total = 0.0;
        int seriesCount = data.getSeriesCount();
        int columnCount = data.getColumnCount();
        for (int s = 0; s < seriesCount; s++) {
            for (int c = 0; c < columnCount; c++) {
                Number n = (Number) data.getValue(s, rowIndex, c);
                if (n != null) {
                    total += n.doubleValue();
                }
            }
        }
        return total;
    }

    /**
     * Returns the total of the non-{@code null} entries in the dataset
     * for the specified column (all series).
     * 
     * @param <C>  the column key (must implement Comparable).
     * @param data  the dataset ({@code null} not permitted).
     * @param columnKey  the row key ({@code null} not permitted).
     * 
     * @return The total.
     * 
     * @since 1.2
     */
    public static <C extends Comparable<C>> double totalForColumn(
            KeyedValues3D<?, ?, C, ? extends Number> data, C columnKey) {
        ArgChecks.nullNotPermitted(data, "data");
        ArgChecks.nullNotPermitted(columnKey, "columnKey");
        int columnIndex = data.getColumnIndex(columnKey);
        if (columnIndex < 0) {
            throw new IllegalArgumentException("Column not found: " 
                    + columnKey);
        }
        double total = 0.0;
        int seriesCount = data.getSeriesCount();
        int rowCount = data.getRowCount();
        for (int s = 0; s < seriesCount; s++) {
            for (int r = 0; r < rowCount; r++) {
                Number n = (Number) data.getValue(s, r, columnIndex);
                if (n != null) {
                    total += n.doubleValue();
                }
            }
        }
        return total;
    }

    /**
     * Returns the range of values in the specified data structure (a three
     * dimensional cube).  If there is no data, this method returns
     * {@code null}.
     * 
     * @param data  the data ({@code null} not permitted).
     * 
     * @return The range of data values (possibly {@code null}).
     */
    public static Range findValueRange(Values3D<? extends Number> data) {
        return findValueRange(data, Double.NaN);
    }

    /**
     * Returns the range of values in the specified data cube, or 
     * {@code null} if there is no data.  The range will be expanded, if 
     * required, to include the {@code base} value (unless it
     * is {@code Double.NaN} in which case it is ignored).
     * 
     * @param data  the data ({@code null} not permitted).
     * @param base  a value that must be included in the range (often 0).  This
     *         argument is ignored if it is {@code Double.NaN}.
     * 
     * @return The range (possibly {@code null}). 
     */
    public static Range findValueRange(Values3D<? extends Number> data, 
            double base) {
        return findValueRange(data, base, true);
    }
    
    /**
    /**
     * Returns the range of values in the specified data cube, or 
     * {@code null} if there is no data.  The range will be expanded, if 
     * required, to include the {@code base} value (unless it
     * is {@code Double.NaN} in which case it is ignored).
     * 
     * @param data  the data ({@code null} not permitted).
     * @param base  a value that must be included in the range (often 0).  This
     *         argument is ignored if it is {@code Double.NaN}.
     * @param finite  if {@code true} infinite values will be ignored.
     * 
     * @return The range (possibly {@code null}).   
     * 
     * @since 1.4
     */
    public static Range findValueRange(Values3D<? extends Number> data,
            double base, boolean finite) {
        ArgChecks.nullNotPermitted(data, "data");
        double min = Double.POSITIVE_INFINITY;
        double max = Double.NEGATIVE_INFINITY;
        for (int series = 0; series < data.getSeriesCount(); series++) {
            for (int row = 0; row < data.getRowCount(); row++) {
                for (int col = 0; col < data.getColumnCount(); col++) {
                    double d = data.getDoubleValue(series, row, col);
                    if (!Double.isNaN(d)) {
                        if (!finite || !Double.isInfinite(d)) {
                            min = Math.min(min, d);
                            max = Math.max(max, d);
                        }
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
     * @param data  the data ({@code null} not permitted).
     * 
     * @return The range.
     */
    public static Range findStackedValueRange(Values3D<? extends Number> data) {
        return findStackedValueRange(data, 0.0);
    }
    
    /**
     * Finds the range of values in the dataset considering that each series
     * is stacked on top of the others, starting at the base value.
     * 
     * @param data  the data values ({@code null} not permitted).
     * @param base  the base value.
     * 
     * @return The range.
     */
    public static Range findStackedValueRange(Values3D<? extends Number> data, 
            double base) {
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
     * Returns the positive and negative subtotals of the values for all the 
     * series preceding the specified series.  
     * <br><br>
     * One application for this method is to compute the base values for 
     * individual bars in a stacked bar chart.
     * 
     * @param data  the data ({@code null} not permitted).
     * @param base  the initial base value (normally {@code 0.0}, but the 
     *     values can be stacked from a different starting point).
     * @param series  the index of the current series (series with lower indices
     *     are included in the sub-totals).
     * @param row  the row index of the required item.
     * @param column  the column index of the required item.
     * 
     * @return The subtotals, where {@code result[0]} is the subtotal of
     *     the negative data items, and {@code result[1]} is the subtotal
     *     of the positive data items.
     */
    public static double[] stackSubTotal(Values3D<? extends Number> data, 
            double base, int series, int row, int column) {
        double neg = base;
        double pos = base;
        for (int s = 0; s < series; s++) {
            double v = data.getDoubleValue(s, row, column);
            if (v > 0.0) {
                pos = pos + v;
            } else if (v < 0.0) {
                neg = neg + v;
            }
        }
        return new double[] { neg, pos };
    }

    /**
     * Returns the total of the non-{@code NaN} entries in the dataset
     * for the specified series.
     * 
     * @param <S>  the series key (must implement Comparable).
     * @param data  the dataset ({@code null} not permitted).
     * @param seriesKey  the series key ({@code null} not permitted).
     * 
     * @return The count.
     * 
     * @since 1.2
     */
    public static <S extends Comparable<S>> double total(XYZDataset<S> data, 
            S seriesKey) {
        ArgChecks.nullNotPermitted(data, "data");
        ArgChecks.nullNotPermitted(seriesKey, "seriesKey");
        int seriesIndex = data.getSeriesIndex(seriesKey);
        if (seriesIndex < 0) {
            throw new IllegalArgumentException("Series not found: " 
                    + seriesKey);
        }
        double total = 0;
        int itemCount = data.getItemCount(seriesIndex);
        for (int item = 0; item < itemCount; item++) {
            double y = data.getY(seriesIndex, item);
            if (!Double.isNaN(y)) {
                total += y;
            }
        }
        return total;
    }
    
    /**
     * Returns the range of x-values in the dataset by iterating over all
     * values (and ignoring {@code Double.NaN} and infinite values). 
     * If there are no values eligible for inclusion in the range, this method 
     * returns {@code null}.     
     *
     * @param dataset  the dataset ({@code null} not permitted).
     * 
     * @return The range (possibly {@code null}).
     */
    public static Range findXRange(XYZDataset dataset) {
        return findXRange(dataset, Double.NaN);    
    }
    
    /**
     * Returns the range of x-values in the dataset by iterating over all
     * values (and ignoring {@code Double.NaN} values).  The range will be 
     * extended if necessary to include {@code inc} (unless it is 
     * {@code Double.NaN} in which case it is ignored).  Infinite values 
     * in the dataset will be ignored.  If there are no values eligible for 
     * inclusion in the range, this method returns {@code null}.
     *
     * @param dataset  the dataset ({@code null} not permitted).
     * @param inc  an additional x-value to include.
     * 
     * @return The range (possibly {@code null}).
     */
    public static Range findXRange(XYZDataset dataset, double inc) {
        return findXRange(dataset, inc, true);
    }
    
    /**
     * Returns the range of x-values in the dataset by iterating over all
     * values (and ignoring {@code Double.NaN} values).  The range will be 
     * extended if necessary to include {@code inc} (unless it is 
     * {@code Double.NaN} in which case it is ignored).  If the
     * {@code finite} flag is set, infinite values in the dataset will be 
     * ignored.  If there are no values eligible for inclusion in the range, 
     * this method returns {@code null}.
     * 
     * @param dataset  the dataset ({@code null} not permitted).
     * @param inc  an additional x-value to include.
     * @param finite  a flag indicating whether to exclude infinite values.
     * 
     * @return The range (possibly {@code null}).
     * 
     * @since 1.4
     */
    public static Range findXRange(XYZDataset dataset, double inc, 
            boolean finite) {
        ArgChecks.nullNotPermitted(dataset, "dataset");
        double min = Double.POSITIVE_INFINITY;
        double max = Double.NEGATIVE_INFINITY;
        for (int s = 0; s < dataset.getSeriesCount(); s++) {
            for (int i = 0; i < dataset.getItemCount(s); i++) {
                double x = dataset.getX(s, i);
                if (!Double.isNaN(x)) {
                    if (!finite || !Double.isInfinite(x)) {
                        min = Math.min(x, min);
                        max = Math.max(x, max);
                    }
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
     * Returns the range of y-values in the dataset by iterating over all
     * values (and ignoring {@code Double.NaN} and infinite values). 
     * If there are no values eligible for inclusion in the range, this method 
     * returns {@code null}.     
     *
     * @param dataset  the dataset ({@code null} not permitted).
     * 
     * @return The range. 
     */
    public static Range findYRange(XYZDataset dataset) {
        return findYRange(dataset, Double.NaN);
    }
    
    /**
     * Returns the range of y-values in the dataset by iterating over all
     * values (and ignoring {@code Double.NaN} values).  The range will be 
     * extended if necessary to include {@code inc} (unless it is 
     * {@code Double.NaN} in which case it is ignored).  Infinite values 
     * in the dataset will be ignored.  If there are no values eligible for 
     * inclusion in the range, this method returns {@code null}.
     *
     * @param dataset  the dataset ({@code null} not permitted).
     * @param inc  an additional x-value to include.
     * 
     * @return The range. 
     */
    public static Range findYRange(XYZDataset dataset, double inc) {
        return findYRange(dataset, inc, true);
    }
    
    /**
     * Returns the range of y-values in the dataset by iterating over all
     * values (and ignoring {@code Double.NaN} values).  The range will be 
     * extended if necessary to include {@code inc} (unless it is 
     * {@code Double.NaN} in which case it is ignored).  If the
     * {@code finite} flag is set, infinite values in the dataset will be 
     * ignored.  If there are no values eligible for inclusion in the range, 
     * this method returns {@code null}.
     * 
     * @param dataset  the dataset ({@code null} not permitted).
     * @param inc  an additional y-value to include.
     * @param finite  a flag indicating whether to exclude infinite values.
     * 
     * @return The range (possibly {@code null}).
     * 
     * @since 1.4
     */
    public static Range findYRange(XYZDataset dataset, double inc, 
            boolean finite) {
        ArgChecks.nullNotPermitted(dataset, "dataset");
        double min = Double.POSITIVE_INFINITY;
        double max = Double.NEGATIVE_INFINITY;
        for (int s = 0; s < dataset.getSeriesCount(); s++) {
            for (int i = 0; i < dataset.getItemCount(s); i++) {
                double y = dataset.getY(s, i);
                if (!Double.isNaN(y)) {
                    if (!finite || !Double.isInfinite(y)) {
                        min = Math.min(y, min);
                        max = Math.max(y, max);
                    }
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
     * Returns the range of z-values in the dataset by iterating over all
     * values (and ignoring {@code Double.NaN} and infinite values). 
     * If there are no values eligible for inclusion in the range, this method 
     * returns {@code null}.     
     *
     * @param dataset  the dataset ({@code null} not permitted).
     * 
     * @return The range (possibly {@code null}). 
     */
    public static Range findZRange(XYZDataset dataset) {
        return findZRange(dataset, Double.NaN);
    }
    
    /**
     * Returns the range of z-values in the dataset by iterating over all
     * values (and ignoring {@code Double.NaN} values).  The range will be 
     * extended if necessary to include {@code inc} (unless it is 
     * {@code Double.NaN} in which case it is ignored).  Infinite values 
     * in the dataset will be ignored.  If there are no values eligible for 
     * inclusion in the range, this method returns {@code null}.
     *
     * @param dataset  the dataset ({@code null} not permitted).
     * @param inc  an additional x-value to include.
     * 
     * @return The range (possibly {@code null}).
     */
    public static Range findZRange(XYZDataset dataset, double inc) {
        return findZRange(dataset, inc, true);
    }
    
    /**
     * Returns the range of z-values in the dataset by iterating over all
     * values (and ignoring {@code Double.NaN} values).  The range will be 
     * extended if necessary to include {@code inc} (unless it is 
     * {@code Double.NaN} in which case it is ignored).  If the
     * {@code finite} flag is set, infinite values in the dataset will be 
     * ignored.  If there are no values eligible for inclusion in the range, 
     * this method returns {@code null}.
     * 
     * @param dataset  the dataset ({@code null} not permitted).
     * @param inc  an additional z-value to include.
     * @param finite  a flag indicating whether to exclude infinite values.
     * 
     * @return The range (possibly {@code null}).
     * 
     * @since 1.4
     */
    public static Range findZRange(XYZDataset dataset, double inc, 
            boolean finite) {
        ArgChecks.nullNotPermitted(dataset, "dataset");
        ArgChecks.finiteRequired(inc, "inc");
        double min = Double.POSITIVE_INFINITY;
        double max = Double.NEGATIVE_INFINITY;
        for (int s = 0; s < dataset.getSeriesCount(); s++) {
            for (int i = 0; i < dataset.getItemCount(s); i++) {
                double z = dataset.getZ(s, i);
                if (!Double.isNaN(z)) {
                    if (!finite || !Double.isInfinite(z)) {
                        min = Math.min(z, min);
                        max = Math.max(z, max);
                    }
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
     * Creates an {@link XYZDataset} by extracting values from specified 
     * rows in a {@link KeyedValues3D} instance, across all the available
     * columns (items where any of the x, y or z values is {@code null} 
     * are skipped).  The new dataset contains a copy of the data and is 
     * completely independent of the {@code source} dataset.  
     * <br><br>
     * Note that {@link CategoryDataset3D} is an extension of 
     * {@link KeyedValues3D} so you can use this method for any implementation
     * of the {@code CategoryDataset3D} interface.
     * 
     * @param <S>  the series key (must implement Comparable).
     * @param <R>  the row key (must implement Comparable).
     * @param <C>  the column key (must implement Comparable).
     * @param source  the source data ({@code null} not permitted).
     * @param xRowKey  the row key for x-values ({@code null} not permitted).
     * @param yRowKey  the row key for y-values ({@code null} not permitted).
     * @param zRowKey  the row key for z-values ({@code null} not permitted).
     * 
     * @return A new dataset. 
     * 
     * @since 1.3
     */
    public static <S extends Comparable<S>, R extends Comparable<R>, 
            C extends Comparable<C>> XYZDataset extractXYZDatasetFromRows(
            KeyedValues3D<S, R, C, ? extends Number> source,
            R xRowKey, R yRowKey, R zRowKey) {
        return extractXYZDatasetFromRows(source, xRowKey, yRowKey, zRowKey,
                NullConversion.SKIP, null);
    }

    /**
     * Creates an {@link XYZDataset} by extracting values from specified 
     * rows in a {@link KeyedValues3D} instance.  The new dataset contains 
     * a copy of the data and is completely independent of the 
     * {@code source} dataset.  Note that {@link CategoryDataset3D} is an 
     * extension of {@link KeyedValues3D}.
     * <br><br>
     * Special handling is provided for items that contain {@code null}
     * values.  The caller may pass in an {@code exceptions} list (
     * normally empty) that will be populated with the keys of the items that
     * receive special handling, if any.
     * 
     * @param <S>  the series key (must implement Comparable).
     * @param <R>  the row key (must implement Comparable).
     * @param <C>  the column key (must implement Comparable).
     * @param source  the source data ({@code null} not permitted).
     * @param xRowKey  the row key for x-values ({@code null} not permitted).
     * @param yRowKey  the row key for y-values ({@code null} not permitted).
     * @param zRowKey  the row key for z-values ({@code null} not permitted).
     * @param nullConversion  specifies the treatment for {@code null} 
     *         values in the dataset ({@code null} not permitted).
     * @param exceptions  a list that, if not null, will be populated with 
     *         keys for the items in the source dataset that contain 
     *         {@code null} values ({@code null} permitted).
     * 
     * @return A new dataset. 
     * 
     * @since 1.3
     */
    public static <S extends Comparable<S>, R extends Comparable<R>, 
            C extends Comparable<C>> XYZDataset extractXYZDatasetFromRows(
            KeyedValues3D<S, R, C, ? extends Number> source,
            R xRowKey, R yRowKey, R zRowKey, NullConversion nullConversion, 
            List<KeyedValues3DItemKey> exceptions) {

        ArgChecks.nullNotPermitted(source, "source");
        ArgChecks.nullNotPermitted(xRowKey, "xRowKey");
        ArgChecks.nullNotPermitted(yRowKey, "yRowKey");
        ArgChecks.nullNotPermitted(zRowKey, "zRowKey");
        XYZSeriesCollection<S> dataset = new XYZSeriesCollection<S>();
        for (S seriesKey : source.getSeriesKeys()) {
            XYZSeries<S> series = new XYZSeries<S>(seriesKey);
            for (C colKey : source.getColumnKeys()) {
                Number x = source.getValue(seriesKey, xRowKey, colKey);
                Number y = source.getValue(seriesKey, yRowKey, colKey);
                Number z = source.getValue(seriesKey, zRowKey, colKey);
                if (x != null && y != null && z != null) {
                    series.add(x.doubleValue(), y.doubleValue(), 
                            z.doubleValue());
                } else {
                    if (exceptions != null) {
                        // add only one exception per data value
                        R rrKey = zRowKey;
                        if (x == null) {
                            rrKey = xRowKey;
                        } else if (y == null) {
                            rrKey = yRowKey;
                        }
                        exceptions.add(new KeyedValues3DItemKey<S, R, C>(seriesKey, 
                                rrKey, colKey));
                    }
                    if (nullConversion.equals(NullConversion.THROW_EXCEPTION)) {
                        Comparable rrKey = zRowKey;
                        if (x == null) {
                            rrKey = yRowKey;
                        } else if (y == null) {
                            rrKey = yRowKey;
                        }
                        throw new RuntimeException("There is a null value for "
                                + "the item [" + seriesKey +", " + rrKey + ", " 
                                + colKey + "].");
                    }
                    if (nullConversion != NullConversion.SKIP) {
                        double xx = convert(x, nullConversion);
                        double yy = convert(y, nullConversion);
                        double zz = convert(z, nullConversion);
                        series.add(xx, yy, zz);
                    }
                }
            }
            dataset.add(series);
        }
        return dataset;
    }
        
    /**
     * Creates an {@link XYZDataset} by extracting values from specified 
     * columns in a {@link KeyedValues3D} instance, across all the available
     * rows (items where any of the x, y or z values is {@code null} are 
     * skipped).  The new dataset contains a copy of the data and is completely
     * independent of the {@code source} dataset.  
     * <br><br>
     * Note that {@link CategoryDataset3D} is an extension of 
     * {@link KeyedValues3D} so you can use this method for any implementation
     * of the {@code CategoryDataset3D} interface.
     * 
     * @param <S>  the series key (must implement Comparable).
     * @param <R>  the row key (must implement Comparable).
     * @param <C>  the column key (must implement Comparable).
     * @param source  the source data ({@code null} not permitted).
     * @param xColKey  the column key for x-values ({@code null} not permitted).
     * @param yColKey  the column key for y-values ({@code null} not permitted).
     * @param zColKey  the column key for z-values ({@code null} not permitted).
     * 
     * @return A new dataset. 
     * 
     * @since 1.3
     */
    public static <S extends Comparable<S>, R extends Comparable<R>, 
            C extends Comparable<C>> XYZDataset<S> extractXYZDatasetFromColumns(
            KeyedValues3D<S, R, C, ? extends Number> source,
            C xColKey, C yColKey, C zColKey) {
        return extractXYZDatasetFromColumns(source, xColKey, yColKey, zColKey,
                NullConversion.SKIP, null);
    }

    /**
     * Creates an {@link XYZDataset} by extracting values from specified 
     * columns in a {@link KeyedValues3D} instance.  The new dataset contains 
     * a copy of the data and is completely independent of the 
     * {@code source} dataset.  Note that {@link CategoryDataset3D} is an 
     * extension of {@link KeyedValues3D}.
     * <br><br>
     * Special handling is provided for items that contain {@code null}
     * values.  The caller may pass in an {@code exceptions} list (
     * normally empty) that will be populated with the keys of the items that
     * receive special handling, if any.
     * 
     * @param <S>  the series key (must implement Comparable).
     * @param <R>  the row key (must implement Comparable).
     * @param <C>  the column key (must implement Comparable).
     * @param source  the source data ({@code null} not permitted).
     * @param xColKey  the column key for x-values ({@code null} not permitted).
     * @param yColKey  the column key for y-values ({@code null} not permitted).
     * @param zColKey  the column key for z-values ({@code null} not permitted).
     * @param nullConversion  specifies the treatment for {@code null} 
     *         values in the dataset ({@code null} not permitted).
     * @param exceptions  a list that, if not null, will be populated with 
     *         keys for the items in the source dataset that contain 
     *         {@code null} values ({@code null} permitted).
     * 
     * @return A new dataset. 
     * 
     * @since 1.3
     */
    public static <S extends Comparable<S>, R extends Comparable<R>, 
            C extends Comparable<C>> 
            XYZDataset<S> extractXYZDatasetFromColumns(
            KeyedValues3D<S, R, C, ? extends Number> source,
            C xColKey, C yColKey, C zColKey, NullConversion nullConversion, 
            List<KeyedValues3DItemKey> exceptions) {

        ArgChecks.nullNotPermitted(source, "source");
        ArgChecks.nullNotPermitted(xColKey, "xColKey");
        ArgChecks.nullNotPermitted(yColKey, "yColKey");
        ArgChecks.nullNotPermitted(zColKey, "zColKey");
        XYZSeriesCollection<S> dataset = new XYZSeriesCollection<S>();
        for (S seriesKey : source.getSeriesKeys()) {
            XYZSeries<S> series = new XYZSeries<S>(seriesKey);
            for (R rowKey : source.getRowKeys()) {
                Number x = source.getValue(seriesKey, rowKey, xColKey);
                Number y = source.getValue(seriesKey, rowKey, yColKey);
                Number z = source.getValue(seriesKey, rowKey, zColKey);
                if (x != null && y != null && z != null) {
                    series.add(x.doubleValue(), y.doubleValue(), 
                            z.doubleValue());
                } else {
                    if (exceptions != null) {
                        // add only one key ref out of the possible 3 per item
                        C ccKey = zColKey;
                        if (x == null) {
                            ccKey = xColKey;
                        } else if (y == null) {
                            ccKey = yColKey;
                        }
                        exceptions.add(new KeyedValues3DItemKey<S, R, C>(
                                seriesKey, rowKey, ccKey));
                    }
                    if (nullConversion.equals(NullConversion.THROW_EXCEPTION)) {
                        Comparable<?> ccKey = zColKey;
                        if (x == null) {
                            ccKey = xColKey;
                        } else if (y == null) {
                            ccKey = yColKey;
                        }
                        throw new RuntimeException("There is a null value for "
                                + "the item [" + seriesKey +", " + rowKey + ", " 
                                + ccKey + "].");
                    }
                    if (nullConversion != NullConversion.SKIP) {
                        double xx = convert(x, nullConversion);
                        double yy = convert(y, nullConversion);
                        double zz = convert(z, nullConversion);
                        series.add(xx, yy, zz);
                    }
                }
            }
            dataset.add(series);
        }
        return dataset;
    }

    /**
     * Returns a double primitive for the specified number, with 
     * {@code null} values returning {@code Double.NaN} except in the 
     * case of {@code CONVERT_TO_ZERO} which returns 0.0.  Note that this 
     * method does not throw an exception for {@code THROW_EXCEPTION}, it
     * expects code higher up the call chain to handle that (because there is
     * not enough information here to throw a useful exception).
     * 
     * @param n  the number ({@code null} permitted).
     * @param nullConversion  the null conversion ({@code null} not 
     *         permitted).
     * 
     * @return A double primitive. 
     */
    private static double convert(Number n, NullConversion nullConversion) {
        if (n != null) {
            return n.doubleValue();
        } else {
            if (nullConversion.equals(NullConversion.CONVERT_TO_ZERO)) {
                return 0.0;
            }
            return Double.NaN;
        }
    }
    
}
