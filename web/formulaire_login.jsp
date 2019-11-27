<%--
  Created by IntelliJ IDEA.
  User: stasy
  Date: 16/11/2019
  Time: 19:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Vos informations</title>
</head>
<body>
<div style="text-align: center;">
<H3>*** Vos informations ***</H3>
<P>
<P>Veuiller entrer les informations additionnelles :
<BR><BR>
<form method="POST" action="${pageContext.request.contextPath}/servlets/Controller">
    <P>Nom : <input type="text" name="nomcli" size=20 value="Nom"></P>
    <P>Prenom : <input type="text" name="prenomcli" size=20 value="Prenom"></P>
    <P>Adresse : <input type="text" name="adrcli" size=20 value="rue de l adresse"></P>
    <P>E-mail : <input type="text" name="emailcli" size=20 value="email@email.com"></P>
    <!-- champs hidden : -->
    <!--input type="hidden" name="typeLogin" value="NEW_CLIENT"></P-->

    <P><button type="submit" class="btn btn-success" name="action" value="LOGIN_NEW_FORM">
        Continuer
    </button></P>

</form>
</div>
</body>
</html>
