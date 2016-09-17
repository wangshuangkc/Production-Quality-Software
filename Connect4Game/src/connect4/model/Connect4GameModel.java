package connect4.model;

import java.util.ArrayList;
import java.util.List;

import connect4.grid.ChipColor;
import connect4.grid.Connect4Grid;
import connect4.grid.Drop;
import connect4.grid.DropResult;
import connect4.player.Connect4Player;
import connect4.player.Connect4PlayerFactory;
import connect4.player.PlayerType;
import connect4.view.Connect4Listener;
import connect4.view.SelectModeListener;

/**
 * Implements the model for Connect4 game.
 * The model contains a game grid and a list of listeners, and has a specific game mode.
 * There are two type of game modes, SINGLE or DOUBLE.
 * The model allows only 1 view for SINGLE mode, and 2 listeners for DOUBLE.
 * Using list of listeners to allow further adding listeners as speculators
 * @author kc
 */
public class Connect4GameModel implements Connect4Model{
  private Connect4Grid grid;
  private int playerNum;
  private GameMode gameMode;
  private List<Connect4Listener> listeners;
  private SelectModeListener modeSelector;
  private Connect4Player currentPlayer;
  private Connect4Player playerRed;
  private Connect4Player playerGreen;
  
  private static Connect4GameModel model;
  
  private Connect4GameModel() {
    grid = new Connect4Grid();
    listeners = new ArrayList<>();
    playerRed = new Connect4PlayerFactory().getPlayer(PlayerType.HUMAN, ChipColor.RED);
    currentPlayer = playerRed;
  }
  
  /**
   * Get the singleton instance of the model
   * @return the singleton instance of the model
   */
  public static Connect4GameModel getModel() {
    if (model == null) {
      model = new Connect4GameModel();
    }
    return model;
  }
  
  @Override
  public void setGameMode(GameMode mode) {
    gameMode = mode;
    playerNum = setPlayerNum(mode);
    setPlayerGreen();
  }
  
  private void setPlayerGreen() {
    if (gameMode == GameMode.SINGLE) {
      playerGreen = new Connect4PlayerFactory().getPlayer(PlayerType.AI, ChipColor.GREEN);
    } else {
      playerGreen = new Connect4PlayerFactory().getPlayer(PlayerType.HUMAN, ChipColor.GREEN);
    }
  }
  
  private int setPlayerNum(GameMode mode) {
    if (mode == GameMode.SINGLE) {
      return 1;
    } else if (mode == GameMode.DOUBLE) {
      return 2;
    } else {
      return 0;
    }
  }
  
  @Override
  public void reset() {
    grid = new Connect4Grid();
    currentPlayer = playerRed;
    listeners.clear();
    gameMode = null;
    playerNum = 0;
  }
  
  @Override
  public void addListener(Connect4Listener listener) {
    if (listeners.size() < playerNum && !listeners.contains(listener)) {
      listeners.add(listener);
    }
  }
  
  @Override
  public void removeListener(Connect4Listener listener) {
    listeners.remove(listener);
  }
  
  @Override
  public void setSelectModeListener(SelectModeListener listener) {
    if (modeSelector != null) {
      modeSelector.close();
    }
    modeSelector = listener;
  }
  
  @Override
  public void StartGame() {
    fireGameStartedEvent();
  }
  
  /**
   * Pass the turn to the inactive player
   */
  private void changePlayer() {
    if (currentPlayer == playerRed) {
      currentPlayer = playerGreen;
    } else {
      currentPlayer = playerRed;
    }
  }
  
  @Override
  public void dropChip(Connect4Player player, int col) {
    int row = grid.getNextSpot(col);
    if (row == -1) {
      fireDropFailedEvent();
      return;
    }
    Drop drop = currentPlayer.makeDrop(grid, col);
    raiseEvents(drop);
    
    if (gameMode == GameMode.SINGLE && currentPlayer == playerGreen) {
      drop = playerGreen.makeDrop(grid, -1);
      raiseEvents(drop);
    }
  }
  
  private void raiseEvents(Drop drop) {
    int col = drop.getDropCol();
    int row = drop.getDropRow();
    DropResult result = drop.getResult();
    
    if (result == null) {
      throw new IllegalArgumentException("Invald drop information");
    }
    fireMoveMadeEvent(col, row);
    
    if (result == DropResult.WIN) {
      fireGameWonEvent(currentPlayer);
    } else if (result == DropResult.TIE) {
      fireGameTieEvent();
    } else {
      changePlayer();
    }
  }
  
  private void fireGameStartedEvent() {
    for (Connect4Listener l: listeners) {
      l.gameStarted();
    }
  }

  private void fireMoveMadeEvent(int col, int row) {
    for (Connect4Listener l: listeners) {
      l.updateMove(row, col);
    }
  }
  
  private void fireDropFailedEvent() {
    for (Connect4Listener l: listeners) {
      l.moveFailed();
    }
  }

  private void fireGameTieEvent() {
    for (Connect4Listener l: listeners) {
      l.tie();
    }
  }

  private void fireGameWonEvent(Connect4Player winner) {
    for (Connect4Listener l: listeners) {
        l.gameWon(winner);
    }
  }
  
  @Override
  public void startNewGame() {
    fireNewGameEvent();
    reset();
    modeSelector.startSelect();
  }
  
  private void fireNewGameEvent() {
    for (Connect4Listener l : listeners) {
      l.close();
    }
  }
  
  @Override
  public int getListenerNum() {
    return listeners.size();
  }
  
  @Override
  public Connect4Player getCurrentPlayer() {
    return currentPlayer;
  }
  
  @Override
  public Connect4Player getPlayerRed() {
    return playerRed;
  }
  
  @Override
  public Connect4Player getPlayerGreen() {
    return playerGreen;
  }
  
  @Override
  public int getRowNum() {
    return grid.getRowNum();
  }
  
  @Override
  public int getColNum() {
    return grid.getColNum();
  }
}
