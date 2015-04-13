package edu.ncsu.csc510.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.oauth2.model.Userinfoplus;
import com.google.gson.Gson;

import edu.ncsu.csc510.dao.EventDAO;

/**
 * Servlet implementation class EventSearch
 */
@WebServlet("/event")
public class EventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EventServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Userinfoplus userinfo = (Userinfoplus) request.getSession().getAttribute("userinfo");
        String user = userinfo.getEmail();
        
		String query = request.getParameter("q");
        String cal = request.getParameter("c");
        String json = "";
        // Calendar is specified, so we'll search it for the query (we won't use user)
        if(cal != null && !cal.equals("")){
            Map<String, List<Event>> results = EventDAO.searchCalendar(cal, query);
            Iterator it = results.entrySet().iterator();
            ArrayList<Event> allEvents = new ArrayList<Event>();
            while(it.hasNext()){
                Map.Entry kvp = (Map.Entry) it.next();
                List<Event> events = (List<Event>)kvp.getValue();
                allEvents.addAll(events);
            }
            json = new Gson().toJson(allEvents);
        } else if(user == null || user.equals("")){
            // User wasn't specified, so search all calendars
            Map<String, List<Event>> results = EventDAO.search(user,query);
            Iterator it = results.entrySet().iterator();
            ArrayList<Event> allEvents = new ArrayList<Event>();
            while(it.hasNext()){
                Map.Entry kvp = (Map.Entry) it.next();
                List<Event> events = (List<Event>)kvp.getValue();
                allEvents.addAll(events);
            }
            json = new Gson().toJson(allEvents);

        } else{
            // User was specified, so do a normal search
            Map<String, List<Event>> results = EventDAO.search(user,query);
            Iterator it = results.entrySet().iterator();
            ArrayList<Event> allEvents = new ArrayList<Event>();
            while(it.hasNext()){
                Map.Entry kvp = (Map.Entry) it.next();
                List<Event> events = (List<Event>)kvp.getValue();
                allEvents.addAll(events);
            }
            json = new Gson().toJson(allEvents);
        }



		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	        IOException {
		// TODO Auto-generated method stub
	}

}
