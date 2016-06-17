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

package com.orsoncharts;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;
import com.orsoncharts.graphics3d.RenderedElement;
import com.orsoncharts.graphics3d.RenderingInfo;
import com.orsoncharts.interaction.InteractiveElementType;
import com.orsoncharts.table.TableElement;
import com.orsoncharts.table.TableElementOnDraw;

/**
 * An 'onDraw' handler that handles two aspects related to chart interactivity:
 * (1) it adds a {@link RenderedElement} instance to the {@link RenderingInfo} 
 * for each element in the chart that requires it, and (2) it adds element 
 * hinting to the {@code Graphics2D} output (how this is handled by the
 * {@code Graphics2D} instance is implementation dependent).
 * 
 * @since 1.3
 */
public class OnDrawHandler implements TableElementOnDraw {
    
    /** The rendering info the be populated (if not null). */
    private RenderingInfo info;
    
    /** A flag indicating whether or not element hinting is added. */
    boolean elementHinting;
    
    /**
     * Creates a new handler.
     * 
     * @param info  the rendering info to be populated ({@code null} 
     *     permitted).
     * @param elementHinting  a flag that controls whether or not element 
     *     hinting is performed.
     */
    public OnDrawHandler(RenderingInfo info, boolean elementHinting) {
        this.info = info;
        this.elementHinting = elementHinting;
    }

    @Override
    public void beforeDraw(TableElement element, Graphics2D g2, 
            Rectangle2D bounds) {
        
        InteractiveElementType t = (InteractiveElementType) 
                element.getProperty(TableElement.CLASS);
        
        // handle rendering info
        if (t != null && info != null) {
            switch (t) {
                case TITLE:
                case SUBTITLE:
                    RenderedElement re = new RenderedElement(t, bounds);
                    this.info.addElement(re);
                    break;
                    
                case LEGEND_ITEM:
                    RenderedElement legendItemRE = new RenderedElement(
                            InteractiveElementType.LEGEND_ITEM, bounds);
                    legendItemRE.setProperty(Chart3D.SERIES_KEY, 
                            element.getProperty(Chart3D.SERIES_KEY));
                    this.info.addElement(legendItemRE);
                    break;
                default: 
                    throw new RuntimeException();
            }
        }
        
        // handle hinting
        if (t != null && this.elementHinting) {
            Map<String, String> m = new HashMap<String, String>();
            switch (t) {
                case TITLE:
                    m.put("ref", "{\"type\": \"title\"}");
                    g2.setRenderingHint(Chart3DHints.KEY_BEGIN_ELEMENT, m);
                    break;
                case SUBTITLE:
                    m.put("ref", "{\"type\": \"subtitle\"}");
                    g2.setRenderingHint(Chart3DHints.KEY_BEGIN_ELEMENT, m);
                    break;
                case LEGEND_ITEM:
                    m.put("ref", generateLegendItemRef(element));
                    g2.setRenderingHint(Chart3DHints.KEY_BEGIN_ELEMENT, m);
                    break;
                default:
                    throw new RuntimeException();
            }   
        }
    }
        
    @Override
    public void afterDraw(TableElement element, Graphics2D g2, 
            Rectangle2D bounds) {
        if (!this.elementHinting) {
            return;
        }
        InteractiveElementType t = (InteractiveElementType) 
                element.getProperty(TableElement.CLASS);
        if (t == null) {
            return;
        }
        switch (t) {
            case TITLE:
            case SUBTITLE:
            case LEGEND_ITEM:
                g2.setRenderingHint(Chart3DHints.KEY_END_ELEMENT, Boolean.TRUE);
                break;
            default:
                throw new RuntimeException("Seeing type " + t);
        }   
    }

    private String generateLegendItemRef(TableElement element) {
        Object key = element.getProperty(Chart3D.SERIES_KEY);
        return "{\"type\": \"legendItem\", \"seriesKey\": \"" + key.toString() 
                + "\"}";
    }
}
