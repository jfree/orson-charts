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
import com.orsoncharts.data.category.StandardCategoryDataset3D;
import com.orsoncharts.data.DefaultKeyedValues;
import com.orsoncharts.graphics3d.swing.DisplayPanel3D;

/**
 * A demo of a 3D bar chart.  Here we add a lot of series so we can test the
 * wrapping of the legend.
 */
public class BarChart3DDemo2 extends JFrame {

    /**
     * Creates a new test app.
     *
     * @param title  the frame title.
     */
    public BarChart3DDemo2(String title) {
        super(title);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        getContentPane().add(createDemoPanel());
    }

    public static final JPanel createDemoPanel() {
        JPanel content = new JPanel(new BorderLayout());
        content.setPreferredSize(new Dimension(600, 400));
        CategoryDataset3D dataset = createDataset();
        Chart3D chart = Chart3DFactory.createBarChart("BarChart3DDemo2", 
                dataset, null, "Quarter", "$billion Revenues");
        content.add(new DisplayPanel3D(new ChartPanel3D(chart), true));
        return content;
    }
  
    /**
     * Creates a sample dataset.
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
        dataset.addSeriesAsRow("Google", s1);
        
        DefaultKeyedValues s2 = new DefaultKeyedValues();
        s2.addValue("Q1/2011", 16.43);
        s2.addValue("Q2/2011", 17.37);
        s2.addValue("Q3/2011", 17.37);
        s2.addValue("Q4/2011", 20.89);
        dataset.addSeriesAsRow("Microsoft", s2);
        
        DefaultKeyedValues s3 = new DefaultKeyedValues();
        s3.addValue("Q1/2011", 24.67);
        s3.addValue("Q2/2011", 28.57);
        s3.addValue("Q3/2011", 28.27);
        s3.addValue("Q4/2011", 46.33);
        dataset.addSeriesAsRow("Apple", s3);

        DefaultKeyedValues s4 = new DefaultKeyedValues();
        s3.addValue("Q1/2011", 24.67);
        s3.addValue("Q2/2011", 28.57);
        s3.addValue("Q3/2011", 28.27);
        s3.addValue("Q4/2011", 46.33);
        dataset.addSeriesAsRow("Apple2", s3);

        DefaultKeyedValues s5 = new DefaultKeyedValues();
        s3.addValue("Q1/2011", 24.67);
        s3.addValue("Q2/2011", 28.57);
        s3.addValue("Q3/2011", 28.27);
        s3.addValue("Q4/2011", 46.33);
        dataset.addSeriesAsRow("Apple3", s3);

        DefaultKeyedValues s6 = new DefaultKeyedValues();
        s3.addValue("Q1/2011", 24.67);
        s3.addValue("Q2/2011", 28.57);
        s3.addValue("Q3/2011", 28.27);
        s3.addValue("Q4/2011", 46.33);
        dataset.addSeriesAsRow("Apple4", s3);
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

