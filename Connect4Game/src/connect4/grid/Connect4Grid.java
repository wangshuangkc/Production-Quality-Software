package connect4.grid;

/**
 * This is an implementation of the Connect4 game grid.
 * The grid contains ChipColor element representing individual players, and 
 * a dynamic record of next available spot in each column.
 * This is designed for the Connect4Model, and 
 * not to be extended in case the model does not support.
 * @author kc
 *
 */
public final class Connect4Grid {
  private static final int ROW = 6;
  private static final int COL = 7;
  private static final int WIN = 4;
  
  private ChipColor[][] grid;
  private int emptySpotNum;
  private int[] nextSpots;
  
  public Connect4Grid() {
    grid = new ChipColor[ROW][COL];
    nextSpots = new int[COL];
    for (int i = 0; i < COL; i++) {
      nextSpots[i] = ROW - 1;
    }
    emptySpotNum = ROW * COL;
  }
  
  /**
   * Get the row number of next available spot in the target column
   * @param col target column the chip is expected to drop
   * @return an int representing the row number of next available spot
   *         the int ranges from 0 to 6, or is -1
   *         returning -1 means the column is full, and cannot drop the chip
   */
  public int getNextSpot(int col) {
    return nextSpots[col];
  }
  
  /**
   * Drop a chip into a target column
   * @param col the target column number
   * @param color the color of the dropped chip
   * @return a Drop object holding the information of drop coordinates and game state as a result
   * @throws RuntimeException if col is negative or greater than the column limit
   */
  public Drop dropChip(int col, ChipColor color) {
    if (col < 0 || col >= COL) {
      throw new IllegalArgumentException("Invalid column number");
    }
    
    DropResult result;
    
    int row = getNextSpot(col);
    if (row == -1) {
      result = DropResult.ILLEGAL;
    } else {
      grid[row][col] = color;
      nextSpots[col]--;
      emptySpotNum--;
      
      if (isWinnerDrop(col, row, color)) {
        result = DropResult.WIN;
      } else if (isTie()) {
        result = DropResult.TIE;
      } else {
        result = DropResult.LEGAL;
      }
    }
     return new Drop.DropBuilder(col, row).dropResult(result).build();
  }
  
  /**
   * Check if a drop is a winner drop in the target column
   * @param col the target column number
   * @param row the target row number
   * @param color the dropped chip color
   * @return true if the drop forms a four-chip chain, false otherwise
   * @throws IllegalArgumentException if the either of the drop coordinates is negative or 
   *         greater than the limit
   */
  public boolean isWinnerDrop(int col, int row, ChipColor color) {
    if (col < 0 || col >= COL || row < 0 || row >= ROW) {
      return false;
    }
    if (isHorizontalWinning(row, col, color) || isVerticalWinning(row, col, color) ||
        isLeftdownWinning(row, col, color) || isRightdownWinning(row, col, color)) {
      return true;
    }
    return false;
  }

  /**
   * Check if all the spots are taken, and the game ends in a tie
   * @return true if no empty spots left, false otherwise
   */
  public boolean isTie() {
    return emptySpotNum == 0;
  }
  
  private boolean isLeftdownWinning(int row, int col, ChipColor color) {
    int cnt = 1;
    int nextRow = row + 1;
    int nextCol = col - 1;
    while (nextRow < ROW && nextCol >= 0 && grid[nextRow][nextCol] == color) {
      cnt++;
      nextRow++;
      nextCol--;
    }
    
    nextRow = row - 1;
    nextCol = col + 1;
    while (nextRow >= 0 && nextCol < COL && grid[nextRow][nextCol] == color) {
      cnt++;
      nextRow--;
      nextCol--;
    }
    return cnt >= WIN;
  }

  private boolean isRightdownWinning(int row, int col, ChipColor color) {
    int cnt = 1;
    int nextRow = row + 1;
    int nextCol = col + 1;
    while (nextRow < ROW && nextCol < COL && grid[nextRow][nextCol] == color) {
      cnt++;
      nextRow++;
      nextCol++;
    }
    
    nextRow = row - 1;
    nextCol = col - 1;
    while (nextRow >= 0 && nextCol >= 0 && grid[nextRow][nextCol] == color) {
      cnt++;
      nextRow--;
      nextCol--;
    }
    return cnt >= WIN;
  }

  private boolean isVerticalWinning(int row, int col, ChipColor color) {
    if (row > 2) {
      return false;
    }
    
    int cnt = 1;
    int nextRow = row + 1;
    while (nextRow < ROW && grid[nextRow][col] == color) {
      cnt++;
      nextRow++;
    }
    return cnt >= WIN;
  }

  private boolean isHorizontalWinning(int row, int col, ChipColor color) {
    int cnt = 1;
    int nextCol = col + 1;
    while (nextCol < COL && grid[row][nextCol] == color) {
      cnt++;
      nextCol++;
    }
    if (cnt >= WIN) {
      return true;
    }
    
    nextCol = col - 1;
    while (nextCol >= 0 && grid[row][nextCol] == color) {
      cnt++;
      nextCol--;
    }
    return cnt >= WIN;
  }
  
  /**
   * Get the number of rows of the grid
   * @return the constant row number
   */
  public int getRowNum() {
    return ROW;
  }
  
  /**
   * Get the number of columns of the grid
   * @return the constant column number
   */
  public int getColNum() {
    return COL;
  }
  
}
