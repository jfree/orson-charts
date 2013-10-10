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
import com.orsoncharts.axis.NumberAxis3D;
import com.orsoncharts.axis.NumberTickSelector;
import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.data.category.StandardCategoryDataset3D;
import com.orsoncharts.data.DefaultKeyedValues;
import com.orsoncharts.data.KeyedValues;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.swing.DisplayPanel3D;
import com.orsoncharts.plot.CategoryPlot3D;

/**
 * A demo of a 3D line chart.
 */
public class LineChart3DDemo1 extends JFrame {

    ChartPanel3D chartPanel3D;

    /**
     * Creates a new test app.
     *
     * @param title  the frame title.
     */
    public LineChart3DDemo1(String title) {
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
        Chart3D chart = Chart3DFactory.createLineChart("LineChart3DDemo1", 
                dataset, null, null, "Market Share (%)");
        CategoryPlot3D plot = (CategoryPlot3D) chart.getPlot();
        plot.setDimensions(new Dimension3D(18, 8, 4));
        plot.getRowAxis().setVisible(false);
        NumberAxis3D valueAxis = (NumberAxis3D) plot.getValueAxis();
        valueAxis.setTickSelector(new NumberTickSelector(true));
        this.chartPanel3D = new ChartPanel3D(chart);
        content.add(new DisplayPanel3D(this.chartPanel3D, true));
        return content;
    }
  
    /**
     * Creates a dataset.
     *
     * @return the dataset.
     */
    private static CategoryDataset3D createDataset() {
        StandardCategoryDataset3D dataset = new StandardCategoryDataset3D();
        dataset.addSeriesAsRow("Safari", createSafariData());
        dataset.addSeriesAsRow("Firefox", createFirefoxData());
        dataset.addSeriesAsRow("Internet Explorer", createInternetExplorerData());
        dataset.addSeriesAsRow("Chrome", createChromeData());
        return dataset;
    }

    private static KeyedValues createChromeData() {
        DefaultKeyedValues series = new DefaultKeyedValues();
        series.addValue("Jan-2012", 0.2840);
        series.addValue("Feb-2012", 0.2984);
        series.addValue("Mar-2012", 0.3087);
        series.addValue("Apr-2012", 0.3123);
        series.addValue("May-2012", 0.3243);
        series.addValue("Jun-2012", 0.3276);
        series.addValue("Jul-2012", 0.3381);
        series.addValue("Aug-2012", 0.3359);
        series.addValue("Sep-2012", 0.3421);
        series.addValue("Oct-2012", 0.3477);
        series.addValue("Nov-2012", 0.3572);
        series.addValue("Dec-2012", 0.3642);

        series.addValue("Jan-2013", 0.3652);
        series.addValue("Feb-2013", 0.3709);
        series.addValue("Mar-2013", 0.3807);
        series.addValue("Apr-2013", 0.3915);
        series.addValue("May-2013", 0.4138);
        series.addValue("Jun-2013", 0.4268);
        return series;
    }

    private static KeyedValues createFirefoxData() {
        DefaultKeyedValues series = new DefaultKeyedValues();
        series.addValue("Jan-2012", 0.2478);
        series.addValue("Feb-2012", 0.2488);
        series.addValue("Mar-2012", 0.2498);
        series.addValue("Apr-2012", 0.2487);
        series.addValue("May-2012", 0.2555);
        series.addValue("Jun-2012", 0.2456);
        series.addValue("Jul-2012", 0.2373);
        series.addValue("Aug-2012", 0.2285);
        series.addValue("Sep-2012", 0.2240);
        series.addValue("Oct-2012", 0.2232);
        series.addValue("Nov-2012", 0.2237);
        series.addValue("Dec-2012", 0.2189);
        series.addValue("Jan-2013", 0.2142);
        series.addValue("Feb-2013", 0.2134);
        series.addValue("Mar-2013", 0.2087);
        series.addValue("Apr-2013", 0.2006);
        series.addValue("May-2013", 0.1976);
        series.addValue("Jun-2013", 0.2001);
        return series;
    }

    private static KeyedValues createInternetExplorerData() {
        DefaultKeyedValues series = new DefaultKeyedValues();
        series.addValue("Jan-2012", 0.3745);
        series.addValue("Feb-2012", 0.3575);
        series.addValue("Mar-2012", 0.3481);
        series.addValue("Apr-2012", 0.3407);
        series.addValue("May-2012", 0.3212);
        series.addValue("Jun-2012", 0.3231);
        series.addValue("Jul-2012", 0.3204);
        series.addValue("Aug-2012", 0.3285);
        series.addValue("Sep-2012", 0.3270);
        series.addValue("Oct-2012", 0.3208);
        series.addValue("Nov-2012", 0.3123);
        series.addValue("Dec-2012", 0.3078);
        series.addValue("Jan-2013", 0.3069);
        series.addValue("Feb-2013", 0.2982);
        series.addValue("Mar-2013", 0.2930);
        series.addValue("Jun-2013", 0.2544);
        series.addValue("May-2013", 0.2772);
        series.addValue("Apr-2013", 0.2971);
        return series;
    }
//
    private static KeyedValues createSafariData() {
        DefaultKeyedValues series = new DefaultKeyedValues();
        series.addValue("Jan-2012", 0.0662);
        series.addValue("Feb-2012", 0.0677);
        series.addValue("Mar-2012", 0.0672);
        series.addValue("Apr-2012", 0.0713);
        series.addValue("May-2012", 0.0709);
        series.addValue("Jun-2012", 0.0700);
        series.addValue("Jul-2012", 0.0712);
        series.addValue("Aug-2012", 0.0739);
        series.addValue("Sep-2012", 0.0770);
        series.addValue("Oct-2012", 0.0781);
        series.addValue("Nov-2012", 0.0783);
        series.addValue("Dec-2012", 0.0792);
        series.addValue("Jan-2013", 0.0830);
        series.addValue("Feb-2013", 0.0860);
        series.addValue("Mar-2013", 0.0850);
        series.addValue("Apr-2013", 0.0800);
        series.addValue("May-2013", 0.0796);
        series.addValue("Jun-2013", 0.0839);
        return series;
    }
    
    /**
     * Starting point for the app.
     *
     * @param args  command line arguments (ignored).
     */
    public static void main(String[] args) {
        LineChart3DDemo1 app = new LineChart3DDemo1(
                "OrsonCharts: LineChart3DDemo1.java");
        app.pack();
        app.setVisible(true);
    }
}

