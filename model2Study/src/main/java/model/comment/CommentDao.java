package model.comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import model.MybatisConnection;
import model.mapper.CommentMapper;

public class CommentDao {
	private Class<CommentMapper> cls = CommentMapper.class;
	private Map<String, Object> map = new HashMap<>();
	
	public int maxseq(int num) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			return session.getMapper(cls).maxseq(num);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MybatisConnection.close(session);
		}
		return 0;
	}

	public boolean insert(Comment comm) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			return session.getMapper(cls).insert(comm) > 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MybatisConnection.close(session);
		}
		return false;
	}

	public List<Comment> list(int num) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			return session.getMapper(cls).list(num);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MybatisConnection.close(session);
		}
		return null;
	}

	public boolean delete(int num, int seq) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			return session.getMapper(cls).delete(num,seq) > 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MybatisConnection.close(session);
		}
		return false;
	}
}
