package mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import main.Student;

public interface StudentMapper2 {
	
	@Select({"<script>",
		"select * from student ",
		"<where>",
		"<if test='grade != null'> grade = #{grade}</if>",
		"<if test='height != null'> height >= #{height}</if>",
		"<if test='weight != null'> weight &lt;= #{weight}</if>",
		"</where>",
		"</script>"
	})
	List<Student> select(Map<String, Object> map);
	
	@Select({
		"<script>",
        "select * FROM student ",
        "<trim prefix='WHERE' prefixOverrides='AND'||'OR'>",
        "<if test='grade != null'> AND grade = #{grade}</if>",
        "<if test='height != null'> AND height >= #{height}</if>",
        "</trim>",
        "</script>"
        })
	List<Student> select2(Map<String, Object> map);

}
