<%-- 
    Document   : index
    Created on : 26/11/2015, 12:46:44
    Author     : Danillo
--%>

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
	<h3>Listagem de projetos</h3>
	<div class="container">
            <c:forEach items="${requestScope.todosProjetos}" var="projeto">
                ${projeto.nome} - ${projeto.dataInicio} - ${projeto.dataTermino}
                <form action="">
                    <input type="submit" value="Selecionar"/>
                </form>
            </c:forEach>
	</div>
</body>
<%@include file="/rodape.jsp" %>
</html>