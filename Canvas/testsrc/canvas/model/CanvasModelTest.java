package canvas.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import canvas.view.CanvasListener;

/**
 * Tests the CanvasModel
 * @author kc
 *
 */
public class CanvasModelTest {
  
  CanvasModel model = CanvasModel.getCanvasModel();
  TestCanvasListener listener1;
  TestCanvasListener listener2;

  @Before
  public void setUp() throws Exception {
    listener1 = new TestCanvasListener(model);
    listener2 = new TestCanvasListener(model);
  }

  @After
  public void tearDown() throws Exception {
    if (model.getListenerNum() > 0) {
      model.removeListener(listener1);
      model.removeListener(listener2);
    }
  }

  @Test
  public void testAddListener() {
    assertEquals(model.getListenerNum(), 2);
  }

  @Test
  public void testRemoveExistingListener() {
    model.removeListener(listener1);
    assertEquals(model.getListenerNum(), 1);
    model.removeListener(listener2);
    assertEquals(model.getListenerNum(), 0);
  }
  
  @Test
  public void testRemoveNonExistingListener() {
    model.removeListener(listener1);
    model.removeListener(listener1);
    assertEquals(model.getListenerNum(), 1);
  }
  
  @Test
  public void testStartDraw() {
    model.startDraw(0, 0);
    String expectedPoint = "(0, 0)";
    assertEquals(listener1.getCurrentPoint(), expectedPoint);
    assertEquals(listener2.getCurrentPoint(), expectedPoint);
  }
  
  @Test
  public void testContinueDraw() {
    model.continueDraw(1,2);
    String expectedPoint = "(1, 2)";
    assertEquals(listener1.getCurrentPoint(), expectedPoint);
    assertEquals(listener2.getCurrentPoint(), expectedPoint);
  }
  
  @Test
  public void testChangeColor() {
    PaintColor[] colors = {
        PaintColor.BLACK, 
        PaintColor.RED,
        PaintColor.GREEN,
        PaintColor.BLUE,
        PaintColor.YELLOW
    };
    
    for (PaintColor color: colors) {
      model.changeColor(color);
      assertEquals(listener1.getPaintColor(), color);
      assertEquals(listener2.getPaintColor(), color);
    }
  }
  
  @Test
  public void testChangeColorToNull() {
    model.changeColor(null);
    assertNull(listener1.getPaintColor());
    assertNull(listener2.getPaintColor());
  }
  
  @Test
  public void testClearCanvas() {
    assertFalse(listener1.isCleared());
    assertFalse(listener2.isCleared());
    model.clearCanvas();
    assertTrue(listener1.isCleared());
    assertTrue(listener2.isCleared());
  }
  
  /**
   * Represents a simplified canvas observer for testing only
   * @author kc
   *
   */
  private class TestCanvasListener implements CanvasListener {
    private PaintColor paintColor;
    private int currentX = -1;
    private int currentY = -1;
    private boolean cleared = false;
    
    public TestCanvasListener(CanvasModel model) {
      model.addListener(this);
      paintColor = PaintColor.BLACK;
    }
    
    @Override
    public void drawingStarted(int x, int y) {
      currentX = x;
      currentY = y;
    }

    @Override
    public void drawingContinued(int x, int y) {
      currentX = x;
      currentY = y;
    }

    @Override
    public void colorChanged(PaintColor color) {
        paintColor = color;
    }

    @Override
    public void canvasCleared() {
      cleared = true;
    }
    
    /**
     * Gets a string represents the current point
     * @return current point coordinates
     */
    String getCurrentPoint() {
      return "(" + currentX + ", " + currentY + ")";
    }
    
    /**
     * Gets the boolean representing whether the canvas has been cleared
     * @return true if the canvasCleared() has been called, false otherwise
     */
    boolean isCleared() {
      return cleared;
    }
    
    /**
     * Gets the current paint color
     * @return current paint color
     */
    PaintColor getPaintColor() {
      return paintColor;
    }
  }
}
