/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.legend;

import java.awt.Paint;
import java.awt.Shape;
import java.util.HashMap;
import java.util.Map;
import com.orsoncharts.util.ArgChecks;

/**
 * A standard implementation of the {@link LegendItemInfo} interface.
 */
public class StandardLegendItemInfo implements LegendItemInfo {

    private Comparable seriesKey;
    
    private String label;
    
    private String description;
    
    private Paint paint;
    
    private Shape shape;
    
    private Map<Comparable, Object> properties;
    
    /**
     * Creates a new instance.
     * 
     * @param seriesKey  the series key (<code>null</code> not permitted).
     * @param label  the label (<code>null</code> not permitted).
     * @param paint  the paint (<code>null</code> not permitted).
     */
    public StandardLegendItemInfo(Comparable seriesKey, String label, 
            Paint paint) {
        this(seriesKey, label, null, paint, null);
    }
    
    /**
     * 
     * @param seriesKey  the series key (<code>null</code> not permitted).
     * @param label  the label (<code>null</code> not permitted).
     * @param description
     * @param paint  the paint (<code>null</code> not permitted).
     * @param shape 
     */
    public StandardLegendItemInfo(Comparable seriesKey, String label, 
            String description, Paint paint, Shape shape) {
        ArgChecks.nullNotPermitted(seriesKey, "seriesKey");
        ArgChecks.nullNotPermitted(label, "label");
        ArgChecks.nullNotPermitted(paint, "paint");
        this.seriesKey = seriesKey;  
        this.label = label;
        this.description = description;
        this.paint = paint;
        this.shape = shape;
        this.properties = new HashMap<Comparable, Object>();
    }
    
    @Override
    public Comparable getSeriesKey() {
        return this.seriesKey;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public Shape getShape() {
        return this.shape;
    }

    @Override
    public Paint getPaint() {
        return this.paint;    
    }

    @Override
    public Map<Comparable, Object> getProperties() {
        return this.properties;
    }
    
}
