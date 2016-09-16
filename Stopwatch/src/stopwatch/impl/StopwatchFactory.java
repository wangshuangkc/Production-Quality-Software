package stopwatch.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import stopwatch.api.IStopwatch;

/**
 * The StopwatchFactory is a thread-safe factory class for IStopwatch objects.
 * It maintains references to all created IStopwatch objects and provides a
 * convenient method for getting a list of those objects.
 *
 */
public class StopwatchFactory {
  private static ConcurrentMap<String, IStopwatch> stopwatches = new ConcurrentHashMap<>();
  private static Object lock = new Object();
	  /**
	   * Creates and returns a new IStopwatch object
	   * @param id The identifier of the new object
	   * @return The new IStopwatch object
	   * @throws IllegalArgumentException if <code>id</code> is empty, null, or already
   *     taken.
	   */
	  public static IStopwatch getStopwatch(String id) throws IllegalArgumentException{
	    if (id == null || id.isEmpty()) {
	      throw new IllegalArgumentException("Invalid ID: ID is never null or empty.");
	    }
	    IStopwatch stopwatch;
	    synchronized (lock) {
	      if (stopwatches.containsKey(id)) {
	        throw new IllegalArgumentException("Invalid ID: " + id + " is used.");
	      }
	    
	      stopwatch = Stopwatch.createStopwatch(id);
	      stopwatches.put(id, stopwatch);
	    }
	    return stopwatch;
	  }

	  /**
	   * Returns a list of all created stopwatches
	   * @return a List of all created IStopwatch objects.  Returns an empty
	   * list if no IStopwatches have been created.
	   */
	  public static List<IStopwatch> getStopwatches() {
	    List<IStopwatch> stopwatchList;
	    synchronized (lock) {
	      stopwatchList = new ArrayList<>(stopwatches.values());
	    }
	    return stopwatchList;
	  }
}
