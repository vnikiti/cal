package edu.ncsu.csc510.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Null;

import com.google.api.services.calendar.model.Event;
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
    //private static final long serialVersionUID = 1L;

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

        String user = request.getParameter("u");
        System.out.println("GOT USER "+user);

        List<UserCalendar> results = UserCalendarDAO.getUserCalendars(user);
        String json = new Gson().toJson(results);

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

        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String data = buffer.toString();
        System.out.println(data);

        System.out.println("IN POST: "+data);
        Gson gson = new Gson();
        Type type = new TypeToken<List<UserCalendar>>(){}.getType();
        List<UserCalendar> userCalendarList = null;
        try {
             userCalendarList = gson.fromJson(data, type);
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        if (userCalendarList == null)
        {
            System.out.println("GOT NULL");
        }
        else
        {
            System.out.println("NOT NULL");
        }
        for( UserCalendar c : userCalendarList)
        {
            System.out.println("GOT BACK "+c.getCalendarId());
        }

    }

}
