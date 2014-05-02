/* ===================
 * Orson Charts - Demo
 * ===================
 * 
 * Copyright (c) 2013, 2014, Object Refinery Limited.
 * All rights reserved.
 *
 * http://www.object-refinery.com/orsoncharts/index.html
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *   - Neither the name of the Object Refinery Limited nor the
 *     names of its contributors may be used to endorse or promote products
 *     derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL OBJECT REFINERY LIMITED BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * Note that the above terms apply to the demo source only, and not the 
 * Orson Charts library.
 * 
 */

package com.orsoncharts.demo.fx;

import static javafx.application.Application.launch;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import com.orsoncharts.Chart3D;
import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.demo.AreaChart3DDemo1;
import com.orsoncharts.fx.Chart3DCanvas;

/**
 * Demo application for Orson Charts in JavaFX.
 */
public class OrsonChartsFXDemo extends Application {

    @Override
    public void start(Stage stage) throws Exception {
       
        TabPane tabPane = new TabPane();
        Tab tab1 = new Tab();
        tab1.setText("Demos");
        
        SplitPane sp = new SplitPane();
        final StackPane sp1 = new StackPane();
        sp1.getChildren().add(createTreeView());
        final StackPane sp2 = new StackPane();
        sp2.getChildren().add(createChartPane());
 
        sp.getItems().addAll(sp1, sp2);
        sp.setDividerPositions(0.3f, 0.6f);
        tab1.setContent(sp);
        tabPane.getTabs().add(tab1);        
 
        Tab tab2 = new Tab();
        tab2.setText("About");
        tab2.setContent(new Rectangle(200,200, Color.AQUA));
        tabPane.getTabs().add(tab2);        

        stage.setScene(new Scene(tabPane));
        stage.setTitle("Orson Charts Demo for JavaFX");
        stage.show();
    }
    
    private StackPane createTreeView() {
        TreeItem<String> rootItem = new TreeItem<String> ("Orson Charts", null);
        rootItem.setExpanded(true);
        TreeItem<String> categoryChartsNode = new TreeItem<String>("Category Charts");
        rootItem.getChildren().add(categoryChartsNode);
        //categoryChartsNode.getChildren().add(new TreeItem<String>("AreaChart3DDemo1.java"));
        for (int i = 1; i < 6; i++) {
            TreeItem<String> item = new TreeItem<String> ("Message" + i);            
            //categoryChartsNode.getChildren().add(item);
        }        
        

        TreeView<String> tree = new TreeView<String> (rootItem);
        tree.getSelectionModel().selectedItemProperty().addListener(
            (ObservableValue<? extends TreeItem<String>> observableValue, 
                    TreeItem<String> oldItem, TreeItem<String> newItem) -> {
            selectDemo(newItem.getValue());
            //System.out.println(newItem.getValue());
        });
        StackPane root = new StackPane();
        root.getChildren().add(tree);
        return root;
    }
    
    private void selectDemo(String name) {
        System.out.println(name);        
    }
    
    private SplitPane createChartPane() {
        CategoryDataset3D dataset = AreaChart3DDemo1.createDataset();
        Chart3D chart = AreaChart3DDemo1.createChart(dataset);
        Chart3DCanvas canvas = new Chart3DCanvas(chart);
      
        SplitPane splitter = new SplitPane();
        splitter.setOrientation(Orientation.VERTICAL);
        final StackPane stackPane = new StackPane();
        stackPane.getChildren().add(canvas);
        
       // Bind canvas size to stack pane size.
        canvas.widthProperty().bind(
                       stackPane.widthProperty());
        canvas.heightProperty().bind(
                       stackPane.heightProperty());
        

        final StackPane sp2 = new StackPane();
        sp2.getChildren().add(new Button("Chart Description goes here."));  
        splitter.getItems().addAll(stackPane, sp2);
        splitter.setDividerPositions(0.75f, 0.25f);
        return splitter;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
