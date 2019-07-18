<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
	<head>
		<meta charset="utf-8">
	</head>
	
	<body>
		<input type="hidden" id="gu" value="${gu}">
	</body>
	
	<script type="text/javascript">
		window.onload = () => {
			let input = document.getElementById("gu");
			let obj = { };
			obj[input.id] = decodeURIComponent(input.value);
			
			window.opener.postMessage({"login_failure": obj}, location.origin);
			window.close();
		};
	</script>
</html>
