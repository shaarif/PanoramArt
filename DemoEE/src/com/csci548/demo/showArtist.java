package com.csci548.demo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class showArtist
 */
public class showArtist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public showArtist() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
			String aname = request.getParameter("aname");
			response.setContentType("text/HTML");
			response.setCharacterEncoding("UTF-8");
			Statement stmt = connection.createStatement() ;
			String q = "select distinct Museum_Name, Museum_city from museum where Artist='"+aname+"'";
			String query2= "select Title,Image_URL,Technique from museum where Artist='"+aname+"' LIMIT 10";
			ResultSet rs = stmt.executeQuery(q) ;
			ArrayList<String> museumList=new ArrayList<String>();
			ArrayList<String> addressList=new ArrayList<String>();
			while(rs.next())
			{
				int numColumns = rs.getMetaData().getColumnCount();
	            for ( int i = 1 ; i <= numColumns ; i++ ) {
	            	museumList.add(rs.getObject(i).toString());
	            	addressList.add(rs.getObject(++i).toString());
	            }
			}
			request.setAttribute("Artist", aname);
			request.setAttribute("Museum", museumList);  //museum name
			request.setAttribute("City", addressList);      //museum address
			Statement stmt1 = connection.createStatement() ;
			ResultSet rs1 = stmt1.executeQuery(query2) ;
			JSONObject objects=new JSONObject();
			JSONArray jsonArray = new JSONArray();
			while(rs1.next())
			{
				int numColumns = rs1.getMetaData().getColumnCount();
	            for ( int i = 1 ; i <= numColumns ; i++ ) {
	            	JSONObject object=new JSONObject();
	            	object.put("Title",rs1.getObject(i).toString());
	            	object.put("Image",rs1.getObject(++i).toString());
	            	object.put("Technique",rs1.getObject(++i).toString());
	            	jsonArray.add(object);
	            }
			}
			objects.put("Artworks", jsonArray);
			//System.out.println(objects);
			request.setAttribute("Results", objects.toString());
			RequestDispatcher RequetsDispatcherObj =request.getRequestDispatcher("artists.jsp");
			RequetsDispatcherObj.include(request, response);
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
