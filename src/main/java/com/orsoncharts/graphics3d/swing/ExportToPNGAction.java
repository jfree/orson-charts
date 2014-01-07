/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * Redistribution of these source files is prohibited.
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
import com.orsoncharts.util.ArgChecks;

/**
 * An action that handles saving the content of a panel to a PNG image.
 */
public class ExportToPNGAction extends AbstractAction {

    /** The panel to which this action applies. */
    private Panel3D panel;
  
    /**
     * Creates a new action instance.
     * 
     * @param panel  the panel (<code>null</code> not permitted).
     */
    public ExportToPNGAction(Panel3D panel) {
        super("PNG...");
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
                    "PNG Image Files", "png");
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setFileFilter(filter);

        int option = fileChooser.showSaveDialog(this.panel);
        if (option == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser.getSelectedFile().getPath();
            if (!filename.endsWith(".png")) {
                filename = filename + ".png";
            }
            Dimension2D size = this.panel.getSize();
            int w = (int) size.getWidth();
            int h = (int) size.getHeight();
            BufferedImage image = new BufferedImage(w, h, 
                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = image.createGraphics();
            this.panel.getDrawable().draw(g2, new Rectangle(w, h));
            try {
                ImageIO.write(image, "png", new File(filename));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    
}
