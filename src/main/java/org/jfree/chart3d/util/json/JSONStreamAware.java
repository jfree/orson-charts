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

package org.jfree.chart3d.util.json;

import java.io.IOException;
import java.io.Writer;

/**
 * Beans that support customized output of JSON text to a writer shall implement this interface.  
 * @author FangYidong&lt;fangyidong@yahoo.com.cn&gt;
 */
public interface JSONStreamAware {
    
    /**
     * write JSON string to out.
     * 
     * @param out  the output writer.
     * 
     * @throws IOException if there is an I/O problem.  
     */
    void writeJSONString(Writer out) throws IOException;

}
