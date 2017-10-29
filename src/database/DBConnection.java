package database;

import java.util.List;

import entity.Location;;

/**
 * @author siyangtao
 *
 */
public interface DBConnection {
	
	/**
	 * Close the connection.
	 */
	public void close();

	
	/**
	 * 
	 * @return
	 */
	public List<Location> getLocationsByVotes();
	
	/**
	 * 
	 */
	public List<Location> getLocationsByMostPopularCountry();
	
	/**
	 * 
	 */
	public List<Location> getLocationsByDistance(double lat, double lng);
	
	/**
	 * 
	 * @param loc
	 */
	public boolean addLocation(Location loc);
	
	/**
	 * 
	 */
	public boolean voteLocation(String user_id, int locationId);
}
