package com.csci548.demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class showDetails
 */
public class showDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public showDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			//System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
			return;
		}
	 
		//System.out.println("MySQL JDBC Driver Registered!");
		Connection connection = null;
	 
		try {
			connection = DriverManager
			.getConnection("jdbc:mysql://localhost:3306/test","root", "");
	 
		} catch (SQLException e) {
			//System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	 
		if (connection != null) {
			//System.out.println("You made it, take control your database now!");
		} else {
			//System.out.println("Failed to make connection!");
		}
		try
		{
			String image = request.getParameter("image");
			response.setContentType("text/HTML");
			response.setCharacterEncoding("UTF-8");
			Statement stmt = connection.createStatement() ;
			String q = "select Museum_Name,Title,Artist,Inscription,Technique,Dimension,Place,Date,Description from museum where Image_URL='"+image+"' LIMIT 10";
			ResultSet rs = stmt.executeQuery(q) ;
			String mname=null,title=null,artist=null,inscription=null,technique=null,dimension=null,place=null,date=null,description=null;
			while(rs.next())
			{
				int numColumns = rs.getMetaData().getColumnCount();
	            for ( int i = 1 ; i <= numColumns ; i++ ) {
	            	mname=rs.getObject(i).toString();
	            	title=rs.getObject(++i).toString();
	            	artist=rs.getObject(++i).toString();
	            	inscription=rs.getObject(++i).toString();
	            	technique=rs.getObject(++i).toString();
	            	dimension=rs.getObject(++i).toString();
	            	place=rs.getObject(++i).toString();
	            	date=rs.getObject(++i).toString();
	            	description=rs.getObject(++i).toString();
	            }
			}
			out.println("<!DOCTYPE html>");
			out.println("	<html lang='en'>");
			out.println("	  <head>");			   
			out.println("	    <meta http-equiv='X-UA-Compatible' content='IE=edge'>");
			out.println("	    <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
			out.println("    <meta name='description' content=''>");
			out.println("   <meta name='author' content=''>");
			out.println("   <meta http-equiv='Content-Type' content='text/html; charset=ISO-8859-1'>");
			out.println("	    <link rel='shortcut icon' href='docs-assets/ico/favicon.png'>");
			out.println("		     <title>PanoramArt</title>");
			out.println("	    <!-- Bootstrap core CSS -->");
			out.println("	    <link href='dist/css/bootstrap.css' rel='stylesheet'>");
			out.println("			    <!-- Custom styles for this template -->");
			out.println("		    <link href='navbar-fixed-top2.css' rel='stylesheet'>");
			out.println("<script src='https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js'></script>");
			out.println("<script src='https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js'></script>");
			out.println("</head>");
			out.println("<body>");
			out.println("<!-- Fixed navbar -->");
			out.println("<div class='navbar navbar-default navbar-fixed-top' role='navigation'>");
			out.println("<div class='container'>");
			out.println("<div class='navbar-header'>");
			out.println("<button type='button' class='navbar-toggle' data-toggle='collapse' data-target='.navbar-collapse'>");
			out.println("<span class='sr-only'>Toggle navigation</span>");
			out.println("<span class='icon-bar'></span>");
			out.println("<span class='icon-bar'></span>");
			out.println("<span class='icon-bar'></span>");
			out.println("</button>");
			out.println("<a class='navbar-brand' href='#'>Artwork Details</a>");
			out.println("</div>");
			out.println("</div>");
			out.println("</div>");
			out.println("<div class='container'>");
			out.println("<div class ='row'>");
			out.println("<div class = 'col-sm-3' >");
			out.println("<img id='image_here' src="+image+" />");
			out.println("</div>");
			out.println("<div class = 'col-sm-9'>");
			out.println("<p><b>Title: </b>"+title+"</p>");
			if(!inscription.equals("None"))
			out.println("<p><b>Inscription:</b> "+inscription+"</p>");
			if(!technique.equals("None"))
			out.println("<p><b>Technique:</b> "+technique+"</p>");
			if(!dimension.equals("None"))
			out.println("<p><b>Dimension:</b> "+dimension+"</p>");
			if(!place.equals("None"))
			out.println("<p><b>Place:</b> "+place+"</p>");
			if(!date.equals("None"))
			out.println("<p><b>Date:</b> "+date+"</p>");
			if(!description.equals("None"))
			out.println("<p><b>Description:</b> "+description+"</p>");
			out.println("<p><b>Artist:</b> "+artist+"</p>");
			out.println("<p><b>Museum Name:</b> "+mname+"</p>");
			out.println("</div>");
			out.println("</div>");
			out.println("<div class = 'footer'>");
			out.println("<p>&copy; Shaarif Zia Hardik Desai 2013</p>");
			out.println("</div>");
			out.println("</div> <!-- /container -->");
			out.println("<!-- Bootstrap core JavaScript");
			out.println("================================================== -->");
			out.println("<!-- Placed at the end of the document so the pages load faster -->");
			out.println("<script src='https://code.jquery.com/jquery-1.10.2.min.js'></script>");
			out.println("<script src='dist/js/bootstrap.min.js'></script>");	      
			out.println("</body>");
			out.println("</html>");

		}
		catch(Exception e)
		{
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
