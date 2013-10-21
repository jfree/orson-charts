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
import java.awt.Color;
import java.awt.Font;
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
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.renderer.category.AreaRenderer3D;
import javax.swing.ImageIcon;

/**
 * A demo of a 3D area chart.
 */
public class AreaChart3DDemo1 extends JFrame {

    //ChartPanel3D chartPanel3D;

    /**
     * Creates a new test app.
     *
     * @param title  the frame title.
     */
    public AreaChart3DDemo1(String title) {
        super(title);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        getContentPane().add(createDemoPanel());
    }

    public static JPanel createDemoPanel() {
        JPanel content = new JPanel(new BorderLayout());
        content.setPreferredSize(new Dimension(600, 400));
        CategoryDataset3D dataset = createDataset();
        Chart3D chart = Chart3DFactory.createAreaChart("AreaChart3DDemo1", 
                dataset, "Company", "Quarter", "Value");
        chart.setTitle("AreaChart3DDemo1", new Font("Dialog", Font.BOLD, 20),
                Color.DARK_GRAY);
//        ImageIcon icon = new ImageIcon("/Users/dgilbert/Desktop/me.png"); TODO
//        chart.setBackgroundImage(icon.getImage()); TODO
        CategoryPlot3D plot = (CategoryPlot3D) chart.getPlot();
        AreaRenderer3D renderer = (AreaRenderer3D) plot.getRenderer();
        renderer.setBaseColor(Color.GRAY);
        ChartPanel3D chartPanel3D = new ChartPanel3D(chart);
        content.add(new DisplayPanel3D(chartPanel3D, true));
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
        s1.addValue("Q1", 1.0);
        s1.addValue("Q2", 7.0);
        s1.addValue("Q3", 3.0);
        s1.addValue("Q4", 4.0);
        dataset.addSeriesAsRow("S1", s1);

        DefaultKeyedValues s2 = new DefaultKeyedValues();
        s2.addValue("Q1", 14.0);
        s2.addValue("Q2", 7.0);
        s2.addValue("Q3", 6.0);
        s2.addValue("Q4", 4.0);
        dataset.addSeriesAsRow("S2", s2);

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