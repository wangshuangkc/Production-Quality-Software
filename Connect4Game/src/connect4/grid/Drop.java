package connect4.grid;

import java.util.Objects;

/**
 * Represents a drop in the game grid, with coordinates as required fields, 
 * and optional game state as a result of the drop
 * @author kc
 */
public class Drop {
  private DropResult dropResult;
  private final int dropCol;
  private final int dropRow;
  
  /**
   * This implements the builder pattern of creating a drop instance.
   * Drop coordinates are required to initialize the instance, and drop result is optional
   * @author kc
   *
   */
  public static class DropBuilder {
    private DropResult dropResult;
    private final int dropCol;
    private final int dropRow;
    
    public DropBuilder(int col, int row) {
      dropCol = col;
      dropRow = row;
    }
    
    public DropBuilder dropResult(DropResult result) {
      dropResult = result;
      return this;
    }
    
    public Drop build() {
      return new Drop(this);
    }
  }
  
  private Drop(DropBuilder builder) {
    dropResult = builder.dropResult;
    dropCol = builder.dropCol;
    dropRow = builder.dropRow;
  }
  
  /**
   * Get the game state as a result of the drop
   * @return the drop result, ILLEGAL, LEGAL, WIN OR TIE
   */
  public DropResult getResult() {
    return dropResult;
  }
  
  /**
   * Get the horizontal coordinate of the drop
   * @return row number of the drop
   */
  public int getDropRow() {
    return dropRow;
  }
  
  /**
   * Get the vertical coordinate of the drop
   * @return column number of the drop
   */
  public int getDropCol() {
    return dropCol;
  }
  
  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof Drop)) {
      return false;
    }
    Drop drop = (Drop) o;
    return dropRow == drop.getDropRow() && dropCol == drop.getDropCol() 
        && (dropResult == null && drop.dropResult == null || dropResult == drop.getResult());
  }
  
  @Override
  public int hashCode() {
    if (dropResult == null) {
      return Objects.hash(Integer.valueOf(dropRow).hashCode(), 
          Integer.valueOf(dropCol).hashCode());
    }
    return Objects.hash(Integer.valueOf(dropRow).hashCode(), 
        Integer.valueOf(dropCol).hashCode(), 
        dropResult.hashCode());
  }
}
