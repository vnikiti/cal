package edu.ncsu.csc510.servlet;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfoplus;

/**
 * Servlet implementation class OAuth2Callback
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/oauth2callback" })
public class OAuth2Callback extends OAuth2Servlet {
	

	/** Global instance of the HTTP transport. */
	private static HttpTransport httpTransport;

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	
	/**
	 * 
	 */
    private static final long serialVersionUID = 2585233320218049109L;

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
}