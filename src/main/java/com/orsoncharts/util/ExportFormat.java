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
