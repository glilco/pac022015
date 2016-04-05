<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Criação de Projetos</title>
        <%@include file="/cabecalho.jsp" %>
        <%@include file="/menuEsquerdo.jsp" %>
    </head>
    <body>
        <div class="container">
            <span class="errorMessage">
                <c:out value="${sessionScope.errorMessages}"/>
            </span>
        </div>
    </body>
    <%@include file="/rodape.jsp" %>
</html>