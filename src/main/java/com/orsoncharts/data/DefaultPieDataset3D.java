/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package com.orsoncharts.data;

import com.orsoncharts.ArgChecks;

/**
 * A pie dataset.
 */
public class DefaultPieDataset3D extends AbstractDataset3D 
        implements PieDataset3D {

    private DefaultKeyedValues data;

    /**
     * Creates a new (empty) dataset.
     */
    public DefaultPieDataset3D() {
        this.data = new DefaultKeyedValues();
    }

    /**
     * Returns the number of items in the dataset.
     * 
     * @return The number of items in the dataset. 
     */
    @Override
    public int getItemCount() {
        return this.data.getItemCount();
    }

    @Override
    public Comparable getKey(int item) {
        return this.data.getKey(item);
    }

    @Override
    public Number getValue(int item) {
        return this.data.getValue(item);
    }

    /**
     * Adds a value to the dataset (if there is already a value with the given
     * key, the value is overwritten).
     * 
     * @param key  the key (<code>null</code> not permitted).
     * @param value 
     */
    public void add(Comparable key, Number value) {
        ArgChecks.nullNotPermitted(key, "key");
        // TODO : need to validate that the keys are unique
        this.data.addValue(key, value);
        fireDatasetChanged();
    }

}
