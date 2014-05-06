/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.orsoncharts.demo;

import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.Colors;
import com.orsoncharts.data.DataUtils;
import com.orsoncharts.data.JSONUtils;
import com.orsoncharts.data.KeyedValues3D;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.demo.swing.ScatterPlot3DDemo3;
import com.orsoncharts.graphics3d.ViewPoint3D;
import com.orsoncharts.legend.LegendAnchor;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.renderer.xyz.ScatterXYZRenderer;
import com.orsoncharts.style.StandardChartStyle;
import com.orsoncharts.table.TextElement;
import com.orsoncharts.util.Orientation;
import java.awt.Font;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 *
 * @author dgilbert
 */
public class ScatterPlot3D3 {
    
    
    public static XYZDataset[] createDatasets() {
        XYZDataset[] datasets = new XYZDataset[4];
        datasets[0] = createDataset("sepal length", "sepal width", 
                "petal length");
        datasets[1] = createDataset("sepal length", "sepal width", 
                "petal width");
        datasets[2] = createDataset("sepal length", "petal width", 
                "petal length");
        datasets[3] = createDataset("sepal width", "petal width", 
                "petal length");
        return datasets;
    }
    
    public static Chart3D createChart(String title, XYZDataset dataset, String xLabel, 
            String yLabel, String zLabel) {
        Chart3D chart = Chart3DFactory.createScatterChart(null, null, 
                dataset, xLabel, yLabel, zLabel);
        TextElement title1 = new TextElement(title);
        title1.setFont(StandardChartStyle.createDefaultFont(Font.PLAIN, 16));
        chart.setTitle(title1);
        chart.setLegendAnchor(LegendAnchor.BOTTOM_LEFT);
        chart.setLegendOrientation(Orientation.VERTICAL);
        XYZPlot plot = (XYZPlot) chart.getPlot();
        ScatterXYZRenderer renderer = (ScatterXYZRenderer) plot.getRenderer();
        renderer.setSize(0.15);
        renderer.setColors(Colors.createIntenseColors());
        chart.setViewPoint(ViewPoint3D.createAboveLeftViewPoint(40));
        return chart;
    }
    
    /**
     * Reads a dataset from the file iris.txt.
     * 
     * @return A sample dataset.
     */
    private static XYZDataset createDataset(Comparable<?> xKey, 
            Comparable<?> yKey, Comparable<?> zKey) {
        Reader in = new InputStreamReader(
                ScatterPlot3DDemo3.class.getResourceAsStream("../iris.txt"));
        KeyedValues3D data;
        try {
            data = JSONUtils.readKeyedValues3D(in);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return DataUtils.extractXYZDatasetFromColumns(data, xKey, yKey, zKey);
    }

 
}
