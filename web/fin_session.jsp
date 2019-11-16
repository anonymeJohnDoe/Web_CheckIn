<%--
  Created by IntelliJ IDEA.
  User: stasy
  Date: 16/11/2019
  Time: 20:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="refresh" content="3;url=${pageContext.request.contextPath}/login.jsp" />
    <title>Merci pour votre visite</title>
</head>
<body>
<H3>*** Merci pour votre visite ! ***</H3>
<P>
<P>Redirection sur la page login dans 3 secondes ...
<BR><BR>

<form method="POST" action="${pageContext.request.contextPath}/servlets/Controller">
</form>

</body>
</html>
