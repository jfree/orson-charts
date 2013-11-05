/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.renderer.xyz;

import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.World;

/**
 * Renders a function.  This is a specialised renderer that has special 
 * requirements of the dataset which you must ensure are met.
 */
public class SurfaceXYZRenderer extends AbstractXYZRenderer 
        implements XYZRenderer {
    
    public SurfaceXYZRenderer() {
        
    }

    @Override
    public void composeItem(XYZDataset dataset, int series, int item, 
            World world, Dimension3D dimensions, 
            double xOffset, double yOffset, double zOffset) {

        int seriesCount = dataset.getSeriesCount();
        if (series >= seriesCount - 1) {
            return;
        }
        int itemCount = dataset.getItemCount(series);
        if (item >= itemCount - 2) {
            return;
        }
//        double x0 = dataset.getX(series, item);
//        double x1 = dataset.getX(series, item + 1);
//        double x2 = dataset.getX(series + 1, item);
//        double x3 = dataset.getX(series + 1, item + 1);
//        double z0 = dataset.getZ(series, item);
//        double z1 = dataset.getZ(series, item + 1);
//        double z2 = dataset.getZ(series + 1, item);
//        double z3 = dataset.getZ(series + 1, item + 1);
//        double y0 = dataset.getY(series, item);
//        double y1 = dataset.getY(series, item + 1);
//        double y2 = dataset.getY(series + 1, item);
//        double y3 = dataset.getY(series + 1, item + 1);
//    
//        XYZPlot plot = getPlot();
//        Axis3D xAxis = plot.getXAxis();
//        Axis3D yAxis = plot.getYAxis();
//        Axis3D zAxis = plot.getZAxis();
//        if (!xAxis.getRange().contains(x) || !yAxis.getRange().contains(y) 
//              || !zAxis.getRange().contains(z)) {
//            return;
//        }
//    
//        Dimension3D dim = plot.getDimensions();
//        double xx = xAxis.translateToWorld(x, dim.getWidth());
//        double yy = yAxis.translateToWorld(y, dim.getHeight());
//        double zz = zAxis.translateToWorld(z, dim.getDepth());
//    
//        Paint paint = getPaintSource().getPaint(series, item);
//        Object3D cube = Object3D.createCube(this.size, xx + xOffset, 
//                yy + yOffset, zz + zOffset, (Color) paint);
//        world.add(cube);
    }
    
}
