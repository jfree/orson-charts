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

import java.awt.Color;
import java.awt.Rectangle;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.TitleAnchor;
import com.orsoncharts.data.DefaultKeyedValues;
import com.orsoncharts.data.PieDataset3D;
import com.orsoncharts.data.StandardPieDataset3D;
import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.data.category.StandardCategoryDataset3D;
import com.orsoncharts.legend.LegendAnchor;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.util.Orientation;
import org.jfree.graphics2d.svg.SVGGraphics2D;

/**
 * A demo showing the creation of an HTML page containing two charts in
 * SVG format, with tooltips and mouse-click interactivity.  There are 
 * support files that must be included with the output file, see the
 * 'svg' directory in the distribution.
 */
public class SVGDemo1 {

    /**
     * Creates a sample dataset (hard-coded for the purpose of keeping the
     * demo self-contained - in practice you would normally read your data
     * from a file, database or other source).
     * 
     * @return A sample dataset.
     */
    static PieDataset3D createDataset() {
        StandardPieDataset3D dataset = new StandardPieDataset3D();
        dataset.add("Milk Products", 11625);
        dataset.add("Meat", 5114);
        dataset.add("Wood/Logs", 3060);
        dataset.add("Crude Oil", 2023);
        dataset.add("Machinery", 1865);
        dataset.add("Fruit", 1587);
        dataset.add("Fish", 1367);
        dataset.add("Wine", 1177);
        dataset.add("Other", 18870);
        return dataset; 
    }

    static Chart3D createPieChart(String id) {
        Chart3D chart = Chart3DFactory.createPieChart(
                "New Zealand Exports 2012", 
                "http://www.stats.govt.nz/browse_for_stats/snapshots-of-nz/nz-in-profile-2013.aspx", 
                createDataset());
        chart.setID(id);
        chart.setTitleAnchor(TitleAnchor.TOP_LEFT);
        chart.setLegendPosition(LegendAnchor.BOTTOM_CENTER,
                Orientation.HORIZONTAL);
        return chart;
    }
    
    static String generateSVGForChart(Chart3D chart, int width, int height) {
        SVGGraphics2D g2 = new SVGGraphics2D(width, height);
        chart.setElementHinting(true);
        chart.draw(g2, new Rectangle(width, height));
        return g2.getSVGElement("PieChart1");
    }
    
    /**
     * Creates a sample dataset (hard-coded for the purpose of keeping the
     * demo self-contained - in practice you would normally read your data
     * from a file, database or other source).
     * 
     * @return A sample dataset.
     */
    private static CategoryDataset3D createBarChartDataset() {    
        StandardCategoryDataset3D dataset = new StandardCategoryDataset3D();

        DefaultKeyedValues<Double> s1 = new DefaultKeyedValues<Double>();
        s1.put("Q2/11", 8.181);
        s1.put("Q3/11", 8.792);
        s1.put("Q4/11", 9.039);
        s1.put("Q1/12", 10.916);
        s1.put("Q2/12", 8.181);
        s1.put("Q3/12", 9.094);
        s1.put("Q4/12", 8.958);
        s1.put("Q1/13", 10.947);
        s1.put("Q2/13", 8.372);
        s1.put("Q3/13", 9.275);
        dataset.addSeriesAsRow("Oracle", s1);

        DefaultKeyedValues<Double> s2 = new DefaultKeyedValues<Double>();
        s2.put("Q2/11", 9.03);
        s2.put("Q3/11", 9.72);
        s2.put("Q4/11", 10.58);
        s2.put("Q1/12", 10.65);
        s2.put("Q2/12", 12.214);
        s2.put("Q3/12", 14.101);
        s2.put("Q4/12", 14.419);
        s2.put("Q1/13", 13.969);
        s2.put("Q2/13", 14.105);
        s2.put("Q3/13", 14.893);
        s2.put("Q4/13", 16.858);
        dataset.addSeriesAsRow("Google", s2);
        
        DefaultKeyedValues<Double> s3 = new DefaultKeyedValues<Double>();
        s3.put("Q2/11", 17.37);
        s3.put("Q3/11", 17.37);
        s3.put("Q4/11", 20.89);
        s3.put("Q1/12", 17.41);
        s3.put("Q2/12", 18.06);
        s3.put("Q3/12", 16.008);
        s3.put("Q4/12", 21.456);
        s3.put("Q1/13", 20.489);
        s3.put("Q2/13", 19.896);
        s3.put("Q3/13", 18.529);
        s3.put("Q4/13", 24.519);
        dataset.addSeriesAsRow("Microsoft", s3);
        
        DefaultKeyedValues<Double> s4 = new DefaultKeyedValues<Double>();
        s4.put("Q2/11", 28.57);
        s4.put("Q3/11", 28.27);
        s4.put("Q4/11", 46.33);
        s4.put("Q1/12", 39.20);
        s4.put("Q2/12", 35.00);
        s4.put("Q3/12", 36.00);
        s4.put("Q4/12", 54.5);
        s4.put("Q1/13", 43.6);
        s4.put("Q2/13", 35.323);
        s4.put("Q3/13", 37.5);
        s4.put("Q4/13", 57.594);
        dataset.addSeriesAsRow("Apple", s4);
        
        return dataset;
    }

    static Chart3D createBarChart(String id) {
        CategoryDataset3D dataset = createBarChartDataset();
        Chart3D chart = Chart3DFactory.createBarChart("Quarterly Revenues", 
                "For some large IT companies", dataset, null, "Quarter", 
                "$billion Revenues");
        chart.setID(id);
        chart.setChartBoxColor(new Color(255, 255, 255, 127));
        chart.setLegendAnchor(LegendAnchor.BOTTOM_RIGHT);
        CategoryPlot3D plot = (CategoryPlot3D) chart.getPlot();
        plot.setGridlinePaintForValues(Color.BLACK);
        return chart;
    }
    
    public static void main(String[] args) throws Exception {
                BufferedWriter writer = null;
        try {
            FileOutputStream fos = new FileOutputStream("SVGDemo1.html");
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            writer = new BufferedWriter(osw);
            writer.write("<!DOCTYPE html>\n");
            writer.write("<html>\n");
            
            writer.write("<head>\n");
            writer.write("<title>SVG Demo 1</title>\n");
            writer.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n"); 
            writer.write("<script src=\"lib/opentip-native.js\"></script>");
            writer.write("<link href=\"css/opentip.css\" rel=\"stylesheet\" type=\"text/css\" />");
            writer.write("<script src=\"lib/OrsonCharts.js\"></script>");
            writer.write("</head>\n");
            
            writer.write("<body>\n");
            
            writer.write("<h1>SVG Chart Demo</h1>\n");
            writer.write("<p>Click on an item in the chart or just hover and look at the tooltip (the ");
            writer.write("reference is a string in JSON format that should contain enough information to ");
            writer.write("identify the chart element):</p>\n");
            
            writer.write("<script type=\"application/javascript\">\n");
            writer.write("    // wait until all the resources are loaded\n");
            writer.write("    window.addEventListener(\"load\", registerListeners, false);\n");

            writer.write("    function registerListeners() {\n");
            writer.write("      var chartSVGElement = document.getElementById(\"BarChart1\");\n");
            writer.write("      chartSVGElement.onmouseover = handleMouseOver;\n");
            writer.write("      chartSVGElement.onclick = handleClick;\n");

            writer.write("      // for the pie chart\n");
            writer.write("      var pieChartSVGElement = document.getElementById(\"PieChart1\");\n");
            writer.write("      pieChartSVGElement.onmouseover = handleMouseOver;\n");
            writer.write("      pieChartSVGElement.onclick = handleClick;\n");
            writer.write("    }\n");

            writer.write("    function handleClick(evt) {\n");
            writer.write("      var element = evt.target;\n");
            writer.write("      var ref = OrsonCharts.findChartRef(element);\n");
            writer.write("      var chartId = OrsonCharts.findChartId(element);\n");
            writer.write("      alert('You clicked on the item ' + ref + ' for the chart [' + chartId + ']');\n");
            writer.write("    }\n");

            writer.write("    function handleMouseOver(evt) {\n");
            writer.write("      var element = evt.target;\n");
            writer.write("      var id = OrsonCharts.findChartRef(element);\n");
            writer.write("      if (id != null && id != 'ORSON_CHART_TOP_LEVEL') {\n");
            writer.write("        var myOpentip = new Opentip(element, id);\n");
            writer.write("        myOpentip.prepareToShow();\n");
            writer.write("     }\n");
            writer.write("    }\n");
            writer.write("</script>\n");
            
            writer.write("<p>\n");
            Chart3D pieChart = createPieChart("PieChart1");
            writer.write(generateSVGForChart(pieChart, 600, 370) + "\n");
            writer.write("</p>\n");
            
            writer.write("<p>\n");
            Chart3D barChart = createBarChart("BarChart1");
            writer.write(generateSVGForChart(barChart, 600, 370) + "\n");
            writer.write("</p>\n");
            
            writer.write("</body>\n");
            writer.write("</html>\n");
            writer.flush();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(SVGDemo1.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
        } 

    }
}
