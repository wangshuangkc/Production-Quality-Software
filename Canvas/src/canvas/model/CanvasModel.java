package canvas.model;

import java.util.ArrayList;
import java.util.List;

import canvas.view.CanvasListener;

/**
 * Represents a singleton canvas model.
 * When the mouse is clicked, the model gets the current line starting point, and gets the 
 * continuing points of the line when the mouse is dragged, and stops the line when the mouse is
 * released. The model also allows changing paint color.
 * The model sends message, including line drawn, color changed canvas cleared, to listeners for 
 * updating the view.
 * @author kc
 *
 */
public class CanvasModel {
  private static CanvasModel model;
  private List<CanvasListener> listeners;
  
  private CanvasModel() {
    listeners = new ArrayList<>();
  }
  
  /**
   * Gets the singleton instance of the canvas model
   * @return the model singleton instance
   */
  public static CanvasModel getCanvasModel() {
    if (model == null) {
      model = new CanvasModel();
    }
    return model;
  }
  
  /**
   * Adds new canvas listener
   * @param listener new canvas listener
   */
  public void addListener(CanvasListener listener) {
    listeners.add(listener);
  }
  
  /**
   * Removes canvas listener
   * @param listener canvas listener intended to be deleted
   */
  public void removeListener(CanvasListener listener) {
    listeners.remove(listener);
  }
  
  /**
   * Starts drawing the current line at the coordinates where the mouse is clicked
   * The coordinates is from pressing the mouse on the view panel, so they cannot be negative.
   * @param x the x coordinate
   * @param y the y coordinate
   */
  public void startDraw(int x, int y) {
    fireDrawStartedEvent(x, y);
  }
  
  private void fireDrawStartedEvent(int x, int y) {
    for (CanvasListener listener: listeners) {
      listener.drawingStarted(x, y);
    }
  }
  
  /**
   * Continues drawing the current line at the coordinates where the mouse is held and passed.
   * The coordinates is from pressing the mouse on the view panel, so they cannot be negative.
   * @param x the x coordinate
   * @param y the y coordinate
   */
  public void continueDraw(int x, int y) {
    fireDrawContinuedEvent(x, y);
  }

  private void fireDrawContinuedEvent(int x, int y) {
    for (CanvasListener listener: listeners) {
      listener.drawingContinued(x, y);
    }
  }
  
  /**
   * Changes the color of the paint pen
   * @param color the paint color
   */
  public void changeColor(PaintColor color) {
    fireColorChangedEvent(color);
  }
  
  private void fireColorChangedEvent(PaintColor color) {
    for (CanvasListener listener: listeners) {
      listener.colorChanged(color);
    }
  }
  
  /**
   * Resets the canvas
   */
  public void clearCanvas() {
    fireCanvasClearedEvent();
  }

  private void fireCanvasClearedEvent() {
    for (CanvasListener listener: listeners) {
      listener.canvasCleared();
    }
  }
  
  /**
   * Gets the number of listeners connected to the model
   * This method is just for testing
   * @return number of listeners
   */
  public int getListenerNum() {
    return listeners.size();
  }
}
