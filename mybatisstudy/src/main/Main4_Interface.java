package main;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import mapper.StudentMapper;

public class Main4_Interface {
	private final static SqlSessionFactory sqlMap;
	// cls : Mapper의 클래스 정보
	private final static Class<StudentMapper> cls = StudentMapper.class;
	static { 
		InputStream input = null;
		try {
			input = Resources.getResourceAsStream("mapper/mybatis-config.xml");
		}catch(IOException e) {
			e.printStackTrace();
		}
		sqlMap = new SqlSessionFactoryBuilder().build(input);
	}
	public static void main(String[] args) {
		SqlSession session = sqlMap.openSession();
		System.out.println("모든 학생 정보 조회하기");
		List<Student> list = session.getMapper(cls).select();
		for(Student s : list) {
			System.out.println(s);
		}
		
		System.out.println("====================================================================================================================");
		System.out.println("1학년 학생 정보 조회하기");
		list = session.getMapper(cls).selectGrade(1);
		for(Student s : list) {
			System.out.println(s);
		}
		
		System.out.println("====================================================================================================================");
		System.out.println("240111 학생 정보 조회하기");
		Student st = session.getMapper(cls).selectStudno("240111");
		System.out.println(st);
		
		System.out.println("====================================================================================================================");
		list = session.selectList("mapper.StudentMapper.select");
		System.out.println("인터페이스형태를 xml 형식으로 호출하기");
		for(Student s : list) {
			System.out.println(s);
		}
		System.out.println("====================================================================================================================");
		System.out.println("진영훈 학생 정보 조회하기");
		list = session.getMapper(cls).selectName("진영훈");
		for(Student s : list) {
			System.out.println(s);
		}
		
		System.out.println("====================================================================================================================");
		System.out.println("1학년 학생 중 키가 180이상인 학생의 정보 조회하기");
		Map<String, Object> map = new HashMap<>();
		map.put("grade", 1);
		map.put("height", 180);
		list = session.getMapper(cls).selecGradeHeight(map);
		for(Student s : list) {
			System.out.println(s);
		}
		System.out.println("====================================================================================================================");
		list = session.getMapper(cls).selecGradeHeight2(1,180);
		for(Student s : list) {
			System.out.println(s);
		}
		
	}

}
