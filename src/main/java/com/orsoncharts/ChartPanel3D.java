/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts;

import com.orsoncharts.graphics3d.Drawable3D;
import com.orsoncharts.graphics3d.swing.Panel3D;

/**
 * A panel designed to display a Chart3D.  The panel will manage:
 * 
 * - the chart title;
 * - the chart viewing area;
 * - mouse interaction (drag to rotate, mouse-wheel to zoom in and out);
 * - viewing controls (zoom in/out/best-fit, buttons for rotations)
 * - export to PNG, SVG and PDF.
 * 
 * The panel registers with the chart to receive change notifications,
 * and when these are received the chart is automatically repainted.
 */
public class ChartPanel3D extends Panel3D implements Chart3DChangeListener {

    /** The chart being rendered. */
    private Chart3D chart;

    /**
     * Creates a new chart panel to display the specified chart.
     *
     * @param chart  the chart.
     */
    public ChartPanel3D(Chart3D chart) {
        super((Drawable3D) chart);
        this.chart = chart;
        this.chart.addChangeListener(this);
    }

    @Override
    public void chartChanged(Chart3DChangeEvent event) {
        repaint();
    }

}
