/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.orsoncharts;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Some utility methods for serialization.
 */
public class TestUtils {

    /**
     * Returns a copy of an object that is obtained by serializing the original
     * object to a byte array then deserializing the bytes from the byte
     * array.  
     * 
     * @param original  the original object (<code>null</code> not permitted).
     * 
     * @return A copy of the original object. 
     */
    public static Object serialized(Serializable original) {
        Object result = null;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ObjectOutput out;
        try {
            out = new ObjectOutputStream(buffer);
            out.writeObject(original);
            out.close();
            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            result = in.readObject();
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return result;
    }   
}
