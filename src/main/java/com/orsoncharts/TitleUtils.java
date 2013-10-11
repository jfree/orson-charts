/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts;

import java.awt.Font;
import com.orsoncharts.table.GridElement;
import com.orsoncharts.table.HAlign;
import com.orsoncharts.table.TableElement;
import com.orsoncharts.table.TextElement;
import com.orsoncharts.util.Anchor2D;

/**
 * Some utility methods for creating composite chart titles.
 */
public class TitleUtils {
    
    /** The default title font. */
    public static final Font DEFAULT_TITLE_FONT = new Font("Dialog", Font.BOLD, 
            20);
    
    /** The default sub-title font. */
    public static final Font DEFAULT_SUBTITLE_FONT = new Font("Dialog", 
            Font.PLAIN, 12);
    
    /**
     * Creates a chart title and subtitle using default fonts and left 
     * alignment.
     * 
     * @param title  the title text (<code>null</code> not permitted).
     * @param subtitle  the subtitle text (<code>null</code> not permitted).
     * 
     * @return A composite title. 
     */
    public static TableElement createTitle(String title, String subtitle) {
        return createTitle(title, subtitle, TitleAnchor.TOP_LEFT);
    }
    
    /**
     * Creates a chart title and subtitle using default fonts and alignment
     * that is standard for the specified anchor point (that is, left alignment
     * when the title is anchored left, center alignment when the title is 
     * anchored centrally, and right alignment when the title is anchored to
     * the right).
     * 
     * @param title  the title text (<code>null</code> not permitted).
     * @param subtitle  the subtitle text (<code>null</code> not permitted).
     * @param anchor  the anchor point (<code>null</code> not permitted).
     * 
     * @return A composite title. 
     */
    public static TableElement createTitle(String title, String subtitle, 
            Anchor2D anchor) {
        HAlign alignment = HAlign.LEFT;
        if (anchor.getRefPt().isCenter()) {
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
     * @param subtitle  the subtitle text (<code>null</code> not permitted).
     * @param subtitleFont  the subtitle font (<code>null</code> not permitted).
     * @param anchor  the anchor point (<code>null</code> not permitted).
     * 
     * @return A composite title. 
     */
    public static TableElement createTitle(String title, Font titleFont,
            String subtitle, Font subtitleFont, HAlign alignment) {
        GridElement compositeTitle = new GridElement();
        TextElement t = new TextElement(title, titleFont);
        t.setHorizontalAligment(alignment);
        TextElement st = new TextElement(subtitle, subtitleFont);
        st.setHorizontalAligment(alignment);
        compositeTitle.setElement(t, "R1", "C1");
        compositeTitle.setElement(st, "R2", "C1");
        return compositeTitle;
    }

}
