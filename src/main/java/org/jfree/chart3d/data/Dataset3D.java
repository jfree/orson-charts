/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jfree.chart3d.data;

import org.jfree.chart3d.event.Dataset3DChangeListener;

/**
 * The base interface for datasets in JFreeChart3D.  All datasets must support
 * the change event notification mechanism.
 */
public interface Dataset3D {
    
  void addChangeListener(Dataset3DChangeListener listener);  
  void removeChangeListener(Dataset3DChangeListener listener);  

}
