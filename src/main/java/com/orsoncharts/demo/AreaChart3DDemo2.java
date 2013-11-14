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
import com.orsoncharts.graphics3d.ViewPoint3D;
import com.orsoncharts.graphics3d.swing.DisplayPanel3D;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.renderer.category.AreaRenderer3D;
import com.orsoncharts.renderer.category.StandardCategoryColorSource;

/**
 * A demo of a 3D area chart.
 */
public class AreaChart3DDemo2 extends JFrame {

    /**
     * Creates a new test app.
     *
     * @param title  the frame title.
     */
    public AreaChart3DDemo2(String title) {
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
        
        chart.getLegendBuilder().setItemFont(new Font("Dialog", 
                Font.ITALIC, 12));
        CategoryPlot3D plot = (CategoryPlot3D) chart.getPlot();
        plot.getRowAxis().setVisible(false);
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
        s1.put("A", -1);
        s1.put("B", -4);
        s1.put("C", -9);
        s1.put("D", -5);
        s1.put("E", -5);
        s1.put("F", -2);
        s1.put("G", -4);
        s1.put("H", -7);
        s1.put("I", -3);
        s1.put("J", -1);
        dataset.addSeriesAsRow("Series 1", s1);
        
        DefaultKeyedValues s2 = new DefaultKeyedValues();
        s2.put("A", 1);
        s2.put("B", 12);
        s2.put("C", 14);
        s2.put("D", 7);
        s2.put("E", 2);
        s2.put("F", -9);
        s2.put("G", -14);
        s2.put("H", 0);
        s2.put("I", 12);
        s2.put("J", 4);
        dataset.addSeriesAsRow("Series 2", s2);
        
        DefaultKeyedValues s3 = new DefaultKeyedValues();
        s3.put("A", 5);
        s3.put("B", 13);
        s3.put("C", 19);
        s3.put("D", 11);
        s3.put("E", 10);
        s3.put("F", 5);
        s3.put("G", -7);
        s3.put("H", -3);
        s3.put("I", -15);
        s3.put("J", -20);
        dataset.addSeriesAsRow("Series 3", s3);

        DefaultKeyedValues s4 = new DefaultKeyedValues();
        s4.put("A", 5);
        s4.put("B", 18);
        s4.put("C", 20);
        s4.put("D", 17);
        s4.put("E", 11);
        s4.put("F", 19);
        s4.put("G", 25);
        s4.put("H", 12);
        s4.put("I", 4);
        s4.put("J", 2);
        dataset.addSeriesAsRow("Series 4", s4);

        return dataset;
    }
    
    private static CategoryDataset3D createDataset2() {
        StandardCategoryDataset3D dataset = new StandardCategoryDataset3D();
        
        // http://investor.bankofamerica.com/phoenix.zhtml?c=71595&p=quarterlyearnings#fbid=Ke_-yRMOTA4
        DefaultKeyedValues s0 = new DefaultKeyedValues();
        s0.put("Q3/2011", 5889);
        s0.put("Q4/2011", 1584);
        s0.put("Q1/2012", 328);
        s0.put("Q2/2012", 2098);
        s0.put("Q3/2012", -33);
        s0.put("Q4/2012", 367);
        s0.put("Q1/2013", 1110);
        s0.put("Q2/2013", 3571);
        s0.put("Q3/2013", 2218);
        dataset.addSeriesAsRow("Bank of America", s0);

        // http://www.citigroup.com/citi/investor/data/qer313s.pdf
        DefaultKeyedValues s1 = new DefaultKeyedValues();
        s1.put("Q3/2011", 3771);
        s1.put("Q4/2011", 956);
        s1.put("Q1/2012", 2931);
        s1.put("Q2/2012", 2946);
        s1.put("Q3/2012", 468);
        s1.put("Q4/2012", 1196);
        s1.put("Q1/2013", 3808);
        s1.put("Q2/2013", 4182);
        s1.put("Q3/2013", 3227);
        dataset.addSeriesAsRow("Citigroup", s1);
        
        // https://www.wellsfargo.com/downloads/pdf/press/3q13pr.pdf 
        DefaultKeyedValues s3 = new DefaultKeyedValues();
        s3.put("Q3/2011", 4055);
        s3.put("Q4/2011", 4107);
        s3.put("Q1/2012", 4248);
        s3.put("Q2/2012", 4622);
        s3.put("Q3/2012", 4937);
        s3.put("Q4/2012", 4857);
        s3.put("Q1/2013", 4931);
        s3.put("Q2/2013", 5272);
        s3.put("Q3/2013", 5317);

        dataset.addSeriesAsRow("Wells Fargo", s3);

        // http://files.shareholder.com/downloads/ONE/2724973994x0x696270/df38c408-0315-43dd-b896-6fe6bc895050/3Q13_Earnings_Earnings_Supplement.pdf
        DefaultKeyedValues s2 = new DefaultKeyedValues();
        s2.put("Q3/2011", 4262);
        s2.put("Q4/2011", 3728);
        s2.put("Q1/2012", 4924);
        s2.put("Q2/2012", 4960);
        s2.put("Q3/2012", 5708);
        s2.put("Q4/2012", 5692);
        s2.put("Q1/2013", 6529);
        s2.put("Q2/2013", 6496);
        s2.put("Q3/2013", -380);
        dataset.addSeriesAsRow("J.P.Morgan", s2);
        
        return dataset;
    }

    /**
     * Starting point for the app.
     *
     * @param args  command line arguments (ignored).
     */
    public static void main(String[] args) {
        AreaChart3DDemo2 app = new AreaChart3DDemo2(
                "OrsonCharts: AreaChart3DDemo2.java");
        app.pack();
        app.setVisible(true);
    }

}