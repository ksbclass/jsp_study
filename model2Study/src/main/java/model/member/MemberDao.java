package model.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import model.MybatisConnection;
import model.mapper.Membermapper;

public class MemberDao {
	private Class<Membermapper> cls = Membermapper.class;
	private Map<String, Object> map = new HashMap<>();
	public boolean insert(Member mem) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			// executeUpdate() : 실행 후 변경된 레코드의 갯수를 리턴 
			if(session.getMapper(cls).insert(mem) > 0) {
				return true;
			}
			else return false;
		}catch (Exception e) {
			e.printStackTrace();
		} finally { // 반드시 실행
			MybatisConnection.close(session);
		}
		return false; // 예외발생
	}
	public Member selectOne(String id) {
		// id : loginForm.jsp 에서 입력한 아이디값
		SqlSession session = MybatisConnection.getConnection();
		try {
			return session.getMapper(cls).selectOne(id);			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			MybatisConnection.close(session);
		}
		return null;
	}
	public List<Member> list() {
		SqlSession session = MybatisConnection.getConnection();
		try {
			return session.getMapper(cls).selectList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MybatisConnection.close(session);
		}
		return null;
	}
	public boolean update(Member mem) {
	    SqlSession session = MybatisConnection.getConnection();
	    try {
	    	return session.getMapper(cls).selectUpdate(mem) >0; 
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        MybatisConnection.close(session);
	    }
	    return false;
	}
	public boolean delete(String id) {
		SqlSession session = MybatisConnection.getConnection();
		    try {
		    	map.clear();
		    	map.put("id", id);
		    	return session.getMapper(cls).Delete(map) >0;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
		       MybatisConnection.close(session);
		    }
		    return false;
	}
	public String idSearch(String email, String tel) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			map.clear();
			map.put("email", email);
			map.put("tel",tel);
			return session.getMapper(cls).selectIdSearch(map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MybatisConnection.close(session);
	    }
		return null; // 레코드 찾기 실패 or 오류 발생시
	}
	public String pwSearch(String id, String email, String tel ) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			map.clear();
			map.put("id", id);
			map.put("email", email);
			map.put("tel",tel);
			return session.getMapper(cls).selectpwSearch(map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MybatisConnection.close(session);
	    }
		return null;
	}
	public boolean updatePass(String id, String pass) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			map.clear();
			map.put("id", id);
			map.put("pass", pass);
			return session.getMapper(cls).UpdatePass(map) > 0;
		}catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        MybatisConnection.close(session);
	    }
	    return false;
	}
	public List<Member> emailList(String[] ids) {
		// ids : 선택한 아이디 목록
		SqlSession session = MybatisConnection.getConnection();
		try {
			map.clear();
			map.put("ids", ids);
			return session.getMapper(cls).emailList(map);
		}catch (Exception e) {
	        e.printStackTrace();
		}finally {
			MybatisConnection.close(session);
	}
    return null;
	}
}
