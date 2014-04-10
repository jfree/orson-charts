/* 
 * Orson Charts
 * ------------
 * Copyright (C) 2014 Object Refinery Limited.
 */

"use strict";

if (!orsoncharts) orsoncharts = {};

/**
 * Constructor for a new XYZLabels instance
 * @constructor
 */
orsoncharts.XYZLabels = function() {
    if (!(this instanceof orsoncharts.XYZLabels)) {
	return new orsoncharts.XYZLabels();
    }
    this.format = "{X}, {Y}, {Z} / {S}";
    this.xDP = 2;
    this.yDP = 2;
    this.zDP = 2;
};

// Generates a label for an item in a XYZDataset.
orsoncharts.XYZLabels.prototype.itemLabel = function(dataset, seriesKey, itemIndex) {
    var labelStr = new String(this.format);
    var seriesKeyStr = seriesKey;
    var seriesIndex = dataset.seriesIndex(seriesKey);
    var item = dataset.item(seriesIndex, itemIndex);
    var xStr = item[0].toFixed(this.xDP);
    var yStr = item[1].toFixed(this.yDP);
    var zStr = item[2].toFixed(this.zDP);
    labelStr = labelStr.replace(/{X}/g, xStr);
    labelStr = labelStr.replace(/{Y}/g, yStr);
    labelStr = labelStr.replace(/{Z}/g, zStr);
    labelStr = labelStr.replace(/{S}/g, seriesKeyStr);
    return labelStr;
};
