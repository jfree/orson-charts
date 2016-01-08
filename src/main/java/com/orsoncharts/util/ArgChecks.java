/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-2016, by Object Refinery Limited.  All rights reserved.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 * 
 * If you do not wish to be bound by the terms of the GPL, an alternative
 * commercial license can be purchased.  For details, please see visit the
 * Orson Charts home page:
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 */

package com.orsoncharts.util;

/**
 * Utility methods for argument checking.  Throughout Orson Charts, arguments
 * passed to methods are validated and exceptions thrown for invalid cases
 * (the idea is to fail fast, which usually helps when tracking down errors
 * in programming logic).
 */
public final class ArgChecks {

    private ArgChecks() {
        // no need to instantiate this ever
    }
 
    /**
     * Checks if the specified argument is {@code null} and, if it is,
     * throws an {@code IllegalArgumentException}.
     *
     * @param arg  the argument to check ({@code null} permitted).
     * @param name  the parameter name ({@code null} not permitted).
     */
    public static void nullNotPermitted(Object arg, String name) {
        if (arg == null) {
            throw new IllegalArgumentException("Null '" + name + "' argument.");
        }
    }
    
    /**
     * Checks if the specified argument is negative and, if it is, throws an
     * {@code IllegalArgumentException}.
     * 
     * @param value  the value.
     * @param name  the parameter name ({@code null} not permitted).
     */
    public static void negativeNotPermitted(double value, String name) {
        if (value < 0.0) {
            throw new IllegalArgumentException("Param '" + name 
                    + "' cannot be negative");
        }
    }

    /**
     * Checks if the specified argument is positive and, if it is NOT, throws an
     * {@code IllegalArgumentException}.
     * 
     * @param value  the value.
     * @param name  the parameter name ({@code null} not permitted).
     */
    public static void positiveRequired(double value, String name) {
        if (value <= 0.0) {
            throw new IllegalArgumentException("Param '" + name 
                    + "' must be positive.");
        } 
    }
    
    /**
     * Checks if the specified argument is finite and, if it is NOT, throws an
     * {@code IllegalArgumentException}.
     * 
     * @param value  the value.
     * @param name  the parameter name ({@code null} not permitted).
     * 
     * @since 1.4
     */
    public static void finiteRequired(double value, String name) {
        if (Double.isInfinite(value)) {
            throw new IllegalArgumentException("Param '" + name 
                    + "' must be finite.");
        }
    }

    /**
     * Checks if the specified argument is finite and positive and, 
     * if it is NOT, throws an {@code IllegalArgumentException}.
     * 
     * @param value  the value.
     * @param name  the parameter name ({@code null} not permitted).
     * 
     * @since 1.4
     */
    public static void finitePositiveRequired(double value, String name) {
        if (value <= 0.0 || Double.isInfinite(value)) {
            throw new IllegalArgumentException("Param '" + name 
                    + "' must be finite and positive.");
        }
    }

    /**
     * Checks that the index is less than the specified {@code arrayLimit}
     * and throws an {@code IllegalArgumentException} if it is not.
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
