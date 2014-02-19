/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * Redistribution of this source file is prohibited.
 * 
 */

package com.orsoncharts;

import com.orsoncharts.util.ArgChecks;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Provides centralised access to localised resources.
 * 
 * @since 1.2
 */
public class Resources {
    
    /** The locale for the resources. */
    private static Locale locale = Locale.getDefault();
    
    /** Localised resources. */
    private static ResourceBundle resources = ResourceBundle.getBundle(
            "com.orsoncharts.Resources", locale);
    
    /**
     * Returns the locale that is being used for supplying resources.  The
     * default value is <code>Locale.getDefault()</code>.
     * 
     * @return The locale (never <code>null</code>). 
     */
    public static Locale getLocale() {
        return locale;
    }
    
    /**
     * Sets the locale to use for supplying resources.
     * 
     * @param l  the locale (<code>null</code> not permitted).
     */
    public static void setLocale(Locale l) {
        ArgChecks.nullNotPermitted(l, "l");
        locale = l;
        resources = ResourceBundle.getBundle("com.orsoncharts.Resources",
                locale);
    }
    
    /**
     * Returns a localised string.
     * 
     * @param key  the key (<code>null</code> not permitted).
     * 
     * @return A localised string. 
     */
    public static String localString(String key) {
        return resources.getString(key);
    }
    
}
