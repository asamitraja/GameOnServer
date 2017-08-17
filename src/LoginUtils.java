import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginUtils {
	
	
	private static Connection getCon(){
		Connection con=null;
		try{
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/GameOn","root","");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		System.out.println(con);
		return con;
	}
	
	
	public static void registerUser(String fullname,String username,String password  ){		
		
		try {
			PreparedStatement ps = null;
			Connection conn = getCon();
			if(conn !=null)
			{
				
				String sql = "INSERT INTO user_data(full_name,username,password)"+ 
								" VALUES(?,?,?)"+";";
				ps = conn.prepareStatement(sql);
				
				ps.setString(1,fullname);
				ps.setString(2,username);
				ps.setString(3,password);
				
				ps.executeUpdate();
				
				conn.close();
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static boolean isUser(String user,String pass){
		String password=null;
		Connection conn = getCon();
		
		try {
			if(conn!=null)
			{
				String sql = "SELECT password FROM user_data WHERE username = ? ";
				PreparedStatement ps = conn.prepareStatement(sql);
				
				ps.setString(1, user);
				ResultSet rs = ps.executeQuery();
				
				if(rs.next()) {		
					password = rs.getString("password");
				}
				if(pass.equals(password)){
					return true; 
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static String getFullName(String uname)
	{
		String fullname = null;
		try {

			Connection conn = getCon();
			if(conn!=null)
			{
				String sql = "SELECT full_name FROM user_data WHERE username = ?";
				PreparedStatement ps = conn.prepareStatement(sql);
				
				ps.setString(1, uname);
				
				ResultSet rs = ps.executeQuery();
				if(rs.next()){
					fullname = rs.getString("full_name");
				}
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return fullname;
	}
	
}

