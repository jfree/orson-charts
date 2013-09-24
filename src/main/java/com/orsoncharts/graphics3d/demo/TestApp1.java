package com.orsoncharts.graphics3d.demo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.orsoncharts.graphics3d.Object3D;
import com.orsoncharts.graphics3d.swing.Panel3D;
import com.orsoncharts.graphics3d.ViewPoint3D;
import com.orsoncharts.graphics3d.World;
import com.orsoncharts.graphics3d.DefaultDrawable3D;
import com.orsoncharts.graphics3d.Drawable3D;

/**
 * A test app.
 */
public class TestApp1 extends JFrame implements ChangeListener {
    
    Panel3D panel3D;
    
    JSlider sliderTheta;
    
    JSlider sliderRho;
    
    JSlider sliderPhi;
    
    /**
     * Creates a new test app.
     * 
     * @param title  the frame title.
     */
    public TestApp1(String title) {
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
        //orld.setViewPoint(new ViewPoint3D(0.0f, 0.4f, 10.0f));
//        world.addObject(Object3D.createCube(3, 0.0, 0.0, -20.0, Color.red));
//        world.addObject(Object3D.createCube(2, 0.0, 0.0, -10, Color.green));
//        world.addObject(Object3D.createCube(1, 0.0, 0.0, 0, Color.blue));
        world.add(Object3D.createCube(1, -4.0, -4.0, -4.0, Color.red));
        world.add(Object3D.createCube(1, 0.0, -4.0, -4.0, Color.red));
        world.add(Object3D.createCube(1, 4.0, -4.0, -4.0, Color.red));
        world.add(Object3D.createCube(1, -4.0, 0.0, -4.0, Color.red));
        world.add(Object3D.createCube(1, 0.0, 0.0, -4.0, Color.red));
        world.add(Object3D.createCube(1, 4.0, 0.0, -4.0, Color.red));
        world.add(Object3D.createCube(1, -4.0, 4.0, -4.0, Color.red));
        world.add(Object3D.createCube(1, 0.0, 4.0, -4.0, Color.red));
        world.add(Object3D.createCube(1, 4.0, 4.0, -4.0, Color.red));

        world.add(Object3D.createCube(1, -4.0, -4.0, 0, Color.blue));
        world.add(Object3D.createCube(1, 0.0, -4.0, 0, Color.blue));
        world.add(Object3D.createCube(1, 4.0, -4.0, 0, Color.blue));
        world.add(Object3D.createCube(1, -4.0, 0.0, 0, Color.blue));
        world.add(Object3D.createCube(1, 0.0, 0.0, 0, Color.yellow));
        world.add(Object3D.createCube(1, 4.0, 0.0, 0, Color.blue));
        world.add(Object3D.createCube(1, -4.0, 4.0, 0, Color.blue));
        world.add(Object3D.createCube(1, 0.0, 4.0, 0, Color.blue));
        world.add(Object3D.createCube(1, 4.0, 4.0, 0, Color.blue));

        world.add(Object3D.createCube(1, -4.0, -4.0, 4.0, Color.green));
        world.add(Object3D.createCube(1, 0.0, -4.0, 4.0, Color.green));
        world.add(Object3D.createCube(1, 4.0, -4.0, 4.0, Color.green));
        world.add(Object3D.createCube(1, -4.0, 0.0, 4.0, Color.green));
        world.add(Object3D.createCube(1, 0.0, 0.0, 4.0, Color.green));
        world.add(Object3D.createCube(1, 4.0, 0.0, 4.0, Color.green));
        world.add(Object3D.createCube(1, -4.0, 4.0, 4.0, Color.green));
        world.add(Object3D.createCube(1, 0.0, 4.0, 4.0, Color.green));
        world.add(Object3D.createCube(1, 4.0, 4.0, 4.0, Color.green));
        
        Drawable3D drawable = new DefaultDrawable3D(world);
        this.panel3D = new Panel3D(drawable);
        content.add(this.panel3D);
        
        JPanel controls = new JPanel(new GridLayout(1, 3));
        
        this.sliderTheta = new JSlider(0, 100);
        this.sliderTheta.addChangeListener(this);        
        controls.add(this.sliderTheta);
        
        this.sliderRho = new JSlider(5, 100);
        this.sliderRho.addChangeListener(this);
        controls.add(this.sliderRho);
        
        this.sliderPhi = new JSlider(0, 100);
        this.sliderPhi.addChangeListener(this);
        controls.add(this.sliderPhi);
        
        //controls.add(this.sliderPhi);
        content.add(controls, BorderLayout.SOUTH);
        
        return content;
    }

    /* (non-Javadoc)
     * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
     */
    public void stateChanged(ChangeEvent e) {
        int valTheta = this.sliderTheta.getValue();
        int valRho = this.sliderRho.getValue();
        int valPhi = this.sliderPhi.getValue();
        ViewPoint3D vp = this.panel3D.getViewPoint();
        float theta = valTheta * (float) (Math.PI / 100);
        float rho = valRho * 1f;
        float phi = valPhi * (float) (2 * Math.PI / 100);
        vp.setTheta(theta);
        vp.setRho(rho);
        vp.setPhi(phi);
        this.panel3D.setViewPoint(vp);
    }

    /**
     * Starting point for the app.
     * 
     * @param args  command line arguments (ignored).
     */
    public static void main(String[] args) {
        TestApp1 app = new TestApp1("3D Demo 1");
        app.pack();
        app.setVisible(true);
    }
}
