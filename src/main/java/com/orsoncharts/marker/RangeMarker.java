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
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import com.orsoncharts.Range;

/**
 * A marker that marks a range of values on an axis.
 * 
 * @since 1.2
 */
public class RangeMarker extends AbstractMarker implements ValueMarker {
    
    private NumberMarker start;
    
    private NumberMarker end;
    
    // String label 
    // Font
    // foreground paint
    // background paint
    // label anchor (within the rectangle, nine possibilities)
    
    // note that the lower and upper markers have their own labels, so the
    // RangeMarker will have the possibility of three labels
    Paint fillPaint;
    
    public RangeMarker(double lowerBound, double upperBound) {
        super();
        this.start = new NumberMarker(lowerBound);
        // TODO: add a listener
        this.end = new NumberMarker(upperBound);
        // TODO: add a listener
        this.fillPaint = DEFAULT_FILL_PAINT;
    }
    
    /**
     * Returns the starting point for the range marker.
     * 
     * @return The starting point.
     */
    public NumberMarker getStart() {
        return this.start;
    }
    
    /**
     * Returns the ending point for the range marker.
     * @return 
     */
    public NumberMarker getEnd() {
        return this.end;
    }
    
    /**
     * Returns the range of values for the marker.
     * 
     * @return The range of values for the marker.
     */
    @Override
    public Range getRange() {
        return new Range(this.start.getValue(), this.end.getValue());
    }

    // FIXME: to be completed!!

    @Override
    public void draw(Graphics2D g2, MarkerData markerData) {
        MarkerLine startLine = markerData.getStartLine();
        Line2D l1 = new Line2D.Double(startLine.getStartPoint(), startLine.getEndPoint());
        MarkerLine endLine = markerData.getEndLine();
        Line2D l2 = new Line2D.Double(endLine.getStartPoint(), endLine.getEndPoint());

        Path2D path = new Path2D.Double();
        path.moveTo(l1.getX1(), l1.getY1());
        path.lineTo(l1.getX2(), l1.getY2());
        path.lineTo(l2.getX2(), l2.getY2());
        path.lineTo(l2.getX1(), l2.getY1());
        path.closePath();
        g2.setPaint(this.fillPaint);
        g2.fill(path);

        // TODO: only draw the lines if the range is not truncated
        g2.setPaint(this.start.getPaint());
        g2.setStroke(this.start.getStroke());
        g2.draw(l1);
        g2.setPaint(this.end.getPaint());
        g2.setStroke(this.end.getStroke());
        g2.draw(l2);
        
        
        System.out.println("draw rangeMarker");
    }

    
}
