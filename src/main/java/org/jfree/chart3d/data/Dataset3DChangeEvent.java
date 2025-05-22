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
 * license is available to sponsors (higher tiers only) of the JFree projects.
 * For details, please see visit:
 *
 * https://github.com/sponsors/jfree
 * 
 */

package org.jfree.chart3d.data;

import java.util.EventObject;

/**
 * A dataset change event.  Any object that implements the 
 * {@link Dataset3DChangeListener} interface can register with a dataset to
 * receive notification of change events.  By default, the plot classes in
 * Orson Charts register with their dataset to monitor dataset changes.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. 
 */
@SuppressWarnings("serial")
public class Dataset3DChangeEvent extends EventObject {

    private final Object dataset;
  
    /**
     * Creates a new dataset change event.  The source can be the same as the
     * dataset, but this is not required.
     * 
     * @param source  the source.
     * @param dataset  the dataset.
     */
    public Dataset3DChangeEvent(Object source, Object dataset) {
        super(source);
        this.dataset = dataset;
    }
  
    /**
     * Returns the dataset that this event is associated with.  This will
     * normally be an instance of {@link PieDataset3D}, 
     * {@link org.jfree.chart3d.data.category.CategoryDataset3D} or 
     * {@link org.jfree.chart3d.data.xyz.XYZDataset}.
     * 
     * @return The dataset. 
     */
    public Object getDataset() {
        return this.dataset;
    }
}
