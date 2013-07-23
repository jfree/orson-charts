/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package org.jfree.chart3d.axis;

/**
 * Represents a range of data values.
 */
public class Range {

  private double min;

  private double max;

  public Range(double min, double max) {
    this.min = min;
    this.max = max;
  }

  public double getMin() {
    return this.min;
  }

  public double getMax() {
    return this.max;
  }

  /**
   * Returns the length of the range.
   *
   * @return The length of the range.
   */
  public double getLength() {
    return this.max - this.min;
  }

  public boolean contains(double value) {
    return value >= this.min && value <= this.max;
  }
}
