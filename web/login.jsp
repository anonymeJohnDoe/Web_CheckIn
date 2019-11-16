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
<H3>*** Login ***</H3>
<P>Bonjour !
<P>Veuiller entrer votre numero de client :
<BR><BR>
<form method="POST" action="${pageContext.request.contextPath}/servlets/Controller">

    <P>Client ID : <input type="text" name="numcli" size=20 value=""></P>
    <P><button type="submit" class="btn btn-success" name="action" value="LOGIN_VERIFY">
        Login
    </button>
    <button type="submit" class="btn btn-secondary" name="action" value="LOGIN_NEW">
        Obtenir un nouveau
    </button></P>
<%--        <P>Pass : <input type="password" name="pass" size=20 value="lee"></P>--%>
<%--        <P><input type="hidden" name="action" size=20 value="LOGIN"></P>--%>
<%--        <P><input type="submit" name="action" value="Verifier ID">--%>
<%--        <input type="submit" name="action" value="Obtenir un nouveau"></P>--%>

</form>
</body>
</html>
