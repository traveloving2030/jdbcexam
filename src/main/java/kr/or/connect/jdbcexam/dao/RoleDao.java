package kr.or.connect.jdbcexam.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.or.connect.jdbcexam.dto.Role;

public class RoleDao {
	private static String dburl = "jdbc:mysql://localhost:3306/connectdb";
	private static String dbUser = "connectuser";
	private static String dbpasswd = "connect123!@#";
	
	public Role getRole(Integer roleId) { // role 중에서 하나만 가져온다
		Role role=null;
		Connection conn =null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);
			String sql = "SELECT role_id, description FROM role WHERE role_id=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, roleId); // ?에 해당하는 값 
			rs=ps.executeQuery();
			
			if(rs.next()) { //결과값이 있다면 첫번째 레코드로 커서를 이동시키고 true를 return해줘요, 만약 없다면 false return 해줘요
				int id = rs.getInt("role_id");
				String description = rs.getString(2);
				role = new Role(id, description);
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally { //일단 try catch 하든말든 위에 객체들 닫아주자!
			if(rs!=null) { //Connection 또는 PreparedStatement에서 예외발생하면 rs는 null포인터 exception
				try {
					rs.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(ps!=null) { 
				try {
					ps.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn!=null) { 
				try {
					conn.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return role;
	}
}
