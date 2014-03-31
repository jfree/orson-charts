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

/**
 * ParseException explains why and where the error occurs in source JSON text.
 *
 */
public class ParseException extends Exception {
    
    private static final long serialVersionUID = -7880698968187728548L;
    
    public static final int ERROR_UNEXPECTED_CHAR = 0;
    public static final int ERROR_UNEXPECTED_TOKEN = 1;
    public static final int ERROR_UNEXPECTED_EXCEPTION = 2;

    private int errorType;
    private Object unexpectedObject;
    private int position;
    
    public ParseException(int errorType){
        this(-1, errorType, null);
    }
    
    public ParseException(int errorType, Object unexpectedObject){
        this(-1, errorType, unexpectedObject);
    }
    
    public ParseException(int position, int errorType, 
            Object unexpectedObject) {
        this.position = position;
        this.errorType = errorType;
        this.unexpectedObject = unexpectedObject;
    }
    
    public int getErrorType() {
        return errorType;
    }
    
    public void setErrorType(int errorType) {
        this.errorType = errorType;
    }
    
    /**
     * @see com.orsoncharts.util.json.parser.JSONParser#getPosition()
     * 
     * @return The character position (starting with 0) of the input where the 
     * error occurs.
     */
    public int getPosition() {
        return position;
    }
    
    public void setPosition(int position) {
        this.position = position;
    }
    
    /**
     * @see com.orsoncharts.util.json.parser.Yytoken
     * 
     * @return One of the following base on the value of errorType:
     *         ERROR_UNEXPECTED_CHAR        java.lang.Character
     *         ERROR_UNEXPECTED_TOKEN       com.orsoncharts.util.json.simple.parser.Yytoken
     *         ERROR_UNEXPECTED_EXCEPTION   java.lang.Exception
     */
    public Object getUnexpectedObject() {
        return unexpectedObject;
    }
    
    public void setUnexpectedObject(Object unexpectedObject) {
        this.unexpectedObject = unexpectedObject;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        
        switch(errorType) {
        case ERROR_UNEXPECTED_CHAR:
            sb.append("Unexpected character (").append(unexpectedObject);
            sb.append(") at position ").append(position).append(".");
            break;
        case ERROR_UNEXPECTED_TOKEN:
            sb.append("Unexpected token ").append(unexpectedObject);
            sb.append(" at position ").append(position).append(".");
            break;
        case ERROR_UNEXPECTED_EXCEPTION:
            sb.append("Unexpected exception at position ").append(position);
            sb.append(": ").append(unexpectedObject);
            break;
        default:
            sb.append("Unkown error at position ").append(position).append(".");
            break;
        }
        return sb.toString();
    }
}

