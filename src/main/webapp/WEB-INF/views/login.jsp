<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Credit Card Application</title>
    <style type="text/css">
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
<body onload='document.loginForm.username.focus();'>
<h1>Login</h1>
<c:if test="${not empty errorMessage}">
    <div style="color:red; font-weight: bold; margin: 30px 0;">${errorMessage}</div>
</c:if>
<form name='loginForm' action="/login" method='POST'>
    <table>
        <tr>
            <td>Username</td>
            <td><input type='text' name='username' value=''></td>
        </tr>
        <tr>
            <td>Password</td>
            <td><input type='password' name='password'/></td>
        </tr>
        <tr>
            <td><a href="/users">New user? Register here</a></td>
            <td><input name="submit" type="submit" value="Submit"/></td>
        </tr>
    </table>
</form>
</body>
</html>

