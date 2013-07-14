/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package org.jfree.chart3d.data;

import java.util.ArrayList;
import java.util.List;
import org.jfree.graphics3d.ArgChecks;

/**
 * A series of (x, y, z) data items.
 */
public class XYZSeries {

  private String name;

  private List<XYZDataItem> items;

  public XYZSeries(String name) {
    this.name = name;
    this.items = new ArrayList<XYZDataItem>();
  }

  public int getItemCount() {
    return this.items.size();
  }

  public void add(XYZDataItem item) {
    ArgChecks.nullNotPermitted(item, "item");
    this.items.add(item);
  }

}
