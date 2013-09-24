package com.orsoncharts.graphics3d.demo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.orsoncharts.graphics3d.DefaultDrawable3D;
import com.orsoncharts.graphics3d.Object3D;
import com.orsoncharts.graphics3d.swing.Panel3D;
import com.orsoncharts.graphics3d.World;

/**
 * A test app.
 */
public class TestApp4 extends JFrame {


    Panel3D panel3D;
    
    /**
     * Creates a new test app.
     * 
     * @param title  the frame title.
     */
    public TestApp4(String title) {
        super(title);
        addWindowListener(new WindowAdapter() {
           @Override
           public void windowClosing(WindowEvent e) {
               System.exit(0);
           }
        });
        getContentPane().add(createContent());
    }

    final JPanel createContent() {
        JPanel content = new JPanel(new BorderLayout());
        content.setPreferredSize(new Dimension(600, 400));
        
        World world = new World();
//        world.addObject(Object3D.createOctahedron(1, -4.0, -4.0, -4.0, Color.red));
//        world.addObject(Object3D.createOctahedron(1, 0.0, -4.0, -4.0, Color.red));
//        world.addObject(Object3D.createOctahedron(1, 4.0, -4.0, -4.0, Color.red));
//        world.addObject(Object3D.createOctahedron(1, -4.0, 0.0, -4.0, Color.red));
        world.add(Object3D.createCube(1, 0.0, 0.0, -4.0, Color.RED));
//        world.addObject(Object3D.createOctahedron(1, 4.0, 0.0, -4.0, Color.red));
//        world.addObject(Object3D.createOctahedron(1, -4.0, 4.0, -4.0, Color.red));
//        world.addObject(Object3D.createOctahedron(1, 0.0, 4.0, -4.0, Color.red));
//        world.addObject(Object3D.createOctahedron(1, 4.0, 4.0, -4.0, Color.red));
//
        world.add(Object3D.createOctahedron(1, -4.0, -4.0, 0, Color.BLUE));
        world.add(Object3D.createOctahedron(1, 0.0, -4.0, 0, Color.BLUE));
        world.add(Object3D.createOctahedron(1, 4.0, -4.0, 0, Color.BLUE));
        world.add(Object3D.createOctahedron(1, -4.0, 0.0, 0, Color.BLUE));
        world.add(Object3D.createSphere(2.0, 16, 0.0, 0.0, 0.0, Color.LIGHT_GRAY, Color.YELLOW));
        world.add(Object3D.createOctahedron(1, 4.0, 0.0, 0, Color.BLUE));
       world.add(Object3D.createOctahedron(1, -4.0, 4.0, 0, Color.BLUE));
        //world.addObject(Object3D.createOctahedron(1, 0.0, 4.0, 0, Color.blue));
        world.add(Object3D.createOctahedron(1, 4.0, 4.0, 0, Color.BLUE));
        
     //   world.addObject(Object3D.createSphere(3.0, 30, 2, 4, 5, Color.green));
//
//        world.addObject(Object3D.createOctahedron(1, -4.0, -4.0, 4.0, Color.green));
//        world.addObject(Object3D.createOctahedron(1, 0.0, -4.0, 4.0, Color.green));
//        world.addObject(Object3D.createOctahedron(1, 4.0, -4.0, 4.0, Color.green));
//        world.addObject(Object3D.createOctahedron(1, -4.0, 0.0, 4.0, Color.green));
        world.add(Object3D.createCube(2, 0.0, 0.0, 4.0, Color.MAGENTA));
//        world.addObject(Object3D.createOctahedron(1, 4.0, 0.0, 4.0, Color.green));
//        world.addObject(Object3D.createOctahedron(1, -4.0, 4.0, 4.0, Color.green));
//        world.addObject(Object3D.createOctahedron(1, 0.0, 4.0, 4.0, Color.green));
//        world.addObject(Object3D.createOctahedron(1, 4.0, 4.0, 4.0, Color.green));
//world.addObject(Object3D.createSheet(3, 1.0, 2.0, 3.0, Color.yellow));
        
        this.panel3D = new Panel3D(new DefaultDrawable3D(world));        
        content.add(this.panel3D);
        return content;
    }

    /**
     * Starting point for the app.
     * 
     * @param args  command line arguments (ignored).
     */
    public static void main(String[] args) {
        TestApp4 app = new TestApp4("Graphics3D Demo");
        app.pack();
        app.setVisible(true);
    }
}
