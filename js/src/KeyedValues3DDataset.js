/* 
 * Copyright (C) 2014 Object Refinery Limited.
 */

if (!orsoncharts) orsoncharts = {};

/**
 * Constructor for a new KeyedValuesDataset3D
 * @constructor
 */
orsoncharts.KeyedValues3DDataset = function() {
  if (!(this instanceof orsoncharts.KeyedValues3DDataset)) {
    return new orsoncharts.KeyedValues3DDataset();
  }
  this.data = { "columnKeys": [], "rowKeys": [], "series": [] };
  this.listeners = [];
};

// Returns true if the dataset is empty and false otherwise.
orsoncharts.KeyedValues3DDataset.prototype.isEmpty = function() {
  return this.data["columnKeys"].length === 0 
      && this.data["rowKeys"].length === 0;
};

// Returns the number of series in the dataset.
orsoncharts.KeyedValues3DDataset.prototype.seriesCount = function() {
  return this.data.series.length;  
};

// Returns the number of rows in the dataset.
orsoncharts.KeyedValues3DDataset.prototype.rowCount = function() {
  return this.data.rowKeys.length;
};
  
// Returns the number of columns in the dataset.
orsoncharts.KeyedValues3DDataset.prototype.columnCount = function() {
  return this.data["columnKeys"].length; 
};

orsoncharts.KeyedValues3DDataset.prototype._fetchRow = function(seriesIndex, rowKey) {
  var rows = this.data.series[seriesIndex].rows;
  for (var r = 0; r < rows.length; r++) {
    if (rows[r][0] === rowKey) {
      return rows[r];
    }
  }
  return null;
};

// Returns a value by series, row and column index.
orsoncharts.KeyedValues3DDataset.prototype.valueByIndex = function(seriesIndex, 
    rowIndex, columnIndex) {
    var rowKey = this.rowKey(rowIndex);
    var row = this._fetchRow(seriesIndex, rowKey);
    if (row === null) {
        return null;
    } else {
        return row[1][columnIndex];
    }
};
    
// Returns the index of the series with the specified key, or -1.
orsoncharts.KeyedValues3DDataset.prototype.seriesIndex = function(seriesKey) {
  var seriesCount = this.seriesCount();
  for (var s = 0; s < seriesCount; s++) {
    if (this.data.series[s].seriesKey === seriesKey) {
      return s;
    }
  }
  return -1;
};

// Returns the key for the series with the specified index.
orsoncharts.KeyedValues3DDataset.prototype.seriesKey = function(seriesIndex) {
  return this.data.series[seriesIndex].seriesKey;  
};
    
// Returns the key for the row with the specified index.
orsoncharts.KeyedValues3DDataset.prototype.rowKey = function(rowIndex) {
  return this.data.rowKeys[rowIndex];  
};

// Returns the index of the row with the specified key.
orsoncharts.KeyedValues3DDataset.prototype.rowIndex = function(rowKey) {
  var rowCount = this.data.rowKeys.length;
  for (var r = 0; r < rowCount; r++) {
    if (this.data.rowKeys[r] === rowKey) {
      return r;
    }
  }
  return -1;
};
    
// Returns all the row keys.
orsoncharts.KeyedValues3DDataset.prototype.rowKeys = function() {
  return this.data.rowKeys.map(function(d) { return d; });
};

// Returns the key for the column with the specified index.
orsoncharts.KeyedValues3DDataset.prototype.columnKey = function(columnIndex) {
  return this.data.columnKeys[columnIndex];
};
    
// Returns the index of hte column with the specified key.
orsoncharts.KeyedValues3DDataset.prototype.columnIndex = function(columnKey) {
  var columnCount = this.data.columnKeys.length;
  for (var c = 0; c < columnCount; c++) {
    if (this.data.columnKeys[c] === columnKey) {
      return c;
    }
  }
  return -1;
};

// Returns all the column keys.
orsoncharts.KeyedValues3DDataset.prototype.columnKeys = function() {
  return this.data.columnKeys.map(function(d) { return d; });    
};
    
// Returns the value for the item identified by the specified series, row and
// column keys.
orsoncharts.KeyedValues3DDataset.prototype.valueByKey = function(seriesKey, rowKey, 
        columnKey) {
  var seriesIndex = this.seriesIndex(seriesKey);
    var row = this._fetchRow(seriesIndex, rowKey);
    if (row === null) {
        return null;
    } else {
  var columnIndex = this.columnIndex(columnKey);
        return row[1][columnIndex];
    }
};

// Adds a listener to the dataset (the listener method will be called whenever 
// the dataset is modified)
orsoncharts.KeyedValues3DDataset.prototype.addListener = function(listenerMethod) {
  this.listeners.push(listenerMethod);  
};

// Deregisters the specified listener so that it no longer receives
// notification of dataset changes
orsoncharts.KeyedValues3DDataset.prototype.removeListener = function(listenerMethod) {
  var i = this.listeners.indexOf(listenerMethod);
  if (i >= 0) {
    this.listeners.splice(i, 1);
    }
};

// Notifies all registered listeners that there has been a change to this dataset
orsoncharts.KeyedValues3DDataset.prototype.notifyListeners = function() {
  // TODO: call each listenerMethod
};

// adds a value to the dataset (or updates an existing value)
orsoncharts.KeyedValues3DDataset.prototype.add = function(seriesKey, rowKey, 
    columnKey, value) {
    
  if (this.isEmpty()) {
    this.data.rowKeys.push(rowKey);
    this.data.columnKeys.push(columnKey);
    this.data.series.push({"seriesKey": seriesKey, 
      "rows": [[rowKey, [value]]]});
  } else {
    var seriesIndex = this.seriesIndex(seriesKey);
    if (seriesIndex < 0) {
      this.data.series.push({"seriesKey": seriesKey, "rows": []});
      seriesIndex = this.data.series.length - 1;
    }
    var columnIndex = this.columnIndex(columnKey);
    if (columnIndex < 0) {
        // add the column key and insert a null data item in all existing rows
        this.data.columnKeys.push(columnKey);
        for (var s = 0; s < this.data.series.length; s++) {
          var rows = this.data.series[s].rows;
          for (var r = 0; r < rows.length; r++) {
            rows[r][1].push(null);
          }
        }
        columnIndex = this.columnCount() - 1;
    }
    var rowIndex = this.rowIndex(rowKey);
    if (rowIndex < 0) {
      this.data.rowKeys.push(rowKey);
      // add the row for the current series only
      var rowData = orsoncharts.Utils.makeArrayOf(null, this.columnCount());
      rowData[columnIndex] = value;
      this.data.series[seriesIndex].rows.push([rowKey, rowData]);       
    } else {
      var row = this._fetchRow(seriesIndex, rowKey);
      if (row !== null) {
        row[1][columnIndex] = value;
      } else {
        var rowData = orsoncharts.Utils.makeArrayOf(null, this.columnCount());
        rowData[columnIndex] = value;
        this.data.series[seriesIndex].rows.push([rowKey, rowData]); 
      }
    }
  }
};

orsoncharts.KeyedValues3DDataset.prototype.dataFromJSON = function(jsonStr) {
    this.data = JSON.parse(jsonStr);
    if (!this.data.hasOwnProperty("rowKeys")) {
        this.data.rowKeys = [];
    }
    if (!this.data.hasOwnProperty("columnKeys")) {
        this.data.columnKeys = [];
    }
    if (!this.data.hasOwnProperty("series")) {
        this.data.series = [];
    }
    this.notifyListeners();
};



