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
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DPanel;
import com.orsoncharts.axis.StandardCategoryAxis3D;
import com.orsoncharts.data.DefaultKeyedValues;
import com.orsoncharts.data.KeyedValues3DItemKeys;
import com.orsoncharts.data.KeyedValues3DItemKey;
import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.data.category.StandardCategoryDataset3D;
import com.orsoncharts.demo.CategoryMarker1;
import com.orsoncharts.demo.HighlightCategoryColorSource;
import com.orsoncharts.graphics3d.Object3D;
import com.orsoncharts.graphics3d.RenderedElement;
import com.orsoncharts.graphics3d.swing.DisplayPanel3D;
import com.orsoncharts.interaction.Chart3DMouseEvent;
import com.orsoncharts.interaction.Chart3DMouseListener;
import com.orsoncharts.interaction.InteractiveElementType;
import com.orsoncharts.interaction.KeyedValues3DItemSelection;
import com.orsoncharts.interaction.StandardKeyedValues3DItemSelection;
import com.orsoncharts.label.StandardCategoryItemLabelGenerator;
import com.orsoncharts.marker.CategoryMarker;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.renderer.category.BarRenderer3D;
import com.orsoncharts.style.ChartStyler;

/**
 * A demo showing category markers on a 3D bar chart plus many elements of
 * chart interactivity.
 */
@SuppressWarnings("serial")
public class CategoryMarkerDemo1 extends JFrame {
    
    static class CustomDemoPanel extends DemoPanel 
            implements ActionListener, Chart3DMouseListener {
        
        private String selectedRowKey;
        
        private String selectedColumnKey;
        
        private JCheckBox itemLabelCheckBox;
        
        public CustomDemoPanel(LayoutManager layout) {
            super(layout);
            JPanel controlPanel = new JPanel(new FlowLayout());
            this.itemLabelCheckBox = new JCheckBox("Show item labels?");
            itemLabelCheckBox.addActionListener(this);
            controlPanel.add(itemLabelCheckBox);
            this.selectedRowKey = "Apple";
            this.selectedColumnKey = "Q4/12";
            add(controlPanel, BorderLayout.SOUTH);
        }
        
        @SuppressWarnings("unchecked")
        private void updateColorSource(String selectedRow, 
                String selectedColumn) {
            HighlightCategoryColorSource colorSource = (HighlightCategoryColorSource) 
                    getRenderer().getColorSource();
            int rowIndex = getPlot().getDataset().getRowIndex(selectedRow);
            int columnIndex = getPlot().getDataset().getColumnIndex(
                    selectedColumn);
            colorSource.setHighlightRowIndex(rowIndex);
            colorSource.setHighlightColumnIndex(columnIndex);
        }
        
        @SuppressWarnings("unchecked")
        private void updateItemSelection(String selectedRow, 
                String selectedColumn) {
            StandardKeyedValues3DItemSelection itemSelection 
                    = (StandardKeyedValues3DItemSelection) getItemSelection();
            itemSelection.clear();
            if (this.itemLabelCheckBox.isSelected()) {
                itemSelection.addAll(KeyedValues3DItemKeys.itemKeysForColumn(
                        getPlot().getDataset(), selectedColumn));
                itemSelection.addAll(KeyedValues3DItemKeys.itemKeysForRow(
                        getPlot().getDataset(), selectedRow));
            }
        }
        
        private CategoryPlot3D getPlot() {
            Chart3D chart = getChartPanel().getChart();
            return (CategoryPlot3D) chart.getPlot();
        }
        
        private BarRenderer3D getRenderer() {
            return (BarRenderer3D) getPlot().getRenderer();        
        }
        
        private KeyedValues3DItemSelection getItemSelection() {
            StandardCategoryItemLabelGenerator generator 
                    = (StandardCategoryItemLabelGenerator) 
                    getRenderer().getItemLabelGenerator();
            return generator.getItemSelection();
        }

        private void handleSelectItem(Comparable rowKey, Comparable columnKey) {
            Chart3D chart = getChartPanel().getChart();
            chart.setNotify(false);
            CategoryPlot3D plot = getPlot();
            StandardCategoryAxis3D rowAxis 
                    = (StandardCategoryAxis3D) plot.getRowAxis();
            CategoryMarker rowMarker = rowAxis.getMarker("RM1");
            if (rowMarker == null) {
                rowMarker = new CategoryMarker("");
                rowMarker.receive(new ChartStyler(chart.getStyle()));
            }
            StandardCategoryAxis3D columnAxis 
                    = (StandardCategoryAxis3D) plot.getColumnAxis();
            CategoryMarker columnMarker = columnAxis.getMarker("CM1");
            if (columnMarker == null) {
                columnMarker = new CategoryMarker("");
                columnMarker.receive(new ChartStyler(chart.getStyle()));
            }
            this.selectedRowKey = rowKey.toString();
            this.selectedColumnKey = columnKey.toString();
            rowMarker.setCategory(this.selectedRowKey);
            columnMarker.setCategory(this.selectedColumnKey);
            updateColorSource(this.selectedRowKey, this.selectedColumnKey);
            updateItemSelection(this.selectedRowKey, this.selectedColumnKey);
            chart.setNotify(true);
        }
        
        private void handleSelectRow(Comparable rowKey) {
            handleSelectItem(rowKey, this.selectedColumnKey);
        }
        
        private void handleSelectColumn(Comparable columnKey) {
            handleSelectItem(this.selectedRowKey, columnKey);
        }
        
        @Override
        public void chartMouseClicked(Chart3DMouseEvent event) {
            RenderedElement element = event.getElement();
            if (element == null) {
                return;
            }
            // first handle clicks on data items
            KeyedValues3DItemKey key = (KeyedValues3DItemKey) 
                    element.getProperty(Object3D.ITEM_KEY);
            if (key != null) {
                handleSelectItem(key.getRowKey(), key.getColumnKey());
            } else {
                if (InteractiveElementType.CATEGORY_AXIS_TICK_LABEL.equals(
                        element.getType())) {
                    String label = (String) element.getProperty("label");
                    String axisStr = (String) element.getProperty("axis");
                    if (axisStr.equals("row")) {
                        handleSelectRow(label);
                    } else { // column axis
                        handleSelectColumn(label);
                    }
                } else if (InteractiveElementType.LEGEND_ITEM.equals(
                        element.getType())) {
                    Comparable<?> seriesKey = (Comparable<?>) 
                            element.getProperty(Chart3D.SERIES_KEY);
                    // the row keys are the same as the series keys in 
                    // this chart
                    handleSelectRow(seriesKey);
                } else {
                    //JOptionPane.showMessageDialog(this, 
                    //        Chart3D.renderedElementToString(element));
                }
            }
        }

        @Override
        public void chartMouseMoved(Chart3DMouseEvent event) {
            // we'll do nothing here
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // there is only the item label checkbox
            updateItemSelection(this.selectedRowKey, this.selectedColumnKey);
            getChartPanel().getChart().setNotify(true);
        }
    }

    /**
     * Creates a new test app.
     *
     * @param title  the frame title.
     */
    public CategoryMarkerDemo1(String title) {
        super(title);
        addWindowListener(new ExitOnClose());
        getContentPane().add(createDemoPanel());
    }

    /**
     * Returns a panel containing the content for the demo.  This method is
     * used across all the individual demo applications to allow aggregation 
     * into a single "umbrella" demo (OrsonChartsDemo).
     * 
     * @return A panel containing the content for the demo.
     */
    public static JPanel createDemoPanel() {
        CustomDemoPanel content = new CustomDemoPanel(new BorderLayout());
        content.setPreferredSize(OrsonChartsDemo.DEFAULT_CONTENT_SIZE);
        CategoryDataset3D dataset = CategoryMarker1.createDataset();
        Chart3D chart = CategoryMarker1.createChart(dataset);
        Chart3DPanel chartPanel = new Chart3DPanel(chart);
        chartPanel.setMargin(0.30);
        chartPanel.getViewPoint().panLeftRight(-0.30);
        chartPanel.getViewPoint().moveUpDown(-0.12);
        chartPanel.getViewPoint().roll(-Math.PI / 60);
        content.setChartPanel(chartPanel);
        chartPanel.zoomToFit(OrsonChartsDemo.DEFAULT_CONTENT_SIZE);
        chartPanel.addChartMouseListener(content);
        content.add(new DisplayPanel3D(chartPanel));
        return content;
    }

    /**
     * Starting point for the app.
     *
     * @param args  command line arguments (ignored).
     */
    public static void main(String[] args) {
        CategoryMarkerDemo1 app = new CategoryMarkerDemo1(
                "OrsonCharts: CategoryMarkerDemo1.java");
        app.pack();
        app.setVisible(true);
    }
}
