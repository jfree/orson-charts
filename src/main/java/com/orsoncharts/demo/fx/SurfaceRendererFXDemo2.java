/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.orsoncharts.demo.fx;

import com.orsoncharts.demo.SurfaceRendererDemo2;
import com.orsoncharts.fx.Chart3DCanvas;
import javafx.scene.Node;

/**
 *
 * @author dgilbert
 */
public class SurfaceRendererFXDemo2 {
    
    public static Node createDemoNode() {
        Chart3DCanvas canvas = new Chart3DCanvas(SurfaceRendererDemo2.createChart());
        return canvas;
    }
 
}
