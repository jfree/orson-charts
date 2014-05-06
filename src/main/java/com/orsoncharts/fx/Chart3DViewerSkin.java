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

import javafx.scene.control.SkinBase;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

/**
 * A default skin for the {@link Chart3DViewer}.
 * 
 * @since 1.4
 */
public class Chart3DViewerSkin extends SkinBase<Chart3DViewer>  {

    /**
     * Creates a new instance.
     * 
     * @param control  the control (<code>null</code> not permitted). 
     */    
    public Chart3DViewerSkin(Chart3DViewer control) {
        super(control);
        getChildren().add(createNode());
    }
 
    /** 
     * Creates the node representing this control.
     * 
     * @return The node.
     */
    private BorderPane createNode() {
        BorderPane borderPane = new BorderPane();
        borderPane.setPrefSize(800, 500);
        StackPane sp = new StackPane();
        sp.setMinSize(10, 10);
        sp.setPrefSize(600, 400);
        Chart3DCanvas canvas = new Chart3DCanvas(getSkinnable().getChart());
        canvas.widthProperty().bind(sp.widthProperty());
        canvas.heightProperty().bind(sp.heightProperty());
        sp.getChildren().add(canvas);
        getSkinnable().setCanvas(canvas);
        borderPane.setCenter(sp);
        //borderPane.setBottom(new Button("Hello World"));
        return borderPane;
    }
    
}
