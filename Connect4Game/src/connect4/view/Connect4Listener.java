package connect4.view;

import connect4.player.Connect4Player;

/**
 * This interface declares methods that an observer performs
 * @author kc
 */
public interface Connect4Listener {
  
  /**
   * Listen to the game-start event
   */
  void gameStarted();
  
  /**
   * Listen to the move-made event and update the drop made in the game
   * @param x the row the disc is dropped in
   * @param y the column the disc is dropped in
   */
  void updateMove(int x, int y);
  
  /**
   * Listen to the drop-fail event and react on the view
   * @param col an int representing the target column
   */
  void moveFailed();
  
  /**
   * Listen to the game-won event, and announce the winner
   * @param winner
   */
  void gameWon(Connect4Player winner);
  
  /**
   * Listen to the game-tie event, and react in the view
   */
  void tie();

  /**
   * Close the view
   */
  void close();
  
  /**
   * Get the player controlled by the view
   * @return the player of the view
   */
  Connect4Player getPlayer();
}