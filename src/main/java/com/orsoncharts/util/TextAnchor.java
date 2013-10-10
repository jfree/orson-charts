/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.util;

/**
 * Used to indicate the position of an anchor point for a text string.  This is
 * frequently used to align a string to a fixed point in some coordinate space.
 */
public enum TextAnchor {

    /** Top/left. */
    TOP_LEFT("TextAnchor.TOP_LEFT"),

    /** Top/center. */
    TOP_CENTER("TextAnchor.TOP_CENTER"),

    /** Top/right. */
    TOP_RIGHT("TextAnchor.TOP_RIGHT"),

    /** Half-ascent/left. */
    HALF_ASCENT_LEFT("TextAnchor.HALF_ASCENT_LEFT"),

    /** Half-ascent/center. */
    HALF_ASCENT_CENTER("TextAnchor.HALF_ASCENT_CENTER"),

    /** Half-ascent/right. */
    HALF_ASCENT_RIGHT("TextAnchor.HALF_ASCENT_RIGHT"),

    /** Middle/left. */
    CENTER_LEFT("TextAnchor.CENTER_LEFT"),

    /** Middle/center. */
    CENTER("TextAnchor.CENTER"),

    /** Middle/right. */
    CENTER_RIGHT("TextAnchor.CENTER_RIGHT"),

    /** Baseline/left. */
    BASELINE_LEFT("TextAnchor.BASELINE_LEFT"),

    /** Baseline/center. */
    BASELINE_CENTER("TextAnchor.BASELINE_CENTER"),

    /** Baseline/right. */
    BASELINE_RIGHT("TextAnchor.BASELINE_RIGHT"),

    /** Bottom/left. */
    BOTTOM_LEFT("TextAnchor.BOTTOM_LEFT"),

    /** Bottom/center. */
    BOTTOM_CENTER("TextAnchor.BOTTOM_CENTER"),

    /** Bottom/right. */
    BOTTOM_RIGHT("TextAnchor.BOTTOM_RIGHT");

    /** The name. */
    private String name;

    /**
     * Private constructor.
     *
     * @param name  the name.
     */
    private TextAnchor(final String name) {
        this.name = name;
    }

    /**
     * Returns a string representing the object.
     *
     * @return The string.
     */
    @Override
    public String toString() {
        return this.name;
    }

}
