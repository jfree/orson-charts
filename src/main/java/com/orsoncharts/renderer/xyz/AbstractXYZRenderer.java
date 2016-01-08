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

package com.orsoncharts.renderer.xyz;

import java.awt.Color;
import com.orsoncharts.Range;
import com.orsoncharts.data.DataUtils;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.World;
import com.orsoncharts.label.XYZItemLabelGenerator;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.renderer.AbstractRenderer3D;
import com.orsoncharts.renderer.ComposeType;
import com.orsoncharts.renderer.Renderer3DChangeEvent;
import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.util.ObjectUtils;

/**
 * An abstract base class that can be used to create new {@link XYZRenderer}
 * subclasses.
 */
public class AbstractXYZRenderer extends AbstractRenderer3D {

    private XYZPlot plot;
  
    private XYZColorSource colorSource;
  
    /** 
     * An object that generates item labels for the chart.  Can be null.
     */
    private XYZItemLabelGenerator itemLabelGenerator;

    /**
     * Creates a new default instance.
     */
    protected AbstractXYZRenderer() {
        this.colorSource = new StandardXYZColorSource();
        this.itemLabelGenerator = null;
    }
  
    /**
     * Returns the plot that the renderer is assigned to, if any.
     * 
     * @return The plot (possibly {@code null}). 
     */
    public XYZPlot getPlot() {
        return this.plot;
    }
  
    /**
     * Sets the plot that the renderer is assigned to.
     * 
     * @param plot  the plot ({@code null} permitted). 
     */
    public void setPlot(XYZPlot plot) {
        this.plot = plot;
    }

    /**
     * Returns the item label generator for the renderer.  The default value
     * is {@code null}.  Not all subclasses will use this generator 
     * (for example, the {@link SurfaceRenderer} does not display item labels).
     * 
     * @return The item label generator (possibly {@code null}).
     * 
     * @since 1.3
     */
    public XYZItemLabelGenerator getItemLabelGenerator() {
        return this.itemLabelGenerator;
    }

    /**
     * Sets the item label generator and sends a change event to all registered
     * listeners.  You can set this to {@code null} in which case no
     * item labels will be generated.
     * 
     * @param generator  the new generator ({@code null} permitted).
     * 
     * @since 1.3
     */
    public void setItemLabelGenerator(XYZItemLabelGenerator generator) {
        this.itemLabelGenerator = generator;
    }

    /**
     * Returns the type of composition performed by this renderer.  The default
     * is {@code PER_ITEM} which means the plot will ask the renderer
     * to compose one data item at a time into the 3D model.  Some renderers
     * will override this method to return {@code ALL}, which means the
     * renderer will compose all of the data items in one go (the plot calls
     * the {@link #composeAll(XYZPlot, World, Dimension3D, double, double, double)} 
     * method to trigger this).
     * 
     * @return The compose type (never {@code null}).
     * 
     * @since 1.1
     */
    public ComposeType getComposeType() {
        return ComposeType.PER_ITEM;
    }
    
    /**
     * Adds objects to the {@code world} to represent all the data items
     * that this renderer is responsible for.  This method is only called for
     * renderers that return {@link ComposeType#ALL} from the 
     * {@link #getComposeType()} method.
     * 
     * @param plot  the plot (not {@code null}).
     * @param world  the 3D model (not {@code null}).
     * @param dimensions  the dimensions of the plot (not {@code null}).
     * @param xOffset  the x-offset.
     * @param yOffset  the y-offset.
     * @param zOffset  the z-offset.
     */
    public void composeAll(XYZPlot plot, World world, Dimension3D dimensions, 
            double xOffset, double yOffset, double zOffset) {
        throw new UnsupportedOperationException();
    }
    
    /**
     * Returns the object that provides the color instances for items drawn
     * by the renderer.
     * 
     * @return The color source (never {@code null}). 
     */
    public XYZColorSource getColorSource() {
        return this.colorSource;
    }
    
    /**
     * Sets the color source and sends a {@link Renderer3DChangeEvent} to all
     * registered listeners.
     * 
     * @param colorSource  the color source ({@code null} not permitted). 
     */
    public void setColorSource(XYZColorSource colorSource) {
        ArgChecks.nullNotPermitted(colorSource, "colorSource");
        this.colorSource = colorSource;
        fireChangeEvent(true);
    }
  
    
    /**
     * Sets a new color source for the renderer using the specified colors and
     * sends a {@link Renderer3DChangeEvent} to all registered listeners. This 
     * is a convenience method that is equivalent to 
     * {@code setColorSource(new StandardXYZColorSource(colors))}.
     * 
     * @param colors  one or more colors ({@code null} not permitted).
     * 
     * @since 1.1
     */
    public void setColors(Color... colors) {
        setColorSource(new StandardXYZColorSource(colors));
    }
    
    /**
     * Returns the range that is required on the x-axis for this renderer
     * to display all the items in the specified dataset.
     * 
     * @param dataset  the dataset ({@code null} not permitted).
     * 
     * @return The x-range. 
     */
    public Range findXRange(XYZDataset dataset) {
        return DataUtils.findXRange(dataset);
    }
    
    /**
     * Returns the range that is required on the y-axis for this renderer
     * to display all the items in the specified dataset.
     * 
     * @param dataset  the dataset ({@code null} not permitted).
     * 
     * @return The y-range. 
     */
    public Range findYRange(XYZDataset dataset) {
        return DataUtils.findYRange(dataset);
    }
    
    /**
     * Returns the range that is required on the z-axis for this renderer
     * to display all the items in the specified dataset.
     * 
     * @param dataset  the dataset ({@code null} not permitted).
     * 
     * @return The z-range. 
     */
    public Range findZRange(XYZDataset dataset) {
        return DataUtils.findZRange(dataset);
    }
    
    /**
     * Tests this renderer for equality with an arbitrary object.
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
        if (!(obj instanceof AbstractXYZRenderer)) {
            return false;
        }
        AbstractXYZRenderer that = (AbstractXYZRenderer) obj;
        if (!this.colorSource.equals(that.colorSource)) {
            return false;
        }
        if (!ObjectUtils.equals(this.itemLabelGenerator, 
                that.itemLabelGenerator)) {
            return false;
        }
        return super.equals(obj);
    }

}
