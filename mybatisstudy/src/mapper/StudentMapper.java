package mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import main.Student;

/*
  인터페이스 방식으로 Mapper 사용하기
  1. mybatis-congig.xml 의 mapper에 package 로 설정
  2. namespace : mapper.StudentMapper. 인터페이스의 전체 이름이 namespace임
  3. 주의 사항 : 메서드의 이름이 sql문장의 key 값임 => 같은 이름의 메서드 허용안함
 	=> Mapper 인터페이스는 오버로딩 불가 
 */
public interface StudentMapper {
	
	@Select("select * from student")
	List<Student> select();
	
	@Select("select * from student where grade = #{value}")
	List<Student> selectGrade(int i);
	
	@Select("select * from student where studno = #{value}")
	Student selectStudno(String string);

	@Select("select * from student where name = #{value}")
	List<Student> selectName(String string);
	
	@Select("select * from student where grade = #{grade} and height >= #{height}")
	List<Student> selecGradeHeight(Map<String, Object> map);

	// @Param("grade") int a : a 변수를 grade key값으로 설정 
	// @Param("height") int a : a 변수를 height key값으로 설정 
	@Select("select * from student where grade = #{grade} and height >= #{height}")
	List<Student> selecGradeHeight2(@Param("grade")int a,@Param("height") int b);

	@Insert("insert into student(studno, name, jumin, id) values(#{studno}, #{name}, #{jumin}, #{id})")
	int insert(Student st);
	
	@Update("update student "
			+ "set grade = #{grade}, height = #{height}, weight = #{weight}"
			+ " where name=#{name}")
	int update(Student st);

	@Delete("delete from student where name=#{value}")
	int delectName(String name);
	
	/*
	 <select id = "selectStudno" parameterType="String" resultType="Student">
	 	select * from student where studno = #{value}
	 </select>
	 */
}
