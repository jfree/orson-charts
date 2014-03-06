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
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import com.orsoncharts.ChartPanel3D;
import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.axis.StandardCategoryAxis3D;
import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.data.category.StandardCategoryDataset3D;
import com.orsoncharts.data.DefaultKeyedValues;
import com.orsoncharts.graphics3d.swing.DisplayPanel3D;
import com.orsoncharts.legend.LegendAnchor;
import com.orsoncharts.marker.CategoryMarker;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.renderer.category.CategoryRenderer3D;
import com.orsoncharts.style.ChartStyler;

/**
 * A demo showing category markers on a 3D bar chart.
 */
public class CategoryMarkerDemo1 extends JFrame {
    
    static class CustomDemoPanel extends DemoPanel implements ActionListener {
        
        public CustomDemoPanel(LayoutManager layout) {
            super(layout);
            
            JPanel controlPanel = new JPanel(new GridLayout(2, 1));
            JPanel panel1 = new JPanel(new FlowLayout());
            ButtonGroup cGroup = new ButtonGroup();
            
            JRadioButton appleRB = new JRadioButton("Apple");
            appleRB.setActionCommand("APPLE");
            appleRB.addActionListener(this);
            cGroup.add(appleRB);
            panel1.add(appleRB);

            JRadioButton googleRB = new JRadioButton("Google");
            googleRB.setActionCommand("GOOGLE");
            googleRB.addActionListener(this);
            cGroup.add(googleRB);
            panel1.add(googleRB);
            
            JRadioButton microsoftRB = new JRadioButton("Microsoft");
            microsoftRB.setActionCommand("MICROSOFT");
            microsoftRB.addActionListener(this);
            cGroup.add(microsoftRB);
            panel1.add(microsoftRB);
            
            JRadioButton oracleRB = new JRadioButton("Oracle");
            oracleRB.setActionCommand("ORACLE");
            oracleRB.addActionListener(this);
            cGroup.add(oracleRB);
            panel1.add(oracleRB);

            JPanel panel2 = new JPanel(new FlowLayout());
            
            ButtonGroup qGroup = new ButtonGroup();
            JRadioButton q211 = new JRadioButton("Q2/11");
            q211.setActionCommand("Q211");
            q211.addActionListener(this);
            qGroup.add(q211);
            panel2.add(q211);

            JRadioButton q311 = new JRadioButton("Q3/11");
            q311.setActionCommand("Q311");
            q311.addActionListener(this);
            qGroup.add(q311);
            panel2.add(q311);

            JRadioButton q411 = new JRadioButton("Q4/11");
            q411.setActionCommand("Q411");
            q411.addActionListener(this);
            qGroup.add(q411);
            panel2.add(q411);

            JRadioButton q112 = new JRadioButton("Q1/12");
            q112.setActionCommand("Q112");
            q112.addActionListener(this);
            qGroup.add(q112);
            panel2.add(q112);

            JRadioButton q212 = new JRadioButton("Q2/12");
            q212.setActionCommand("Q212");
            q212.addActionListener(this);
            qGroup.add(q212);
            panel2.add(q212);

            JRadioButton q312 = new JRadioButton("Q3/12");
            q312.setActionCommand("Q312");
            q312.addActionListener(this);
            qGroup.add(q312);
            panel2.add(q312);

            JRadioButton q412 = new JRadioButton("Q4/12");
            q412.setActionCommand("Q412");
            q412.addActionListener(this);
            qGroup.add(q412);
            panel2.add(q412);

            JRadioButton q113 = new JRadioButton("Q1/13");
            q113.setActionCommand("Q113");
            q113.addActionListener(this);
            qGroup.add(q113);
            panel2.add(q113);

            JRadioButton q213 = new JRadioButton("Q2/13");
            q213.setActionCommand("Q213");
            q213.addActionListener(this);
            qGroup.add(q213);
            panel2.add(q213);

            JRadioButton q313 = new JRadioButton("Q3/13");
            q313.setActionCommand("Q313");
            q313.addActionListener(this);
            qGroup.add(q313);
            panel2.add(q313);

            JRadioButton q413 = new JRadioButton("Q4/13");
            q413.setActionCommand("Q413");
            q413.addActionListener(this);
            qGroup.add(q413);
            panel2.add(q413);

            controlPanel.add(panel1);
            controlPanel.add(panel2);
            add(controlPanel, BorderLayout.SOUTH);
        }    

        @Override
        public void actionPerformed(ActionEvent e) {
            Chart3D chart = (Chart3D) getChartPanel().getDrawable();
            chart.setNotify(false);
            CategoryPlot3D plot = (CategoryPlot3D) chart.getPlot();
            CategoryRenderer3D renderer = plot.getRenderer();
            HighlightCategoryColorSource colorSource 
                    = (HighlightCategoryColorSource) renderer.getColorSource();
            StandardCategoryAxis3D rowAxis 
                    = (StandardCategoryAxis3D) plot.getRowAxis();
            CategoryMarker rowMarker = rowAxis.getMarker("RM1");
            if (rowMarker == null) {
                rowMarker = new CategoryMarker("");
                rowMarker.receive(new ChartStyler(chart.getStyle()));
            }
            if (e.getActionCommand().equals("APPLE")) {
                rowMarker.setCategory("Apple");
                colorSource.setHighlightRowIndex(
                        plot.getDataset().getRowIndex("Apple"));
            } else if (e.getActionCommand().equals("GOOGLE")) {
                rowMarker.setCategory("Google");
                colorSource.setHighlightRowIndex(
                        plot.getDataset().getRowIndex("Google"));
            } else if (e.getActionCommand().equals("ORACLE")) {
                rowMarker.setCategory("Oracle");
                colorSource.setHighlightRowIndex(
                        plot.getDataset().getRowIndex("Oracle"));
            } else if (e.getActionCommand().equals("MICROSOFT")) {
                rowMarker.setCategory("Microsoft");
                colorSource.setHighlightRowIndex(
                        plot.getDataset().getRowIndex("Microsoft"));
            }
            if (!rowMarker.getCategory().equals("")) {
                rowAxis.setMarker("RM1", rowMarker);
            }
            
            StandardCategoryAxis3D columnAxis 
                    = (StandardCategoryAxis3D) plot.getColumnAxis();
            CategoryMarker columnMarker = columnAxis.getMarker("CM1");
            if (columnMarker == null) {
                columnMarker = new CategoryMarker("");
                columnMarker.receive(new ChartStyler(chart.getStyle()));
            }
            if (e.getActionCommand().equals("Q211")) {
                columnMarker.setCategory("Q2/11");
                colorSource.setHighlightColumnIndex(
                        plot.getDataset().getColumnIndex("Q2/11"));
            } else if (e.getActionCommand().equals("Q311")) {
                columnMarker.setCategory("Q3/11");
                colorSource.setHighlightColumnIndex(
                        plot.getDataset().getColumnIndex("Q3/11"));
            } else if (e.getActionCommand().equals("Q411")) {
                columnMarker.setCategory("Q4/11");
                colorSource.setHighlightColumnIndex(
                        plot.getDataset().getColumnIndex("Q4/11"));
            } else if (e.getActionCommand().equals("Q112")) {
                columnMarker.setCategory("Q1/12");
                colorSource.setHighlightColumnIndex(
                        plot.getDataset().getColumnIndex("Q1/12"));
            } else if (e.getActionCommand().equals("Q212")) {
                columnMarker.setCategory("Q2/12");
                colorSource.setHighlightColumnIndex(
                        plot.getDataset().getColumnIndex("Q2/12"));
            } else if (e.getActionCommand().equals("Q312")) {
                columnMarker.setCategory("Q3/12");
                colorSource.setHighlightColumnIndex(
                        plot.getDataset().getColumnIndex("Q3/12"));
            } else if (e.getActionCommand().equals("Q412")) {
                columnMarker.setCategory("Q4/12");
                colorSource.setHighlightColumnIndex(
                        plot.getDataset().getColumnIndex("Q4/12"));
            } else if (e.getActionCommand().equals("Q113")) {
                columnMarker.setCategory("Q1/13");
                colorSource.setHighlightColumnIndex(
                        plot.getDataset().getColumnIndex("Q1/13"));
            } else if (e.getActionCommand().equals("Q213")) {
                columnMarker.setCategory("Q2/13");
                colorSource.setHighlightColumnIndex(
                        plot.getDataset().getColumnIndex("Q2/13"));
            } else if (e.getActionCommand().equals("Q313")) {
                columnMarker.setCategory("Q3/13");
                colorSource.setHighlightColumnIndex(
                        plot.getDataset().getColumnIndex("Q3/13"));
            } else if (e.getActionCommand().equals("Q413")) {
                columnMarker.setCategory("Q4/13");
                colorSource.setHighlightColumnIndex(
                        plot.getDataset().getColumnIndex("Q4/13"));
            } else {
                // should not get here                
            }
            if (!columnMarker.getCategory().equals("")) {
                columnAxis.setMarker("CM1", columnMarker);
            }
            chart.setNotify(true);
        }
    }

    /**
     * Creates a new test app.
     *
     * @param title  the frame title.
     */
    public CategoryMarkerDemo1(String title) {
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
        DemoPanel content = new CustomDemoPanel(new BorderLayout());
        content.setPreferredSize(OrsonChartsDemo.DEFAULT_CONTENT_SIZE);
        CategoryDataset3D dataset = createDataset();
        Chart3D chart = Chart3DFactory.createBarChart("Quarterly Revenues", 
                "For some large IT companies", dataset, null, "Quarter", 
                "$billion Revenues");
        chart.setChartBoxColor(new Color(255, 255, 255, 127));
        chart.setLegendAnchor(LegendAnchor.BOTTOM_RIGHT);
        CategoryPlot3D plot = (CategoryPlot3D) chart.getPlot();
        plot.setGridlinePaintForValues(Color.BLACK);
        CategoryRenderer3D renderer = plot.getRenderer();
        renderer.setColorSource(new HighlightCategoryColorSource());
        ChartPanel3D chartPanel = new ChartPanel3D(chart);
        chartPanel.setMargin(0.30);
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
    private static CategoryDataset3D createDataset() {    
        StandardCategoryDataset3D dataset = new StandardCategoryDataset3D();
                
        //DefaultKeyedValues s0 = new DefaultKeyedValues();
        //s0.put("Q1/11", 0.264746);
        //s0.put("Q2/11", 0.281320);
        //s0.put("Q3/11", 0.290026);
        //s0.put("Q4/11", 0.297011);
        //s0.put("Q1/12", 0.314731);
        //s0.put("Q2/12", 0.322595);
        //s0.put("Q3/12", 0.343606);
        //s0.put("Q4/12", 0.347884);
        //s0.put("Q1/13", 0.363259);
        //s0.put("Q2/13", 0.374423);
        //dataset.addSeriesAsRow("Redhat", s0);

        DefaultKeyedValues s1 = new DefaultKeyedValues();
        s1.put("Q2/11", 8.181);
        s1.put("Q3/11", 8.792);
        s1.put("Q4/11", 9.039);
        s1.put("Q1/12", 10.916);
        s1.put("Q2/12", 8.181);
        s1.put("Q3/12", 9.094);
        s1.put("Q4/12", 8.958);
        s1.put("Q1/13", 10.947);
        s1.put("Q2/13", 8.372);
        s1.put("Q3/13", 9.275);
        dataset.addSeriesAsRow("Oracle", s1);

        DefaultKeyedValues s2 = new DefaultKeyedValues();
        s2.put("Q2/11", 9.03);
        s2.put("Q3/11", 9.72);
        s2.put("Q4/11", 10.58);
        s2.put("Q1/12", 10.65);
        s2.put("Q2/12", 12.214);
        s2.put("Q3/12", 14.101);
        s2.put("Q4/12", 14.419);
        s2.put("Q1/13", 13.969);
        s2.put("Q2/13", 14.105);
        s2.put("Q3/13", 14.893);
        s2.put("Q4/13", 16.858);
        dataset.addSeriesAsRow("Google", s2);
        
        DefaultKeyedValues s3 = new DefaultKeyedValues();
        s3.put("Q2/11", 17.37);
        s3.put("Q3/11", 17.37);
        s3.put("Q4/11", 20.89);
        s3.put("Q1/12", 17.41);
        s3.put("Q2/12", 18.06);
        s3.put("Q3/12", 16.008);
        s3.put("Q4/12", 21.456);
        s3.put("Q1/13", 20.489);
        s3.put("Q2/13", 19.896);
        s3.put("Q3/13", 18.529);
        s3.put("Q4/13", 24.519);
        dataset.addSeriesAsRow("Microsoft", s3);
        
        DefaultKeyedValues s4 = new DefaultKeyedValues();
        s4.put("Q2/11", 28.57);
        s4.put("Q3/11", 28.27);
        s4.put("Q4/11", 46.33);
        s4.put("Q1/12", 39.20);
        s4.put("Q2/12", 35.00);
        s4.put("Q3/12", 36.00);
        s4.put("Q4/12", 54.5);
        s4.put("Q1/13", 43.6);
        s4.put("Q2/13", 35.323);
        s4.put("Q3/13", 37.5);
        s4.put("Q4/13", 57.594);
        dataset.addSeriesAsRow("Apple", s4);
        
        return dataset;
    }

    /**
     * Starting point for the app.
     *
     * @param args  command line arguments (ignored).
     */
    public static void main(String[] args) {
        CategoryMarkerDemo1 app = new CategoryMarkerDemo1(
                "OrsonCharts: CategoryMarkerDemo1.java");
        app.pack();
        app.setVisible(true);
    }
}
