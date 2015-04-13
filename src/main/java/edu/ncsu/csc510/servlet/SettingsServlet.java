package edu.ncsu.csc510.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Null;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.oauth2.model.Userinfoplus;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import edu.ncsu.csc510.dao.EventDAO;
import edu.ncsu.csc510.dao.UserCalendarDAO;
import edu.ncsu.csc510.model.UserCalendar;

/**
 * Servlet implementation class EventSearch
 */
@WebServlet("/settings")
public class SettingsServlet extends HttpServlet {

    /**
	 * 
	 */
    private static final long serialVersionUID = -2400915571864192660L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public SettingsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<String> allCalendars = new ArrayList<String>();
        allCalendars.add("ncsu.edu_hpasl5cmtenq7biv0omve1nvq8@group.calendar.google.com"); // CSC Calendar
        allCalendars.add("ncsu.edu_iv41gou4edva6l3sejfg9mjo2k@group.calendar.google.com"); // CCEE Student Organization
        allCalendars.add("ncsu.edu_vd4gv8ter4klr9sa6efm5vmsq0@group.calendar.google.com"); // Physics Department
        allCalendars.add("ncsu.edu_507c8794r25bnebhjrrh3i5c4s@group.calendar.google.com"); // Academic Calendar

        Userinfoplus userinfo = (Userinfoplus) request.getSession().getAttribute("userinfo");
        
        String user = userinfo.getEmail();
        System.out.println("GOT USER " + user);

        List<UserCalendar> results = UserCalendarDAO.getUserCalendars(user);
        List<String> userCalendarNames = new ArrayList<String>();
        for(UserCalendar cal : results){
            userCalendarNames.add(cal.getCalendarId());
        }

        Map<String, String> calNames = new HashMap<String, String>();
        calNames.put("ncsu.edu_hpasl5cmtenq7biv0omve1nvq8@group.calendar.google.com", "CSC Calendar");
        calNames.put("ncsu.edu_iv41gou4edva6l3sejfg9mjo2k@group.calendar.google.com", "CCEE Student Organization Calendar");
        calNames.put("ncsu.edu_vd4gv8ter4klr9sa6efm5vmsq0@group.calendar.google.com", "Phsyics Department Calendar");
        calNames.put("ncsu.edu_507c8794r25bnebhjrrh3i5c4s@group.calendar.google.com", "Academic Calendar");

        request.setAttribute("calNames", calNames);
        request.setAttribute("calendars", allCalendars);
        request.setAttribute("userCalendars", userCalendarNames);

        // String json = new Gson().toJson(results);


        // Pass on to the JSP
        request.getRequestDispatcher("settings.jsp").forward(request, response);
        /*
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);*/
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
    	
        Userinfoplus userinfo = (Userinfoplus) request.getSession().getAttribute("userinfo");

        String userId = userinfo.getEmail();
        System.out.println("Saving settings for " + userId);
        String calendarIds[] = request.getParameterValues("calendarId");
        JsonObject ret = new JsonObject();

        if(calendarIds.length == 0){
            ret.addProperty("success", false);
            ret.addProperty("error", "You must specify at least one calendar!");
        }
        else{
            try {
                // TODO: Try to add the calendars by ID to the user's preferred calendars
                List<UserCalendar> results = new ArrayList<UserCalendar>();
                for (String cal : calendarIds)
                {
                    results.add(new UserCalendar(userId,cal));
                }
                UserCalendarDAO.addUserCalendars(userId, results);
                ret.addProperty("success", true);
            }
            catch(Exception e)
            {
                e.printStackTrace();
                ret.addProperty("success", false);
                ret.addProperty("error", e.getMessage());
            }
        }


        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().println(ret.toString());
    }

}
