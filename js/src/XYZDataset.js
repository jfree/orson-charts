/* 
 * Copyright (C) 2014 Object Refinery Limited and KNIME AG
 */

"use strict";

orsoncharts = orsoncharts ? orsoncharts : {};

/**
 * Constructor for a new XYZDataset
 * @constructor
 */
orsoncharts.XYZDataset = function() {
  this.data = { "series": [], "selections": []};
  this.listeners = [];
};

// returns the number of series in the dataset
orsoncharts.XYZDataset.prototype.seriesCount = function() {
  return this.data.series.length;
};

// returns an array of all series keys in the dataset
orsoncharts.XYZDataset.prototype.seriesKeys = function() {
  return this.data.series.map(function(d) {
    return d[0];
  });
};

// returns the key for the series with the specified index
orsoncharts.XYZDataset.prototype.seriesKey = function(seriesIndex) {
  return this.data.series[seriesIndex][0];
};

// returns the index for the series with the specified key, or -1
orsoncharts.XYZDataset.prototype.seriesIndex = function(seriesKey) {
  if (!(typeof seriesKey === 'string')) throw new Error("The 'seriesKey' must be a string.");
  var seriesArray = this.data.series;
  var seriesCount = this.data.series.length;
  for (var s = 0; s < seriesCount; s++) {
    if (seriesArray[s][0] === seriesKey) {
      return s;
    }
  }
  return -1;
};

// returns the number of items in the specified series
orsoncharts.XYZDataset.prototype.itemCount = function(seriesIndex) {
  return this.data.series[seriesIndex][1].length;
};

// returns the x-value for the specified item in a series
orsoncharts.XYZDataset.prototype.x = function(seriesIndex, itemIndex) {
  return this.data.series[seriesIndex][1][itemIndex][0]; 
};

// returns the y-value for the specified item in a series
orsoncharts.XYZDataset.prototype.y = function(seriesIndex, itemIndex) {
  return this.data.series[seriesIndex][1][itemIndex][1];     
};

// returns the z-value for the specified item in a series
orsoncharts.XYZDataset.prototype.z = function(seriesIndex, itemIndex) {
  return this.data.series[seriesIndex][1][itemIndex][2];     
};

// returns an array [x, y, z] for the specified item in a series
orsoncharts.XYZDataset.prototype.item = function(seriesIndex, itemIndex) {
  return this.data.series[seriesIndex][1][itemIndex]; 
};

// adds a data value (x, y) to the specified series (if the series does not
// already exist in the dataset it is created
orsoncharts.XYZDataset.prototype.add = function(seriesKey, x, y, z) {
  if (!(typeof seriesKey === 'string')) throw new Error("The 'seriesKey' must be a string.");
  var s = this.seriesIndex(seriesKey);
  if (s < 0) this.addSeries(seriesKey);
  var series = this.data.series;
  series[series.length-1][1].push([x, y, z]);
};

// adds a new empty series with the specified key (which must be a string).
orsoncharts.XYZDataset.prototype.addSeries = function(seriesKey) {
  if (!(typeof seriesKey === 'string')) throw new Error("The 'seriesKey' must be a string.");
  var s = this.seriesIndex(seriesKey);
  if (s >= 0) throw new Error("There is already a series with the key '" + seriesKey);
  this.data["series"].push([seriesKey, []]);
};

// removeSeries
orsoncharts.XYZDataset.prototype.removeSeries = function(seriesKey) {
  if (!(typeof seriesKey === 'string')) throw new Error("The 'seriesKey' must be a string.");
  var s = this.seriesIndex(seriesKey);
  if (s >= 0) {
    this.data["series"].splice(s, 1);
  }    
};

// Adds a listener to the dataset (the listener method will be called whenever 
// the dataset is modified)
orsoncharts.XYZDataset.prototype.addListener = function(listenerMethod) {
  this.listeners.push(listenerMethod);  
};

// Deregisters the specified listener so that it no longer receives
// notification of dataset changes
orsoncharts.XYZDataset.prototype.removeListener = function(listenerMethod) {
  var i = this.listeners.indexOf(listenerMethod);
  if (i >= 0) {
    this.listeners.splice(i, 1);
  }
};


