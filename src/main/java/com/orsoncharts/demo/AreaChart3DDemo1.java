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
import java.awt.Color;

import javax.swing.JFrame;

import com.orsoncharts.ChartPanel3D;
import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.data.category.StandardCategoryDataset3D;
import com.orsoncharts.data.DefaultKeyedValues;
import com.orsoncharts.graphics3d.swing.DisplayPanel3D;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.renderer.category.AreaRenderer3D;

/**
 * A demo of a 3D area chart.
 */
@SuppressWarnings("serial")
public class AreaChart3DDemo1 extends JFrame {

    /**
     * Creates a new test app.
     *
     * @param title  the frame title.
     */
    public AreaChart3DDemo1(String title) {
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
    public static DemoPanel createDemoPanel() {
        DemoPanel content = new DemoPanel(new BorderLayout());
        content.setPreferredSize(OrsonChartsDemo.DEFAULT_CONTENT_SIZE);
        CategoryDataset3D dataset = createDataset();
        Chart3D chart = Chart3DFactory.createAreaChart(
                "Reported Revenues By Quarter", 
                "Large companies in the IT industry", dataset, "Company", 
                "Quarter", "Value");
        chart.setChartBoxColor(new Color(255, 255, 255, 128));
        CategoryPlot3D plot = (CategoryPlot3D) chart.getPlot();
        plot.getRowAxis().setVisible(false);
        AreaRenderer3D renderer = (AreaRenderer3D) plot.getRenderer();
        renderer.setBaseColor(Color.GRAY);
        ChartPanel3D chartPanel = new ChartPanel3D(chart);
        content.setChartPanel(chartPanel);
        content.add(new DisplayPanel3D(chartPanel));
        chartPanel.zoomToFit(OrsonChartsDemo.DEFAULT_CONTENT_SIZE);
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
                
        DefaultKeyedValues<Double> s1 = new DefaultKeyedValues<Double>();
        s1.put("Q1/11", 8.58);
        s1.put("Q2/11", 9.03);
        s1.put("Q3/11", 9.72);
        s1.put("Q4/11", 10.58);
        s1.put("Q1/12", 10.65);
        s1.put("Q2/12", 12.214);
        s1.put("Q3/12", 14.101);
        s1.put("Q4/12", 14.419);
        s1.put("Q1/13", 13.969);
        s1.put("Q2/13", 14.105);
        dataset.addSeriesAsRow("Google", s1);
        
        DefaultKeyedValues<Double> s2 = new DefaultKeyedValues<Double>();
        s2.put("Q1/11", 16.43);
        s2.put("Q2/11", 17.37);
        s2.put("Q3/11", 17.37);
        s2.put("Q4/11", 20.89);
        s2.put("Q1/12", 17.41);
        s2.put("Q2/12", 18.06);
        s2.put("Q3/12", 16.008);
        s2.put("Q4/12", 21.456);
        s2.put("Q1/13", 20.489);
        s2.put("Q2/13", 19.896);
        dataset.addSeriesAsRow("Microsoft", s2);
        
        DefaultKeyedValues<Double> s3 = new DefaultKeyedValues<Double>();
        s3.put("Q1/11", 24.67);
        s3.put("Q2/11", 28.57);
        s3.put("Q3/11", 28.27);
        s3.put("Q4/11", 46.33);
        s3.put("Q1/12", 39.20);
        s3.put("Q2/12", 35.00);
        s3.put("Q3/12", 36.00);
        s3.put("Q4/12", 54.5);
        s3.put("Q1/13", 43.6);
        s3.put("Q2/13", 35.323);
        dataset.addSeriesAsRow("Apple", s3);

        return dataset;
    }

    /**
     * Starting point for the app.
     *
     * @param args  command line arguments (ignored).
     */
    public static void main(String[] args) {
        AreaChart3DDemo1 app = new AreaChart3DDemo1(
                "OrsonCharts: AreaChart3DDemo1.java");
        app.pack();
        app.setVisible(true);
    }

}