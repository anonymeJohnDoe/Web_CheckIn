<%--
  Created by IntelliJ IDEA.
  User: stasy
  Date: 16/11/2019
  Time: 20:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row">
    <div class="col-md-12">
        <div class="row">
            <div class="col-md-2">
            </div>
            <div class="col-md-4">
            </div>
            <div class="col-md-4">
            </div>
            <div class="col-md-2">
                <form method="POST" action="${pageContext.request.contextPath}/servlets/Controller">

                        <input type="hidden" name="page_prec" value="${param.page_prec}"/>
                        <button type="submit" class="btn btn-outline-info btn-lg btn-block" name="action" value="PANIER">
                        PANIER
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>






