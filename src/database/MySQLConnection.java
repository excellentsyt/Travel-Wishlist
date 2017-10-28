package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import algorithm.LocationComparator;
import db.mysql.MySQLDBUtil;
import entity.Location;
import entity.Location.LocationBuilder;;

public class MySQLConnection implements DBConnection {
	private Connection conn;

	public MySQLConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(MySQLDBUtil.URL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public List<Location> getLocationsByVotes() {
		if (conn == null) {
			return null;
		}
		
		List<Location> locations = new ArrayList<>();
		try {
			String sql = "SELECT * FROM locations ORDER BY count_of_votes DESC";
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			locations = toLocationList(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return locations;
	}
	
	private List<Location> toLocationList(ResultSet rs) {
		List<Location> locations = new ArrayList<>();
		try {
			while (rs.next()) {
				LocationBuilder builder = new LocationBuilder();
				builder.setCountry(rs.getString("country"));
				builder.setDescription(rs.getString("description"));
				builder.setGeohash(rs.getString("geohash"));
				builder.setLocationId(rs.getInt("location_id"));
				builder.setLongitude(rs.getFloat("longitude"));
				builder.setLatitude(rs.getFloat("latitude"));
				builder.setVotes(rs.getInt("count_of_votes"));
				
				Location loc = builder.build();
				locations.add(loc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return locations;
	}

	@Override
	public List<Location> getLocationsByMostPopularCountry() {
		if (conn == null) {
			return null;
		}
		
		List<Location> locations = new ArrayList<>();
		String country = getMostPopularCountry();
		try {
			// Find all locations from the  most popular country
			String sql = "SELECT * FROM locations WHERE country = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, country);
			ResultSet rs = statement.executeQuery();
			locations = toLocationList(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return locations;
	}

	private String getMostPopularCountry() {
		if (conn == null) {
			return null;
		}
	
		String country = "";
		try {
			// Find the most popular country
			String sql = "SELECT country, COUNT(country) as country_occurence FROM Customers Group by country ORDER BY country_occurence DESC limit 1";
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			
			if (rs.next()) {
				country = rs.getString("country");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return country;
	}

	@Override
	public List<Location> getLocationsByDistance(double lat, double lng) {
		if (conn == null) {
			return null;
		}
		
		List<Location> locations = new ArrayList<>();
		try {
			String sql = "SELECT * FROM locations";
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			locations = toLocationList(rs);
			
			// Sort locations list based on distance to the current location
			LocationBuilder builder = new LocationBuilder();
			Location cur = builder.setLatitude(lat).setLongitude(lng).build();
			Collections.sort(locations, new LocationComparator(cur));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return locations;
	}


	@Override
	public void addLocation(Location loc) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void voteLocation(String user_id, int locationId) {
		if (conn == null) {
			return;
		}
		
		try {
			// Check whether it is a unique vote and update the votes table
			String sql = "INSERT IGNORE INTO votes (user_id, location_id) VALUES (?,?)";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, user_id);
			statement.setInt(2, locationId);
			int result = statement.executeUpdate();	
			if (result == 1) { // It is a unique user -> location key. If result == 0, it is an existing one
							   // and the votes of this location should NOT be updated.
				// Make the location vote increase by 1.
				sql = "UPDATE locations SET count_of_votes = count_of_votes + 1 WHERE location_id = ?";
				statement = conn.prepareStatement(sql);
				statement.setInt(1, locationId);
				statement.execute();	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void close() {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
