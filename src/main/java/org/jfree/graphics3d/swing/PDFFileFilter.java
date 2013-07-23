/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package org.jfree.graphics3d.swing;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * File filter for PDF files.
 */
public class PDFFileFilter extends FileFilter {
  
  public PDFFileFilter() {
      
  }

    @Override
    public boolean accept(File f) {
      if (f.getName().endsWith(".pdf")) {
        return true;
      }
      return false;
    }

    @Override
    public String getDescription() {
        return "Portable Document Format (PDF)";
    }
}
