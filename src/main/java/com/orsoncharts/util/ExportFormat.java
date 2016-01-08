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
 * An enumeration of the different export formats supported by Orson Charts.
 * PNG and JPEG export are provided by the Java standard library.  PDF export
 * is enabled when Orson PDF is available on the classpath, and SVG export is
 * enabled when JFreeSVG is available on the classpath.
 * 
 * @since 1.2
 */
public enum ExportFormat {
    
    /** The PNG image format. */
    PNG,
    
    /** The JPEG image format. */
    JPEG,
    
    /** The Acrobat Portable Document Format. */
    PDF,
    
    /** The Scalable Vector Graphics format. */
    SVG;

}
