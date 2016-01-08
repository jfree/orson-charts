/* ===================
 * Orson Charts - Demo
 * ===================
 * 
 * Copyright (c) 2013-2016, Object Refinery Limited.
 * All rights reserved.
 *
 * http://www.object-refinery.com/orsoncharts/index.html
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *   - Neither the name of the Object Refinery Limited nor the
 *     names of its contributors may be used to endorse or promote products
 *     derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL OBJECT REFINERY LIMITED BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * Note that the above terms apply to the demo source only, and not the 
 * Orson Charts library.
 * 
 */

package com.orsoncharts.demo.swing;

import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.orsoncharts.Chart3DPanel;

/**
 * The base class for panels created by demo applications.  Some demos will 
 * subclass to add extra controls in addition to the main {@link Chart3DPanel}.
 */
@SuppressWarnings("serial")
public class DemoPanel extends JPanel {
    
    /** 
     * A list of the chart panels on the demo panel (usually just one, but 
     * there can be multiple panels).
     */
    private List<Chart3DPanel> chartPanels;
    
    /**
     * Creates a new instance.
     * 
     * @param layout  the layout manager. 
     */
    public DemoPanel(LayoutManager layout) {
        super(layout);
        this.chartPanels = new ArrayList<Chart3DPanel>();
    }
    
    /**
     * Returns the chart panel for this demo panel.  In the case where there
     * are multiple chart panels, this method will return the first one.
     * 
     * @return The chart panel (possibly {@code null}). 
     */
    public Chart3DPanel getChartPanel() {
        if (this.chartPanels.isEmpty()) {
            return null;
        }
        return this.chartPanels.get(0);    
    }
    
    /**
     * Returns the {@link Chart3DPanel} from the demo panel.
     * 
     * @return The {@link Chart3DPanel}. 
     */
    public List<Chart3DPanel> getChartPanels() {
        return this.chartPanels;
    }
    
    /**
     * Sets the chart panel that is displayed within this demo panel (for the
     * case where there is only one chart panel).
     * 
     * @param panel  the panel.
     */
    public void setChartPanel(Chart3DPanel panel) {
        this.chartPanels.clear();
        this.chartPanels.add(panel);
    }
    
    /**
     * Adds the {@link Chart3DPanel} for this demo panel.  This can be
     * accessed by code that wants to do something to the chart.
     * 
     * @param panel  the panel. 
     */
    public void addChartPanel(Chart3DPanel panel) {
        this.chartPanels.add(panel);
    }
}
