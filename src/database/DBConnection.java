package database;

import java.util.Set;

import entity.Location;;

public interface DBConnection {
	
	/**
	 * Close the connection.
	 */
	public void close();

	/**
	 * 
	 */
	public Set<Location> getLocationsByVotes();
	
	/**
	 * 
	 */
	public Set<Location> getLocationsByPopularCountries();
	
	/**
	 * 
	 */
	public Set<Location> getLocationsByDistance(String geoHash);
	
	/**
	 * 
	 */
	public void addLocation(Location loc);
	
	/**
	 * 
	 */
	public void voteLocation(Location loc);
}
