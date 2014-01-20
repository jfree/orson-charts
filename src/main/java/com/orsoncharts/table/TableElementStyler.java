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

import java.awt.Font;
import java.awt.Paint;
import java.util.HashMap;
import java.util.Map;

/**
 * A table element visitor that applies style changes.
 * 
 * @since 1.2
 */
public class TableElementStyler implements TableElementVisitor {

    private Map<String, Font> fontChanges;
    
    private Map<String, Paint> foregroundPaintChanges;
    
    private Map<String, Paint> backgroundPaintChanges;
    
    /**
     * Creates a new styler.
     * 
     * @param fontChanges
     * @param fgChanges
     * @param bgChanges 
     */
    public TableElementStyler(Map<String, Font> fontChanges, 
            Map<String, Paint> fgChanges, Map<String, Paint> bgChanges) {
        this.fontChanges = new HashMap(fontChanges);
        this.foregroundPaintChanges = fgChanges;
        this.backgroundPaintChanges = bgChanges;
    }
    
    @Override
    public void visit(TableElement element) {
        if (element instanceof TextElement) {
            TextElement te = (TextElement) element;
            Font f = this.fontChanges.get(te.getTag());
            if (f != null) {
                te.setFont(f);
            }
            Paint bg = this.backgroundPaintChanges.get(te.getTag());
            if (bg != null) {
                te.setBackgroundPaint(bg);
            }
            Paint fg = this.foregroundPaintChanges.get(te.getTag());
            if (fg != null) {
                te.setForegroundPaint(fg);
            }
        }
    }
    
}
