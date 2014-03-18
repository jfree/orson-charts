/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * Redistribution of this source file is prohibited.
 * 
 */

package com.orsoncharts.graphics3d.swing;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.geom.Dimension2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.orsoncharts.Resources;
import com.orsoncharts.util.ArgChecks;

/**
 * An action that handles saving the content of a panel to a JPEG image.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. 
 * 
 * @since 1.2
 */
@SuppressWarnings("serial")
public class ExportToJPEGAction extends AbstractAction {

    /** The panel to which this action applies. */
    private Panel3D panel;
  
    /**
     * Creates a new action instance.
     * 
     * @param panel  the panel (<code>null</code> not permitted).
     */
    public ExportToJPEGAction(Panel3D panel) {
        super(Resources.localString("JPG_MENU_LABEL"));
        ArgChecks.nullNotPermitted(panel, "panel");
        this.panel = panel;
    }

    /**
     * Writes the content of the panel to a PNG image, using Java's ImageIO.
     * 
     * @param e  the event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                Resources.localString("JPG_FILE_FILTER_DESCRIPTION"), "jpg");
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setFileFilter(filter);

        int option = fileChooser.showSaveDialog(this.panel);
        if (option == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser.getSelectedFile().getPath();
            if (!filename.endsWith(".jpg")) {
                filename = filename + ".jpg";
            }
            Dimension2D size = this.panel.getSize();
            int w = (int) size.getWidth();
            int h = (int) size.getHeight();
            BufferedImage image = new BufferedImage(w, h, 
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = image.createGraphics();
            this.panel.getDrawable().draw(g2, new Rectangle(w, h));
            try {
                ImageIO.write(image, "jpeg", new File(filename));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    
}
