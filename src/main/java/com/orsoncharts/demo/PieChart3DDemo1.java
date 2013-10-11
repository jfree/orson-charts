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
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import com.orsoncharts.ChartPanel3D;
import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.TitleAnchor;
import com.orsoncharts.data.StandardPieDataset3D;
import com.orsoncharts.data.PieDataset3D;
import com.orsoncharts.plot.PiePlot3D;
import com.orsoncharts.graphics3d.swing.DisplayPanel3D;
import com.orsoncharts.legend.LegendAnchor;
import com.orsoncharts.plot.Pie3DPaintSource;
import com.orsoncharts.plot.StandardPie3DPaintSource;

/**
 * A test app.
 */
public class PieChart3DDemo1 extends JFrame {

    /**
     * Creates a new test app.
     *
     * @param title  the frame title.
     */
    public PieChart3DDemo1(String title) {
        super(title);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        getContentPane().add(createDemoPanel());
    }

    public static final JPanel createDemoPanel() {
        JPanel content = new JPanel(new BorderLayout());
        content.setPreferredSize(new Dimension(720, 400));
        Chart3D chart = Chart3DFactory.createPieChart("PieChart3DDemo1", 
                createDataset());
        chart.setTitleAnchor(TitleAnchor.TOP_LEFT);
        chart.setLegendAnchor(LegendAnchor.BOTTOM_RIGHT);
        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        Pie3DPaintSource paintSource = new StandardPie3DPaintSource(
                new Color[] {new Color(0x1A9641), new Color(0xA6D96A), 
                    new Color(0xFDAE61), new Color(0xFFFFBF)});
      
        content.add(new DisplayPanel3D(new ChartPanel3D(chart), true));
        return content;
    }

    static PieDataset3D createDataset() {
        StandardPieDataset3D dataset = new StandardPieDataset3D();
        dataset.add("United States", new Double(30.0));
        dataset.add("France", new Double(20.0));
        dataset.add("New Zealand", new Double(12.0));
        dataset.add("United Kingdom", new Double(43.3));
        return dataset; 
    }
    
    /**
     * Starting point for the app.
     *
     * @param args  command line arguments (ignored).
     */
    public static void main(String[] args) {
        PieChart3DDemo1 app = new PieChart3DDemo1(
                "OrsonCharts: PieChart3DDemo1.java");
        app.pack();
        app.setVisible(true);
    }

}