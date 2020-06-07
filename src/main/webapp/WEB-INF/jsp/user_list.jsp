<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 <title>User List</title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="menu.jsp" />
 <div class="container">
  <h2>User List</h2>
  <table class="table table-striped">
   <thead>
    <tr>
     <th scope="row">User Id</th>
     <th scope="row">User Name</th>
     <th scope="row">Email</th>
     <th scope="row">Role</th>
     <th scope="row">Edit</th>
     <th scope="row">Delete</th>
    </tr>
   </thead>
   <tbody>
    <c:forEach items="${userList }" var="user" >
     <tr>
      <td>${user.userId }</td>
      <td>${user.username }</td>
      <td>${user.emailId }</td>
      <td>${user.role }</td>
      <td>
       <spring:url value="/update/${user.userId }" var="updateURL" />
       <a class="btn btn-primary" href="${updateURL }" role="button">Update</a>
      </td>
      <td>
       <spring:url value="/delete/${user.userId }" var="deleteURL" />
       <a class="btn btn-primary" href="${deleteURL }" role="button">Delete</a>
      </td>
     </tr>
    </c:forEach>
   </tbody>
  </table>
  <spring:url value="/add" var="addURL" />
  <a class="btn btn-primary" href="${addURL }" role="button">Add New User</a>
 </div>
</body>
</html>