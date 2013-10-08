/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */
package com.orsoncharts;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Point;
import java.awt.event.MouseEvent;
import com.orsoncharts.graphics3d.Drawable3D;
import com.orsoncharts.graphics3d.swing.Panel3D;
import com.orsoncharts.util.Offset2D;

/**
 * A panel designed to display a Chart3D. The panel will manage:
 *
 * - the chart viewing area; - mouse interaction (drag to rotate, mouse-wheel to
 * zoom in and out); - viewing controls (zoom in/out/best-fit, buttons for
 * rotations) - export to PNG, SVG and PDF.
 *
 * The panel registers with the chart to receive change notifications, and when
 * these are received the chart is automatically repainted.
 */
public class ChartPanel3D extends Panel3D implements Chart3DChangeListener,
        KeyListener {

    /**
     * The chart being rendered.
     */
    private Chart3D chart;
    /**
     * Temporary state to track the 2D offset during an ALT-mouse-drag
     * operation.
     */
    private Offset2D offsetAtMousePressed;

    /**
     * Creates a new chart panel to display the specified chart.
     *
     * @param chart the chart.
     */
    public ChartPanel3D(Chart3D chart) {
        super((Drawable3D) chart);
        this.chart = chart;
        this.chart.addChangeListener(this);
        addKeyListener(this);
    }

    /**
     * Return <code>true</code> to allow this panel to receive the focus and,
     * therefore, key events.
     * 
     * @return <code>true</code>. 
     */
    @Override
    public boolean isFocusable() {
        return true;
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
    public void keyTyped(KeyEvent e) {
        // we don't care
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.isAltDown()) {
            Offset2D offset = this.chart.getWorldOffset();
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                this.chart.setWorldOffset(new Offset2D(offset.getDX() - 1,
                        offset.getDY()));
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                this.chart.setWorldOffset(new Offset2D(offset.getDX() + 1,
                        offset.getDY()));
            } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                this.chart.setWorldOffset(new Offset2D(offset.getDX(),
                        offset.getDY() - 1));
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                this.chart.setWorldOffset(new Offset2D(offset.getDX(),
                        offset.getDY() + 1));
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // we don't care
    }

    /**
     * Records the offset at the time of the mouse press, so that later
     * drag events can update the offset in a relative manner.
     * 
     * @param e  the event. 
     */
    @Override
    public void mousePressed(MouseEvent e) {
        this.offsetAtMousePressed = this.chart.getWorldOffset();
        super.mousePressed(e);
    }

    /**
     * Handles the case where the user presses the ALT key and drags the mouse,
     * this translates the chart in 2D-space (to provide the ability to better
     * align the chart with the title and legend). If the user is not pressing
     * the ALT key, we pass control to the super class for the standard chart
     * rotation in 3D-space.
     *
     * @param e the mouse event.
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.isAltDown()) {
            Point currPt = e.getPoint();
            Offset2D offset = this.offsetAtMousePressed;
            Point lastPt = getLastClickPoint();
            double dx = offset.getDX() + (currPt.x - lastPt.x);
            double dy = offset.getDY() + (currPt.y - lastPt.y);
            this.chart.setWorldOffset(new Offset2D(dx, dy));
        } else {
            super.mouseDragged(e);
        }
    }
}
