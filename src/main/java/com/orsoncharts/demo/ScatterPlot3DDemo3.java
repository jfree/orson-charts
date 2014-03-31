/* ===================
 * Orson Charts - Demo
 * ===================
 * 
 * Copyright (c) 2013, 2014, Object Refinery Limited.
 * All rights reserved.
 *
 * http://www.object-refinery.com/orsoncharts/index.html
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *   - Neither the name of the Object Refinery Limited nor the
 *     names of its contributors may be used to endorse or promote products
 *     derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL OBJECT REFINERY LIMITED BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * Note that the above terms apply to the demo source only, and not the 
 * Orson Charts library.
 * 
 */

package com.orsoncharts.demo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.orsoncharts.Chart3DPanel;
import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.Colors;
import com.orsoncharts.data.DataUtils;
import com.orsoncharts.data.JSONUtils;
import com.orsoncharts.data.KeyedValues3D;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.graphics3d.ViewPoint3D;
import com.orsoncharts.graphics3d.swing.DisplayPanel3D;
import com.orsoncharts.legend.LegendAnchor;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.renderer.xyz.ScatterXYZRenderer;
import com.orsoncharts.style.StandardChartStyle;
import com.orsoncharts.table.TextElement;
import com.orsoncharts.util.Orientation;

/**
 * A demonstration of a scatter plot in 3D.
 */
@SuppressWarnings("serial")
public class ScatterPlot3DDemo3 extends JFrame {

    /**
     * Creates a new test app.
     *
     * @param title  the frame title.
     */
    public ScatterPlot3DDemo3(String title) {
        super(title);
        addWindowListener(new ExitOnClose());
        getContentPane().add(createDemoPanel());
    }

    /**
     * Returns a panel containing the content for the demo.  This method is
     * used across all the individual demo applications to allow aggregation 
     * into a single "umbrella" demo (OrsonChartsDemo).
     * 
     * @return A panel containing the content for the demo.
     */
    public static JPanel createDemoPanel() {
        DemoPanel content = new DemoPanel(new GridLayout(2, 2));
        content.setMinimumSize(new Dimension(100, 100));
        
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
        Chart3DPanel chartPanel1 = new Chart3DPanel(chart1);
        chartPanel1.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 0)), 
                BorderFactory.createLineBorder(Color.DARK_GRAY)));
        chartPanel1.setMargin(0.35);
        
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
        Chart3DPanel chartPanel2 = new Chart3DPanel(chart2);
        chartPanel2.setPreferredSize(new Dimension(10, 10));
        chartPanel2.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 0)), 
                BorderFactory.createLineBorder(Color.DARK_GRAY)));
        chartPanel2.setMargin(0.35);
        
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
        Chart3DPanel chartPanel3 = new Chart3DPanel(chart3);
        chartPanel3.setPreferredSize(new Dimension(10, 10));
        chartPanel3.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 0)), 
                BorderFactory.createLineBorder(Color.DARK_GRAY)));
        chartPanel3.setMargin(0.35);
        
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
        Chart3DPanel chartPanel4 = new Chart3DPanel(chart4);
        chartPanel4.setPreferredSize(new Dimension(10, 10));
        chartPanel4.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 0)), 
                BorderFactory.createLineBorder(Color.DARK_GRAY)));
        chartPanel4.setMargin(0.35);
        
        content.add(new DisplayPanel3D(chartPanel1, false, true));
        content.addChartPanel(chartPanel1);
        content.add(new DisplayPanel3D(chartPanel2, false, true));
        content.addChartPanel(chartPanel2);
        content.add(new DisplayPanel3D(chartPanel3, false, true));
        content.addChartPanel(chartPanel3);
        content.add(new DisplayPanel3D(chartPanel4, false, true));
        content.addChartPanel(chartPanel4);
        return content;
    }

    /**
     * Creates a sample dataset (hard-coded for the purpose of keeping the
     * demo self-contained - in practice you would normally read your data
     * from a file, database or other source).
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

    /**
     * Starting point for the app.
     *
     * @param args  command line arguments (ignored).
     */
    public static void main(String[] args) {
        ScatterPlot3DDemo3 app = new ScatterPlot3DDemo3(
                "OrsonCharts : ScatterPlot3DDemo3.java");
        app.pack();
        app.setVisible(true);
    }
}
