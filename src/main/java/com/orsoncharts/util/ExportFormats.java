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
 * Utility methods related to export formats.
 * 
 * @since 1.2
 */
public class ExportFormats {

    /**
     * Returns <code>true</code> if JFreeSVG is on the classpath, and 
     * <code>false</code> otherwise.  The JFreeSVG library can be found at
     * http://www.jfree.org/jfreesvg/
     * 
     * @return A boolean.
     * 
     * @since 1.2
     */
    public static boolean isJFreeSVGAvailable() {
        Class<?> svgClass = null;
        try {
            svgClass = Class.forName("org.jfree.graphics2d.svg.SVGGraphics2D");
        } catch (ClassNotFoundException e) {
            // svgClass will be null so the function will return false
        }
        return (svgClass != null);
    }

    /**
     * Returns <code>true</code> if OrsonPDF is on the classpath, and 
     * <code>false</code> otherwise.  The OrsonPDF library can be found at
     * http://www.object-refinery.com/pdf/
     * 
     * @return A boolean.
     */
    public static boolean isOrsonPDFAvailable() {
        Class<?> pdfDocumentClass = null;
        try {
            pdfDocumentClass = Class.forName("com.orsonpdf.PDFDocument");
        } catch (ClassNotFoundException e) {
            // pdfDocument class will be null so the function will return false
        }
        return (pdfDocumentClass != null);
    }
    
}
