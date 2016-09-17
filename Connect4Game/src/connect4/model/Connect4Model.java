package connect4.model;

import connect4.player.Connect4Player;
import connect4.view.Connect4Listener;
import connect4.view.SelectModeListener;

/**
 * Declares the methods that a connect 4 game mode performs
 * @author kc
 */
public interface Connect4Model {
  
  /**
   * Set the game mode
   * @param mode the game mode, SINGLE or DOUBLE
   */
  public void setGameMode(GameMode mode);
  
  /**
   * Reset the fields of game mode
   */
  public void reset();
  
  /**
   * Add a listener that does not exist in the listener list to the list
   * @param listener an Connect4Listener instance
   */
  public void addListener(Connect4Listener listener);
  
  /**
   * Delete an existing listener from the listener list
   * @param listener a Connect4Listener
   */
  public void removeListener(Connect4Listener listener);
  
  /**
   * Show the select mode observer for users to select game mode
   * @param listener the listener connecting the select mode observer
   */
  public void setSelectModeListener(SelectModeListener listener);
  
  /**
   * Start the game by firing a game-start event
   */
  public void StartGame();
  
  /**
   * Drop a chip with given color in the target column of the grid
   * @param the player that intends to make a drop
   * @param col the target column
   */
  public void dropChip(Connect4Player player, int col);
  
  /**
   * Starts a new game by closing all player observers, reseting the model, and 
   * invoking the select mode observer.
   * Model cannot starts a new game unless the current game ends.
   */
  public void startNewGame();
  
  /**
   * Get the number of listeners added to the model
   * @return the number of added listener
   */
  public int getListenerNum();
  
  /**
   * Get the player that is expected to move
   * @return the player holding the turn
   */
  public Connect4Player getCurrentPlayer();
  
  /**
   * Get the player that holds the red chip
   * @return the red player which moves first
   */
  public Connect4Player getPlayerRed();
  
  /**
   * Get the player that holds the green chip
   * @return the green player which moves after red player
   */
  public Connect4Player getPlayerGreen();
  
  /**
   * Get the number of rows of the grid
   * @return the constant row number
   */
  public int getRowNum();
  
  /**
   * Get the number of columns of the game grid
   * @return the constant column number
   */
  public int getColNum();
}
