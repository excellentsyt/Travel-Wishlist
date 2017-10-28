package entity;

public class Location {
	private int itemId;
	private String country;
	private double latitude;
	private double longitude;
	private String description;
	private int votes;
	private String geohash;
	
	private Location(LocationBuilder builder) {
		this.itemId = builder.itemId;
		this.country = builder.country;
		this.latitude = builder.latitude;
		this.longitude = builder.longitude;
		this.description = builder.description;
		this.votes = builder.votes;
		this.geohash = builder.geohash;
	}
	
	public int getItemId() {
		return itemId;
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
		private int itemId;
		private String country;
		private double latitude;
		private double longitude;
		private String description;
		private int votes;
		private String geohash;
		
		public LocationBuilder setItemId(int itemId) {
			this.itemId = itemId;
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
