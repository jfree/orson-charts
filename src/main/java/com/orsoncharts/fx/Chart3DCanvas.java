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

package com.orsoncharts.fx;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Dimension2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.control.Tooltip;
import com.orsoncharts.Chart3D;
import com.orsoncharts.data.ItemKey;
import com.orsoncharts.fx.graphics2d.FXGraphics2D;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.Object3D;
import com.orsoncharts.graphics3d.Offset2D;
import com.orsoncharts.graphics3d.RenderingInfo;
import com.orsoncharts.graphics3d.ViewPoint3D;
import com.orsoncharts.util.ArgChecks;
import javafx.scene.control.ContextMenu;

/**
 * A canvas for displaying a {@link Chart3D} in JavaFX. 
 * 
 * @since 1.4
 */
public class Chart3DCanvas extends Canvas {
    
    /** The chart being displayed in the canvas. */
    private Chart3D chart;
    
    /**
     * The graphics drawing context.
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
    
    /** The roll increment (in radians). */
    private double rollIncrement;
    
    /** 
     * The (screen) point of the last mouse click (will be <code>null</code> 
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
    
    /**
     * Creates a new canvas to display the supplied chart in JavaFX.
     * 
     * @param chart  the chart (<code>null</code> not permitted). 
     */
    public Chart3DCanvas(Chart3D chart) {
        this.chart = chart;
        this.minViewingDistance = chart.getDimensions().getDiagonalLength();
        this.maxViewingDistanceMultiplier = 8.0;        
        widthProperty().addListener(evt -> draw());
        heightProperty().addListener(evt -> draw());
        this.g2 = new FXGraphics2D(getGraphicsContext2D());

        setOnMouseMoved((MouseEvent me) -> { updateTooltip(me); });
        
        setOnMousePressed((MouseEvent me) -> {
            Chart3DCanvas.this.lastClickPoint = new Point((int) me.getScreenX(),
                    (int) me.getScreenY());
            Chart3DCanvas.this.lastMovePoint = Chart3DCanvas.this.lastClickPoint;
        });

        setOnMouseDragged((MouseEvent me) -> {
            Point currPt = new Point((int) me.getScreenX(), 
                    (int) me.getScreenY());
            int dx = currPt.x - Chart3DCanvas.this.lastMovePoint.x;
            int dy = currPt.y - Chart3DCanvas.this.lastMovePoint.y;
            Chart3DCanvas.this.lastMovePoint = currPt;
            Chart3DCanvas.this.chart.getViewPoint().panLeftRight(
                    -dx * this.panIncrement);
            Chart3DCanvas.this.chart.getViewPoint().moveUpDown(
                    -dy * this.rotateIncrement);
            Chart3DCanvas.this.draw();
        });

        setOnScroll((ScrollEvent event) -> {
            double units = event.getDeltaY();
            double maxViewingDistance 
                    = Chart3DCanvas.this.maxViewingDistanceMultiplier
                    * Chart3DCanvas.this.minViewingDistance;
            ViewPoint3D vp = Chart3DCanvas.this.chart.getViewPoint();
            double valRho = Math.max(Chart3DCanvas.this.minViewingDistance,
                    Math.min(maxViewingDistance, vp.getRho() + units));
            vp.setRho(valRho);
            Chart3DCanvas.this.draw();
        });
        
    }
    
    private boolean isContextMenuShowing() {
        return false;  // FIXME
    }
    
    /**
     * Returns the chart that is being displayed by this node.
     * 
     * @return The chart (never <code>null</code>). 
     */
    public Chart3D getChart() {
        return this.chart;
    }
    
    /**
     * Sets the chart to be displayed by this node.
     * 
     * @param chart  the chart (<code>null</code> not permitted). 
     */
    public void setChart(Chart3D chart) {
        ArgChecks.nullNotPermitted(chart, "chart");
        this.chart = chart;
        draw();
    }

    public double getMargin() {
        return this.margin;
    }

    public void setMargin(double margin) {
        this.margin = margin;
    }
    
    public RenderingInfo getRenderingInfo() {
        return this.renderingInfo;
    }

    public double getMinViewingDistance() {
        return this.minViewingDistance;
    }

    public void setMinViewingDistance(double minViewingDistance) {
        this.minViewingDistance = minViewingDistance;
        if (this.chart.getViewPoint().getRho() < this.minViewingDistance) {
            this.chart.getViewPoint().setRho(this.minViewingDistance);
        }
    }

    public double getMaxViewingDistanceMultiplier() {
        return this.maxViewingDistanceMultiplier;
    }

    public void setMaxViewingDistanceMultiplier(double multiplier) {
        this.maxViewingDistanceMultiplier = multiplier;
        double maxDistance = this.minViewingDistance * multiplier;
        if (this.chart.getViewPoint().getRho() > maxDistance) {
            this.chart.getViewPoint().setRho(maxDistance);
        }
    }

    public double getPanIncrement() {
        return panIncrement;
    }

    public void setPanIncrement(double panIncrement) {
        this.panIncrement = panIncrement;
    }

    public double getRotateIncrement() {
        return rotateIncrement;
    }

    public void setRotateIncrement(double rotateIncrement) {
        this.rotateIncrement = rotateIncrement;
    }

    public double getRollIncrement() {
        return rollIncrement;
    }

    public void setRollIncrement(double rollIncrement) {
        this.rollIncrement = rollIncrement;
    }    
 
    /**
     * Adjusts the viewing distance so that the chart fits the specified
     * size.  A margin is left (see {@link #getMargin()} around the edges to 
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
     * Draws the content of the canvas.
     */
    public void draw() {
        getGraphicsContext2D().save();
        double width = getWidth();
        double height = getHeight();
        if (width > 0 && height > 0) {
            this.renderingInfo = this.chart.draw(this.g2, 
                    new Rectangle((int) width, (int) height));
        }
        getGraphicsContext2D().restore();
    }
 
    @Override
    public boolean isResizable() {
        return true;
    }

    protected void updateTooltip(MouseEvent me) {
        if (this.renderingInfo == null) {
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
}

