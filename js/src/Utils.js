/* 
 * Copyright (C) 2014 Object Refinery Limited
 */

var orsoncharts;
if (!orsoncharts) orsoncharts = {};

orsoncharts.Utils = {};

orsoncharts.Utils.makeArrayOf = function(value, length) {
  var arr = [], i = length;
  while (i--) {
    arr[i] = value;
  }
  return arr;
};

// returns the chart entity reference for this element        
orsoncharts.Utils.findChartRef = function(element) {
    var id = element.getAttribute("jfreesvg:ref");
    var found = false;
    var current = element;
    while (!found) {
        current = current.parentNode;
        if (current != null) {
            id = current.getAttribute("jfreesvg:ref");
            found = (id != null);
        } else {
            found = true;
        }
    }
    return id;
}

// find the chart id by finding the group that is written for the entire chart
orsoncharts.Utils.findChartId = function(element) {
    var id = null;
    var found = false;
    var current = element;
    while (!found) {
        current = current.parentNode;
        if (current != null) {
            var ref = current.getAttribute("jfreesvg:ref");
            if (ref == 'ORSON_CHART_TOP_LEVEL') {
                found = true;
                id = current.getAttribute("id");
            }
        } else {
            found = true;
        }
    }
    return id;
}


