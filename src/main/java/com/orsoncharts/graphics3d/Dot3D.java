/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package com.orsoncharts.graphics3d;

import java.awt.Color;

/**
 *
 */
public class Dot3D extends Object3D {
        
    private Color color;
    
    public Dot3D(float x, float y, float z, Color color) {
      addVertex(new Point3D(x, y, z));
      this.color = color;
    }
    
}
