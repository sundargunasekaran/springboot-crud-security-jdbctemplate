<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %> 
    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 <title>Add User</title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="menu.jsp" />
 <div class="container" style="width: 25%;margin-left: 10%;">
  <spring:url value="/save" var="saveURL" />
  <h2>User</h2>
  <form:form modelAttribute="user" method="post" action="${saveURL }" cssClass="form">
   <form:hidden path="userId"/>
   <div class="form-group">
    <lable for="firstName">Name</lable>
    <form:input path="username" cssClass="form-control" id="username" />
   </div>
	<div class="form-group">
    <lable for="password">Password</lable>
    <form:password path="password" cssClass="form-control" id="password" />
   </div>
   <div class="form-group">
    <lable for="email">Email</lable>
    <form:input path="emailId" cssClass="form-control" id="emailId" />
   </div>
   
   <div class="form-group">
    <lable for="role">Role</lable>
     <form:select path="roleId" items="${roles}"/>
<%--     <form:select path="roleId">
    	<form:option value="0" label="Select One" />
   		<form:option value="1" label="Admin" />
   		<form:option value="2" label="User" />
   		<form:option value="3" label="Guest" />
	</form:select> --%>
   </div>
   <button type="submit" class="btn btn-primary">Save</button>
  </form:form>
 </div>
</body>
</html>