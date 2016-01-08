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

import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;
import javax.swing.event.EventListenerList;

import com.orsoncharts.ChartElementVisitor;
import com.orsoncharts.label.ItemLabelPositioning;
import com.orsoncharts.util.ArgChecks;

/**
 * A base class for 3D renderers.
 */
public abstract class AbstractRenderer3D implements Renderer3D, Serializable {
    
    /** The font used to draw item labels. */
    private Font itemLabelFont;
    
    /** The foreground color for item labels. */
    private Color itemLabelColor;
    
    /** The background color for item labels. */
    private Color itemLabelBackgroundColor;
    
    /** The item label positioning. */
    private ItemLabelPositioning itemLabelPositioning;

    /** Storage for registered change listeners. */
    private transient EventListenerList listenerList;

    /**
     * A flag that controls whether or not the renderer will notify listeners
     * of changes (defaults to {@code true}, but sometimes it is useful 
     * to disable this).
     */
    private boolean notify;

    /**
     * Default constructor.
     */
    protected AbstractRenderer3D() {
        this.itemLabelFont = new Font(Font.SERIF, Font.PLAIN, 12);
        this.itemLabelColor = Color.WHITE;
        this.itemLabelBackgroundColor = new Color(100, 100, 100, 100); //Renderer3D.TRANSPARENT_COLOR;
        this.itemLabelPositioning = ItemLabelPositioning.CENTRAL;
        this.listenerList = new EventListenerList();
        this.notify = true;
    }

    /**
     * Returns the font used to display item labels, if there are any.
     * The default value is {@code Font(Font.SERIF, Font.PLAIN, 8)}.
     * 
     * @return The font (never {@code null}).
     * 
     * @since 1.3
     */
    public Font getItemLabelFont() {
        return itemLabelFont;
    }

    /**
     * Sets the font used to display item labels and sends a change event
     * to all registered listeners.
     * 
     * @param itemLabelFont  the font ({@code null} not permitted).
     * 
     * @since 1.3
     */
    public void setItemLabelFont(Font itemLabelFont) {
        ArgChecks.nullNotPermitted(itemLabelFont, "itemLabelFont");
        this.itemLabelFont = itemLabelFont;
        fireChangeEvent(true);
    }

    /**
     * Returns the foreground color used to display item labels.  The default
     * value is {@code Color.BLACK}.
     * 
     * @return The foreground color (never {@code null}).
     * 
     * @since 1.3
     */
    public Color getItemLabelColor() {
        return itemLabelColor;
    }

    /**
     * Sets the foreground color used to display item labels and sends a 
     * change event to all registered listeners.
     * 
     * @param itemLabelColor  the new color ({@code null} not permitted).
     * 
     * @since 1.3
     */
    public void setItemLabelColor(Color itemLabelColor) {
        ArgChecks.nullNotPermitted(itemLabelColor, "itemLabelColor");
        this.itemLabelColor = itemLabelColor;
        fireChangeEvent(true);
    }

    /**
     * Returns the background color for item labels.
     * 
     * @return The background color (never {@code null}).
     * 
     * @since 1.3
     */
    public Color getItemLabelBackgroundColor() {
        return itemLabelBackgroundColor;
    }

    /**
     * Sets the background color and sends a change event to all registered
     * listeners.
     * 
     * @param color  the new color ({@code null} not permitted).
     * 
     * @since 1.3
     */
    public void setItemLabelBackgroundColor(Color color) {
        ArgChecks.nullNotPermitted(color, "color");
        this.itemLabelBackgroundColor = color ;
        fireChangeEvent(true);
    }

    /**
     * Returns the item label positioning.  The default value is 
     * {@link ItemLabelPositioning#CENTRAL}.
     * 
     * @return The item label positioning (never {@code null}).
     * 
     * @since 1.3
     */
    public ItemLabelPositioning getItemLabelPositioning() {
        return itemLabelPositioning;
    }

    /**
     * Sets the item label positioning and sends a change event to all 
     * registered listeners.
     * 
     * @param positioning  the new positioning ({@code null} not 
     *     permitted).
     * 
     * @since 1.3
     */
    public void setItemLabelPositioning(ItemLabelPositioning positioning) {
        ArgChecks.nullNotPermitted(positioning, "positioning");
        this.itemLabelPositioning = positioning;
        fireChangeEvent(true);
    }

    /**
     * Returns a flag that controls whether or not change events are sent to
     * registered listeners.
     *
     * @return A boolean.
     *
     * @see #setNotify(boolean)
     */
    public boolean isNotify() {
        return this.notify;
    }

    /**
     * Sets a flag that controls whether or not listeners receive
     * {@link Renderer3DChangeEvent} notifications.
     *
     * @param notify  a boolean.
     *
     * @see #isNotify()
     */
    public void setNotify(boolean notify) {
        this.notify = notify;
        // if the flag is being set to true, there may be queued up changes...
        if (notify) {
            fireChangeEvent(true);
        }
    }
    
    /**
     * Receives a {@link ChartElementVisitor}.  This is part of a general 
     * purpose mechanism for traversing the chart structure and performing 
     * operations on the elements in the structure.  You won't normally call
     * this method directly.
     * 
     * @param visitor  the visitor ({@code null} not permitted).
     * 
     * @since 1.2
     */
    @Override
    public void receive(ChartElementVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Registers an object for notification of changes to the renderer.
     *
     * @param listener  the object to be registered.
     *
     * @see #removeChangeListener(Renderer3DChangeListener)
     */
    @Override
    public void addChangeListener(Renderer3DChangeListener listener) {
        this.listenerList.add(Renderer3DChangeListener.class, listener);
    }

    /**
     * Unregisters an object for notification of changes to the renderer.
     *
     * @param listener  the object to be unregistered.
     *
     * @see #addChangeListener(Renderer3DChangeListener) 
     */
    @Override
    public void removeChangeListener(Renderer3DChangeListener listener) {
        this.listenerList.remove(Renderer3DChangeListener.class, listener);
    }

    /**
     * Notifies all registered listeners that the plot has been modified.
     *
     * @param event  information about the change event.
     */
    public void notifyListeners(Renderer3DChangeEvent event) {
        // if the 'notify' flag has been switched to false, we don't notify
        // the listeners
        if (!this.notify) {
            return;
        }
        Object[] listeners = this.listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == Renderer3DChangeListener.class) { 
                ((Renderer3DChangeListener) listeners[i + 1]).rendererChanged(
                        event);
            }
        }
    }

    /**
     * Sends a {@link Renderer3DChangeEvent} to all registered listeners.
     * 
     * @param requiresWorldUpdate  a flag indicating whether or not the change
     *     requires the 3D world to be updated.
     */
    protected void fireChangeEvent(boolean requiresWorldUpdate) {
        notifyListeners(new Renderer3DChangeEvent(this, requiresWorldUpdate));
    }

    /**
     * Tests this renderer for equality with an arbitrary object.  The 
     * change listeners are NOT considered in the test, but the 
     * {@code notify} flag is taken into account.
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
        if (!(obj instanceof AbstractRenderer3D)) {
            return false;
        }
        AbstractRenderer3D that = (AbstractRenderer3D) obj;
        if (this.notify != that.notify) {
            return false;
        }
        if (!this.itemLabelFont.equals(that.itemLabelFont)) {
            return false;
        }
        if (!this.itemLabelColor.equals(that.itemLabelColor)) {
            return false;
        }
        if (!this.itemLabelBackgroundColor.equals(
                that.itemLabelBackgroundColor)) {
            return false;
        }
        if (this.itemLabelPositioning != that.itemLabelPositioning) {
            return false;
        }
        return true;
    }
}
