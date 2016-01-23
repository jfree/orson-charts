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

package com.orsoncharts.fx;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Dimension2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DChangeEvent;
import com.orsoncharts.Chart3DChangeListener;
import com.orsoncharts.data.ItemKey;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.Object3D;
import com.orsoncharts.graphics3d.Offset2D;
import com.orsoncharts.graphics3d.RenderingInfo;
import com.orsoncharts.graphics3d.ViewPoint3D;
import com.orsoncharts.util.ArgChecks;
import org.jfree.fx.FXGraphics2D;

/**
 * A canvas node for displaying a {@link Chart3D} in JavaFX.  This node
 * handles mouse events and tooltips but does not provide a context menu or
 * toolbar (these features are provided by the {@link Chart3DViewer} class.)
 * 
 * @since 1.4
 */
public class Chart3DCanvas extends Canvas implements Chart3DChangeListener {
    
    /** The chart being displayed in the canvas. */
    private Chart3D chart;
    
    /**
     * The graphics drawing context (will be an instance of FXGraphics2D).
     */
    private Graphics2D g2;

    /** The rendering info from the last drawing of the chart. */
    private RenderingInfo renderingInfo;

    /** 
     * The minimum viewing distance (zooming in will not go closer than this).
     */
    private double minViewingDistance;

    /** 
     * The multiplier for the maximum viewing distance (a multiple of the 
     * minimum viewing distance).
     */
    private double maxViewingDistanceMultiplier;
    
    /**
     * The margin around the chart (used when zooming to fit).
     */
    private double margin = 0.25;
    
    /** The angle increment for panning left and right (in radians). */
    private double panIncrement = Math.PI / 120.0;
    
    /** The angle increment for rotating up and down (in radians). */
    private double rotateIncrement = Math.PI / 120.0;
    
    /** 
     * The (screen) point of the last mouse click (will be {@code null} 
     * initially).  Used to calculate the mouse drag distance and direction.
     */
    private Point lastClickPoint;
    
    /**
     * The (screen) point of the last mouse move point that was handled.
     */
    private Point lastMovePoint;
    
    /**
     * Temporary state to track the 2D offset during an ALT-mouse-drag
     * operation.
     */
    private Offset2D offsetAtMousePressed;
    
    /** The tooltip object for the canvas. */
    private Tooltip tooltip;
    
    /** Are tooltips enabled? */
    private boolean tooltipEnabled = true;
    
    /** Is rotation by mouse-dragging enabled? */
    private boolean rotateViewEnabled = true;
    
    /**
     * Creates a new canvas to display the supplied chart in JavaFX.
     * 
     * @param chart  the chart ({@code null} not permitted). 
     */
    public Chart3DCanvas(Chart3D chart) {
        this.chart = chart;
        this.minViewingDistance = chart.getDimensions().getDiagonalLength();
        this.maxViewingDistanceMultiplier = 8.0;        
        widthProperty().addListener(e -> draw());
        heightProperty().addListener(e -> draw());
        this.g2 = new FXGraphics2D(getGraphicsContext2D());

        setOnMouseMoved((MouseEvent me) -> { updateTooltip(me); });
        setOnMousePressed((MouseEvent me) -> {
            Chart3DCanvas canvas = Chart3DCanvas.this;
            canvas.lastClickPoint = new Point((int) me.getScreenX(),
                    (int) me.getScreenY());
            canvas.lastMovePoint = canvas.lastClickPoint;
        });

        setOnMouseDragged((MouseEvent me) -> { handleMouseDragged(me); });
        setOnScroll((ScrollEvent event) -> { handleScroll(event); });
        this.chart.addChangeListener(this);
    }
    
    /**
     * Returns the chart that is being displayed by this node.
     * 
     * @return The chart (never {@code null}). 
     */
    public Chart3D getChart() {
        return this.chart;
    }
    
    /**
     * Sets the chart to be displayed by this node.
     * 
     * @param chart  the chart ({@code null} not permitted). 
     */
    public void setChart(Chart3D chart) {
        ArgChecks.nullNotPermitted(chart, "chart");
        if (this.chart != null) {
            this.chart.removeChangeListener(this);
        }
        this.chart = chart;
        this.chart.addChangeListener(this);
        draw();
    }

    /**
     * Returns the margin that is used when zooming to fit.  The margin can 
     * be used to control the amount of space around the chart (where labels
     * are often drawn).  The default value is 0.25 (25 percent).
     * 
     * @return The margin. 
     */
    public double getMargin() {
        return this.margin;
    }

    /**
     * Sets the margin (note that this will not have an immediate effect, it
     * will only be applied on the next call to 
     * {@link #zoomToFit(double, double)}).
     * 
     * @param margin  the margin. 
     */
    public void setMargin(double margin) {
        this.margin = margin;
    }
    
    /**
     * Returns the rendering info from the most recent drawing of the chart.
     * 
     * @return The rendering info (possibly {@code null}).
     */
    public RenderingInfo getRenderingInfo() {
        return this.renderingInfo;
    }

    /**
     * Returns the minimum distance between the viewing point and the origin. 
     * This is initialised in the constructor based on the chart dimensions.
     * 
     * @return The minimum viewing distance.
     */
    public double getMinViewingDistance() {
        return this.minViewingDistance;
    }

    /**
     * Sets the minimum between the viewing point and the origin. If the
     * current distance is lower than the new minimum, it will be set to this
     * minimum value.
     * 
     * @param minViewingDistance  the minimum viewing distance.
     */
    public void setMinViewingDistance(double minViewingDistance) {
        this.minViewingDistance = minViewingDistance;
        if (this.chart.getViewPoint().getRho() < this.minViewingDistance) {
            this.chart.getViewPoint().setRho(this.minViewingDistance);
        }
    }

    /**
     * Returns the multiplier used to calculate the maximum permitted distance
     * between the viewing point and the origin.  The multiplier is applied to
     * the minimum viewing distance.  The default value is 8.0.
     * 
     * @return The multiplier. 
     */
    public double getMaxViewingDistanceMultiplier() {
        return this.maxViewingDistanceMultiplier;
    }

    /**
     * Sets the multiplier used to calculate the maximum viewing distance.
     * 
     * @param multiplier  the multiplier (must be &gt; 1.0).
     */
    public void setMaxViewingDistanceMultiplier(double multiplier) {
        if (multiplier < 1.0) {
            throw new IllegalArgumentException(
                    "The 'multiplier' should be greater than 1.0.");
        }
        this.maxViewingDistanceMultiplier = multiplier;
        double maxDistance = this.minViewingDistance * multiplier;
        if (this.chart.getViewPoint().getRho() > maxDistance) {
            this.chart.getViewPoint().setRho(maxDistance);
        }
    }

    /**
     * Returns the increment for panning left and right.  This is an angle in
     * radians, and the default value is {@code Math.PI / 120.0}.
     * 
     * @return The panning increment. 
     */
    public double getPanIncrement() {
        return this.panIncrement;
    }

    /**
     * Sets the increment for panning left and right (an angle measured in 
     * radians).
     * 
     * @param increment  the angle in radians.
     */
    public void setPanIncrement(double increment) {
        this.panIncrement = increment;
    }

    /**
     * Returns the increment for rotating up and down.  This is an angle in
     * radians, and the default value is {@code Math.PI / 120.0}.
     * 
     * @return The rotate increment. 
     */
    public double getRotateIncrement() {
        return this.rotateIncrement;
    }

    /**
     * Sets the increment for rotating up and down (an angle measured in 
     * radians).
     * 
     * @param increment  the angle in radians.
     */
    public void setRotateIncrement(double increment) {
        this.rotateIncrement = increment;
    }
 
    /**
     * Returns the flag that controls whether or not tooltips are enabled.
     * 
     * @return The flag. 
     */
    public boolean isTooltipEnabled() {
        return this.tooltipEnabled;
    }

    /**
     * Sets the flag that controls whether or not tooltips are enabled.
     * 
     * @param tooltipEnabled  the new flag value. 
     */
    public void setTooltipEnabled(boolean tooltipEnabled) {
        this.tooltipEnabled = tooltipEnabled;
    }

    /**
     * Returns a flag that controls whether or not rotation by mouse dragging
     * is enabled.
     * 
     * @return A boolean.
     */
    public boolean isRotateViewEnabled() {
        return this.rotateViewEnabled;
    }

    /**
     * Sets the flag that controls whether or not rotation by mouse dragging
     * is enabled.
     * 
     * @param enabled  the new flag value.
     */
    public void setRotateViewEnabled(boolean enabled) {
        this.rotateViewEnabled = enabled;
    }

    /**
     * Adjusts the viewing distance so that the chart fits the specified
     * size.  A margin is left (see {@link #getMargin()}) around the edges to 
     * leave room for labels etc.
     * 
     * @param width  the width.
     * @param height  the height.
     */    
    public void zoomToFit(double width, double height) {
        int w = (int) (width * (1.0 - this.margin));
        int h = (int) (height * (1.0 - this.margin));
        Dimension2D target = new Dimension(w, h);
        Dimension3D d3d = this.chart.getDimensions();
        float distance = this.chart.getViewPoint().optimalDistance(target, 
                d3d, this.chart.getProjDistance());
        this.chart.getViewPoint().setRho(distance);
        draw();        
    }

    /**
     * Draws the content of the canvas and updates the 
     * {@code renderingInfo} attribute with the latest rendering 
     * information.
     */
    public void draw() {
        GraphicsContext ctx = getGraphicsContext2D();
        ctx.save();
        double width = getWidth();
        double height = getHeight();
        if (width > 0 && height > 0) {
            ctx.clearRect(0, 0, width, height);
            this.renderingInfo = this.chart.draw(this.g2, 
                    new Rectangle((int) width, (int) height));
        }
        ctx.restore();
    }
 
    /**
     * Return {@code true} to indicate the canvas is resizable.
     * 
     * @return {@code true}. 
     */
    @Override
    public boolean isResizable() {
        return true;
    }

    /**
     * Updates the tooltip.  This method will return without doing anything if
     * the {@code tooltipEnabled} flag is set to false.
     * 
     * @param me  the mouse event.
     */
    protected void updateTooltip(MouseEvent me) {
        if (!this.tooltipEnabled || this.renderingInfo == null) {
            return;
        }
        Object3D object = this.renderingInfo.fetchObjectAt(me.getX(), 
                me.getY());
        if (object != null) {
            ItemKey key = (ItemKey) object.getProperty(Object3D.ITEM_KEY);
            if (key != null) {
                String toolTipText = chart.getPlot().generateToolTipText(key);
                if (this.tooltip == null) {
                    this.tooltip = new Tooltip(toolTipText);
                    Tooltip.install(this, this.tooltip);
                } else {
                    this.tooltip.setText(toolTipText);
                    this.tooltip.setAnchorX(me.getScreenX());
                    this.tooltip.setAnchorY(me.getScreenY());
                }                   
            } else {
                if (this.tooltip != null) {
                    Tooltip.uninstall(this, this.tooltip);
                }
                this.tooltip = null;
            }
        }
    }
    
    /**
     * Handles a mouse dragged event by rotating the chart (unless the
     * {@code rotateViewEnabled} flag is set to false, in which case this
     * method does nothing).
     * 
     * @param event  the mouse event. 
     */
    private void handleMouseDragged(MouseEvent event) {
        if (!this.rotateViewEnabled) {
            return;
        }
        Point currPt = new Point((int) event.getScreenX(), 
                    (int) event.getScreenY());
        int dx = currPt.x - this.lastMovePoint.x;
        int dy = currPt.y - this.lastMovePoint.y;
        this.lastMovePoint = currPt;
        this.chart.getViewPoint().panLeftRight(-dx * this.panIncrement);
        this.chart.getViewPoint().moveUpDown(-dy * this.rotateIncrement);
        this.draw();        
    }

    private void handleScroll(ScrollEvent event) {
        double units = -event.getDeltaY();
        double maxViewingDistance = this.maxViewingDistanceMultiplier
                    * this.minViewingDistance;
        ViewPoint3D vp = this.chart.getViewPoint();
        double valRho = Math.max(this.minViewingDistance,
                Math.min(maxViewingDistance, vp.getRho() + units));
        vp.setRho(valRho);
        draw();
    }

    @Override
    public void chartChanged(Chart3DChangeEvent event) {
        draw();
    }
}

