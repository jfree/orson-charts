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

import java.util.List;
import java.util.Map;

/**
 * Container factory for creating containers for JSON object and JSON array.
 * 
 * @see com.orsoncharts.util.json.parser.JSONParser#parse(java.io.Reader,
 *     ContainerFactory)
 */
public interface ContainerFactory {
    
    /**
     * @return A Map instance to store JSON object, or null if you want to use
     *     com.orsoncharts.util.json.JSONObject.
     */
    Map createObjectContainer();
    
    /**
     * @return A List instance to store JSON array, or null if you want to use 
     *     com.orsoncharts.util.json.JSONArray. 
     */
    List creatArrayContainer();
}

