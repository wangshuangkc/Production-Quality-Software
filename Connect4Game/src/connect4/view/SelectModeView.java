package connect4.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import connect4.model.Connect4GameModel;
import connect4.model.GameMode;

/**
 * Implements a select mode observer for the Connect4 game
 * The view contains two buttons allowing the user to select the game mode, single or double.
 * SINGLE mode invokes one view for player to play with the computer.
 * DOUBLE mode invokes two view for players to play on a separate view
 * @author kc
 */
public class SelectModeView implements SelectModeListener {
  private final Connect4GameModel model;
  private JFrame frame = new JFrame("Connect Four: Mode Selection");
  private JLabel instruction = new JLabel("Who would you like to play against?");
  private JButton singleModeButton = new JButton("AI");
  private JButton doubleModeButton = new JButton("Friend");
  
  public SelectModeView(Connect4GameModel model) {
    this.model = model;
    model.setSelectModeListener(this);
    createView();
    bindButtons();
  }

  private void createView() {
    instruction.setHorizontalAlignment(JLabel.CENTER);
    instruction.setVerticalAlignment(JLabel.CENTER);
    
    JPanel viewPanel = new JPanel(new BorderLayout());
    JPanel buttonPanel = new JPanel(new BorderLayout());
    
    buttonPanel.setLayout(new GridLayout(1,2,4,4));
    buttonPanel.add(singleModeButton, BorderLayout.NORTH);
    buttonPanel.add(doubleModeButton, BorderLayout.NORTH);
    
    viewPanel.setLayout(new GridLayout(2,1,4,4));
    viewPanel.add(instruction, BorderLayout.NORTH);
    viewPanel.add(buttonPanel, BorderLayout.NORTH);
    
    frame.setSize(500, 100);
    frame.getContentPane().add(viewPanel, BorderLayout.NORTH);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  
  private void bindButtons() {
    singleModeButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        buttonClicked(GameMode.SINGLE);
      }
    });
    
    doubleModeButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        buttonClicked(GameMode.DOUBLE);
      }
    });
  }
  
  private void buttonClicked(GameMode gameMode) {
    model.setGameMode(gameMode);
    if (gameMode == GameMode.DOUBLE) {
      new Connect4View(model, model.getPlayerRed());
      new Connect4View(model, model.getPlayerGreen());
    } else {
      new Connect4View(model, model.getPlayerRed());
    }
    model.StartGame();
    frame.setVisible(false);
  }
  
  @Override
  public void startSelect() {
    if (!frame.isVisible())
      frame.setVisible(true);
  }

  @Override
  public void close() {
    frame.dispose();
  }
}
