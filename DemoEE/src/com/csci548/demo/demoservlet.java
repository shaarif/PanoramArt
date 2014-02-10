package com.csci548.demo;

import java.io.IOException;
import java.io.PrintWriter;
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
 * Servlet implementation class demoservlet
 */
public class demoservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public demoservlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<body>");
		System.out.println("-------- MySQL JDBC Connection Testing ------------");
		 
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
			return;
		}
	 
		System.out.println("MySQL JDBC Driver Registered!");
		Connection connection = null;
	 
		try {
			connection = DriverManager
			.getConnection("jdbc:mysql://localhost:3306/test","root", "");
	 
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	 
		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
		String[] stopWrds={"i", "a","and", "about","an","are","as","at","be","by","com","for","from","how","in", "is","it",
                "of","on","or","that","the","this","to","was","what","when","where","who","will","with","untitled"};
		try
		{
		String query=request.getParameter("query");
		System.out.println(query);
		String[] q=query.split(" ");
		String quer="";
		String token="";
		String[] qry=new String[3];
		String query2="";
		if(q.length==1)
		{
		quer = "select distinct Museum_Name, Museum_city from museum where Title like '%"+query+"%' or Keywords like '%"+query+"%';"; 
		query2= "select Title,Image_URL,Artist,Technique from museum where Title like '%"+query+"%' or Keywords like '%"+query+"%' LIMIT 10;" ; 
		}
		else
		{
			
			for(String stop:stopWrds)
			{
				for(String qtoken:q)
				{
					if(qtoken.equals(stop))
					{
						token=stop;
						break;
					}
				}
			}
			int i=0;
			for(String qtoken:q)
			{
				if(!qtoken.equals(token))
				{
					qry[i]=qtoken;
					i++;
				}
			}
			quer="select Museum_Name, Museum_city from museum where ((Title like '%"+qry[0]+"%' and Title like '%"+qry[1]+"%') or (Keywords like '%"+qry[0]+"%' and Keywords like '%"+qry[1]+"%'))LIMIT 10;";
			query2= "select Title,Image_URL,Artist,Technique from museum where ((Title like '%"+qry[0]+"%' and Title like '%"+qry[1]+"%') or (Keywords like '%"+qry[0]+"%' and Keywords like '%"+qry[1]+"%'))LIMIT 10;";
		}
		Statement stmt = connection.createStatement() ;
		ResultSet rs = stmt.executeQuery(quer) ;
		ArrayList<String> museumList=new ArrayList<String>();
		ArrayList<String> cityList=new ArrayList<String>();
		while(rs.next())
		{
			int numColumns = rs.getMetaData().getColumnCount();
            for ( int i = 1 ; i <= numColumns ; i++ ) {
            	museumList.add(rs.getObject(i).toString());
            	cityList.add(rs.getObject(++i).toString());
            }
		}
		request.setAttribute("Museum", museumList);  //museum name
		request.setAttribute("City", cityList);      //museum address
		request.setAttribute("Query", query); 
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
            	object.put("Technique",rs1.getObject(++i).toString());
            	jsonArray.add(object);
            }
		}
		objects.put("Artworks", jsonArray);
		request.setAttribute("Results", objects.toString());
		//response.getWriter().write(objects.toString());
		RequestDispatcher RequetsDispatcherObj =request.getRequestDispatcher("page2.jsp");
		RequetsDispatcherObj.include(request, response);
		} catch(Exception e)
		{
			System.out.println(e);
		}	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
