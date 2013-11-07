/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.legend;

import java.awt.Color;
import java.awt.Paint;
import java.awt.geom.Rectangle2D;
import java.awt.Font;
import java.awt.Shape;
import java.util.List;
import java.io.Serializable;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.plot.PiePlot3D;
import com.orsoncharts.plot.Plot3D;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.table.FlowElement;
import com.orsoncharts.table.GridElement;
import com.orsoncharts.table.HAlign;
import com.orsoncharts.table.ShapeElement;
import com.orsoncharts.table.TableElement;
import com.orsoncharts.table.TextElement;
import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.util.ObjectUtils;

/**
 * The standard legend builder, which creates a simple horizontal legend
 * with a flow layout and optional header and footer text.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 */
public final class StandardLegendBuilder implements LegendBuilder, 
        Serializable {

    /** The default header font. */
    public static final Font DEFAULT_HEADER_FONT = new Font("Dialog", Font.BOLD,
            14);
    
    /** The default footer font. */
    public static final Font DEFAULT_FOOTER_FONT = new Font("Dialog", 
            Font.PLAIN, 10);
    
    /** The default font for legend items. */
    public static final Font DEFAULT_ITEM_FONT = new Font("Dialog", Font.PLAIN,
            12);
    
    private static final Shape DEFAULT_LEGEND_SHAPE 
            = new Rectangle2D.Double(-6, -4, 12, 8);

    /** An optional header/title for the legend (can be <code>null</code>). */
    private String header;
    
    /** The header font (never <code>null</code>). */
    private Font headerFont;
    
    /** The header alignment (never <code>null</code>). */
    private HAlign headerAlignment;
    
    /** An optional footer for the legend (can be <code>null</code>). */
    private String footer;
    
    /** The footer font (never <code>null</code>). */    
    private Font footerFont;
    
    /** The footer alignment (never <code>null</code>). */
    private HAlign footerAlignment;
    
    /** The font used for legend items. */
    private Font itemFont;
    
    /**
     * Creates a builder for a simple legend with no header and no footer.
     */
    public StandardLegendBuilder() {
        this(null, null);
    }
    
    /**
     * Creates a builder for a simple legend with the specified header and/or
     * footer.
     * 
     * @param header  the legend header (<code>null</code> permitted).
     * @param footer  the legend footer (<code>null</code> permitted).
     */
    public StandardLegendBuilder(String header, String footer) {
        this.header = null;
        this.headerFont = DEFAULT_HEADER_FONT;
        this.headerAlignment = HAlign.LEFT;
        this.footer = null;
        this.footerFont = DEFAULT_FOOTER_FONT;
        this.footerAlignment = HAlign.RIGHT;
        this.itemFont = DEFAULT_ITEM_FONT;
    }
    
    /**
     * Returns the header text.
     * 
     * @return The header text (possibly <code>null</code>).
     */
    public String getHeader() {
        return this.header;
    }
    
    /**
     * Sets the header text.
     * 
     * @param header  the header (<code>null</code> permitted). 
     */
    public void setHeader(String header) {
        this.header = header;
    }
    
    /**
     * Returns the header font.
     * 
     * @return The header font (never <code>null</code>). 
     */
    public Font getHeaderFont() {
        return this.headerFont;
    }
    
    /**
     * Sets the header font.
     * 
     * @param font  the header font (<code>null</code> not permitted). 
     */
    public void setHeaderFont(Font font) {
        ArgChecks.nullNotPermitted(font, "font");
        this.headerFont = font;
    }
    
    /**
     * Returns the header alignment.
     * 
     * @return The header alignment (never </code>null</code>).
     */
    public HAlign getHeaderAlignment() {
        return this.headerAlignment;
    }
    
    /**
     * Sets the header alignment.
     * 
     * @param align  the header alignment (<code>null</code> not permitted). 
     */
    public void setHeaderAlignment(HAlign align) {
        ArgChecks.nullNotPermitted(align, "align");
        this.headerAlignment = align;
    }
    
    /**
     * Returns the footer text.
     * 
     * @return The footer text (possibly <code>null</code>).
     */
    public String getFooter() {
        return this.footer;
    }
    
    /**
     * Sets the footer text.
     * 
     * @param footer  the footer (<code>null</code> permitted). 
     */
    public void setFooter(String footer) {
        this.footer = footer;
    }
    
    /**
     * Returns the footer font.
     * 
     * @return The footer font (never <code>null</code>). 
     */
    public Font getFooterFont() {
        return this.footerFont;
    }
    
    /**
     * Sets the footer font.
     * 
     * @param font  the footer font (<code>null</code> not permitted). 
     */
    public void setFooterFont(Font font) {
        ArgChecks.nullNotPermitted(font, "font");
        this.footerFont = font;
    }
    
    /**
     * Returns the footer alignment.
     * 
     * @return The footer alignment (never </code>null</code>).
     */
    public HAlign getFooterAlignment() {
        return this.footerAlignment;
    }
    
    /**
     * Sets the footer alignment.
     * 
     * @param align  the footer alignment (<code>null</code> not permitted). 
     */
    public void setFooterAlignment(HAlign align) {
        ArgChecks.nullNotPermitted(align, "align");
        this.footerAlignment = align;
    }
    
    /**
     * Returns the font used for each item within the legend (the default value
     * is {@link #DEFAULT_ITEM_FONT}).
     * 
     * @return The item font (never <code>null</code>).
     */
    @Override
    public Font getItemFont() {
        return this.itemFont;
    }
    
    /**
     * Sets the font used for each item within the legend.
     * 
     * @param font  the font (<code>null</code> not permitted). 
     */
    @Override
    public void setItemFont(Font font) {
        ArgChecks.nullNotPermitted(font, "font");
        this.itemFont = font;
    }
    
    /**
     * Creates and returns a legend (instance of {@link TableElement}) that
     * provides a visual key for the data series in the specified plot.  The
     * plot can be any of the built-in plot types: {@link PiePlot3D}, 
     * {@link CategoryPlot3D} or {@link XYZPlot}.
     * 
     * @param plot  the plot (<code>null</code> not permitted).
     * 
     * @return The legend. 
     */
    @Override
    public TableElement createLegend(Plot3D plot) {
        TableElement legend = createSimpleLegend(plot.getLegendInfo());
        if (this.header != null || this.footer != null) {
            GridElement compositeLegend = new GridElement();
            if (header != null) {
                TextElement he = new TextElement(this.header, this.headerFont);
                he.setHorizontalAligment(this.headerAlignment);
                compositeLegend.setElement(he, "R0", "C1");                
            }
            compositeLegend.setElement(legend, "R1", "C1");
            if (this.footer != null) {
                TextElement fe = new TextElement(this.footer, this.footerFont);
                fe.setHorizontalAligment(this.footerAlignment);
                compositeLegend.setElement(fe, "R2", "C1");
            }
            return compositeLegend;
        } else {
            return legend;
        }
    }
    
    /**
     * Creates a simple legend based on a horizontal flow layout of the 
     * individual legend items.
     * 
     * @param plot  the plot (<code>null</code> not permitted).
     * 
     * @return The simple legend. 
     */
    private TableElement createSimpleLegend(List<LegendItemInfo> items) {
        ArgChecks.nullNotPermitted(items, "items");
        FlowElement legend = new FlowElement();
        for (LegendItemInfo item : items) {
            Shape shape = item.getShape();
            if (shape == null) {
                shape = DEFAULT_LEGEND_SHAPE;
            }
            legend.addElement(createLegendItem(item.getLabel(), this.itemFont,
                    shape, item.getPaint()));
        }
        return legend;
    }
    
    /**
     * Creates a single item in the legend (normally this represents one
     * data series from the dataset).
     * 
     * @param text  the legend item text (<code>null</code> not permitted).
     * @param font  the font (<code>null</code> not permitted).
     * @param shape  the shape (<code>null</code> not permitted).
     * @param color  the color (<code>null</code> not permitted).
     * 
     * @return A legend item (never <code>null</code>). 
     */
    private TableElement createLegendItem(String text, Font font, Shape shape, 
            Paint color) {
        // defer argument checks...
        ShapeElement se = new ShapeElement(shape, color);
        se.setBackgroundPaint(new Color(0, 0, 0, 0));
        TextElement te = new TextElement(text, font);
        te.setBackgroundPaint(new Color(0, 0, 0, 0));
        GridElement ge = new GridElement();
        ge.setElement(se, "R1", "C1");
        ge.setElement(te, "R1", "C2");
        return ge;
    }
    
    /**
     * Tests this legend builder for equality with an arbitrary object.
     * 
     * @param obj  the object (<code>null</code> permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof StandardLegendBuilder)) {
            return false;
        }
        StandardLegendBuilder that = (StandardLegendBuilder) obj;
        if (!ObjectUtils.equals(this.header, that.header)) {
            return false;
        }
        if (this.headerAlignment != that.headerAlignment) {
            return false;
        }
        if (!this.headerFont.equals(that.headerFont)) {
            return false;
        }        
        if (!ObjectUtils.equals(this.footer, that.footer)) {
            return false;
        }
        if (this.footerAlignment != that.footerAlignment) {
            return false;
        }
        if (!this.footerFont.equals(that.footerFont)) {
            return false;
        }
        if (!this.itemFont.equals(that.itemFont)) {
            return false;
        }
        return true;
    }

}
