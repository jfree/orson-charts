/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.demo;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import com.orsoncharts.ChartPanel3D;
import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.data.category.StandardCategoryDataset3D;
import com.orsoncharts.data.DefaultKeyedValues;
import com.orsoncharts.graphics3d.swing.DisplayPanel3D;
import com.orsoncharts.legend.LegendAnchor;
import com.orsoncharts.util.Orientation;

/**
 * A demo of a 3D bar chart.
 */
public class BarChart3DDemo2 extends JFrame {

    /**
     * Creates a new test app.
     *
     * @param title  the frame title.
     */
    public BarChart3DDemo2(String title) {
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
        Chart3D chart = Chart3DFactory.createBarChart(
                "Average Maximum Temperature", 
                "http://www.worldclimateguide.co.uk/climateguides/", dataset, 
                null, null, "Temp °C");
        chart.setLegendPosition(LegendAnchor.BOTTOM_RIGHT, Orientation.VERTICAL);
        chart.getViewPoint().panLeftRight(-Math.PI / 60);
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
                
        DefaultKeyedValues s3 = new DefaultKeyedValues();
        s3.put("Jan", 7);
        s3.put("Feb", 7);
        s3.put("Mar", 10);
        s3.put("Apr", 13);
        s3.put("May", 17);
        s3.put("Jun", 20);
        s3.put("Jul", 22);
        s3.put("Aug", 21);
        s3.put("Sep", 19);
        s3.put("Oct", 15);
        s3.put("Nov", 10);
        s3.put("Dec", 8);
        dataset.addSeriesAsRow("London", s3);

        DefaultKeyedValues s1 = new DefaultKeyedValues();
        s1.put("Jan", 3);
        s1.put("Feb", 5);
        s1.put("Mar", 9);
        s1.put("Apr", 14);
        s1.put("May", 18);
        s1.put("Jun", 22);
        s1.put("Jul", 25);
        s1.put("Aug", 24);
        s1.put("Sep", 20);
        s1.put("Oct", 14);
        s1.put("Nov", 8);
        s1.put("Dec", 4);
        dataset.addSeriesAsRow("Geneva", s1);
        
        DefaultKeyedValues s2 = new DefaultKeyedValues();
        s2.put("Jan", 9);
        s2.put("Feb", 11);
        s2.put("Mar", 13);
        s2.put("Apr", 16);
        s2.put("May", 20);
        s2.put("Jun", 23);
        s2.put("Jul", 26);
        s2.put("Aug", 26);
        s2.put("Sep", 24);
        s2.put("Oct", 19);
        s2.put("Nov", 13);
        s2.put("Dec", 9);
        dataset.addSeriesAsRow("Bergerac", s2);

        DefaultKeyedValues s4 = new DefaultKeyedValues();
        s4.put("Jan", 22);
        s4.put("Feb", 22);
        s4.put("Mar", 20);
        s4.put("Apr", 17);
        s4.put("May", 14);
        s4.put("Jun", 11);
        s4.put("Jul", 11);
        s4.put("Aug", 12);
        s4.put("Sep", 14);
        s4.put("Oct", 17);
        s4.put("Nov", 19);
        s4.put("Dec", 21);
        dataset.addSeriesAsRow("Christchurch", s4);

        DefaultKeyedValues s5 = new DefaultKeyedValues();
        s5.put("Jan", 20);
        s5.put("Feb", 20);
        s5.put("Mar", 19);
        s5.put("Apr", 17);
        s5.put("May", 14);
        s5.put("Jun", 12);
        s5.put("Jul", 11);
        s5.put("Aug", 12);
        s5.put("Sep", 13);
        s5.put("Oct", 15);
        s5.put("Nov", 17);
        s5.put("Dec", 19);
        dataset.addSeriesAsRow("Wellington", s5);

        return dataset;
    }

    /**
     * Starting point for the app.
     *
     * @param args  command line arguments (ignored).
     */
    public static void main(String[] args) {
        BarChart3DDemo2 app = new BarChart3DDemo2(
                "OrsonCharts: BarChart3DDemo2.java");
        app.pack();
        app.setVisible(true);
    }
}

