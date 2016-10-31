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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DPanel;
import com.orsoncharts.style.ChartStyle;
import com.orsoncharts.style.ChartStyles;

/**
 * A demo application for Orson Charts.  This aggregates all the individual
 * demos which can also be run independently.
 */
@SuppressWarnings("serial")
public class OrsonChartsDemo extends JFrame implements ActionListener {
    
    /** Default size for the content panel in the demo applications. */
    public static final Dimension DEFAULT_CONTENT_SIZE 
            = new Dimension(760, 480);
    
    private OrsonChartsDemoComponent demoComponent;

    /** 
     * Creates a new demo instance with the specified frame title.
     * 
     * @param title  the title.
     */
    public OrsonChartsDemo(String title) {
        super(title);
        addWindowListener(new ExitOnClose());
        setJMenuBar(createMenuBar());
        add(createContent());
    }
    
    /**
     * Creates the menu bar.
     * 
     * @return The menu bar. 
     */
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu("File"));
        menuBar.add(createStyleMenu("Style"));
        return menuBar;
    }
    
    /**
     * Creates the file menu.
     * 
     * @param title  the menu title.
     * 
     * @return The menu. 
     */
    private JMenu createFileMenu(String title) {
        JMenu fileMenu = new JMenu(title);
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setActionCommand("EXIT");
        exitItem.addActionListener(this);
        fileMenu.add(exitItem);
        return fileMenu;
    }
    
    private JMenu createStyleMenu(String title) {
        JMenu styleMenu = new JMenu(title);
        JMenuItem noStyleMenuItem = new JRadioButtonMenuItem("No Style (style as coded)");
        noStyleMenuItem.setActionCommand("NO_STYLE");
        noStyleMenuItem.addActionListener(this);
        
        JMenuItem orson1StyleMenuItem = new JRadioButtonMenuItem("Orson 1 Style");
        orson1StyleMenuItem.setActionCommand("ORSON1_STYLE");
        orson1StyleMenuItem.addActionListener(this);

        JMenuItem orson2StyleMenuItem = new JRadioButtonMenuItem("Orson 2 Style");
        orson2StyleMenuItem.setActionCommand("ORSON2_STYLE");
        orson2StyleMenuItem.addActionListener(this);

        JMenuItem iceCubeStyleMenuItem = new JRadioButtonMenuItem("Ice Cube Style");
        iceCubeStyleMenuItem.setActionCommand("ICE_CUBE_STYLE");
        iceCubeStyleMenuItem.addActionListener(this);
        
        JMenuItem pastelStyleMenuItem = new JRadioButtonMenuItem("Pastel");
        pastelStyleMenuItem.setActionCommand("PASTEL_STYLE");
        pastelStyleMenuItem.addActionListener(this);
        
        JMenuItem logicalFontStyleMenuItem = new JRadioButtonMenuItem("Logical Font Style");
        logicalFontStyleMenuItem.setActionCommand("LOGICAL_FONT_STYLE");
        logicalFontStyleMenuItem.addActionListener(this);

        styleMenu.add(noStyleMenuItem);
        styleMenu.add(orson1StyleMenuItem);
        styleMenu.add(orson2StyleMenuItem);
        styleMenu.add(iceCubeStyleMenuItem);
        styleMenu.add(pastelStyleMenuItem);
        styleMenu.add(logicalFontStyleMenuItem);

        // set radio button group and default selection
        ButtonGroup group = new ButtonGroup();
        group.add(noStyleMenuItem);
        group.add(orson1StyleMenuItem);
        group.add(orson2StyleMenuItem);
        group.add(iceCubeStyleMenuItem);
        group.add(pastelStyleMenuItem);
        group.add(logicalFontStyleMenuItem);
        noStyleMenuItem.setSelected(true);

        return styleMenu;
    }
    
    /**
     * Creates the main content of the demo application, a tabbed pane with 
     * one tab showing the demo charts and another showing general information
     * about Orson Charts.
     * 
     * @return The content component.
     */
    private JComponent createContent() {
        JTabbedPane tabs = new JTabbedPane();
        this.demoComponent = new OrsonChartsDemoComponent();
        tabs.add("Demos", this.demoComponent);
        tabs.add("About", createAboutPanel());
        return tabs;
    }
    
    /**
     * Creates a panel containing information about Orson Charts.
     * 
     * @return A panel containing information about Orson Charts. 
     */
    private JPanel createAboutPanel() {
        JPanel result = new JPanel(new BorderLayout());
        result.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setPreferredSize(new Dimension(800, 400));
        URL descriptionURL = OrsonChartsDemo.class.getResource(
                "/com/orsoncharts/demo/about.html");
        try {
            textPane.setPage(descriptionURL);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        JScrollPane scroller = new JScrollPane(textPane);
        result.add(scroller);
        return result;
    }
    
    private void applyStyleToChartsInPanels(List<Chart3DPanel> panels, 
            ChartStyle style) {
        for (Chart3DPanel panel : panels) {
            Chart3D chart = (Chart3D) panel.getDrawable();
            ChartStyle s = style.clone();
            chart.setStyle(s);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if ("EXIT".equals(e.getActionCommand())) {
            System.exit(0);
        }
        Component c = this.demoComponent.getChartContainer().getComponent(0);
        if (c instanceof DemoPanel) {
            DemoPanel dp = (DemoPanel) c;
            List<Chart3DPanel> chartPanels = dp.getChartPanels();
            if ("NO_STYLE".equals(e.getActionCommand())) {
                this.demoComponent.setChartStyle(null);
            }
            
            if ("ORSON1_STYLE".equals(e.getActionCommand())) {
                this.demoComponent.setChartStyle(ChartStyles.createOrson1Style());
                applyStyleToChartsInPanels(chartPanels, ChartStyles.createOrson1Style());
            }

            if ("ORSON2_STYLE".equals(e.getActionCommand())) {
                this.demoComponent.setChartStyle(ChartStyles.createOrson2Style());
                applyStyleToChartsInPanels(chartPanels, ChartStyles.createOrson2Style());
            }

            if ("ICE_CUBE_STYLE".equals(e.getActionCommand())) {
                this.demoComponent.setChartStyle(ChartStyles.createIceCubeStyle());
                applyStyleToChartsInPanels(chartPanels, ChartStyles.createIceCubeStyle());
            }

            if ("PASTEL_STYLE".equals(e.getActionCommand())) {
                this.demoComponent.setChartStyle(ChartStyles.createPastelStyle());
                applyStyleToChartsInPanels(chartPanels, ChartStyles.createPastelStyle());
            }

            if ("LOGICAL_FONT_STYLE".equals(e.getActionCommand())) {
                this.demoComponent.setChartStyle(ChartStyles.createLogicalFontStyle());
                applyStyleToChartsInPanels(chartPanels, ChartStyles.createLogicalFontStyle());
            }
        }

    }

    /**
     * Starting point for the demo application.
     * 
     * @param args  ignored.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        try {
                            UIManager.setLookAndFeel(info.getClassName());
                        } catch (Exception ex) {
                            try {
                                UIManager.setLookAndFeel(
                                        UIManager.getSystemLookAndFeelClassName());
                            }
                            catch (Exception e2) {
                                // do nothing
                            }
                        }
                    }
                }
                OrsonChartsDemo app = new OrsonChartsDemo(
                        "Orson Charts Demo 1.6");
                app.pack();
                app.setVisible(true);
            }
            
        });
    }
   
}
