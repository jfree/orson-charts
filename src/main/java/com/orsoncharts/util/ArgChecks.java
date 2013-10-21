/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.util;

/**
 * Utility methods for argument checking.
 */
public class ArgChecks {

    /**
     * Checks if the specified argument is <code>null</code> and, if it is,
     * throws an <code>IllegalArgumentException</code>.
     *
     * @param arg  the argument to check (<code>null</code> permitted).
     * @param name  the parameter name (<code>null</code> not permitted).
     */
    public static void nullNotPermitted(Object arg, String name) {
        if (arg == null) {
            throw new IllegalArgumentException("Null '" + name + "' argument.");
        }
    }
    
    /**
     * Checks if the specified argument is negative and, if it is, throws an
     * <code>IllegalArgumentException</code>.
     * 
     * @param value  the value.
     * @param name  the parameter name (<code>null</code> not permitted).
     */
    public static void negativeNotPermitted(double value, String name) {
        if (value < 0.0) {
            throw new IllegalArgumentException("Param '" + name 
                    + "' cannot be negative");
        }
    }

    /**
     * Checks if the specified argument is positive and, if it is NOT, throws an
     * <code>IllegalArgumentException</code>.
     * 
     * @param value  the value.
     * @param name  the parameter name (<code>null</code> not permitted).
     */
    public static void positiveRequired(double value, String name) {
        if (value <= 0.0) {
            throw new IllegalArgumentException("Param '" + name 
                    + "' must be positive.");
        } 
    }

    /**
     * Checks that the index is less than the specified <code>arrayLimit</code>
     * and throws an <code>IllegalArgumentException</code> if it is not.
     * 
     * @param index  the array index.
     * @param name  the parameter name (to display in the error message).
     * @param arrayLimit  the array size.
     */
    public static void checkArrayBounds(int index, String name, 
            int arrayLimit) {
        if (index >= arrayLimit) {
            throw new IllegalArgumentException("Requires '" + name
                    + "' in the range 0 to " + (arrayLimit - 1));
        }
    }

}
