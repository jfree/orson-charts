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

package com.orsoncharts.graphics3d;

import com.orsoncharts.util.ArgChecks;

/**
 * A face belonging to an {@link Object3D} that has a tag.
 * 
 * @since 1.3 
 */
public class TaggedFace extends Face {
    
    /** The tag. */
    private String tag;
    
    /**
     * Creates a new instance.
     * 
     * @param owner  the owner ({@code null} not permitted).
     * @param vertices  the vertices (must contain at least 3 vertices).
     * @param tag  the tag ({@code null} not permitted).
     */
    public TaggedFace(Object3D owner, int[] vertices, String tag) {
        super(owner, vertices);
        ArgChecks.nullNotPermitted(tag, "tag");
        this.tag = tag;
    }
    
    /**
     * Returns the tag that was specified in the constructor.
     * 
     * @return The tag (never {@code null}).
     */
    @Override
    public String getTag() {
        return this.tag;
    }
}
