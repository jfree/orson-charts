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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.swing.BorderFactory;
import javax.swing.JButton;
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

/**
 * A demo application for Orson Charts.
 */
public class OrsonChartsDemo extends JFrame implements TreeSelectionListener {
    
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
    
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu("File"));
        return menuBar;
    }
    
    public JMenu createFileMenu(String title) {
        JMenu fileMenu = new JMenu(title);
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(exitItem);
        return fileMenu;
    }
    
    private JComponent createContent() {
        JTabbedPane tabs = new JTabbedPane();
        JSplitPane splitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitter.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        JTree tree = new JTree(createTreeModel());
        tree.addTreeSelectionListener(this);
        splitter.add(new JScrollPane(tree));
        splitter.add(createChartPanel());
        tabs.add("Demos", splitter);
        tabs.add("Other Info", new JButton("How to purchase."));
        return tabs;
    }
    
    public JPanel chartContainer = new JPanel(new BorderLayout());
    private JComponent createChartPanel() {
        JSplitPane splitter = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
       
        Border b = BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(4, 4, 4, 4), 
                BorderFactory.createLineBorder(Color.DARK_GRAY));
        
        JPanel chartPanel = AreaChart3DDemo1.createDemoPanel();
        chartPanel.setBorder(b);
        chartContainer.add(chartPanel);
        splitter.add(chartContainer);
        splitter.add(new JButton("Chart description goes here!"));
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
        return n;
    }
    
    private MutableTreeNode createPieChartsNode() {
        DefaultMutableTreeNode n = new DefaultMutableTreeNode("Pie Charts");
        n.add(createNode("com.orsoncharts.demo.PieChart3DDemo1", 
                "PieChart3DDemo1.java"));
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

    
//    private ListModel createDemoChartList() {
//        DefaultListModel dlm = new DefaultListModel();
//        dlm.addElement("AreaChart3DDemo1");
//        dlm.addElement("BarChart3DDemo1");
//        dlm.addElement("BarChart3DDemo2");
//        dlm.addElement("LineChart3DDemo1");
//        return dlm;
//    }
    
    public static void main(String[] args) {
        OrsonChartsDemo app = new OrsonChartsDemo("Orson Charts Demo 1.0");
        app.pack();
        app.setVisible(true);
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
//                this.chartContainer.removeAll();
//                this.chartContainer.add(createNoDemoSelectedPanel());
//                this.displayPanel.validate();
//                displayDescription("select.html");
            }
        }

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
                panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(4,4, 4, 4), BorderFactory.createLineBorder(Color.BLACK)));
                this.app.chartContainer.removeAll();
                this.app.chartContainer.add(panel);
                this.app.chartContainer.validate();
//                String className = c.getName();
//                String fileName = className;
//                int i = className.lastIndexOf('.');
//                if (i > 0) {
//                    fileName = className.substring(i + 1);
//                }
//                fileName = fileName + ".html";
//                this.app.displayDescription(fileName);

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
