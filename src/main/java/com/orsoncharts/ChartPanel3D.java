/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */
package com.orsoncharts;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import com.orsoncharts.graphics3d.swing.Panel3D;

/**
 * A panel designed to display a {@link Chart3D}.  The panel registers with the
 * chart to receive change notifications, and when these are received the chart
 * is automatically repainted.
 */
public class ChartPanel3D extends Panel3D implements Chart3DChangeListener, 
        ComponentListener {

    /**
     * The chart being rendered.
     */
    private Chart3D chart;
    
    /** Auto-fit the chart on resize? */
    private boolean autoFitOnPanelResize;
    
    /**
     * Creates a new chart panel to display the specified chart.
     *
     * @param chart the chart.
     */
    public ChartPanel3D(Chart3D chart) {
        super(chart);
        this.chart = chart;
        this.chart.addChangeListener(this);
        this.addComponentListener(this);
        this.autoFitOnPanelResize = false;
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

    @Override
    public void componentResized(ComponentEvent e) {
        if (this.autoFitOnPanelResize) {
            zoomToFit();
        }
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        // do nothing
    }

    @Override
    public void componentShown(ComponentEvent e) {
        // do nothing
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        // do nothing
    }

}
