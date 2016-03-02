<%-- 
    Document   : criarPacote
    Created on : 12/02/2016, 08:36:02
    Author     : auf
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page isELIgnored ="false" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

        <c:forEach var="item" items=" ${resposta.laudo}">
            ${item} <br/>
        </c:forEach>            

        <hr>
        <form action="manterPacote" method="post" style="width: 1000px" enctype="multipart/form-data">
            Nome: <br/> <input style="width:500px " id="nomePacote" title="Nome:" type="text" name="nomePacote" placeholder="Insira um nome que identifique o pacote" tabindex="1" required="true" > <br/><br/>

            Descrição: <br/> <input style="width:500px"id="descricaoPacote" title="Descrição:" type="text" name="descricaoPacote" placeholder="Descreve detalhes da atividade a ser desenvolvida no pacote" tabindex="2" required="true"> <br/><br/>

            <label hidden="${pacote.dataCriacao}==null"> 
                Data Criação: </label> <br/> <input style="" id="dataCriacao" title="Data criação:" type="date" name="dataCriacao" tabindex="3" disabled="true" 
                                                value="${pacote.dataCriacao}" hidden="${pacote.dataCriacao}==null"> <br/> <br/> 

            Data Previsão de Realização: <br/> <input id="dataPrevistaRealizacao" title="Previsão de Realização:" type="text" placeholder="dd/mm/yyyy" 
                                                      pattern="^(?:(?:31(\/)(?:0?[13578]|1[02]))\1|(?:(?:29|30)(\/)(?:0?[1,3-9]|1[0-2])\2))(?:(?:1[6-9]|[2-9]\d)?\d{2})$|^(?:29(\/)0?2\3(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\d|2[0-8])(\/)(?:(?:0?[1-9])|(?:1[0-2]))\4(?:(?:1[6-9]|[2-9]\d)?\d{2})$" 
                                                      name="dataPrevistaRealizacao" tabindex="4" > <br/> <br/> 

            Abandonado: <input id="abandonado" type="checkbox" name="abandonado" tabindex="5"> <br/> <br/> 

            Documentos: <br/> <input id="documento" type="file" name="documento"  tabindex="6" required="true" > <br/> <br/> 

            <div> <input type="reset" value="Cancelar"/> &nbsp; <input type="submit" value="Confirmar"/> </div>
        </form>

    </body>
</html>
