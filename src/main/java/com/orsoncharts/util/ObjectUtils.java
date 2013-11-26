/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.util;

import java.awt.GradientPaint;
import java.awt.Paint;

/**
 * Some general utility methods for working with objects.
 */
public class ObjectUtils {
    
    /**
     * Returns <code>true</code> if the objects are equal or both 
     * <code>null</code>. 
     * 
     * @param obj1  object 1 (<code>null</code> permitted).
     * @param obj2  object 2 (<code>null</code> permitted).
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
     * <code>null</code>.  This method is provided in the Objects class in 
     * Java 1.7, but you can use this one to run on Java 1.6.
     * 
     * @param obj (<code>null</code> permitted).
     * 
     * @return A hash code.
     * 
     * @since 1.1
     */
    public static int hashCode(Object obj) {
        return obj != null ? obj.hashCode() : 0;
    }
    
    /**
     * Returns <code>true</code> if the two <code>Paint</code> objects are equal 
     * OR both <code>null</code>.  This method handles
     * <code>GradientPaint</code> as a special case.
     *
     * @param p1  paint 1 (<code>null</code> permitted).
     * @param p2  paint 2 (<code>null</code> permitted).
     *
     * @return A boolean.
     */
    public static boolean equalsPaint(Paint p1, Paint p2) {

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
        }
        return p1.equals(p2);
    }

}
