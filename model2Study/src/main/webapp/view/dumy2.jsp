<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">
	opener.document.f.id.value = "${id}";
	alert("${msg}");
	self.close();
</script>