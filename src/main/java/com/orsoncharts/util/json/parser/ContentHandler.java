/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * JSON.simple
 * -----------
 * The code in this file originates from the JSON.simple project by 
 * FangYidong<fangyidong@yahoo.com.cn>:
 * 
 *     https://code.google.com/p/json-simple/
 *  
 * which is licensed under the Apache Software License version 2.0.  
 * 
 * It has been modified locally and repackaged under 
 * com.orsoncharts.util.json.* to avoid conflicts with any other version that
 * may be present on the classpath. 
 * 
 */

package com.orsoncharts.util.json.parser;

import java.io.IOException;

/**
 * A simplified and stoppable SAX-like content handler for stream processing of 
 * JSON text. 
 *
 * @see com.orsoncharts.util.json.parser.JSONParser#parse(java.io.Reader,
 *     ContentHandler, boolean)
 */
public interface ContentHandler {
    
    /**
     * Receive notification of the beginning of JSON processing.
     * The parser will invoke this method only once.
     * 
     * @throws ParseException  JSONParser will stop and throw the same 
     *     exception to the caller when receiving this exception.
     * @throws IOException if there is an I/O problem.
     */
    void startJSON() throws ParseException, IOException;
    
    /**
     * Receive notification of the end of JSON processing.
     * 
     * @throws ParseException if there is a parsing problem.
     * @throws IOException if there is an I/O problem. 
     */
    void endJSON() throws ParseException, IOException;
    
    /**
     * Receive notification of the beginning of a JSON object.
     * 
     * @return false if the handler wants to stop parsing after return.
     * @throws ParseException  JSONParser will stop and throw the same 
     *     exception to the caller when receiving this exception.
     * @throws IOException if there is an I/O problem. 
     * 
     * @see #endJSON
     */
    boolean startObject() throws ParseException, IOException;
    
    /**
     * Receive notification of the end of a JSON object.
     * 
     * @return false if the handler wants to stop parsing after return.
     * @throws ParseException if there is a parsing problem.
     * @throws IOException if there is an I/O problem. 
     * 
     * @see #startObject
     */
    boolean endObject() throws ParseException, IOException;
    
    /**
     * Receive notification of the beginning of a JSON object entry.
     * 
     * @param key  key of a JSON object entry. 
     * 
     * @return false if the handler wants to stop parsing after return.
     * @throws ParseException if there is a parsing problem.
     * @throws IOException if there is an I/O problem. 
     * 
     * @see #endObjectEntry
     */
    boolean startObjectEntry(String key) throws ParseException, IOException;
    
    /**
     * Receive notification of the end of the value of previous object entry.
     * 
     * @return false if the handler wants to stop parsing after return.
     * @throws ParseException if there is a parsing problem.
     * @throws IOException if there is an I/O problem. 
     * 
     * @see #startObjectEntry
     */
    boolean endObjectEntry() throws ParseException, IOException;
    
    /**
     * Receive notification of the beginning of a JSON array.
     * 
     * @return false if the handler wants to stop parsing after return.
     * @throws ParseException if there is a parsing problem.
     * @throws IOException if there is an I/O problem. 
     *
     * @see #endArray
     */
    boolean startArray() throws ParseException, IOException;
    
    /**
     * Receive notification of the end of a JSON array.
     * 
     * @return false  if the handler wants to stop parsing after return.
     * @throws ParseException if there is a parsing problem.
     * @throws IOException if there is an I/O problem. 
     * 
     * @see #startArray
     */
    boolean endArray() throws ParseException, IOException;
    
    /**
     * Receive notification of the JSON primitive values:
     *     java.lang.String,
     *     java.lang.Number,
     *     java.lang.Boolean
     *     null
     * 
     * @param value  instance of the following:
     *             java.lang.String,
     *             java.lang.Number,
     *             java.lang.Boolean,
     *             null
     * 
     * @return false if the handler wants to stop parsing after return.
     * @throws ParseException if there is a parsing problem.
     * @throws IOException if there is an I/O problem.  
     */
    boolean primitive(Object value) throws ParseException, IOException;
        
}

