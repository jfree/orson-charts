/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts;

/**
 * A holder for information about a sequence of tick values.
 */
public class TickInfo {
    
    /** The range of values for the ticks. */
    private Range range;
    
    /** The number of ticks (min 2). */
    private int count;
    
    public static TickInfo calculateTickInfo(Range range, double tickUnit) {
        double min = range.firstStandardTickValue(tickUnit);
        int count = 0;
        if (min < range.getMax()) {
            count = (int) ((range.getMax() - min) / tickUnit) + 1;
        }
        return new TickInfo(new Range(min, min + (count - 1) * tickUnit), count);
    }
    
    public TickInfo(Range range, int count) {
        this.range = range;
        this.count = count;
    }
    
    public Range getRange() {
        return this.range;
    }
    
    public int getCount() {
        return this.count;
    }
    
}
