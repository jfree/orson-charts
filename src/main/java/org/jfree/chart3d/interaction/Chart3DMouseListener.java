/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-present, by David Gilbert.  All rights reserved.
 * 
 * https://github.com/jfree/orson-charts
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

package org.jfree.chart3d.interaction;

import java.util.EventListener;
import org.jfree.chart3d.Chart3DPanel;

/**
 * The interface that must be implemented by classes that wish to receive
 * {@link Chart3DMouseEvent} notifications from a {@link Chart3DPanel}.
 *
 * @see Chart3DPanel#addChartMouseListener(org.jfree.chart3d.interaction.Chart3DMouseListener) 
 * 
 * @since 1.3
 */
public interface Chart3DMouseListener extends EventListener {

    /**
     * Callback method for receiving notification of a mouse click on a chart.
     *
     * @param event  information about the event.
     */
    void chartMouseClicked(Chart3DMouseEvent event);

    /**
     * Callback method for receiving notification of a mouse movement on a
     * chart.
     *
     * @param event  information about the event.
     */
    void chartMouseMoved(Chart3DMouseEvent event);

}