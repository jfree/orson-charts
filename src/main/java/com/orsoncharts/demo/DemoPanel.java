/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.demo;

import java.awt.LayoutManager;
import javax.swing.JPanel;
import com.orsoncharts.ChartPanel3D;

/**
 *
 * @author dgilbert
 */
public class DemoPanel extends JPanel {
    
    private ChartPanel3D chartPanel;
    
    public DemoPanel(LayoutManager layout) {
        super(layout);
    }
    
    public ChartPanel3D getChartPanel() {
        return this.chartPanel;
    }
    
    public void setChartPanel(ChartPanel3D panel) {
        this.chartPanel = panel;
    }
}
