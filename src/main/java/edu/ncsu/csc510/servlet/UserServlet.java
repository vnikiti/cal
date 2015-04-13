package edu.ncsu.csc510.servlet;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfoplus;
import com.google.gson.Gson;

/**
 * Servlet implementation class CalendarServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/user" })
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/** Global instance of the HTTP transport. */
	private static HttpTransport httpTransport;

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	/**
	 * Default constructor.
	 */
	public UserServlet() {
		// TODO Auto-generated constructor stub

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Credential credential = (Credential) request.getSession().getAttribute("googleCredential");
		
		try {
	        httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        } catch (GeneralSecurityException e) {
	        e.printStackTrace();
        }
		

		Oauth2 oauth2 = new Oauth2.Builder(httpTransport, JSON_FACTORY, credential).build();
		Userinfoplus userinfo = oauth2.userinfo().get().execute();

		String json = new Gson().toJson(userinfo);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}
}
