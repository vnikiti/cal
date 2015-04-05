package edu.ncsu.csc510.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.services.calendar.model.Event;
import com.google.gson.Gson;

import edu.ncsu.csc510.dao.EventDAO;

/**
 * Servlet implementation class EventDetailServlet
 */
@WebServlet("/eventDetail")
public class EventDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EventDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String calId = request.getParameter("cid");
		String eventId = request.getParameter("eid");
		//String user = request.getParameter("u");

		Event results = EventDAO.getEventDetail(calId,eventId);
		String json = new Gson().toJson(results);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}

}
