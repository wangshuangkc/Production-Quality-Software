package connect4.player;

import connect4.grid.ChipColor;
import connect4.grid.Connect4Grid;
import connect4.grid.Drop;

/**
 * Declares the methods that a Connect4 player performs
 * @author kc
 */
public interface Connect4Player {
  
  /**
   * Get the color representing the player
   * @return the color of the player
   */
  public ChipColor getPlayerColor();
  
  /**
   * Get the player type
   * @return the type of the player, human or AI
   */
  public PlayerType getType();
  
  /**
   * Make a drop in the game
   * @param model the game model that the player plays on
   */
  public Drop makeDrop(Connect4Grid grid, int col);
}
