package entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Location {
	private int locationId;
	private String country;
	private double latitude;
	private double longitude;
	private String description;
	private int votes;
	private String geohash;
	
	private Location(LocationBuilder builder) {
		this.locationId = builder.locationId;
		this.country = builder.country;
		this.latitude = builder.latitude;
		this.longitude = builder.longitude;
		this.description = builder.description;
		this.votes = builder.votes;
		this.geohash = builder.geohash;
	}
	
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("location_id", locationId);
			obj.put("country", country);
			obj.put("latitude", latitude);
			obj.put("longitude", longitude);
			obj.put("description", description);
			obj.put("votes", votes);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	public int getLocationId() {
		return locationId;
	}

	public String getCountry() {
		return country;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public String getDescription() {
		return description;
	}

	public int getVotes() {
		return votes;
	}

	public String getGeohash() {
		return geohash;
	}

	public static class LocationBuilder {
		private int locationId;
		private String country;
		private double latitude;
		private double longitude;
		private String description;
		private int votes;
		private String geohash;
		
		public LocationBuilder setLocationId(int locationId) {
			this.locationId = locationId;
			return this;
		}
		public LocationBuilder setCountry(String country) {
			this.country = country;
			return this;
		}
		public LocationBuilder setLatitude(double latitude) {
			this.latitude = latitude;
			return this;
		}
		public LocationBuilder setLongitude(double longitude) {
			this.longitude = longitude;
			return this;
		}
		public LocationBuilder setDescription(String description) {
			this.description = description;
			return this;
		}
		public LocationBuilder setVotes(int votes) {
			this.votes = votes;
			return this;
		}
		public LocationBuilder setGeohash(String geohash) {
			this.geohash = geohash;
			return this;
		}
		
		public Location build() {
			return new Location(this);
		}
	}
}
