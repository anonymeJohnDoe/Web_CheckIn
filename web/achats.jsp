<%@ page import="java.util.ArrayList" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%  ArrayList<String> listTrav = new ArrayList<>(); %>
<%  ArrayList<String> listHorair = new ArrayList<>(); %>
<%  ArrayList<String> listDest = new ArrayList<>(); %>
<%  String action = (String)session.getAttribute("action"); %>


<% %>
<% %>

<html>
<head>
    <title>Reservation traversee</title>
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
                            <h3>
                                Reserver une traversee
                            </h3>
                            <table class="table">
                                <thead>
                                <tr>
                                    <th>
                                        Code Traversee
                                    </th>
                                    <th>
                                        Heure de depart
                                    </th>
                                    <th>
                                        Destination
                                    </th>
                                </tr>
                                </thead>
                                <tbody>
                                    <%-- Boucle : afficher les  traversees --%>
                                    <%  if(action != null && action.equals("ACHATS_LISTE_TRAV_TROUV")) {%>

                                        <% int nbTrav = (int)session.getAttribute("nbTrav"); %>
                                        <% listTrav = (ArrayList<String>) session.getAttribute("listTrav");%>
                                        <% listHorair = (ArrayList<String>) session.getAttribute("listHorair");%>
                                        <% listDest = (ArrayList<String>) session.getAttribute("listDest");%>

                                        <% for( int i=0; i< nbTrav; i++) { %>
                                            <tr class="table-active">
                                                <td>
                                                    <%=listTrav.get(i) %>
                                                </td>
                                                <td>
                                                    <%=listHorair.get(i) %>
                                                </td>
                                                <td>
                                                    <%=listDest.get(i) %>
                                                </td>
                                            </tr>
                                        <% } %>
                                    <% } else {%>
                                    <tr class="table-active">
                                        <td>
                                            Veuillez choisir une date où il y a des traversees enregistrees
                                        </td>
                                    </tr>
                                    <% } %>



                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
