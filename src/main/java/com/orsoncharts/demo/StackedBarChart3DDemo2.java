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

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.orsoncharts.ChartPanel3D;
import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.Colors;
import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.data.category.StandardCategoryDataset3D;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.swing.DisplayPanel3D;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.renderer.category.StackedBarRenderer3D;

/**
 * A demo of a 3D stacked bar chart.
 */
@SuppressWarnings("serial")
public class StackedBarChart3DDemo2 extends JFrame {

    /**
     * Creates a new test app.
     *
     * @param title  the frame title.
     */
    public StackedBarChart3DDemo2(String title) {
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
        DemoPanel content = new DemoPanel(new BorderLayout());
        content.setPreferredSize(OrsonChartsDemo.DEFAULT_CONTENT_SIZE);
        CategoryDataset3D dataset = createDataset();
        Chart3D chart = Chart3DFactory.createStackedBarChart(
                "Water Usage Chart", 
                "Source: http://en.wikipedia.org/wiki/Peak_water#Water_supply", 
                dataset, null, null, "Cubic meters / person / year");
        CategoryPlot3D plot = (CategoryPlot3D) chart.getPlot();
        plot.setDimensions(new Dimension3D(14, 6.5, 2));
        plot.getRowAxis().setVisible(false);
        StackedBarRenderer3D renderer 
                = (StackedBarRenderer3D) plot.getRenderer();
        renderer.setBarZWidth(0.3);
        renderer.setColors(Colors.createBlueOceanColors());
        chart.getViewPoint().moveUpDown(Math.PI / 30);
        ChartPanel3D chartPanel = new ChartPanel3D(chart);
        content.setChartPanel(chartPanel);
        chartPanel.zoomToFit(OrsonChartsDemo.DEFAULT_CONTENT_SIZE);
        content.add(new DisplayPanel3D(chartPanel));
        return content;
    }
  
    /**
     * Creates a sample dataset (hard-coded for the purpose of keeping the
     * demo self-contained - in practice you would normally read your data
     * from a file, database or other source).
     * 
     * @return A sample dataset.
     */
    private static CategoryDataset3D createDataset() {
        
        StandardCategoryDataset3D dataset = new StandardCategoryDataset3D();

        dataset.addValue(197, "Agricultural", "R1", "Brazil");
        dataset.addValue(64, "Domestic", "R1", "Brazil");
        dataset.addValue(57, "Industrial", "R1", "Brazil");
        
        dataset.addValue(339, "Agricultural", "R1", "Indonesia");
        dataset.addValue(30, "Domestic", "R1", "Indonesia");
        dataset.addValue(4, "Industrial", "R1", "Indonesia");
        
        dataset.addValue(279, "Agricultural", "R1", "China");
        dataset.addValue(27, "Domestic", "R1", "China");
        dataset.addValue(107, "Industrial", "R1", "China");

        dataset.addValue(92, "Agricultural", "R1", "Germany");
        dataset.addValue(55, "Domestic", "R1", "Germany");
        dataset.addValue(313, "Industrial", "R1", "Germany");

        dataset.addValue(96, "Agricultural", "R1", "Russia");
        dataset.addValue(102, "Domestic", "R1", "Russia");
        dataset.addValue(337, "Industrial", "R1", "Russia");

        dataset.addValue(403, "Agricultural", "R1", "Turkey");
        dataset.addValue(82, "Domestic", "R1", "Turkey");
        dataset.addValue(60, "Industrial", "R1", "Turkey");
        
        dataset.addValue(536, "Agricultural", "R1", "Bangladesh");
        dataset.addValue(17, "Domestic", "R1", "Bangladesh");
        dataset.addValue(6, "Industrial", "R1", "Bangladesh");
        
        dataset.addValue(508, "Agricultural", "R1", "India");
        dataset.addValue(47, "Domestic", "R1", "India");
        dataset.addValue(30, "Industrial", "R1", "India");
        
        dataset.addValue(428, "Agricultural", "R1", "Japan");
        dataset.addValue(138, "Domestic", "R1", "Japan");
        dataset.addValue(124, "Industrial", "R1", "Japan");

        dataset.addValue(325, "Agricultural", "R1", "Italy");
        dataset.addValue(130, "Domestic", "R1", "Italy");
        dataset.addValue(268, "Industrial", "R1", "Italy");
        
        dataset.addValue(569, "Agricultural", "R1", "Mexico");
        dataset.addValue(126, "Domestic", "R1", "Mexico");
        dataset.addValue(37, "Industrial", "R1", "Mexico");

        dataset.addValue(576, "Agricultural", "R1", "Vietnam");
        dataset.addValue(68, "Domestic", "R1", "Vietnam");
        dataset.addValue(203, "Industrial", "R1", "Vietnam");
        
        dataset.addValue(794, "Agricultural", "R1", "Egypt");
        dataset.addValue(74, "Domestic", "R1", "Egypt");
        dataset.addValue(55, "Industrial", "R1", "Egypt");

        dataset.addValue(954, "Agricultural", "R1", "Iran");
        dataset.addValue(21, "Domestic", "R1", "Iran");
        dataset.addValue(73, "Industrial", "R1", "Iran");

        dataset.addValue(1029, "Agricultural", "R1", "Pakistan");
        dataset.addValue(21, "Domestic", "R1", "Pakistan");
        dataset.addValue(21, "Industrial", "R1", "Pakistan");

        dataset.addValue(1236, "Agricultural", "R1", "Thailand");
        dataset.addValue(26, "Domestic", "R1", "Thailand");
        dataset.addValue(26, "Industrial", "R1", "Thailand");

        dataset.addValue(165, "Agricultural", "R1", "Canada");
        dataset.addValue(274, "Domestic", "R1", "Canada");
        dataset.addValue(947, "Industrial", "R1", "Canada");
        
        dataset.addValue(1363, "Agricultural", "R1", "Iraq");
        dataset.addValue(44, "Domestic", "R1", "Iraq");
        dataset.addValue(74, "Industrial", "R1", "Iraq");
        
        dataset.addValue(656, "Agricultural", "R1", "US");
        dataset.addValue(208, "Domestic", "R1", "US");
        dataset.addValue(736, "Industrial", "R1", "US");
        
        dataset.addValue(2040, "Agricultural", "R1", "Uzbekistan");
        dataset.addValue(110, "Domestic", "R1", "Uzbekistan");
        dataset.addValue(44, "Industrial", "R1", "Uzbekistan");
        
        return dataset;
    }
    
    /**
     * Starting point for the app.
     *
     * @param args  command line arguments (ignored).
     */
    public static void main(String[] args) {
        StackedBarChart3DDemo2 app = new StackedBarChart3DDemo2(
                "OrsonCharts: StackedBarChart3DDemo1.java");
        app.pack();
        app.setVisible(true);
    }
}

