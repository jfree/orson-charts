/**
 * <b>Orson Charts</b> is a chart library for the Java(tm) platform (module name
 * {@code org.jfree.chart3d}) that can generate a wide variety of 3D charts for 
 * use in client-side applications (JavaFX and Swing) and server-side 
 * applications (with export to SVG, PDF, PNG and JPEG formats). Key features 
 * include:
 * <ol>
 *   <li>multiple chart types: pie charts, bar charts (regular and
 *       stacked), line charts, area charts, scatter plots and surface charts;</li>
 *   <li>a built-in lightweight 3D rendering engine - no additional or complex 
 *       dependencies, resulting in easy deployment;</li>
 *   <li>mouse-enabled chart viewing components (for both JavaFX and Swing) 
 *       provide 360 degree rotation and zooming for precise end-user view 
 *       control;</li>
 *   <li>auto-adaptive axis labeling;</li>
 *   <li>easy export of charts to PDF and SVG for reporting;</li>
 *   <li>a clean and well-documented API, with a high degree of chart 
 *       configurability;</li>
 * </ol>
 * <table>
 * <tr>
 *   <td>
 *     <img src="../doc-files/BarChart3DDemo1.svg" alt="BarChart3DDemo1.svg" 
 *       width="500" height="359">
 *   </td>
 *   <td>
 *     <img src="../doc-files/ScatterPlot3DDemo2.svg" alt="ScatterPlot3DDemo2.svg"
 *       width="500" height="359">
 *   </td>
 * </tr>
 * <tr>
 *   <td>
 *     <img src="../doc-files/PieChart3DDemo1.svg" alt="PieChart3DDemo1.svg" 
 *       width="500" height="359">
 *   </td>
 *   <td>
 *     <img src="../doc-files/AreaChart3DDemo1.svg" alt="AreaChart3DDemo1.svg" 
 *       width="500" height="359">
 *   </td>
 * </tr>
 * <caption>Samples</caption>
 * </table>
 * <br><br>
 * Orson Charts includes only 3D charts, for 2D charts we recommend the excellent 
 * <a href="http://www.jfree.org/" target="JFreeChart">JFreeChart</a> library
 * (by the same author).
 * <br><br>
 * There is also a version of Orson Charts available for the Android platform.
 * <br><br>
 * <b>Getting Started</b>
 * <br>
 * To get started with Orson Charts, you simply need to add the 
 * <code>org.jfree.chart3d</code> module to your application and begin coding.  
 * Your first step is to set up some data that you want to display.  Orson Charts
 * reads data through a dataset interface - there are three key interfaces, one 
 * that is used for pie charts (<code>PieDataset3D</code>), one that is used for 
 * bar charts and other category based charts (<code>CategoryDataset3D</code>) 
 * and one for plots that use numerical data (<code>XYZDataset</code>).  There 
 * are standard implementations of these interfaces includes in the library, 
 * making it straightforward to create a new dataset. Here is an example for a 
 * pie chart:
 * <br><br>
 * <code>&nbsp;&nbsp;StandardPieDataset3D dataset = new StandardPieDataset3D();<br></code>
 * <code>&nbsp;&nbsp;dataset.add("Milk Products", 625);<br></code>
 * <code>&nbsp;&nbsp;dataset.add("Meat", 114);</code>
 * <br><br>
 * Once your dataset is ready, the next step is to create a chart object - here the 
 * <code>Chart3DFactory</code> class can help, as it has utility methods to create 
 * some standard chart types:
 * <br><br>
 * <code>&nbsp;&nbsp;Chart3D chart = Chart3DFactory.createPieChart("Title", "Subtitle", dataset);</code>
 * <br><br>
 * Finally, if you are developing a Swing application, you will want to place the
 * chart somewhere in your UI.  Here the <code>Chart3DPanel</code> class can
 * be used:
 * <br><br>
 * <code>&nbsp;&nbsp;Chart3DPanel chartPanel = new Chart3DPanel(chart);</code>
 * <br><br>
 * You can find complete examples in the <code>JFree Demos</code> project at GitHub.
 * You are encouraged to explore these example programs and review these Javadoc
 * pages to learn more about Orson Charts.
 * <br><br>
 * <b>More Information</b>
 * <br>
 * Please visit <a href="http://www.object-refinery.com/orsoncharts/index.html" target="_blank">
 * http://www.object-refinery.com/orsoncharts/index.html</a> for the latest 
 * information about Orson Charts.
 */
module org.jfree.chart3d {
    requires java.base;
    requires java.desktop;
    requires java.logging;
    exports com.orsoncharts;
    exports com.orsoncharts.axis;
    exports com.orsoncharts.data;
    exports com.orsoncharts.data.category; 
    exports com.orsoncharts.data.function;
    exports com.orsoncharts.data.xyz;
    exports com.orsoncharts.graphics2d;
    exports com.orsoncharts.graphics3d;
    exports com.orsoncharts.graphics3d.swing;
    exports com.orsoncharts.interaction;
    exports com.orsoncharts.label;
    exports com.orsoncharts.legend;
    exports com.orsoncharts.marker;
    exports com.orsoncharts.plot;
    exports com.orsoncharts.renderer;
    exports com.orsoncharts.renderer.category;
    exports com.orsoncharts.renderer.xyz;
    exports com.orsoncharts.style;
    exports com.orsoncharts.table;
    exports com.orsoncharts.util;
}
