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
 * hinting to the <code>Graphics2D</code> output (how this is handled by the
 * <code>Graphics2D</code> instance is implementation dependent).
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
     * @param info  the rendering info to be populated (<code>null</code> 
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
            Map m = new HashMap<String, String>();
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
        return "{\"type\": \"legendItem\"; \"seriesKey\": \"" + key.toString() 
                + "\"}";
    }
}
