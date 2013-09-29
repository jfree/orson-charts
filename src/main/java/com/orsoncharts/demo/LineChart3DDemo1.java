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
        CategoryDataset3D dataset = createDataset2();
        Chart3D chart = Chart3DFactory.createLineChart("LineChart3DDemo1", 
                dataset, null, null, "Market Share (%)");
        CategoryPlot3D plot = (CategoryPlot3D) chart.getPlot();
        plot.setDimensions(new Dimension3D(18, 4, 4));
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
        s1.addValue("Q1", 1.0);
        s1.addValue("Q2", -2.0);
        s1.addValue("Q3", 3.0);
        s1.addValue("Q4", 4.0);
        dataset.addSeriesAsRow("S1", s1);
        
        DefaultKeyedValues s2 = new DefaultKeyedValues();
        s2.addValue("Q1", 4.0);
        s2.addValue("Q2", 3.0);
        s2.addValue("Q3", 2.0);
        s2.addValue("Q4", 1.0);
        dataset.addSeriesAsRow("S2", s2);
        
        return dataset;
    }
    /**
     * Creates a dataset.
     *
     * @return the dataset.
     */
    private static CategoryDataset3D createDataset2() {
        DefaultCategoryDataset3D dataset = new DefaultCategoryDataset3D();
        dataset.addSeriesAsRow("Firefox", createFirefoxData());
        dataset.addSeriesAsRow("Internet Explorer", createInternetExplorerData());
        dataset.addSeriesAsRow("Safari", createSafariData());
        //dataset.addSeriesAsRow("Chrome", createChromeData());
        return dataset;
    }

    private static KeyedValues createChromeData() {
        DefaultKeyedValues series = new DefaultKeyedValues();
        series.addValue("Jan-2010", 0.0604);
        series.addValue("Feb-2010", 0.0672);
        series.addValue("Mar-2010", 0.0729);
        series.addValue("Apr-2010", 0.0806);
        series.addValue("May-2010", 0.0861);
        series.addValue("Jun-2010", 0.0924);
        series.addValue("Jul-2010", 0.0988);
        series.addValue("Aug-2010", 0.1076);
        series.addValue("Sep-2010", 0.1154);
        series.addValue("Oct-2010", 0.1239);
        series.addValue("Nov-2010", 0.1335);
        series.addValue("Dec-2010", 0.1485);

        series.addValue("Jan-2011", 0.1568);
        series.addValue("Feb-2011", 0.1654);
        series.addValue("Mar-2011", 0.1737);
        series.addValue("Apr-2011", 0.1829);
        series.addValue("May-2011", 0.1936);
        series.addValue("Jun-2011", 0.2065);
        series.addValue("Jul-2011", 0.2214);
        series.addValue("Aug-2011", 0.2316);
        series.addValue("Sep-2011", 0.2361);
        series.addValue("Oct-2011", 0.2500);
        series.addValue("Nov-2011", 0.2569);
        series.addValue("Dec-2011", 0.2727);
        
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
//        series.addValue("Dec-2009", 0.0545);
//        series.addValue("Nov-2009", 0.0466);
//        series.addValue("Oct-2009", 0.0417);
//        series.addValue("Sep-2009", 0.0369);
//        series.addValue("Aug-2009", 0.0338);
//        series.addValue("Jul-2009", 0.0301);
//        series.addValue("Jun-2009", 0.0282);
//        series.addValue("May-2009", 0.0242);
//        series.addValue("Apr-2009", 0.0207);
//        series.addValue("Mar-2009", 0.0173);
//        series.addValue("Feb-2009", 0.0152);
//        series.addValue("Jan-2009", 0.0138);
        return series;
    }

    private static KeyedValues createFirefoxData() {
        DefaultKeyedValues series = new DefaultKeyedValues();
        series.addValue("Apr-2013", 0.2006);
        series.addValue("May-2013", 0.1976);
        series.addValue("Jun-2013", 0.2001);
//        series.addValue(new Month(3, 2013), 0.2087);
//        series.addValue(new Month(2, 2013), 0.2134);
//        series.addValue(new Month(1, 2013), 0.2142);
//        series.addValue(new Month(12, 2012), 0.2189);
//        series.addValue(new Month(11, 2012), 0.2237);
//        series.addValue(new Month(10, 2012), 0.2232);
//        series.addValue(new Month(9, 2012), 0.2240);
//        series.addValue(new Month(8, 2012), 0.2285);
//        series.addValue(new Month(7, 2012), 0.2373);
//        series.addValue(new Month(6, 2012), 0.2456);
//        series.addValue(new Month(5, 2012), 0.2555);
//        series.addValue(new Month(4, 2012), 0.2487);
//        series.addValue(new Month(3, 2012), 0.2498);
//        series.addValue(new Month(2, 2012), 0.2488);
//        series.addValue(new Month(1, 2012), 0.2478);
//        series.addValue(new Month(12, 2011), 0.2527);
//        series.addValue(new Month(11, 2011), 0.2523);
//        series.addValue(new Month(10, 2011), 0.2639);
//        series.addValue(new Month(9, 2011), 0.2679);
//        series.addValue(new Month(8, 2011), 0.2749);
//        series.addValue(new Month(7, 2011), 0.2795);
//        series.addValue(new Month(6, 2011), 0.2834);
//        series.addValue(new Month(5, 2011), 0.2929);
//        series.addValue(new Month(4, 2011), 0.2967);
//        series.addValue(new Month(3, 2011), 0.2998);
//        series.addValue(new Month(2, 2011), 0.3037);
//        series.addValue(new Month(1, 2011), 0.3068);
//        series.addValue(new Month(12, 2010), 0.3076);
//        series.addValue(new Month(11, 2010), 0.3117);
//        series.addValue(new Month(10, 2010), 0.3124);
//        series.addValue(new Month(9, 2010), 0.3150);
//        series.addValue(new Month(8, 2010), 0.3109);
//        series.addValue(new Month(7, 2010), 0.3069);
//        series.addValue(new Month(6, 2010), 0.3115);
//        series.addValue(new Month(5, 2010), 0.3164);
//        series.addValue(new Month(4, 2010), 0.3174);
//        series.addValue(new Month(3, 2010), 0.3127);
//        series.addValue(new Month(2, 2010), 0.3182);
//        series.addValue(new Month(1, 2010), 0.3164);
//        series.addValue(new Month(12, 2009), 0.3197);
//        series.addValue(new Month(11, 2009), 0.3221);
//        series.addValue(new Month(10, 2009), 0.3182);
//        series.addValue(new Month(9, 2009), 0.3134);
//        series.addValue(new Month(8, 2009), 0.3128);
//        series.addValue(new Month(7, 2009), 0.3050);
//        series.addValue(new Month(6, 2009), 0.3033);
//        series.addValue(new Month(5, 2009), 0.2875);
//        series.addValue(new Month(4, 2009), 0.2967);
//        series.addValue(new Month(3, 2009), 0.2940);
//        series.addValue(new Month(2, 2009), 0.2785);
//        series.addValue(new Month(1, 2009), 0.2703);
        return series;
    }

    private static KeyedValues createInternetExplorerData() {
        DefaultKeyedValues series = new DefaultKeyedValues();
        series.addValue("Jun-2013", 0.2544);
        series.addValue("May-2013", 0.2772);
        series.addValue("Apr-2013", 0.2971);
//        series.addValue(new Month(3, 2013), 0.2930);
//        series.addValue(new Month(2, 2013), 0.2982);
//        series.addValue(new Month(1, 2013), 0.3069);
//        series.addValue(new Month(12, 2012), 0.3078);
//        series.addValue(new Month(11, 2012), 0.3123);
//        series.addValue(new Month(10, 2012), 0.3208);
//        series.addValue(new Month(9, 2012), 0.3270);
//        series.addValue(new Month(8, 2012), 0.3285);
//        series.addValue(new Month(7, 2012), 0.3204);
//        series.addValue(new Month(6, 2012), 0.3231);
//        series.addValue(new Month(5, 2012), 0.3212);
//        series.addValue(new Month(4, 2012), 0.3407);
//        series.addValue(new Month(3, 2012), 0.3481);
//        series.addValue(new Month(2, 2012), 0.3575);
//        series.addValue(new Month(1, 2012), 0.3745);
//        series.addValue(new Month(12, 2011), 0.3865);
//        series.addValue(new Month(11, 2011), 0.4063);
//        series.addValue(new Month(10, 2011), 0.4018);
//        series.addValue(new Month(9, 2011), 0.4166);
//        series.addValue(new Month(8, 2011), 0.4189);
//        series.addValue(new Month(7, 2011), 0.4245);
//        series.addValue(new Month(6, 2011), 0.4358);
//        series.addValue(new Month(5, 2011), 0.4387);
//        series.addValue(new Month(4, 2011), 0.4452);
//        series.addValue(new Month(3, 2011), 0.4511);
//        series.addValue(new Month(2, 2011), 0.4544);
//        series.addValue(new Month(1, 2011), 0.4600);
//        series.addValue(new Month(12, 2010), 0.4694);
//        series.addValue(new Month(11, 2010), 0.4816);
//        series.addValue(new Month(10, 2010), 0.4921);
//        series.addValue(new Month(9, 2010), 0.4987);
//        series.addValue(new Month(8, 2010), 0.5134);
//        series.addValue(new Month(7, 2010), 0.5268);
//        series.addValue(new Month(6, 2010), 0.5286);
//        series.addValue(new Month(5, 2010), 0.5277);
//        series.addValue(new Month(4, 2010), 0.5326);
//        series.addValue(new Month(3, 2010), 0.5444);
//        series.addValue(new Month(2, 2010), 0.5450);
//        series.addValue(new Month(1, 2010), 0.5525);
//        series.addValue(new Month(12, 2009), 0.5572);
//        series.addValue(new Month(11, 2009), 0.5657);
//        series.addValue(new Month(10, 2009), 0.5796);
//        series.addValue(new Month(9, 2009), 0.5837);
//        series.addValue(new Month(8, 2009), 0.5869);
//        series.addValue(new Month(7, 2009), 0.6011);
//        series.addValue(new Month(6, 2009), 0.5949);
//        series.addValue(new Month(5, 2009), 0.6209);
//        series.addValue(new Month(4, 2009), 0.6188);
//        series.addValue(new Month(3, 2009), 0.6252);
//        series.addValue(new Month(2, 2009), 0.6443);
//        series.addValue(new Month(1, 2009), 0.6541);
        return series;
    }
//
    private static KeyedValues createSafariData() {
        DefaultKeyedValues series = new DefaultKeyedValues();
        series.addValue("Jun-2013", 0.0839);
        series.addValue("May-2013", 0.0796);
        series.addValue("Apr-2013", 0.0800);
//        series.addValue(new Month(3, 2013), 0.0850);
//        series.addValue(new Month(2, 2013), 0.0860);
//        series.addValue(new Month(1, 2013), 0.0830);
//        series.addValue(new Month(12, 2012), 0.0792);
//        series.addValue(new Month(11, 2012), 0.0783);
//        series.addValue(new Month(10, 2012), 0.0781);
//        series.addValue(new Month(9, 2012), 0.0770);
//        series.addValue(new Month(8, 2012), 0.0739);
//        series.addValue(new Month(7, 2012), 0.0712);
//        series.addValue(new Month(6, 2012), 0.0700);
//        series.addValue(new Month(5, 2012), 0.0709);
//        series.addValue(new Month(4, 2012), 0.0713);
//        series.addValue(new Month(3, 2012), 0.0672);
//        series.addValue(new Month(2, 2012), 0.0677);
//        series.addValue(new Month(1, 2012), 0.0662);
//        series.addValue(new Month(12, 2011), 0.0608);
//        series.addValue(new Month(11, 2011), 0.0592);
//        series.addValue(new Month(10, 2011), 0.0593);
//        series.addValue(new Month(9, 2011), 0.0560);
//        series.addValue(new Month(8, 2011), 0.0519);
//        series.addValue(new Month(7, 2011), 0.0517);
//        series.addValue(new Month(6, 2011), 0.0507);
//        series.addValue(new Month(5, 2011), 0.0501);
//        series.addValue(new Month(4, 2011), 0.0504);
//        series.addValue(new Month(3, 2011), 0.0502);
//        series.addValue(new Month(2, 2011), 0.0508);
//        series.addValue(new Month(1, 2011), 0.0509);
//        series.addValue(new Month(12, 2010), 0.0479);
//        series.addValue(new Month(11, 2010), 0.0470);
//        series.addValue(new Month(10, 2010), 0.0456);
//        series.addValue(new Month(9, 2010), 0.0442);
//        series.addValue(new Month(8, 2010), 0.0423);
//        series.addValue(new Month(7, 2010), 0.0409);
//        series.addValue(new Month(6, 2010), 0.0407);
//        series.addValue(new Month(5, 2010), 0.0414);
//        series.addValue(new Month(4, 2010), 0.0423);
//        series.addValue(new Month(3, 2010), 0.0416);
//        series.addValue(new Month(2, 2010), 0.0408);
//        series.addValue(new Month(1, 2010), 0.0376);
//        series.addValue(new Month(12, 2009), 0.0348);
//        series.addValue(new Month(11, 2009), 0.0367);
//        series.addValue(new Month(10, 2009), 0.0347);
//        series.addValue(new Month(9, 2009), 0.0328);
//        series.addValue(new Month(8, 2009), 0.0325);
//        series.addValue(new Month(7, 2009), 0.0302);
//        series.addValue(new Month(6, 2009), 0.0293);
//        series.addValue(new Month(5, 2009), 0.0265);
//        series.addValue(new Month(4, 2009), 0.0275);
//        series.addValue(new Month(3, 2009), 0.0273);
//        series.addValue(new Month(2, 2009), 0.0259);
//        series.addValue(new Month(1, 2009), 0.0257);
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

