package fr.mycommerce.view.catalog;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/imageServlet")
public class imageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long userProfileId = Long.valueOf(request.getParameter("id"));

      
      //  statement.setLong(1, userProfileId);

        response.setContentType(getServletContext().getMimeType("imageFileName"));
        
        // resultSet.getInt("imageContentLength")
        response.setContentLength(123);
        response.setHeader("Content-Disposition", "inline;filename=\"" + "imageFileName" + "\"");

        //resultSet.getBinaryStream("image")
       
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
      
    }

}