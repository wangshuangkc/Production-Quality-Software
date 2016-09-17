package connect4.app;

import connect4.model.Connect4GameModel;
import connect4.view.SelectModeView;

/**
 * This program runs connect4 game.
 * @author kc
 *
 */
public class Connect4App {

  public static void main(String[] args) {
    new Connect4App().startGame();
  }
  
  /**
   * Start the game and invoke a SelectModeView
   */
  public void startGame() {
    Connect4GameModel model = Connect4GameModel.getModel();
    new SelectModeView(model);
  }
}
