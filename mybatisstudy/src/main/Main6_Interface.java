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

import mapper.StudentMapper2;

public class Main6_Interface {
	private final static SqlSessionFactory sqlMap;
	private final static Class<StudentMapper2> cls = StudentMapper2.class;
	private static Map<String, Object> map = new HashMap<>();
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
		List<Student> list = session.getMapper(cls).select(map);
		for(Student s : list) {
			System.out.println(s);
		}
		
		System.out.println("===============================================================================");
		System.out.println("1학년 학생 정보 조회하기");
		map.put("grade", 1);
		list = session.getMapper(cls).select(map);
		for(Student s : list) {
			System.out.println(s);
		}
		
		System.out.println("===============================================================================");
		System.out.println("키가 175이상인 학생 정보 조회하기");
		map.clear();
		map.put("height", 175);
		list = session.getMapper(cls).select(map);
		for(Student s : list) {
			System.out.println(s);
		}
		
		System.out.println("===============================================================================");
		System.out.println("몸무게가 60이하인 학생 정보 조회하기");
		map.clear();
		map.put("weight", 60);
		list = session.getMapper(cls).select(map);
		for(Student s : list) {
			System.out.println(s);
		}
		
		System.out.println("모든 학생 정보 조회하기");
		list = session.getMapper(cls).select2(map);
		for(Student s : list) {
			System.out.println(s);
		}
		
		System.out.println("===============================================================================");
		System.out.println("1학년 학생 정보 조회하기");
		map.put("grade", 1);
		list = session.getMapper(cls).select2(map);
		for(Student s : list) {
			System.out.println(s);
		}
		
		System.out.println("===============================================================================");
		System.out.println("키가 175이상인 학생 정보 조회하기");
		map.clear();
		map.put("height", 175);
		list = session.getMapper(cls).select2(map);
		for(Student s : list) {
			System.out.println(s);
		}
		
		System.out.println("===============================================================================");
		System.out.println("1학년 학생중 키가 175 이상인 학생의 정보 조회하기");
		map.clear();
		map.put("grade", 1);
		map.put("height", 175);
		list = session.getMapper(cls).select2(map);
		for(Student s : list) {
			System.out.println(s);
		}
		
	}

}
