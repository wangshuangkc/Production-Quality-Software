package connect4.player;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import connect4.grid.ChipColor;

public class Connect4PlayerFactoryTest {

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
  public void testGetPlayer_AI() {
    Connect4Player player = new Connect4PlayerFactory().getPlayer(PlayerType.AI, ChipColor.RED);
    assertTrue(player instanceof Connect4AIPlayer);
    assertEquals(player.getPlayerColor(), ChipColor.GREEN);
  }

  @Test
  public void testGetPlayer_human() {
    Connect4Player player1 = 
        new Connect4PlayerFactory().getPlayer(PlayerType.HUMAN, ChipColor.RED);
    assertTrue(player1 instanceof Connect4HumanPlayer);
    assertEquals(player1.getPlayerColor(), ChipColor.RED);
    
    Connect4Player player2 = 
        new Connect4PlayerFactory().getPlayer(PlayerType.HUMAN, ChipColor.GREEN);
    assertTrue(player2 instanceof Connect4HumanPlayer);
    assertEquals(player2.getPlayerColor(), ChipColor.GREEN);
  }
  
  @Test
  public void testGetPlayer_null() {
    Connect4Player player = 
        new Connect4PlayerFactory().getPlayer(null, ChipColor.RED);
    assertNull(player);
  }
}
