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

package com.orsoncharts;

import java.awt.Color;
import java.awt.Font;
import com.orsoncharts.interaction.InteractiveElementType;
import com.orsoncharts.table.GridElement;
import com.orsoncharts.table.HAlign;
import com.orsoncharts.table.TableElement;
import com.orsoncharts.table.TextElement;
import com.orsoncharts.util.Anchor2D;

/**
 * Some utility methods for creating chart titles.
 */
public class TitleUtils {
    
    /** The default title font. */
    public static final Font DEFAULT_TITLE_FONT = new Font("Dialog", Font.BOLD, 
            20);
    
    /** The default foreground color for titles. */
    public static final Color DEFAULT_TITLE_COLOR = Color.BLACK;
    
    /** The default sub-title font. */
    public static final Font DEFAULT_SUBTITLE_FONT = new Font("Dialog", 
            Font.PLAIN, 12);
    
    private TitleUtils() {
        // no need to instantiate this class
    }
    
    /**
     * Creates a chart title using the default font and alignment.
     * 
     * @param title  the chart title (<code>null</code> not permitted).
     * 
     * @return The chart title. 
     */
    public static TableElement createTitle(String title) {
        return createTitle(title, null);    
    }
    
    /**
     * Creates a chart title and subtitle using default fonts and left 
     * alignment.  The <code>subtitle</code> is optional.
     * 
     * @param title  the title text (<code>null</code> not permitted).
     * @param subtitle  the subtitle text (<code>null</code> permitted).
     * 
     * @return A composite title. 
     */
    public static TableElement createTitle(String title, String subtitle) {
        return createTitle(title, subtitle, TitleAnchor.TOP_LEFT);
    }
    
    /**
     * Creates a chart title and subtitle (optional) using default fonts and 
     * alignment that is standard for the specified anchor point (that is, left 
     * alignment when the title is anchored left, center alignment when the 
     * title is anchored centrally, and right alignment when the title is 
     * anchored to the right).
     * 
     * @param title  the title text (<code>null</code> not permitted).
     * @param subtitle  the subtitle text (<code>null</code> permitted).
     * @param anchor  the anchor point (<code>null</code> not permitted).
     * 
     * @return A composite title. 
     */
    public static TableElement createTitle(String title, String subtitle,
            Anchor2D anchor) {
        HAlign alignment = HAlign.LEFT;
        if (anchor.getRefPt().isHorizontalCenter()) {
            alignment = HAlign.CENTER;
        } else if (anchor.getRefPt().isRight()) {
            alignment = HAlign.RIGHT;
        }
        return createTitle(title, DEFAULT_TITLE_FONT, subtitle, 
                DEFAULT_SUBTITLE_FONT, alignment);
    }
    
    /**
     * Creates a chart title and subtitle using the specified fonts and 
     * alignment.
     * 
     * @param title  the title text (<code>null</code> not permitted).
     * @param titleFont  the title font (<code>null</code> not permitted).
     * @param subtitle  the subtitle text (<code>null</code> permitted).
     * @param subtitleFont  the subtitle font (<code>null</code> permitted).
     * @param alignment  the horizontal alignment (<code>null</code> not 
     *     permitted).
     * 
     * @return A chart title (never <code>null</code>). 
     */
    public static TableElement createTitle(String title, Font titleFont,
            String subtitle, Font subtitleFont, HAlign alignment) {
        
        TextElement t = new TextElement(title, titleFont);
        t.setHorizontalAligment(alignment);
        t.setColor(DEFAULT_TITLE_COLOR);
        t.setTag("CHART_TITLE");
        t.setProperty(TableElement.CLASS, InteractiveElementType.TITLE);
        if (subtitle == null) {
            return t;
        }
        if (subtitleFont == null) {
            throw new IllegalArgumentException(
                    "A subtitleFont is required when there is a subtitle.");
        }
        GridElement compositeTitle = new GridElement();
        TextElement st = new TextElement(subtitle, subtitleFont);
        st.setHorizontalAligment(alignment);
        st.setColor(DEFAULT_TITLE_COLOR);
        st.setTag("CHART_SUBTITLE");
        st.setProperty(TableElement.CLASS, InteractiveElementType.SUBTITLE);
        compositeTitle.setElement(t, "R1", "C1");
        compositeTitle.setElement(st, "R2", "C1");
        return compositeTitle;
    }

}
