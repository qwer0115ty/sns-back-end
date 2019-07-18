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
		<input type="hidden" id="access_token" value="${access_token}">
		<input type="hidden" id="refresh_token" value="${refresh_token}">
		<input type="hidden" id="user" value="${user}">
	</body>
	
	<script type="text/javascript">
		window.onload = () => {
			let inputs = document.getElementsByTagName("input");
			let obj = { };
	
			for(let i = 0 ;i<inputs.length;i++){
				if(inputs[i].id == "user") {
					obj[inputs[i].id] = decodeURIComponent(inputs[i].value);
				} else {
					obj[inputs[i].id] = inputs[i].value;
				}
			}
			
			window.opener.postMessage({"login_success": obj}, location.origin);
			window.close();
		};
	</script>
</html>
