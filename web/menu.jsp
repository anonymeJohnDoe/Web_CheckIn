<%--
  Created by IntelliJ IDEA.
  User: stasy
  Date: 10/11/2019
  Time: 19:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Menu</title>
</head>
<body>

<div class="container-fluid">
    <div class="row">
        <div class="col-md-12">
            <div class="row">
                <div class="col-md-3">
                </div>
                <div class="col-md-6">
                    <form method="POST" action="ControlServlet">
                        <h3>
                            MENU
                        </h3>
                        <button type="button" class="btn btn-success" name="action" value="Achats">
                            Achats
                        </button>
                        <button type="button" class="btn btn-success" name="action" value="Promo">
                            Promotions à ne pas rater
                        </button>
                        <button type="button" class="btn btn-secondary" name="action" value="Terminer">
                            Terminer session
                        </button>
                        <!-- si Button marche pas, remplacer par Input -->
                    </form>
                </div>
                <div class="col-md-3">
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
