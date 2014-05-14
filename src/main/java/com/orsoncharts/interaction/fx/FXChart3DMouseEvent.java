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
    public static final EventType MOUSE_CLICKED 
            = new EventType("FXChart3DMouseEvent.CLICKED");
    
    /** Mouse moved event type. */
    public static final EventType MOUSE_MOVED 
            = new EventType("FXChart3DMouseEvent.MOVED");

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
