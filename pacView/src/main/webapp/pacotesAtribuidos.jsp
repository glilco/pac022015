<%-- 
    Document   : pacotesAtribuidos
    Created on : 26/11/2015, 13:34:54
    Author     : Danillo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

        Usuario <b> '${usuarioLogado.nome}' </b>  - Email: <b> ${usuarioLogado.email} </b> logado com sucesso

        <h1>Pacotes atribuidos</h1>
        <form action="ManipuladorDePacote" method="post">
            <input type="submit" value="Visualizar detalhes"/>
            <input type="button" value="Criar Pacotes" onclick="window.open('criarPacote.jsp')"/>
            <input type="button" value="Atribuir Equipe" onclick="window.open('atribuirEquipe.jsp')"/>
            
        </form>

    </body>
</html>