package canvas.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import canvas.model.CanvasModel;

/**
 * Represents a panel on canvas view displaying paintings
 * @author kc
 *
 */
public class CanvasPaintPanel extends JPanel {
  private static final long serialVersionUID = 1L;
  private CanvasModel model;
  private BufferedImage image;
  private Graphics2D graphics;
  
  private int startX = 0;
  private int startY = 0;
  private int currentX = 0;
  private int currentY = 0;
  
  public CanvasPaintPanel(CanvasModel canvasModel) {
    super(new BorderLayout());
    model = canvasModel;
    setBorder(BorderFactory.createLineBorder(Color.BLACK));

    addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        model.startDraw(e.getX(), e.getY());
      }
    });
    
    addMouseMotionListener(new MouseMotionAdapter() {
      @Override
      public void mouseDragged(MouseEvent e) {
        model.continueDraw(e.getX(), e.getY());
      }
    });
  }
  
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    
    if (image == null) {
      image = new BufferedImage(getSize().width, getSize().height, BufferedImage.TYPE_INT_RGB);
      graphics = (Graphics2D) image.getGraphics();
      graphics.setBackground(Color.WHITE);
      graphics.setColor(Color.BLACK);
      clear();
    }
    g.drawImage(image, 0, 0, null);
  } 
  
  /**
   * Resets the canvas
   */
  public void clear() {
    graphics.clearRect(0, 0, getSize().width, getSize().height);
    graphics.setPaint(Color.BLACK);
    repaint();
  }
  
  /**
   * Sets the start point of a new line when the mouse is pressed
   * @param x the x coordinate
   * @param y the y coordinate
   */
  public void setMousePressedPoint(int x, int y) {
    startX = x;
    startY = y;
  }
  
  /**
   * Set the intermediate point of the current line when the mouse is dragged 
   * @param x the x coordinate
   * @param y the y coordinate
   */
  public void setMouseDraggedPoint(int x, int y) {
    currentX = x;
    currentY = y;
    
    drawLine();
  }
  
  private void drawLine() {
    if (graphics != null) {
      graphics.drawLine(startX, startY, currentX, currentY);
      repaint();
      startX = currentX;
      startY = currentY;
    }
  }
  
  /**
   * Set the color of the paint pen
   * @param color the color intended to be used
   */
  public void setColor (Color color) {
    graphics.setPaint(color);
  }
}

