/* ===================
 * Orson Charts - Demo
 * ===================
 * 
 * Copyright (c) 2013, 2014, Object Refinery Limited.
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

package com.orsoncharts.demo;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.orsoncharts.ChartPanel3D;
import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.axis.NumberAxis3D;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.data.xyz.XYZItemKey;
import com.orsoncharts.data.xyz.XYZSeries;
import com.orsoncharts.data.xyz.XYZSeriesCollection;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.Object3D;
import com.orsoncharts.graphics3d.RenderedElement;
import com.orsoncharts.graphics3d.ViewPoint3D;
import com.orsoncharts.graphics3d.swing.DisplayPanel3D;
import com.orsoncharts.interaction.Chart3DMouseEvent;
import com.orsoncharts.interaction.Chart3DMouseListener;
import com.orsoncharts.interaction.StandardXYZDataItemSelection;
import com.orsoncharts.interaction.XYZDataItemSelection;
import com.orsoncharts.label.StandardXYZItemLabelGenerator;
import com.orsoncharts.marker.RangeMarker;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.renderer.xyz.ScatterXYZRenderer;
import com.orsoncharts.renderer.xyz.StandardXYZColorSource;
import com.orsoncharts.style.ChartStyler;
import com.orsoncharts.style.ChartStyles;
import com.orsoncharts.util.Anchor2D;

/**
 * A demonstration of range markers on the axes.
 */
@SuppressWarnings("serial")
public class RangeMarkerDemo1 extends JFrame {

    static class CustomDemoPanel extends DemoPanel implements ActionListener,
            Chart3DMouseListener {
        
        private JCheckBox checkBox;
        
        public CustomDemoPanel(LayoutManager layout) {
            super(layout);
            this.checkBox = new JCheckBox("Highlight items within range intersection");
            this.checkBox.setSelected(true);
            this.checkBox.addActionListener(this);
            JPanel controlPanel = new JPanel(new FlowLayout());
            controlPanel.add(this.checkBox);
            add(controlPanel, BorderLayout.SOUTH);
        }    

        @Override
        public void actionPerformed(ActionEvent e) {
            Chart3D chart = (Chart3D) getChartPanel().getDrawable();
            XYZPlot plot = (XYZPlot) chart.getPlot();
            RangeMarker xm = (RangeMarker) plot.getXAxis().getMarker("X1");
            RangeMarker ym = (RangeMarker) plot.getYAxis().getMarker("Y1");
            RangeMarker zm = (RangeMarker) plot.getZAxis().getMarker("Z1");
            if (this.checkBox.isSelected()) {
                HighlightXYZColorSource colorSource 
                        = new HighlightXYZColorSource(plot.getDataset(), 
                        Color.RED, xm.getRange(), ym.getRange(), zm.getRange(), 
                        chart.getStyle().getStandardColors());
                plot.getRenderer().setColorSource(colorSource);
            } else {
                StandardXYZColorSource colorSource = new StandardXYZColorSource(
                        chart.getStyle().getStandardColors());
                plot.getRenderer().setColorSource(colorSource);                
            }
        }

        @Override
        public void chartMouseClicked(Chart3DMouseEvent event) {
            RenderedElement element = event.getElement();
            XYZItemKey key = (XYZItemKey) element.getProperty(Object3D.ITEM_KEY);
            if (key == null) {
                getItemSelection().clear();
                getChartPanel().getChart().setNotify(true);
                return;
            }
            if (event.getTrigger().isShiftDown()) {
                getItemSelection().add(key);
            } else {
                getItemSelection().clear();
                getItemSelection().add(key);
            }
            getChartPanel().getChart().setNotify(true);
        }
        
        private StandardXYZDataItemSelection getItemSelection() {
            Chart3D chart = getChartPanel().getChart();
            XYZPlot plot = (XYZPlot) chart.getPlot();
            ScatterXYZRenderer renderer = (ScatterXYZRenderer) plot.getRenderer();
            StandardXYZItemLabelGenerator itemLabelGenerator 
                    = (StandardXYZItemLabelGenerator) renderer.getItemLabelGenerator();
            return (StandardXYZDataItemSelection) itemLabelGenerator.getItemSelection();
        }
        
        @Override
        public void chartMouseMoved(Chart3DMouseEvent event) {
            // not interested in these
        }
    }

    /**
     * Creates a new test app.
     *
     * @param title  the frame title.
     */
    public RangeMarkerDemo1(String title) {
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
        CustomDemoPanel content = new CustomDemoPanel(new BorderLayout());
        content.setPreferredSize(OrsonChartsDemo.DEFAULT_CONTENT_SIZE);
        XYZDataset dataset = createDataset();
        Chart3D chart = Chart3DFactory.createScatterChart("RangeMarkerDemo1", 
                null, dataset, "X", "Y", "Z");
        chart.setStyle(ChartStyles.createOrson1Style());
        
        XYZPlot plot = (XYZPlot) chart.getPlot();
        plot.setDimensions(new Dimension3D(10.0, 6.0, 10.0));

        ChartStyler styler = new ChartStyler(chart.getStyle());
        NumberAxis3D xAxis = (NumberAxis3D) plot.getXAxis();
        RangeMarker xMarker1 = new RangeMarker(60, 90, "X: 60 to 90");
        xMarker1.receive(styler);
        xMarker1.setFillColor(new Color(128, 128, 255, 128));
        xMarker1.setLabelAnchor(Anchor2D.BOTTOM_LEFT);
        xAxis.setMarker("X1", xMarker1);
        NumberAxis3D yAxis = (NumberAxis3D) plot.getYAxis();
        RangeMarker yMarker1 = new RangeMarker(0.002, 0.006, "Y: 0.002 to 0.006");
        yMarker1.receive(styler);
        yMarker1.setFillColor(new Color(128, 255, 128, 128));
        yAxis.setMarker("Y1", yMarker1);
        NumberAxis3D zAxis = (NumberAxis3D) plot.getZAxis();
        RangeMarker zMarker1 = new RangeMarker(20, 60, "Z: 20 to 60");
        zMarker1.setLabelAnchor(Anchor2D.TOP_LEFT);
        zMarker1.receive(styler);
        zMarker1.setFillColor(new Color(255, 128, 128, 128));
        zAxis.setMarker("Z1", zMarker1);
        ScatterXYZRenderer renderer = (ScatterXYZRenderer) plot.getRenderer();
        renderer.setSize(0.15);
        HighlightXYZColorSource colorSource = new HighlightXYZColorSource(
                plot.getDataset(), Color.RED, xMarker1.getRange(), 
                yMarker1.getRange(), zMarker1.getRange(), 
                chart.getStyle().getStandardColors());
        renderer.setColorSource(colorSource);
        StandardXYZItemLabelGenerator generator 
                = new StandardXYZItemLabelGenerator();
        XYZDataItemSelection selection = new StandardXYZDataItemSelection();
        generator.setItemSelection(selection);
        renderer.setItemLabelGenerator(generator);
        chart.setViewPoint(ViewPoint3D.createAboveLeftViewPoint(40));
        
        ChartPanel3D chartPanel = new ChartPanel3D(chart);
        content.setChartPanel(chartPanel);
        chartPanel.addChartMouseListener(content);
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
        XYZSeries s1 = createRandomSeries("S1", 10);
        XYZSeries s2 = createRandomSeries("S2", 50);
        XYZSeries s3 = createRandomSeries("S3", 150);
        XYZSeriesCollection dataset = new XYZSeriesCollection();
        dataset.add(s1);
        dataset.add(s2);
        dataset.add(s3);
        return dataset;
    }
    
    private static XYZSeries createRandomSeries(String name, int count) {
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
        RangeMarkerDemo1 app = new RangeMarkerDemo1(
                "OrsonCharts : RangeMarkerDemo1.java");
        app.pack();
        app.setVisible(true);
    }
}
