/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * Redistribution of these source files is prohibited.
 * 
 */

package com.orsoncharts.legend;

import java.awt.Shape;
import java.util.HashMap;
import java.util.Map;
import java.awt.Color;
import com.orsoncharts.util.ArgChecks;

/**
 * A standard implementation of the {@link LegendItemInfo} interface.
 */
public class StandardLegendItemInfo implements LegendItemInfo {

    /** The series key. */
    private Comparable<?> seriesKey;
    
    /** The series label. */
    private String label;
    
    /** A description of the item. */
    private String description;
    
    /** The color to represent this legend item. */
    private Color color;
    
    /** The shape to represent this legend item. */
    private Shape shape;
    
    /** Storage for other properties. */
    private Map<Comparable, Object> properties;
    
    /**
     * Creates a new instance.
     * 
     * @param seriesKey  the series key (<code>null</code> not permitted).
     * @param label  the label (<code>null</code> not permitted).
     * @param color  the color (<code>null</code> not permitted).
     */
    public StandardLegendItemInfo(Comparable<?> seriesKey, String label, 
            Color color) {
        this(seriesKey, label, null, color, null);
    }
    
    /**
     * Creates a new instance with the specified attributes.
     * 
     * @param seriesKey  the series key (<code>null</code> not permitted).
     * @param label  the label (<code>null</code> not permitted).
     * @param description  the description (<code>null</code> permitted).
     * @param color  the color (<code>null</code> not permitted).
     * @param shape the shape (<code>null</code> permitted).
     */
    public StandardLegendItemInfo(Comparable<?> seriesKey, String label, 
            String description, Color color, Shape shape) {
        ArgChecks.nullNotPermitted(seriesKey, "seriesKey");
        ArgChecks.nullNotPermitted(label, "label");
        ArgChecks.nullNotPermitted(color, "color");
        this.seriesKey = seriesKey;  
        this.label = label;
        this.description = description;
        this.color = color;
        this.shape = shape;
        this.properties = new HashMap<Comparable, Object>();
    }
    
    /**
     * Returns the series key.
     * 
     * @return The series key (never <code>null</code>). 
     */
    @Override
    public Comparable<?> getSeriesKey() {
        return this.seriesKey;
    }

    /**
     * Returns the label for the legend item.
     * 
     * @return The label (never <code>null</code>). 
     */
    @Override
    public String getLabel() {
        return this.label;
    }

    /**
     * Returns the description for the legend item.
     * 
     * @return The description (possibly <code>null</code>). 
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the shape for the legend item.
     * 
     * @return The shape (possibly <code>null</code>). 
     */
    @Override
    public Shape getShape() {
        return this.shape;
    }

    /**
     * Returns the color for the legend item.
     * 
     * @return The color (never <code>null</code>). 
     */
    @Override
    public Color getColor() {
        return this.color;    
    }

    /**
     * Returns the properties for the legend item.
     * 
     * @return The properties for the legend item. 
     */
    @Override
    public Map<Comparable, Object> getProperties() {
        return this.properties;
    }
    
}
