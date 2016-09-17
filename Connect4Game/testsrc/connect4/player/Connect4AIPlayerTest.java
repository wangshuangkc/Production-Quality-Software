package connect4.player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import connect4.grid.ChipColor;
import connect4.grid.Connect4Grid;
import connect4.grid.Drop;
import connect4.grid.DropResult;

public class Connect4AIPlayerTest {

  Connect4AIPlayer player = new Connect4AIPlayer();
  Connect4Grid grid = new Connect4Grid();
  
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testGetPlayerColor() {
    assertEquals(player.getPlayerColor(), ChipColor.GREEN);
  }

  @Test
  public void testGetPlayerType() {
    assertEquals(player.getType(), PlayerType.AI);
  }
  
  @Test
  public void testMakeDrop_winVertical() {
    for (int i = 0; i < 3; i++) {
      grid.dropChip(0, ChipColor.GREEN);
    }
    Drop drop = player.makeDrop(grid, -1);
    assertEquals(drop.getDropCol(), 0);
    assertEquals(drop.getDropRow(), 2);
  }

  @Test
  public void testMakeDrop_stopPlayerWinVertical() {
    for (int i = 0; i < 3; i++) {
      grid.dropChip(0, ChipColor.RED);
    }
    Drop drop = player.makeDrop(grid, -1);
    assertEquals(drop.getDropCol(), 0);
    assertEquals(drop.getDropRow(), 2);
  }
  
  @Test
  public void testMakeDrop_winHonrizontal() {
    for (int i = 2; i < 5; i++) {
      grid.dropChip(i, ChipColor.GREEN);
    }
    Drop drop = player.makeDrop(grid, -1);
    assertEquals(drop.getDropCol(), 1);
    assertEquals(drop.getDropRow(), 5);
  }
  
  @Test
  public void testMakeDrop_stopPlayerWinHonrizontal() {
    for (int i = 2; i < 5; i++) {
      grid.dropChip(i, ChipColor.RED);
    }
    Drop drop = player.makeDrop(grid, -1);
    assertEquals(drop.getDropCol(), 1);
    assertEquals(drop.getDropRow(), 5);
  }
  
  @Test
  public void testMakeDrop_winLeftDownDiagonal() {
    grid.dropChip(0, ChipColor.RED);
    grid.dropChip(1, ChipColor.GREEN);
    grid.dropChip(2, ChipColor.RED);
    grid.dropChip(3, ChipColor.GREEN);
    grid.dropChip(4, ChipColor.RED);
    grid.dropChip(4, ChipColor.GREEN);
    grid.dropChip(4, ChipColor.RED);
    grid.dropChip(4, ChipColor.GREEN);
    grid.dropChip(3, ChipColor.RED);
    grid.dropChip(2, ChipColor.GREEN);
    grid.dropChip(0, ChipColor.RED);
    
    Drop drop = player.makeDrop(grid, -1);
    assertEquals(drop.getDropCol(), 3);
    assertEquals(drop.getDropRow(), 3);
  }
  
  @Test
  public void testMakeDrop_stopPlayerWinLeftDownDiagonal() {
    grid.dropChip(0, ChipColor.GREEN);
    grid.dropChip(1, ChipColor.RED);
    grid.dropChip(2, ChipColor.GREEN);
    grid.dropChip(3, ChipColor.RED);
    grid.dropChip(4, ChipColor.GREEN);
    grid.dropChip(4, ChipColor.RED);
    grid.dropChip(4, ChipColor.GREEN);
    grid.dropChip(4, ChipColor.RED);
    grid.dropChip(3, ChipColor.GREEN);
    grid.dropChip(2, ChipColor.RED);
    grid.dropChip(0, ChipColor.GREEN);
    
    Drop drop = player.makeDrop(grid, -1);
    assertEquals(drop.getDropCol(), 3);
    assertEquals(drop.getDropRow(), 3);
  }
  
  @Test
  public void testMakeDrop_winRightDownDiagonal() {
    grid.dropChip(4, ChipColor.RED);
    grid.dropChip(3, ChipColor.GREEN);
    grid.dropChip(2, ChipColor.RED);
    grid.dropChip(1, ChipColor.GREEN);
    grid.dropChip(0, ChipColor.RED);
    grid.dropChip(0, ChipColor.GREEN);
    grid.dropChip(0, ChipColor.RED);
    grid.dropChip(0, ChipColor.GREEN);
    grid.dropChip(1, ChipColor.RED);
    grid.dropChip(2, ChipColor.GREEN);
    grid.dropChip(4, ChipColor.RED);
    
    Drop drop = player.makeDrop(grid, -1);
    assertEquals(drop.getDropCol(), 1);
    assertEquals(drop.getDropRow(), 3);
  }
  
  @Test
  public void testMakeDrop_stopPlayerWinRightDownDiagonal() {
    grid.dropChip(4, ChipColor.GREEN);
    grid.dropChip(3, ChipColor.RED);
    grid.dropChip(2, ChipColor.GREEN);
    grid.dropChip(1, ChipColor.RED);
    grid.dropChip(0, ChipColor.GREEN);
    grid.dropChip(0, ChipColor.RED);
    grid.dropChip(0, ChipColor.GREEN);
    grid.dropChip(0, ChipColor.RED);
    grid.dropChip(1, ChipColor.GREEN);
    grid.dropChip(2, ChipColor.RED);
    grid.dropChip(4, ChipColor.GREEN);
    
    Drop drop = player.makeDrop(grid, -1);
    Drop expected = new Drop.DropBuilder(1, 3).dropResult(DropResult.LEGAL).build();
    assertEquals(drop, expected);
  }
  
  @Test
  public void testMakeDrop_randomDrop() {
    Drop drop;
    drop = player.makeDrop(grid, 0);
    Drop expected = new Drop.DropBuilder(-1, -1).build();
    assertFalse(drop.equals(expected));
  }
  
  @Test
  public void testMakeDrop_withFullColumns() {
    for (int i = 0; i < 7; i++) {
      if (i == 2 || i == 3 || i == 6) {
        continue;
      }
      for (int j = 0; j < 3; j++) {
        grid.dropChip(i, ChipColor.RED);
        grid.dropChip(i, ChipColor.GREEN);
      }
    }
    
    for (int j = 0; j < 3; j++) {
      grid.dropChip(2, ChipColor.GREEN);
      grid.dropChip(2, ChipColor.RED);
      grid.dropChip(6, ChipColor.GREEN);
      grid.dropChip(6, ChipColor.RED);
    }
    
    Drop drop = player.makeDrop(grid, 100);
    assertEquals(drop.getDropCol(), 3);
    assertEquals(drop.getDropRow(), 5);
  }
}
