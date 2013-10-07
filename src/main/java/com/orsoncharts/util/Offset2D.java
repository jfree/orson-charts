/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.orsoncharts.util;

/**
 * An offset (dx, dy) in two dimensional space.
 */
public class Offset2D {
    
    private double dx;
    
    private double dy;
    
    public Offset2D(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }
    
    public double getDX() {
        return this.dx;
    }
    
    public double getDY() {
        return this.dy;
    }
}
