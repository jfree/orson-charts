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
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.swing.DisplayPanel3D;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.renderer.category.StackedBarRenderer3D;

/**
 * A demo of a 3D stacked bar chart.
 */
public class StackedBarChart3DDemo1 extends JFrame {

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
        Chart3D chart = Chart3DFactory.createStackedBarChart("Water Usage Chart", 
                dataset, null, null, "Value");
        CategoryPlot3D plot = (CategoryPlot3D) chart.getPlot();
        plot.setDimensions(new Dimension3D(14, 5, 2));
        StackedBarRenderer3D renderer 
                = (StackedBarRenderer3D) plot.getRenderer();
        renderer.setBarZWidth(0.3);
        this.chartPanel3D = new ChartPanel3D(chart);
        content.add(new DisplayPanel3D(this.chartPanel3D, true));
        return content;
    }
  
    private CategoryDataset3D createDataset() {
        
        DefaultCategoryDataset3D dataset = new DefaultCategoryDataset3D();

        dataset.addValue(197, "Agricultural", "R1", "Brazil");
        dataset.addValue(64, "Domestic", "R1", "Brazil");
        dataset.addValue(57, "Industrial", "R1", "Brazil");
        
        dataset.addValue(339, "Agricultural", "R1", "Indonesia");
        dataset.addValue(30, "Domestic", "R1", "Indonesia");
        dataset.addValue(4, "Industrial", "R1", "Indonesia");
        
        dataset.addValue(279, "Agricultural", "R1", "China");
        dataset.addValue(27, "Domestic", "R1", "China");
        dataset.addValue(107, "Industrial", "R1", "China");

        dataset.addValue(92, "Agricultural", "R1", "Germany");
        dataset.addValue(55, "Domestic", "R1", "Germany");
        dataset.addValue(313, "Industrial", "R1", "Germany");

        dataset.addValue(96, "Agricultural", "R1", "Russia");
        dataset.addValue(102, "Domestic", "R1", "Russia");
        dataset.addValue(337, "Industrial", "R1", "Russia");

        dataset.addValue(403, "Agricultural", "R1", "Turkey");
        dataset.addValue(82, "Domestic", "R1", "Turkey");
        dataset.addValue(60, "Industrial", "R1", "Turkey");
        
        dataset.addValue(536, "Agricultural", "R1", "Bangladesh");
        dataset.addValue(17, "Domestic", "R1", "Bangladesh");
        dataset.addValue(6, "Industrial", "R1", "Bangladesh");
        
        dataset.addValue(508, "Agricultural", "R1", "India");
        dataset.addValue(47, "Domestic", "R1", "India");
        dataset.addValue(30, "Industrial", "R1", "India");
        
        dataset.addValue(428, "Agricultural", "R1", "Japan");
        dataset.addValue(138, "Domestic", "R1", "Japan");
        dataset.addValue(124, "Industrial", "R1", "Japan");

        dataset.addValue(325, "Agricultural", "R1", "Italy");
        dataset.addValue(130, "Domestic", "R1", "Italy");
        dataset.addValue(268, "Industrial", "R1", "Italy");
        
        dataset.addValue(569, "Agricultural", "R1", "Mexico");
        dataset.addValue(126, "Domestic", "R1", "Mexico");
        dataset.addValue(37, "Industrial", "R1", "Mexico");

        dataset.addValue(576, "Agricultural", "R1", "Vietnam");
        dataset.addValue(68, "Domestic", "R1", "Vietnam");
        dataset.addValue(203, "Industrial", "R1", "Vietnam");
        
        dataset.addValue(794, "Agricultural", "R1", "Egypt");
        dataset.addValue(74, "Domestic", "R1", "Egypt");
        dataset.addValue(55, "Industrial", "R1", "Egypt");

        dataset.addValue(954, "Agricultural", "R1", "Iran");
        dataset.addValue(21, "Domestic", "R1", "Iran");
        dataset.addValue(73, "Industrial", "R1", "Iran");

        dataset.addValue(1029, "Agricultural", "R1", "Pakistan");
        dataset.addValue(21, "Domestic", "R1", "Pakistan");
        dataset.addValue(21, "Industrial", "R1", "Pakistan");

        dataset.addValue(1236, "Agricultural", "R1", "Thailand");
        dataset.addValue(26, "Domestic", "R1", "Thailand");
        dataset.addValue(26, "Industrial", "R1", "Thailand");

        dataset.addValue(165, "Agricultural", "R1", "Canada");
        dataset.addValue(274, "Domestic", "R1", "Canada");
        dataset.addValue(947, "Industrial", "R1", "Canada");
        
        dataset.addValue(1363, "Agricultural", "R1", "Iraq");
        dataset.addValue(44, "Domestic", "R1", "Iraq");
        dataset.addValue(74, "Industrial", "R1", "Iraq");
        
        dataset.addValue(656, "Agricultural", "R1", "US");
        dataset.addValue(208, "Domestic", "R1", "US");
        dataset.addValue(736, "Industrial", "R1", "US");
        
        dataset.addValue(2040, "Agricultural", "R1", "Uzbekistan");
        dataset.addValue(110, "Domestic", "R1", "Uzbekistan");
        dataset.addValue(44, "Industrial", "R1", "Uzbekistan");
        
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

