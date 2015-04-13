package edu.ncsu.csc510.servlet;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.jdo.JdoDataStoreFactory;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeCallbackServlet;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow.Builder;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.Oauth2Scopes;
import com.google.api.services.oauth2.model.Userinfoplus;

import edu.ncsu.csc510.dao.PMF;

/**
 * Servlet implementation class OAuth2Callback
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/oauth2callback" })
public class OAuth2Callback extends AbstractAuthorizationCodeCallbackServlet {
	private static final long serialVersionUID = 2585233320218049109L;
	
	private static final String CLIENT_ID = "794973057266-titj4dapr8hoq2lchtc17b67tbcqhgjr.apps.googleusercontent.com";
	private static final String CLIENT_SECRET = "F-5GlFlph4XprFbhdeI1NHb2";
	
	/** Global instance of the HTTP transport. */
	private static HttpTransport httpTransport;

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	
	//private static final PersistenceManager pm = PMF.get().getPersistenceManager();
	//private static final JdoDataStoreFactory dataStoreFactory = new JdoDataStoreFactory(pm.getPersistenceManagerFactory());

	@Override
	protected void onSuccess(HttpServletRequest req, HttpServletResponse resp, Credential credential)
	        throws ServletException, IOException {
		
		try {
	        httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        } catch (GeneralSecurityException e) {
	        e.printStackTrace();
        }
		
		Oauth2 oauth2 = new Oauth2.Builder(httpTransport, JSON_FACTORY, credential).build();
		Userinfoplus userinfo = oauth2.userinfo().get().execute();
		
		req.getSession().setAttribute("userinfo", userinfo);
				
		resp.sendRedirect("/");
	}

	@Override
	protected void onError(HttpServletRequest req, HttpServletResponse resp, AuthorizationCodeResponseUrl errorResponse)
	        throws ServletException, IOException {
		// handle error
	}

	@Override
	protected String getRedirectUri(HttpServletRequest req) throws ServletException, IOException {
		//GenericUrl url = new GenericUrl(req.getRequestURL().toString());
		//url.setRawPath("/oauth2callback");
		//return url.build();
		return "http://cal-csc510.rhcloud.com/oauth2callback";
	}

	@Override
	protected AuthorizationCodeFlow initializeFlow() throws IOException {
		
		try {
	        httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        } catch (GeneralSecurityException e) {
	        e.printStackTrace();
        }
		
		
		List<String> scopes = new ArrayList<String>();
		scopes.add(CalendarScopes.CALENDAR);
		scopes.add(Oauth2Scopes.USERINFO_EMAIL);
		
		Builder builder = new GoogleAuthorizationCodeFlow.Builder(httpTransport, 
				JSON_FACTORY, CLIENT_ID, CLIENT_SECRET, scopes);
		
		//builder.setDataStoreFactory(dataStoreFactory);
		builder.setAccessType("offline");
		AuthorizationCodeFlow authFlow = builder.build();
				
		return authFlow;
	}

	@Override
	protected String getUserId(HttpServletRequest req) throws ServletException, IOException {
		//req.getUserPrincipal().toString();
		String sessionId = req.getSession().getId();
		System.out.println("oauth callback userId: " + sessionId);
		return sessionId;
	}
}