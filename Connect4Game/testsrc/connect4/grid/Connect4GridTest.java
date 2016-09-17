package connect4.grid;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Connect4GridTest {

  Connect4Grid grid;
  
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }

  @Before
  public void setUp() throws Exception {
    grid = new Connect4Grid();
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testGetNextSpot() {
    int[] expected = {5, 5, 5, 5, 5, 5, 5};
    int[] arr = new int[7];
    for (int i = 0; i < 7; i++) {
      arr[i] = grid.getNextSpot(i);
    }
    assertTrue(Arrays.equals(expected, arr));
  }

  @Test
  public void testDropChip_legalDrop() {
    Drop expected;
    Drop drop;
    for (int i = 0; i < 6; i+=2) {
      drop = grid.dropChip(i, ChipColor.RED);
      expected = new Drop.DropBuilder(i, 5).dropResult(DropResult.LEGAL).build();
      assertTrue(drop.equals(expected));
    }
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testDropChip_negativeCol() {
    grid.dropChip(-1, ChipColor.RED);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testDropChip_tooLargeCol() {
    grid.dropChip(8, ChipColor.RED);
  }
  
  @Test
  public void testDropChip_dropToFullColumn() {
    grid.dropChip(0, ChipColor.RED);
    grid.dropChip(0, ChipColor.GREEN);
    grid.dropChip(0, ChipColor.RED);
    grid.dropChip(0, ChipColor.GREEN);
    grid.dropChip(0, ChipColor.RED);
    grid.dropChip(0, ChipColor.GREEN);
    Drop drop = grid.dropChip(0, ChipColor.RED);
    assertEquals(drop.getResult(), DropResult.ILLEGAL);
  }
  
  @Test
  public void testIsTie() {
    for (int i = 0; i < 7; i++) {
      assertFalse(grid.isTie());
      for (int j = 0; j < 6; j++) {
        grid.dropChip(i, ChipColor.RED);
      }
    }
    assertTrue(grid.isTie());
  }
  
  @Test
  public void testWinnerDrop_negativeCol() {
    assertFalse(grid.isWinnerDrop(-1, 0, ChipColor.RED));
  }
  
  @Test
  public void testWinnerDrop_tooBigCol() {
    assertFalse(grid.isWinnerDrop(8, 0, ChipColor.RED));
  }
  
  @Test
  public void testWinnerDrop_negativeRow() {
    assertFalse(grid.isWinnerDrop(0, -1, ChipColor.RED));
  }
  
  @Test
  public void testWinnerDrop_tooBigRow() {
    assertFalse(grid.isWinnerDrop(0, 7, ChipColor.RED));
  }
  
  @Test
  public void testWinnerDrop_notWinner() {
    for (int i = 0; i < 3; i++) {
      grid.dropChip(i, ChipColor.RED);
    }
    assertFalse(grid.isWinnerDrop(4, 5, ChipColor.RED));
  }
  
  @Test
  public void testWinnerDrop_horizontalWinner() {
    grid.dropChip(1, ChipColor.RED);
    grid.dropChip(2, ChipColor.RED);
    grid.dropChip(3, ChipColor.RED);
    grid.dropChip(5, ChipColor.RED);
    grid.dropChip(6, ChipColor.RED);
    
    int row = grid.getNextSpot(4);
    assertTrue(grid.isWinnerDrop(4, row, ChipColor.RED));
    assertFalse(grid.isWinnerDrop(4, row, ChipColor.GREEN));

    row = grid.getNextSpot(0);
    assertTrue(grid.isWinnerDrop(0, row, ChipColor.RED));
    assertFalse(grid.isWinnerDrop(0, row, ChipColor.GREEN));
  }
  
  @Test
  public void testWinnerDrop_verticalWinner() {
    grid.dropChip(1, ChipColor.RED);
    grid.dropChip(1, ChipColor.RED);
    grid.dropChip(1, ChipColor.RED);
    
    grid.dropChip(2, ChipColor.GREEN);
    grid.dropChip(2, ChipColor.GREEN);
    
    int row = grid.getNextSpot(1);
    assertTrue(grid.isWinnerDrop(1, row, ChipColor.RED));
    assertFalse(grid.isWinnerDrop(1, row, ChipColor.GREEN));

    row = grid.getNextSpot(2);
    assertFalse(grid.isWinnerDrop(2, row, ChipColor.GREEN));
  }
  
  @Test
  public void testWinnerDrop_leftDownWinner() {
    grid.dropChip(6, ChipColor.RED);
    grid.dropChip(6, ChipColor.GREEN);
    int row = grid.getNextSpot(5);
    assertFalse(grid.isWinnerDrop(5, row, ChipColor.GREEN));
    
    grid.dropChip(0, ChipColor.RED);
    grid.dropChip(1, ChipColor.GREEN);
    grid.dropChip(3, ChipColor.RED);
    grid.dropChip(3, ChipColor.GREEN);
    grid.dropChip(3, ChipColor.RED);
    grid.dropChip(0, ChipColor.GREEN);
    grid.dropChip(1, ChipColor.RED);
    grid.dropChip(2, ChipColor.GREEN);
    grid.dropChip(3, ChipColor.RED);
    grid.dropChip(2, ChipColor.GREEN);
    row = grid.getNextSpot(2);
    assertTrue(grid.isWinnerDrop(2, row, ChipColor.RED));
    
    grid.dropChip(0, ChipColor.RED);
    row = grid.getNextSpot(1);
    assertFalse(grid.isWinnerDrop(1, row, ChipColor.GREEN));
    
    grid.dropChip(1, ChipColor.GREEN);
    grid.dropChip(0, ChipColor.RED);
    grid.dropChip(1, ChipColor.GREEN);
    grid.dropChip(0, ChipColor.RED);
    grid.dropChip(1, ChipColor.GREEN);
    row = grid.getNextSpot(1);
    assertFalse(grid.isWinnerDrop(1, row, ChipColor.RED));
  }
  
  @Test
  public void testWinnerDrop_rightDownWinner() {
    grid.dropChip(6, ChipColor.RED);
    grid.dropChip(5, ChipColor.GREEN);
    int row = grid.getNextSpot(5);
    assertFalse(grid.isWinnerDrop(5, row, ChipColor.RED));
    
    grid.dropChip(6, ChipColor.RED);
    grid.dropChip(5, ChipColor.GREEN);
    row = grid.getNextSpot(5);
    assertFalse(grid.isWinnerDrop(5, row, ChipColor.RED));
    
    grid.dropChip(0, ChipColor.RED);
    grid.dropChip(0, ChipColor.GREEN);
    row = grid.getNextSpot(1);
    assertFalse(grid.isWinnerDrop(1, row, ChipColor.RED));
    assertFalse(grid.isWinnerDrop(1, row, ChipColor.GREEN));
    
    grid.dropChip(1, ChipColor.RED);
    grid.dropChip(1, ChipColor.GREEN);
    grid.dropChip(2, ChipColor.RED);
    grid.dropChip(2, ChipColor.GREEN);
    grid.dropChip(0, ChipColor.RED);
    grid.dropChip(1, ChipColor.GREEN);
    grid.dropChip(4, ChipColor.RED);
    grid.dropChip(3, ChipColor.GREEN);
    grid.dropChip(3, ChipColor.RED);
    row = grid.getNextSpot(0);
    assertTrue(grid.isWinnerDrop(0, row, ChipColor.GREEN));
  }
  
  @Test
  public void testGetRowNum() {
    assertEquals(grid.getRowNum(), 6);
  }
  
  @Test
  public void testGetColNum() {
    assertEquals(grid.getColNum(), 7);
  }
}
