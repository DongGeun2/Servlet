package kr.or.bit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import kr.or.bit.dto.Mvcregister;
import kr.or.bit.utils.ConnectionHelper;

public class registerdao {
		
	// CRUD
	public int writeOk(Mvcregister m) {
		
	
		Connection conn = null;
		PreparedStatement pstmt = null;
		int resultrow = 0;
		
		String sql = "insert into mvcregister(id,pwd,email) values(?,?,?)";
		System.out.println(m.toString());
		try {
			
			conn = ConnectionHelper.getConnection("oracle");
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, m.getId());
			pstmt.setString(2, m.getPwd());
			pstmt.setString(3, m.getEmail());
			
			resultrow = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionHelper.close(pstmt);
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return resultrow;
		
		
	}
}
