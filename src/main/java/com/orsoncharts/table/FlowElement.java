/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.table;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.orsoncharts.util.ArgChecks;

/**
 * A table element that displays a list of sub-elements in a flow layout.
 */
public class FlowElement extends AbstractTableElement implements TableElement {

    private List<TableElement> elements;
    
    private HAlign horizontalAlignment;

    /** 
     * The horizontal gap between elements on the same line, specified in 
     * Java2D units. 
     */
    private int hgap;
    
    /**
     * Creates a new instance.
     */
    public FlowElement() {
        this.elements = new ArrayList<TableElement>();
        this.horizontalAlignment = HAlign.CENTER;
        this.hgap = 2;
    }
    
    /**
     * Adds a sub-element to the list.
     * 
     * @param element  the element (<code>null</code> not permitted).
     */
    public void addElement(TableElement element) {
        ArgChecks.nullNotPermitted(element, "element");
        this.elements.add(element);
    }
    
    @Override
    public Dimension2D preferredSize(Graphics2D g2, Rectangle2D bounds, 
            Map<String, Object> constraints) {
        double maxWidth = bounds.getWidth();
        double width = 0.0;
        double height = 0.0;
        double w = 0.0;
        double h = 0.0;
        for (TableElement e : this.elements) {
            Dimension2D dim = e.preferredSize(g2, bounds, constraints);
            if (w + dim.getWidth() <= maxWidth) {
                w += dim.getWidth();
                h = Math.max(dim.getHeight(), h);
                width = Math.max(width, w);
                height = Math.max(height, h);
            } else {
                width = Math.max(width, w);
                height = Math.max(height, h);
                w = dim.getWidth();
                h = dim.getHeight();
            }
        }
        return new Dimension((int) width, (int) height);
    }

    /**
     *
     * @param firstElement
     * @param g2
     * @param bounds
     * @return 
     */
    private ElementLine lineOfElements(int firstElement, Graphics2D g2, 
            Rectangle2D bounds) {
        List<TableElement> elementsInLine = new ArrayList<TableElement>();
        List<Double> elementWidths = new ArrayList<Double>();
        double x = getInsets().left;
        double w = 0.0;
        double h = 0.0;
        for (int i = firstElement; i < this.elements.size(); i++) {
            TableElement e = this.elements.get(i);
            Dimension2D dim = e.preferredSize(g2, bounds);
            if (x + dim.getWidth() < bounds.getWidth() + 10) {
                elementsInLine.add(this.elements.get(i));
                elementWidths.add(dim.getWidth());
                w = dim.getWidth();
                if (i > firstElement) {
                    w += this.hgap;
                }
                h = Math.max(h, dim.getHeight());
            }
            x += dim.getWidth() + this.hgap;
        }
        if (elementsInLine.isEmpty()) {
            elementsInLine.add(this.elements.get(firstElement));
            w = bounds.getWidth() - getInsets().left - getInsets().right;
            elementWidths.add(w);
        }
        return new ElementLine(elementsInLine, elementWidths, w, h);
    }
    
    @Override
    public List<Rectangle2D> layoutElements(Graphics2D g2, Rectangle2D bounds, 
            Map<String, Object> constraints) {
        List<Rectangle2D> result = new ArrayList<Rectangle2D>(
                this.elements.size());
        double x = bounds.getX() + getInsets().left;
        double y = bounds.getY() + getInsets().top;
        int i = 0;
        while (i < this.elements.size()) {
            ElementLine line = lineOfElements(i, g2, bounds);
            for (int elementIndex = 0; elementIndex < line.getElements().size(); elementIndex++) {
                // x will depend on horizontal alignment, the gap and the element index
                // y is already known
                double dx = calculateXOffset(elementIndex, line.getWidths(), this.hgap);
                Rectangle2D rect = new Rectangle2D.Double(x + dx, y, 
                        line.getWidths().get(elementIndex), line.getHeight());
                result.add(rect);
            }
            i += line.getElements().size();
            y += line.getHeight();
        }
        return result;
    }

    private double calculateXOffset(int elementIndex, List<Double> widths, 
            int gap) {
        double x = 0.0;
        for (int i = 0; i < elementIndex; i++) {
            x += widths.get(i).doubleValue();
        }
        if (elementIndex > 1) {
            x += gap * (elementIndex - 1);
        }
        return x;    
    }
    
    @Override
    public void draw(Graphics2D g2, Rectangle2D bounds) {
        List<Rectangle2D> layoutInfo = layoutElements(g2, bounds, null);
        for (int i = 0; i < this.elements.size(); i++) {
            Rectangle2D rect = layoutInfo.get(i);
            TableElement element = this.elements.get(i);
            element.draw(g2, rect);
        }
    }
    
    private static class ElementLine {
        private double width;
        private double height;
        private List<TableElement> elements;
        private List<Double> widths;
        public ElementLine() {
            this(new ArrayList<TableElement>(), new ArrayList<Double>(), 0.0, 
                    0.0);
        }
        public ElementLine(List<TableElement> elements, 
                List<Double> elementWidths, double width, double height) {
            this.elements = elements;
            this.widths = elementWidths;
            this.width = width;
            this.height = height;
        }
        public List<TableElement> getElements() {
            return this.elements;
        }
        public List<Double> getWidths() {
            return this.widths;
        }
        public double getWidth() {
            return this.width;
        }
        public double getHeight() {
            return this.height;
        }
    }
}
