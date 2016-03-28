<%-- 
    Document   : index
    Created on : 26/11/2015, 12:46:44
    Author     : Danillo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PAC - Controle de Pacotes</title>
    </head>
    <body>
        <div>
            <c:choose>
                <c:when test="${empty sessionScope.usuario}">
                    <form action="autenticacao" method="post">
                        <div><input type="text" name="edtUsuario" placeholder="Informe usuário"></div>
                        <div><input type="password" name="edtSenha" placeholder="Informe senha"></div>
                        <div>
                            <input type="reset" value="Cancelar"/> &nbsp; <input type="submit" value="Confirmar"/>
                        </div>
                    </form>
                </c:when>
                <c:otherwise>
                    <a href="pacotesAtribuidos.jsp">Pacotes atribuídos</a>
                </c:otherwise>
            </c:choose>
        </div>
    </body>
</html>
