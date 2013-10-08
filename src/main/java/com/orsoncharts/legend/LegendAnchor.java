/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.legend;

import com.orsoncharts.TitleAnchor;
import com.orsoncharts.util.Anchor2D;

/**
 * Predefined legend anchor points, provided for convenience.  The anchor
 * points are simply instances of the {@link Anchor2D} class.
 */
public class LegendAnchor {
    
    /**
     * An anchor point at the top-left of the chart area. 
     */
    public static final Anchor2D TOP_LEFT = TitleAnchor.TOP_LEFT;

    /**
     * An anchor point at the top-right of the chart area. 
     */
    public static final Anchor2D TOP_RIGHT = TitleAnchor.TOP_RIGHT;

    /**
     * An anchor point at the top-center of the chart area. 
     */
    public static final Anchor2D TOP_CENTER = TitleAnchor.TOP_CENTER;

    /**
     * An anchor point at the center-left of the chart area. 
     */
    public static final Anchor2D CENTER_LEFT = TitleAnchor.CENTER_LEFT;

    /**
     * An anchor point at the center-right of the chart area. 
     */
    public static final Anchor2D CENTER_RIGHT = TitleAnchor.CENTER_RIGHT;

    /**
     * An anchor point at the bottom-center of the chart area. 
     */
    public static final Anchor2D BOTTOM_CENTER = TitleAnchor.BOTTOM_CENTER;

    /**
     * An anchor point at the bottom-left of the chart area. 
     */
    public static final Anchor2D BOTTOM_LEFT = TitleAnchor.BOTTOM_LEFT;
    
    /**
     * An anchor point at the bottom-right of the chart area. 
     */
    public static final Anchor2D BOTTOM_RIGHT = TitleAnchor.BOTTOM_RIGHT;
    
    private LegendAnchor() {
        // no need to instantiate this
    }
}
