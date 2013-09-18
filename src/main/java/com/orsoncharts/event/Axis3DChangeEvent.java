/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package com.orsoncharts.event;

import java.util.EventObject;
import com.orsoncharts.axis.Axis3D;

/**
 * A change event...
 */
public class Axis3DChangeEvent extends EventObject {
  
  private Axis3D axis;
  
  public Axis3DChangeEvent(Object source, Axis3D axis) {
    super(source);
    this.axis = axis;
  }
  
  public Axis3D getAxis() {
    return this.axis;
  }
}
