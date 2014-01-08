/* ===================
 * Orson Charts - Demo
 * ===================
 * 
 * Copyright (c) 2013, 2014, Object Refinery Limited.
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

package com.orsoncharts.demo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * A demo application for Orson Charts.  This aggregates all the individual
 * demos which can also be run independently.
 */
public class OrsonChartsDemo extends JFrame implements ActionListener {
    
    /** Default size for the content panel in the demo applications. */
    public static final Dimension DEFAULT_CONTENT_SIZE 
            = new Dimension(760, 480);
    
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
    
    /**
     * Creates the main content of the demo application, a tabbed pane with 
     * one tab showing the demo charts and another showing general information
     * about Orson Charts.
     * 
     * @return The content component.
     */
    private JComponent createContent() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Demos", new OrsonChartsDemoComponent());
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
        URL descriptionURL = OrsonChartsDemo.class.getResource("about.html");
        try {
            textPane.setPage(descriptionURL);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        JScrollPane scroller = new JScrollPane(textPane);
        result.add(scroller);
        return result;
    }
    
       
        
    @Override
    public void actionPerformed(ActionEvent e) {
        if ("EXIT".equals(e.getActionCommand())) {
            System.exit(0);
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
                        "Orson Charts Demo 1.2");
                app.pack();
                app.setVisible(true);
            }
            
        });
    }
   
}
