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

import java.awt.Insets;
import java.awt.Graphics2D;
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
 * A table element that displays a list of sub-elements in a flow layout.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 */
@SuppressWarnings("serial")
public class FlowElement extends AbstractTableElement 
        implements ContainerElement, Serializable {

    /** The sub-elements in this flow. */
    private List<TableElement> elements;
    
    /** The horizontal alignment of each row. */
    private HAlign horizontalAlignment;

    /** 
     * The horizontal gap between elements on the same line, specified in 
     * Java2D units. 
     */
    private int hgap;
    
    /**
     * Creates a new instance (equivalent to 
     * {@code new FlowElement(HAlign.CENTER, 2)}).
     */
    public FlowElement() {
        this(HAlign.CENTER, 2);
    }
    
    /**
     * Creates a new instance with the specified attributes.
     * 
     * @param alignment  the horizontal alignment of the elements within
     *     each row ({@code null} not permitted).
     * @param hgap  the gap between elements.
     * 
     * @since 1.1
     */
    public FlowElement(HAlign alignment, int hgap) {
        super();
        ArgChecks.nullNotPermitted(alignment, "alignment");
        this.elements = new ArrayList<TableElement>();
        this.horizontalAlignment = alignment;
        this.hgap = hgap;
    }
    
    /**
     * Returns the horizontal gap between elements, specified in Java2D units.
     * The default value is {@code 2}.
     * 
     * @return The horizontal gap. 
     */
    public int getHGap() {
        return this.hgap;
    }
    
    /**
     * Sets the horizontal gap between elements.
     * 
     * @param gap  the gap (in Java2D units). 
     */
    public void setHGap(int gap) {
        this.hgap = gap;
    }
    
    /**
     * Returns the horizontal alignment of items within rows.  The default
     * value is {@link HAlign#CENTER}.
     * 
     * @return The horizontal alignment (never {@code null}).
     * 
     * @since 1.1
     */
    public HAlign getHorizontalAlignment() {
        return this.horizontalAlignment;
    }
    
    /**
     * Sets the horizontal alignment.
     * 
     * @param alignment  the alignment ({@code null} not permitted).
     * 
     * @since 1.1
     */
    public void setHorizontalAlignment(HAlign alignment) {
        ArgChecks.nullNotPermitted(alignment, "alignment");
        this.horizontalAlignment = alignment;
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
     * Receives a visitor.  The implementation ensures that the vistor visits
     * all the elements belonging to the flow.
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
     * Returns info for as many elements as we can fit into one row.
     * 
     * @param first  the index of the first element.
     * @param g2  the graphics target.
     * @param bounds  the bounds.
     * 
     * @return A list of elements and dimensions. 
     */
    private List<ElementInfo> rowOfElements(int first, 
            Graphics2D g2, Rectangle2D bounds) {
        List<ElementInfo> result = new ArrayList<ElementInfo>();
        int index = first;
        boolean full = false;
        double w = getInsets().left + getInsets().right;
        while (index < this.elements.size() && !full) {
            TableElement element = this.elements.get(index);
            Dimension2D dim = element.preferredSize(g2, bounds);
            if (w + dim.getWidth() <= bounds.getWidth() || index == first) {
                result.add(new ElementInfo(element, dim));
                w += dim.getWidth() + this.hgap;
                index++;
            } else {
                full = true;
            }
        }
        return result;
    }
    
    /**
     * Returns the height of the tallest element in the list.
     * 
     * @param elementInfoList  element info list
     * 
     * @return The height. 
     */
    private double calcRowHeight(List<ElementInfo> elementInfoList) {
        double result = 0.0;
        for (ElementInfo elementInfo : elementInfoList) {
            result = Math.max(result, elementInfo.getDimension().getHeight());
        }
        return result;
    }
    
    /**
     * Calculates the total width of the elements that will form one row.
     * 
     * @param elementInfoList  the elements in the column.
     * @param hgap  the gap between elements.
     * 
     * @return The total height. 
     */
    private double calcRowWidth(List<ElementInfo> elementInfoList, 
            double hgap) {
        double result = 0.0;
        for (ElementInfo elementInfo : elementInfoList) {
            result += elementInfo.getDimension().getWidth();
        }
        int count = elementInfoList.size();
        if (count > 1) {
            result += (count - 1) * hgap;
        }
        return result;
    }
    
    /**
     * Returns the preferred size of the element (including insets).
     * 
     * @param g2  the graphics target.
     * @param bounds  the bounds.
     * @param constraints  the constraints (ignored for now).
     * 
     * @return The preferred size. 
     */
    @Override
    public Dimension2D preferredSize(Graphics2D g2, Rectangle2D bounds, 
            Map<String, Object> constraints) {
        Insets insets = getInsets();
        double width = insets.left + insets.right;
        double height = insets.top + insets.bottom;
        double maxRowWidth = 0.0;
        int elementCount = this.elements.size();
        int i = 0;
        while (i < elementCount) {
            // get one row of elements...
            List<ElementInfo> elementsInRow = rowOfElements(i, g2, 
                    bounds);
            double rowHeight = calcRowHeight(elementsInRow);
            double rowWidth = calcRowWidth(elementsInRow, this.hgap);
            maxRowWidth = Math.max(rowWidth, maxRowWidth);
            height += rowHeight;
            i = i + elementsInRow.size();
        }
        width += maxRowWidth;
        return new ElementDimension(width, height);        
    }
    
    /**
     * Calculates the layout of the elements for the given bounds and 
     * constraints.
     * 
     * @param g2  the graphics target ({@code null} not permitted).
     * @param bounds  the bounds ({@code null} not permitted).
     * @param constraints  the constraints (not used here).
     * 
     * @return A list of positions for the sub-elements. 
     */
    @Override
    public List<Rectangle2D> layoutElements(Graphics2D g2, Rectangle2D bounds, 
            Map<String, Object> constraints) {
        int elementCount = this.elements.size();
        List<Rectangle2D> result = new ArrayList<Rectangle2D>(elementCount);
        int i = 0;
        double x = bounds.getX() + getInsets().left;
        double y = bounds.getY() + getInsets().top;
        while (i < elementCount) {
            // get one row of elements...
            List<ElementInfo> elementsInRow = rowOfElements(i, g2, 
                    bounds);
            double height = calcRowHeight(elementsInRow);
            double width = calcRowWidth(elementsInRow, this.hgap);  
            if (this.horizontalAlignment == HAlign.CENTER) {
                x = bounds.getCenterX() - (width / 2.0);
            } else if (this.horizontalAlignment == HAlign.RIGHT) {
                x = bounds.getMaxX() - getInsets().right - width;
            }
            for (ElementInfo elementInfo : elementsInRow) {
                Dimension2D dim = elementInfo.getDimension();
                Rectangle2D position = new Rectangle2D.Double(x, y, 
                        dim.getWidth(), height);
                result.add(position);
                x += position.getWidth() + this.hgap;
            }
            i = i + elementsInRow.size();
            x = bounds.getX() + getInsets().left;
            y += height;
        }
        return result;

    }
    
    /**
     * Draws the element within the specified bounds.
     * 
     * @param g2  the graphics target ({@code null} not permitted).
     * @param bounds  the bounds ({@code null} not permitted).
     */
    @Override
    public void draw(Graphics2D g2, Rectangle2D bounds) {
        draw(g2, bounds, null);
    }
    
    /**
     * Draws the element within the specified bounds.
     * 
     * @param g2  the graphics target ({@code null} not permitted).
     * @param bounds  the bounds ({@code null} not permitted).
     * @param onDrawHandler  an object that will receive notification before 
     *     and after the element is drawn ({@code null} permitted).
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
        List<Rectangle2D> layoutInfo = this.layoutElements(g2, dest, null);
        
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
        if (!(obj instanceof FlowElement)) {
            return false;
        }
        FlowElement that = (FlowElement) obj;
        if (this.hgap != that.hgap) {
            return false;
        }
        if (this.horizontalAlignment != that.horizontalAlignment) {
            return false;
        }
        if (!this.elements.equals(that.elements)) {
            return false;
        }
        return super.equals(obj);
    }

}
