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

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import com.orsoncharts.Chart3D;
import com.orsoncharts.fx.graphics2d.FXGraphics2D;
import com.orsoncharts.graphics3d.Offset2D;
import com.orsoncharts.graphics3d.ViewPoint3D;

/**
 * A canvas for displaying a {@link Chart3D} in JavaFX. 
 */
public class Chart3DCanvas extends Canvas {
    
    private Chart3D chart;
    
    private Graphics2D g2;

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
    
    
    public Chart3DCanvas(Chart3D chart) {
        this.chart = chart;
        widthProperty().addListener(evt -> draw());
        heightProperty().addListener(evt -> draw());
        this.g2 = new FXGraphics2D(getGraphicsContext2D());

        setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                double units = event.getDeltaY();
                //double maxViewingDistance = this.maxViewingDistanceMultiplier 
                //* this.minViewingDistance;
                ViewPoint3D vp = Chart3DCanvas.this.chart.getViewPoint();
                double valRho = vp.getRho();
                
                //double valRho = Math.max(this.minViewingDistance, 
                //Math.min(maxViewingDistance, 
                //this.drawable.getViewPoint().getRho() + units));
                vp.setRho(valRho + units);
        Chart3DCanvas.this.draw();
                
               
            }
        });
        setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                Chart3DCanvas.this.lastClickPoint = new Point((int) me.getScreenX(), (int) me.getScreenY());
                Chart3DCanvas.this.lastMovePoint = Chart3DCanvas.this.lastClickPoint;
                //this.offsetAtMousePressed = this.drawable.getTranslate2D();
            }
        });
        setOnMouseDragged(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                Point currPt = new Point((int) me.getScreenX(), (int) me.getScreenY());
                int dx = currPt.x - Chart3DCanvas.this.lastMovePoint.x;
                int dy = currPt.y - Chart3DCanvas.this.lastMovePoint.y;
                Chart3DCanvas.this.lastMovePoint = currPt;
                Chart3DCanvas.this.chart.getViewPoint().panLeftRight(-dx * Math.PI / 120);
                Chart3DCanvas.this.chart.getViewPoint().moveUpDown(-dy * Math.PI / 120);            
                Chart3DCanvas.this.draw();
            }
        });
        
    }
        
    public Graphics2D getGraphics2D() {
        return this.g2;
    }
 
    public void draw() {
        getGraphicsContext2D().save();
        double width = getWidth();
        double height = getHeight();
        if (width > 0 && height > 0) {
            this.chart.draw(this.g2, new Rectangle((int) width, (int) height));
        }
        getGraphicsContext2D().restore();
    }
 
    @Override
    public boolean isResizable() {
        return true;
    }
 
    @Override
    public double prefWidth(double height) {
        return getWidth();
    }
 
    @Override
    public double prefHeight(double width) {
        return getHeight();
    }

}

