/* ===================
 * Orson Charts - Demo
 * ===================
 * 
 * Copyright (c) 2013-2016, Object Refinery Limited.
 * All rights reserved.
 *
 * http://www.object-refinery.com/orsoncharts/index.html
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *   - Neither the name of the Object Refinery Limited nor the
 *     names of its contributors may be used to endorse or promote products
 *     derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL OBJECT REFINERY LIMITED BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * Note that the above terms apply to the demo source only, and not the 
 * Orson Charts library.
 * 
 */

package com.orsoncharts.demo.swing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.orsoncharts.Chart3DPanel;
import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.Range;
import com.orsoncharts.axis.ValueAxis3D;
import com.orsoncharts.data.DataUtils;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.data.xyz.XYZSeries;
import com.orsoncharts.data.xyz.XYZSeriesCollection;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.ViewPoint3D;
import com.orsoncharts.graphics3d.swing.DisplayPanel3D;
import com.orsoncharts.plot.XYZPlot;

/**
 * A demonstration of a scatter plot in 3D.
 */
@SuppressWarnings("serial")
public class AxisRangeDemo7 extends JFrame {

    static class CustomDemoPanel extends DemoPanel implements ChangeListener {
        
        private JSlider xslider1;
        
        private JSlider xslider2;
        
        private JSlider yslider1;
        
        private JSlider yslider2;

        private JSlider zslider1;
        
        private JSlider zslider2;

        public CustomDemoPanel(LayoutManager layout, XYZDataset dataset) {
            super(layout);
            Range xRange = DataUtils.findXRange(dataset);
            Range yRange = DataUtils.findYRange(dataset);
            Range zRange = DataUtils.findZRange(dataset);
            int xstart = (int) Math.floor(xRange.getMin());
            int ystart = (int) Math.floor(yRange.getMin());
            int zstart = (int) Math.floor(zRange.getMin());
            int xend = (int) Math.ceil(xRange.getMax());
            int yend = (int) Math.ceil(yRange.getMax());
            int zend = (int) Math.ceil(zRange.getMax());
            int xmid = (xstart + xend) / 2;
            int ymid = (ystart + yend) / 2;
            int zmid = (zstart + zend) / 2;
            this.xslider1 = new JSlider(xstart, xmid);
            this.xslider1.setValue(xstart);
            this.xslider2 = new JSlider(xmid, xend);
            this.xslider2.setValue(xend);
            this.xslider1.addChangeListener(this);
            this.xslider2.addChangeListener(this);
            
            this.yslider1 = new JSlider(ystart, ymid);
            this.yslider1.setValue(ystart);
            this.yslider2 = new JSlider(ymid, yend);
            this.yslider2.setValue(yend);
            this.yslider1.addChangeListener(this);
            this.yslider2.addChangeListener(this);

            this.zslider1 = new JSlider(zstart, zmid);
            this.zslider1.setValue(zstart);
            this.zslider2 = new JSlider(zmid, zend);
            this.zslider2.setValue(zend);
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
            double xmin = this.xslider1.getValue();
            double xmax = this.xslider2.getValue();
            if (xmin != xmax) {
                xAxis.setRange(xmin, xmax);
            }

            ValueAxis3D yAxis = plot.getYAxis();
            double ymin = this.yslider1.getValue();
            double ymax = this.yslider2.getValue();
            if (ymin != ymax) {
                yAxis.setRange(ymin, ymax);
            }
        
            ValueAxis3D zAxis = plot.getZAxis();
            double zmin = this.zslider1.getValue();
            double zmax = this.zslider2.getValue();
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
    public AxisRangeDemo7(String title) {
        super(title);
        addWindowListener(new ExitOnClose());
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
        XYZDataset dataset = createDataset();
        DemoPanel content = new CustomDemoPanel(new BorderLayout(), dataset);
        content.setPreferredSize(OrsonChartsDemo.DEFAULT_CONTENT_SIZE);
        Chart3D chart = Chart3DFactory.createXYZLineChart("AxisRangeDemo7", 
                "Chart created with Orson Charts", dataset, "X", "Y", "Z");
        XYZPlot plot = (XYZPlot) chart.getPlot();
        plot.setDimensions(new Dimension3D(10.0, 4.0, 10.0));
        chart.setViewPoint(ViewPoint3D.createAboveLeftViewPoint(40));
        Chart3DPanel chartPanel = new Chart3DPanel(chart);
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
    private static XYZDataset<String> createDataset() {
        XYZSeriesCollection<String> dataset = new XYZSeriesCollection<String>();
        for (int s = 1; s <= 10; s++) {
            XYZSeries<String> s1 = createRandomSeries("S" + s, s, 100);
            dataset.add(s1);
        }
        return dataset;
    }
   
    private static XYZSeries<String> createRandomSeries(String name, int series,
            int count) {
        XYZSeries<String> s = new XYZSeries<String>(name);
        double y = 10.0;
        for (int i = 0; i < count; i++) {
            y = y + (Math.random() - 0.48) * 2.0;
            s.add(i, y, series * 30);
        }
        return s;
    }
    
    /**
     * Starting point for the app.
     *
     * @param args  command line arguments (ignored).
     */
    public static void main(String[] args) {
        AxisRangeDemo7 app = new AxisRangeDemo7(
                "OrsonCharts : AxisRangeDemo7.java");
        app.pack();
        app.setVisible(true);
    }
}
