/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-present, by David Gilbert.  All rights reserved.
 * 
 * https://github.com/jfree/orson-charts
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
 * license is available to sponsors (higher tiers only) of the JFree projects.
 * For details, please see visit:
 *
 * https://github.com/sponsors/jfree
 * 
 */

package org.jfree.chart3d.legend;

import java.awt.Shape;
import java.util.HashMap;
import java.util.Map;
import java.awt.Color;
import org.jfree.chart3d.internal.Args;

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
    private Map<Comparable<?>, Object> properties;
    
    /**
     * Creates a new instance.
     * 
     * @param seriesKey  the series key ({@code null} not permitted).
     * @param label  the label ({@code null} not permitted).
     * @param color  the color ({@code null} not permitted).
     */
    public StandardLegendItemInfo(Comparable<?> seriesKey, String label, 
            Color color) {
        this(seriesKey, label, null, color, null);
    }
    
    /**
     * Creates a new instance with the specified attributes.
     * 
     * @param seriesKey  the series key ({@code null} not permitted).
     * @param label  the label ({@code null} not permitted).
     * @param description  the description ({@code null} permitted).
     * @param color  the color ({@code null} not permitted).
     * @param shape the shape ({@code null} permitted).
     */
    public StandardLegendItemInfo(Comparable<?> seriesKey, String label, 
            String description, Color color, Shape shape) {
        Args.nullNotPermitted(seriesKey, "seriesKey");
        Args.nullNotPermitted(label, "label");
        Args.nullNotPermitted(color, "color");
        this.seriesKey = seriesKey;  
        this.label = label;
        this.description = description;
        this.color = color;
        this.shape = shape;
        this.properties = new HashMap<>();
    }
    
    /**
     * Returns the series key.
     * 
     * @return The series key (never {@code null}). 
     */
    @Override
    public Comparable<?> getSeriesKey() {
        return this.seriesKey;
    }

    /**
     * Returns the label for the legend item.
     * 
     * @return The label (never {@code null}). 
     */
    @Override
    public String getLabel() {
        return this.label;
    }

    /**
     * Returns the description for the legend item.
     * 
     * @return The description (possibly {@code null}). 
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the shape for the legend item.
     * 
     * @return The shape (possibly {@code null}). 
     */
    @Override
    public Shape getShape() {
        return this.shape;
    }

    /**
     * Returns the color for the legend item.
     * 
     * @return The color (never {@code null}). 
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
    public Map<Comparable<?>, Object> getProperties() {
        return this.properties;
    }
    
}
