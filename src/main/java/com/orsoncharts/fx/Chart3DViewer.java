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

import java.io.File;
import java.io.IOException;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Skinnable;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.WindowEvent;
import com.orsoncharts.Chart3D;
import com.orsoncharts.graphics3d.ExportUtils;
import com.orsoncharts.graphics3d.RenderedElement;
import com.orsoncharts.graphics3d.RenderingInfo;
import com.orsoncharts.graphics3d.ViewPoint3D;
import com.orsoncharts.interaction.fx.FXChart3DMouseEvent;
import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.util.ExportFormats;

/**
 * A control for displaying a {@link Chart3D} in JavaFX.  This control embeds
 * a {@link Chart3DCanvas} and also provides a context menu.
 * 
 * @since 1.4
 */
public class Chart3DViewer extends Control implements Skinnable {
    
    /** The chart to display. */
    private Chart3D chart;

    /** 
     * The chart canvas (which is a child node for this control).  This 
     * reference is kept for convenience, and is initialised by the control's
     * skin.
     */
    private Chart3DCanvas canvas;
    
    /** The context menu that will be attached to the canvas. */
    private ContextMenu contextMenu;
    
    /** 
     * The zoom multiplier (applicable for the zoom in and out options in 
     * the context menu).
     */
    private double zoomMultiplier = 0.95;

    /**
     * Creates a new viewer to display the supplied chart in JavaFX.
     * 
     * @param chart  the chart ({@code null} not permitted). 
     */
    public Chart3DViewer(Chart3D chart) {
        this(chart, true);
    }
    
    /**
     * Creates a new viewer instance.
     * 
     * @param chart  the chart ({@code null} not permitted).
     * @param contextMenuEnabled  enable the context menu?
     */
    public Chart3DViewer(Chart3D chart, boolean contextMenuEnabled) {
        ArgChecks.nullNotPermitted(chart, "chart");
        this.chart = chart;
        getStyleClass().add("chart3d-control");
        this.contextMenu = createContextMenu();
        this.contextMenu.setOnShowing((WindowEvent event) -> {
            Chart3DViewer viewer = Chart3DViewer.this;
            if (viewer.canvas != null) {
                viewer.canvas.setRotateViewEnabled(false);
                viewer.canvas.setTooltipEnabled(false);
            }
        });
        this.contextMenu.setOnHiding((WindowEvent event) -> {
            Chart3DViewer viewer = Chart3DViewer.this;
            if (viewer.canvas != null) {
                viewer.canvas.setRotateViewEnabled(true);
                viewer.canvas.setTooltipEnabled(true);
            }
        });
        setContextMenu(this.contextMenu);
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
        this.chart = chart;
        this.canvas.setChart(chart);
    }

    /**
     * Returns the canvas used within this control to display the chart.
     * 
     * @return The canvas (never {@code null}). 
     */
    public Chart3DCanvas getCanvas() {
        return this.canvas;
    }
    
    /**
     * Sets the canvas used within this control to display the chart.
     * This method is called by the control's skin, you should not need to
     * call it directly.
     * 
     * @param canvas  the canvas ({@code null} not permitted). 
     */
    public void setCanvas(final Chart3DCanvas canvas) {
        ArgChecks.nullNotPermitted(canvas, "canvas");
        this.canvas = canvas;
        this.canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, 
                (MouseEvent event) -> {
            RenderingInfo info = canvas.getRenderingInfo();
            RenderedElement element = info.findElementAt(
                    event.getX(), event.getY());

            Chart3DViewer viewer = Chart3DViewer.this;
            viewer.fireEvent(new FXChart3DMouseEvent(viewer,
                    FXChart3DMouseEvent.MOUSE_CLICKED, element, event)); 
        });
    }

    /**
     * Returns the multiplier used for the zoom in and out options in the
     * context menu.  The default value is {@code 0.95}.
     * 
     * @return The zoom multiplier. 
     */
    public double getZoomMultiplier() {
        return this.zoomMultiplier;
    }

    /**
     * Sets the zoom multiplier used for the zoom in and out options in the
     * context menu.  When zooming in, the current viewing distance will be
     * multiplied by this value (which defaults to 0.95).  When zooming out,
     * the viewing distance is multiplied by 1 / zoomMultiplier.
     * 
     * @param multiplier  the new multiplier.
     */
    public void setZoomMultiplier(double multiplier) {
        this.zoomMultiplier = multiplier;
    }

    /**
     * Creates the context menu.
     * 
     * @return The context menu.
     */
    private ContextMenu createContextMenu() {
        final ContextMenu menu = new ContextMenu();
        MenuItem zoomIn = new MenuItem("Zoom In");
        zoomIn.setOnAction(e -> { handleZoom(this.zoomMultiplier); });
        MenuItem zoomOut = new MenuItem("Zoom Out");
        zoomOut.setOnAction(e -> { handleZoom(1.0 / this.zoomMultiplier); });
        
        MenuItem zoomToFit = new MenuItem("Zoom To Fit");
        zoomToFit.setOnAction(e -> { handleZoomToFit(); });
        
        SeparatorMenuItem separator = new SeparatorMenuItem();
        Menu export = new Menu("Export As");
        
        MenuItem pngItem = new MenuItem("PNG...");
        pngItem.setOnAction(e -> { handleExportToPNG(); });        
        export.getItems().add(pngItem);
        
        MenuItem jpegItem = new MenuItem("JPEG...");
        jpegItem.setOnAction(e -> { handleExportToJPEG(); });        
        export.getItems().add(jpegItem);
        
        // automatically detect if OrsonPDF is on the classpath and, if it is,
        // provide a PDF export menu item
        if (ExportFormats.isOrsonPDFAvailable()) {
            MenuItem pdfItem = new MenuItem("PDF...");
            pdfItem.setOnAction(e -> { handleExportToPDF(); });
            export.getItems().add(pdfItem);
        }
        // automatically detect if JFreeSVG is on the classpath and, if it is,
        // provide a SVG export menu item
        if (ExportFormats.isJFreeSVGAvailable()) {
            MenuItem svgItem = new MenuItem("SVG...");
            svgItem.setOnAction(e -> { handleExportToSVG(); });
            export.getItems().add(svgItem);        
        }
        menu.getItems().addAll(zoomIn, zoomOut, zoomToFit, separator, export);
        return menu;
    }
    
    /**
     * A handler for the zoom in and out options in the context menu.
     * 
     * @param multiplier  the multiplier (less than 1.0 zooms in, greater than
     *         1.0 zooms out). 
     */
    private void handleZoom(double multiplier) {
        ViewPoint3D viewPt = this.chart.getViewPoint();
        double minDistance = this.canvas.getMinViewingDistance();
        double maxDistance = minDistance 
                * this.canvas.getMaxViewingDistanceMultiplier();
        double valRho = Math.max(minDistance, 
                Math.min(maxDistance, viewPt.getRho() * multiplier));
        viewPt.setRho(valRho);
        this.canvas.draw();
    }
    
    /**
     * A handler for the zoom to fit option in the context menu.
     */
    private void handleZoomToFit() {
        this.canvas.zoomToFit(canvas.getWidth(), canvas.getHeight());
    }
    
    /**
     * A handler for the export to PDF option in the context menu.  Note that
     * the Export to PDF menu item is only installed if OrsonPDF is on the 
     * classpath.
     */
    private void handleExportToPDF() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter(
                "Portable Document Format (PDF)", "pdf"));
        fileChooser.setTitle("Export to PDF");
        File file = fileChooser.showSaveDialog(this.getScene().getWindow());
        if (file != null) {
            ExportUtils.writeAsPDF(this.chart, (int) getWidth(), 
                    (int) getHeight(), file);
        } 
    }
    
    /**
     * A handler for the export to SVG option in the context menu.
     */
    private void handleExportToSVG() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export to SVG");
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter(
                "Scalable Vector Graphics (SVG)", "svg"));
        File file = fileChooser.showSaveDialog(this.getScene().getWindow());
        if (file != null) {
            ExportUtils.writeAsSVG(this.chart, (int) getWidth(), 
                    (int) getHeight(), file);
        }
    }
    
    /**
     * A handler for the export to PNG option in the context menu.
     */
    private void handleExportToPNG() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export to PNG");
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter(
                "Portable Network Graphics (PNG)", "png"));
        File file = fileChooser.showSaveDialog(this.getScene().getWindow());
        if (file != null) {
            try {
                ExportUtils.writeAsPNG(this.chart, (int) getWidth(),
                        (int) getHeight(), file);
            } catch (IOException ex) {
                // FIXME: show a dialog with the error
            }
        }        
    }

    /**
     * A handler for the export to JPEG option in the context menu.
     */
    private void handleExportToJPEG() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export to JPEG");
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter(
                "JPEG", "jpg"));
        File file = fileChooser.showSaveDialog(this.getScene().getWindow());
        if (file != null) {
            try {
                ExportUtils.writeAsJPEG(this.chart, (int) getWidth(),
                        (int) getHeight(), file);
            } catch (IOException ex) {
                // FIXME: show a dialog with the error
            }
        }        
    }

    @Override
    public String getUserAgentStylesheet() {
        return Chart3DViewer.class.getResource("chart3d-viewer.css")
                .toExternalForm();
    }
 
}

