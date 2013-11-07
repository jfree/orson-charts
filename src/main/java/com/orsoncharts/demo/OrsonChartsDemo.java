/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.demo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
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
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import com.orsoncharts.ChartPanel3D;
import com.orsoncharts.graphics3d.swing.DisplayPanel3D;
import java.io.IOException;
import java.net.URL;
import javax.swing.JButton;

/**
 * A demo application for Orson Charts.  This aggregates all the individual
 * demos which can also be run independently.
 */
public class OrsonChartsDemo extends JFrame implements ActionListener, 
        TreeSelectionListener {
    
    /** Default size for the content panel in the demo applications. */
    public static final Dimension DEFAULT_CONTENT_SIZE 
            = new Dimension(760, 480);
    
    private JTextPane chartDescriptionPane;

    /**
     * Creates a new demo instance with the specified frame title.
     * 
     * @param title  the title.
     */
    public OrsonChartsDemo(String title) {
        super(title);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
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
    
    private JComponent createContent() {
        JTabbedPane tabs = new JTabbedPane();
        JSplitPane splitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitter.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        JTree tree = new JTree(createTreeModel());
        tree.addTreeSelectionListener(this);
        JScrollPane scroller = new JScrollPane(tree);
        scroller.setPreferredSize(new Dimension(300, 580));
        splitter.add(scroller);
        splitter.add(createChartPanel());
        tabs.add("Demos", splitter);
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
            ex.printStackTrace();
        }
        JScrollPane scroller = new JScrollPane(textPane);
        result.add(scroller);
        return result;
    }
    
    public JPanel chartContainer = new JPanel(new BorderLayout());
    
    /**
     * Creates the panel that contains the chart and the chart description.
     * @return 
     */
    private JComponent createChartPanel() {
        JSplitPane splitter = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
       
        Border b = BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(4, 4, 4, 4), 
                BorderFactory.createLineBorder(Color.DARK_GRAY));
        
        JPanel chartPanel = AreaChart3DDemo1.createDemoPanel();
        chartPanel.setBorder(b);
        chartContainer.add(chartPanel);
        splitter.add(chartContainer);
        JPanel lowerPanel = new JPanel(new BorderLayout());
        lowerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(4, 4, 4, 4), 
                BorderFactory.createLineBorder(Color.BLACK)));
        this.chartDescriptionPane = new JTextPane();
        this.chartDescriptionPane.setPreferredSize(new Dimension(400, 200));
        this.chartDescriptionPane.setEditable(false);
        this.chartDescriptionPane.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        this.chartDescriptionPane.setText("No chart description available.");
        lowerPanel.add(this.chartDescriptionPane);
        splitter.add(lowerPanel);
        return splitter;
    }
    
    /**
     * Creates a <code>TreeModel</code> with references to all the individual
     * demo applications.
     *
     * @return A TreeModel.
     */
    private TreeModel createTreeModel() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Orson Charts");
        root.add(createCategoryChartsNode(root));
        root.add(createPieChartsNode());
        root.add(createXYZChartsNode());
        return new DefaultTreeModel(root);
    }
    
    private MutableTreeNode createNode(String name, String file) {
        return new DefaultMutableTreeNode(new DemoDescription(name, file));
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
        MutableTreeNode defaultNode = createNode("com.orsoncharts.demo.AreaChart3DDemo1", "AreaChart3DDemo1.java");
        //this.defaultChartPath = new TreePath(new Object[] {r, root, defaultNode});
        n.add(defaultNode);
        n.add(createNode("com.orsoncharts.demo.BarChart3DDemo1", "BarChart3DDemo1.java"));
        n.add(createNode("com.orsoncharts.demo.BarChart3DDemo2", "BarChart3DDemo2.java"));
        n.add(createNode("com.orsoncharts.demo.LineChart3DDemo1", "LineChart3DDemo1.java"));
        n.add(createNode("com.orsoncharts.demo.StackedBarChart3DDemo1", "StackedBarChart3DDemo1.java"));
        n.add(createNode("com.orsoncharts.demo.StackedBarChart3DDemo2", "StackedBarChart3DDemo2.java"));
        return n;
    }
    
    /**
     * Creates a node for the pie chart demos.
     * 
     * @return A node for the pie chart demos. 
     */
    private MutableTreeNode createPieChartsNode() {
        DefaultMutableTreeNode n = new DefaultMutableTreeNode("Pie Charts");
        n.add(createNode("com.orsoncharts.demo.PieChart3DDemo1", 
                "PieChart3DDemo1.java"));
        n.add(createNode("com.orsoncharts.demo.PieChart3DDemo2", 
                "PieChart3DDemo2.java"));
        return n;        
    }
    private MutableTreeNode createXYZChartsNode() {
        DefaultMutableTreeNode n = new DefaultMutableTreeNode("XYZ Charts");
        n.add(createNode("com.orsoncharts.demo.ScatterPlot3DDemo1", 
                "ScatterPlot3DDemo1.java"));
        n.add(createNode("com.orsoncharts.demo.XYZBarChart3DDemo1", 
                "XYZBarChart3DDemo1.java"));
        return n;        
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
                SwingUtilities.invokeLater(new DisplayDemo(this, dd));
            }
            else {
                this.chartContainer.removeAll();
                this.chartContainer.add(createNoDemoSelectedPanel());
                this.chartContainer.validate();
                URL descriptionURL = OrsonChartsDemo.class.getResource("select.html");
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
        OrsonChartsDemo app = new OrsonChartsDemo("Orson Charts Demo 1.0");
        app.pack();
        app.setVisible(true);
    }
   
    static class DisplayDemo implements Runnable {

        private OrsonChartsDemo app;

        private DemoDescription demoDescription;

        /**
         * Creates a new runnable.
         *
         * @param app  the application.
         * @param d  the demo description.
         */
        public DisplayDemo(OrsonChartsDemo app, DemoDescription d) {
            this.app = app;
            this.demoDescription = d;
        }

        /**
         * Runs the task.
         */
        public void run() {
            try {
                Class c = Class.forName(this.demoDescription.getClassName());
                Method m = c.getDeclaredMethod("createDemoPanel",
                        (Class[]) null);
                JPanel panel = (JPanel) m.invoke(null, (Object[]) null);
                panel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(4,4, 4, 4), 
                        BorderFactory.createLineBorder(Color.BLACK)));
                this.app.chartContainer.removeAll();
                this.app.chartContainer.add(panel);
                this.app.chartContainer.validate();
                if (panel instanceof DisplayPanel3D) {
                    DisplayPanel3D displayPanel = (DisplayPanel3D) panel;
                    ChartPanel3D chartPanel = (ChartPanel3D) displayPanel.getContent();
                    chartPanel.zoomToFit();
                }
                String f = this.demoDescription.getFileName();
                String f2 = f.substring(0, f.indexOf('.')) + ".html";
                URL descriptionURL = OrsonChartsDemo.class.getResource(f2);
                if (descriptionURL != null) {
                    try {
                        this.app.chartDescriptionPane.setPage(descriptionURL);
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

}
