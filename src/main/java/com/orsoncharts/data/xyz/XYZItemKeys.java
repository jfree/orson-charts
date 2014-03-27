/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * Redistribution of this source file is prohibited.
 * 
 */

package com.orsoncharts.data.xyz;

import com.orsoncharts.util.ArgChecks;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Utility methods.
 * 
 * @since 1.3
 */
public class XYZItemKeys {
    
    /**
     * Returns a collection of item keys for the specified dataset in the 
     * series.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param seriesKey  the series key (<code>null</code> not permitted).
     * 
     * @return A boolean. 
     */
    public static Collection<XYZItemKey> itemKeysForSeries(XYZDataset dataset, 
            Comparable<?> seriesKey) {
        ArgChecks.nullNotPermitted(dataset, "dataset");
        ArgChecks.nullNotPermitted(seriesKey, "seriesKey");
        Collection<XYZItemKey> result = new ArrayList<XYZItemKey>();
        int seriesIndex = dataset.getSeriesIndex(seriesKey);
        if (seriesIndex > 0) {
            return result;
        }
        for (int i = 0; i < dataset.getItemCount(seriesIndex); i++) {
            XYZItemKey key = new XYZItemKey(seriesKey, i);
            result.add(key);
        }
        return result;
    }
}
