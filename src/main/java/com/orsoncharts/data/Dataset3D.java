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

package com.orsoncharts.data;

import java.util.EventListener;

/**
 * The base interface for datasets in Orson Charts.  All datasets must support
 * the change event notification mechanism.  The idea is that when a dataset
 * is changed, an event is passed to the plot.  The plot can react to this
 * event, then pass on the change event to the chart.  The chart in turn will
 * pass on the event and this can result in the chart being repainted (for
 * example, if the chart is displayed in a
 * {@link com.orsoncharts.Chart3DPanel}).
 */
public interface Dataset3D {

    /**
     * Registers a change listener to receive notification of changes to the
     * dataset.
     * 
     * @param listener  the listener ({@code null} not permitted). 
     */
    void addChangeListener(Dataset3DChangeListener listener);  
  
    /**
     * De-registers a change listener so that it no longer receives notification
     * of changes to the dataset.
     * 
     * @param listener  the listener ({@code null} not permitted). 
     */
    void removeChangeListener(Dataset3DChangeListener listener);  

    /**
     * Returns {@code true} if the specified listener is registered with
     * the dataset, and {@code false} otherwise.  This method is used
     * for unit testing to confirm that listeners are removed correctly 
     * following dataset switches.
     * 
     * @param listener  the listener.
     * 
     * @return A boolean. 
     */
    boolean hasListener(EventListener listener);
}
