package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import algorithm.GeoHash;
import database.DBConnection;
import database.DBConnectionFactory;
import entity.Location;
import entity.Location.LocationBuilder;

class TestMySQLConnection {

	DBConnection conn;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		conn = DBConnectionFactory.getDBConnection();
	}

	@AfterEach
	void tearDown() throws Exception {
		conn.close();
	}

	@Test
	void testGetLocationsByVotes() {
		assertTrue(conn.getLocationsByVotes() instanceof ArrayList<?>);
		
		List<Location> list = conn.getLocationsByVotes();
		assertTrue(list.get(0) instanceof Location);
	}
	
	@Test
	void testgetLocationsByMostPopularCountry() {
		assertTrue(conn.getLocationsByMostPopularCountry() instanceof ArrayList<?>);
		
		List<Location> list = conn.getLocationsByMostPopularCountry();
		assertTrue(list.get(0) instanceof Location);
	}
	
	@Test
	void testGetLocationsByDistance() {
		double lat = 33.8523;
		double lng = 151.2108;
		assertTrue(conn.getLocationsByDistance(lat, lng) instanceof ArrayList<?>);
		
		List<Location> list = conn.getLocationsByDistance(lat, lng);
		assertTrue(list.get(0) instanceof Location);
	}
	
	@Test
	void testAddLocation() {
		LocationBuilder builder = new LocationBuilder();
		double lat = 33.8523;
		double lng = 151.2108;
		String geoHash = GeoHash.encodeGeohash(lat, lng, 4);
		Location loc = builder.setCountry("Australia")
				.setDescription("Harbour Bridge")
				.setLatitude(lat)
				.setLongitude(lng)
				.setVotes(0)
				.setGeohash(geoHash)
				.build();
		assertTrue(conn.addLocation(loc));
	}
	
	@Test
	void testVoteLocation() {
		int locationId = 1;
		assertTrue(conn.voteLocation("2222", locationId));
	}

}
