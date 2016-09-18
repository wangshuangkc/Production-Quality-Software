package canvas.app;

import canvas.model.CanvasModel;
import canvas.view.CanvasView;

public class CanvasApp {
  public void startPainting() {
    CanvasModel model = CanvasModel.getCanvasModel();
    new CanvasView(model);
    new CanvasView(model);
    new CanvasView(model);
  }
  
  public static void main(String[] args) {
    new CanvasApp().startPainting();
  }

}
