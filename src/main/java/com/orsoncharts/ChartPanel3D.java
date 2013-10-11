/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */
package com.orsoncharts;

import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.swing.Panel3D;
import java.awt.Dimension;

/**
 * A panel designed to display a {@link Chart3D}.  The panel registers with the
 * chart to receive change notifications, and when these are received the chart
 * is automatically repainted.
 */
public class ChartPanel3D extends Panel3D implements Chart3DChangeListener {

    /**
     * The chart being rendered.
     */
    private Chart3D chart;

    /**
     * Creates a new chart panel to display the specified chart.
     *
     * @param chart the chart.
     */
    public ChartPanel3D(Chart3D chart) {
        super(chart);
        this.chart = chart;
        this.chart.addChangeListener(this);
    }

    /**
     * Receives notification when the chart has been modified, and responds
     * by completely repainting the panel and chart.
     * 
     * @param event  the event. 
     */
    @Override
    public void chartChanged(Chart3DChangeEvent event) {
        repaint();
    }

    public void zoomToFit(double margin) {
        Dimension d2d = getSize();
        Dimension3D d3d = this.chart.getPlot().getDimensions();
        // ask the viewpoint to suggest the correct viewing distance
        
    }
}
