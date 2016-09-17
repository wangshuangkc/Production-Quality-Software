package connect4.player;

import connect4.grid.ChipColor;

/**
 * Factory class for vending standard Connect4Player objects. 
 * Wherever possible, this factory will hand out references to Connect4Player instances. 
 * @author kc
 */
public class Connect4PlayerFactory {
  
  /**
   * Creates a Connect4Player with a specific player type
   * @param type player type, either HUMAN or AI
   * @param color the chip color representative of the player
   * @return
   */
  public Connect4Player getPlayer(PlayerType type, ChipColor color) {
    if (type == PlayerType.AI) {
      return new Connect4AIPlayer();
    } else if (type == PlayerType.HUMAN) {
      return new Connect4HumanPlayer.PlayerBuilder(color).build();
    } else {
      return null;
    }
  }
}