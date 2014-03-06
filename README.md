Orson Charts
============

(C)opyright 2013, 2014, by Object Refinery Limited


Overview
--------
Orson Charts is a 3D chart library for the Java(tm) platform that can generate a wide variety of 3D charts for use in applications, applets and servlets. Key features include:

- multiple chart types: pie charts, bar charts (regular and stacked), line charts, area charts, scatter charts and surface plots;
- a mouse-enabled chart viewer provides 360 degree rotation and zooming for precise end-user view control;
- flexible data sources;
- regular and logarithmic axis scales;
- auto-adaptive axis labeling;
- value and range marker annotations
- support for PDF, SVG and PNG export of charts for reporting;
- a clean and well-documented API with a high degree of chart configurability.

Orson Charts is very easy to use, and includes comprehensive Javadocs.  Licenses can be purchased from Object Refinery Limited.  The license permits royalty-free redistribution of the Orson Charts runtime jar file.

Orson Charts is also available for Android and for HTML5 (Javascript).


Evaluation Version
------------------
The evaluation version of Orson Charts is a fully functional copy of the library that can be used for evaluation purposes only.  There is no time limit on the evaluation.  It is not permitted to use the evaluation version in production systems, nor is it permitted to modify or redistribute the evaluation version in any way.


Documentation
-------------
The Javadoc generated documentation is the principal source of documentation for Orson Charts:

    http://www.object-refinery.com/orsoncharts/javadoc/index.html

If there are topics that are not covered or not clear in the documentation, please let us know so that we can improve the coverage.


More Info
---------
You can find out more about Orson Charts at:

    http://www.object-refinery.com/orsoncharts/

If you have any questions, don't hesitate to send a mail to info@object-refinery.com.


History
-------

??-Mar-2014 : Version 1.2
- added value and range markers for numerical axes, and category markers for category axes;
- added a tickLabelOrientation attribute for axes so that tick labels can be drawn either perpendicular or parallel to the axis line;
- added a logarithmic axis;
- added theme support, with several built-in themes;
- added label generators for pie section labels and category axis labels;
- added export to JPEG, plus options to configure the available export types;
- made projection distance configurable in the chart viewer;
- optimized rendering code to reduce memory usage;
- added series accessors for XYZSeriesCollection;
- added yDimensionOverride attribute for CategoryPlot3D;
- put in place localisation mechanism, and added German and Italian localisations;
- fixed issue with StackedBarRenderer and negative values;
- various other bug fixes.


23-Dec-2013 : Version 1.1

- added surface plots (via the new SurfaceRenderer class and Chart3DFactory.createSurfaceChart());
- added ColorScale and supporting classes for use by the surface charts;
- added orientation attribute to control the chart legend orientation;
- optimized rendering code for improved performance;
- added missing change events for gridline attributes in CategoryPlot3D;
- added constants including SCALE_TO_FIT_TARGET and CENTER_NO_SCALING to the Fit2D class;
- added setColors(Color...) to AbstractCategoryRenderer3D and AbstractXYZRenderer3D;


17-Nov-2013 : Version 1.0

This is the first public release of Orson Charts, we welcome your feedback and suggestions.
