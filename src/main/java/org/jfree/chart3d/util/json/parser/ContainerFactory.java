/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013-2020, by Object Refinery Limited.
 * 
 * https://github.com/jfree/orson-charts
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
 * org.jfree.chart3d.util.json.* to avoid conflicts with any other version that
 * may be present on the classpath. 
 * 
 */

package org.jfree.chart3d.util.json.parser;

import java.util.List;
import java.util.Map;

/**
 * Container factory for creating containers for JSON object and JSON array.
 * 
 * @see org.jfree.chart3d.util.json.parser.JSONParser#parse(java.io.Reader,
 *     ContainerFactory)
 */
public interface ContainerFactory {
    
    /**
     * @return A Map instance to store JSON object, or null if you want to use
     *     org.jfree.chart3d.util.json.JSONObject.
     */
    Map createObjectContainer();
    
    /**
     * @return A List instance to store JSON array, or null if you want to use 
     *     org.jfree.chart3d.util.json.JSONArray. 
     */
    List creatArrayContainer();
}

