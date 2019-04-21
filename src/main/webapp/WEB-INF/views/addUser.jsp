<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Credit Card Application</title>
    <style type="text/css">
        .error {
            color: red;
        }

        table {
            width: 50%;
            border-collapse: collapse;
            border-spacing: 0;
        }

        table td {
            border: 1px solid #565454;
            padding: 20px;
        }
    </style>
</head>
<body>
<h1>New User</h1>
<a style="margin-right:100px;" href="/cards/cards">Go To Cards Page</a>
<span style="margin-right: 100px">
    <sec:authorize access="isAuthenticated()">
        Welcome <sec:authentication property="principal.username"/>!
    </sec:authorize>
</span>
<a style="margin-left: 200px;" href="/logout">Logout</a>
<form:form action="addUser" method="post" modelAttribute="user">
    <table>
        <tr>
            <td>Username</td>
            <td>
                <form:input path="username"/> <br/>
                <form:errors path="username" cssClass="error"/>
            </td>
        </tr>
        <tr>
            <td>Password</td>
            <td>
                <form:password path="password"/> <br/>
                <form:errors path="password" cssClass="error"/>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <button style="float: right;" type="submit">Submit</button>
            </td>
        </tr>
    </table>
</form:form>

<c:if test="${not empty users}">
    <h2>Users List</h2>
    <table>
        <tr>
            <td><strong>Username</strong></td>
            <td><strong>Created Date</strong></td>
        </tr>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>${user.username}</td>
                <td><fmt:formatDate value="${user.created}" pattern="dd/MM/yyyy HH:mm"/></td>
            </tr>
        </c:forEach>
    </table>
</c:if>
</body>
</html>