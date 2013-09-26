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
import com.orsoncharts.axis.DefaultCategoryAxis3D;
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
        Chart3D chart = Chart3DFactory.createStackedBarChart(dataset, "Company",
                "Quarter", "Value");
//        DefaultCategoryAxis3D columnAxis = new DefaultCategoryAxis3D("Quarter");
//        columnAxis.setCategories(dataset.getColumnKeys());
//        NumberAxis3D yAxis = new NumberAxis3D("Value", new Range(0.0, 5.0));
//        DefaultCategoryAxis3D rowAxis = new DefaultCategoryAxis3D("Company");
//        rowAxis.setCategories(dataset.getRowKeys());
//        CategoryPlot3D plot = new CategoryPlot3D(dataset, rowAxis, columnAxis, yAxis);
//        CategoryRenderer3D renderer = new StackedBarRenderer3D();
//        plot.setRenderer(renderer);
//        renderer.setPlot(plot);
    
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
        dataset.setValue(1.0, "S1", "R1", "C1");
        dataset.setValue(2.0, "S2", "R1", "C1");
        dataset.setValue(3.0, "S1", "R1", "C2");
        dataset.setValue(4.0, "S2", "R1", "C2");
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

