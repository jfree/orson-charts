/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package com.orsoncharts.graphics3d;

/**
 * Represents a triangle, defined in terms of vertex indices (not actual
 * coordinates - these are obtained elsewhere).
 */
public class Triangle {

    private int[] vertices;
    
    /**
     * Creates a new triangle.
     * 
     * @param a  the index for vertex A.
     * @param b  the index for vertex B.
     * @param c  the index for vertex C.
     */
    public Triangle(int a, int b, int c) {
        this.vertices = new int[] {a, b, c};    
    }
    
    /**
     * Returns the index for vertex A.
     * 
     * @return The index for vertex A.
     */
    public int getA() {
        return this.vertices[0];
    }
    
    /**
     * Returns the index for vertex B.
     * 
     * @return The index for vertex B.
     */
    public int getB() {
        return this.vertices[1];
    }
    
    /**
     * Returns the index for vertex C.
     * 
     * @return The index for vertex C.
     */
    public int getC() {
        return this.vertices[2];
    }
    
    /**
     * Returns a string representation of the triangle.
     * 
     * @return A string.
     */
    public String toString() {
        return "Triangle[" + this.vertices[0] + "," + this.vertices[1] 
                + "," + this.vertices[2] + "]";
    }
    
    
}