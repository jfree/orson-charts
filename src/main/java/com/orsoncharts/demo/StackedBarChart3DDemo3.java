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
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.net.URL;
import com.orsoncharts.ChartPanel3D;
import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.Colors;
import com.orsoncharts.RectanglePainter;
import com.orsoncharts.StandardRectanglePainter;
import com.orsoncharts.axis.LabelOrientation;
import com.orsoncharts.axis.StandardCategoryAxis3D;
import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.data.category.StandardCategoryDataset3D;
import com.orsoncharts.graphics3d.swing.DisplayPanel3D;
import com.orsoncharts.label.StandardCategoryLabelGenerator;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.renderer.category.StackedBarRenderer3D;
import com.orsoncharts.util.Fit2D;

/**
 * A demo of a 3D stacked bar chart.
 */
public class StackedBarChart3DDemo3 extends JFrame {

    /**
     * Creates a new test app.
     *
     * @param title  the frame title.
     */
    public StackedBarChart3DDemo3(String title) {
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
                "The Sinking of the Titanic", 
                "Survival data for 2,201 passengers", 
                dataset, null, 
                "Class", "Passengers");

        URL imageURL = StackedBarChart3DDemo3.class.getResource("iStock_000003105870Small.jpg");
        ImageIcon icon = new ImageIcon(imageURL); 
        RectanglePainter background = new StandardRectanglePainter(Color.WHITE, 
                icon.getImage(), Fit2D.SCALE_TO_FIT_TARGET);
        chart.setBackground(background);
        chart.setChartBoxColor(new Color(255, 255, 255, 155));
        CategoryPlot3D plot = (CategoryPlot3D) chart.getPlot();
        plot.setLegendLabelGenerator(new StandardCategoryLabelGenerator(
                StandardCategoryLabelGenerator.TOTAL_TEMPLATE));   
        StandardCategoryAxis3D rowAxis 
                = (StandardCategoryAxis3D) plot.getRowAxis();
        rowAxis.setTickLabelGenerator(new StandardCategoryLabelGenerator(
                StandardCategoryLabelGenerator.TOTAL_TEMPLATE));

        StandardCategoryAxis3D columnAxis 
                = (StandardCategoryAxis3D) plot.getColumnAxis();
        columnAxis.setTickLabelGenerator(new StandardCategoryLabelGenerator(
                StandardCategoryLabelGenerator.TOTAL_TEMPLATE));
        columnAxis.setTickLabelOrientation(LabelOrientation.PARALLEL);
        columnAxis.setMaxTickLabelLevels(2);

        StackedBarRenderer3D renderer = (StackedBarRenderer3D) plot.getRenderer();
        renderer.setColors(Colors.createIceCubeColors());
        
        ChartPanel3D chartPanel = new ChartPanel3D(chart);
        chartPanel.setMargin(0.33);
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
        
        dataset.addValue(146, "Survivors", "Women/Children", "1st");
        dataset.addValue(104, "Survivors", "Women/Children", "2nd");
        dataset.addValue(103, "Survivors", "Women/Children", "3rd");
        dataset.addValue(20, "Survivors", "Women/Children", "Crew");

        dataset.addValue(57, "Survivors", "Men", "1st");
        dataset.addValue(14, "Survivors", "Men", "2nd");
        dataset.addValue(75, "Survivors", "Men", "3rd");
        dataset.addValue(192, "Survivors", "Men", "Crew");
 
        dataset.addValue(4, "Victims", "Women/Children", "1st");
        dataset.addValue(13, "Victims", "Women/Children", "2nd");
        dataset.addValue(141, "Victims", "Women/Children", "3rd");
        dataset.addValue(3, "Victims", "Women/Children", "Crew");
        
        dataset.addValue(118, "Victims", "Men", "1st");
        dataset.addValue(154, "Victims", "Men", "2nd");
        dataset.addValue(387, "Victims", "Men", "3rd");
        dataset.addValue(670, "Victims", "Men", "Crew");
          
        return dataset;
    }
    
    /**
     * Starting point for the app.
     *
     * @param args  command line arguments (ignored).
     */
    public static void main(String[] args) {
        StackedBarChart3DDemo3 app = new StackedBarChart3DDemo3(
                "OrsonCharts: StackedBarChart3DDemo3.java");
        app.pack();
        app.setVisible(true);
    }
}


