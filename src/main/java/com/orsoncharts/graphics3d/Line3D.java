/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-2016, by Object Refinery Limited.  All rights reserved.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
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
 * commercial license can be purchased.  For details, please see visit the
 * Orson Charts home page:
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 */

package com.orsoncharts.graphics3d;

import com.orsoncharts.util.ArgChecks;

/**
 * A line segment in 3D space.
 * 
 * @since 1.5
 */
public class Line3D {
    
    private Point3D start;
    
    private Point3D end;
    
    /**
     * Creates a new line in 3D space.
     * 
     * @param start  the starting point ({@code null} not permitted).
     * @param end  the ending point ({@code null} not permitted).
     */
    public Line3D(Point3D start, Point3D end) {
        ArgChecks.nullNotPermitted(start, "start");
        ArgChecks.nullNotPermitted(end, "end");
        this.start = start;
        this.end = end;
    }
  
    /**
     * Creates a new line in 3D space between the points {@code (x0, y0, z0)} 
     * and {@code (x1, y1, z1)}.
     * 
     * @param x0  the x-coordinate for the line's start point.
     * @param y0  the y-coordinate for the line's start point.
     * @param z0  the z-coordinate for the line's start point.
     * @param x1  the x-coordinate for the line's end point.
     * @param y1  the y-coordinate for the line's end point.
     * @param z1  the z-coordinate for the line's end point.
     */
    public Line3D(double x0, double y0, double z0, double x1, double y1, 
            double z1) {
        this.start = new Point3D(x0, y0, z0);
        this.end = new Point3D(x1, y1, z1);
    }

    /**
     * Returns the starting point for the line.
     * 
     * @return The starting point (never {@code null}). 
     */
    public Point3D getStart() {
        return this.start;
    }
    
    /**
     * Returns the ending point for the line.
     * 
     * @return The ending point (never {@code null}). 
     */
    public Point3D getEnd() {
        return this.end;
    } 

    /**
     * Calculates and returns the line segment that is the result of cropping
     * the specified line segment to fit within an axis aligned bounding box.
     * 
     * @param line  the original line segment ({@code null} not permitted).
     * @param x0  the lower x-bound.
     * @param x1  the upper x-bound.
     * @param y0  the lower y-bound.
     * @param y1  the upper y-bound.
     * @param z0  the lower z-bound.
     * @param z1  the upper z-bound.
     * 
     * @return The cropped line segment (or {@code null} if the original line
     *     segment falls outside the bounding box). 
     */
    public static Line3D cropLineToAxisAlignedBoundingBox(Line3D line, 
            double x0, double x1, double y0, double y1, double z0, double z1) {

        // we need this method to be fast, since it will get called a lot of
        // times during line chart rendering...there may be a faster method
        // than what is implemented below, in which case we should use it
        double xx0 = line.getStart().getX();
        double xx1 = line.getEnd().getX();
        int kx0 = 2;
        if (xx0 < x0) {
            kx0 = 1;
        } else if (xx0 > x1) {
            kx0 = 4;
        }
        int kx1 = 2;
        if (xx1 < x0) {
            kx1 = 1;
        } else if (xx1 > x1) {
            kx1 = 4;
        }
        if ((kx0 | kx1) == 1 || (kx0 | kx1) == 4) {
            return null;
        }
        
        double yy0 = line.getStart().getY();
        double yy1 = line.getEnd().getY();
        int ky0 = 2;
        if (yy0 < y0) {
            ky0 = 1;
        } else if (yy0 > y1) {
            ky0 = 4;
        }
        int ky1 = 2;
        if (yy1 < y0) {
            ky1 = 1;
        } else if (yy1 > y1) {
            ky1 = 4;
        }
        if ((ky0 | ky1) == 1 || (ky0 | ky1) == 4) {
            return null;
        }
        
        double zz0 = line.getStart().getZ();
        double zz1 = line.getEnd().getZ();
        int kz0 = 2;
        if (zz0 < z0) {
            kz0 = 1;
        } else if (zz0 > z1) {
            kz0 = 4;
        }
        int kz1 = 2;
        if (zz1 < z0) {
            kz1 = 1;
        } else if (zz1 > z1) {
            kz1 = 4;
        }
        if ((kz0 | kz1) == 1 || (kz0 | kz1) == 4) {
            return null;
        }
        
        // if the X's, Y's and Z's are all inside, just return the line
        if ((kx0 | kx1 | ky0 | ky1 | kz0 | kz1) == 2) {
            return line;
        }
        
        // now we know we have to do some clipping
        Point3D firstValidPoint = null;
        if (isPoint3DInBoundingBox(line.getStart(), x0, x1, y0, y1, z0, z1)) {
            firstValidPoint = line.getStart();
        }
        if (isPoint3DInBoundingBox(line.getEnd(), x0, x1, y0, y1, z0, z1)) {
            firstValidPoint = line.getEnd();
        }
        if (((kx0 | kx1) & 1) != 0) { // X's span the lower bound
            Point3D p = projectLineOnX(line, x0);
            if (isPoint3DInBoundingBox(p, x0, x1, y0, y1, z0, z1)) {
                if (firstValidPoint == null) {
                    firstValidPoint = p;
                } else {
                    return new Line3D(firstValidPoint, p);
                }
            }            
        }
        if (((kx0 | kx1) & 4) != 0) { // X's span the upper bound
            Point3D p = projectLineOnX(line, x1);
            if (isPoint3DInBoundingBox(p, x0, x1, y0, y1, z0, z1)) {
                if (firstValidPoint == null) {
                    firstValidPoint = p;
                } else {
                    return new Line3D(firstValidPoint, p);
                }
            }            
        }

        if (((ky0 | ky1) & 1) != 0) { // Y's span the lower bound
            Point3D p = projectLineOnY(line, y0);
            if (isPoint3DInBoundingBox(p, x0, x1, y0, y1, z0, z1)) {
                if (firstValidPoint == null) {
                    firstValidPoint = p;
                } else {
                    return new Line3D(firstValidPoint, p);
                }
            }            
        }
        if (((ky0 | ky1) & 4) != 0) { // Y's span the upper bound
            Point3D p = projectLineOnY(line, y1);
            if (isPoint3DInBoundingBox(p, x0, x1, y0, y1, z0, z1)) {
                if (firstValidPoint == null) {
                    firstValidPoint = p;
                } else {
                    return new Line3D(firstValidPoint, p);
                }
            }            
        }
        if (((kz0 | kz1) & 1) != 0) { // X's span the lower bound
            Point3D p = projectLineOnZ(line, z0);
            if (isPoint3DInBoundingBox(p, x0, x1, y0, y1, z0, z1)) {
                if (firstValidPoint == null) {
                    firstValidPoint = p;
                } else {
                    return new Line3D(firstValidPoint, p);
                }
            }            
        }
        if (((kz0 | kz1) & 4) != 0) { // X's span the upper bound
            Point3D p = projectLineOnZ(line, z1);
            if (isPoint3DInBoundingBox(p, x0, x1, y0, y1, z0, z1)) {
                if (firstValidPoint == null) {
                    firstValidPoint = p;
                } else {
                    return new Line3D(firstValidPoint, p);
                }
            }            
        }
        return null;
    }
    
    private static Point3D projectLineOnX(Line3D line, double x) {
        double x0 = line.getStart().getX();
        double x1 = line.getEnd().getX();
        double factor = 0.0;
        if (Math.abs(x1 - x0) > 0) {
            factor = (x - x0) / (x1 - x0);
        }
        if (factor >= 1.0) {
            return line.getEnd();
        } else if (factor <= 0.0) {
            return line.getStart();
        } else {
            double y0 = line.getStart().getY();
            double y1 = line.getEnd().getY();
            double z0 = line.getStart().getZ();
            double z1 = line.getEnd().getZ();
            return new Point3D(x0 + factor * (x1 - x0), y0 + factor * (y1 - y0),
                z0 + factor * (z1 - z0));
        }
    }
    
    private static Point3D projectLineOnY(Line3D line, double y) {
        double y0 = line.getStart().getY();
        double y1 = line.getEnd().getY();
        double factor = 0.0;
        if (Math.abs(y1 - y0) > 0) {
            factor = (y - y0) / (y1 - y0);
        }
        if (factor >= 1.0) {
            return line.getEnd();
        } else if (factor <= 0.0) {
            return line.getStart();
        } else {
            double x0 = line.getStart().getX();
            double x1 = line.getEnd().getX();
            double z0 = line.getStart().getZ();
            double z1 = line.getEnd().getZ();
            return new Point3D(x0 + factor * (x1 - x0), y0 + factor * (y1 - y0),
                z0 + factor * (z1 - z0));
        }
    }
    
    private static Point3D projectLineOnZ(Line3D line, double z) {
        double z0 = line.getStart().getZ();
        double z1 = line.getEnd().getZ();
        double factor = 0.0;
        if (Math.abs(z1 - z0) > 0) {
            factor = (z - z0) / (z1 - z0);
        }
        if (factor >= 1.0) {
            return line.getEnd();
        } else if (factor <= 0.0) {
            return line.getStart();
        } else {
            double x0 = line.getStart().getX();
            double x1 = line.getEnd().getX();
            double y0 = line.getStart().getY();
            double y1 = line.getEnd().getY();
            return new Point3D(x0 + factor * (x1 - x0), y0 + factor * (y1 - y0),
                z0 + factor * (z1 - z0));
        }
    }
 
    private static boolean isPoint3DInBoundingBox(Point3D p, double x0, 
            double x1, double y0, double y1, double z0, double z1) {
        double x = p.getX();
        if (x < x0) {
            return false;
        }
        if (x > x1) {
            return false;
        }
        double y = p.getY();
        if (y < y0) {
            return false;
        }
        if (y > y1) {
            return false;
        }
        double z = p.getZ();
        if (z < z0) {
            return false;
        }
        if (z > z1) {
            return false;
        }
        return true;
    }
}
