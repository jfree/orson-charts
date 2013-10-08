/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.demo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import com.orsoncharts.ChartPanel3D;
import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.data.category.DefaultCategoryDataset3D;
import com.orsoncharts.data.DefaultKeyedValues;
import com.orsoncharts.graphics3d.swing.DisplayPanel3D;
import com.orsoncharts.legend.LegendAnchor;

/**
 * A demo of a 3D bar chart.
 * 
 * http://www.theverge.com/2013/7/23/4549094/apple-microsoft-google-profit-revenue-margins-q2-2013-chart
 */
public class BarChart3DDemo1 extends JFrame {

    ChartPanel3D chartPanel3D;

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
        getContentPane().add(createContent());
    }

    final JPanel createContent() {
        JPanel content = new JPanel(new BorderLayout());
        content.setPreferredSize(new Dimension(600, 400));
        CategoryDataset3D dataset = createDataset();
        Chart3D chart = Chart3DFactory.createBarChart("BarChart3DDemo1", 
                dataset, null, "Quarter", "$billion Revenues");
        chart.setLegendAnchor(LegendAnchor.BOTTOM_RIGHT);
        this.chartPanel3D = new ChartPanel3D(chart);
        content.add(new DisplayPanel3D(this.chartPanel3D, true));
        return content;
    }
  
    /**
     * Creates a sample dataset.
     * 
     * @return A sample dataset. 
     */
    private CategoryDataset3D createDataset() {    
        DefaultCategoryDataset3D dataset = new DefaultCategoryDataset3D();
                
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

