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

package com.orsoncharts.graphics3d.swing;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * A file filter for SVG files.
 */
public class SVGFileFilter extends FileFilter {
  
    /**
     * Default constructor.
     */
    public SVGFileFilter() {
        super();
    }

    /**
     * Returns <code>true</code> if the file name has a ".svg" suffix, and 
     * <code>false</code> otherwise.
     * 
     * @param f  the file (<code>null</code> not permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean accept(File f) {
      if (f.getName().endsWith(".svg")) {
        return true;
      }
      return false;
    }

    /**
     * Returns the string "Scalable Vector Graphics (SVG)".
     * 
     * @return The string "Scalable Vector Graphics (SVG)".
     */
    @Override
    public String getDescription() {
        return "Scalable Vector Graphics (SVG)";
    }
}
