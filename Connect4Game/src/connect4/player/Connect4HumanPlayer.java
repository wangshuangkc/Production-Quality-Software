package connect4.player;

import connect4.grid.ChipColor;
import connect4.grid.Connect4Grid;
import connect4.grid.Drop;

/**
 * Represents a player with type of HUMAN played in Connect4 game
 * @author kc
 */
public class Connect4HumanPlayer implements Connect4Player{
  private final ChipColor playerColor;

  /**
   * Implements a builder creating a HumanPlayer
   * The required fields include game model and player color.
   * Allows further adding optional features, such player name, and Id, etc.
   */
  public static class PlayerBuilder {
    private final ChipColor playerColor;
    
    public PlayerBuilder(ChipColor color) {
      playerColor = color;
    }
    
    /**
     * Create a HumanPlayer
     * @return a HumanPlayer
     */
    public Connect4HumanPlayer build() {
      return new Connect4HumanPlayer(this);
    }
  }
  
  private Connect4HumanPlayer(PlayerBuilder builder) {
    playerColor = builder.playerColor;
  }
  
  @Override
  public ChipColor getPlayerColor() {
    return playerColor;
  }

  @Override
  public PlayerType getType() {
    return PlayerType.HUMAN;
  }

  @Override
  public Drop makeDrop(Connect4Grid grid, int col) {
    return grid.dropChip(col, playerColor);
  }
  
  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof Connect4HumanPlayer)) {
      return false;
    }
    Connect4HumanPlayer player = (Connect4HumanPlayer) o;
    return playerColor == player.getPlayerColor();
  }
  
  @Override
  public int hashCode() {
    return playerColor.hashCode();
  }
}
