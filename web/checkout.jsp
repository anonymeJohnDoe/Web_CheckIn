<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Checkout page</title>
    <style>

    </style>
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
                            Achat des réservations
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
                            <div class="form-group">

                                <label for="nbPass">
                                    Nombre de passager (sans conducteur) :
                                </label>
                                <input align="right" type="number" class="form-control" name="nbPass" id="nbPass">
                            </div>
                            <div class="form-group">

                                <label for="adresse">
                                    Adresse :
                                </label>
                                <input align="right" type="text" class="form-control" name="adresse" id="adresse">
                            </div>
                            <div class="form-group">

                                <label for="numTel">
                                    Numéro de GSM :
                                </label>
                                <input align="right" type="text" class="form-control" name="numTel" id="numTel">
                            </div>
                            <div class="form-group">

                                <label for="numCarte">
                                    Numéro carte bancaire :
                                </label>
                                <input align="right" type="number" class="form-control" name="numCarte" id="numCarte">
                            </div>
                            <div class="form-group">

                                <label for="dateExp">
                                    Date de validité de la carte :
                                </label>
                                <input align="right" type="text" class="form-control" name="dateExp" id="dateExp">
                            </div>

                            <P><button type="submit" class="btn btn-success" name="action" value="CHECKOUT"> Continuer </button>
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
