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
        <h3>Controle de pacotes</h3>
        <div class="container">

            <form action="criarPacote" method="post" 
                  enctype="multipart/form-data">
                <div>
                    <label for="documento">Documentos</label>
                </div>
                <div>
                    <input id="documento" type="file" name="documento" tabindex="6" 
                           required="true" >
                </div>
                <div><label for="nomePacote">Nome</label></div>
                <div>
                    <input style="width:500px " id="nomePacote" 
                           title="Nome:" type="text" name="nomePacote" 
                           placeholder="Insira um nome que identifique o pacote" 
                           tabindex="1" required="true" >
                </div>
                <div><label for="descricaoPacote">Descrição</label></div>
                <div>
                    <input style="width:500px"id="descricaoPacote" 
                           title="Descrição:" type="text" name="descricaoPacote" 
                           placeholder="Descreve detalhes da atividade a ser 
                           desenvolvida no pacote" tabindex="2" required="true"> 
                </div>
                <div><label for="dataPrevistaRealizacao">Data realização</label></div>
                <div>
                    <input id="dataPrevistaRealizacao" title="Previsão de Realização:" 
                           type="text" placeholder="dd/mm/yyyy" 
                           name="dataPrevistaRealizacao" tabindex="4" >
                </div>
                <div>
                    <button class="submit" type="submit">Criar</button> 
                    <button class="reset" type="reset">Limpar</button>
                </div>
            </form>
        </div>
    </body>
    <%@include file="/rodape.jsp" %>
</html>