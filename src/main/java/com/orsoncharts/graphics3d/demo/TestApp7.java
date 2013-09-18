package com.orsoncharts.graphics3d.demo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author dgilbert
 */
public class TestApp7 extends JFrame {

    public TestApp7(String title) {
        super(title);
        add(createContent());
    }

    private JPanel createContent() {
        JPanel content = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setPaint(Color.RED);
                g2.setStroke(new BasicStroke(2.5f));
                Line2D line = new Line2D.Double(24, 47, 280, 430);
                //Line2D line = new Line2D.Double(24, 47, 24, 430);
                //Line2D line = new Line2D.Double(24, 47, 240, 47);

                g2.draw(line);
                Line2D perpLine = createPerpendicularLine(line, 50);
                g2.setPaint(Color.GREEN);
                g2.draw(perpLine);
            }

            private Line2D createPerpendicularLine(Line2D line, double size) {
                double dx = line.getX2() - line.getX1();
                double dy = line.getY2() - line.getY1();
                double length = Math.sqrt(dx * dx + dy * dy);
                double pdx = dy / length;
                double pdy = -dx / length;
                Point2D pt1 = line.getP1();
                Point2D pt2 = new Point2D.Double(pt1.getX() + size * pdx, pt1.getY() + size * pdy);
                return new Line2D.Double(pt1, pt2);
            }
        };
        content.setPreferredSize(new Dimension(600, 600));
        return content;
    }

    public static void main(String[] args) {
      TestApp7 app = new TestApp7("TestApp7");
      app.pack();
      app.setVisible(true);
    }
}
