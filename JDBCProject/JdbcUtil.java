package JDBCProject;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcUtil {
	
	static{
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}//end of static
	
	public static Connection getConnect(){
		Connection con= null;
		try{
			con = DriverManager.getConnection("Jdbc:oracle:thin:@localhost:1521:xe","java","java");
			con.setAutoCommit(false);
			System.out.println("¡ÚConnection Success");
		}catch(Exception e){
			e.printStackTrace();
		}
		return con;
	}//end of getConnect
}
