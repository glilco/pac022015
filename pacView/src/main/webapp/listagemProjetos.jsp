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
<jsp:useBean id="beanListagemProjetos" scope="session" 
             class="br.ufg.inf.fabrica.pac.view.beans.BeanListagemProjetos"/>
</head>
<body>
	<h3>Listagem de projetos</h3>
	<div class="container">
            <c:forEach items="${requestScope.todosProjetos}" var="projeto">
                ${projeto.nome} - 
                ${beanListagemProjetos.dateToStr(projeto.dataInicio)} - 
                ${beanListagemProjetos.dateToStr(projeto.dataTermino)}
                <form action="" class="frmLinha">
                    <input type="submit" value="Selecionar" class="buttonSubmit"/>
                </form>
            </c:forEach>
	</div>
</body>
<%@include file="/rodape.jsp" %>
</html>