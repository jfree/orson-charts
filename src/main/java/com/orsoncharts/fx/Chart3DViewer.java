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

import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Skinnable;
import com.orsoncharts.Chart3D;
import com.orsoncharts.util.ArgChecks;

/**
 * A control for displaying a {@link Chart3D} in JavaFX.
 * 
 * @since 1.4
 */
public class Chart3DViewer extends Control implements Skinnable {
    
    private Chart3D chart;

    /** The chart canvas (which is a child node for this control). */
    private Chart3DCanvas canvas;
    
    /** The context menu that will be attached to the canvas. */
    private ContextMenu contextMenu;
    
    /**
     * Creates a new canvas to display the supplied chart in JavaFX.
     * 
     * @param chart  the chart (<code>null</code> not permitted). 
     */
    public Chart3DViewer(Chart3D chart) {
        this(chart, true);
    }
    
    public Chart3DViewer(Chart3D chart, boolean contextMenuEnabled) {
        this.chart = chart;
        getStyleClass().add("chart3d-control");
        this.contextMenu = createContextMenu();
        setContextMenu(this.contextMenu);
    }

    private ContextMenu createContextMenu() {
        final ContextMenu contextMenu = new ContextMenu();
        MenuItem item1 = new MenuItem("Zoom In");
        MenuItem item2 = new MenuItem("Zoom Out");
        MenuItem item3 = new MenuItem("Zoom To Fit");
        SeparatorMenuItem separator = new SeparatorMenuItem();
        Menu export = new Menu("Export As");
        MenuItem item4 = new MenuItem("PNG...");
        MenuItem item5 = new MenuItem("JPEG...");
        MenuItem item6 = new MenuItem("PDF...");
        MenuItem item7 = new MenuItem("SVG...");
        export.getItems().addAll(item4, item5, item6, item7);
        contextMenu.getItems().addAll(item1, item2, item3, separator, export);
        return contextMenu;
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
        this.canvas.setChart(chart);
    }

    @Override
    protected String getUserAgentStylesheet() {
        return Chart3DViewer.class.getResource("chart3d-control.css").toExternalForm();
    }
 
}

