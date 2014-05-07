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

import java.io.File;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Skinnable;
import com.orsoncharts.Chart3D;
import com.orsoncharts.graphics3d.ExportUtils;
import com.orsoncharts.graphics3d.ViewPoint3D;
import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.util.ExportFormats;

/**
 * A control for displaying a {@link Chart3D} in JavaFX.
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
     * the context menu.
     */
    private double zoomMultiplier = 0.95;
    
    /**
     * Creates a new viewer to display the supplied chart in JavaFX.
     * 
     * @param chart  the chart (<code>null</code> not permitted). 
     */
    public Chart3DViewer(Chart3D chart) {
        this(chart, true);
    }
    
    /**
     * Creates a new viewer instance.
     * 
     * @param chart  the chart (<code>null</code> not permitted).
     * @param contextMenuEnabled  enable the context menu?
     */
    public Chart3DViewer(Chart3D chart, boolean contextMenuEnabled) {
        ArgChecks.nullNotPermitted(chart, "chart");
        this.chart = chart;
        getStyleClass().add("chart3d-control");
        this.contextMenu = createContextMenu();
        setContextMenu(this.contextMenu);
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

    /**
     * Returns the canvas used within this control to display the chart.
     * 
     * @return The canvas. 
     */
    public Chart3DCanvas getCanvas() {
        return this.canvas;
    }
    
    /**
     * Sets the canvas used within this control to display the chart.
     * This method is called by the control's skin, you should not need to
     * call it directly.
     * 
     * @param canvas  the canvas. 
     */
    public void setCanvas(Chart3DCanvas canvas) {
        this.canvas = canvas;
    }

    /**
     * Returns the multiplier used for the zoom in and out options in the
     * context menu.  The default value is <code>0.95</code>.
     * 
     * @return The zoom multiplier. 
     */
    public double getZoomMultiplier() {
        return this.zoomMultiplier;
    }

    /**
     * Sets the zoom multiplier used for the zoom in and out options in the
     * context menu.
     * 
     * @param multiplier  the new multiplier.
     */
    public void setZoomMultiplier(double multiplier) {
        this.zoomMultiplier = zoomMultiplier;
    }

    /**
     * Creates the context menu.
     * 
     * @return The context menu.
     */
    private ContextMenu createContextMenu() {
        final ContextMenu contextMenu = new ContextMenu();
        MenuItem zoomIn = new MenuItem("Zoom In");
        zoomIn.setOnAction((ActionEvent e) -> { 
            handleZoom(this.zoomMultiplier);
        });
        MenuItem zoomOut = new MenuItem("Zoom Out");
        zoomOut.setOnAction((ActionEvent e) -> {
            handleZoom(1.0 / this.zoomMultiplier);
        });
        
        MenuItem zoomToFit = new MenuItem("Zoom To Fit");
        zoomToFit.setOnAction((ActionEvent e) -> { handleZoomToFit(); });
        
        SeparatorMenuItem separator = new SeparatorMenuItem();
        Menu export = new Menu("Export As");
        
        MenuItem pngItem = new MenuItem("PNG...");
        pngItem.setOnAction((ActionEvent e) -> { handleExportToPNG(); });        
        export.getItems().add(pngItem);
        
        MenuItem jpegItem = new MenuItem("JPEG...");
        jpegItem.setOnAction((ActionEvent e) -> { handleExportToJPEG(); });        
        export.getItems().add(jpegItem);
        
        if (ExportFormats.isOrsonPDFAvailable()) {
            MenuItem pdfItem = new MenuItem("PDF...");
            pdfItem.setOnAction((ActionEvent e) -> {
                handleExportToPDF();
            });
            export.getItems().add(pdfItem);
        }
        if (ExportFormats.isJFreeSVGAvailable()) {
            MenuItem svgItem = new MenuItem("SVG...");
            svgItem.setOnAction((ActionEvent e) -> {
                handleExportToSVG();
            });
            export.getItems().add(svgItem);        
        }
        contextMenu.getItems().addAll(zoomIn, zoomOut, zoomToFit, separator, 
                export);
        return contextMenu;
    }
    
    /**
     * A handler for the zoom in and out options in the context menu.
     * 
     * @param multiplier  the multiplier. 
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
     * A handler for the export to PDF option in the context menu.
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
                ExportUtils.writeAsPNG(this.chart, (int) getWidth(),
                        (int) getHeight(), file);
            } catch (IOException ex) {
                // FIXME: show a dialog with the error
            }
        }        
    }

    @Override
    protected String getUserAgentStylesheet() {
        return Chart3DViewer.class.getResource("chart3d-viewer.css")
                .toExternalForm();
    }
 
}

