/* 
 * Orson Charts
 * ------------
 * Copyright (C) 2014 Object Refinery Limited.
 */

"use strict";

if (!orsoncharts) orsoncharts = {};

/**
 * Constructor for a new KeyedValue3DLabels instance
 * @constructor
 */
orsoncharts.KeyedValue3DLabels = function() {
    if (!(this instanceof orsoncharts.KeyedValue3DLabels)) {
	return new orsoncharts.KeyedValue3DLabels();
    }
    this.format = "{S}, {R}, {C} = {V}";
    this.valueDP = 2;
};

// Generates a label for an item in a KeyedValue3DDataset.
orsoncharts.KeyedValue3DLabels.prototype.itemLabel = function(keyedValues3D, 
        seriesIndex, rowIndex, columnIndex) {
    var labelStr = new String(this.format);
    var seriesKeyStr = keyedValues3D.seriesKey(seriesIndex);
    var rowKeyStr = keyedValues3D.rowKey(rowIndex);
    var columnKeyStr = keyedValues3D.columnKey(columnIndex);
    var value = keyedValues3D.valueByIndex(seriesIndex, rowIndex, columnIndex);
    var valueStr = value.toFixed(this.valueDP);
    labelStr = labelStr.replace(/{S}/g, seriesKeyStr);
    labelStr = labelStr.replace(/{R}/g, rowKeyStr);
    labelStr = labelStr.replace(/{C}/g, columnKeyStr);
    labelStr = labelStr.replace(/{V}/g, valueStr);
    return labelStr;
};