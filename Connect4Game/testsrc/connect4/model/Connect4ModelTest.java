package connect4.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import connect4.grid.ChipColor;
import connect4.player.Connect4AIPlayer;
import connect4.player.Connect4HumanPlayer;
import connect4.player.Connect4Player;
import connect4.view.Connect4Listener;
import connect4.view.SelectModeListener;

public class Connect4ModelTest {

  static Connect4GameModel model;
  static TestPlayerListener listener1;
  static TestPlayerListener listener2;
  static Connect4Player redPlayer;
  static Connect4Player greenPlayer;
  static Connect4Player AIPlayer;
  
  /**
   * This represents a simplified Connect4Listener for testing methods related to player listener.
   * @author kc
   */
  public class TestPlayerListener implements Connect4Listener {
    private String status;
    private Connect4Player player;
    
    public TestPlayerListener(Connect4Player p) {
      player = p;
      status = "None";
    }

    @Override
    public void gameStarted() {
      status = "Game Start";
    }
    
    @Override
    public void updateMove(int x, int y) {
      status = "Update Move";
    }

    @Override
    public void moveFailed() {
      status = "Move Failed";
    }
    
    @Override
    public void gameWon(Connect4Player winner) {
      status = winner.getPlayerColor() + "Win";
    }

    @Override
    public void tie() {
      status = "Tie";
    }

    @Override
    public void close() {
      status = "Close";
    } 
    
    @Override
    public Connect4Player getPlayer() {
      return player;
    }
    
    public String getStatus() {
      return status;
    }
  }
  
  public class TestSelectModeListener implements SelectModeListener {
    private String status = "None";
    
    @Override
    public void startSelect() {
      status = "Select";
    }

    @Override
    public void close() {
      status = "Close";
    }
    
    public String getStatus() {
      return status;
    }
    
  }
  
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    model = Connect4GameModel.getModel();
    redPlayer = new Connect4HumanPlayer.PlayerBuilder(ChipColor.RED).build();
    greenPlayer = new Connect4HumanPlayer.PlayerBuilder(ChipColor.GREEN).build();
    AIPlayer = new Connect4AIPlayer();
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }

  @Before
  public void setUp() throws Exception {
    listener1 = new TestPlayerListener(redPlayer);
    listener2 = new TestPlayerListener(greenPlayer);
  }

  @After
  public void tearDown() throws Exception {
    model.reset();
  }

  @Test
  public void testGetModel_newInstance() {
    assertNotNull(model);
  }
  
  @Test
  public void testGetModel_singletonInstance() {
    Connect4GameModel model2 = Connect4GameModel.getModel();
    assertEquals(model, model2);
  }
  
  @Test
  public void testgetCurrentPlayer() {
    Connect4Player player = new Connect4HumanPlayer.PlayerBuilder(ChipColor.RED).build();
    assertEquals(model.getCurrentPlayer(), player);
  }
  
  @Test
  public void testGetPlayer_singleMode() {
    model.setGameMode(GameMode.SINGLE);
    assertEquals(model.getPlayerRed(), redPlayer);
    assertEquals(model.getPlayerGreen(), AIPlayer);
  }
  
  @Test
  public void testGetPlayer_doubleMode() {
    model.setGameMode(GameMode.DOUBLE);
    assertEquals(model.getPlayerRed(), redPlayer);
    assertEquals(model.getPlayerGreen(), greenPlayer);
  }
  
  @Test
  public void testGetListenerNum() {
    assertEquals(model.getListenerNum(), 0);
  }
  
  @Test
  public void testAddListenerAndSetMode_noMode() {
    model.setGameMode(null);
    model.addListener(listener1);
    assertEquals(model.getListenerNum(), 0);
  }
  
  @Test
  public void testAddListenerAndSetMode_singleMode() {
    model.setGameMode(GameMode.SINGLE);
    assertEquals(model.getPlayerGreen(), AIPlayer);
    model.addListener(listener1);
    assertEquals(model.getListenerNum(), 1);
    model.addListener(listener1);
    assertEquals(model.getListenerNum(), 1);
    model.addListener(listener2);
    assertEquals(model.getListenerNum(), 1);
  }
  
  @Test
  public void testAddListenerAndSetMode_doubleMode() {
    model.setGameMode(GameMode.DOUBLE);
    assertTrue(model.getPlayerGreen() instanceof Connect4HumanPlayer);
    model.addListener(listener1);
    assertEquals(model.getListenerNum(), 1);
    model.addListener(listener1);
    assertEquals(model.getListenerNum(), 1);
    model.addListener(listener2);
    assertEquals(model.getListenerNum(), 2);
    model.addListener(listener2);
    assertEquals(model.getListenerNum(), 2);
  }
  
  @Test
  public void testAddListenerAndSetMode_nullMode() {
    model.addListener(listener1);
    assertEquals(model.getListenerNum(), 0);
  }
  
  @Test
  public void testRemoveListener() {
    model.setGameMode(GameMode.SINGLE);
    model.addListener(listener1);
    model.removeListener(listener1);
    assertEquals(model.getListenerNum(), 0);
  }
  
  @Test
  public void testRemoveListener_notExistListener() {
    model.setGameMode(GameMode.SINGLE);
    model.removeListener(listener2);
    assertEquals(model.getListenerNum(), 0);
    model.addListener(listener1);
    model.removeListener(listener2);
    assertEquals(model.getListenerNum(), 1);
  }
  
  @Test
  public void testStartGame() {
    model.setGameMode(GameMode.DOUBLE);
    model.addListener(listener1);
    model.addListener(listener2);
    model.StartGame();
    assertEquals(listener1.getStatus(), "Game Start");
    assertEquals(listener2.getStatus(), "Game Start");
  }
  
  @Test(expected = RuntimeException.class)
  public void testDropChip_negativeColumn() {
    model.dropChip(redPlayer, -1);
  }
  
  @Test(expected = RuntimeException.class)
  public void testDropChip_notExistColumn() {
    model.dropChip(redPlayer, 10);
  }
  
  @Test
  public void testDropChip() {
    model.setGameMode(GameMode.DOUBLE);
    model.addListener(listener1);
    model.addListener(listener2);
    model.dropChip(redPlayer, 0);
    assertEquals(listener1.getStatus(), "Update Move");
    assertEquals(listener2.getStatus(), "Update Move");
  }
  
  @Test
  public void testDropChip_addToFullColumn() {
    model.setGameMode(GameMode.DOUBLE);
    model.addListener(listener1);
    for (int i = 0; i < 3; i++) {
      model.dropChip(redPlayer, 0);
      model.dropChip(greenPlayer, 0);
    }
    model.dropChip(redPlayer, 0);
    assertEquals(listener1.getStatus(), "Move Failed");
  }
  
  @Test
  public void testDropChip_tieDrop() {
    model.setGameMode(GameMode.DOUBLE);
    model.addListener(listener1);
    model.addListener(listener2);
    
    model.dropChip(redPlayer, 0);
    model.dropChip(greenPlayer, 1);
    model.dropChip(redPlayer, 3);
    model.dropChip(greenPlayer, 2);
    model.dropChip(redPlayer, 4);
    model.dropChip(greenPlayer, 6);
    model.dropChip(redPlayer, 5);
    model.dropChip(greenPlayer, 0);
    model.dropChip(redPlayer, 1);
    model.dropChip(greenPlayer, 2);
    model.dropChip(redPlayer, 3);
    model.dropChip(greenPlayer, 4);
    model.dropChip(redPlayer, 6);
    model.dropChip(greenPlayer, 5);
    
    assertNotEquals(listener1.getStatus(), "Tie");
    assertNotEquals(listener2.getStatus(), "Tie");
    
    model.dropChip(redPlayer, 1);
    model.dropChip(greenPlayer, 0);
    model.dropChip(redPlayer, 2);
    model.dropChip(greenPlayer, 3);
    model.dropChip(redPlayer, 6);
    model.dropChip(greenPlayer, 4);
    model.dropChip(redPlayer, 0);
    model.dropChip(greenPlayer, 5);
    model.dropChip(redPlayer, 4);
    model.dropChip(greenPlayer, 1);
    model.dropChip(redPlayer, 5);
    model.dropChip(greenPlayer, 2);
    model.dropChip(redPlayer, 6);
    model.dropChip(greenPlayer, 3);
    
    assertNotEquals(listener1.getStatus(), "Tie");
    assertNotEquals(listener2.getStatus(), "Tie");
    
    model.dropChip(redPlayer, 3);
    model.dropChip(greenPlayer, 0);
    model.dropChip(redPlayer, 6);
    model.dropChip(greenPlayer, 1);
    model.dropChip(redPlayer, 0);
    model.dropChip(greenPlayer, 2);
    model.dropChip(redPlayer, 1);
    model.dropChip(greenPlayer, 4);
    model.dropChip(redPlayer, 3);
    model.dropChip(greenPlayer, 5);
    model.dropChip(redPlayer, 4);
    model.dropChip(greenPlayer, 2);
    model.dropChip(redPlayer, 5);
    model.dropChip(greenPlayer, 6);
    
    assertEquals(listener1.getStatus(), "Tie");
    assertEquals(listener2.getStatus(), "Tie");
  }
  
  @Test 
  public void testStartNewGame() {
    TestSelectModeListener selector = new TestSelectModeListener();
    model.setGameMode(GameMode.DOUBLE);
    model.setSelectModeListener(selector);
    model.addListener(listener1);
    model.addListener(listener2);
    
    model.startNewGame();
    assertEquals(selector.getStatus(), "Select");
    assertEquals(listener1.getStatus(), "Close");
    assertEquals(listener2.getStatus(), "Close");
  }
  
  @Test
  public void testGetRowNum() {
    assertEquals(model.getRowNum(), 6);
  }
  
  @Test
  public void testGetColNum() {
    assertEquals(model.getColNum(), 7);
  }
}
