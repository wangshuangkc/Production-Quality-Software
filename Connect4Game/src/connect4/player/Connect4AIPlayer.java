package connect4.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import connect4.grid.ChipColor;
import connect4.grid.Connect4Grid;
import connect4.grid.Drop;

/**
 * Represents an AIPlayer for Connect4 game
 * AIPlayer usually takes the green chip
 * @author kc
 */
public class Connect4AIPlayer implements Connect4Player {
  private final ChipColor playerColor;
  
  public Connect4AIPlayer() {
    playerColor = ChipColor.GREEN;
  }
  
  @Override
  public ChipColor getPlayerColor() {
    return playerColor;
  }

  @Override
  public PlayerType getType() {
    return PlayerType.AI;
  }
  
  @Override
  public Drop makeDrop(Connect4Grid grid, int column) {
    Drop drop = takeNextWin(grid, playerColor);
    if (drop.getDropCol() == -1) {
      drop = takeNextWin(grid, ChipColor.RED);
    }
    if (drop.getDropCol() == -1) {
      drop = randomDrop(grid);
    }
    return grid.dropChip(drop.getDropCol(), playerColor);
  }
  
  private Drop takeNextWin(Connect4Grid grid, ChipColor color) {
    int col = -1;
    int row = -1;
    
    for (int i = 0; i < grid.getColNum(); i++) {
      row = grid.getNextSpot(i);
      if (grid.isWinnerDrop(i, row, color)) {
        col = i;
        break;
      }
    }
    return new Drop.DropBuilder(col, row).build();
  }

  private Drop randomDrop(Connect4Grid grid) {
    int colNum = grid.getColNum();
    Random r = new Random();
    int col = -1;
    int row = -1;
    
    List<Integer> cols = new ArrayList<>(colNum);
    for (int i = 0; i < colNum;i++) {
      cols.add(i);
    }
    while (cols.size() > 0) {
      int i = r.nextInt(cols.size());
      col = cols.get(i);
      cols.remove(i);
      row = grid.getNextSpot(col);
      if (row != -1) {
        break;
      }
    }
    return new Drop.DropBuilder(col, row).build();
  }
  
  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof Connect4AIPlayer)) {
      return false;
    }
    Connect4AIPlayer player = (Connect4AIPlayer)o;
    return playerColor == player.getPlayerColor();
  }
  
  @Override
  public int hashCode() {
    return playerColor.hashCode();
  }
}
