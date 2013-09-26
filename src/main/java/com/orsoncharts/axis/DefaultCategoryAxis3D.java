/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.axis;

import com.orsoncharts.data.CategoryDataset3D;
import com.orsoncharts.graphics3d.ArgChecks;
import com.orsoncharts.plot.CategoryPlot3D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * An axis that displays categories.
 */
public class DefaultCategoryAxis3D extends AbstractAxis3D 
        implements CategoryAxis3D {

    private List<Comparable> categories;
  
    /** The axis range. */
    private Range range;

    /** The percentage margin to leave at the lower end of the axis. */
    private double lowerMargin;
    
    /** The percentage margin to leave at the upper end of the axis. */
    private double upperMargin;

    /** 
     * Hide half of the first category?  This brings the category label 
     * closer to the beginning of the axis.  It is useful if the renderer 
     * doesn't make full use of the category space for the first item.
     */
    private boolean hideFirstCategoryHalf = false;
    
    /** 
     * Hide half of the last category?  This brings the category label 
     * closer to the end of the axis.  It is useful if the renderer 
     * doesn't make full use of the category space for the last item.
     */
    private boolean hideLastCategoryHalf = false;

    /**
     * Creates a new axis with the specified label.
     * 
     * @param label  the axis label (<code>null</code> permitted). 
     */
    public DefaultCategoryAxis3D(String label) {
        super(label);
        this.categories = new ArrayList<Comparable>();
        this.range = new Range(0, 1);
        this.lowerMargin = 0.05;
        this.upperMargin = 0.05;
        this.hideFirstCategoryHalf = false;
        this.hideLastCategoryHalf = false;
    }

    @Override
    public void configureAsRowAxis(CategoryPlot3D plot) {
        this.categories = plot.getDataset().getRowKeys();
    }

    @Override
    public void configureAsColumnAxis(CategoryPlot3D plot) {
        this.categories = plot.getDataset().getColumnKeys();
    }

    public void setCategories(List<Comparable> categories) {
        this.categories = new ArrayList<Comparable>(categories);
        fireChangeEvent();
    }
    
//    // FIXME: scaffolding method
//    public void setCategoryLabel(Comparable key, double value) {
//        labels.put(key, Double.valueOf(value));
//    }
  
    /**
     * Returns the range for the axis.  By convention, the range is normally
     * 0 to N + 1 (where N is the number of categories in the dataset).  This 
     * way, the data items can be centered on 1, 2, ... N.
     * 
     * @return The range. 
     */
    @Override
    public Range getRange() {
        return this.range;
    }

    /**
     * Sets the range for the axis.
     * 
     * @param lowerBound  the lower bound.
     * @param upperBound  the upper bound.
     */
    public void setRange(double lowerBound, double upperBound) {
        setRange(new Range(lowerBound, upperBound));
    }

    /**
     * Sets the range for the axis and sends an {@link Axis3DChangeEvent} to
     * all registered listeners.
     * 
     * @param range  the range (<code>null</code> not permitted). 
     */
    @Override
    public void setRange(Range range) {
        ArgChecks.nullNotPermitted(range, "range");
        this.range = range;
        fireChangeEvent();
    }

    /**
     * Returns the value for the specified category, or <code>Double.NaN</code>
     * if the category is not registered on the axis.
     * 
     * @param category  the category (<code>null</code> not permitted).
     * 
     * @return 
     */
    public double getCategoryValue(Comparable category) {
        int index = this.categories.indexOf(category);
        if (index < 0) {
            return Double.NaN;
        }
        double length = this.range.getLength();
        double start = this.range.getMin() + (this.lowerMargin * length);
        double end = this.range.getMax() - (this.upperMargin * length);
        double available = (end - start);
        double categoryWidth = available / this.categories.size();
        return start + (0.5 + index) * categoryWidth;
    }
    
    @Override
    public double translateToWorld(double value, double length) {
        return length * (value - this.range.getMin()) / this.range.getLength();
    }

    @Override
    public void render(Graphics2D g2, Point2D pt0, Point2D pt1, 
            Point2D opposingPt, boolean labels) {
        g2.setStroke(getLineStroke());
        g2.setPaint(getLineColor());
        Line2D axisLine = new Line2D.Float(pt0, pt1);
        g2.draw(axisLine);

        // now draw a small black line perpendicular to the axis - the aim is to
        // point to the side where the text labels will be displayed
        // we could do this by assuming that the diagonally opposite
        // line segment in the cube is on the "inside" of the chart
        g2.setFont(getTickLabelFont());
        for (Comparable key : this.categories) {
            double d = getCategoryValue(key);
            double p = getRange().percent(d);
            Line2D perpLine = createPerpendicularLine(axisLine, p, 10.0, opposingPt);
            g2.setPaint(Color.BLACK);
            g2.setStroke(new BasicStroke(1f));
            g2.draw(perpLine);
      
            double theta = calculateTheta(axisLine);
            double thetaAdj = theta + Math.PI / 2.0;
            if (thetaAdj < -Math.PI / 2.0) {
                thetaAdj = thetaAdj + Math.PI;
            }
            if (thetaAdj > Math.PI / 2.0) {
                thetaAdj = thetaAdj - Math.PI;
            }

            double perpTheta = calculateTheta(perpLine);
            TextAnchor textAnchor = TextAnchor.CENTER_LEFT;
            if (Math.abs(perpTheta) > Math.PI / 2.0) {
                textAnchor = TextAnchor.CENTER_RIGHT;
            } 
            TextUtils.drawRotatedString(key.toString(), g2, 
                (float) perpLine.getX2(), (float) perpLine.getY2(), textAnchor,
                thetaAdj, textAnchor);
        }

        if (getLabel() != null) {
            g2.setFont(getLabelFont());
            Line2D labelPosLine = createPerpendicularLine(axisLine, 0.5, 60.0, 
                opposingPt);
            double theta = calculateTheta(axisLine);
            if (theta < -Math.PI / 2.0) {
                theta = theta + Math.PI;
            }
            if (theta > Math.PI / 2.0) {
                theta = theta - Math.PI;
            }
            TextUtils.drawRotatedString(getLabel(), g2, 
                    (float) labelPosLine.getX2(), (float) labelPosLine.getY2(), 
                    TextAnchor.CENTER, theta, TextAnchor.CENTER);
        }
    }

}
