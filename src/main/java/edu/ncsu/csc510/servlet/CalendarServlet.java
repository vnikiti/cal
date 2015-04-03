package edu.ncsu.csc510.servlet;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.extensions.jdo.JdoDataStoreFactory;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeServlet;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.gson.Gson;

import edu.ncsu.csc510.dao.PMF;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/**
 * Servlet implementation class CalendarServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/calendar" })
public class CalendarServlet extends AbstractAuthorizationCodeServlet {
	private static final long serialVersionUID = 1L;
	
	private static final PersistenceManager pm = PMF.get().getPersistenceManager();
	private static final JdoDataStoreFactory dataStoreFactory = new JdoDataStoreFactory(pm.getPersistenceManagerFactory());
	
	/**
	 * Default constructor.
	 */
	public CalendarServlet() {
		// TODO Auto-generated constructor stub

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// do stuff
		List<String> list = new ArrayList<String>();
		list.add("item1");
		list.add("item2");
		list.add("item3");
		String json = new Gson().toJson(list);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}

	@Override
	protected String getRedirectUri(HttpServletRequest req) throws ServletException, IOException {
		GenericUrl url = new GenericUrl(req.getRequestURL().toString());
		url.setRawPath("/oauth2callback");
		return url.build();
	}

	@Override
	protected AuthorizationCodeFlow initializeFlow() throws IOException {
		return new GoogleAuthorizationCodeFlow.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(),
		        "[[ENTER YOUR CLIENT ID]]", "[[ENTER YOUR CLIENT SECRET]]",
		        Collections.singleton(CalendarScopes.CALENDAR)).setDataStoreFactory(dataStoreFactory)
		        .setAccessType("offline").build();
	}

	@Override
	protected String getUserId(HttpServletRequest req) throws ServletException, IOException {
		return null;
		// TODO: return user ID
	}
}
