package main;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Main2_Student {
	private final static SqlSessionFactory sqlMap;
	static { // static 초기화 블럭
		// 입력스트림
		InputStream input = null;
		try {
			input = Resources.getResourceAsStream("mapper/mybatis-config.xml");
		}catch(IOException e) {
			e.printStackTrace();
		}
		sqlMap = new SqlSessionFactoryBuilder().build(input);
	}
	public static void main(String[] args) {
		// Connection 객체를 이용하여 mabatis 와 db를 연결
		SqlSession session = sqlMap.openSession();
		System.out.println("학생 테이블에 레코드 추가하기");
		Student st = new Student(); //db의 student 테이블의 컬럼과 같은 프로퍼치를 가지는 클래스
		st.setStudno(1002);
		st.setName("김삿갓");
		st.setGrade(1);
		st.setId("kimsg2");
		st.setJumin("9901031234567");
		st.setMajor1(101);
		int cnt = session.insert("student.insert",st);
		// insert(namespace.id값,파라미터 값)
		// 파라미터값을 이용하여 db에 새로운 레코드를 추가. 추가된 레코드 수를 리턴
		System.out.println("student 레코드 추가 : " + cnt);
		Student stResult = session.selectOne("student.selectNo",st.getStudno());
		System.out.println(stResult);
		// 1002번 학생의 학년을 2학년, 몸무게 :80, 키 :170 지도교수:1001로 수정하기
		// sql 키 : student.update
		st.setStudno(1002);
		st.setGrade(2);
		st.setHeight(170);
		st.setWeight(80);
		st.setProfno(1001);
		cnt = session.update("student.update",st);
		stResult = session.selectOne("student.selectNo",st.getStudno());
		System.err.println("수정된 레코드의 건수 : " + cnt);
		System.out.println(stResult);
		// 1002번 학생 정보 삭제하기
		// student.delte
		cnt = session.delete("student.delete",st.getStudno());
		stResult = session.selectOne("student.selectNo",st.getStudno());
		System.out.println("삭제된 레코드의 건수 : " + cnt);
//		session.commit(); // db에 자료추가
	}

}
