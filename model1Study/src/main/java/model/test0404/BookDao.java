package model.test0404;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.DBConnection;

public class BookDao {
	public boolean insert(Book book) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		String sql ="insert into book(writer,title,content)"
				+ " values(?,?,?)";
		try {
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, book.getWriter());
			pstmt.setString(2, book.getTitle());
			pstmt.setString(3, book.getContent());
			if(pstmt.executeUpdate() > 0) return true;
			else return false;
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBConnection.close(conn, pstmt, null);
		}
		return false;
	}
}
