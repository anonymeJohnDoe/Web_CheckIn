<%--
  Created by IntelliJ IDEA.
  User: stasy
  Date: 24/11/2019
  Time: 18:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div style="text-align: center;">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="row">
                    <div class="col-md-3">
                    </div>
                    <div class="col-md-6">
                        <h3>
                            Session expirée !

                            Veuillez cliquer le bouton pour revenir à la page Login pour vous identifier :
                        </h3>
                        <form method="POST" action="${pageContext.request.contextPath}/servlets/Controller">

                            <button type="submit" class="btn btn-success" name="action" value="MENU_ERR_LOGIN">
                                Revenir sur login
                            </button>
                        </form>
                    </div>
                    <div class="col-md-3">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
