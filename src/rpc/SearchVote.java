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
		String userId = request.getParameter("user_id");
		String sortedby = request.getParameter("sortedby");

		List<Location> locations = new ArrayList<>();
		DBConnection conn = DBConnectionFactory.getDBConnection();
		switch (sortedby) {
		case "vote":
			locations = conn.getLocationsByVotes();
			break;
		case "distance":
			double lat = Double.parseDouble(request.getParameter("lat"));
			double lng = Double.parseDouble(request.getParameter("lng"));
			locations = conn.getLocationsByDistance(lat, lng);
			break;
		case "country":
			locations = conn.getLocationsByMostPopularCountry();
			break;
		default:
			break;
		}

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
		// Vote is received here
		
	}

}
