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
 * Servlet implementation class showTechnique
 */
public class showTechnique extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public showTechnique() {
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
			String technique = request.getParameter("technique");
			response.setContentType("text/HTML");
			response.setCharacterEncoding("UTF-8");
			Statement stmt = connection.createStatement() ;
			String q = "select distinct Museum_Name, Museum_city from museum where Technique like '%"+technique+"%'";
			String query2="select Title,Image_URL,Artist from museum where Technique like '%"+technique+"%' LIMIT 10";
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
			request.setAttribute("Technique", technique);
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
	            	object.put("Artist",rs1.getObject(++i).toString());
	            	jsonArray.add(object);
	            }
			}
			objects.put("Artworks", jsonArray);
			//System.out.println(objects);
			request.setAttribute("Results", objects.toString());
			RequestDispatcher RequetsDispatcherObj =request.getRequestDispatcher("techniques.jsp");
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
