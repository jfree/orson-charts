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

package com.orsoncharts;

import java.util.Locale;
import java.util.ResourceBundle;
import com.orsoncharts.util.ArgChecks;

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
            "com.orsoncharts.Resources", Locale.getDefault());
    
    /**
     * Returns the locale that is being used for supplying resources.  The
     * default value is {@code Locale.getDefault()}.
     * 
     * @return The locale (never {@code null}). 
     */
    public static Locale getLocale() {
        return locale;
    }
    
    /**
     * Sets the locale to use for supplying resources.
     * 
     * @param l  the locale ({@code null} not permitted).
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
     * @param key  the key ({@code null} not permitted).
     * 
     * @return A localised string. 
     */
    public static String localString(String key) {
        return resources.getString(key);
    }
    
}
