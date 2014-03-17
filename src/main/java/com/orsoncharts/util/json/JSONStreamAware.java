package com.orsoncharts.util.json;

import java.io.IOException;
import java.io.Writer;

/**
 * Beans that support customized output of JSON text to a writer shall implement this interface.  
 * @author FangYidong<fangyidong@yahoo.com.cn>
 */
public interface JSONStreamAware {
    
    /**
     * write JSON string to out.
     * @param out
     * @throws IOException  
     */
    void writeJSONString(Writer out) throws IOException;

}
