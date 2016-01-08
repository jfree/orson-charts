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

import java.awt.Color;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DPanel;
import com.orsoncharts.demo.DemoDescription;

public class DemoDisplayer implements Runnable {

    private OrsonChartsDemoComponent demoComp;

    private DemoDescription demoDescription;

    /**
     * Creates a new runnable.
     *
     * @param demoComp  the demo component.
     * @param d  the demo description.
     */
    public DemoDisplayer(OrsonChartsDemoComponent demoComp, DemoDescription d) {
        this.demoComp = demoComp;
        this.demoDescription = d;
    }

    /**
     * Runs the task.
     */
    @Override
    public void run() {
        try {
            Class<?> c = Class.forName(this.demoDescription.getClassName());
            Method m = c.getDeclaredMethod("createDemoPanel", (Class[]) null);
            JPanel panel = (JPanel) m.invoke(null, (Object[]) null);
            panel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(4,4, 4, 4), 
                    BorderFactory.createLineBorder(Color.BLACK)));
            this.demoComp.getChartContainer().removeAll();
            this.demoComp.getChartContainer().add(panel);
            this.demoComp.getChartContainer().validate();
            if (panel instanceof DemoPanel) {
                DemoPanel demoPanel = (DemoPanel) panel;
                for (Chart3DPanel cp3d : demoPanel.getChartPanels()) {
                    if (demoComp.getChartStyle() != null) {
                        Chart3D chart = (Chart3D) cp3d.getDrawable();
                        chart.setStyle(demoComp.getChartStyle());
                    }
                    cp3d.zoomToFit(); 
                }
            }
            String f = this.demoDescription.getDescriptionFileName();
            URL descriptionURL = OrsonChartsDemo.class.getResource(f);
            if (descriptionURL != null) {
                try {
                    this.demoComp.getChartDescriptionPane().setPage(descriptionURL);
                }
                catch (IOException e) {
                    System.err.println("Attempted to read a bad URL: "
                        + descriptionURL);
                }
            }
        }
        catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        catch (NoSuchMethodException e2) {
            e2.printStackTrace();
        }
        catch (InvocationTargetException e3) {
            e3.printStackTrace();
        }
        catch (IllegalAccessException e4) {
            e4.printStackTrace();
        }

    }

}
