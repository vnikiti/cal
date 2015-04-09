package edu.ncsu.csc510.servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
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
        // Hard-coded method to set image Url
        String eventEmail = results.getOrganizer().getEmail();
        String imgUrl = "";

        if(eventEmail.equals("ncsu.edu_hpasl5cmtenq7biv0omve1nvq8@group.calendar.google.com")){
            imgUrl = "http://users.dsic.upv.es/~afernandez/images/logos/csc.png";
        } else if(eventEmail.equals("ncsu.edu_iv41gou4edva6l3sejfg9mjo2k@group.calendar.google.com")){
            imgUrl = "http://yt3.ggpht.com/-zwmOVhYtX6Q/AAAAAAAAAAI/AAAAAAAAAAA/Y4Ot1GQ9VeA/s100-c-k-no/photo.jpg";
        } else if(eventEmail.equals("ncsu.edu_vd4gv8ter4klr9sa6efm5vmsq0@group.calendar.google.com")){
            imgUrl = "http://www.physics.ncsu.edu/images/Physics.gif";
        } else if(eventEmail.equals("ncsu.edu_507c8794r25bnebhjrrh3i5c4s@group.calendar.google.com")){
            imgUrl = "http://moss.csc.ncsu.edu/~mueller/cluster/arc/ncsu_block.gif";
        } else{
            imgUrl = "http://moss.csc.ncsu.edu/~mueller/cluster/arc/ncsu_block.gif";
        }

        // Get the event information and pass it to the JSP
        request.setAttribute("location", results.getLocation());
        request.setAttribute("description", results.getDescription());
        request.setAttribute("title", results.getSummary());
        request.setAttribute("imgUrl", imgUrl);
     
        //Constructing Add to Calendar URL
        String strSDT = null;
        String strEDT = null;
        DateTime eventSDT = results.getStart().getDateTime();
        DateTime eventEDT = results.getEnd().getDateTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        Date sdate,edate;
		try {
			sdate = df.parse(eventSDT.toString());
			edate = df.parse(eventEDT.toString());
			SimpleDateFormat mySdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
		    strSDT = mySdf.format(sdate);
		    strEDT = mySdf.format(edate);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
	   String s= URLEncoder.encode(results.getDescription(), "UTF-8");
	   int n = 1900; //max length of description in characters
	   String upToNCharacters = s.substring(0, Math.min(s.length(), n));
       String addToCalUrl = "http://www.google.com/calendar/event?action=TEMPLATE"
        		+"&text="+URLEncoder.encode(results.getSummary(), "UTF-8")
        		+"&details="+upToNCharacters
        		+"&dates="+strSDT	//"20151112T190000"
        		+"/"+strEDT			//"20151112T200000"
        		+"&location="+results.getLocation() ;
			  
        System.out.println("addToCalUrl:: " + addToCalUrl);
        request.setAttribute("addToCalUrl",addToCalUrl);
        
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