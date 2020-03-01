/**
 * Orson Charts is a 3D chart library for the Java platform.
 */
module org.jfree.chart3d {
    requires java.base;
    requires java.desktop;
    requires java.logging;
    exports com.orsoncharts;
    exports com.orsoncharts.axis;
    exports com.orsoncharts.data;
    exports com.orsoncharts.data.category; // checked
    exports com.orsoncharts.data.function;
    exports com.orsoncharts.data.xyz;
    exports com.orsoncharts.graphics3d;
    exports com.orsoncharts.graphics3d.swing;
    exports com.orsoncharts.interaction;
    exports com.orsoncharts.label;
    exports com.orsoncharts.legend;
    exports com.orsoncharts.marker;
    exports com.orsoncharts.plot;
    exports com.orsoncharts.renderer;
    exports com.orsoncharts.renderer.category;
    exports com.orsoncharts.renderer.xyz;
    exports com.orsoncharts.style;
    exports com.orsoncharts.table;
    exports com.orsoncharts.util;
}
