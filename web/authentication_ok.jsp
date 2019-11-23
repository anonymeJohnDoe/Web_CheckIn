<%--
  Created by IntelliJ IDEA.
  User: stasy
  Date: 10/11/2019
  Time: 19:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Authentication successful</title>
</head>
<body>
<div style="text-align: center;">
<H3>*** Authentication successful ! ***</H3>
<P>Veuillez entrer les informations additionnelles :
    <BR><BR>
    <form method="POST" action="ControlServlet">
<P>Prénom : <input type="text" name="prenom" size=20 value="prenom"></P>
<P>E-mal : <input type="text" name="email" size=20 value="stas.yeremitsa@gmail.com"></P>
<P><input type="hidden" name="action" size=20 value="LoginOK"></P>
<P><input type="submit" value="Confirmer"></P>
</form>
</div>
</body>
</html>
