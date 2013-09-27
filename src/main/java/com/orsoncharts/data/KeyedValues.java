/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.orsoncharts.data;

import java.util.List;

/**
 * A list of values that are associated with unique keys.
 */
public interface KeyedValues<T> extends Values<T> { 

    public Comparable getKey(int index);
  
    public int getIndex(Comparable key);
  
    public List<Comparable> getKeys();

    public T getValue(Comparable key);
}
