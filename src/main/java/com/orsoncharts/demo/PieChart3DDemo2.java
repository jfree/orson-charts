/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.demo;

import java.awt.BorderLayout;
import java.awt.Dimension;
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

/**
 * A test app.
 */
public class PieChart3DDemo2 extends JFrame implements ActionListener {

    Chart3D chart;
    
    ChartPanel3D chartPanel3D;

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
        getContentPane().add(createContent());
    }

    
    final JPanel createContent() {
        JPanel content = new JPanel(new BorderLayout());
        content.setPreferredSize(new Dimension(720, 400));
        this.chart = Chart3DFactory.createPieChart("PieChart3DDemo2", 
                createDataset());
        this.chart.setTitle(TitleUtils.createTitle("PieChart3DDemo2", 
                "For more info see: http://www.object-refinery.com", 
                TitleAnchor.TOP_LEFT));
        this.chart.setTitleAnchor(TitleAnchor.TOP_LEFT);
        StandardLegendBuilder slb = new StandardLegendBuilder();
        slb.setFooter("Orson Charts (C)opyright 2013, by Object Refinery Limited");
        this.chart.setLegendBuilder(slb);
        this.chartPanel3D = new ChartPanel3D(this.chart);
        content.add(new DisplayPanel3D(this.chartPanel3D, true));
        JButton button = new JButton("Change the Data");
        button.addActionListener(this);
        content.add(button, BorderLayout.SOUTH);
        return content;
    }

    PieDataset3D createDataset() {
        StandardPieDataset3D dataset = new StandardPieDataset3D();
        dataset.add("United States", Math.random() * 30);
        dataset.add("France", Math.random() * 20);
        dataset.add("New Zealand", Math.random() * 12);
        dataset.add("United Kingdom", Math.random() * 43);
        return dataset; 
    }
  
    @Override
    public void actionPerformed(ActionEvent e) {
        PieDataset3D dataset = createDataset();
        PiePlot3D plot = (PiePlot3D) this.chart.getPlot();
        plot.setDataset(dataset);
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