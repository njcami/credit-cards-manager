<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
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
<h1>New Card</h1>
<c:if test="${pageContext.request.isUserInRole('ADMIN')}">
    <span style="margin-right: 100px">
        <a href="/users">Go To Users Page</a>
    </span>
</c:if>
<span style="margin-right: 100px">
    <sec:authorize access="isAuthenticated()">
        Welcome <sec:authentication property="principal.username"/>!
    </sec:authorize>
</span>
<a style="margin-left: 200px;" href="/logout">Logout</a>
<form:form action="/cards/addCard" method="post" modelAttribute="card">
    <table>
        <tr>
            <td>Card Number</td>
            <form:hidden path="id" value="${card.id}"/>
            <td>
                <form:input path="number" value="${card.number}"/> <br/>
                <form:errors path="number" cssClass="error"/>
            </td>
        </tr>
        <tr>
            <td>Expiry Month</td>
            <td>
                <form:input path="expiryMonth" value="${card.expiryMonth}"/> <br/>
                <form:errors path="expiryMonth" cssClass="error"/>
            </td>
        </tr>
        <tr>
            <td>Expiry Year</td>
            <td>
                <form:input path="expiryYear" value="${card.expiryYear}"/> <br/>
                <form:errors path="expiryYear" cssClass="error"/>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <button style="float: right;" type="submit">Submit</button>
            </td>
        </tr>
    </table>
</form:form>

<c:if test="${not empty cards}">
    <h2>Cards List</h2>
    <table>
        <tr>
            <td><strong>Name</strong></td>
            <td><strong>Expiry</strong></td>
            <td><strong>Created Date</strong></td>
            <td><strong>Last Updated</strong></td>
            <td><strong>Options</strong></td>
        </tr>
        <c:forEach items="${cards}" var="card">
            <tr>
                <td>${card.number}</td>
                <td>${card.expiryMonth}/${card.expiryYear}</td>
                <td><fmt:formatDate value="${card.created}" pattern="dd/MM/yyyy HH:mm"/></td>
                <td><fmt:formatDate value="${card.lastUpdated}" pattern="dd/MM/yyyy HH:mm"/></td>
                <td><a href="/editCard?id=${card.id}">Edit</a>
                    <a style="margin-left: 40px;" href="/deleteCard?id=${card.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</c:if>
</body>
</html>