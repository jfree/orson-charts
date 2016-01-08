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

package com.orsoncharts.renderer.category;

import java.awt.Color;
import com.orsoncharts.Range;
import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.data.Values3D;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.World;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.renderer.Renderer3D;
import com.orsoncharts.renderer.Renderer3DChangeEvent;

/**
 * Defines the methods that all renderers must support to work with a 
 * {@link CategoryPlot3D}.
 */
public interface CategoryRenderer3D extends Renderer3D {
    
    /**
     * Returns the plot that this renderer is assigned to.
     * 
     * @return The plot (possibly {@code null}). 
     */
    CategoryPlot3D getPlot();
  
    /**
     * Sets the plot that the renderer is assigned to.  Although this method
     * is part of the public API, client code should not need to call it.
     * 
     * @param plot  the plot ({@code null} permitted). 
     */
    void setPlot(CategoryPlot3D plot);
  
    /**
     * Returns the color source for the renderer, which is an object that
     * is responsible for providing the colors used by the renderer to draw
     * data (and legend) items.
     * 
     * @return The paint source (never {@code null}). 
     */
    CategoryColorSource getColorSource();
    
    /**
     * Sets the color source for the renderer and sends a 
     * {@link Renderer3DChangeEvent} to all registered listeners.
     * 
     * @param source  the color source ({@code null} not permitted).
     */
    void setColorSource(CategoryColorSource source);
    
    /**
     * Sets the colors for the renderer.
     * 
     * @param colors  the colors. 
     * 
     * @since 1.2
     */
    void setColors(Color... colors);
    
    /**
     * Returns the range that should be used on the value axis to display all 
     * the specified data using this renderer.  Normally this will return the
     * minimum and maximum values in the dataset, but some renderers require 
     * a larger range (for example, the stacked bar renderer).
     * 
     * @param data  the data values ({@code null} not permitted).
     * 
     * @return The data range. 
     */
    Range findValueRange(Values3D<? extends Number> data);
    
    /**
     * Constructs and places one item from the specified dataset into the given 
     * world.  This method will be called by the {@link CategoryPlot3D} class
     * while iterating over the items in the dataset.
     * 
     * @param dataset  the dataset ({@code null} not permitted).
     * @param series  the series index.
     * @param row  the row index.
     * @param column  the column index.
     * @param world  the world ({@code null} not permitted).
     * @param dimensions  the plot dimensions ({@code null} not permitted).
     * @param xOffset  the x-offset.
     * @param yOffset  the y-offset.
     * @param zOffset  the z-offset.
     */
    void composeItem(CategoryDataset3D dataset, int series, int row, int column,
            World world, Dimension3D dimensions, 
            double xOffset, double yOffset, double zOffset);
 
}
