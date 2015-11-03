/* ===================
 * Orson Charts - Demo
 * ===================
 * 
 * Copyright (c) 2013-2015, Object Refinery Limited.
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

import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.Colors;
import com.orsoncharts.axis.NumberAxis3D;
import com.orsoncharts.axis.NumberTickSelector;
import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.data.category.StandardCategoryDataset3D;
import com.orsoncharts.data.DefaultKeyedValues;
import com.orsoncharts.data.KeyedValues;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.ViewPoint3D;
import com.orsoncharts.plot.CategoryPlot3D;

/**
 * 3D line chart configuration for demo applications.
 */
public class LineChart3D1 {
  
    /**
     * Creates a line chart with the supplied dataset.
     * 
     * @param dataset  the dataset.
     * 
     * @return A line chart.
     */
    public static Chart3D createChart(CategoryDataset3D dataset) {
        Chart3D chart = Chart3DFactory.createLineChart(
                "Desktop Web Browser Market Share", 
                "Source: http://gs.statcounter.com", dataset, null, null, 
                "Market Share (%)");
        CategoryPlot3D plot = (CategoryPlot3D) chart.getPlot();
        plot.setDimensions(new Dimension3D(18, 8, 4));
        plot.getRowAxis().setVisible(false);
        NumberAxis3D valueAxis = (NumberAxis3D) plot.getValueAxis();
        valueAxis.setTickSelector(new NumberTickSelector(true));
        plot.getRenderer().setColors(Colors.createFancyDarkColors());
        chart.setViewPoint(ViewPoint3D.createAboveViewPoint(30));
        return chart;    
    }
    
    /**
     * Creates a sample dataset (hard-coded for the purpose of keeping the
     * demo self-contained - in practice you would normally read your data
     * from a file, database or other source).
     * 
     * @return A sample dataset.
     */
    public static CategoryDataset3D createDataset() {
        StandardCategoryDataset3D dataset = new StandardCategoryDataset3D();
        dataset.addSeriesAsRow("Safari", createSafariData());
        dataset.addSeriesAsRow("Firefox", createFirefoxData());
        dataset.addSeriesAsRow("Internet Explorer", createInternetExplorerData());
        dataset.addSeriesAsRow("Chrome", createChromeData());
        return dataset;
    }

    private static KeyedValues<Double> createChromeData() {
        DefaultKeyedValues<Double> series = new DefaultKeyedValues<Double>();
        series.put("Nov-12", 0.3697);
        series.put("Dec-12", 0.3782);
        series.put("Jan-13", 0.3808);
        series.put("Feb-13", 0.3883);
        series.put("Mar-13", 0.3989);
        series.put("Apr-13", 0.4088);
        series.put("May-13", 0.4328);
        series.put("Jun-13", 0.4481);
        series.put("Jul-13", 0.454);
        series.put("Aug-13", 0.4503);
        series.put("Sep-13", 0.4284);
        series.put("Oct-13", 0.4245);
        series.put("Nov-13", 0.4409);
        series.put("Dec-13", 0.4663);
        series.put("Jan-14", 0.466);
        series.put("Feb-14", 0.4669);
        series.put("Mar-14", 0.4649);
        series.put("Apr-14", 0.4808);
        series.put("May-14", 0.4863);
        series.put("Jun-14", 0.4872);
        series.put("Jul-14", 0.4869);
        series.put("Aug-14", 0.4988);
        series.put("Sep-14", 0.4918);
        series.put("Oct-14", 0.5131);
        series.put("Nov-14", 0.5181);
        series.put("Dec-14", 0.4966);
        series.put("Jan-15", 0.5172);
        series.put("Feb-15", 0.5227);
        series.put("Mar-15", 0.5259);
        series.put("Apr-15", 0.5296);
        series.put("May-15", 0.5232);
        series.put("Jun-15", 0.5282);
        series.put("Jul-15", 0.5539);
        series.put("Aug-15", 0.5651);
        series.put("Sep-15", 0.5651);
        series.put("Oct-15", 0.5695);
        return series;
    }

    private static KeyedValues<Double> createFirefoxData() {
        DefaultKeyedValues<Double> series = new DefaultKeyedValues<Double>();
        series.put("Nov-12", 0.2324);
        series.put("Dec-12", 0.2284);
        series.put("Jan-13", 0.2247);
        series.put("Feb-13", 0.225);
        series.put("Mar-13", 0.2203);
        series.put("Apr-13", 0.2109);
        series.put("May-13", 0.208);
        series.put("Jun-13", 0.2117);
        series.put("Jul-13", 0.2131);
        series.put("Aug-13", 0.2042);
        series.put("Sep-13", 0.1942);
        series.put("Oct-13", 0.1916);
        series.put("Nov-13", 0.1927);
        series.put("Dec-13", 0.203);
        series.put("Jan-14", 0.2039);
        series.put("Feb-14", 0.2078);
        series.put("Mar-14", 0.2028);
        series.put("Apr-14", 0.2013);
        series.put("May-14", 0.2033);
        series.put("Jun-14", 0.1963);
        series.put("Jul-14", 0.1925);
        series.put("Aug-14", 0.1929);
        series.put("Sep-14", 0.1925);
        series.put("Oct-14", 0.1877);
        series.put("Nov-14", 0.1846);
        series.put("Dec-14", 0.1801);
        series.put("Jan-15", 0.187);
        series.put("Feb-15", 0.1822);
        series.put("Mar-15", 0.186);
        series.put("Apr-15", 0.1827);
        series.put("May-15", 0.1787);
        series.put("Jun-15", 0.1759);
        series.put("Jul-15", 0.1724);
        series.put("Aug-15", 0.1716);
        series.put("Sep-15", 0.1734);
        series.put("Oct-15", 0.1695);
        return series;
    }

    private static KeyedValues<Double> createInternetExplorerData() {
        DefaultKeyedValues<Double> series = new DefaultKeyedValues<Double>();
        series.put("Nov-12", 0.3246);
        series.put("Dec-12", 0.3214);
        series.put("Jan-13", 0.3225);
        series.put("Feb-13", 0.3147);
        series.put("Mar-13", 0.3095);
        series.put("Apr-13", 0.3127);
        series.put("May-13", 0.2921);
        series.put("Jun-13", 0.2693);
        series.put("Jul-13", 0.2605);
        series.put("Aug-13", 0.2713);
        series.put("Sep-13", 0.3025);
        series.put("Oct-13", 0.3067);
        series.put("Nov-13", 0.2903);
        series.put("Dec-13", 0.2491);
        series.put("Jan-14", 0.2465);
        series.put("Feb-14", 0.2439);
        series.put("Mar-14", 0.2444);
        series.put("Apr-14", 0.2319);
        series.put("May-14", 0.226);
        series.put("Jun-14", 0.2297);
        series.put("Jul-14", 0.2352);
        series.put("Aug-14", 0.2241);
        series.put("Sep-14", 0.2262);
        series.put("Oct-14", 0.2131);
        series.put("Nov-14", 0.2165);
        series.put("Dec-14", 0.2455);
        series.put("Jan-15", 0.2116);
        series.put("Feb-15", 0.2075);
        series.put("Mar-15", 0.1973);
        series.put("Apr-15", 0.1985);
        series.put("May-15", 0.199);
        series.put("Jun-15", 0.2015);
        series.put("Jul-15", 0.1886);
        series.put("Aug-15", 0.1751);
        series.put("Sep-15", 0.1711);
        series.put("Oct-15", 0.167);
        return series;
    }
//
    private static KeyedValues<Double> createSafariData() {
        DefaultKeyedValues<Double> series = new DefaultKeyedValues<Double>();
        series.put("Nov-12", 0.051);
        series.put("Dec-12", 0.0499);
        series.put("Jan-13", 0.0512);
        series.put("Feb-13", 0.051);
        series.put("Mar-13", 0.0493);
        series.put("Apr-13", 0.0467);
        series.put("May-13", 0.0457);
        series.put("Jun-13", 0.0475);
        series.put("Jul-13", 0.048);
        series.put("Aug-13", 0.0484);
        series.put("Sep-13", 0.0491);
        series.put("Oct-13", 0.0496);
        series.put("Nov-13", 0.0464);
        series.put("Dec-13", 0.048);
        series.put("Jan-14", 0.0509);
        series.put("Feb-14", 0.050);
        series.put("Mar-14", 0.0512);
        series.put("Apr-14", 0.0509);
        series.put("May-14", 0.0504);
        series.put("Jun-14", 0.0494);
        series.put("Jul-14", 0.0489);
        series.put("Aug-14", 0.049);
        series.put("Sep-14", 0.0515);
        series.put("Oct-14", 0.0513);
        series.put("Nov-14", 0.0495);
        series.put("Dec-14", 0.047);
        series.put("Jan-15", 0.0494);
        series.put("Feb-15", 0.0494);
        series.put("Mar-15", 0.0543);
        series.put("Apr-15", 0.0523);
        series.put("May-15", 0.0631);
        series.put("Jun-15", 0.0594);
        series.put("Jul-15", 0.047);
        series.put("Aug-15", 0.0417);
        series.put("Sep-15", 0.0427);
        series.put("Oct-15", 0.0449);
        return series;
    }
    
}