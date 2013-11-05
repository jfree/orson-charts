/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.graphics3d.swing;

import com.orsoncharts.Chart3D;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import javax.swing.JMenu;

/**
 * A panel for displaying 3D content, with a toolbar and popup menu to control 
 * the view.
 */
public class DisplayPanel3D extends JPanel implements MouseListener {
  
    private static final int FONT_SIZE = 22;

    /** The 3D content. */
    Panel3D content;
    
    /** The popup menu. */
    private JPopupMenu popup;
  
    /**
     * Creates a new display panel for the given content, with a toolbar
     * and popup menu configured.
     * 
     * @param content  the content (<code>null</code> not permitted). 
     */
    public DisplayPanel3D(Panel3D content) {
        this(content, true, true);
    }
    
    /** 
     * Creates a new display panel.
     * 
     * @param content  the content (<code>null</code> not permitted).
     * @param toolbar  toolbar?
     * @param popupMenu  popup menu?
     */
    public DisplayPanel3D(Panel3D content, boolean toolbar, boolean popupMenu) {
        super(new BorderLayout());
        
        this.content = content;
        add(this.content);
        
        if (toolbar) {
            JToolBar tb = createToolBar(content);
            add(tb, BorderLayout.WEST);
        }
        if (popupMenu) {
            this.popup = createPopupMenu();
        }
        this.content.addMouseListener(this);
    }

    /**
     * Returns a reference to the content panel.
     * 
     * @return A reference to the content panel.
     */
    public Panel3D getContent() {
        return this.content;
    }
  
    /**
     * Creates the toolbar used to control zooming etc.
     * 
     * @param content  the 3D content that will be updated by toolbar actions.
     * 
     * @return The toolbar. 
     */
    private JToolBar createToolBar(Panel3D content) {
        JToolBar tb = new JToolBar(JToolBar.VERTICAL);
        JButton zoomInButton = new JButton(new ZoomInAction(this.content, 
                true));
        zoomInButton.setFont(Panel3D.getFontAwesomeFont(FONT_SIZE));
        JButton zoomOutButton = new JButton(new ZoomOutAction(this.content, 
                true));
        zoomOutButton.setFont(Panel3D.getFontAwesomeFont(FONT_SIZE));
        JButton zoomToFitButton = new JButton(new ZoomToFitAction(this.content, 
                true));
        zoomToFitButton.setFont(Panel3D.getFontAwesomeFont(FONT_SIZE));
        JButton leftButton = new JButton(new LeftAction(content));
        leftButton.setFont(Panel3D.getFontAwesomeFont(FONT_SIZE));
        JButton rightButton = new JButton(new RightAction(content));
        rightButton.setFont(Panel3D.getFontAwesomeFont(FONT_SIZE));
        JButton upButton = new JButton(new UpAction(content));
        upButton.setFont(Panel3D.getFontAwesomeFont(FONT_SIZE));
        JButton downButton = new JButton(new DownAction(content));
        downButton.setFont(Panel3D.getFontAwesomeFont(FONT_SIZE));
        JButton rotateLeftButton = new JButton(new RotateLeftAction(content));
        rotateLeftButton.setFont(Panel3D.getFontAwesomeFont(FONT_SIZE));
        JButton rotateRightButton = new JButton(new RotateRightAction(content));
        rotateRightButton.setFont(Panel3D.getFontAwesomeFont(FONT_SIZE));
        JButton exportButton = new JButton(new ExportAction(content));
        exportButton.setFont(Panel3D.getFontAwesomeFont(FONT_SIZE));
        tb.add(zoomInButton);
        tb.add(zoomOutButton);
        tb.add(zoomToFitButton);
        tb.add(new JToolBar.Separator());
        tb.add(leftButton);
        tb.add(rightButton);
        tb.add(upButton);
        tb.add(downButton);
        tb.add(rotateLeftButton);
        tb.add(rotateRightButton);
//        tb.add(new JToolBar.Separator());
//        tb.add(exportButton);
        return tb;   
    }
    
    private JPopupMenu createPopupMenu() {
        JPopupMenu popup = new JPopupMenu();
        popup.add(new JMenuItem(new ZoomInAction(this.content, false)));
        popup.add(new JMenuItem(new ZoomOutAction(this.content, false)));
        popup.add(new JMenuItem(new ZoomToFitAction(this.content, false)));
        popup.addSeparator();
        JMenu exportSubMenu = new JMenu("Export as");
        JMenuItem pngItem = new JMenuItem(new ExportToPNGAction(this.content));
        exportSubMenu.add(pngItem);
            
        if (this.content.isOrsonPDFAvailable()) {
            JMenuItem pdfItem = new JMenuItem(new ExportToPDFAction(
                    this.content));
            exportSubMenu.add(pdfItem);
        }
        
        if (this.content.isJFreeSVGAvailable()) {
            JMenuItem svgItem = new JMenuItem(new ExportToSVGAction(
                    this.content));
            exportSubMenu.add(svgItem);
        }
        popup.add(exportSubMenu);
        return popup;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // popup is triggered on mousePressed for Linux and Mac, but Windows
        // is mouseReleased
        if (e.isPopupTrigger()) {
            if (this.popup != null) {
                this.popup.show(this, e.getX(), e.getY());
                e.consume();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // popup is triggered on mouseReleased for Windows, but Linux and Mac
        // is mousePressed
        if (e.isPopupTrigger()) {
            if (this.popup != null) {
                this.popup.show(this, e.getX(), e.getY());
                e.consume();
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // nothing to do
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // nothing to do
    }

}
