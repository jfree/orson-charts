/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */
package com.orsoncharts.renderer.xyz;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.orsoncharts.Range;
import com.orsoncharts.axis.ValueAxis3D;
import com.orsoncharts.data.function.Function3D;
import com.orsoncharts.data.function.Function3DUtils;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.Object3D;
import com.orsoncharts.graphics3d.Point3D;
import com.orsoncharts.graphics3d.World;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.renderer.ColorScale;
import com.orsoncharts.renderer.ColorScaleRenderer;
import com.orsoncharts.renderer.ComposeType;
import com.orsoncharts.renderer.FixedColorScale;
import com.orsoncharts.renderer.Renderer3DChangeEvent;
import com.orsoncharts.util.ArgChecks;

/**
 * A renderer that plots a surface based on a function (any implementation
 * of {@link Function3D}).  This renderer is different to others in that it
 * does not plot data from a dataset, instead it samples a function and plots
 * those values.  By default 900 samples are taken (30 x-values by 30 z-values)
 * although this can be modified.
 * <br><br>
 * For the fastest rendering, the <code>drawFaceOutlines</code> flag can be set 
 * to <code>false</code> (the default is <code>true</code>) but this may 
 * cause slight rendering artifacts if anti-aliasing is on (note that switching
 * off anti-aliasing as well also improves rendering performance).
 * 
 * @since 1.1
 */
public class SurfaceRenderer extends AbstractXYZRenderer implements XYZRenderer,
        ColorScaleRenderer, Serializable {
    
    /** The function. */
    private Function3D function;

    /** The number of samples along the x-axis (minimum 2). */
    private int xSamples;
    
    /** The number of samples along the z-axis (minimum 2). */
    private int zSamples;
    
    /** The color scale. */
    private ColorScale colorScale;
    
    /** 
     * A flag that controls whether the faces that make up the surface have
     * their outlines drawn (in addition to the shape being filled).  The
     * default value is <code>true</code> which renders a solid surface but
     * is slower.
     */
    private boolean drawFaceOutlines;
    
    /**
     * Creates a new renderer for the specified function.  By default, the 
     * renderer will take 30 samples along the x-axis and 30 samples along the 
     * z-axis (this is configurable).
     * 
     * @param function  the function (<code>null</code> not permitted). 
     */
    public SurfaceRenderer(Function3D function) {
        ArgChecks.nullNotPermitted(function, "function");
        this.function = function;
        this.xSamples = 30;
        this.zSamples = 30;
        this.colorScale = new FixedColorScale(Color.YELLOW);
        this.drawFaceOutlines = true;
    }
    
    /**
     * Returns the number of samples the renderer will take along the
     * x-axis when plotting the function.  The default value is 30.
     * 
     * @return The number of samples. 
     */
    public int getXSamples() {
        return this.xSamples;
    }
    
    /**
     * Sets the number of samples the renderer will take along the x-axis when
     * plotting the function and sends a {@link Renderer3DChangeEvent} to all
     * registered listeners.  The default value is 30, setting this to higher
     * values will result in smoother looking plots, but they will take
     * longer to draw.
     * 
     * @param count  the count. 
     * 
     * @see #setZSamples(int) 
     */
    public void setXSamples(int count) {
        this.xSamples = count;
        fireChangeEvent();
    }
    
    /**
     * Returns the number of samples the renderer will take along the z-axis
     * when plotting the function and sends a {@link Renderer3DChangeEvent} to
     * all registered listeners.  The default value is 30.
     * 
     * @return The number of samples.
     */
    public int getZSamples() {
        return this.zSamples;
    }
    
    /**
     * Sets the number of samples the renderer will take along the z-axis when
     * plotting the function and sends a {@link Renderer3DChangeEvent} to all
     * registered listeners.  The default value is 30, setting this to higher
     * values will result in smoother looking plots, but they will take
     * longer to draw.
     * 
     * @param count  the count. 
     * 
     * @see #setXSamples(int) 
     */
    public void setZSamples(int count) {
        this.zSamples = count;
    }
    
    /**
     * Returns the compose-type for the renderer.  Here the value is
     * <code>ComposeType.ALL</code> which means the plot will call the 
     * {@link #composeAll(com.orsoncharts.plot.XYZPlot, 
     * com.orsoncharts.graphics3d.World, com.orsoncharts.graphics3d.Dimension3D,
     * double, double, double)} method for composing the chart.
     * 
     * @return The compose type (never <code>null</code>). 
     */
    @Override
    public ComposeType getComposeType() {
        return ComposeType.ALL;
    }
    
    /**
     * Returns the color scale.  This determines the color of the surface
     * according to the y-value.
     * 
     * @return The color scale (never <code>null</code>). 
     */
    @Override
    public ColorScale getColorScale() {
        return this.colorScale;
    }
    
    /**
     * Sets the color scale and sends a {@link Renderer3DChangeEvent} to all 
     * registered listeners.
     * 
     * @param colorScale  the color scale (<code>null</code> not permitted). 
     */
    public void setColorScale(ColorScale colorScale) {
        ArgChecks.nullNotPermitted(colorScale, "colorScale");
        this.colorScale = colorScale;
        fireChangeEvent();
    }
    
    /**
     * Returns the flag that controls whether or not the faces that make
     * up the surface have their outlines drawn during rendering.  The
     * default value is <code>true</code>.  
     * 
     * @return A boolean. 
     */
    public boolean getDrawFaceOutlines() {
        return this.drawFaceOutlines;
    }
    
    /**
     * Sets a flag that controls whether or not the faces that make up the 
     * surface are drawn (as well as filled) and sends a 
     * {@link Renderer3DChangeEvent} to all registered listeners.  If the face 
     * outlines are drawn (the default), the surface is solid (but takes longer
     * to draw).  If the face outlines are not drawn, Java2D can leave small 
     * gaps that you can "see" through (if you don't mind this, then the
     * performance is better).
     * 
     * @param draw  the new flag value. 
     */
    public void setDrawFaceOutlines(boolean draw) {
        this.drawFaceOutlines = draw;
        fireChangeEvent();
    }
    
    /**
     * Composes the entire representation of the function in the supplied
     * <code>world</code>.
     * 
     * @param plot  the plot.
     * @param world  the world.
     * @param dimensions  the plot dimensions.
     * @param xOffset  the x-offset.
     * @param yOffset  the y-offset.
     * @param zOffset  the z-offset.
     */
    @Override
    public void composeAll(XYZPlot plot, World world, Dimension3D dimensions, 
            double xOffset, double yOffset, double zOffset) {
        
        // need to know the x-axis range and the z-axis range
        ValueAxis3D xAxis = plot.getXAxis();
        ValueAxis3D yAxis = plot.getYAxis();
        ValueAxis3D zAxis = plot.getZAxis();
        Dimension3D dim = plot.getDimensions();
        double xlen = dim.getWidth();
        double ylen = dim.getHeight();
        double zlen = dim.getDepth();
        Range yRange = new Range(yOffset, -yOffset);
        for (int xIndex = 0; xIndex < this.xSamples; xIndex++) {
            double xfrac0 = xIndex / (double) this.xSamples;
            double xfrac1 = (xIndex + 1) / (double) this.xSamples;
            for (int zIndex = 0; zIndex < this.zSamples; zIndex++) {
                double zfrac0 = zIndex / (double) this.zSamples;
                double zfrac1 = (zIndex + 1) / (double) this.zSamples;
                
                double x0 = xAxis.getRange().value(xfrac0);
                double x1 = xAxis.getRange().value(xfrac1);
                double xm = x0 / 2.0 + x1 / 2.0;
                double z0 = zAxis.getRange().value(zfrac0);
                double z1 = zAxis.getRange().value(zfrac1);
                double zm = z0 / 2.0 + z1 / 2.0;
                double y00 = this.function.getValue(x0, z0);
                double y01 = this.function.getValue(x0, z1);
                double y10 = this.function.getValue(x1, z0);
                double y11 = this.function.getValue(x1, z1);
                double ymm = this.function.getValue(xm, zm);
                
                double wx0 = xAxis.translateToWorld(x0, xlen) + xOffset;
                double wx1 = xAxis.translateToWorld(x1, xlen) + xOffset;
                double wy00 = yAxis.translateToWorld(y00, ylen) + yOffset;
                double wy01 = yAxis.translateToWorld(y01, ylen) + yOffset;
                double wy10 = yAxis.translateToWorld(y10, ylen) + yOffset;
                double wy11 = yAxis.translateToWorld(y11, ylen) + yOffset;
                double wz0 = zAxis.translateToWorld(z0, zlen) + zOffset;
                double wz1 = zAxis.translateToWorld(z1, zlen) + zOffset;

                Color color = this.colorScale.valueToColor(ymm);
                Object3D obj = new Object3D();
                List<Point3D> pts1 = facePoints1(wx0, wx1, wz0, wz1, wy00, wy01, 
                        wy11, yRange);
                int count1 = pts1.size();
                for (Point3D pt : pts1) {
                    obj.addVertex(pt);
                }
                if (count1 == 3) {
                    obj.addDoubleSidedFace(new int[] {0, 1, 2}, color, 
                            this.drawFaceOutlines);
                } else if (count1 == 4) {
                    obj.addDoubleSidedFace(new int[] {0, 1, 2, 3}, color, 
                            this.drawFaceOutlines);
                } else if (count1 == 5) {
                    obj.addDoubleSidedFace(new int[] {0, 1, 2, 3, 4}, color, 
                            this.drawFaceOutlines);
                }
                List<Point3D> pts2 = facePoints2(wx0, wx1, wz0, wz1, wy00, wy11,
                        wy10, yRange);
                int count2 = pts2.size();
                for (Point3D pt : pts2) {
                    obj.addVertex(pt);
                }
                if (count2 == 3) {
                    obj.addDoubleSidedFace(new int[] {count1, count1 + 1, 
                        count1 + 2}, color, this.drawFaceOutlines);
                } else if (count2 == 4) {
                    obj.addDoubleSidedFace(new int[] {count1, count1 + 1, 
                        count1 + 2, count1 + 3}, color, this.drawFaceOutlines);
                } else if (count2 == 5) {
                    obj.addDoubleSidedFace(new int[] {count1, count1 + 1, 
                        count1 + 2, count1 + 3, count1 + 4}, color, 
                        this.drawFaceOutlines);                    
                }
                world.add(obj);
            }
            
        }        
    }
    
    private Point3D intersectPoint(double x0, double y0, double z0, double x1, 
                double y1, double z1, double yy) {
        double p = (yy - y0) / (y1 - y0);
        double x = x0 + p * (x1 - x0);
        double y = y0 + p * (y1 - y0);
        double z = z0 + p * (z1 - z0);
        return new Point3D(x, y, z);
    }
    
    private List<Point3D> facePoints1(double x0, double x1, double z0, 
            double z1, double y00, double y01, double y11, Range yRange) {
        
        List<Point3D> pts = new ArrayList<Point3D>(4);
        double ymin = yRange.getMin();
        double ymax = yRange.getMax();
        
        // handle y00
        if (y00 > yRange.getMax()) {
            if (yRange.contains(y01)) {
                pts.add(intersectPoint(x0, y00, z0, x0, y01, z1, ymax));
            } else if (y01 < yRange.getMin()) {
                pts.add(intersectPoint(x0, y00, z0, x0, y01, z1, ymax));
                pts.add(intersectPoint(x0, y00, z0, x0, y01, z1, ymin));
            }
        } else if (yRange.contains(y00)) {
            pts.add(new Point3D(x0, y00, z0));
            if (y01 > yRange.getMax()) {
                pts.add(intersectPoint(x0, y00, z0, x0, y01, z1, ymax));
            } else if (y01 < yRange.getMin()) {
                pts.add(intersectPoint(x0, y00, z0, x0, y01, z1, ymin));
            }
        } else { // below the range
            if (yRange.contains(y01)) {
                pts.add(intersectPoint(x0, y00, z0, x0, y01, z1, ymin));
            } else if (y01 > yRange.getMax()) {
                pts.add(intersectPoint(x0, y00, z0, x0, y01, z1, ymin));
                pts.add(intersectPoint(x0, y00, z0, x0, y01, z1, ymax));
            }
        }
        
        // handle y01
        if (y01 > yRange.getMax()) {
            if (yRange.contains(y11)) {
                pts.add(intersectPoint(x0, y01, z1, x1, y11, z1, ymax));
            } else if (y11 < yRange.getMin()) {
                pts.add(intersectPoint(x0, y01, z1, x1, y11, z1, ymax));
                pts.add(intersectPoint(x0, y01, z1, x1, y11, z1, ymin));
            }
        } else if (yRange.contains(y01)) {
            pts.add(new Point3D(x0, y01, z1));
            if (y11 > yRange.getMax()) {
                pts.add(intersectPoint(x0, y01, z1, x1, y11, z1, ymax));                
            } else if (y11 < yRange.getMin()) {
                pts.add(intersectPoint(x0, y01, z1, x1, y11, z1, ymin));         
            }
        } else {
            if (y11 > yRange.getMax()) {
                pts.add(intersectPoint(x0, y01, z1, x1, y11, z1, ymin));
                pts.add(intersectPoint(x0, y01, z1, x1, y11, z1, ymax));
            } else if (yRange.contains(y11)) {
                pts.add(intersectPoint(x0, y01, z1, x1, y11, z1, ymin));                
            }
        }
        
        // handle y11
        if (y11 > yRange.getMax()) {
            if (yRange.contains(y00)) {
                pts.add(intersectPoint(x1, y11, z1, x0, y00, z0, ymax));                
            } else if (y00 < yRange.getMin()) {
                pts.add(intersectPoint(x1, y11, z1, x0, y00, z0, ymax));                
                pts.add(intersectPoint(x1, y11, z1, x0, y00, z0, ymin));
            }
        } else if (yRange.contains(y11)) {
            pts.add(new Point3D(x1, y11, z1));
            if (y00 > yRange.getMax()) {
                pts.add(intersectPoint(x1, y11, z1, x0, y00, z0, ymax));                
            } else if (y00 < yRange.getMin()) {
                pts.add(intersectPoint(x1, y11, z1, x0, y00, z0, ymin));                
            }
        } else {
            if (y00 > yRange.getMax()) {
                pts.add(intersectPoint(x1, y11, z1, x0, y00, z0, ymin));                
                pts.add(intersectPoint(x1, y11, z1, x0, y00, z0, ymax));                
            } else if (yRange.contains(y00)) {
                pts.add(intersectPoint(x1, y11, z1, x0, y00, z0, ymin));                
            }
        }
        return pts;
    }
    
    private List<Point3D> facePoints2(double x0, double x1, double z0, 
            double z1, double y00, double y11, double y10, Range yRange) {
        
        List<Point3D> pts = new ArrayList<Point3D>(4);
        double ymin = yRange.getMin();
        double ymax = yRange.getMax();        
        // handle y00
        if (y00 > yRange.getMax()) {
            if (yRange.contains(y11)) {
                pts.add(intersectPoint(x0, y00, z0, x1, y11, z1, ymax));
            } else if (y11 < yRange.getMin()) {
                pts.add(intersectPoint(x0, y00, z0, x1, y11, z1, ymax));
                pts.add(intersectPoint(x0, y00, z0, x1, y11, z1, ymin));
            }
        } else if (yRange.contains(y00)) {
            pts.add(new Point3D(x0, y00, z0));
            if (y11 > yRange.getMax()) {
                pts.add(intersectPoint(x0, y00, z0, x1, y11, z1, ymax));
            } else if (y11 < yRange.getMin()) {
                pts.add(intersectPoint(x0, y00, z0, x1, y11, z1, ymin));
            }
        } else { // below the range
            if (yRange.contains(y11)) {
                pts.add(intersectPoint(x0, y00, z0, x1, y11, z1, ymin));
            } else if (y11 > yRange.getMax()) {
                pts.add(intersectPoint(x0, y00, z0, x1, y11, z1, ymin));
                pts.add(intersectPoint(x0, y00, z0, x1, y11, z1, ymax));
            }
        }
        
        // handle y11
        if (y11 > yRange.getMax()) {
            if (yRange.contains(y10)) {
                pts.add(intersectPoint(x1, y11, z1, x1, y10, z0, ymax));
            } else if (y10 < yRange.getMin()) {
                pts.add(intersectPoint(x1, y11, z1, x1, y10, z0, ymax));
                pts.add(intersectPoint(x1, y11, z1, x1, y10, z0, ymin));
            }
        } else if (yRange.contains(y11)) {
            pts.add(new Point3D(x1, y11, z1));
            if (y10 > yRange.getMax()) {
                pts.add(intersectPoint(x1, y11, z1, x1, y10, z0, ymax));                
            } else if (y10 < yRange.getMin()) {
                pts.add(intersectPoint(x1, y11, z1, x1, y10, z0, ymin));         
            }
        } else {
            if (y10 > yRange.getMax()) {
                pts.add(intersectPoint(x1, y11, z1, x1, y10, z0, ymin));
                pts.add(intersectPoint(x1, y11, z1, x1, y10, z0, ymax));
            } else if (yRange.contains(y10)) {
                pts.add(intersectPoint(x1, y11, z1, x1, y10, z0, ymin));                
            }
        }
        
        // handle y10
        if (y10 > yRange.getMax()) {
            if (yRange.contains(y00)) {
                pts.add(intersectPoint(x1, y10, z0, x0, y00, z0, ymax));                
            } else if (y00 < yRange.getMin()) {
                pts.add(intersectPoint(x1, y10, z0, x0, y00, z0, ymax));                
                pts.add(intersectPoint(x1, y10, z0, x0, y00, z0, ymin));
            }
        } else if (yRange.contains(y10)) {
            pts.add(new Point3D(x1, y10, z0));
            if (y00 > yRange.getMax()) {
                pts.add(intersectPoint(x1, y10, z0, x0, y00, z0, ymax));                
            } else if (y00 < yRange.getMin()) {
                pts.add(intersectPoint(x1, y10, z0, x0, y00, z0, ymin));                
            }
        } else {
            if (y00 > yRange.getMax()) {
                pts.add(intersectPoint(x1, y10, z0, x0, y00, z0, ymin));                
                pts.add(intersectPoint(x1, y10, z0, x0, y00, z0, ymax));                
            } else if (yRange.contains(y00)) {
                pts.add(intersectPoint(x1, y10, z0, x0, y00, z0, ymin));                
            }
        }
        
        return pts;
    }

    /**
     * Throws an <code>UnsupportedOperationException</code> because this 
     * renderer does not support per-item rendering.
     * 
     * @param dataset the dataset (<code>null</code> not permitted).
     * @param series  the series index.
     * @param item  the item index.
     * @param world  the world (<code>null</code> not permitted).
     * @param dimensions  the dimensions (<code>null</code> not permitted).
     * @param xOffset  the x-offset.
     * @param yOffset  the y-offset.
     * @param zOffset  the z-offset.
     */
    @Override
    public void composeItem(XYZDataset dataset, int series, int item, 
            World world, Dimension3D dimensions, double xOffset, 
            double yOffset, double zOffset) {
        throw new UnsupportedOperationException(
                "Not supported by this renderer.");
    }
    
    /**
     * Returns the current range for the x-axis - the method is overridden 
     * because this renderer does not use a dataset (it samples and plots a
     * function directly).
     * 
     * @param dataset  the dataset (ignored).
     * 
     * @return The x-range (never <code>null</code>). 
     */
    @Override
    public Range findXRange(XYZDataset dataset) {
        return getPlot().getXAxis().getRange();
    }

    /**
     * Returns the range that the renderer requires on the y-axis to display
     * all the data in the function.
     * 
     * @param dataset  the dataset (ignored).
     * 
     * @return The range. 
     */
    @Override
    public Range findYRange(XYZDataset dataset) {
        return Function3DUtils.findYRange(this.function, 
                getPlot().getXAxis().getRange(), 
                getPlot().getZAxis().getRange(), 
                this.xSamples, this.zSamples, true);
    }

    /**
     * Returns the current range for the x-axis - the method is overridden 
     * because this renderer does not use a dataset (it samples and plots a
     * function directly).
     * 
     * @param dataset  the dataset (ignored).
     * 
     * @return The x-range (never <code>null</code>). 
     */
    @Override
    public Range findZRange(XYZDataset dataset) {
        return getPlot().getXAxis().getRange();
    }

    /**
     * Tests this renderer for equality with an arbitrary object.
     * 
     * @param obj  the object (<code>null</code> not permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SurfaceRenderer)) {
            return false;
        }
        SurfaceRenderer that = (SurfaceRenderer) obj;
        if (!this.function.equals(that.function)) {
            return false;
        }
        if (this.xSamples != that.xSamples) {
            return false;
        }
        if (this.zSamples != that.zSamples) {
            return false;
        }
        if (!this.colorScale.equals(that.colorScale)) {
            return false;
        }
        if (this.drawFaceOutlines != that.drawFaceOutlines) {
            return false;
        }
        return super.equals(obj);
    }
}
