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

import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.ArrayList;

/**
 * Rendering info returned from the {@link Drawable3D} {@code draw()} 
 * method.
 * 
 * @since 1.3
 */
public class RenderingInfo {
    
    /**
     * A list of the faces drawn in order of rendering.
     */
    private List<Face> faces;
    
    /** The projected points for the vertices in the faces. */
    Point2D[] projPts;
    
    /** The x-translation. */
    private double dx;
    
    /** The y-translation. */
    public double dy;
    
    /** 
     * Storage for rendered elements in the model other than the 3D objects
     * (caters for code that overlays other items such as labels).
     */
    List<RenderedElement> otherElements;

    List<RenderedElement> otherOffsetElements;
    
    /**
     * Creates a new instance.
     * 
     * @param faces  the rendered faces (in order of rendering).
     * @param projPts  the projected points for all vertices in the 3D model.
     * @param dx  the x-delta.
     * @param dy  the y-delta.
     */
    public RenderingInfo(List<Face> faces, Point2D[] projPts, double dx, 
            double dy) {
        this.faces = faces;
        this.projPts = projPts;
        this.dx = dx;
        this.dy = dy;
        this.otherElements = new ArrayList<RenderedElement>();
        this.otherOffsetElements = new ArrayList<RenderedElement>();
    }
    
    /**
     * Returns the list of faces rendered.
     * 
     * @return The list of faces.
     */
    public List<Face> getFaces() {
        return this.faces;
    }
    
    /**
     * Returns the projected points.
     * 
     * @return The projected points. 
     */
    public Point2D[] getProjectedPoints() {
        return this.projPts;
    }
    
    /**
     * Returns the x-translation amount.  All projected points are centered
     * on (0, 0) but the rendering to the screen (or other Graphics2D target)
     * performs two translations: the first is to the center of the bounding
     * rectangle, and the second is to apply the translate2D attribute of the
     * chart.  The result of these translations is stored here and used in the
     * fetchObjectAt(x, y) method.
     * 
     * @return The x-translation. 
     */
    public double getDX() {
        return this.dx;
    }
    
    /**
     * Returns the y-translation amount.
     * 
     * @return The y-translation. 
     */
    public double getDY() {
        return this.dy;
    }
    
    /**
     * Adds a rendered element to the rendering info.
     * 
     * @param element  the element ({@code null} not permitted). 
     */
    public void addElement(RenderedElement element) {
        this.otherElements.add(element);
    }
    
    /**
     * Adds a rendered element to the list of offset elements.
     * 
     * @param element  the element ({@code null} not permitted). 
     */
    public void addOffsetElement(RenderedElement element) {
        this.otherOffsetElements.add(element);
    }
    
    /**
     * Fetches the object, if any, that is rendered at {@code (x, y)}.
     * 
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     * 
     * @return The object (or {@code null}). 
     */
    public Object3D fetchObjectAt(double x, double y) {
        for (int i = this.faces.size() - 1; i >= 0; i--) {
            Face f = this.faces.get(i);
            if (f instanceof LabelFace) {
                Rectangle2D bounds 
                        = (Rectangle2D) f.getOwner().getProperty("labelBounds");
                if (bounds != null && bounds.contains(x - dx, y - dy)) {
                    return f.getOwner();
                }
            } else {
                Path2D p = f.createPath(this.projPts);
                if (p.contains(x - dx, y - dy)) {
                    return f.getOwner();
                }
            }
        }
        return null;
    }
    
    /**
     * Finds the rendered element, if any, at the location {@code (x, y)}.
     * The method first calls fetchObjectAt(x, y) to see if there is an
     * object at the specified location and, if there is, returns a new
     * RenderedElement instance for that object.  Otherwise, it searches the
     * otherElements list to see if there is some other element (such as a
     * title, legend, axis label or axis tick label) and returns that item.
     * Finally, if no element is found, the method returns {@code null}.
     * 
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     * 
     * @return The interactive element or {@code null}.
     */
    public RenderedElement findElementAt(double x, double y) {
        for (int i = this.otherElements.size() - 1; i >= 0; i--) {
            RenderedElement element = this.otherElements.get(i);
            Shape bounds = (Shape) element.getProperty(RenderedElement.BOUNDS);
            if (bounds.contains(x, y)) {
                return element;
            }
        }
        
        for (int i = this.otherOffsetElements.size() - 1; i >= 0; i--) {
            RenderedElement element = this.otherOffsetElements.get(i);
            Shape bounds = (Shape) element.getProperty(RenderedElement.BOUNDS);
            if (bounds != null && bounds.contains(x - dx, y - dy)) {
                return element;
            }
        }

        Object3D obj = fetchObjectAt(x, y);
        if (obj != null) {
            RenderedElement element = new RenderedElement("obj3d", null);
            element.setProperty(Object3D.ITEM_KEY, 
                    obj.getProperty(Object3D.ITEM_KEY));
            if (obj.getProperty(Object3D.CLASS_KEY) != null) {
                element.setProperty(Object3D.CLASS_KEY, 
                        obj.getProperty(Object3D.CLASS_KEY));
            }
            return element;
        }
        return null;
    }
    
}
