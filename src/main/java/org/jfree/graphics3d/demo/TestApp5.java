package org.jfree.graphics3d.demo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.graphics3d.Object3D;
import org.jfree.graphics3d.Panel3D;
import org.jfree.graphics3d.World;

/**
 * A test app.
 */
public class TestApp5 extends JFrame {

    Panel3D panel3D;
    
    /**
     * Creates a new test app.
     * 
     * @param title  the frame title.
     */
    public TestApp5(String title) {
        super(title);
        addWindowListener(new WindowAdapter() {
           public void windowClosing(WindowEvent e) {
               System.exit(0);
           }
        });
        getContentPane().add(createContent());
    }

    JPanel createContent() {
        JPanel content = new JPanel(new BorderLayout());
        content.setPreferredSize(new Dimension(600, 400));
        
        World world = new World();
//        world.addObject(Object3D.createOctahedron(1, -4.0, -4.0, -4.0, Color.red));
//        world.addObject(Object3D.createOctahedron(1, 0.0, -4.0, -4.0, Color.red));
//        world.addObject(Object3D.createOctahedron(1, 4.0, -4.0, -4.0, Color.red));
//        world.addObject(Object3D.createOctahedron(1, -4.0, 0.0, -4.0, Color.red));
        world.add(Object3D.createPieSegment(8.0, 0.0, -1.25, 2.5, 0, Math.PI / 3, Math.PI / 120, Color.red));
        world.add(Object3D.createPieSegment(8.0, 0.0, -1.25, 2.5, Math.PI / 3, Math.PI, Math.PI / 120, Color.green));
        world.add(Object3D.createPieSegment(8.0, 0.0, -1.25, 2.5, Math.PI,  3 * Math.PI / 2, Math.PI / 120, Color.blue));
        world.add(Object3D.createPieSegment(8.0, 3.0, -1.25, 2.5, 3 * Math.PI / 2, 2 * Math.PI, Math.PI / 120, Color.yellow));
//        world.addObject(Object3D.createOctahedron(1, 4.0, 0.0, -4.0, Color.red));
//        world.addObject(Object3D.createOctahedron(1, -4.0, 4.0, -4.0, Color.red));
//        world.addObject(Object3D.createOctahedron(1, 0.0, 4.0, -4.0, Color.red));
//        world.addObject(Object3D.createOctahedron(1, 4.0, 4.0, -4.0, Color.red));
//
//        world.addObject(Object3D.createOctahedron(1, -4.0, -4.0, 0, Color.blue));
//        world.addObject(Object3D.createOctahedron(1, 0.0, -4.0, 0, Color.blue));
//        world.addObject(Object3D.createOctahedron(1, 4.0, -4.0, 0, Color.blue));
//        world.addObject(Object3D.createOctahedron(1, -4.0, 0.0, 0, Color.blue));
//        world.addObject(Object3D.createSphere(2.0, 8, 0.0, 0.0, 0.0, Color.yellow));
//        world.addObject(Object3D.createOctahedron(1, 4.0, 0.0, 0, Color.blue));
//       world.addObject(Object3D.createOctahedron(1, -4.0, 4.0, 0, Color.blue));
        //world.addObject(Object3D.createOctahedron(1, 0.0, 4.0, 0, Color.blue));
//        world.addObject(Object3D.createOctahedron(1, 4.0, 4.0, 0, Color.blue));
//
//        world.addObject(Object3D.createOctahedron(1, -4.0, -4.0, 4.0, Color.green));
//        world.addObject(Object3D.createOctahedron(1, 0.0, -4.0, 4.0, Color.green));
//        world.addObject(Object3D.createOctahedron(1, 4.0, -4.0, 4.0, Color.green));
//        world.addObject(Object3D.createOctahedron(1, -4.0, 0.0, 4.0, Color.green));
//        world.addObject(Object3D.createCube(1, 0.0, 0.0, 4.0, Color.green));
//        world.addObject(Object3D.createOctahedron(1, 4.0, 0.0, 4.0, Color.green));
//        world.addObject(Object3D.createOctahedron(1, -4.0, 4.0, 4.0, Color.green));
//        world.addObject(Object3D.createOctahedron(1, 0.0, 4.0, 4.0, Color.green));
//        world.addObject(Object3D.createOctahedron(1, 4.0, 4.0, 4.0, Color.green));

        
        this.panel3D = new Panel3D(world);
        
        content.add(this.panel3D);
        
        return content;
    }

    /**
     * Starting point for the app.
     * 
     * @param args  command line arguments (ignored).
     */
    public static void main(String[] args) {
        TestApp5 app = new TestApp5("Graphics3D Demo");
        app.pack();
        app.setVisible(true);
    }
}
