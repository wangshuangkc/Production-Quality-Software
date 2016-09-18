package canvas.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import canvas.model.CanvasModel;
import canvas.model.PaintColor;

/**
 * Represents a canvas view allowing drawing lines in different colors
 * @author kc
 *
 */
public class CanvasView implements CanvasListener {
  private final CanvasModel model;
  private Color paintColor;
  
  private JFrame frame;
  private CanvasPaintPanel paintPanel;
  private JPanel buttonPanel;
  private JPanel colorPanel;
  private JLabel colorLabel;
  private JButton blackButton;
  private JButton redButton;
  private JButton greenButton;
  private JButton blueButton;
  private JButton yellowButton;
  private JButton clearButton;

  public CanvasView(CanvasModel canvasModel) {
    model = canvasModel;
    model.addListener(this);
    paintColor = Color.BLACK;
    createView();
  }
  
  private void createView() {
    colorLabel = new JLabel("Change Color");
    
    initializeColorButtons();
    initializeClearButtons();
    initializePaintPanel();
    
    colorPanel = new JPanel(new BorderLayout());
    colorPanel.setLayout(new GridLayout(6, 1));
    colorPanel.add(colorLabel, BorderLayout.NORTH);
    colorPanel.add(blackButton, BorderLayout.NORTH);
    colorPanel.add(redButton, BorderLayout.NORTH);
    colorPanel.add(greenButton, BorderLayout.NORTH);
    colorPanel.add(blueButton, BorderLayout.NORTH);
    colorPanel.add(yellowButton, BorderLayout.NORTH);
    
    buttonPanel = new JPanel(new BorderLayout());
    buttonPanel.add(colorPanel, BorderLayout.NORTH);
    buttonPanel.add(clearButton, BorderLayout.SOUTH);
    
    frame = new JFrame("Canvas");
    frame.getContentPane().add(paintPanel, BorderLayout.CENTER);
    frame.getContentPane().add(buttonPanel, BorderLayout.EAST);
    
    frame.setSize(600, 500);
    frame.setResizable(false);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  private void initializePaintPanel() {
    paintPanel = new CanvasPaintPanel(model);
  }
  
  private void initializeClearButtons() {
    clearButton = new JButton("CLEAR");
    clearButton.addActionListener (new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        model.clearCanvas();
      }
    });
  }

  private void initializeColorButtons() {
    blackButton = new JButton("BLACK");
    blackButton.setForeground(Color.BLACK);
    blackButton.addActionListener (new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        model.changeColor(PaintColor.BLACK);
      }
    });
    
    redButton = new JButton("RED");
    redButton.setForeground(Color.RED);
    redButton.addActionListener (new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        model.changeColor(PaintColor.RED);
      }
    });
    
    greenButton = new JButton("GREEN");
    greenButton.setForeground(Color.GREEN);
    greenButton.addActionListener (new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        model.changeColor(PaintColor.GREEN);
      }
    });
    
    blueButton = new JButton("BLUE");
    blueButton.setForeground(Color.BLUE);
    blueButton.addActionListener (new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        model.changeColor(PaintColor.BLUE);
      }
    });
    
    yellowButton = new JButton("YELLOW");
    yellowButton.setForeground(Color.YELLOW);
    yellowButton.addActionListener (new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        model.changeColor(PaintColor.YELLOW);
      }
    });
  }

  @Override
  public void drawingStarted(int x, int y) {
    paintPanel.setMousePressedPoint(x, y);
  }

  @Override
  public void drawingContinued(int x, int y) {
    paintPanel.setMouseDraggedPoint(x, y);
  }
  
  @Override
  public void colorChanged(PaintColor color) {
    switch (color) {
      case BLACK: 
        paintColor = Color.BLACK;
        break;
      case RED: 
        paintColor = Color.RED;
        break;
      case GREEN: 
        paintColor = Color.GREEN;
        break;
      case BLUE: 
        paintColor = Color.BLUE;
        break;
      case YELLOW :
        paintColor = Color.YELLOW;
        break;
      default: 
        paintColor = Color.BLACK;
        break;
    }
    paintPanel.setColor(paintColor);
  }

  @Override
  public void canvasCleared() {
    paintPanel.clear();
  }
  
}
