/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.demo;

/**
 * A description of a demo application.
 */
public class DemoDescription {

    private String className;
    
    private String fileName;
    
    /**
     * Creates a new description.
     * 
     * @param demoClassName  the class name.
     * @param fileName  the file name.
     */
    public DemoDescription(String demoClassName, String fileName) {
        this.className = demoClassName;
        this.fileName = fileName;
    }
    
    /**
     * Returns the class name.
     * 
     * @return The class name.
     */
    public String getClassName() {
        return this.className;
    }
    
    /**
     * Returns the file name.
     * 
     * @return The file name.
     */
    public String getFileName() {
        return this.fileName;
    }
    
    /**
     * Returns the class description.
     * 
     * @return The class description.
     */
    @Override
    public String toString() {
        return this.fileName;
    }
    
}