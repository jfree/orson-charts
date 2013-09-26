/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.axis;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Objects;
import com.orsoncharts.graphics3d.ArgChecks;
import com.orsoncharts.plot.XYZPlot;

/**
 * An axis in 3D space.
 */
public class NumberAxis3D extends AbstractAxis3D implements ValueAxis3D {

    /** The axis range. */
    private Range range;

    private boolean autoAdjustRange;
    
    /** 
     * The default range to apply when there is no data in the dataset.  
     * A sensible default is going to depend on the context, so the user
     * should change it as necessary.
     */
    private Range defaultAutoRange;
    
    private double tickSize;

    private NumberFormat tickFormatter;

    public NumberAxis3D(String label) {
        this(label, new Range(0.0, 1.0));
    }
    
    /**
     * Creates a new axis with the specified range.
     *
     * @param label  the axis label (<code>null</code> permitted).
     * @param range  the range (<code>null</code> not permitted).
     */
    public NumberAxis3D(String label, Range range) {
        super(label);
        this.range = range;
        this.autoAdjustRange = true;
        this.defaultAutoRange = new Range(0.0, 1.0);
        this.tickFormatter = new DecimalFormat("0.00");
        this.tickSize = range.getLength() / 10.0;  // FIXME
    }
  
    /**
     * Returns the axis range.
     * 
     * @return the axis range (never <code>null</code>).
     */
    @Override
    public Range getRange() {
        return this.range;
    }
  
    /**
     * Sets the axis range (bounds) and sends a change event to all registered
     * listeners.
     * 
     * @param range  the new range (<code>null</code> not permitted).
     */
    @Override
    public void setRange(Range range) {
        ArgChecks.nullNotPermitted(range, "range");
        this.range = range;
        fireChangeEvent();
    }
  
    /**
     * Returns the tick size.
     * 
     * @return The tick size.
     */
    public double getTickSize() {
        return this.tickSize;
    }

    /**
     * Sets the tick size and sends a change event to all registered listeners.
     * 
     * @param tickSize  the new tick size.
     */
    public void setTickSize(double tickSize) {
        this.tickSize = tickSize;
        fireChangeEvent();
    }

    @Override
    public void configureAsXAxis(XYZPlot plot) {
        if (this.autoAdjustRange) {
            Range xRange = plot.getRenderer().findXRange(plot.getDataset());
            if (xRange != null) {
                this.range = xRange;
            } else {
                this.range = this.defaultAutoRange;
            }
        }
    }

    @Override
    public void configureAsYAxis(XYZPlot plot) {
        if (this.autoAdjustRange) {
            Range yRange = plot.getRenderer().findYRange(plot.getDataset());
            if (yRange != null) {
                this.range = yRange;
            } else {
                this.range = this.defaultAutoRange;
            }
        }
    }

    @Override
    public void configureAsZAxis(XYZPlot plot) {
        if (this.autoAdjustRange) {
            Range zRange = plot.getRenderer().findZRange(plot.getDataset());
            if (zRange != null) {
                this.range = zRange;
            } else {
                this.range = this.defaultAutoRange;
            }
        }
    }

    /**
     * Renders the axis using the supplied graphics device, with the
     * specified starting and ending points for the line.
     *
     * @param g2
     * @param pt0
     * @param pt1
     * @param opposingPt
     * @param labels
     */
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
        for (int i = 0; i <= 10; i++) {
            Line2D perpLine = createPerpendicularLine(axisLine, 0.1 * i, 10.0, 
                    opposingPt);
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
            double v = range.getMin() + (0.1 * i * range.getLength());

            double perpTheta = calculateTheta(perpLine);  
            TextAnchor textAnchor = TextAnchor.CENTER_LEFT;
            if (Math.abs(perpTheta) > Math.PI / 2.0) {
                textAnchor = TextAnchor.CENTER_RIGHT;
            } 
            TextUtils.drawRotatedString(this.tickFormatter.format(v), g2, 
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
            TextUtils.drawRotatedString(getLabel(), g2, (float) labelPosLine.getX2(), 
                    (float) labelPosLine.getY2(), TextAnchor.CENTER, theta, 
                    TextAnchor.CENTER);
        }
    }

    @Override
    public double translateToWorld(double value, double length) {
        return length * (value - this.range.getMin()) / this.range.getLength();
    }
  
    /**
     * Tests this instance for equality with an arbitrary object.
     * 
     * @param obj  the object to test against (<code>null</code> permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof NumberAxis3D)) {
            return false;
        }
        NumberAxis3D that = (NumberAxis3D) obj;
        if (!this.range.equals(that.range)) {
            return false;
        }
        if (this.tickSize != that.tickSize) {
            return false;
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.range);
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.tickSize) 
                ^ (Double.doubleToLongBits(this.tickSize) >>> 32));
        hash = 59 * hash + Objects.hashCode(this.tickFormatter);
        return hash;
    }

}
