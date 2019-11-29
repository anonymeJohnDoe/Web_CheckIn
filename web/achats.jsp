<%@ page import="java.util.ArrayList" %>
<%@ page import="DataClass.Traversees" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%  ArrayList<Traversees> listTrav = new ArrayList<>(); %>
<%  String action = (String)session.getAttribute("action"); %>





<html>
<head>
    <title>Reservation traversee</title>
    <%@ include file="/html_style.jsp" %>

</head>
<body>
<!-- Verifier si client est connecte : -->
<% String numCl = (String)session.getAttribute("numCl"); %>
<% if(numCl != null) { %>

<div style="text-align: center;">
    <div class="container-fluid">
        <!-- Bouton panier   -->
        <%--@ include file="/html_panierbtn.jsp" --%>
        <jsp:include page="/html_panierbtn.jsp">
            <jsp:param name="page_prec" value="achats.jsp"/>
        </jsp:include>
        <div class="row">
            <div class="col-md-12">
                <div class="row">
                    <div class="col-md-2">
                    </div>
                    <div class="col-md-8">
                        <h3>
                            Reserver une traversee
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
                            <span class="badge badge-default">Choisissez une date : </span>
                            <P><input type="date" name="datetr"></P>
                            <P><button type="submit" class="btn btn-success" name="action" value="ACHATS_DATE_CH"> Continuer </button>
                            <button type="submit" class="btn btn-success" name="action" value="ACHATS_RET_MENU"> Revenir au menu </button></P>
                        </form>
                    </div>
                    <div class="col-md-2">
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div style="text-align: center;">
                            <P></P>
                            <h3 style="display: inline; border: 1px solid black;">
                                <%  if(action != null && action.equals("ACHATS_LISTE_TRAV_TROUV")) {%>

                                <% } else {%>
                                Veuillez choisir une date où il y a des traversees enregistrees
                                <% } %>
                            </h3>
                            <table class="table" >
                                <thead>
                                <tr>
                                    <th>
                                        #
                                    </th>
                                    <th>
                                        Code Traversee
                                    </th>
                                    <th>
                                        Heure de depart
                                    </th>
                                    <th>
                                        Destination
                                    </th>
                                    <th>
                                        Prix
                                    </th>
                                    <th>
                                        Panier
                                    </th>
                                </tr>
                                </thead>
                                <tbody>
                                    <%-- Boucle : afficher les  traversees --%>
                                    <%  if(action != null && action.equals("ACHATS_LISTE_TRAV_TROUV")) {%>

                                        <% listTrav = (ArrayList<Traversees>) session.getAttribute("listTrav");%>

                                    <% for( int i=0; i< listTrav.size(); i++) { %>
                                            <tr class="table-active">
                                                <td>
                                                    <%=i+1 %>
                                                </td>
                                                <td>
                                                    <%=listTrav.get(i).get_idTraversees() %>
                                                </td>
                                                <td>
                                                    <%=listTrav.get(i).get_heureDep() %>
                                                </td>
                                                <td>
                                                    <%=listTrav.get(i).get_destination() %>
                                                </td>
                                                <td>
                                                    <%=listTrav.get(i).get_prix() %>
                                                </td>
                                                <td>
                                                    <form method="POST" action="${pageContext.request.contextPath}/servlets/Controller">
                                                        <input type="hidden" name="traverseesId" value="<%=listTrav.get(i).get_idTraversees()%>"/>
                                                        <input type="hidden" name="prix" value="<%=listTrav.get(i).get_prix()%>"/>
                                                        <input type="hidden" name="page_prec" value="achats.jsp"/>
                                                        <button type="submit" class="btn btn-success"  id="btn_add" name="action" value="ADD_PANIER">
                                                            Add
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
                                        <td> - </td>
                                    </tr>
                                    <% } %>

                                </tbody>
                            </table>
<%--                            <form method="POST" action="${pageContext.request.contextPath}/servlets/Controller">--%>
<%--                                <button type="submit" class="btn btn-success" name="action" value="PANIER">--%>
<%--                                    Voir Panier--%>
<%--                                </button>--%>
<%--                            </form>--%>
                        </div>
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
