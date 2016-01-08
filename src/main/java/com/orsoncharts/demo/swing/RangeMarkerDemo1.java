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

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.orsoncharts.Chart3DPanel;
import com.orsoncharts.Chart3D;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.data.xyz.XYZItemKey;
import com.orsoncharts.demo.HighlightXYZColorSource;
import com.orsoncharts.demo.RangeMarker1;
import com.orsoncharts.graphics3d.Object3D;
import com.orsoncharts.graphics3d.RenderedElement;
import com.orsoncharts.graphics3d.swing.DisplayPanel3D;
import com.orsoncharts.interaction.Chart3DMouseEvent;
import com.orsoncharts.interaction.Chart3DMouseListener;
import com.orsoncharts.interaction.StandardXYZDataItemSelection;
import com.orsoncharts.label.StandardXYZItemLabelGenerator;
import com.orsoncharts.marker.RangeMarker;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.renderer.xyz.ScatterXYZRenderer;
import com.orsoncharts.renderer.xyz.StandardXYZColorSource;

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
        XYZDataset dataset = RangeMarker1.createDataset();
        Chart3D chart = RangeMarker1.createChart(dataset);
        Chart3DPanel chartPanel = new Chart3DPanel(chart);
        content.setChartPanel(chartPanel);
        chartPanel.addChartMouseListener(content);
        chartPanel.zoomToFit(OrsonChartsDemo.DEFAULT_CONTENT_SIZE);
        content.add(new DisplayPanel3D(chartPanel));
        return content;
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
