package connect4.view;

/**
 * This declares the method performed by a select mode observer of the Connect 4 game model
 * @author kc
 *
 */
public interface SelectModeListener {
  
  /**
   * Show the observer for the user to select the game mode
   */
  void startSelect();
  
  /**
   * Close this select mode observer
   */
  void close();
}
