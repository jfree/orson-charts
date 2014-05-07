package com.orsoncharts.util.json;

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
