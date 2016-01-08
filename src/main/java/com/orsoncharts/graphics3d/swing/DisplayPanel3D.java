/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-2016, by Object Refinery Limited.  All rights reserved.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 * 
 * If you do not wish to be bound by the terms of the GPL, an alternative
 * commercial license can be purchased.  For details, please see visit the
 * Orson Charts home page:
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
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

import com.orsoncharts.Resources;
import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.util.ExportFormat;
import com.orsoncharts.util.ExportFormats;

/**
 * A panel for displaying 3D content, with a toolbar and popup menu to control 
 * the view.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. 
 */
@SuppressWarnings("serial")
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
     * @param content  the content ({@code null} not permitted). 
     */
    public DisplayPanel3D(Panel3D content) {
        this(content, true, true);
    }
    
    /** 
     * Creates a new display panel.
     * 
     * @param content  the content ({@code null} not permitted).
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
            this.popup = createPopupMenu(ExportFormat.values());
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
     * Sets the list of export formats that will be shown in the popup menu.
     * If you provide an empty list, there will be no export submenu in the 
     * popup menu.
     * 
     * @param formats  the list of formats ({@code null} not permitted). 
     * 
     * @since 1.2
     */
    public void setExportFormats(ExportFormat... formats) {
        // defer argument checking
        this.popup = createPopupMenu(formats);
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
    
    /**
     * Creates a popup menu containing zooming items plus, if exportFormats
     * is not empty, a submenu of export items.
     * 
     * @param exportFormats  an ordered list of export formats to add to the
     *     submenu ({@code null} not permitted).
     * 
     * @return A popup menu.
     */
    private JPopupMenu createPopupMenu(ExportFormat... exportFormats) {
        ArgChecks.nullNotPermitted(exportFormats, "exportFormats");
        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.add(new JMenuItem(new ZoomInAction(this.content, false)));
        popupMenu.add(new JMenuItem(new ZoomOutAction(this.content, false)));
        popupMenu.add(new JMenuItem(new ZoomToFitAction(this.content, false)));
        
        if (exportFormats.length > 0) {
            JMenu exportSubMenu = new JMenu(Resources.localString("EXPORT_AS"));
            for (ExportFormat f : exportFormats) {
                if (f.equals(ExportFormat.PNG)) {
                    JMenuItem pngItem = new JMenuItem(new ExportToPNGAction(
                            this.content));
                    exportSubMenu.add(pngItem);                    
                } else if (f.equals(ExportFormat.JPEG)) {
                    JMenuItem jpgItem = new JMenuItem(new ExportToJPEGAction(
                            this.content));
                    exportSubMenu.add(jpgItem);                    
                } else if (f.equals(ExportFormat.PDF)) {
                    if (ExportFormats.isOrsonPDFAvailable()) {
                        JMenuItem pdfItem = new JMenuItem(new ExportToPDFAction(
                                this.content));
                        exportSubMenu.add(pdfItem);
                    }
                } else if (f.equals(ExportFormat.SVG)) {
                    if (ExportFormats.isJFreeSVGAvailable()) {
                        JMenuItem svgItem = new JMenuItem(new ExportToSVGAction(
                                this.content));
                        exportSubMenu.add(svgItem);
                    }
                }
            }
            if (exportSubMenu.getItemCount() > 0) {
                popupMenu.addSeparator();
                popupMenu.add(exportSubMenu);           
            }
        }
        return popupMenu;
    }
    
    /**
     * This method does nothing.  
     * 
     * @param e  the mouse event.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        // nothing to do
    }

    /**
     * Checks if the popup is triggered in which case it is displayed.
     * 
     * @param e  the mouse event. 
     */
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

    /**
     * Checks if the popup is triggered in which case it is displayed.
     * 
     * @param e  the mouse event. 
     */
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

    /**
     * This method does nothing.  
     * 
     * @param e  the mouse event.
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        // nothing to do
    }

    /**
     * This method does nothing.  
     * 
     * @param e  the mouse event.
     */
    @Override
    public void mouseExited(MouseEvent e) {
        // nothing to do
    }

}
