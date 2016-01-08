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
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import com.orsoncharts.demo.DemoDescription;
import com.orsoncharts.style.ChartStyle;

/**
 * A component that fits inside a tab and contains a JTree to select individual
 * demos, a chart panel to display the chart, and a chart description panel
 * to display the description.  This component is intended to be self-contained
 * so it can be instantiated and embedded wherever (such as in the JFreeChart
 * demo application).
 */
@SuppressWarnings("serial")
public class OrsonChartsDemoComponent extends JPanel 
        implements TreeSelectionListener {
    
    private static final String PREFIX = "com.orsoncharts.demo.swing.";
    
    private static final String ABOUT_PREFIX = "/com/orsoncharts/demo/";
    
    /** 
     * The path to the default demo chart.  This gets used to ensure the
     * JTree is open at the initial demo selection.
     */
    private TreePath defaultChartPath;

    /** 
     * The panel that contains the current chart - sits in the upper section of 
     * the right-hand split pane. 
     */
    private JPanel chartContainer;

    /**
     * The panel that contains the chart description - sits in the lower 
     * section of the right-hand split pane.
     */
    private JTextPane chartDescriptionPane;
    
    /** 
     * The chart style (if non-{@code null} this will be applied to 
     * new charts). 
     */
    private ChartStyle style;
    
    /**
     * Creates a new instance.
     */
    public OrsonChartsDemoComponent() {
        super(new BorderLayout());
        this.style = null;
        add(createContent());
    }
    
    public ChartStyle getChartStyle() {
        if (this.style == null) {
            return null;
        }
        return this.style.clone();
    }
    
    public void setChartStyle(ChartStyle style) {
        this.style = style;    
    }
    
    /**
     * Returns a reference to the panel that contains the chart viewer.
     * 
     * @return A reference to the panel that contains the chart viewer. 
     */
    public JPanel getChartContainer() {
        return this.chartContainer;
    }
    
    /**
     * Returns a reference to the chart description pane.
     * 
     * @return A reference to the chart description pane.
     */
    public JTextPane getChartDescriptionPane() {
        return this.chartDescriptionPane;
    }
    
    private JComponent createContent() {
        JSplitPane content = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        content.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        
        // tree on the left
        JTree tree = new JTree(createTreeModel());
        tree.addTreeSelectionListener(this);
        tree.setSelectionPath(this.defaultChartPath);
        JScrollPane scroller = new JScrollPane(tree);
        scroller.setPreferredSize(new Dimension(300, 580));
        content.add(scroller);
        
        // chart display on the right
        content.add(createChartComponent());
        return content;
    }

    /**
     * Creates a {@code TreeModel} with references to all the individual
     * demo applications.
     *
     * @return A TreeModel.
     */
    private TreeModel createTreeModel() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(
                "Orson Charts");
        root.add(createCategoryChartsNode(root));
        root.add(createPieChartsNode());
        root.add(createXYZChartsNode());       
        root.add(createAxisRangeTestNode());
        return new DefaultTreeModel(root);
    }
    
    private MutableTreeNode createNode(String name, String file, 
            String description) {
        return new DefaultMutableTreeNode(new DemoDescription(name, file, 
                description));
    }
    
    /**
     * A node for the first group of charts.
     *
     * @param r  the root node.
     *
     * @return The node.
     */
    private MutableTreeNode createCategoryChartsNode(DefaultMutableTreeNode r) {
        DefaultMutableTreeNode n = new DefaultMutableTreeNode(
                "Category Charts");
        MutableTreeNode defaultNode = createNode(PREFIX + "AreaChart3DDemo1", 
                "AreaChart3DDemo1.java", ABOUT_PREFIX + "AreaChart3D1.html");
        this.defaultChartPath = new TreePath(new Object[] {r, n, defaultNode});
        n.add(defaultNode);
        n.add(createNode(PREFIX + "AreaChart3DDemo2", 
                "AreaChart3DDemo2.java", ABOUT_PREFIX + "AreaChart3D2.html"));
        n.add(createNode(PREFIX + "BarChart3DDemo1", 
                "BarChart3DDemo1.java", ABOUT_PREFIX + "BarChart3D1.html"));
        n.add(createNode(PREFIX + "BarChart3DDemo2", 
                "BarChart3DDemo2.java", ABOUT_PREFIX + "BarChart3D2.html"));
        n.add(createNode(PREFIX + "CategoryMarkerDemo1", 
                "CategoryMarkerDemo1.java", 
                ABOUT_PREFIX + "CategoryMarkerDemo1.html"));
        n.add(createNode(PREFIX + "LineChart3DDemo1", 
                "LineChart3DDemo1.java", ABOUT_PREFIX + "LineChart3D1.html"));
        n.add(createNode(PREFIX + "LineChart3DDemo2", 
                "LineChart3DDemo2.java", ABOUT_PREFIX + "LineChart3D2.html"));
        n.add(createNode(PREFIX + "StackedBarChart3DDemo1", 
                "StackedBarChart3DDemo1.java", 
                ABOUT_PREFIX + "StackedBarChart3D1.html"));
        n.add(createNode(PREFIX + "StackedBarChart3DDemo2", 
                "StackedBarChart3DDemo2.java", 
                ABOUT_PREFIX + "StackedBarChart3D2.html"));
        n.add(createNode(PREFIX + "StackedBarChart3DDemo3", 
                "StackedBarChart3DDemo3.java", 
                ABOUT_PREFIX + "StackedBarChart3D3.html"));
        return n;
    }
    
    /**
     * Creates a node for the pie chart demos.
     * 
     * @return A node for the pie chart demos. 
     */
    private MutableTreeNode createPieChartsNode() {
        DefaultMutableTreeNode n = new DefaultMutableTreeNode("Pie Charts");
        n.add(createNode(PREFIX + "PieChart3DDemo1", 
                "PieChart3DDemo1.java", ABOUT_PREFIX + "PieChart3D1.html"));
        n.add(createNode(PREFIX + "PieChart3DDemo2", 
                "PieChart3DDemo2.java", ABOUT_PREFIX + "PieChart3D2.html"));
        return n;        
    }
    
    private MutableTreeNode createXYZChartsNode() {
        DefaultMutableTreeNode n = new DefaultMutableTreeNode("XYZ Charts");
        n.add(createNode(PREFIX + "RangeMarkerDemo1", "RangeMarkerDemo1.java", 
                ABOUT_PREFIX + "RangeMarker1.html"));
        n.add(createNode(PREFIX + "ScatterPlot3DDemo1", 
                "ScatterPlot3DDemo1.java", 
                ABOUT_PREFIX + "ScatterPlot3D1.html"));
        n.add(createNode(PREFIX + "ScatterPlot3DDemo2",  
                "ScatterPlot3DDemo2.java", 
                ABOUT_PREFIX + "ScatterPlot3D2.html"));
        n.add(createNode(PREFIX + "ScatterPlot3DDemo3",  
                "ScatterPlot3DDemo3.java", 
                ABOUT_PREFIX + "ScatterPlot3D3.html"));
        n.add(createNode(PREFIX + "SurfaceRendererDemo1", 
                "SurfaceRendererDemo1.java", 
                ABOUT_PREFIX + "SurfaceRenderer1.html"));
        n.add(createNode(PREFIX + "SurfaceRendererDemo2", 
                "SurfaceRendererDemo2.java", 
                ABOUT_PREFIX + "SurfaceRenderer2.html"));
        n.add(createNode(PREFIX + "XYZBarChart3DDemo1", 
                "XYZBarChart3DDemo1.java", 
                ABOUT_PREFIX + "XYZBarChart3D1.html"));
        n.add(createNode(PREFIX + "XYZLineChart3DDemo1", 
                "XYZLineChart3DDemo1.java", 
                ABOUT_PREFIX + "XYZLineChart3D1.html"));
        return n;        
    }
    
    private MutableTreeNode createAxisRangeTestNode() {
        DefaultMutableTreeNode n = new DefaultMutableTreeNode(
                "Axis Range Tests");
        n.add(createNode(PREFIX + "AxisRangeDemo1", "AxisRangeDemo1.java", 
                ABOUT_PREFIX + "AxisRangeDemo1.html"));
        n.add(createNode(PREFIX + "AxisRangeDemo2", "AxisRangeDemo2.java", 
                ABOUT_PREFIX + "AxisRangeDemo2.html"));
        n.add(createNode(PREFIX + "AxisRangeDemo3", "AxisRangeDemo3.java", 
                ABOUT_PREFIX + "AxisRangeDemo3.html"));
        n.add(createNode(PREFIX + "AxisRangeDemo4", "AxisRangeDemo4.java", 
                ABOUT_PREFIX + "AxisRangeDemo4.html"));
        n.add(createNode(PREFIX + "AxisRangeDemo5", "AxisRangeDemo5.java", 
                ABOUT_PREFIX + "AxisRangeDemo5.html"));
        n.add(createNode(PREFIX + "AxisRangeDemo6", "AxisRangeDemo6.java", 
                ABOUT_PREFIX + "AxisRangeDemo6.html"));
        n.add(createNode(PREFIX + "AxisRangeDemo7", "AxisRangeDemo7.java", 
                ABOUT_PREFIX + "AxisRangeDemo7.html"));
        return n;                
    }
    
    /**
     * Creates the panel that contains the chart and the chart description,
     * divided by a splitter.
     * 
     * @return 
     */
    private JComponent createChartComponent() {
        JSplitPane splitter = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
       
        Border b = BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(4, 4, 4, 4), 
                BorderFactory.createLineBorder(Color.DARK_GRAY));
        
        this.chartContainer = new JPanel(new BorderLayout());
        DemoPanel chartPanel = AreaChart3DDemo1.createDemoPanel();
        chartPanel.setBorder(b);
        this.chartContainer.add(chartPanel);
        splitter.add(this.chartContainer);
        JPanel lowerPanel = new JPanel(new BorderLayout());
        lowerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(4, 4, 4, 4), 
                BorderFactory.createLineBorder(Color.BLACK)));
        this.chartDescriptionPane = new JTextPane();
        this.chartDescriptionPane.setPreferredSize(new Dimension(400, 200));
        this.chartDescriptionPane.setEditable(false);
        this.chartDescriptionPane.setBorder(
                BorderFactory.createEmptyBorder(2, 2, 2, 2));
        this.chartDescriptionPane.setText("No chart description available.");
        lowerPanel.add(this.chartDescriptionPane);
        splitter.add(lowerPanel);
        return splitter;
    }    

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        TreePath path = e.getPath();
        Object obj = path.getLastPathComponent();
        if (obj != null) {
            DefaultMutableTreeNode n = (DefaultMutableTreeNode) obj;
            Object userObj = n.getUserObject();
            if (userObj instanceof DemoDescription) {
                DemoDescription dd = (DemoDescription) userObj;
                SwingUtilities.invokeLater(new DemoDisplayer(this, dd));
            }
            else {
                this.chartContainer.removeAll();
                this.chartContainer.add(createNoDemoSelectedPanel());
                this.chartContainer.validate();
                URL descriptionURL = OrsonChartsDemo.class.getResource(
                        "select.html");
                if (descriptionURL != null) {
                    try {
                        this.chartDescriptionPane.setPage(descriptionURL);
                    }
                    catch (IOException ex) {
                        System.err.println("Attempted to read a bad URL: "
                            + descriptionURL);
                    }
                }
            }
        }
    }

    private JComponent createNoDemoSelectedPanel() {
        return new JButton("No demo selected.");
    }
    
}
