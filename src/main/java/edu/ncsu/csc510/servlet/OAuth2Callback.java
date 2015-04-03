package edu.ncsu.csc510.servlet;

import java.io.IOException;
import java.util.Collections;

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
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.CalendarScopes;

import edu.ncsu.csc510.dao.PMF;

/**
 * Servlet implementation class OAuth2Callback
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/oauth2callback" })
public class OAuth2Callback extends AbstractAuthorizationCodeCallbackServlet {
	private static final long serialVersionUID = 1L;
	
	private static final PersistenceManager pm = PMF.get().getPersistenceManager();
	private static final JdoDataStoreFactory dataStoreFactory = new JdoDataStoreFactory(pm.getPersistenceManagerFactory());

	@Override
	protected void onSuccess(HttpServletRequest req, HttpServletResponse resp, Credential credential)
	        throws ServletException, IOException {
		resp.sendRedirect("/");
	}

	@Override
	protected void onError(HttpServletRequest req, HttpServletResponse resp, AuthorizationCodeResponseUrl errorResponse)
	        throws ServletException, IOException {
		// handle error
	}

	@Override
	protected String getRedirectUri(HttpServletRequest req) throws ServletException, IOException {
		GenericUrl url = new GenericUrl(req.getRequestURL().toString());
		url.setRawPath("/oauth2callback");
		return url.build();
	}

	@Override
	  protected AuthorizationCodeFlow initializeFlow() throws IOException {
	    return new GoogleAuthorizationCodeFlow.Builder(
	        new NetHttpTransport(), JacksonFactory.getDefaultInstance(),
	        "[[ENTER YOUR CLIENT ID]]", "[[ENTER YOUR CLIENT SECRET]]",
	        Collections.singleton(CalendarScopes.CALENDAR))
	    	.setDataStoreFactory(dataStoreFactory).setAccessType("offline").build();
	  }

	@Override
	protected String getUserId(HttpServletRequest req) throws ServletException, IOException {
		// TODO: return user ID
		return null;
	}
}