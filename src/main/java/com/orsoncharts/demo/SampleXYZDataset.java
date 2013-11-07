/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.demo;

import com.orsoncharts.data.AbstractDataset3D;
import com.orsoncharts.data.xyz.XYZDataset;
import java.util.ArrayList;
import java.util.List;

/**
 * A sample {@link XYZDataset} implementation.  This is used for some 
 * demo applications.
 */
public class SampleXYZDataset extends AbstractDataset3D implements XYZDataset {

    private double[][] xValues;
    private double[][] yValues;
    private double[][] zValues;

    public SampleXYZDataset(int seriesCount, int pointCount) {
        this.xValues = new double[seriesCount][pointCount];
        this.yValues = new double[seriesCount][pointCount];
        this.zValues = new double[seriesCount][pointCount];
        for (int s = 0; s < seriesCount; s++) {
            for (int i = 0; i < pointCount; i++) {
                xValues[s][i] = Math.random() * 10.0;
                yValues[s][i] = Math.random() * 20000.0;
                zValues[s][i] = Math.random() * 0.005;
            }
        }
    }

    @Override
    public int getSeriesCount() {
        return xValues.length;
    }

    @Override
    public int getItemCount(int series) {
        return xValues[series].length;
    }

    @Override
    public double getX(int series, int item) {
        return xValues[series][item];
    }

    @Override
    public double getY(int series, int item) {
        return yValues[series][item];
    }

    @Override
    public double getZ(int series, int item) {
        return zValues[series][item];
    }

    @Override
    public List<Comparable> getSeriesKeys() {
        List<Comparable> result = new ArrayList<Comparable>();
        for (int s = 0; s < getSeriesCount(); s++) {
            result.add("S" + s);
        }
        return result;
    }

    @Override
    public int getSeriesIndex(Comparable key) {
        return getSeriesKeys().indexOf(key);
    }

}
