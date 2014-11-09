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
