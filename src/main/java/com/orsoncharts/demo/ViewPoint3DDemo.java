/* ===================
 * Orson Charts - Demo
 * ===================
 * 
 * Copyright (c) 2013, 2014, Object Refinery Limited.
 * All rights reserved.
 *
 * http://www.object-refinery.com/orsoncharts/index.html
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *   - Neither the name of the Object Refinery Limited nor the
 *     names of its contributors may be used to endorse or promote products
 *     derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL OBJECT REFINERY LIMITED BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * Note that the above terms apply to the demo source only, and not the 
 * Orson Charts library.
 * 
 */

package com.orsoncharts.demo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import com.orsoncharts.graphics3d.DefaultDrawable3D;
import com.orsoncharts.graphics3d.Object3D;
import com.orsoncharts.graphics3d.Point3D;
import com.orsoncharts.graphics3d.Rotate3D;
import com.orsoncharts.graphics3d.ViewPoint3D;
import com.orsoncharts.graphics3d.World;
import com.orsoncharts.graphics3d.swing.DisplayPanel3D;
import com.orsoncharts.graphics3d.swing.Panel3D;

/**
 * A demo of the viewing point.
 */
public class ViewPoint3DDemo extends JFrame {

    List<Point3D> xlist;
    List<Point3D> ylist;
    List<Point3D> zlist;
    Panel3D panel3D;
    
    /**
     * Creates a new test app.
     *
     * @param title  the frame title.
     */
    public ViewPoint3DDemo(String title) {
        super(title);
        addWindowListener(new ExitOnClose());
        getContentPane().add(createDemoPanel());
    }

    /**
     * Returns a panel containing the content for the demo.  This method is
     * used across all the individual demo applications to allow aggregation 
     * into a single "umbrella" demo (OrsonChartsDemo).
     * 
     * @return A panel containing the content for the demo.
     */
    public JPanel createDemoPanel() {
        JPanel content = new JPanel(new BorderLayout());
        content.setPreferredSize(OrsonChartsDemo.DEFAULT_CONTENT_SIZE);
        World world = new World();
        world.add(Object3D.createCube(1.0, 0, 0, 0, Color.BLUE));
        ViewPoint3D vp = new ViewPoint3D(new Point3D(10, 10, 10), 0);
        xlist = addRing(true, world, new Point3D(0, 5, 0), Point3D.UNIT_X, Color.GREEN);
        ylist = addRing(true, world, new Point3D(0, 0, 5), Point3D.UNIT_Y, Color.ORANGE);
        zlist = addRing(true, world, new Point3D(0, 5, 0), Point3D.UNIT_Z, Color.RED);
        DefaultDrawable3D drawable = new DefaultDrawable3D(world);
        this.panel3D = new Panel3D(drawable);
        panel3D.setViewPoint(vp);
        content.add(new DisplayPanel3D(panel3D));
        return content;
    }
    
    private List<Point3D> addRing(boolean b, World world, Point3D pt, Point3D v1, Color color) {
        boolean first = true;
        List<Point3D> result = new ArrayList<Point3D>();
        Rotate3D r = new Rotate3D(Point3D.ORIGIN, v1, 0);
        for (int i = 0; i < 60; i++) {
            r.setAngle(2 * Math.PI / 60 * i);
            Point3D p = r.applyRotation(pt);
            result.add(p);
            if (b) {
                if (first) {
                    world.add(Object3D.createCube(0.20, p.x, p.y, p.z, Color.RED));
                    first = false;
                } else {
                    world.add(Object3D.createCube(0.20, p.x, p.y, p.z, color));                    
                }
            }
        }
        return result;
    }
    
    /**
     * Starting point for the app.
     *
     * @param args  command line arguments (ignored).
     */
    public static void main(String[] args) {
        ViewPoint3DDemo app = new ViewPoint3DDemo(
                "OrsonCharts: ViewPointDemo.java");
        app.pack();
        app.setVisible(true);
    }

}