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

import java.awt.GradientPaint;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.util.Arrays;

/**
 * Some general utility methods for working with objects.
 */
public class ObjectUtils {
    
    /**
     * Returns {@code true} if the objects are equal or both {@code null}. 
     * 
     * @param obj1  object 1 ({@code null} permitted).
     * @param obj2  object 2 ({@code null} permitted).
     * 
     * @return A boolean. 
     */
    public static boolean equals(Object obj1, Object obj2) {
        if (obj1 != null) {
            return obj1.equals(obj2);
        } else {
            return obj2 == null;
        }
    }
    
    /**
     * Returns the hash code of the object, or 0 if the object is 
     * {@code null}.  This method is provided in the Objects class in 
     * Java 1.7, but you can use this one to run on Java 1.6.
     * 
     * @param obj ({@code null} permitted).
     * 
     * @return A hash code.
     * 
     * @since 1.1
     */
    public static int hashCode(Object obj) {
        return obj != null ? obj.hashCode() : 0;
    }
    
    /**
     * Returns {@code true} if the two {@code Paint} objects are equal 
     * OR both {@code null}.  This method handles
     * {@code GradientPaint}, {@code LinearGradientPaint} and 
     * {@code RadialGradientPaint} as a special cases, since those classes do
     * not override the {@code equals()} method.
     *
     * @param p1  paint 1 ({@code null} permitted).
     * @param p2  paint 2 ({@code null} permitted).
     *
     * @return A boolean.
     */
    public static boolean equalsPaint(Paint p1, Paint p2) {
        if (p1 == p2) {
            return true;
        }
            
        // handle cases where either or both arguments are null
        if (p1 == null) {
            return (p2 == null);   
        }
        if (p2 == null) {
            return false;   
        }

        // handle GradientPaint as a special case...
        if (p1 instanceof GradientPaint && p2 instanceof GradientPaint) {
            GradientPaint gp1 = (GradientPaint) p1;
            GradientPaint gp2 = (GradientPaint) p2;
            return gp1.getColor1().equals(gp2.getColor1()) 
                    && gp1.getColor2().equals(gp2.getColor2())
                    && gp1.getPoint1().equals(gp2.getPoint1())    
                    && gp1.getPoint2().equals(gp2.getPoint2())
                    && gp1.isCyclic() == gp2.isCyclic()
                    && gp1.getTransparency() == gp1.getTransparency(); 
        } else if (p1 instanceof LinearGradientPaint 
                && p2 instanceof LinearGradientPaint) {
            LinearGradientPaint lgp1 = (LinearGradientPaint) p1;
            LinearGradientPaint lgp2 = (LinearGradientPaint) p2;
            return lgp1.getStartPoint().equals(lgp2.getStartPoint())
                    && lgp1.getEndPoint().equals(lgp2.getEndPoint()) 
                    && Arrays.equals(lgp1.getFractions(), lgp2.getFractions())
                    && Arrays.equals(lgp1.getColors(), lgp2.getColors())
                    && lgp1.getCycleMethod() == lgp2.getCycleMethod()
                    && lgp1.getColorSpace() == lgp2.getColorSpace()
                    && lgp1.getTransform().equals(lgp2.getTransform());
        } else if (p1 instanceof RadialGradientPaint 
                && p2 instanceof RadialGradientPaint) {
            RadialGradientPaint rgp1 = (RadialGradientPaint) p1;
            RadialGradientPaint rgp2 = (RadialGradientPaint) p2;
            return rgp1.getCenterPoint().equals(rgp2.getCenterPoint())
                    && rgp1.getRadius() == rgp2.getRadius() 
                    && rgp1.getFocusPoint().equals(rgp2.getFocusPoint())
                    && Arrays.equals(rgp1.getFractions(), rgp2.getFractions())
                    && Arrays.equals(rgp1.getColors(), rgp2.getColors())
                    && rgp1.getCycleMethod() == rgp2.getCycleMethod()
                    && rgp1.getColorSpace() == rgp2.getColorSpace()
                    && rgp1.getTransform().equals(rgp2.getTransform());
        } else {
            return p1.equals(p2);
        }
    }

}
