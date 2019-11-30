<%@ page import="DataClass.Traversees" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="DataClass.Panier" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%  int somme_Total = 0; %>
<%  String result = (String)session.getAttribute("result"); %>
<%  String page_prec = (String)session.getAttribute("page_prec"); %>
<% ArrayList<Panier> list_Panier = (ArrayList<Panier>) session.getAttribute("panier");%>



<html>
<head>
    <title>Panier</title>
    <%@ include file="/html_style.jsp" %>

</head>
<body>
<!-- Verifier si client est connecte : -->
<% String numCl = (String)session.getAttribute("numCl"); %>
<% if(numCl != null) { %>
    <% if(!result.equals("GET_PANIER_FAIL")) { %>

<div style="text-align: center;">
    <div class="container-fluid">
        <div class="row">
            <h3>
                Votre numero client : <%=numCl%>
            </h3><br>
        </div>

            <div class="row">

            <div class="col-md-12">
                <div class="row">
                    <div class="col-md-2">
                    </div>
                    <div class="col-md-8">
                        <h3>
                            Voir Panier
                        </h3>
                    </div>
                    <div class="col-md-2">
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div style="text-align: center;">
                            <table class="table" >
                                <thead>
                                <tr>
                                    <th>
                                        ID Panier
                                    </th>
                                    <th>
                                        Traversees ID
                                    </th>
                                    <th>
                                        Client ID
                                    </th>
                                    <th>
                                        Prix
                                    </th>
                                    <th>
                                        Remove
                                    </th>
                                </tr>
                                </thead>
                                <tbody>
                                <%-- Boucle : afficher les  traversees --%>
                                <%  if(result != null && result.equals("GET_PANIER_OK")) {%>

                                <% if(list_Panier != null) { for( int i=0; i< list_Panier.size(); i++) { %>
                                <tr class="table-active">
                                    <td>
                                        <%=list_Panier.get(i).get_id_panier() %>
                                    </td>
                                    <td>
                                        <%=list_Panier.get(i).get_traversee_id() %>
                                    </td>
                                    <td>
                                        <%=list_Panier.get(i).get_client_id() %>
                                    </td>
                                    <td>
                                        <%=list_Panier.get(i).get_prix() %>
                                    </td>
                                    <td>
                                        <form method="POST" action="${pageContext.request.contextPath}/servlets/Controller">
                                            <input type="hidden" name="panierId" value="<%=list_Panier.get(i).get_id_panier()%>"/>
                                            <input type="hidden" name="numCli" value="<%=list_Panier.get(i).get_client_id()%>"/>
                                            <button type="submit" class="btn btn-success"  id="btn_remove" name="action" value="REMOVE_FROM_PANIER">
                                                Remove
                                            </button>
                                        </form>
                                    </td>
                                </tr>

                                <% } } %>
                                <% } else {%>
                                <tr class="table-active">
                                    <td> - </td>
                                    <td> - </td>
                                    <td> - </td>
                                    <td> - </td>
                                    <td> - </td>
                                </tr>
                                <% } %>
                                <%
                                    if(list_Panier != null)
                                    {
                                        for (int i = 0; i < list_Panier.size(); i++)
                                        {

                                            somme_Total += Integer.parseInt(list_Panier.get(i).get_prix());

                                        }
                                        session.setAttribute("somme_Total", somme_Total);
                                    }
                                %>
                                </tbody>
                            </table>
                            <form method="POST" action="${pageContext.request.contextPath}/servlets/Controller">
                                <input type="hidden" name="page_prec" value="${param.page_prec}"/>
                                <P><button type="submit" class="btn btn-success" name="action" value="CHECKOUT">
                                    CHECKOUT
                                </button>
                                <button type="submit" class="btn btn-success" name="action" value="PANIER_RETOUR">
                                    Retour a la page précédente
                                </button></P>

                            </form>
                            <div>
                                Somme Total : <%=somme_Total%>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
    <% } else {%>
<h3>
    Votre numero client : <%=numCl%>
</h3>
<div style="text-align: center;">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="row">
                    <div class="col-md-2">
                    </div>
                    <div class="col-md-8">
                        <h3>
                            Voir Panier
                        </h3>
                    </div>
                    <div class="col-md-2">
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div style="text-align: center;">

                            <h3 style="color: #0049f7">Le panier est vide. Vous êtes invités d'aller sur une page des achats.</h3>

                            <table class="table" >
                                <thead>
                                    <tr>
                                        <th>
                                            ID Panier
                                        </th>
                                        <th>
                                            Traversees ID
                                        </th>
                                        <th>
                                            Client ID
                                        </th>
                                        <th>
                                            Prix
                                        </th>
                                        <th>
                                            Remove
                                        </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr class="table-active">
                                        <td> - </td>
                                        <td> - </td>
                                        <td> - </td>
                                        <td> - </td>
                                        <td> - </td>
                                    </tr>
                                </tbody>
                            </table>

                            <form method="POST" action="${pageContext.request.contextPath}/servlets/Controller">
                                <input type="hidden" name="page_prec" value="${param.page_prec}"/>
                                <P><button type="submit" class="btn btn-success" name="action" value="PANIER_RETOUR">
                                        Retour a la page précédente
                                </button></P>

                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

    <% } %>
<% } else {%>
<!-- si client pas connecté : -->
<!-- Afficher page d'erreur   -->
<%@ include file="/session_expired.jsp" %>

<% } %> <%-- end else --%></body>
</html>
