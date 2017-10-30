package rpc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import database.DBConnection;
import database.DBConnectionFactory;
import entity.Location;

/**
 * Servlet implementation class SearchVote
 */
@WebServlet("/vote")
public class SearchVote extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchVote() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		String userId = request.getParameter("user_id");
		String sortedby = request.getParameter("sortedby");

		List<Location> locations = new ArrayList<>();
		DBConnection conn = DBConnectionFactory.getDBConnection();
		switch (sortedby) {
		case "vote":
			locations = conn.getLocationsByVotes();
			break;
		case "distance":
			try {
				double lat = Double.parseDouble(request.getParameter("lat"));
				double lng = Double.parseDouble(request.getParameter("lng"));
				locations = conn.getLocationsByDistance(lat, lng);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			break;
		case "country":
			locations = conn.getLocationsByMostPopularCountry();
			break;
		default:
			// By default return locations by most votes
			locations = conn.getLocationsByVotes();
			break;
		}

		// Create response JSON Array
		List<JSONObject> list = new ArrayList<>();
		try {
			for (Location loc : locations) {
				JSONObject obj = loc.toJSONObject();
				list.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONArray array = new JSONArray(list);
		RpcHelper.writeJsonArray(response, array);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			// Get request body and convert to JSONObject
			JSONObject input = RpcHelper.readJsonObject(request);
			
			// Get user_id and location_id from input
			String userId = input.getString("user_id");
			int locationId = input.getInt("location_id");
			DBConnection conn = DBConnectionFactory.getDBConnection();
			conn.voteLocation(userId, locationId);
	
			// Return save result to client
			RpcHelper.writeJsonObject(response, new JSONObject().put("result", "SUCCESS"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
