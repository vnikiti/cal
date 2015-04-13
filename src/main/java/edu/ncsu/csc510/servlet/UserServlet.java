package edu.ncsu.csc510.servlet;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.jdo.JdoDataStoreFactory;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeServlet;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow.Builder;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfoplus;
import com.google.gson.Gson;

import edu.ncsu.csc510.dao.PMF;

import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/**
 * Servlet implementation class CalendarServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/user" })
public class UserServlet {
	private static final long serialVersionUID = 1L;

	private static final String CLIENT_ID = "794973057266-titj4dapr8hoq2lchtc17b67tbcqhgjr.apps.googleusercontent.com";
	private static final String CLIENT_SECRET = "F-5GlFlph4XprFbhdeI1NHb2";

	/** Global instance of the HTTP transport. */
	private static HttpTransport httpTransport;

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	private static final PersistenceManager pm = PMF.get().getPersistenceManager();
	private static final JdoDataStoreFactory dataStoreFactory = new JdoDataStoreFactory(
	        pm.getPersistenceManagerFactory());

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
