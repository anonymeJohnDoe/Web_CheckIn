<%--
  Created by IntelliJ IDEA.
  User: stasy
  Date: 10/11/2019
  Time: 12:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<H3>*** Identification ***</H3>
<P>Veuillez vous identifer :
    <BR><BR>
    <form method="POST" action="ControlServlet">
        <P>User : <input type="text" name="user" size=20 value="stan"></P>
        <P>Pass : <input type="password" name="pass" size=20 value="lee"></P>
        <P><input type="hidden" name="action" size=20 value="Login"></P>
        <P><input type="submit" value="Let's go"></P>
    </form>
</body>
</html>
