package rpc;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import algorithm.GeoHash;
import database.DBConnection;
import database.DBConnectionFactory;
import entity.Location;
import entity.Location.LocationBuilder;
import algorithm.GeoHash;

/**
 * Servlet implementation class pinPointLocation
 */
@WebServlet("/pin")
public class pinPointLocation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public pinPointLocation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// Get Post body
//			String userId = request.getParameter("user_id");
			String description = request.getParameter("description");
			String country = request.getParameter("country");
			double lat = Double.parseDouble(request.getParameter("lat"));
			double lng = Double.parseDouble(request.getParameter("lng"));
			String geoHash = GeoHash.encodeGeohash(lat, lng, 4); // Here the precision is 4 which is +- 20km. TODO: change to +-10km.
			
			// Build location object
			LocationBuilder builder = new LocationBuilder();
			builder.setCountry(country);
			builder.setDescription(description);
			builder.setLongitude(lng);
			builder.setLatitude(lat);
			builder.setVotes(0);
			builder.setGeohash(geoHash);
			Location loc = builder.build();
			
			// Insert to DB
			DBConnection conn = DBConnectionFactory.getDBConnection();
			conn.addLocation(loc);
	
			// Return save result to client
			RpcHelper.writeJsonObject(response, new JSONObject().put("result", "SUCCESS"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
