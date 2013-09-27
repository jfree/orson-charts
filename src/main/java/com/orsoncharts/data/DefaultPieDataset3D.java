/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package com.orsoncharts.data;

import java.util.List;
import com.orsoncharts.ArgChecks;
import com.orsoncharts.plot.PiePlot3D;

/**
 * A dataset that can be used with a {@link PiePlot3D}.
 */
public class DefaultPieDataset3D extends AbstractDataset3D 
        implements PieDataset3D {

    private DefaultKeyedValues<Number> data;

    /**
     * Creates a new (empty) dataset.
     */
    public DefaultPieDataset3D() {
        this.data = new DefaultKeyedValues<Number>();
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
    public int getIndex(Comparable key) {
        return this.data.getIndex(key);
    }

    @Override
    public Number getValue(int item) {
        return this.data.getValue(item);
    }

    @Override
    public Number getValue(Comparable key) {
        return this.data.getValue(key);
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

    @Override
    public List<Comparable> getKeys() {
        return this.data.getKeys();
    }

    @Override
    public double getDoubleValue(int item) {
        return this.data.getDoubleValue(item);
    }

}
