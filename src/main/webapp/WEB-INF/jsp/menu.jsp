<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div style="border: 1px solid #ccc; padding: 5px; margin-bottom: 20px;">

	<a href="${pageContext.request.contextPath}/list">Home</a> | &nbsp;

	<a href="${pageContext.request.contextPath}/add">Add User</a> |   
            <a onclick="document.forms['logoutForm'].submit()">Logout</a>
            </h3></u>

    <form id="logoutForm" method="POST" action="${contextPath}/logout">
    <sec:csrfInput /> 
    </form>

</div>
