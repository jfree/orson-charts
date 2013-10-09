/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.orsoncharts.util;

import java.awt.geom.Line2D;

/**
 *
 * @author dgilbert
 */
public class ShapeUtils {
 
    public static String lineToString(Line2D line) {
        return "[" + line.getX1() + ", " + line.getY1() + ", " + line.getX2() 
                + ", " + line.getY2() + "]";
    }
}
