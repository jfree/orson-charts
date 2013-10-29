/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.demo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import com.orsoncharts.plot.CategoryPlot3D;

/**
 * A demo of a 3D bar chart.
 * 
 * http://www.theverge.com/2013/7/23/4549094/apple-microsoft-google-profit-revenue-margins-q2-2013-chart
 */
public class BarChart3DDemo1 extends JFrame {
    
    /**
     * Creates a new test app.
     *
     * @param title  the frame title.
     */
    public BarChart3DDemo1(String title) {
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
        Chart3D chart = Chart3DFactory.createBarChart("BarChart3DDemo1", 
                dataset, null, "Quarter", "$billion Revenues");
        chart.setLegendAnchor(LegendAnchor.BOTTOM_RIGHT);
        CategoryPlot3D plot = (CategoryPlot3D) chart.getPlot();
        plot.setGridlinePaintForValues(Color.BLACK);
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
        s1.addValue("Q1/2011", 8.58);
        s1.addValue("Q2/2011", 9.03);
        s1.addValue("Q3/2011", 9.72);
        s1.addValue("Q4/2011", 10.58);
        s1.addValue("Q1/2012", 10.65);
        s1.addValue("Q2/2012", 12.214);
        s1.addValue("Q3/2012", 14.101);
        s1.addValue("Q4/2012", 14.419);
        s1.addValue("Q1/2013", 13.969);
        s1.addValue("Q2/2013", 14.105);
        dataset.addSeriesAsRow("Google", s1);
        
        DefaultKeyedValues s2 = new DefaultKeyedValues();
        s2.addValue("Q1/2011", 16.43);
        s2.addValue("Q2/2011", 17.37);
        s2.addValue("Q3/2011", 17.37);
        s2.addValue("Q4/2011", 20.89);
        s2.addValue("Q1/2012", 17.41);
        s2.addValue("Q2/2012", 18.06);
        s2.addValue("Q3/2012", 16.008);
        s2.addValue("Q4/2012", 21.456);
        s2.addValue("Q1/2013", 20.489);
        s2.addValue("Q2/2013", 19.896);
        dataset.addSeriesAsRow("Microsoft", s2);
        
        DefaultKeyedValues s3 = new DefaultKeyedValues();
        s3.addValue("Q1/2011", 24.67);
        s3.addValue("Q2/2011", 28.57);
        s3.addValue("Q3/2011", 28.27);
        s3.addValue("Q4/2011", 46.33);
        s3.addValue("Q1/2012", 39.20);
        s3.addValue("Q2/2012", 35.00);
        s3.addValue("Q3/2012", 36.00);
        s3.addValue("Q4/2012", 54.5);
        s3.addValue("Q1/2013", 43.6);
        s3.addValue("Q2/2013", 35.323);
        dataset.addSeriesAsRow("Apple", s3);

        return dataset;
    }

    /**
     * Starting point for the app.
     *
     * @param args  command line arguments (ignored).
     */
    public static void main(String[] args) {
        BarChart3DDemo1 app = new BarChart3DDemo1(
                "OrsonCharts: BarChart3DDemo1.java");
        app.pack();
        app.setVisible(true);
    }
}

