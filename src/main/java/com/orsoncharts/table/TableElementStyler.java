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
     * Creates a new styler.  The keys in each map are tag values - each 
     * element with a matching tag will have the associated value applied
     * to it.
     * 
     * @param fontChanges  a map of font changes.
     * @param fgChanges  a map of foreground color changes.
     * @param bgChanges  a map of background color changes.
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
