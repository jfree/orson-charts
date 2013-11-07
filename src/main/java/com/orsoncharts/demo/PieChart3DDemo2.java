/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.demo;

import com.orsoncharts.Colors;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.orsoncharts.ChartPanel3D;
import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.TitleAnchor;
import com.orsoncharts.TitleUtils;
import com.orsoncharts.data.StandardPieDataset3D;
import com.orsoncharts.data.PieDataset3D;
import com.orsoncharts.plot.PiePlot3D;
import com.orsoncharts.graphics3d.swing.DisplayPanel3D;
import com.orsoncharts.legend.StandardLegendBuilder;
import com.orsoncharts.plot.StandardColorSource;

/**
 * A test app.
 */
public class PieChart3DDemo2 extends JFrame {
    
    /**
     * Creates a sample dataset (hard-coded for the purpose of keeping the
     * demo self-contained - in practice you would normally read your data
     * from a file, database or other source).
     * 
     * @return A sample dataset.
     */
    static PieDataset3D createDataset() {
        StandardPieDataset3D dataset = new StandardPieDataset3D();
        dataset.add("United States", Math.random() * 30);
        dataset.add("France", Math.random() * 20);
        dataset.add("New Zealand", Math.random() * 12);
        dataset.add("United Kingdom", Math.random() * 43);
        dataset.add("Australia", Math.random() * 43);
        dataset.add("Canada", Math.random() * 43);
        return dataset; 
    }
  
    /**
     * Returns a panel containing the content for the demo.  This method is
     * used across all the individual demo applications to allow aggregation 
     * into a single "umbrella" demo (OrsonChartsDemo).
     * 
     * @return A panel containing the content for the demo.
     */
    public static JPanel createDemoPanel() {
        JPanel content = new JPanel(new BorderLayout());
        content.setPreferredSize(OrsonChartsDemo.DEFAULT_CONTENT_SIZE);
        final Chart3D chart = Chart3DFactory.createPieChart("PieChart3DDemo2", 
                "www.object-refinery.com", createDataset());
        chart.setTitle(TitleUtils.createTitle("PieChart3DDemo2", 
                "For more info see: http://www.object-refinery.com", 
                TitleAnchor.TOP_LEFT));
        chart.setTitleAnchor(TitleAnchor.TOP_LEFT);
        StandardLegendBuilder slb = new StandardLegendBuilder();
        
        slb.setFooter("Orson Charts (C)opyright 2013, by Object Refinery Limited");
        chart.setLegendBuilder(slb);
        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setSectionColorSource(new StandardColorSource(
                new Color[] {
                    new Color(228, 233, 239),
                    new Color(184, 197, 219),
                    new Color(111, 122, 143),
                    new Color(95, 89, 89),
                    new Color(206, 167, 145),
                    new Color(188, 182, 173)
                        
                }
                ));
        plot.setSectionColorSource(new StandardColorSource(Colors.getDesignSeedsShells()));
        // 255, 219, 142
        // 220, 21, 20
        // 149, 0, 1
        // 82, 102, 41
        // 142, 101, 72
        // 199, 169, 128
        
        
        
        ChartPanel3D chartPanel = new ChartPanel3D(chart);
        chartPanel.setMargin(0.15);
        chartPanel.zoomToFit(OrsonChartsDemo.DEFAULT_CONTENT_SIZE);
        content.add(new DisplayPanel3D(chartPanel));
        JButton button = new JButton("Change the Data");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PieDataset3D dataset = createDataset();
                PiePlot3D plot = (PiePlot3D) chart.getPlot();
                plot.setDataset(dataset);
            }
        });
        content.add(button, BorderLayout.SOUTH);
        return content;
    }
    
    /**
     * Creates a new test app.
     *
     * @param title  the frame title.
     */
    public PieChart3DDemo2(String title) {
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
     * Starting point for the app.
     *
     * @param args  command line arguments (ignored).
     */
    public static void main(String[] args) {
        PieChart3DDemo2 app = new PieChart3DDemo2(
                "OrsonCharts: PieChart3DDemo2.java");
        app.pack();
        app.setVisible(true);
    }

}