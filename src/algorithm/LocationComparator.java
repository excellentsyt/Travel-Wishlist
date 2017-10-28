package algorithm;

import java.util.Comparator;

import entity.Location;

public class LocationComparator implements Comparator<Location> {

	private Location currentLoc;

	public LocationComparator(Location current) {
		currentLoc = current;
	}

	@Override
	public int compare(Location l1, Location l2) {
		double distance1 = getDistance(l1.getLatitude(), l1.getLongitude(), currentLoc.getLatitude(),
				currentLoc.getLongitude());
		double distance2 = getDistance(l2.getLatitude(), l2.getLongitude(), currentLoc.getLatitude(),
				currentLoc.getLongitude());
		return (int) (distance1 - distance2);
	}

	// Calculate the distances between two geolocations.
	// Source : http://andrew.hedges.name/experiments/haversine/
	private static double getDistance(double lat1, double lon1, double lat2, double lon2) {
		double dlon = lon2 - lon1;
		double dlat = lat2 - lat1;
		double a = Math.sin(dlat / 2 / 180 * Math.PI) * Math.sin(dlat / 2 / 180 * Math.PI)
				+ Math.cos(lat1 / 180 * Math.PI) * Math.cos(lat2 / 180 * Math.PI) * Math.sin(dlon / 2 / 180 * Math.PI)
						* Math.sin(dlon / 2 / 180 * Math.PI);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		// Radius of earth in miles.
		double R = 3961;
		return R * c;
	}
}
