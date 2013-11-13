/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.demo;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import com.orsoncharts.ChartPanel3D;
import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.axis.ValueAxis3D;
import com.orsoncharts.data.xyz.XYZDataItem;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.data.xyz.XYZSeries;
import com.orsoncharts.data.xyz.XYZSeriesCollection;
import static com.orsoncharts.demo.AxisRangeDemo5.createDemoPanel;
import com.orsoncharts.graphics3d.ViewPoint3D;
import com.orsoncharts.graphics3d.swing.DisplayPanel3D;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.renderer.xyz.BarXYZRenderer;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * A demonstration of an XYZ bar chart in 3D.
 */
public class AxisRangeDemo6 extends JFrame {

    static class CustomDemoPanel extends DemoPanel implements ChangeListener {
        
        private JSlider xslider1;
        
        private JSlider xslider2;
        
        private JSlider yslider1;
        
        private JSlider yslider2;

        private JSlider zslider1;
        
        private JSlider zslider2;

        public CustomDemoPanel(LayoutManager layout) {
            super(layout);
            this.xslider1 = new JSlider(0, 50);
            this.xslider1.setValue(0);
            this.xslider2 = new JSlider(50, 100);
            this.xslider2.setValue(100);
            this.xslider1.addChangeListener(this);
            this.xslider2.addChangeListener(this);
            
            this.yslider1 = new JSlider(0, 50);
            this.yslider1.setValue(0);
            this.yslider2 = new JSlider(50, 100);
            this.yslider2.setValue(100);
            this.yslider1.addChangeListener(this);
            this.yslider2.addChangeListener(this);

            this.zslider1 = new JSlider(0, 50);
            this.zslider1.setValue(0);
            this.zslider2 = new JSlider(50, 100);
            this.zslider2.setValue(100);
            this.zslider1.addChangeListener(this);
            this.zslider2.addChangeListener(this);

            JPanel rangePanel = new JPanel(new GridLayout(3, 1));
            JPanel xPanel = new JPanel(new FlowLayout());
            xPanel.add(new JLabel("X axis: "));
            xPanel.add(new JLabel("Lower bound: "));
            xPanel.add(this.xslider1);
            xPanel.add(new JLabel("Upper bound: "));
            xPanel.add(this.xslider2);
            rangePanel.add(xPanel);

            JPanel yPanel = new JPanel(new FlowLayout());
            yPanel.add(new JLabel("Y axis: "));
            yPanel.add(new JLabel("Lower bound: "));
            yPanel.add(this.yslider1);
            yPanel.add(new JLabel("Upper bound: "));
            yPanel.add(this.yslider2);
            rangePanel.add(yPanel);
            
            JPanel zPanel = new JPanel(new FlowLayout());
            zPanel.add(new JLabel("Z axis: "));
            zPanel.add(new JLabel("Lower bound: "));
            zPanel.add(this.zslider1);
            zPanel.add(new JLabel("Upper bound: "));
            zPanel.add(this.zslider2);
            rangePanel.add(zPanel);

            add(rangePanel, BorderLayout.SOUTH);
        }    

        @Override
        public void stateChanged(ChangeEvent e) {
            Chart3D chart = (Chart3D) getChartPanel().getDrawable();
            XYZPlot plot = (XYZPlot) chart.getPlot();
            ValueAxis3D xAxis = plot.getXAxis();
            double xmin = this.xslider1.getValue() / 50.0 * Math.PI;
            double xmax = this.xslider2.getValue() / 50.0 * Math.PI;
            if (xmin != xmax) {
                xAxis.setRange(xmin, xmax);
            }

            ValueAxis3D yAxis = plot.getYAxis();
            double ymin = this.yslider1.getValue() / 50.0;
            double ymax = this.yslider2.getValue() / 50.0;
            if (ymin != ymax) {
                yAxis.setRange(ymin, ymax);
            }
        
            ValueAxis3D zAxis = plot.getZAxis();
            double zmin = Math.PI + this.zslider1.getValue() / 100.0 * Math.PI;
            double zmax = Math.PI + this.zslider2.getValue() / 100.0 * Math.PI;
            if (zmin != zmax) {
                zAxis.setRange(zmin, zmax);
            }
        
        }
    }

    /**
     * Creates a new test app.
     *
     * @param title  the frame title.
     */
    public AxisRangeDemo6(String title) {
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
        CustomDemoPanel content = new CustomDemoPanel(new BorderLayout());
        content.setPreferredSize(OrsonChartsDemo.DEFAULT_CONTENT_SIZE);
        XYZDataset dataset = createDataset();
        Chart3D chart = Chart3DFactory.createXYZBarChart("AxisRangeDemo6", 
                "Chart created with Orson Charts", dataset, "X", "Y", "Z");
        XYZPlot plot = (XYZPlot) chart.getPlot();
        plot.getXAxis().setRange(0, 2 * Math.PI);
        plot.getYAxis().setRange(0, 2);
        plot.getZAxis().setRange(Math.PI, 2 * Math.PI);
        BarXYZRenderer renderer = (BarXYZRenderer) plot.getRenderer();
        renderer.setBarXWidth(0.05);
        renderer.setBarZWidth(0.05);
        chart.setViewPoint(ViewPoint3D.createAboveLeftViewPoint(40));
        ChartPanel3D chartPanel = new ChartPanel3D(chart);
        content.setChartPanel(chartPanel);
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
    private static XYZDataset createDataset() {
        XYZSeries series1 = new XYZSeries("y = cos(x) * sin(z)");
        for (double x = Math.PI / 2; x < 3 * Math.PI / 2; x += Math.PI / 20) {
            for (double z = Math.PI; z < 2 * Math.PI; z += Math.PI / 20) {
                series1.add(new XYZDataItem(x, 2 * Math.cos(x) * Math.sin(z), z));            
            }
        }
        XYZSeriesCollection dataset = new XYZSeriesCollection();
        dataset.add(series1);
        return dataset;
    }
    
    /**
     * Starting point for the app.
     *
     * @param args  command line arguments (ignored).
     */
    public static void main(String[] args) {
        AxisRangeDemo6 app = new AxisRangeDemo6(
                "OrsonCharts : AxisRangeDemo6.java");
        app.pack();
        app.setVisible(true);
    }
}
