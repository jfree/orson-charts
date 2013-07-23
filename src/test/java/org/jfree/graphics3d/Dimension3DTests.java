/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package org.jfree.graphics3d;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

/**
 * Tests for the {@link Dimension3D} class.
 */
public class Dimension3DTests {
    
  @Test
  public void checkEquals() {
    Dimension3D dim1 = new Dimension3D(1, 2, 3);
    Dimension3D dim2 = new Dimension3D(1, 2, 3);
    assertEquals(dim1, dim2);
    
    dim1 = new Dimension3D(2, 2, 3);
    assertNotEquals(dim1, dim2);
    dim2 = new Dimension3D(2, 2, 3);
    assertEquals(dim1, dim2);
    
    dim1 = new Dimension3D(2, 1, 3);
    assertNotEquals(dim1, dim2);
    dim2 = new Dimension3D(2, 1, 3);
    assertEquals(dim1, dim2);
  
    dim1 = new Dimension3D(2, 1, 4);
    assertNotEquals(dim1, dim2);
    dim2 = new Dimension3D(2, 1, 4);
    assertEquals(dim1, dim2);
  }
}
