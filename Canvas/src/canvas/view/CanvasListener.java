package canvas.view;

import canvas.model.PaintColor;

/**
 * Declares the method implemented by canvas view
 * @author kc
 *
 */
public interface CanvasListener {
  
  /**
   * Listener to the drawing-started event, and updates the coordinates where the drawing starts
   * @param x the x coordinate
   * @param y the y coordinate
   */
  public void drawingStarted(int x, int y);
  
  /**
   * Listener to the drawing-continued event, and updates the coordinates where the drawing continues
   * @param x the x coordinate
   * @param y the y coordinate
   */
  public void drawingContinued(int x, int y);
  
  /**
   * Listen to the change-color event, and changes the paint color
   * @param color
   */
  public void colorChanged(PaintColor color);
  
  /**
   * Listen to the canvas-cleared event, and reset the canvas
   */
  public void canvasCleared();
}
