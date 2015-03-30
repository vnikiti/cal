package edu.ncsu.csc510.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import javax.servlet.ServletException;

/**
 * Created by Mitchell Neville (mdnevill@ncsu.edu) on 3/30/15.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/index" })
public class IndexServlet extends HttpServlet {

    /**
     * Default constructor.
     */
    public IndexServlet() {
        // TODO Auto-generated constructor stub

    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");

        try{
            request.getRequestDispatcher("/index.jsp").include(request, response);
        } catch(ServletException e){
            e.printStackTrace();
        }
    }

}
