package connect4.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import connect4.grid.ChipColor;
import connect4.model.Connect4GameModel;
import connect4.player.Connect4Player;

/**
 * This is an implement of view for the Connect4 game.
 * It implements the Connect4Listener interface.
 * The view contains a 6*7 game grid, buttons to drop chip in a column, and a message window
 * @author kc
 *
 */
public class Connect4View implements Connect4Listener {
  private static final int ROW = 6;
  private static final int COL = 7;
  private static final String DROP_MESSAGE = 
      "Your turn! Select a column, and press the DROP button.";
  private static final String WAIT_MESSAGE = "The opponent's turn!";
  private static final String WIN_MESSAGE = "You win!";
  private static final String LOSE_MESSAGE = "You lose.";
  private static final String INVALID_MESSAGE = "The column is full. Try another.";
  private static final String TIE_MESSAGE = "It's a tie.";
                  
  private final Connect4GameModel model;
  private final Connect4Player player;
  
  private JFrame frame;
  private JLabel message;
  private JLabel[][] spots;
  private JButton[] dropButtons;
  private JButton newGameButton;
  
  public Connect4View(Connect4GameModel m, Connect4Player p) {
    player = p;
    model = m;
    model.addListener(this);
    creatView();
  }
  
  private void creatView() {
    message = new JLabel();
    message.setHorizontalAlignment(JLabel.CENTER);
    message.setVerticalAlignment(JLabel.CENTER);
    message.setBorder(new LineBorder(Color.BLACK));
    
    initializeSpots();
    initializeDropButtons();
    initializeNewGameButtons();
    
    JPanel mainPanel = new JPanel(new BorderLayout());
    JPanel messagePanel = new JPanel(new BorderLayout());
    JPanel gamePanel = new JPanel(new BorderLayout());
    JPanel buttonPanel = new JPanel(new BorderLayout());
    JPanel chipPanel = new JPanel(new BorderLayout());
    
    messagePanel.add(message, BorderLayout.CENTER);
    messagePanel.add(newGameButton, BorderLayout.EAST);
    
    buttonPanel.setLayout(new GridLayout(1,7));
    for (JButton jb: dropButtons) {
      buttonPanel.add(jb, BorderLayout.WEST);
    }
    
    chipPanel.setLayout(new GridLayout(6,7));
    for (JLabel[] jl: spots) {
      for (JLabel l: jl) {
        chipPanel.add(l, BorderLayout.CENTER);
      }
    }
    
    gamePanel.add(buttonPanel, BorderLayout.NORTH);
    gamePanel.add(chipPanel, BorderLayout.CENTER);
    
    mainPanel.add(messagePanel, BorderLayout.NORTH);
    mainPanel.add(gamePanel, BorderLayout.CENTER);
    
    frame = new JFrame ("Connect Four");
    frame.setSize(700, 800);
    frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  private void initializeSpots() {
    spots = new JLabel[ROW][COL];
    for (int i = 0; i < ROW; i++) {
      for (int j = 0; j < COL; j++) {
        spots[i][j] = new JLabel();
        spots[i][j].setBorder(new LineBorder(Color.BLACK));
        spots[i][j].setBackground(Color.WHITE);
        spots[i][j].setOpaque(true);
      }
    }
  }
  
  private void initializeDropButtons() {
    dropButtons = new JButton[COL];
    for (int i = 0; i < COL; i++) {
      dropButtons[i] = new JButton("DROP");
      dropButtons[i].setActionCommand("" + i);
      dropButtons[i].addActionListener (new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          int col = Integer.parseInt(e.getActionCommand());
          model.dropChip(player, col);
        }
      });
    }
  }
  
  private void initializeNewGameButtons() {
    newGameButton = new JButton("New Game");
    newGameButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        model.startNewGame();
      }
    });
    newGameButton.setEnabled(false);
  }
  
  @Override
  public void gameStarted() {
    if (player == model.getCurrentPlayer()) {
      message.setText(DROP_MESSAGE);
    } else {
      message.setText(WAIT_MESSAGE);
      disableButtons();
    }
  }

  @Override
  public void updateMove(int x, int y) {
    Connect4Player current = model.getCurrentPlayer();
    Color c = current.getPlayerColor() == ChipColor.RED ? Color.RED : Color.GREEN;
    spots[x][y].setBackground(c);
    if (player != current) {
      enableButtons();
      message.setText(DROP_MESSAGE);
    } else {
      disableButtons();
      message.setText(WAIT_MESSAGE);
    }
  }
  
  @Override
  public void moveFailed() {
    if (player == model.getCurrentPlayer()) {
      message.setText(INVALID_MESSAGE);
    }
  }
  
  private void disableButtons() {
    for (JButton jb : dropButtons) {
      jb.setEnabled(false);
    }
  }
  
  private void enableButtons() {
    for (JButton jb : dropButtons) {
      jb.setEnabled(true);
    }
  }
 
  @Override
  public void gameWon(Connect4Player winner) {
    if (winner == player) {
      message.setText(WIN_MESSAGE);
    } else {
      message.setText(LOSE_MESSAGE);
    }
    disableButtons();
    newGameButton.setEnabled(true);
  }

  @Override
  public void tie() {
    message.setText(TIE_MESSAGE);
    disableButtons();
    newGameButton.setEnabled(true);
  }

  @Override
  public void close() {
    frame.setVisible(false);
    frame.dispose();
  }
  
  @Override
  public Connect4Player getPlayer() {
    return player;
  }
  
  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof Connect4View)) {
      return false;
    }
    Connect4View view = (Connect4View) o;
    return player == view.getPlayer();
  }
  
  @Override
  public int hashCode() {
    return player.hashCode();
  }
}
