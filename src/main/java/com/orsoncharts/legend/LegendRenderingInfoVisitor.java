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

package com.orsoncharts.legend;

import com.orsoncharts.Chart3D;
import com.orsoncharts.graphics3d.RenderedElement;
import com.orsoncharts.graphics3d.RenderingInfo;
import com.orsoncharts.interaction.InteractiveElementType;
import com.orsoncharts.table.TableElement;
import com.orsoncharts.table.TableElementVisitor;

/**
 * A visitor that extracts rendering info from a {@link TableElement} that
 * represents the chart's legend.
 * 
 * @since 1.3
 */
public class LegendRenderingInfoVisitor implements TableElementVisitor {

    /** The rendering info object to be updated. */
    private RenderingInfo info;
    
    /**
     * Creates a new visitor that will populate the supplied 
     * <code>info</code> instance.
     * 
     * @param info  the rendering info to be populated (<code>null</code> not 
     *     permitted). 
     */
    public LegendRenderingInfoVisitor(RenderingInfo info) {
        this.info = info;    
    }
    
    /**
     * A callback method that will be called for each element in the chart's
     * legend.
     * 
     * @param element  the element (<code>null</code> not permitted). 
     */
    @Override
    public void visit(TableElement element) {
        String elementClass = (String) element.getProperty(TableElement.CLASS);
        if ("LegendItem".equals(elementClass)) {
            RenderedElement legendItemRE = new RenderedElement(
                    InteractiveElementType.LEGEND_ITEM);
            legendItemRE.setProperty(Chart3D.SERIES_KEY, 
                    element.getProperty(Chart3D.SERIES_KEY));
            legendItemRE.setProperty(RenderedElement.BOUNDS_2D, 
                    element.getProperty(TableElement.BOUNDS_2D));
            info.addElement(legendItemRE);
        }
    }
    
}
