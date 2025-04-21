package model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import model.board.Board;

public interface BoardMapper {

	@Select("select ifnull(max(num),0) from board")
	int maxnum();

	@Insert("insert into board(num, writer, pass, title, content, file1, regdate, readcnt, grp, grplevel, grpstep,boardid) "
			+ "values(#{num}, #{writer}, #{pass}, #{title}, #{content}, #{file1}, now(), 0, #{grp}, #{grplevel}, #{grpstep}, #{boardid}) ")
	int insert(Board board);

	String sqlcol="<if test = 'column != null'>"
			+ "<if test = 'col1 != null'> and ( ${col1} like '%${find}%' </if>"
			+ "<if test = 'col2 == null'> ) </if>"
			+ "<if test = 'col2 != null'> or ${col2} like '%${find}%' </if>"
			+ "<if test = 'col2 != null and col3 == null'> ) </if>"
			+ "<if test = 'col3 != null '> or ${col3} like '%${find}%' ) </if>"
			+ "</if>";
	@Select({
		"<script>"+
		"select count(*) from board where boardid = #{boardid}"+
		sqlcol+
		"</script>"
		})
	int count(Map<String, Object> map);

/*
	 grp 값과  num 값이 같다
	 limit #{start},#{limit} => limit 0, 10 => 0번에서 10개 조회
	 num 컬럼의 역순
	 5	=> 0
	 4	=> 1
	 3	=> 2
	 2 	=> 3
	 1	=> 4
*/
	@Select({
		"<script>"+
			"select * from board where boardid=#{boardid} " +sqlcol
			+ " order by grp desc, grpstep asc limit #{start},#{limit}"+
			"</script>"
			})
	List<Board> list(Map<String, Object> map);
	
	@Select("select * from board where num = #{value}")
	Board selectOne(int num);

	@Update("update board "
			+ "set readcnt = readcnt + 1 "
			+ "where num = #{value}")
	void readcntAdd(int num);

	@Update("update board "
			+ "set grpstep = grpstep+1 "
			+ "where grp = #{grp} and grpstep > #{grpstep}")
	void grpStepAdd(@Param("grp")int grp, @Param("grpstep")int grpstep);

	@Update("update board "
			+ "set writer = #{writer},"
			+ " title = #{title},"
			+ " content = #{content},"
			+ " num = #{num},"
			+ " file1 = #{file1},"
			+ "  regdate = #{regdate}"
			+ " where num=#{num}")
	int Update(Board b);

	@Delete("delete from board where num = #{num}")
	int delete(int num);
	
	@Select("select count(*) from board WHERE grp = #{num} and grplevel != 0 and grpstep !=0")
	int check(int num);

	

	
	

}
