// Utility code for Orson Charts
var OrsonCharts = (function() {
 
        // returns the chart entity reference for this element        
	var findChartRef = function(element) {
	    var id = element.getAttribute("jfreesvg:ref");
            var found = false;
            var current = element;
            while (!found) {
	        current = current.parentElement;
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
        var findChartId = function(element) {
            var id = null;
            var found = false;
            var current = element;
            while (!found) {
	        current = current.parentElement;
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

	var handleMouseClick = function(evt) {
	    var element = evt.target;
	    var id = element.getAttribute("jfreesvg:ref");
	    if (id == null) {
		id = element.parentElement.getAttribute("jfreesvg:ref");
		if (id == null) {
		    id = element.parentElement.parentElement.getAttribute("jfreesvg:ref");
		}

	    }
	    alert('You clicked on the item ' + id);
	};

	var handleMouseOver = function(evt) {
            var element = evt.target;
	    var id = element.getAttribute("jfreesvg:ref");
	    if (id == null) {
		id = element.parentElement.getAttribute("jfreesvg:ref");
		if (id == null) {
		    id = element.parentElement.parentElement.getAttribute("jfreesvg:ref");
		}

	    }
	    if (id != null) {
		var myOpentip = new Opentip(element, id);
		myOpentip.prepareToShow();
	    }
        };

	return {
            findChartId: findChartId,
	    findChartRef: findChartRef,
	    handleMouseOver: handleMouseOver,
            handleMouseClick: handleMouseClick
	};
    })();