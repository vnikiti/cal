package edu.ncsu.csc510.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.util.DateTime;
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
        // TODO: get the image URL associated with this calendar ID, then pass it on
        // Get the event information and pass it to the JSP
        request.setAttribute("location", results.getLocation());
        request.setAttribute("description", results.getDescription());
        request.setAttribute("title", results.getSummary());
        DateTime eventDateTime = results.getStart().getDateTime();
        // Try to parse the Google DateTime to Java Date, then format for human reading
        Date date;
        try{
            SimpleDateFormat eventSdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            date = eventSdf.parse(eventDateTime.toString());

            SimpleDateFormat mySdf = new SimpleDateFormat("E, M d, yyyy 'at' h:mm a");
            String start = mySdf.format(date);
            request.setAttribute("start", start);
        } catch(Exception e){

        }

        // Pass on to the JSP
        request.getRequestDispatcher("detail.jsp").forward(request, response);

		String json = new Gson().toJson(results);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}

}
