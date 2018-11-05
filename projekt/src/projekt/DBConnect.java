package projekt;

import java.sql.*;

public class DBConnect 
{

	private Connection con;
	private Statement st;
	private ResultSet rs;
	
	public DBConnect()
	{try
	{
		Class.forName("com.mysql.jdbc.Driver");
		
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");
		st = con.createStatement();
		
	}catch(Exception ex) {
		System.out.println("B³¹d Po³¹czenia"+ ex);
	}
		
	}

}
