<%@ page import="DataClass.Traversees" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="DataClass.Panier" %><%--
  Created by IntelliJ IDEA.
  User: Anonyme
  Date: 25/11/2019
  Time: 09:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%  ArrayList<Panier> list_Panier = new ArrayList<>(); %>
<%  int somme_Total = 0; %>
<%  String action = (String)session.getAttribute("action"); %>
<%  String page_prec = (String)session.getAttribute("page_prec"); %>


<html>
<head>
    <title>Panier</title>
    <style>
        table {
            border: 1px solid black;
            border-collapse: collapse;
            width: 100%;
        }
        td, th {
            border: 1px solid black;
            text-align: left;
            padding: 8px;
        }
        tr:nth-child(even) {background-color: #f2f2f2;}
    </style>
</head>
<body>
<!-- Verifier si client est connecte : -->
<% String numCl = (String)session.getAttribute("numCl"); %>
<% if(numCl != null) { %>
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
                                <%  if(action != null && action.equals("GET_PANIER_OK")) {%>

                                <% list_Panier = (ArrayList<Panier>) session.getAttribute("list_Panier");%>

                                <% for( int i=0; i< list_Panier.size(); i++) { %>
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
                                            <button type="submit" class="btn btn-success" name="action" value="REMOVE_FROM_PANIER">
                                                Remove
                                            </button>
                                        </form>
                                    </td>
                                </tr>

                                <% } %>
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
                                    for( int i=0; i< list_Panier.size(); i++) {

                                        somme_Total += Integer.parseInt(list_Panier.get(i).get_prix());

                                    }
                                    session.setAttribute("somme_Total", somme_Total);
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
<%-- end else --%>
<% } %>
</body>
</html>
