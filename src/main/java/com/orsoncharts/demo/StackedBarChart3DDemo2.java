/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.demo;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import com.orsoncharts.ChartPanel3D;
import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.data.DefaultKeyedValues;
import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.data.category.StandardCategoryDataset3D;
import com.orsoncharts.graphics3d.swing.DisplayPanel3D;

/**
 * A demo of a 3D stacked bar chart.
 */
public class StackedBarChart3DDemo2 extends JFrame {

    /**
     * Creates a new test app.
     *
     * @param title  the frame title.
     */
    public StackedBarChart3DDemo2(String title) {
        super(title);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
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
        JPanel content = new JPanel(new BorderLayout());
        content.setPreferredSize(OrsonChartsDemo.DEFAULT_CONTENT_SIZE);
        CategoryDataset3D dataset = createDataset();
        Chart3D chart = Chart3DFactory.createStackedBarChart(
                "Stacked Bar Chart", "Put the data source here", dataset, null, 
                null, "Value");

        ChartPanel3D chartPanel = new ChartPanel3D(chart);
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

        DefaultKeyedValues s1 = new DefaultKeyedValues();
        s1.put("A", 4);
        s1.put("B", 2);
        s1.put("C", 3);
        s1.put("D", 5);
        s1.put("E", 2);
        s1.put("F", 1);
        DefaultKeyedValues s2 = new DefaultKeyedValues();
        s2.put("A", 1);
        s2.put("B", 2);
        s2.put("C", 3);
        s2.put("D", 2);
        s2.put("E", 3);
        s2.put("F", 1);
        DefaultKeyedValues s3 = new DefaultKeyedValues();
        s3.put("A", 6);
        s3.put("B", 6);
        s3.put("C", 6);
        s3.put("D", 4);
        s3.put("E", 4);
        s3.put("F", 4);
        DefaultKeyedValues s4 = new DefaultKeyedValues();
        s4.put("A", 9);
        s4.put("B", 8);
        s4.put("C", 7);
        s4.put("D", 6);
        s4.put("D", 3);
        s4.put("E", 4);
        s4.put("F", 6);
        DefaultKeyedValues s5 = new DefaultKeyedValues();
        s5.put("A", 9);
        s5.put("B", 8);
        s5.put("C", 7);
        s5.put("D", 6);
        s5.put("E", 7);
        s5.put("F", 9);

        dataset.addSeriesAsRow("Series 1", "Row 1", s1);
        dataset.addSeriesAsRow("Series 2", "Row 2", s2);
        dataset.addSeriesAsRow("Series 3", "Row 2", s3);
        dataset.addSeriesAsRow("Series 4", "Row 3", s4);
        dataset.addSeriesAsRow("Series 5", "Row 3", s5);
        return dataset;
    }
    
    /**
     * Starting point for the app.
     *
     * @param args  command line arguments (ignored).
     */
    public static void main(String[] args) {
        StackedBarChart3DDemo2 app = new StackedBarChart3DDemo2(
                "OrsonCharts: StackedBarChart3DDemo2.java");
        app.pack();
        app.setVisible(true);
    }
}


