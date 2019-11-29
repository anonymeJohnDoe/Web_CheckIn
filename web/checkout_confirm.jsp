<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Confirmation</title>
    <%@ include file="/html_style.jsp" %>

</head>
<body>
<!-- Verifier si client est connecte : -->
<% String numCl = (String)session.getAttribute("numCl"); %>
<% if(numCl != null) { %>
<div style="text-align: center;">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="row">
                    <div class="col-md-2">
                    </div>
                    <div class="col-md-8">
                        <h3>
                            Veuillez confirmer l'achat des produits :
                        </h3>
                    </div>
                    <div class="col-md-2">
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-2">
                    </div>
                    <div class="col-md-8">
                        <form method="POST" action="${pageContext.request.contextPath}/servlets/Controller">
                                <%  int prixTotal = (int)session.getAttribute("somme_Total"); %>
                                <%  String cardNumber = (String)session.getAttribute("cardNumber"); %>
                                <label>Total a payer :</label>
                                <h3><%=prixTotal%></h3>

                            <P><button type="submit" class="btn btn-success" name="action" value="CHECKOUT_MERCI"> Continuer </button>
                            <button type="submit" class="btn btn-success" name="action" value="CHECKOUT_RET_PANIER"> Revenir au panier </button></P>
                        </form>

                    </div>
                    <div class="col-md-2">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<% } else {%>
<!-- si client pas connecté : -->
<!-- Afficher page d'erreur   -->
<%@ include file="/session_expired.jsp" %>

<% } %> <%-- end else --%>
</body>
</html>
