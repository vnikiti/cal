package edu.ncsu.csc510.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.services.oauth2.model.Userinfoplus;
import com.google.gson.Gson;

/**
 * Servlet implementation class CalendarServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/user" })
public class UserServlet extends HttpServlet {
	
	/**
	 * 
	 */
    private static final long serialVersionUID = -7627044970325225882L;

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
		Userinfoplus userinfo = (Userinfoplus) request.getSession().getAttribute("userinfo");

		String json = new Gson().toJson(userinfo);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}
}
