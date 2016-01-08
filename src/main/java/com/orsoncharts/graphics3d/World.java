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

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import com.orsoncharts.util.ArgChecks;

/**
 * A world is a model containing a collection of objects in 3D space and a 
 * direction vector for the sunlight.  A viewing point ({@link ViewPoint3D}) is 
 * specified externally.  Objects in the world are assigned to a partition, 
 * providing the ability to group objects.
 */
public class World {

    /** 
     * The default partition key.  All objects in the world are added with
     * a partition key.  There always exists at least one partition (the 
     * default partition).
     * 
     * @since 1.2
     */
    public static final String DEFAULT_PARTITION_KEY = "default";
    
    /** The sunlight vector. */
    private double sunX;
    private double sunY;
    private double sunZ;

    /** 
     * Storage for the objects in the world.  A map is used to store
     * one or more lists of objects (the partitioning is useful so
     * that updates can be made to subsets of the world).
     */
    private Map<String, List<Object3D>> objects;
    
    /**
     * Creates a new empty world.
     */
    public World() {
        this.objects = new java.util.TreeMap<String, List<Object3D>>();
        this.objects.put(DEFAULT_PARTITION_KEY, new ArrayList<Object3D>());
        setSunSource(new Point3D(2, -1, 10));
    }

  
    /**
     * Returns the x-component of the sunlight vector.
     *
     * @return The x-component of the sunlight vector.
     */
    public double getSunX() {
        return this.sunX;
    }

    /**
     * Returns the y-component of the sunlight vector.
     *
     * @return The y-component of the sunlight vector.
     */
    public double getSunY() {
        return this.sunY;
    }

    /**
     * Returns the z-component of the sunlight vector.
     *
     * @return The z-component of the sunlight vector.
     */
    public double getSunZ() {
        return this.sunZ;
    }
    
    /**
     * Sets the light source point.
     * 
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     * @param z  the z-coordinate.
     * 
     * @since 1.2
     */
    public final void setSunSource(double x, double y, double z) {
        setSunSource(new Point3D(x, y, z));
    }
    
    /**
     * Sets the light source point.
     * 
     * @param p  the point ({@code null} not permitted).
     * 
     * @since 1.2
     */
    public final void setSunSource(Point3D p) {
        ArgChecks.nullNotPermitted(p, "p");
        Point3D normal = Utils3D.normalise(p);
        this.sunX = normal.getX();
        this.sunY = normal.getY();
        this.sunZ = normal.getZ();
    }
    
    /**
     * Adds an object to the world in the default partition.
     *
     * @param object  the object ({@code null} not permitted).
     */
    public void add(Object3D object) {
        // defer argument checking
        add(DEFAULT_PARTITION_KEY, object);
    }

    /**
     * Adds an object to a specific partition.
     * 
     * @param partition  the partition ({@code null} not permitted).
     * @param object  the object ({@code null} not permitted).
     * 
     * @since 1.2
     */
    public void add(String partition, Object3D object) {
        ArgChecks.nullNotPermitted(partition, "partition");
        ArgChecks.nullNotPermitted(object, "object");
        List<Object3D> list = this.objects.get(partition);
        if (list == null) {
            list = new ArrayList<Object3D>();
            this.objects.put(partition, list);
        }
        list.add(object);
    }
    
    /**
     * Adds a collection of objects to the world (in the default
     * partition).
     * 
     * @param objects  the objects ({@code null} not permitted). 
     */
    public void addAll(Collection<Object3D> objects) {
        ArgChecks.nullNotPermitted(objects, "objects");
        for (Object3D object : objects) {
            add(object);
        }
    }

    /**
     * Clears any objects belonging to the specified partition.
     * 
     * @param partitionKey  the partition key ({@code null} not permitted).
     * 
     * @since 1.2
     */
    public void clear(String partitionKey) {
        ArgChecks.nullNotPermitted(partitionKey, "partitionKey");
        this.objects.put(partitionKey, null);
    }
    
    /**
     * Returns the total number of vertices for all objects in this world.
     *
     * @return The total number of vertices.
     */
    public int getVertexCount() {
        int count = 0;
        for (Entry<String, List<Object3D>> entry : this.objects.entrySet()) {
            List<Object3D> objs = entry.getValue();    
            for (Object3D object: objs) {
                count += object.getVertexCount();
            }
        }
        return count;
    }

    /**
     * Returns an array containing the vertices for all objects in this
     * world, transformed to eye coordinates.
     *
     * @param vp  the view point ({@code null} not permitted).
     *
     * @return The eye coordinates.
     */
    public Point3D[] calculateEyeCoordinates(ViewPoint3D vp) {
        Point3D[] result = new Point3D[getVertexCount()];
        int index = 0;
        for (Entry<String, List<Object3D>> entry : this.objects.entrySet()) {
            List<Object3D> objs = entry.getValue();    
            for (Object3D object : objs) {
                Point3D[] vertices = object.calculateEyeCoordinates(vp);
                System.arraycopy(vertices, 0, result, index, vertices.length);
                index = index + vertices.length;
            }
        }
        return result;
    }

    /**
     * Calculates the projected points in 2D-space for all the vertices of the
     * objects in the world.
     * 
     * @param vp  the view point ({@code null} not permitted).
     * @param d  the distance.
     * 
     * @return The projected points.
     */
    public Point2D[] calculateProjectedPoints(ViewPoint3D vp, double d) {
        Point2D[] result = new Point2D[getVertexCount()];
        int index = 0;
        for (Entry<String, List<Object3D>> entry : this.objects.entrySet()) {
            List<Object3D> objs = entry.getValue();    
            for (Object3D object : objs) {
                Point2D[] pts = object.calculateProjectedPoints(vp, d);
                System.arraycopy(pts, 0, result, index, pts.length);
                index = index + pts.length;
            }
        }
        return result;
    }

    /**
     * Fetches the faces for all the objects in this world, updating the
     * offset to match the current position.
     *
     * @return A list of faces.
     */
    public List<Face> getFaces() {
        List<Face> result = new java.util.ArrayList<Face>();
        int offset = 0;
        for (Entry<String, List<Object3D>> entry : this.objects.entrySet()) {
            List<Object3D> objs = entry.getValue();    
            for (Object3D object : objs) {
                for (Face f : object.getFaces()) {
                    f.setOffset(offset);
                }
                offset += object.getVertexCount();
                result.addAll(object.getFaces());
            }
        }
        return result;
    }
    
    /**
     * Returns a newly created list containing all the objects in the world 
     * model.
     * 
     * @return The list of objects.
     * 
     * @since 1.2
     */
    public List<Object3D> getObjects() {
        List<Object3D> result = new ArrayList<Object3D>();
        for (Entry<String, List<Object3D>> entry : this.objects.entrySet()) {
            List<Object3D> objs = entry.getValue();    
            result.addAll(objs);
        }
        return result;
    }

}
