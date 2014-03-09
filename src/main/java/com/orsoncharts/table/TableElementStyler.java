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

package com.orsoncharts.table;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

/**
 * A table element visitor that applies style changes.
 * 
 * @since 1.2
 */
public class TableElementStyler implements TableElementVisitor {

    private Map<String, Font> fontChanges;
    
    private Map<String, Color> foregroundColorChanges;
    
    private Map<String, Color> backgroundColorChanges;
    
    /**
     * Creates a new styler.
     * 
     * @param fontChanges
     * @param fgChanges
     * @param bgChanges 
     */
    public TableElementStyler(Map<String, Font> fontChanges, 
            Map<String, Color> fgChanges, Map<String, Color> bgChanges) {
        this.fontChanges = new HashMap<String, Font>(fontChanges);
        this.foregroundColorChanges = fgChanges;
        this.backgroundColorChanges = bgChanges;
    }
    
    @Override
    public void visit(TableElement element) {
        if (element instanceof TextElement) {
            TextElement te = (TextElement) element;
            Font f = this.fontChanges.get(te.getTag());
            if (f != null) {
                te.setFont(f);
            }
            Color bg = this.backgroundColorChanges.get(te.getTag());
            if (bg != null) {
                te.setBackgroundColor(bg);
            }
            Color fg = this.foregroundColorChanges.get(te.getTag());
            if (fg != null) {
                te.setColor(fg);
            }
        }
    }
    
}
