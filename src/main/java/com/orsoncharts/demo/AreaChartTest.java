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
import static com.orsoncharts.demo.AreaChart3DDemo2.createDemoPanel;
import com.orsoncharts.graphics3d.ViewPoint3D;
import com.orsoncharts.graphics3d.swing.DisplayPanel3D;
import com.orsoncharts.legend.StandardLegendBuilder;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.renderer.category.AreaRenderer3D;
import com.orsoncharts.renderer.category.StandardCategoryColorSource;

/**
 * A demo of a 3D area chart.
 */
public class AreaChartTest extends JFrame {

    /**
     * Creates a new test app.
     *
     * @param title  the frame title.
     */
    public AreaChartTest(String title) {
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
        DemoPanel content = new DemoPanel(new BorderLayout());
        content.setPreferredSize(OrsonChartsDemo.DEFAULT_CONTENT_SIZE);
        CategoryDataset3D dataset = createDataset();
        Chart3D chart = Chart3DFactory.createAreaChart("AreaChart3DDemo2", 
                "Chart created with Orson Charts", dataset, "Row", 
                "Category", "Value");
        chart.setChartBoxColor(new Color(255, 255, 255, 128));
        chart.setViewPoint(ViewPoint3D.createAboveLeftViewPoint(40));
        StandardLegendBuilder slb 
                = (StandardLegendBuilder) chart.getLegendBuilder();
        slb.setFooter("Orson Charts (c) 2013, by Object Refinery Limited");
        
        chart.getLegendBuilder().setItemFont(new Font("Dialog", 
                Font.ITALIC, 12));
//        ImageIcon icon = new ImageIcon("/Users/dgilbert/Desktop/me.png");
//        RectanglePainter background = new StandardRectanglePainter(Color.WHITE, 
//                icon.getImage());
//        chart.setBackground(background); 
        CategoryPlot3D plot = (CategoryPlot3D) chart.getPlot();
        plot.getRowAxis().setVisible(false);
        //plot.getValueAxis().setRange(new Range(-2, 4));
        AreaRenderer3D renderer = (AreaRenderer3D) plot.getRenderer();
        renderer.setBaseColor(Color.GRAY);
        renderer.setColorSource(new StandardCategoryColorSource(
                Colors.getColors1()));
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
                
        DefaultKeyedValues s1 = new DefaultKeyedValues();
        s1.put("A", 0);
        s1.put("B", 5);
        s1.put("C", 12);
        dataset.addSeriesAsRow("Series 1", s1);

        return dataset;
    }

    /**
     * Starting point for the app.
     *
     * @param args  command line arguments (ignored).
     */
    public static void main(String[] args) {
        AreaChartTest app = new AreaChartTest(
                "OrsonCharts: AreaChartTest.java");
        app.pack();
        app.setVisible(true);
    }

}