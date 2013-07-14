package org.jfree.graphics3d;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

/**
 * A panel that displays a set of 3D objects from some viewing point.
 */
public class HiddenLinesPanel3D extends JPanel {
    
    ViewPoint3D viewPoint;
    
    /** A list of 3D objects which we'll compose and display. */
    private List objects;
    
    public HiddenLinesPanel3D(ViewPoint3D viewPoint, List objects) {
        this.viewPoint = viewPoint;
        this.objects = objects;    
    }
    
    public void setViewPoint(ViewPoint3D vp) {
        this.viewPoint = vp;
        repaint();
    }
    
    /**
     * Draws the parts of the line PQ that are visible with respect to the
     * supplied set of triangles.
     * 
     * @param g2
     * @param eyeCoords
     * @param screenCoords
     * @param p
     * @param q
     * @param pScr
     * @param qScr
     * @param iP  the vertex index of point p (specify -1 if p is calculated 
     *            and doesn't fall on a vertex).
     * @param iQ  the vertex index of point q (specify -1 if q is calculated
     *            and doesn't fall on a vertex).
     * @param triangles
     * @param start
     */
    private void doLineSegment(Graphics2D g2, Point3D[] eyeCoords, 
            Point2D[] screenCoords, Point3D p, Point3D q, Point2D pScr, 
            Point2D qScr, int iP, int iQ, Triangle[] triangles, int start) {
        double px = pScr.getX(); double py = pScr.getY();
        double qx = qScr.getX(); double qy = qScr.getY();
        double u1 = qx - px;
        double u2 = qy - py;
        double minPQx = Math.min(px, qx);
        double minPQy = Math.min(py, qy);
        double maxPQx = Math.max(px, qx);
        double maxPQy = Math.max(py, qy);
        double zP = p.z;
        double zQ = q.z;
        double minPQz = Math.min(zP, zQ);
        for (int i = start; i < triangles.length; i++) {
            Triangle t = triangles[i];
            int iA = t.getA(); int iB = t.getB(); int iC = t.getC();
            Point2D aScr = screenCoords[iA]; 
            Point2D bScr = screenCoords[iB];
            Point2D cScr = screenCoords[iC];
            double ax = aScr.getX(); double ay = aScr.getY();
            double bx = bScr.getX(); double by = bScr.getY();
            double cx = cScr.getX(); double cy = cScr.getY();
            
            // 1. Minimax test for x and y screen coordinates
            if (maxPQx <= ax && maxPQx <= bx && maxPQx <= cx 
                    || minPQx >= ax && minPQx >= bx && minPQx >= cx
                    || maxPQy <= ay && maxPQy <= by && maxPQy <= cy
                    || minPQy >= ay && minPQy >= by && minPQy >= cy) {
                continue;  // this triangle does not obscure PQ
            }
            
            // 2. Test if PQ is an edge of ABC
            if (iP == iA || iP == iB || iP == iC
                    && iQ == iA || iQ == iB || iQ == iC) {
                continue;  // this triangle does not obscure PQ
            }
            
            // 3. Test if PQ is clearly nearer than ABC
            double zA = eyeCoords[iA].z; 
            double zB = eyeCoords[iB].z;
            double zC = eyeCoords[iC].z;
            if (minPQz >= zA && minPQz >= zB && minPQz >= zC) {
                continue;  // this triangle does not obscure PQ
            }
            
            // 4. Do P and Q (in 2D) lie in a half plane defined by AB, on the 
            //    other side from C?  Likewise with BC and CA?
            double eps = 0.1;
            if (Tools2D.area2(aScr, bScr, pScr) < eps 
                    && Tools2D.area2(aScr, bScr, qScr) < eps 
                    || Tools2D.area2(bScr, cScr, pScr) < eps 
                    && Tools2D.area2(bScr, cScr, qScr) < eps 
                    || Tools2D.area2(cScr, aScr, pScr) < eps 
                    && Tools2D.area2(cScr, aScr, qScr) < eps) {
                continue;  // this triangle does not obscure PQ
            }
            
            // 5. Do A, B and C lie on the same side of the (extended) line
            //    defined by PQ?
            double pqa = Tools2D.area2(pScr, qScr, aScr);
            double pqb = Tools2D.area2(pScr, qScr, bScr);
            double pqc = Tools2D.area2(pScr, qScr, cScr);
            
            if (pqa < +eps && pqb < + eps && pqc < +eps 
                    || pqa > -eps && pqb > -eps && pqc > -eps) {
                continue;  // this triangle does not obscure PQ
            }
            
            // 6. Do both P and Q lie in front of the (extended) plane defined
            //    by A, B and C.
            
            // 7. Are both P and Q behind the triangle ABC?
            boolean pInside = Tools2D.insideTriangle(aScr, bScr, cScr, pScr);
            boolean qInside = Tools2D.insideTriangle(aScr, bScr, cScr, qScr);
            if (pInside && qInside) {
                return;  // this triangle completely obscures PQ
            }
            
            // 8. If P is nearer than ABC, and inside, PQ is visible; Likewise
            //    for Q.
            
            // 9. Compute the intersection of I and J of PQ with ABC in 2D...
            
            return;
        }
        // no triangle obscures the line, so we draw it
        g2.draw(new Line2D.Double(pScr, qScr));
    }
    
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_ROUND, 
                BasicStroke.JOIN_ROUND));
        Dimension dim = getSize();
        g2.setPaint(Color.white);
        g2.fillRect(0, 0, dim.width, dim.height);
        g2.setPaint(Color.blue);
        AffineTransform saved = g2.getTransform();
        g2.translate(dim.width / 2, dim.height / 2);
        Map hints = new HashMap();
        hints.put(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.addRenderingHints(hints);
        int objectCount = this.objects.size();
        for (int i = 0; i < objectCount; i++) {
            //Object3D obj = (Object3D) this.objects.get(i);
            
            //List frameLines = obj.getWireFrameLines(this.viewPoint, -1000f);
            
            
        }
        g2.setTransform(saved);
    }

}
