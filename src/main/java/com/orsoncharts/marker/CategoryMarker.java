/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * Redistribution of this source file is prohibited.
 * 
 */

package com.orsoncharts.marker;

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import com.orsoncharts.util.ArgChecks;

/**
 * A marker for a category on a CategoryAxis3D.  This marker could be used to
 * highlight one selected category.
 * 
 * @since 1.2
 */
public class CategoryMarker extends AbstractMarker {

    /** The category to mark. */
    Comparable category;

    /** 
     * The marker type (used to indicate whether the marker is represented by
     * a line or a band). 
     */
    CategoryMarkerType type;
    
    /** The stroke for the marker line. */
    private Stroke stroke;
    
    /** The paint for the marker line. */
    private Paint paint;
    
    /** The fill paint used when drawing a band for the marker. */
    private Paint fillPaint;
    
    // TODO: labels
    
    /**
     * Creates a marker for the specified category. 
     */
    public CategoryMarker(Comparable category) {
        super();
        ArgChecks.nullNotPermitted(category, "category");
        this.category = category;
        this.type = CategoryMarkerType.BAND;
        this.stroke = DEFAULT_LINE_STROKE;
        this.paint = DEFAULT_MARKER_PAINT;
        this.fillPaint = DEFAULT_FILL_PAINT;
    }
    
    /**
     * Returns the category.
     * 
     * @return The category (never <code>null</code>). 
     */
    public Comparable getCategory() {
        return this.category;
    }
    
    /**
     * Sets the category for the marker and sends a change event to all 
     * registered listeners.
     * 
     * @param category  the new category (<code>null</code> not permitted). 
     */
    public void setCategory(Comparable category) {
        ArgChecks.nullNotPermitted(category, "category");
        this.category = category;
        fireChangeEvent();
    }

    /**
     * Returns the marker type which determines whether the marker is drawn
     * as a band (the default) or a line.
     * 
     * @return The type (never <code>null</code>). 
     */
    public CategoryMarkerType getType() {
        return this.type;
    }
    
    /**
     * Sets the marker type and sends a change event to all registered 
     * listeners. 
     * 
     * @param type  the type (<code>null</code> not permitted). 
     */
    public void setType(CategoryMarkerType type) {
        ArgChecks.nullNotPermitted(type, "type");
        this.type = type;
        fireChangeEvent();
    }
    
    /**
     * Handles drawing of the marker.  This method is called by the library,
     * you won't normally call it directly.
     * 
     * @param g2  the graphics device (<code>null</code> not permitted).
     * @param markerData   the marker data (<code>null</code> not permitted).
     */
    @Override
    public void draw(Graphics2D g2, MarkerData markerData) {
        if (markerData.getType().equals(MarkerDataType.VALUE)) {
            MarkerLine ml = markerData.getValueLine();
            g2.setPaint(this.paint);
            g2.setStroke(this.stroke);
            Line2D l = new Line2D.Double(ml.getStartPoint(), ml.getEndPoint());
            g2.draw(l);
        } else if (markerData.getType().equals(MarkerDataType.RANGE)) {
            MarkerLine sl = markerData.getStartLine();
            Line2D l1 = new Line2D.Double(sl.getStartPoint(), sl.getEndPoint());
            MarkerLine el = markerData.getEndLine();
            Line2D l2 = new Line2D.Double(el.getStartPoint(), el.getEndPoint());

            Path2D path = new Path2D.Double();
            path.moveTo(l1.getX1(), l1.getY1());
            path.lineTo(l1.getX2(), l1.getY2());
            path.lineTo(l2.getX2(), l2.getY2());
            path.lineTo(l2.getX1(), l2.getY1());
            path.closePath();
            g2.setPaint(this.fillPaint);
            g2.fill(path);
        }
    }
    
    // TODO : equals and hashcode
    
}
