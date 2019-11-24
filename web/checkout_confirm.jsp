<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%  int nbRes = (int)session.getAttribute("nbRes"); %>
<%  int prixTotal = (int)session.getAttribute("prixTotal"); %>



<% %>
<% %>

<html>
<head>
    <title>Confirmation</title>
    <style>

    </style>
</head>
<body>
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

                                <label>Nombre de reservations :</label>
                                <label><%=nbRes%></label>
                                <label>Total a payer :</label>
                                <h3><%=prixTotal%></h3>

                            <P><button type="submit" class="btn btn-success" name="action" value="CHECKOUT_CONFIRM"> Continuer </button>
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
</body>
</html>
