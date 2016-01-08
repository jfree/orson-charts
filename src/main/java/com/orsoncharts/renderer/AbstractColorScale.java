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

package com.orsoncharts.renderer;

import java.io.Serializable;

import com.orsoncharts.Range;
import com.orsoncharts.util.ArgChecks;

/**
 * An abstract class from which a {@link ColorScale} implementation can be
 * derived.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. 
 * 
 * @since 1.1
 */
@SuppressWarnings("serial")
public abstract class AbstractColorScale implements Serializable {
    
    /** The data value range for the scale. */
    private Range range;
    
    /**
     * Creates a new color scale for the specified data value range.  
     * 
     * @param range  the data value range ({@code null} not permitted).
     */
    protected AbstractColorScale(Range range) {
        ArgChecks.nullNotPermitted(range, "range");
        this.range = range;
    }

    /**
     * Returns the range of data values over which the scale is defined.  This
     * is specified via the constructor and once set cannot be changed.
     * 
     * @return The range (never {@code null}). 
     */
    public Range getRange() {
        return this.range;
    }

    /**
     * Tests this color scale for equality with an arbitrary object.
     * 
     * @param obj  the object ({@code null} permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AbstractColorScale)) {
            return false;
        }
        AbstractColorScale that = (AbstractColorScale) obj;
        if (!this.range.equals(that.range)) {
            return false;
        }
        return true;
    }

}
