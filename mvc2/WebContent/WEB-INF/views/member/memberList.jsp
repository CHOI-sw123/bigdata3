<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cpath" value="${pageContext.request.contextPath}"/>


<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>

<script type="text/javascript">
	function insertForm() {
		location.href="${cpath}/insertForm.do";
	}
	
	function deleteFn(num) {
		location.href="${cpath}/delete.do?num="+num;
	}
</script>

</head>
<body>
-회원리스트 보기(mvc1)-
	<table border='1'>
	
	<tr>
		<td>번호</td>
		<td>이름</td>
		<td>전화번호</td>
		<td>주소</td>
		<td>위도</td>
		<td>경도</td>
		<td>삭제</td>
	</tr>
	
	<c:forEach var="vo" items="${list}">
		<tr>
			<td>${vo.num}</td>
			<td><a href="${cpath}/content.do?num=${vo.num}">${vo.name}</a></td>	<!-- ?num=${vo.num} : 쿼리스트링(get 방식) -->
			<td>${vo.phone}</td>
			<td>${vo.addr}</td>
			<td>${vo.lat}</td>
			<td>${vo.lng}</td>
			<td><input type="button" value="삭제" onclick="deleteFn(${vo.num})"/></td>
		</tr>
	</c:forEach>
	
	<tr>
		<td colspan="7" align="left">
			<input type="button" value="회원가입" onclick="insertForm()"/>
		</td>
	</tr>
	
	</table>
</body>
</html>