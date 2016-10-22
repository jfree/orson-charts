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

package com.orsoncharts.interaction.fx;

import com.orsoncharts.fx.Chart3DViewer;
import com.orsoncharts.graphics3d.RenderedElement;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;

/**
 * A chart mouse event for the {@link Chart3DViewer} component.
 * 
 * @since 1.4
 */
public class FXChart3DMouseEvent extends Event {

    /** Mouse clicked event type. */
    public static final EventType<FXChart3DMouseEvent> MOUSE_CLICKED 
            = new EventType<FXChart3DMouseEvent>("FXChart3DMouseEvent.CLICKED");
    
    /** Mouse moved event type. */
    public static final EventType<FXChart3DMouseEvent> MOUSE_MOVED 
            = new EventType<FXChart3DMouseEvent>("FXChart3DMouseEvent.MOVED");

    /** The chart element under the mouse pointer. */
    private final RenderedElement element;
    
    /** The JavaFX mouse event that triggered this event. */
    private final MouseEvent trigger;
    
    /**
     * Creates a new event.
     * 
     * @param source  the event source.
     * @param eventType  the event type.
     * @param element  the chart element under the mouse pointer.
     * @param trigger  the mouse event that triggered this event.
     */
    public FXChart3DMouseEvent(Object source, 
            EventType<? extends Event> eventType,
            RenderedElement element, MouseEvent trigger) {
        super(eventType);
        this.element = element;
        this.trigger = trigger;
    }
    
    /**
     * Returns the chart element under the mouse pointer.
     * 
     * @return The chart element. 
     */
    public RenderedElement getElement() {
        return this.element;
    }
    
    /**
     * Returns the mouse event that triggered this event.
     * 
     * @return The mouse event. 
     */
    public MouseEvent getTrigger() {
        return this.trigger;
    }
}
