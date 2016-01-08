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

package com.orsoncharts.table;

import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Shape;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.util.Fit2D;

/**
 * A table element that displays a list of sub-elements in a vertical flow 
 * layout.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 * 
 * @since 1.1
 */
@SuppressWarnings("serial")
public class VerticalFlowElement extends AbstractTableElement 
        implements ContainerElement, Serializable {

    /** The sub-elements in this flow. */
    private List<TableElement> elements;
    
    /** The vertical alignment of the contents of each column. */
    private VAlign verticalAlignment;

    /** 
     * The vertical gap between elements in the same column, specified in 
     * Java2D units. 
     */
    private int vgap;
    
    /**
     * Creates a new instance (equivalent to 
     * {@code new VerticalFlowElement(VAlign.MIDDLE, 2)}).
     */
    public VerticalFlowElement() {
        this(VAlign.MIDDLE, 2);
    }

    /**
     * Creates a new instance.
     * 
     * @param alignment  the vertical alignment of columns ({@code null} 
     *         not permitted).
     * @param vgap  the gap between elements. 
     */
    public VerticalFlowElement(VAlign alignment, int vgap) {
        ArgChecks.nullNotPermitted(alignment, null);
        this.elements = new ArrayList<TableElement>();
        this.verticalAlignment = alignment;
        this.vgap = vgap;
    }
    
    /**
     * Returns the vertical alignment for the elements.
     * 
     * @return The vertical alignment (never {@code null}). 
     */
    public VAlign getVerticalAlignment() {
        return this.verticalAlignment;
    }
    
    /**
     * Sets the vertical alignment of elements within columns,
     * 
     * @param alignment  the alignment ({@code null} not permitted). 
     */
    public void setVerticalAlignment(VAlign alignment) {
        ArgChecks.nullNotPermitted(alignment, "alignment");
        this.verticalAlignment = alignment;
    }
    
    /**
     * Returns the vertical gap between elements, in Java2D units.
     * 
     * @return The vertical gap. 
     */
    public int getVGap() {
        return this.vgap;
    }
    
    /**
     * Sets the vertical gap between elements.
     * 
     * @param vgap  the gap (in Java2D units). 
     */
    public void setVGap(int vgap) {
        this.vgap = vgap;
    }
    
    /**
     * Returns a (new) list containing the elements in this flow layout.
     * 
     * @return A list containing the elements (possibly empty, but never 
     *     {@code null}). 
     */
    public List<TableElement> getElements() {
        return new ArrayList<TableElement>(this.elements);
    }
    
    /**
     * Adds a sub-element to the list.
     * 
     * @param element  the element ({@code null} not permitted).
     */
    @Override
    public void addElement(TableElement element) {
        ArgChecks.nullNotPermitted(element, "element");
        this.elements.add(element);
    }

    /**
     * Receives a {@link TableElementVisitor} (the visitor will be received
     * by all the elements in the flow).
     * 
     * @param visitor  the visitor ({@code null} not permitted).
     * 
     * @since 1.2
     */
    @Override
    public void receive(TableElementVisitor visitor) {
        for (TableElement element : elements) {
            element.receive(visitor);
        }
    }

    /**
     * Returns the preferred size for the element.
     * 
     * @param g2  the graphics target ({@code null} not permitted).
     * @param bounds  the bounds ({@code null} not permitted).
     * @param constraints  the layout constraints (ignored here).
     * 
     * @return The preferred size (never {@code null}). 
     */
    @Override
    public Dimension2D preferredSize(Graphics2D g2, Rectangle2D bounds, 
            Map<String, Object> constraints) {
        Insets insets = getInsets();
        double width = insets.left + insets.right;
        double height = insets.top + insets.bottom;
        double maxColHeight = 0.0;
        int elementCount = this.elements.size();
        int i = 0;
        while (i < elementCount) {
            // get one column of elements...
            List<ElementInfo> elementsInColumn = columnOfElements(i, g2, 
                    bounds);
            double colWidth = calcColumnWidth(elementsInColumn);
            double colHeight = calcColumnHeight(elementsInColumn, this.vgap);
            maxColHeight = Math.max(colHeight, maxColHeight);
            width += colWidth;
            i = i + elementsInColumn.size();
        }
        height += maxColHeight;
        return new ElementDimension(width, height);
    }

    /**
     * Returns info for as many elements as we can fit into one column.
     * 
     * @param first  the index of the first element.
     * @param g2  the graphics target.
     * @param bounds  the bounds.
     * 
     * @return A list of elements and dimensions. 
     */
    private List<ElementInfo> columnOfElements(int first, 
            Graphics2D g2, Rectangle2D bounds) {
        List<ElementInfo> result = new ArrayList<ElementInfo>();
        int index = first;
        boolean full = false;
        double h = getInsets().top + getInsets().bottom;
        while (index < this.elements.size() && !full) {
            TableElement element = this.elements.get(index);
            Dimension2D dim = element.preferredSize(g2, bounds);
            if (h + dim.getHeight() <= bounds.getHeight() || index == first) {
                result.add(new ElementInfo(element, dim));
                h += dim.getHeight() + this.vgap;
                index++;
            } else {
                full = true;
            }
        }
        return result;
    }
    
    /**
     * Returns the width of the widest element in the list.
     * 
     * @param elementInfoList  element info list
     * 
     * @return The width. 
     */
    private double calcColumnWidth(List<ElementInfo> elementInfoList) {
        double result = 0.0;
        for (ElementInfo elementInfo : elementInfoList) {
            result = Math.max(result, elementInfo.getDimension().getWidth());
        }
        return result;
    }
    
    /**
     * Calculates the total height of the elements that will form one column.
     * 
     * @param elementInfoList  the elements in the column.
     * @param vgap  the gap between elements.
     * 
     * @return The total height. 
     */
    private double calcColumnHeight(List<ElementInfo> elementInfoList, 
            double vgap) {
        double result = 0.0;
        for (ElementInfo elementInfo : elementInfoList) {
            result += elementInfo.getDimension().getHeight();
        }
        int count = elementInfoList.size();
        if (count > 1) {
            result += (count - 1) * vgap;
        }
        return result;
    }
    
    @Override
    public List<Rectangle2D> layoutElements(Graphics2D g2, Rectangle2D bounds, 
            Map<String, Object> constraints) {
        int elementCount = this.elements.size();
        List<Rectangle2D> result = new ArrayList<Rectangle2D>(elementCount);
        int i = 0;
        double x = bounds.getX() + getInsets().left;
        double y = bounds.getY() + getInsets().top;
        while (i < elementCount) {
            // get one column of elements...
            List<ElementInfo> elementsInColumn = columnOfElements(i, g2, 
                    bounds);
            double width = calcColumnWidth(elementsInColumn);
            double height = calcColumnHeight(elementsInColumn, this.vgap);  
            if (this.verticalAlignment == VAlign.MIDDLE) {
                y = bounds.getCenterY() - (height / 2.0);
            } else if (this.verticalAlignment == VAlign.BOTTOM) {
                y = bounds.getMaxY() - getInsets().bottom - height;
            }
            for (ElementInfo elementInfo : elementsInColumn) {
                Dimension2D dim = elementInfo.getDimension();
                Rectangle2D position = new Rectangle2D.Double(x, y, 
                        width, dim.getHeight());
                result.add(position);
                y += position.getHeight() + this.vgap;
            }
            i = i + elementsInColumn.size();
            x += width;
            y = bounds.getY() + getInsets().top;
        }
        return result;
    }

    /**
     * Draws the element and all of its subelements within the specified
     * bounds.
     * 
     * @param g2  the graphics target ({@code null} not permitted).
     * @param bounds  the bounds ({@code null} not permitted).
     */
    @Override
    public void draw(Graphics2D g2, Rectangle2D bounds) {
        draw(g2, bounds, null);
    }
    
    
    /**
     * Draws the element within the specified bounds.  If the 
     * {@code recordBounds} flag is set, this element and each of its
     * children will have its {@code BOUNDS_2D} property updated with 
     * the current bounds.
     * 
     * @param g2  the graphics target ({@code null} not permitted).
     * @param bounds  the bounds ({@code null} not permitted).
     * @param onDrawHandler  record the bounds?
     * 
     * @since 1.3
     */
    @Override
    public void draw(Graphics2D g2, Rectangle2D bounds, 
            TableElementOnDraw onDrawHandler) {
        if (onDrawHandler != null) {
            onDrawHandler.beforeDraw(this, g2, bounds);
        }
        Shape savedClip = g2.getClip();
        g2.clip(bounds);
        
        // find the preferred size of the flow layout
        Dimension2D prefDim = preferredSize(g2, bounds);
        
        // fit a rectangle of this dimension to the bounds according to the 
        // element anchor
        Fit2D fitter = Fit2D.getNoScalingFitter(getRefPoint());
        Rectangle2D dest = fitter.fit(prefDim, bounds);
        
        // perform layout within this bounding rectangle
        List<Rectangle2D> layoutInfo = layoutElements(g2, dest, null);
        
        // draw the elements
        for (int i = 0; i < this.elements.size(); i++) {
            Rectangle2D rect = layoutInfo.get(i);
            TableElement element = this.elements.get(i);
            element.draw(g2, rect, onDrawHandler);
        }
        g2.setClip(savedClip);
        if (onDrawHandler != null) {
            onDrawHandler.afterDraw(this, g2, bounds);
        }
    }
    
    /**
     * Tests this element for equality with an arbitrary object.
     * 
     * @param obj  the object ({@code null} permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof VerticalFlowElement)) {
            return false;
        }
        VerticalFlowElement that = (VerticalFlowElement) obj;
        if (this.vgap != that.vgap) {
            return false;
        }
        if (this.verticalAlignment != that.verticalAlignment) {
            return false;
        }
        if (!this.elements.equals(that.elements)) {
            return false;
        }
        return super.equals(obj);
    }
    
}
