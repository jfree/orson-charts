/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-present, by David Gilbert.  All rights reserved.
 * 
 * https://github.com/jfree/orson-charts
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
 * license is available to sponsors (higher tiers only) of the JFree projects.
 * For details, please see visit:
 *
 * https://github.com/sponsors/jfree
 * 
 */

package org.jfree.chart3d.export;

/**
 * An enumeration of the different export formats supported by Orson Charts.
 * PNG and JPEG export are provided by the Java standard library.  PDF export
 * is enabled when JFreePDF is available on the classpath, and SVG export is
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
    SVG

}
