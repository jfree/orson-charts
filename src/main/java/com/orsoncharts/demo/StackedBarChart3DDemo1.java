/**
 * (C)opyright 2013, by Object Refinery Limited
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
import com.orsoncharts.axis.CategoryAxis3D;
import com.orsoncharts.axis.NumberAxis3D;
import com.orsoncharts.axis.Range;
import com.orsoncharts.data.CategoryDataset3D;
import com.orsoncharts.data.DefaultCategoryDataset3D;
import com.orsoncharts.graphics3d.swing.DisplayPanel3D;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.renderer.category.CategoryRenderer3D;
import com.orsoncharts.renderer.category.StackedBarRenderer3D;

/**
 * A demo of a 3D stacked bar chart.
 */
public class StackedBarChart3DDemo1 extends JFrame {
    
    Chart3D chart;

    ChartPanel3D chartPanel3D;

    /**
     * Creates a new test app.
     *
     * @param title  the frame title.
     */
    public StackedBarChart3DDemo1(String title) {
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
        CategoryAxis3D columnAxis = new CategoryAxis3D("Quarter");
//        columnAxis.setRange(0.0, 5.0);
//        columnAxis.setCategoryLabel("Q1", 1.0);
//        columnAxis.setCategoryLabel("Q2", 2.0);
//        columnAxis.setCategoryLabel("Q3", 3.0);
//        columnAxis.setCategoryLabel("Q4", 4.0);
        NumberAxis3D yAxis = new NumberAxis3D("Value", new Range(0.0, 5.0));
        CategoryAxis3D rowAxis = new CategoryAxis3D("Company");
        rowAxis.setRange(0, 3);
        rowAxis.setCategoryLabel("Google", 1.0);
        rowAxis.setCategoryLabel("Yahoo", 2.0);
        CategoryPlot3D plot = new CategoryPlot3D(dataset, rowAxis, columnAxis, yAxis);
        CategoryRenderer3D renderer = new StackedBarRenderer3D();
        plot.setRenderer(renderer);
        renderer.setPlot(plot);
    
        this.chartPanel3D = new ChartPanel3D(new Chart3D(plot));
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
        
        dataset.setValue(1.0, "S1", "R1", "C1");
        dataset.setValue(2.0, "S2", "R1", "C1");
        dataset.setValue(3.0, "S1", "R2", "C1");
        dataset.setValue(4.0, "S2", "R2", "C1");
//        DefaultKeyedValues s1 = new DefaultKeyedValues();
//        s1.addValue("Q1", 1.0);
//        s1.addValue("Q2", 2.0);
//        s1.addValue("Q3", 3.0);
//        s1.addValue("Q4", 4.0);
//        dataset.addSeries("S1", s1);
//        
//        DefaultKeyedValues s2 = new DefaultKeyedValues();
//        s2.addValue("Q1", 4.0);
//        s2.addValue("Q2", 3.0);
//        s2.addValue("Q3", 2.0);
//        s2.addValue("Q4", 1.0);
//        dataset.addSeries("S2", s2);
        
        return dataset;
    }

    /**
     * Starting point for the app.
     *
     * @param args  command line arguments (ignored).
     */
    public static void main(String[] args) {
        StackedBarChart3DDemo1 app = new StackedBarChart3DDemo1(
                "OrsonCharts: StackedBarChart3DDemo1.java");
        app.pack();
        app.setVisible(true);
    }
}

