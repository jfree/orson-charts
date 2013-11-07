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
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import com.orsoncharts.Colors;
import com.orsoncharts.ChartPanel3D;
import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.data.category.StandardCategoryDataset3D;
import com.orsoncharts.data.DefaultKeyedValues;
import com.orsoncharts.graphics3d.swing.DisplayPanel3D;
import com.orsoncharts.legend.StandardLegendBuilder;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.renderer.category.AreaRenderer3D;
import com.orsoncharts.renderer.category.StandardCategory3DPaintSource;

/**
 * A demo of a 3D area chart.
 */
public class AreaChart3DDemo1 extends JFrame {

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
        Chart3D chart = Chart3DFactory.createAreaChart("Reported Revenues By Quarter", 
                "Chart created with Orson Charts", dataset, "Company", 
                "Quarter", "Value");
        chart.setChartBoxColor(new Color(255, 255, 255, 128));
        //chart.setViewPoint(ViewPoint3D.createAboveLeftViewPoint(40));
        StandardLegendBuilder slb = (StandardLegendBuilder) chart.getLegendBuilder();
        slb.setFooter("Orson Charts (c) 2013, by Object Refinery Limited");
        
        chart.getLegendBuilder().setItemFont(new Font("Dialog", Font.ITALIC, 12));
//        ImageIcon icon = new ImageIcon("/Users/dgilbert/Desktop/me.png");
//        RectanglePainter background = new StandardRectanglePainter(Color.WHITE, 
//                icon.getImage());
//        chart.setBackground(background); 
        CategoryPlot3D plot = (CategoryPlot3D) chart.getPlot();
        plot.getRowAxis().setVisible(false);
        //plot.getValueAxis().setLabelFont();
            // plot.getValueAxis().setRange(-10.0, 4.0);
        AreaRenderer3D renderer = (AreaRenderer3D) plot.getRenderer();
        renderer.setBaseColor(Color.GRAY);
        renderer.setPaintSource(new StandardCategory3DPaintSource(Colors.getColors1()));
        ChartPanel3D chartPanel = new ChartPanel3D(chart);
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
                
        DefaultKeyedValues s1 = new DefaultKeyedValues();
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
        
        DefaultKeyedValues s2 = new DefaultKeyedValues();
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
        
        DefaultKeyedValues s3 = new DefaultKeyedValues();
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