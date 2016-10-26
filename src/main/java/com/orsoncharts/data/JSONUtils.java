/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-2016, by Object Refinery Limited.  All rights reserved.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 * 
 * If you do not wish to be bound by the terms of the GPL, an alternative
 * commercial license can be purchased.  For details, please see visit the
 * Orson Charts home page:
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 */

package com.orsoncharts.data;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.orsoncharts.data.category.StandardCategoryDataset3D;
import com.orsoncharts.util.json.JSONValue;
import com.orsoncharts.util.json.parser.JSONParser;
import com.orsoncharts.util.json.parser.ParseException;
import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.util.json.parser.ContainerFactory;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.data.xyz.XYZSeries;
import com.orsoncharts.data.xyz.XYZSeriesCollection;

/**
 * Utility methods for interchange between datasets ({@link KeyedValues}, 
 * {@link KeyedValues3D} and {@link XYZDataset}) and JSON format strings.
 * 
 * @since 1.3
 */
public class JSONUtils {

    /**
     * Parses the supplied JSON string into a {@link KeyedValues} instance.
     * <br><br>
     * Implementation note:  this method returns an instance of 
     * {@link StandardPieDataset3D}).
     * 
     * @param json  the JSON string ({@code null} not permitted).
     * 
     * @return A {@link KeyedValues} instance. 
     */
    public static KeyedValues<String, Number> readKeyedValues(String json) {
        ArgChecks.nullNotPermitted(json, "json");
        StringReader in = new StringReader(json);
        KeyedValues<String, Number> result;
        try {
            result = readKeyedValues(in);
        } catch (IOException ex) {
            // not for StringReader
            result = null;
        }
        return result;
    }
    
    /**
     * Parses characters from the supplied reader and returns the corresponding
     * {@link KeyedValues} instance.
     * <br><br>
     * Implementation note:  this method returns an instance of 
     * {@link StandardPieDataset3D}).
     * 
     * @param reader  the reader ({@code null} not permitted).
     * 
     * @return A {@code KeyedValues} instance.
     * 
     * @throws IOException if there is an I/O problem.
     */
    public static KeyedValues<String, Number> readKeyedValues(
            Reader reader) throws IOException {
        ArgChecks.nullNotPermitted(reader, "reader");
        try {
            JSONParser parser = new JSONParser();
            // parse with custom containers (to preserve item order)
            List list = (List) parser.parse(reader, createContainerFactory());
            StandardPieDataset3D<String> result = new StandardPieDataset3D<String>();
            for (Object item : list) {
                List itemAsList = (List) item;
                result.add(itemAsList.get(0).toString(), (Number) itemAsList.get(1));
            }
            return result;        
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Returns a string containing the data in JSON format.  The format is
     * an array of arrays, where each sub-array represents one data value.
     * The sub-array should contain two items, first the item key as a string
     * and second the item value as a number.  For example:
     * {@code [["Key A", 1.0], ["Key B", 2.0]]}
     * <br><br>
     * Note that this method can be used with instances of {@link PieDataset3D}.
     * 
     * @param data  the data ({@code null} not permitted).
     * 
     * @return A string in JSON format. 
     */
    @SuppressWarnings("unchecked")
    public static String writeKeyedValues(KeyedValues data) {
        ArgChecks.nullNotPermitted(data, "data");
        StringWriter sw = new StringWriter();
        try {
            writeKeyedValues(data, sw);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return sw.toString();
    }

    /**
     * Writes the data in JSON format to the supplied writer.
     * <br><br>
     * Note that this method can be used with instances of {@link PieDataset3D}.
     * 
     * @param data  the data ({@code null} not permitted).
     * @param writer  the writer ({@code null} not permitted).
     * 
     * @throws IOException if there is an I/O problem.
     */
    @SuppressWarnings("unchecked")
    public static void writeKeyedValues(KeyedValues data, Writer writer) 
            throws IOException {
        ArgChecks.nullNotPermitted(data, "data");
        ArgChecks.nullNotPermitted(writer, "writer");
        writer.write("[");
        boolean first = true;
        for (Object key : data.getKeys()) {
            if (!first) {
                writer.write(", ");
            } else {
                first = false;
            }
            writer.write("[");
            writer.write(JSONValue.toJSONString(key.toString()));
            writer.write(", ");
            writer.write(JSONValue.toJSONString(data.getValue((Comparable) key)));
            writer.write("]");
        }
        writer.write("]");
    }
    
    /**
     * Reads a data table from a JSON format string.
     * 
     * @param json  the string ({@code null} not permitted).
     * 
     * @return A data table. 
     */
    @SuppressWarnings("unchecked")
    public static KeyedValues2D<String, String, Number> 
            readKeyedValues2D(String json) {
        ArgChecks.nullNotPermitted(json, "json");
        StringReader in = new StringReader(json);
        KeyedValues2D<String, String, Number> result;
        try { 
            result = readKeyedValues2D(in);
        } catch (IOException ex) {
            // not for StringReader
            result = null;
        }
        return result;
    }
 
    /**
     * Reads a data table from a JSON format string coming from the specified
     * reader.
     * 
     * @param reader  the reader ({@code null} not permitted).
     * 
     * @return A data table.
     * 
     * @throws java.io.IOException if there is an I/O problem.
     */
    @SuppressWarnings("unchecked")
    public static KeyedValues2D<String, String, Number> 
            readKeyedValues2D(Reader reader) throws IOException {
        
        JSONParser parser = new JSONParser();
        try {
            Map map = (Map) parser.parse(reader, createContainerFactory());
            DefaultKeyedValues2D<String, String, Number> result 
                    = new DefaultKeyedValues2D<String, String, Number>();
            if (map.isEmpty()) {
                return result;
            }
            
            // read the keys
            Object keysObj = map.get("columnKeys");
            List<String> keys = null;
            if (keysObj instanceof List) {
                keys = (List<String>) keysObj;
            } else {
                if (keysObj == null) {
                    throw new RuntimeException("No 'columnKeys' defined.");    
                } else {
                    throw new RuntimeException("Please check the 'columnKeys', " 
                            + "the format does not parse to a list.");
                }
            }
            
            Object dataObj = map.get("rows");
            if (dataObj instanceof List) {
                List<String> rowList = (List<String>) dataObj;
                // each entry in the map has the row key and an array of
                // values (the length should match the list of keys above
                for (Object rowObj : rowList) {
                    processRow(rowObj, keys, result);
                }
            } else { // the 'data' entry is not parsing to a list
                if (dataObj == null) {
                    throw new RuntimeException("No 'rows' section defined.");
                } else {
                    throw new RuntimeException("Please check the 'rows' "
                            + "entry, the format does not parse to a list of "
                            + "rows.");
                }
            }
            return result;
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Processes an entry for one row in a {@link KeyedValues2D}.
     * 
     * @param rowObj  the series object.
     * @param columnKeys  the required column keys.
     * @param dataset  the dataset.
     */
    @SuppressWarnings("unchecked")
    static void processRow(Object rowObj, List<String> columnKeys, 
            DefaultKeyedValues2D dataset) {
        
        if (!(rowObj instanceof List)) {
            throw new RuntimeException("Check the 'data' section it contains "
                    + "a row that does not parse to a list.");
        }
        
        // we expect the row data object to be an array containing the 
        // rowKey and rowValueArray entries, where rowValueArray
        // should have the same number of entries as the columnKeys
        List rowList = (List) rowObj;
        Object rowKey = rowList.get(0);
        Object rowDataObj = rowList.get(1);
        if (!(rowDataObj instanceof List)) {
            throw new RuntimeException("Please check the row entry for " 
                    + rowKey + " because it is not parsing to a list (of " 
                    + "rowKey and rowDataValues items.");
        }
        List<?> rowData = (List<?>) rowDataObj;
        if (rowData.size() != columnKeys.size()) {
            throw new RuntimeException("The values list for series "
                    + rowKey + " does not contain the correct number of "
                    + "entries to match the columnKeys.");
        }

        for (int c = 0; c < rowData.size(); c++) {
            Object columnKey = columnKeys.get(c);
            dataset.setValue(objToDouble(rowData.get(c)), 
                    rowKey.toString(), columnKey.toString());
        }
    }
    
    /**
     * Writes a data table to a string in JSON format.
     * 
     * @param data  the data ({@code null} not permitted).
     * 
     * @return The string. 
     */
    public static String writeKeyedValues2D(KeyedValues2D data) {
        ArgChecks.nullNotPermitted(data, "data");
        StringWriter sw = new StringWriter();
        try {
            writeKeyedValues2D(data, sw);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return sw.toString();
    }

    /**
     * Writes the data in JSON format to the supplied writer.
     * 
     * @param data  the data ({@code null} not permitted).
     * @param writer  the writer ({@code null} not permitted).
     * 
     * @throws IOException if there is an I/O problem.
     */
    @SuppressWarnings("unchecked")
    public static void writeKeyedValues2D(KeyedValues2D data, Writer writer) 
            throws IOException {
        ArgChecks.nullNotPermitted(data, "data");
        ArgChecks.nullNotPermitted(writer, "writer");
        List<Comparable> columnKeys = data.getColumnKeys();
        List<Comparable> rowKeys = data.getRowKeys();
        writer.write("{");
        if (!columnKeys.isEmpty()) {
            writer.write("\"columnKeys\": [");
            boolean first = true;
            for (Comparable columnKey : columnKeys) {
                if (!first) {
                    writer.write(", ");
                } else {
                    first = false;
                }
                writer.write(JSONValue.toJSONString(columnKey.toString()));
            }
            writer.write("]");
        }
        if (!rowKeys.isEmpty()) {
            writer.write(", \"rows\": [");
            boolean firstRow = true;
            for (Comparable rowKey : rowKeys) {   
                if (!firstRow) {
                    writer.write(", [");
                } else {
                    writer.write("[");
                    firstRow = false;
                }
                // write the row data 
                writer.write(JSONValue.toJSONString(rowKey.toString()));
                writer.write(", [");
                boolean first = true;
                for (Comparable columnKey : columnKeys) {
                    if (!first) {
                        writer.write(", ");
                    } else {
                        first = false;
                    }
                    writer.write(JSONValue.toJSONString(data.getValue(rowKey, 
                            columnKey)));
                }
                writer.write("]]");
            }
            writer.write("]");
        }
        writer.write("}");
    }

    /**
     * Parses the supplied string and (if possible) creates a 
     * {@link KeyedValues3D} instance.
     * 
     * @param json  the JSON string ({@code null} not permitted).
     * 
     * @return A {@code KeyedValues3D} instance.
     */
    public static KeyedValues3D<String, String, String, Number> 
            readKeyedValues3D(String json) {
        StringReader in = new StringReader(json);
        KeyedValues3D<String, String, String, Number> result;
        try { 
            result = readKeyedValues3D(in);
        } catch (IOException ex) {
            // not for StringReader
            result = null;
        }
        return result;
    }

    /**
     * Parses character data from the reader and (if possible) creates a
     * {@link KeyedValues3D} instance.  This method will read back the data
     * written by {@link JSONUtils#writeKeyedValues3D(
     * com.orsoncharts.data.KeyedValues3D, java.io.Writer) }.
     * 
     * @param reader  the reader ({@code null} not permitted).
     * 
     * @return A {@code KeyedValues3D} instance.
     * 
     * @throws IOException if there is an I/O problem.  
     */
    @SuppressWarnings("unchecked")
    public static KeyedValues3D<String, String, String, Number> 
            readKeyedValues3D(Reader reader) throws IOException {
        JSONParser parser = new JSONParser();
        try {
            Map map = (Map) parser.parse(reader, createContainerFactory());
            StandardCategoryDataset3D result = new StandardCategoryDataset3D();
            if (map.isEmpty()) {
                return result;
            }
            
            // read the row keys, we'll use these to validate the row keys
            // supplied with the data
            Object rowKeysObj = map.get("rowKeys");
            List<String> rowKeys;
            if (rowKeysObj instanceof List) {
                rowKeys = (List<String>) rowKeysObj;
            } else {
                if (rowKeysObj == null) {
                    throw new RuntimeException("No 'rowKeys' defined.");    
                } else {
                    throw new RuntimeException("Please check the 'rowKeys', " 
                            + "the format does not parse to a list.");
                }
            }
            
            // read the column keys, the data is provided later in rows that
            // should have the same number of entries as the columnKeys list
            Object columnKeysObj = map.get("columnKeys");
            List<String> columnKeys;
            if (columnKeysObj instanceof List) {
                columnKeys = (List<String>) columnKeysObj;
            } else {
                if (columnKeysObj == null) {
                    throw new RuntimeException("No 'columnKeys' defined.");    
                } else {
                    throw new RuntimeException("Please check the 'columnKeys', " 
                            + "the format does not parse to a list.");
                }
            }
            
            // the data object should be a list of data series
            Object dataObj = map.get("data");
            if (dataObj instanceof List) {
                List<String> seriesList = (List<String>) dataObj;
                // each entry in the map has the series name as the key, and
                // the value is a map of row data (rowKey, list of values)
                for (Object seriesObj : seriesList) {
                    processSeries(seriesObj, rowKeys, columnKeys, result);
                }
            } else { // the 'data' entry is not parsing to a list
                if (dataObj == null) {
                    throw new RuntimeException("No 'data' section defined.");
                } else {
                    throw new RuntimeException("Please check the 'data' "
                            + "entry, the format does not parse to a list of "
                            + "series.");
                }
            }
            return result;
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    /**
     * Processes an entry for one series.
     * 
     * @param seriesObj  the series object.
     * @param rowKeys  the expected row keys.
     * @param columnKeys  the required column keys.
     */
    static <R extends Comparable<R>, C extends Comparable<C>> 
            void processSeries(Object seriesObj, List<R> rowKeys, 
            List<C> columnKeys, 
            StandardCategoryDataset3D<String, String, String> dataset) {
        
        if (!(seriesObj instanceof Map)) {
            throw new RuntimeException("Check the 'data' section it contains "
                    + "a series that does not parse to a map.");
        }
        
        // we expect the series data object to be a map of
        // rowKey ==> rowValueArray entries, where rowValueArray
        // should have the same number of entries as the columnKeys
        Map seriesMap = (Map) seriesObj;
        Object seriesKey = seriesMap.get("seriesKey");
        Object seriesRowsObj = seriesMap.get("rows");
        if (!(seriesRowsObj instanceof Map)) {
            throw new RuntimeException("Please check the series entry for " 
                    + seriesKey + " because it is not parsing to a map (of " 
                    + "rowKey -> rowDataValues items.");
        }
        Map<?, ?> seriesData = (Map<?, ?>) seriesRowsObj;
        for (Object rowKey : seriesData.keySet()) {
            if (!rowKeys.contains(rowKey)) {
                throw new RuntimeException("The row key " + rowKey + " is not "
                        + "listed in the rowKeys entry."); 
            }
            Object rowValuesObj = seriesData.get(rowKey);
            if (!(rowValuesObj instanceof List<?>)) {
                throw new RuntimeException("Please check the entry for series " 
                        + seriesKey + " and row " + rowKey + " because it "
                        + "does not parse to a list of values.");
            }
            List<?> rowValues = (List<?>) rowValuesObj;
            if (rowValues.size() != columnKeys.size()) {
                throw new RuntimeException("The values list for series "
                        + seriesKey + " and row " + rowKey + " does not " 
                        + "contain the correct number of entries to match "
                        + "the columnKeys.");
            }
            for (int c = 0; c < rowValues.size(); c++) {
                Object columnKey = columnKeys.get(c);
                dataset.addValue(objToDouble(rowValues.get(c)), 
                        seriesKey.toString(), rowKey.toString(), 
                        columnKey.toString());
            }
        }
    }
    
    /**
     * Returns a string containing the data in JSON format.
     * 
     * @param dataset  the data ({@code null} not permitted).
     * 
     * @return A string in JSON format. 
     */
    public static String writeKeyedValues3D(KeyedValues3D dataset) {
        ArgChecks.nullNotPermitted(dataset, "dataset");
        StringWriter sw = new StringWriter();
        try {
            writeKeyedValues3D(dataset, sw);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return sw.toString();
    }

    /**
     * Writes the dataset in JSON format to the supplied writer.
     * 
     * @param dataset  the dataset ({@code null} not permitted).
     * @param writer  the writer ({@code null} not permitted).
     * 
     * @throws IOException if there is an I/O problem.
     */
    @SuppressWarnings("unchecked")
    public static void writeKeyedValues3D(KeyedValues3D dataset, Writer writer) 
            throws IOException {
        ArgChecks.nullNotPermitted(dataset, "dataset");
        ArgChecks.nullNotPermitted(writer, "writer");

        writer.write("{");
        if (!dataset.getColumnKeys().isEmpty()) {
            writer.write("\"columnKeys\": [");
            boolean first = true;
            for (Object key : dataset.getColumnKeys()) {
                if (!first) {
                    writer.write(", ");
                } else {
                    first = false;
                }
                writer.write(JSONValue.toJSONString(key.toString()));
            }
            writer.write("], ");
        }
        
        // write the row keys
        if (!dataset.getRowKeys().isEmpty()) {
            writer.write("\"rowKeys\": [");
            boolean first = true;
            for (Object key : dataset.getRowKeys()) {
                if (!first) {
                    writer.write(", ");
                } else {
                    first = false;
                }
                writer.write(JSONValue.toJSONString(key.toString()));
            }
            writer.write("], ");
        }
        
        // write the data which is zero, one or many data series
        // a data series has a 'key' and a 'rows' attribute
        // the 'rows' attribute is a Map from 'rowKey' -> array of data values
        if (dataset.getSeriesCount() != 0) {
            writer.write("\"series\": [");
            boolean first = true;
            for (Object seriesKey : dataset.getSeriesKeys()) {
                if (!first) {
                    writer.write(", ");
                } else {
                    first = false;
                }
                writer.write("{\"seriesKey\": ");
                writer.write(JSONValue.toJSONString(seriesKey.toString()));
                writer.write(", \"rows\": [");
            
                boolean firstRow = true;
                for (Object rowKey : dataset.getRowKeys()) {
                    if (countForRowInSeries(dataset, (Comparable) seriesKey, 
                            (Comparable) rowKey) > 0) {
                        if (!firstRow) {
                            writer.write(", [");
                        } else {
                            writer.write("[");
                            firstRow = false;
                        }
                        // write the row values
                        writer.write(JSONValue.toJSONString(rowKey.toString()) 
                                + ", [");
                        for (int c = 0; c < dataset.getColumnCount(); c++) {
                            Object columnKey = dataset.getColumnKey(c);
                            if (c != 0) {
                                writer.write(", ");
                            }
                            writer.write(JSONValue.toJSONString(
                                    dataset.getValue((Comparable) seriesKey, 
                                    (Comparable) rowKey, 
                                    (Comparable) columnKey)));
                        }
                        writer.write("]]");
                    }
                }            
                writer.write("]}");
            }
            writer.write("]");
        }
        writer.write("}");
    }
 
    /**
     * Returns the number of non-{@code null} entries for the specified
     * series and row.
     * 
     * @param data  the dataset ({@code null} not permitted).
     * @param seriesKey  the series key ({@code null} not permitted).
     * @param rowKey  the row key ({@code null} not permitted).
     * 
     * @return The count. 
     */
    @SuppressWarnings("unchecked")
    private static int countForRowInSeries(KeyedValues3D data, 
            Comparable seriesKey, Comparable rowKey) {
        ArgChecks.nullNotPermitted(data, "data");
        ArgChecks.nullNotPermitted(seriesKey, "seriesKey");
        ArgChecks.nullNotPermitted(rowKey, "rowKey");
        int seriesIndex = data.getSeriesIndex(seriesKey);
        if (seriesIndex < 0) {
            throw new IllegalArgumentException("Series not found: " 
                    + seriesKey);
        }
        int rowIndex = data.getRowIndex(rowKey);
        if (rowIndex < 0) {
            throw new IllegalArgumentException("Row not found: " + rowKey);
        }
        int count = 0;
        for (int c = 0; c < data.getColumnCount(); c++) {
            Object n = data.getValue(seriesIndex, rowIndex, c);
            if (n != null) {
                count++;
            }
        }
        return count;
    }

    /**
     * Parses the string and (if possible) creates an {XYZDataset} instance 
     * that represents the data.  This method will read back the data that
     * is written by 
     * {@link #writeXYZDataset(com.orsoncharts.data.xyz.XYZDataset)}.
     * 
     * @param json  a JSON formatted string ({@code null} not permitted).
     * 
     * @return A dataset.
     * 
     * @see #writeXYZDataset(com.orsoncharts.data.xyz.XYZDataset) 
     */
    public static XYZDataset<String> readXYZDataset(String json) {
        ArgChecks.nullNotPermitted(json, "json");
        StringReader in = new StringReader(json);
        XYZDataset<String> result;
        try {
            result = readXYZDataset(in);
        } catch (IOException ex) {
            // not for StringReader
            result = null;
        }
        return result;
    }
    
    /**
     * Parses character data from the reader and (if possible) creates an 
     * {XYZDataset} instance that represents the data.
     * 
     * @param reader  a reader ({@code null} not permitted).
     * 
     * @return A dataset.
     * 
     * @throws IOException if there is an I/O problem.
     */
    @SuppressWarnings("unchecked")
    public static XYZDataset<String> readXYZDataset(Reader reader) throws IOException {
        JSONParser parser = new JSONParser();
        XYZSeriesCollection<String> result = new XYZSeriesCollection<String>();
        try {
            List<?> list = (List<?>) parser.parse(reader, 
                    createContainerFactory());
            // each entry in the array should be a series array (where the 
            // first item is the series name and the next value is an array 
            // (of arrays of length 3) containing the data items
            for (Object seriesArray : list) {
                if (seriesArray instanceof List) {
                    List<?> seriesList = (List<?>) seriesArray;
                    XYZSeries series = createSeries(seriesList);
                    result.add(series);
                } else {
                    throw new RuntimeException(
                            "Input for a series did not parse to a list.");
                }
            }
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
        return result;
    }

    /**
     * Returns a string containing the dataset in JSON format.
     * 
     * @param dataset  the dataset ({@code null} not permitted).
     * 
     * @return A string in JSON format. 
     */
    public static String writeXYZDataset(XYZDataset dataset) {
        StringWriter sw = new StringWriter();
        try {
            writeXYZDataset(dataset, sw);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return sw.toString();
    }
    
    /**
     * Writes the dataset in JSON format to the supplied writer.
     * 
     * @param dataset  the data ({@code null} not permitted).
     * @param writer  the writer ({@code null} not permitted).
     * 
     * @throws IOException if there is an I/O problem.
     */
    @SuppressWarnings("unchecked")
    public static void writeXYZDataset(XYZDataset dataset, Writer writer)
            throws IOException {
        writer.write("[");
        boolean first = true;
        for (Object seriesKey : dataset.getSeriesKeys()) {
            if (!first) {
                writer.write(", [");
            } else {
                writer.write("[");
                first = false;
            }
            writer.write(JSONValue.toJSONString(seriesKey.toString()));
            writer.write(", [");
            int seriesIndex = dataset.getSeriesIndex((Comparable) seriesKey);
            int itemCount = dataset.getItemCount(seriesIndex);
            for (int i = 0; i < itemCount; i++) {
                if (i != 0) {
                    writer.write(", ");
                }
                writer.write("[");
                writer.write(JSONValue.toJSONString(Double.valueOf(
                        dataset.getX(seriesIndex, i))));
                writer.write(", ");
                writer.write(JSONValue.toJSONString(Double.valueOf(
                        dataset.getY(seriesIndex, i))));
                writer.write(", ");
                writer.write(JSONValue.toJSONString(Double.valueOf(
                        dataset.getZ(seriesIndex, i))));
                writer.write("]");
            }
            writer.write("]]");
        }
        writer.write("]");        
    }
        
    /**
     * Converts an arbitrary object to a double.
     * 
     * @param obj  an object ({@code null} permitted).
     * 
     * @return A double primitive (possibly Double.NaN). 
     */
    private static double objToDouble(Object obj) {
        if (obj == null) {
            return Double.NaN;
        }
        if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        }
        double result = Double.NaN;
        try {
            result = Double.valueOf(obj.toString());
        } catch (Exception e) {
            
        }
        return result;
    }
    
    /**
     * Creates an {@link XYZSeries} from the supplied list.  The list is 
     * coming from the JSON parser and should contain the series name as the
     * first item, and a list of data items as the second item.  The list of
     * data items should be a list of lists (
     * 
     * @param sArray  the series array.
     * 
     * @return A data series. 
     */
    @SuppressWarnings("unchecked")
    private static XYZSeries createSeries(List<?> sArray) {
        Comparable<?> key = (Comparable<?>) sArray.get(0);
        List<?> dataItems = (List<?>) sArray.get(1);
        XYZSeries series = new XYZSeries(key);
        for (Object item : dataItems) {
            if (item instanceof List<?>) {
                List<?> xyz = (List<?>) item;
                if (xyz.size() != 3) {
                    throw new RuntimeException(
                            "A data item should contain three numbers, " 
                            + "but we have " + xyz);
                }
                double x = objToDouble(xyz.get(0));
                double y = objToDouble(xyz.get(1));
                double z = objToDouble(xyz.get(2));
                series.add(x, y, z);
                
            } else {
                throw new RuntimeException(
                        "Expecting a data item (x, y, z) for series " + key 
                        + " but found " + item + ".");
            }
        }
        return series;
    }

    /**
     * Returns a custom container factory for the JSON parser.  We create this 
     * so that the collections respect the order of elements.
     * 
     * @return The container factory.
     */
    private static ContainerFactory createContainerFactory() {
        return new ContainerFactory() {
            @Override
            public Map createObjectContainer() {
                return new LinkedHashMap();
            }

            @Override
            public List creatArrayContainer() {
                return new ArrayList();
            }
        };
    }

}
