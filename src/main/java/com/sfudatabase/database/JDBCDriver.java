package main.java.com.sfudatabase.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCDriver {

    private static Connection con;
	private static String space = "                                           ";
    PreparedStatement pstmt = null;
	ResultSet rs;
	String sSQL= "select * from helpdesk";	//the table was created by helpdesk
	String temp="";
		
	String sUsername = "s_username";
	String sPassphrase = "******";
	// ^^^ modify these 2 lines before compiling this program
	// please replace the username with your SFU Comuting ID
	// please get the passphrase from table 'helpdesk' of your course database
		
    String connectionUrl = "jdbc:sqlserver://cypress.csil.sfu.ca;" + "user = " + sUsername + ";" + "password =" + sPassphrase;
			        
        //System.out.println("\n connectionUrl = " + connectionUrl + "\n\n");
		
		/*    
		try
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		}catch(ClassNotFoundException ce)
			{
				System.out.println("\n\nNo JDBC Driver for SQL Server; exit now.\n\n");
				return;
			}
		*/	
		
	try {
		con = DriverManager.getConnection ( connectionUrl );
	} catch ( SQLException se ) {
		System.out.println ( "\n\nFail to connect to CSIL SQL Server; exit now.\n\n" );
		return;
	}
		
	try {
		pstmt = con.prepareStatement(sSQL);
		rs = pstmt.executeQuery();
			
		System.out.println("\nThe table 'helpdesk' contains:\n\n");
			
		while (rs.next()) {
			temp= rs.getString("username");	//the table has a field 'username'
			System.out.println(temp);
		}
		rs.close();
		System.out.println("\nSuccessfully connected to CSIL SQL Server!\n\n");
	} catch (SQLException se) {
		System.out.println("\nSQL Exception occurred, the state : "+ se.getSQLState()+"\nMessage:\n"+se.getMessage()+"\n");
		return;
	}
}
