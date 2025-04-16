package test0415;
/*
 * StudentMapper1.xml 파일을 이용하기
 * 1. 학생테이블의 등록된 레코드의 건수를 출력하기
 * 2. 학생테이블의 등록된 레코드의 정보를 출력하기
 * 3. 학생테이블의 등록된 레코드의 1학년 학생의 정보를 출력하기
 * 4. 학생테이블의 등록된 레코드의 성이 김씨인 학생의 정보를 출력하기
 * 5. 학생테이블의 등록된 레코드의 3학년 학생 중 주민번호 기준 여학생 정보를 출력하기
 */

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import main.Student;

public class Test1 {

	public static void main(String[] args) {
		SqlSessionFactory sqlMap = null;
		Reader reader = null;
		try {
			reader = Resources.getResourceAsReader("mapper/mybatis-config.xml");
			sqlMap = new SqlSessionFactoryBuilder().build(reader);
		}catch(IOException e) {
			e.printStackTrace();
		}
		// 1. 학생테이블의 등록된 레코드의 건수를 출력하기
		int x = 0;
		SqlSession session = sqlMap.openSession();
		x=(Integer)session.selectOne("student.count");
		System.out.println("학생테이블의 등록된 레코드의 건수 : " + x);
		
		// 2. 학생테이블의 등록된 레코드의 정보를 출력하기
		System.out.println("학생테이블의 등록된 레코드의 정보 조회하기");
		List<Student> list = session.selectList("student.list");
		for(Student s : list) {
			System.out.println(s);
		}
		
		//3. 학생테이블의 등록된 레코드의 1학년 학생의 정보를 출력하기
		System.out.println("학생테이블의 등록된 레코드의 1학년 학생의 정보 조회하기");
		list = session.selectList("student.selectgrade","1");
		for(Student s : list) {
			System.out.println(s);
		}
		
		//4. 학생테이블의 등록된 레코드의 성이 김씨인 학생의 정보를 출력하기
		System.out.println("학생테이블의 등록된 레코드의 성이 김씨인 학생의 정보 조회하기");
		list = session.selectList("student.selectname","김");
		for(Student s : list) {
			System.out.println(s);
		}
		
		//5. 학생테이블의 등록된 레코드의 3학년 학생 중 주민번호 기준 여학생 정보를 출력하기
		System.out.println("학생테이블의 등록된 레코드의 3학년 학생 중 주민번호 기준 여학생 정보 조회하기");
		Map<Object, String> map = new HashMap<>();
		map.put("grade", "3");
		map.put("jumin1","2");
		map.put("jumin2","4");
		list = session.selectList("student.selectjumin",map);
		for(Student s : list) {
			System.out.println(s);
		}
	}

}
