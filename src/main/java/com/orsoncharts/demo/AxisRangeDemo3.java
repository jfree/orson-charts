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
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import com.orsoncharts.ChartPanel3D;
import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.axis.ValueAxis3D;
import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.data.category.StandardCategoryDataset3D;
import com.orsoncharts.data.DefaultKeyedValues;
import com.orsoncharts.graphics3d.ViewPoint3D;
import com.orsoncharts.graphics3d.swing.DisplayPanel3D;
import com.orsoncharts.legend.StandardLegendBuilder;
import com.orsoncharts.plot.CategoryPlot3D;

/**
 * A test for changes to the value axis range on a bar chart.
 */
public class AxisRangeDemo3 extends JFrame {

    static class CustomDemoPanel extends DemoPanel implements ChangeListener {
        
        private JSlider slider1;
        
        private JSlider slider2;
        
        public CustomDemoPanel(LayoutManager layout) {
            super(layout);
            this.slider1 = new JSlider(-1000, 0);
            this.slider1.setValue(-500);
            this.slider2 = new JSlider(0, 1000);
            this.slider2.setValue(500);
            this.slider1.addChangeListener(this);
            this.slider2.addChangeListener(this);
            JPanel sliderPanel = new JPanel(new FlowLayout());
            sliderPanel.add(new JLabel("Lower bound: "));
            sliderPanel.add(this.slider1);
            sliderPanel.add(new JLabel("Upper bound: "));
            sliderPanel.add(this.slider2);
            add(sliderPanel, BorderLayout.SOUTH);
        }    

        @Override
        public void stateChanged(ChangeEvent e) {
            Chart3D chart = (Chart3D) getChartPanel().getDrawable();
            CategoryPlot3D plot = (CategoryPlot3D) chart.getPlot();
            ValueAxis3D yAxis = plot.getValueAxis();
            int min = this.slider1.getValue();
            int max = this.slider2.getValue();
            if (min != max) {
                yAxis.setRange(min, max);
            }
        }
    }
    
    /**
     * Creates a new test app.
     *
     * @param title  the frame title.
     */
    public AxisRangeDemo3(String title) {
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
        DemoPanel content = new AxisRangeDemo1.CustomDemoPanel(new BorderLayout());
        content.setPreferredSize(OrsonChartsDemo.DEFAULT_CONTENT_SIZE);
        CategoryDataset3D dataset = createDataset();
        Chart3D chart = Chart3DFactory.createLineChart("AxisRangeDemo3", 
                "A test for axis range changes on a line chart", dataset, "Row", 
                "Category", "Value");
        chart.setChartBoxColor(new Color(255, 255, 255, 128));
        chart.setViewPoint(ViewPoint3D.createAboveLeftViewPoint(40));
        StandardLegendBuilder slb 
                = (StandardLegendBuilder) chart.getLegendBuilder();
        slb.setFooter("Orson Charts (c) 2013, by Object Refinery Limited");
        
        chart.getLegendBuilder().setItemFont(new Font("Dialog", 
                Font.ITALIC, 12));
        CategoryPlot3D plot = (CategoryPlot3D) chart.getPlot();
        plot.getValueAxis().setRange(-500, 500);
        plot.getRowAxis().setVisible(false);
//        AreaRenderer3D renderer = (AreaRenderer3D) plot.getRenderer();
//        renderer.setBaseColor(Color.GRAY);
//        renderer.setColorSource(new StandardCategoryColorSource(
//                Colors.getColors1()));
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
        
        DefaultKeyedValues s0 = new DefaultKeyedValues();
        s0.put("A", -500);
        s0.put("B", -200);
        s0.put("C", -400);
        s0.put("D", -150);
        dataset.addSeriesAsRow("All Negative", s0);
        
        DefaultKeyedValues s1 = new DefaultKeyedValues();
        s1.put("A", -500);
        s1.put("B", 500);
        s1.put("C", 0);
        s1.put("D", -150);
        dataset.addSeriesAsRow("Alternating 1", s1);

        DefaultKeyedValues s2 = new DefaultKeyedValues();
        s2.put("A", 500);
        s2.put("B", -500);
        s2.put("C", 0);
        s2.put("D", 150);
        dataset.addSeriesAsRow("Alternating 2", s2);

        DefaultKeyedValues s3 = new DefaultKeyedValues();
        s3.put("A", 500);
        s3.put("B", 200);
        s3.put("C", 400);
        s3.put("D", 150);
        dataset.addSeriesAsRow("All Positive", s3);

        return dataset;
    }

    /**
     * Starting point for the app.
     *
     * @param args  command line arguments (ignored).
     */
    public static void main(String[] args) {
        AxisRangeDemo3 app = new AxisRangeDemo3(
                "OrsonCharts: AxisRangeDemo3.java");
        app.pack();
        app.setVisible(true);
    }

}
