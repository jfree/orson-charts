/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package org.jfree.graphics3d;

/**
 * Utility methods for argument checking.
 */
public class ArgChecks {

  /**
   * Checks if the specified argument is <code>null</code> and, if it is,
   * throws an IllegalArgumentException.
   *
   * @param arg  the argument to check (<code>null</code> permitted).
   * @param name  the argument name (<code>null</code> not permitted).
   */
  public static void nullNotPermitted(Object arg, String name) {
    if (arg == null) {
      throw new IllegalArgumentException("Null '" + name + "' argument.");
    }
  }

  public static void checkArrayBounds(int index, String name, int arrayLimit) {
    if (index >= arrayLimit) {
      throw new IllegalArgumentException("Requires '" + name
              + "' in the range 0 to " + (arrayLimit - 1));
    }
  }

}
