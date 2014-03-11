/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * Redistribution of this source file is prohibited.
 * 
 */

package com.orsoncharts;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;

import com.orsoncharts.data.ItemKey;
import com.orsoncharts.graphics3d.Object3D;
import com.orsoncharts.graphics3d.RenderingInfo;
import com.orsoncharts.graphics3d.swing.Panel3D;
import com.orsoncharts.graphics3d.swing.DisplayPanel3D;

/**
 * A panel designed to display a {@link Chart3D}.  The panel registers with the
 * chart to receive change notifications, and when these are received the chart
 * is automatically repainted.
 * <br><br>
 * This panel will display the chart in a Swing user interface, but does not
 * include additional features such as the view toolbar and popup menu (these
 * are provided by the {@link DisplayPanel3D} class).
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 */
@SuppressWarnings("serial")
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
        registerForTooltips();
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

    @Override
    public String getToolTipText(MouseEvent e) {
        RenderingInfo info = getRenderingInfo();
        if (info == null) {
            return null;
        }
        Object3D object = info.fetchObjectAt(e.getX(), e.getY());
        if (object != null) {
            ItemKey key = (ItemKey) object.getProperty(Object3D.ITEM_KEY);
            if (key != null) {
                return chart.getPlot().generateToolTipText(key);
            }
        }
        return null;
    }

}
