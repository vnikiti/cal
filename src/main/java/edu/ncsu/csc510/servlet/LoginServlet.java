package edu.ncsu.csc510.servlet;

import javax.servlet.annotation.WebServlet;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/login" })
public class LoginServlet extends OAuth2Servlet {

	/**
	 * 
	 */
    private static final long serialVersionUID = 6584124724401465347L;
	
}
