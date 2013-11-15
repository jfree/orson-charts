/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.graphics3d.swing;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private static Font FONT_AWESOME;
    
    /**
     * Returns a font for "Font Awesome" (http://fontawesome.io) at the 
     * specified size.
     * 
     * @param size  the point size.
     * 
     * @return A font. 
     */
    public static Font getFontAwesomeFont(int size) {
        if (FONT_AWESOME == null) {
            try {
                InputStream in = DisplayPanel3D.class.getResourceAsStream(
                        "fontawesome-webfont.ttf");
                FONT_AWESOME = Font.createFont(Font.TRUETYPE_FONT, in);
            } catch (FontFormatException ex) {
                Logger.getLogger(Panel3D.class.getName()).log(Level.SEVERE, 
                        null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Panel3D.class.getName()).log(Level.SEVERE, 
                        null, ex);
            }
        }
        return FONT_AWESOME.deriveFont(Font.PLAIN, size);
    }

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
            add(tb, BorderLayout.EAST);
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
        Font font = getFontAwesomeFont(FONT_SIZE);
        JButton zoomInButton = new JButton(new ZoomInAction(this.content, 
                true));
        zoomInButton.setFont(font);
        JButton zoomOutButton = new JButton(new ZoomOutAction(this.content, 
                true));
        zoomOutButton.setFont(font);
        JButton zoomToFitButton = new JButton(new ZoomToFitAction(this.content, 
                true));
        zoomToFitButton.setFont(font);
        JButton leftButton = new JButton(new LeftAction(content));
        leftButton.setFont(font);
        JButton rightButton = new JButton(new RightAction(content));
        rightButton.setFont(font);
        JButton upButton = new JButton(new UpAction(content));
        upButton.setFont(font);
        JButton downButton = new JButton(new DownAction(content));
        downButton.setFont(font);
        JButton rotateLeftButton = new JButton(new RollLeftAction(content));
        rotateLeftButton.setFont(font);
        JButton rotateRightButton = new JButton(new RollRightAction(content));
        rotateRightButton.setFont(font);
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
        return tb;   
    }
    
    private JPopupMenu createPopupMenu() {
        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.add(new JMenuItem(new ZoomInAction(this.content, false)));
        popupMenu.add(new JMenuItem(new ZoomOutAction(this.content, false)));
        popupMenu.add(new JMenuItem(new ZoomToFitAction(this.content, false)));
        popupMenu.addSeparator();
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
        popupMenu.add(exportSubMenu);
        return popupMenu;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        // nothing to do
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
