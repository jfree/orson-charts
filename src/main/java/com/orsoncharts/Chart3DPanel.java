/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-2016, by Object Refinery Limited.  All rights reserved.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 * 
 * If you do not wish to be bound by the terms of the GPL, an alternative
 * commercial license can be purchased.  For details, please see visit the
 * Orson Charts home page:
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 */

package com.orsoncharts;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.EventListener;
import javax.swing.event.EventListenerList;

import com.orsoncharts.data.ItemKey;
import com.orsoncharts.graphics3d.Object3D;
import com.orsoncharts.graphics3d.RenderingInfo;
import com.orsoncharts.graphics3d.swing.Panel3D;
import com.orsoncharts.graphics3d.swing.DisplayPanel3D;
import com.orsoncharts.interaction.Chart3DMouseEvent;
import com.orsoncharts.interaction.Chart3DMouseListener;
import com.orsoncharts.graphics3d.RenderedElement;
import com.orsoncharts.util.ArgChecks;

/**
 * A panel designed to display a {@link Chart3D} in a Swing-based desktop
 * application.  The panel registers with the chart to receive change 
 * notifications, and when these are received the chart is automatically 
 * repainted.
 * <br><br>
 * This panel will display the chart, but does not include additional features
 * such as the view toolbar and popup menu (these are provided by the 
 * {@link DisplayPanel3D} class).
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 */
@SuppressWarnings("serial")
public class Chart3DPanel extends Panel3D implements Chart3DChangeListener, 
        ComponentListener {

    /**
     * The chart being rendered.
     */
    private Chart3D chart;
    
    /** Auto-fit the chart on resize? */
    private boolean autoFitOnPanelResize;
    
    /** Storage for registered (chart) mouse listeners. */
    private transient EventListenerList chartMouseListeners;

    /**
     * Creates a new chart panel to display the specified chart.
     *
     * @param chart the chart.
     */
    public Chart3DPanel(Chart3D chart) {
        super(chart);
        this.chartMouseListeners = new EventListenerList();
        this.chart = chart;
        this.chart.addChangeListener(this);
        addComponentListener(this);
        this.autoFitOnPanelResize = false;
        registerForTooltips();
    }

    /**
     * Returns the chart being displayed in this panel.
     * 
     * @return The chart (never {@code null}).
     * 
     * @since 1.3
     */
    public Chart3D getChart() {
        return this.chart;
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

    /**
     * Receives a mouse event and passes it on to registered 
     * {@link Chart3DMouseListener}s along with the underlying rendered
     * element if any.
     * 
     * @param e  the mouse event.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        Object[] listeners = this.chartMouseListeners.getListeners(
                Chart3DMouseListener.class);
        if (listeners.length == 0) {
            return;
        }
        RenderedElement element = null;
        RenderingInfo info = getRenderingInfo();
        if (info != null) {
            element = info.findElementAt(e.getX(), e.getY());
        }
        Chart3DMouseEvent chartEvent = new Chart3DMouseEvent(this.chart, e,
                element);
        for (int i = listeners.length - 1; i >= 0; i -= 1) {
            ((Chart3DMouseListener) listeners[i]).chartMouseClicked(chartEvent);
        }
        super.mouseClicked(e);
    }

    /**
     * Receives a mouse event and passes it on to registered 
     * {@link Chart3DMouseListener}s along with the underlying rendered
     * element if any.
     * 
     * @param e  the mouse event.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        Object[] listeners = this.chartMouseListeners.getListeners(
                Chart3DMouseListener.class);
        if (listeners.length == 0) {
            return;
        }
        RenderedElement element = null;
        RenderingInfo info = getRenderingInfo();
        if (info != null) {
            element = info.findElementAt(e.getX(), e.getY());
        }
        Chart3DMouseEvent chartEvent = new Chart3DMouseEvent(this.chart, e,
                element);
        for (int i = listeners.length - 1; i >= 0; i -= 1) {
            ((Chart3DMouseListener) listeners[i]).chartMouseMoved(chartEvent);
        }
        super.mouseMoved(e);
    }

    /**
     * Adds a listener to the list of objects listening for chart mouse events.
     *
     * @param listener  the listener ({@code null} not permitted).
     * 
     * @since 1.3
     */
    public void addChartMouseListener(Chart3DMouseListener listener) {
        ArgChecks.nullNotPermitted(listener, "listener");
        this.chartMouseListeners.add(Chart3DMouseListener.class, listener);
    }

    /**
     * Removes a listener from the list of objects listening for chart mouse
     * events.
     *
     * @param listener  the listener ({@code null} not permitted).
     * 
     * @since 1.3
     */
    public void removeChartMouseListener(Chart3DMouseListener listener) {
        ArgChecks.nullNotPermitted(listener, "listener");
        this.chartMouseListeners.remove(Chart3DMouseListener.class, listener);
    }

    /**
     * Returns an array of the listeners of the given type registered with the
     * panel.
     *
     * @param listenerType  the listener type.
     *
     * @return An array of listeners.
     */
    @Override
    public <T extends EventListener> T[] getListeners(Class<T> listenerType) {
        if (listenerType == Chart3DMouseListener.class) {
            // fetch listeners from local storage
            return this.chartMouseListeners.getListeners(listenerType);
        }
        else {
            return super.getListeners(listenerType);
        }
    }
    
    /**
     * Provides serialization support.
     *
     * @param stream  the input stream.
     *
     * @throws IOException  if there is an I/O error.
     * @throws ClassNotFoundException  if there is a classpath problem.
     */
    private void readObject(ObjectInputStream stream)
        throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        // we create a new but empty chartMouseListeners list
        this.chartMouseListeners = new EventListenerList();
        // register as a listener with sub-components...
        if (this.chart != null) {
            this.chart.addChangeListener(this);
        }
    }

}
