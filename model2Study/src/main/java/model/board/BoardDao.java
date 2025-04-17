package model.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import model.MybatisConnection;
import model.mapper.BoardMapper;

public class BoardDao {
	private Class<BoardMapper> cls = BoardMapper.class;

	private Map<String, Object> map = new HashMap<>();

	public int maxnum() {
		SqlSession session = MybatisConnection.getConnection();
		try {
			return session.getMapper(cls).maxnum();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MybatisConnection.close(session);
		}
		return 0;
	}

	public boolean insert(Board board) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			return session.getMapper(cls).insert(board) > 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MybatisConnection.close(session);
		}
		return false;
	}

	public int boardCount(String boardid) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			return session.getMapper(cls).count(boardid);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MybatisConnection.close(session);
		}
		return 0;
	}

	public List<Board> list(String boardid, int pageNum, int limit) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			map.clear();
			map.put("boardid", boardid); // 게시판 종류
			map.put("start", (pageNum - 1) * limit); 
			/*
			 	 pageNum	  start
			 		1			0
			 		2			10
			 */
			map.put("limit", limit);
			return session.getMapper(cls).list(map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MybatisConnection.close(session);
		}
		return null;
	}

	public Board selectOne(int num) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			return session.getMapper(cls).selectOne(num);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MybatisConnection.close(session);
		}
		return null;
	}

	public boolean readcntAdd(int num) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			return session.getMapper(cls).readcntAdd(num) > 0;
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			MybatisConnection.close(session);
		}
		return false;
	}

}
