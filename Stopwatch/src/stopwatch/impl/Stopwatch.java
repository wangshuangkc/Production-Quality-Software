package stopwatch.impl;

import java.util.ArrayList;
import java.util.List;
import stopwatch.api.IStopwatch;

/**
 * The Stopwatch is a thread-safe class implementing IStopwatch interface.
 * It supports start, stop, restart, the recording of laps and display of lap times.
 * This object is used in StopwatchFactory.
 * @author kc
 * @see IStopwatch
 */
public class Stopwatch implements IStopwatch {
  private final String ID;
  private boolean isRunning;
  private List<Long> lapTimes;
  private long startTime = 0;
  private Object lock = new Object();
  
  /**
   * private constructor for Stopwatch
   * @param id: never be null or empty
   */
  private Stopwatch(String id) {
    ID = id;
    lapTimes = new ArrayList<>();
    isRunning = false;
  }
  
  /**
   * static method for creating a new Stopwatch object
   * @param id: never be null or empty, the method using Stopwatch object needs to validate this
   * @return a new Stopwatch object with the given id
   */
  public static Stopwatch createStopwatch(String id) {
    return new Stopwatch(id);
  }
  
  @Override
  public String getId() {
    return ID;
  }

  @Override
  public synchronized void start() throws IllegalStateException{
    if (isRunning) {
      throw new IllegalStateException("Error: Stopwatch is running.");
    }
    isRunning = true;
    if (lapTimes.isEmpty()) {
      startTime = System.currentTimeMillis();
    }
  }

  @Override
  public synchronized void lap() throws IllegalStateException {
    if (!isRunning) {
      throw new IllegalStateException("Error: Stopwatch is not running.");
    }
    
    long pauseTime = System.currentTimeMillis();
    lapTimes.add(pauseTime - startTime);
    startTime = pauseTime;
  }

  @Override
  public synchronized void stop() throws IllegalStateException {
    lap();
    isRunning = false;
  }

  @Override
  public synchronized void reset() {
    if (isRunning) {
      stop();
    }
    lapTimes.clear();
  }

  @Override
  public synchronized List<Long> getLapTimes() {
    return lapTimes;
  }
  
  /**
   * Create a String representation of the Stopwatch object
   * @return information of the fields: id, running or not, number of recorded laps
   */
  @Override
  public synchronized String toString() {
    int numOfLaps = 0;
    StringBuffer sb = new StringBuffer();
    for (Long lap : lapTimes) {
      numOfLaps++;
      sb.append("Lap " + numOfLaps + ": " + lap/1000 + "s\n");
    }
    
    return sb.toString();
  }
}
