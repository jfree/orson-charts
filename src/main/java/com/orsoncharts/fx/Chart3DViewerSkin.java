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
     * @param control  the control ({@code null} not permitted). 
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
        return borderPane;
    }
    
}
