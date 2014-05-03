/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.orsoncharts.demo.fx;

import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.Chart3DPanel;
import com.orsoncharts.Colors;
import com.orsoncharts.data.DataUtils;
import com.orsoncharts.data.JSONUtils;
import com.orsoncharts.data.KeyedValues3D;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.demo.ScatterPlot3DDemo3;
import com.orsoncharts.fx.Chart3DCanvas;
import com.orsoncharts.graphics3d.ViewPoint3D;
import com.orsoncharts.legend.LegendAnchor;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.renderer.xyz.ScatterXYZRenderer;
import com.orsoncharts.style.StandardChartStyle;
import com.orsoncharts.table.TextElement;
import com.orsoncharts.util.Orientation;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javax.swing.BorderFactory;

/**
 *
 * @author dgilbert
 */
public class ScatterPlot3DFXDemo3 {
    
    /**
     * Returns a panel containing the content for the demo.  This method is
     * used across all the individual demo applications to allow aggregation 
     * into a single "umbrella" demo (OrsonChartsDemo).
     * 
     * @return A panel containing the content for the demo.
     */
    public static Node createDemoNode() {
        GridPane grid = new GridPane();
        
        // create 4 datasets
        XYZDataset dataset1 = createDataset("sepal length", "sepal width", 
                "petal length");
        Chart3D chart1 = Chart3DFactory.createScatterChart(null, null, dataset1, 
                "Sepal Length", "Sepal Width", "Petal Length");
        TextElement title1 = new TextElement("Iris Dataset : Combination 1");
        title1.setFont(StandardChartStyle.createDefaultFont(Font.PLAIN, 16));
        chart1.setTitle(title1);
        chart1.setLegendAnchor(LegendAnchor.BOTTOM_LEFT);
        chart1.setLegendOrientation(Orientation.VERTICAL);
        XYZPlot plot1 = (XYZPlot) chart1.getPlot();
        ScatterXYZRenderer renderer1 = (ScatterXYZRenderer) plot1.getRenderer();
        renderer1.setSize(0.15);
        renderer1.setColors(Colors.createIntenseColors());
        chart1.setViewPoint(ViewPoint3D.createAboveLeftViewPoint(40));
                Chart3DCanvas canvas1 = new Chart3DCanvas(chart1);

        
        XYZDataset dataset2 = createDataset("sepal length", "sepal width", 
                "petal width");
        Chart3D chart2 = Chart3DFactory.createScatterChart(
                null, null, dataset2, 
                "Sepal Length", "Sepal Width", "Petal Width");
        TextElement title2 = new TextElement("Iris Dataset : Combination 2");
        title2.setFont(StandardChartStyle.createDefaultFont(Font.PLAIN, 16));
        chart2.setTitle(title2);
        chart2.setLegendAnchor(LegendAnchor.BOTTOM_LEFT);
        chart2.setLegendOrientation(Orientation.VERTICAL);
        XYZPlot plot2 = (XYZPlot) chart2.getPlot();
        ScatterXYZRenderer renderer2 = (ScatterXYZRenderer) plot2.getRenderer();
        renderer2.setSize(0.15);
        renderer2.setColors(Colors.createIntenseColors());
        chart2.setViewPoint(ViewPoint3D.createAboveLeftViewPoint(40));

                Chart3DCanvas canvas2 = new Chart3DCanvas(chart2);

        
        XYZDataset dataset3 = createDataset("sepal length", "petal width", 
                "petal length");
        Chart3D chart3 = Chart3DFactory.createScatterChart(
                null, null, dataset3, 
                "Sepal Length", "Petal Width", "Petal Length");
        TextElement title3 = new TextElement("Iris Dataset : Combination 3");
        title3.setFont(StandardChartStyle.createDefaultFont(Font.PLAIN, 16));
        chart3.setTitle(title3);
        chart3.setLegendAnchor(LegendAnchor.BOTTOM_LEFT);
        chart3.setLegendOrientation(Orientation.VERTICAL);
        XYZPlot plot3 = (XYZPlot) chart3.getPlot();
        ScatterXYZRenderer renderer3 = (ScatterXYZRenderer) plot3.getRenderer();
        renderer3.setSize(0.15);
        renderer3.setColors(Colors.createIntenseColors());
        chart3.setViewPoint(ViewPoint3D.createAboveLeftViewPoint(40));

        Chart3DCanvas canvas3 = new Chart3DCanvas(chart3);

        XYZDataset dataset4 = createDataset("sepal width", "petal width", 
                "petal length");
        Chart3D chart4 = Chart3DFactory.createScatterChart(null, null, dataset4, 
                "Sepal Width", "Petal Width", "Petal Length");
        TextElement title4 = new TextElement("Iris Dataset : Combination 4");
        title4.setFont(StandardChartStyle.createDefaultFont(Font.PLAIN, 16));
        chart4.setTitle(title4);
        chart4.setLegendAnchor(LegendAnchor.BOTTOM_LEFT);
        chart4.setLegendOrientation(Orientation.VERTICAL);
        XYZPlot plot4 = (XYZPlot) chart4.getPlot();
        ScatterXYZRenderer renderer4 = (ScatterXYZRenderer) plot4.getRenderer();
        renderer4.setSize(0.15);
        renderer4.setColors(Colors.createIntenseColors());
        chart4.setViewPoint(ViewPoint3D.createAboveLeftViewPoint(40));
        
        
        Chart3DCanvas canvas4 = new Chart3DCanvas(chart4);
        grid.add(canvas1, 0, 0);
        grid.add(canvas2, 1, 0);
        grid.add(canvas3, 0, 1);
        grid.add(canvas4, 1, 1);
        return grid;
    }

    /**
     * Reads a dataset from the file iris.txt.
     * 
     * @return A sample dataset.
     */
    private static XYZDataset createDataset(Comparable<?> xKey, 
            Comparable<?> yKey, Comparable<?> zKey) {
        Reader in = new InputStreamReader(
                ScatterPlot3DDemo3.class.getResourceAsStream("iris.txt"));
        KeyedValues3D data;
        try {
            data = JSONUtils.readKeyedValues3D(in);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return DataUtils.extractXYZDatasetFromColumns(data, xKey, yKey, zKey);
    }  
}
