package com.csci548.demo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class showitems
 */
public class showitems extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public showitems() {
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
		String[] stopWrds={"i", "a","and", "about","an","are","as","at","be","by","com","for","from","how","in", "is","it",
                "of","on","or","that","the","this","to","was","what","when","where","who","will","with","untitled"};
		try
		{
			String mname = request.getParameter("mname");
			String query = request.getParameter("query");
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			Statement stmt = connection.createStatement() ;
			String[] q=query.split(" ");
			String quer="";
			String token="";
			String[] qry=new String[2];
			if(q.length==1)
			{
			quer = "select Title,Image_URL,Artist,Technique from museum where Museum_Name='"+mname+"' and (Title like '%"+query+"%' or Keywords like '%"+query+"%') LIMIT 10;" ; 
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
				quer="select Title,Image_URL,Artist,Technique from museum where Museum_Name='"+mname+"' and  ((Title like '%"+qry[0]+"%' and Title like '%"+qry[1]+"%') or (Keywords like '%"+qry[0]+"%' and Keywords like '%"+qry[1]+"%'))LIMIT 10;";
			}
			//System.out.println(quer);
			ResultSet rs = stmt.executeQuery(quer) ;
			JSONObject objects=new JSONObject();
			JSONArray jsonArray = new JSONArray();
			while(rs.next())
			{
				int numColumns = rs.getMetaData().getColumnCount();
	            for ( int i = 1 ; i <= numColumns ; i++ ) {
	            	JSONObject object=new JSONObject();
	            	object.put("Title",rs.getObject(i).toString());
	            	object.put("Image",rs.getObject(++i).toString());
	            	object.put("Artist",rs.getObject(++i).toString());
	            	object.put("Technique",rs.getObject(++i).toString());
	            	jsonArray.add(object);
	            }
			}
			objects.put("Artworks", jsonArray);
			//System.out.println(objects);
			response.getWriter().write(objects.toString());
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
