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
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import com.orsoncharts.Chart3D;
import com.orsoncharts.data.ItemKey;
import com.orsoncharts.fx.graphics2d.FXGraphics2D;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.Object3D;
import com.orsoncharts.graphics3d.Offset2D;
import com.orsoncharts.graphics3d.RenderingInfo;
import com.orsoncharts.graphics3d.ViewPoint3D;
import javafx.scene.control.Tooltip;

/**
 * A canvas for displaying a {@link Chart3D} in JavaFX. 
 */
public class Chart3DCanvas extends Canvas {
    
    private Chart3D chart;
    
    /** 
     * The minimum viewing distance (zooming in will not go closer than this).
     */
    private double minViewingDistance;

    private double maxViewingDistanceMultiplier;
    
    private double margin = 0.25;
    
    private Graphics2D g2;

    /** The rendering info from the last drawing of the chart. */
    private RenderingInfo renderingInfo;

    /** The angle increment for panning left and right (in radians). */
    private double panIncrement;
    
    /** The angle increment for rotating up and down (in radians). */
    private double rotateIncrement;
    
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

        setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                double units = event.getDeltaY();
                double maxViewingDistance = Chart3DCanvas.this.maxViewingDistanceMultiplier 
                        * Chart3DCanvas.this.minViewingDistance;
                ViewPoint3D vp = Chart3DCanvas.this.chart.getViewPoint();
                double valRho = Math.max(Chart3DCanvas.this.minViewingDistance, 
                        Math.min(maxViewingDistance, vp.getRho() + units));
                vp.setRho(valRho);
                Chart3DCanvas.this.draw();
            }
        });
        setOnMouseMoved((MouseEvent me) -> { updateTooltip(me); });
        setOnMousePressed((MouseEvent me) -> {
            Chart3DCanvas.this.lastClickPoint = new Point((int) me.getScreenX(), (int) me.getScreenY());
            Chart3DCanvas.this.lastMovePoint = Chart3DCanvas.this.lastClickPoint;
        });
        setOnMouseDragged((MouseEvent me) -> {
            Point currPt = new Point((int) me.getScreenX(), (int) me.getScreenY());
            int dx = currPt.x - Chart3DCanvas.this.lastMovePoint.x;
            int dy = currPt.y - Chart3DCanvas.this.lastMovePoint.y;
            Chart3DCanvas.this.lastMovePoint = currPt;
            Chart3DCanvas.this.chart.getViewPoint().panLeftRight(-dx * Math.PI / 120);
            Chart3DCanvas.this.chart.getViewPoint().moveUpDown(-dy * Math.PI / 120);
            Chart3DCanvas.this.draw();
        });
        
    }
    
    public void setChart(Chart3D chart) {
        this.chart = chart;
        draw();
    }
        
    public Graphics2D getGraphics2D() {
        return this.g2;
    }
 
    /**
     * Adjusts the viewing distance so that the chart fits the specified
     * size.  A margin is left (see {@link #getMargin()} around the edges to 
     * leave room for labels etc.
     * 
     * @param size  the target size (<code>null</code> not permitted).
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
 
    @Override
    public double prefWidth(double height) {
        return 100;
    }
 
    @Override
    public double prefHeight(double width) {
        return 100;
    }

    protected void updateTooltip(MouseEvent me) {
        if (this.renderingInfo != null) {
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
}

