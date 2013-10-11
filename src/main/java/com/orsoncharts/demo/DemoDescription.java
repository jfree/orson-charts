/* --------------------
 * DemoDescription.java
 * --------------------
 * (C) Copyright 2004-2011, by Object Refinery Limited.
 *
 */

package com.orsoncharts.demo;

/**
 * A description of a demo application.
 */
public class DemoDescription {

    private String className;
    
    private String description;
    
    /**
     * Creates a new description.
     * 
     * @param demoClassName  the class name.
     * @param demoDescription  the description.
     */
    public DemoDescription(String demoClassName, String demoDescription) {
        this.className = demoClassName;
        this.description = demoDescription;
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
     * Returns the description.
     * 
     * @return The description.
     */
    public String getDescription() {
        return this.description;
    }
    
    /**
     * Returns the class description.
     * 
     * @return The class description.
     */
    public String toString() {
        return this.description;
    }
    
}