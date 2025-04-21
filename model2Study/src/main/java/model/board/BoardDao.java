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

	public int boardCount(String boardid, String column, String find) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			map.clear();
			map.put("boardid",boardid);
			map.put("column",column);
			map.put("find",find);
			if(column != null) {
				String[] cols = column.split(",");
				switch(cols.length) { // 검색 내용이 존재
				case 3 : map.put("col3",cols[2].trim());
				case 2 : map.put("col2",cols[1].trim());
				case 1 : map.put("col1",cols[0].trim());
				}
			}
			return session.getMapper(cls).count(map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MybatisConnection.close(session);
		}
		return 0;
	}

	public List<Board> list(String boardid, int pageNum, int limit, String column, String find) {
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
			map.put("column",column);
			map.put("find",find);
			if(column != null) {
				String[] cols = column.split(",");
				switch(cols.length) {
				case 3 : map.put("col3",cols[2].trim());
				case 2 : map.put("col2",cols[1].trim());
				case 1 : map.put("col1",cols[0].trim());
				}
			}
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

	public void readcntAdd(int num) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			session.getMapper(cls).readcntAdd(num);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			MybatisConnection.close(session);
		}
	}


	public void grpStepAdd(int grp, int grpstep) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			session.getMapper(cls).grpStepAdd(grp,grpstep);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			MybatisConnection.close(session);
		}
		
	}

	public boolean update(Board b) {
		SqlSession session = MybatisConnection.getConnection();
	    try {
	    	return session.getMapper(cls).Update(b) >0; 
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        MybatisConnection.close(session);
	    }
	    return false;

	}

	public boolean delete(int num) {
		SqlSession session = MybatisConnection.getConnection();
	    try {
	    	return session.getMapper(cls).delete(num) >0; 
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        MybatisConnection.close(session);
	    }
	    return false;
	}

	public boolean check(int num) {
		SqlSession session = MybatisConnection.getConnection();
	    try {
	    	return session.getMapper(cls).check(num) > 0; 
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        MybatisConnection.close(session);
	    }
	    return false;
	}

}
