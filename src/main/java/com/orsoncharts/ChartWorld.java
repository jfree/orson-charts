/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */
package com.orsoncharts;

import com.orsoncharts.graphics3d.Face;
import com.orsoncharts.graphics3d.World;
import java.util.List;

/**
 * An extension of {@link World} that adds a {@link ChartBox3D}.
 * 
 * @since 1.1
 */
public class ChartWorld extends World {
    
    /** The chart box. */
    private ChartBox3D chartBox;
    
    public ChartWorld() {
        super();
    }
    
    public ChartBox3D getChartBox() {
        return this.chartBox;
    }
    
    public void setChartBox(ChartBox3D chartBox) {
        this.chartBox = chartBox;
    }

    @Override
    public int getVertexCount() {
        int count = super.getVertexCount();
        if (this.chartBox != null) {
            count = count + this.chartBox.getObject3D().getVertexCount();
        }
        return count;
    }

    @Override
    public List<Face> getFaces() {
        int offset = 0;
        List<Face> faces = super.getFaces();
        if (!faces.isEmpty()) {
            Face f = faces.get(faces.size() - 1);
            offset = f.getOffset() + f.getVertexCount();
        }
        for (Face f : this.chartBox.getObject3D().getFaces()) {
            f.setOffset(offset);
        }
        faces.addAll(this.chartBox.getObject3D().getFaces());
        return faces;
    }
    
}
