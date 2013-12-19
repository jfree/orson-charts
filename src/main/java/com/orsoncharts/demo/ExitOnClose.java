/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */
package com.orsoncharts.demo;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Shuts down the JVM when the demo window is closed.
 */
public class ExitOnClose extends WindowAdapter {

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }
    
}
