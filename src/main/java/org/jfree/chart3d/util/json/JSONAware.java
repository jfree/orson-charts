/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013-2022, by David Gilbert.
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

package org.jfree.chart3d.util.json;

/**
 * Classes that support customized output of JSON text shall implement this 
 * interface.
 * <br><br>
 * This class is for internal use by Orson Charts, it is not 
 * part of the supported API and you should not call it directly.  If you need
 * JSON support in your project you should include JSON.simple 
 * (https://code.google.com/p/json-simple/) or some other JSON library directly
 * in your project.
 */
public interface JSONAware {
    
    /**
     * Returns a JSON string representing the object.
     * 
     * @return A JSON string.
     */
    String toJSONString();
}
