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
 * Servlet implementation class showReport
 */
public class showReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public showReport() {
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
	    PrintWriter out = response.getWriter();
	    response.setContentType("text/HTML");
	    out.println("<html>");
		Statement stmt = connection.createStatement() ;
		String q = "select count(*) as count from museum where Museum_Name like '%sfmoma%' and (Technique like '%draw%' or Technique like '%paint%' or Technique like '%photo%' or Technique like '%sculp%' or Technique like '%furniture%') group by Technique order by Technique;";
		ResultSet rs = stmt.executeQuery(q) ;
		int arch=0,draw=0,furn=0,painting=0,phot=0,sculpt=0;
		int[] counts=new int[6];
		int j=0;
		while(rs.next())
		{
			int numColumns = rs.getMetaData().getColumnCount();
            for ( int i = 1 ; i <= numColumns ; i++ ) {
            	counts[j]= Integer.parseInt(rs.getObject(i).toString());
            	j++;
            }
		}
		arch=counts[0];
		draw=counts[1];
		furn=counts[2];
		painting=counts[3];
		phot=counts[4];
		sculpt=counts[5];
		out.println("</html>");
            out.println("<head>");
            out.println("<script type='text/javascript' src='https://www.google.com/jsapi'></script>");
            out.println("<script type='text/javascript'>");
            out.println("google.load('visualization', '1', {packages:['corechart']});");
            out.println("google.setOnLoadCallback(drawChart);");
            out.println("function drawChart() {");
            out.println("var data = google.visualization.arrayToDataTable([ ['Technique Used', '# of Items'],");
            out.println("['Architectural Drawing',"+arch+"],");
            out.println("['Drawing',"+draw+"],");
            out.println("['Furniture',"+furn+"],");
            out.println("['Painitng',"+painting+"],");
            out.println("['Photograph',"+phot+"],");
            out.println("['Sculpture',"+sculpt+"]]);");
            out.println("var options = {   title: 'Statistical Analysis of Artwork Techniques'};");
            out.println("var chart = new google.visualization.PieChart(document.getElementById('piechart'));");
            out.println("chart.draw(data, options);");
            out.println("}");
            out.println("</script>");
            out.println("</head>");
            out.println("<body style='margin-left:0px;'>");
            out.println("<p id='text'><b><center>Tha San Francisco Museum of Modern Art</center></b></p>");
            out.println("<div id='piechart' style='width: 600px; height: 400px;'></div>");
            out.println("</body>");
		    out.println("</html>");
		
		}
		catch(Exception e)
		{
			//System.out.println(e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
