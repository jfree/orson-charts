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
import com.orsoncharts.Range;
import com.orsoncharts.data.xyz.StandardXYZDataset;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.data.xyz.XYZSeries;
import com.orsoncharts.data.xyz.XYZSeriesCollection;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.swing.DisplayPanel3D;
import com.orsoncharts.plot.XYZPlot;

/**
 * A test app.
 */
public class ScatterPlot3DDemo1 extends JFrame {

    ChartPanel3D chartPanel3D;

    /**
     * Creates a new test app.
     *
     * @param title  the frame title.
     */
    public ScatterPlot3DDemo1(String title) {
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
        XYZDataset dataset = createDataset();
        Chart3D chart = Chart3DFactory.createScatterPlot("ScatterPlot3DDemo1", 
                dataset, "X", "Y", "Z");
        XYZPlot plot = (XYZPlot) chart.getPlot();
        plot.setDimensions(new Dimension3D(5.0, 2.0, 2.0));
        this.chartPanel3D = new ChartPanel3D(chart);
        content.add(new DisplayPanel3D(this.chartPanel3D, true));
        return content;
    }

    private XYZDataset createDataset() {
        XYZSeries s1 = createRandomSeries("S1", 10);
        XYZSeries s2 = createRandomSeries("S2", 50);
        XYZSeries s3 = createRandomSeries("S3", 150);
        XYZSeriesCollection dataset = new XYZSeriesCollection();
        dataset.add(s1);
        dataset.add(s2);
        dataset.add(s3);
        return dataset;
    }
    
    private XYZSeries createRandomSeries(String name, int count) {
        XYZSeries s = new XYZSeries(name);
        for (int i = 0; i < count; i++) {
            s.add(Math.random() * 100, Math.random() / 100, Math.random() * 100);
        }
        return s;
    }
    /**
     * Starting point for the app.
     *
     * @param args  command line arguments (ignored).
     */
    public static void main(String[] args) {
        ScatterPlot3DDemo1 app = new ScatterPlot3DDemo1(
                "OrsonCharts : ScatterPlot3DDemo1.java");
        app.pack();
        app.setVisible(true);
    }
}
