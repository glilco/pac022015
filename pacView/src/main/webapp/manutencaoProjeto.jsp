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
            <form action="criarProjeto" method="post">
                <div>
                    <label>Nome:*</label> 
                </div>
                <div>
                    <input type="text" size="100" name="nome" required="required">
                </div>
                <div>	
                    <label>Descrição:</label>
                </div>
                <div>
                    <textarea rows="2" cols="100" name="descricao"></textarea>
                </div>
                <div>
                    <label>Data de início:*</label> 
                </div>
                <div>
                    <input type="date" class="date" name="dataInicio" 
                           pattern="dd/MM/yyyy">
                </div>
                <div>
                    <label>Data de termino:*</label> 
                </div>
                <div>
                    <input type="date" class="date" maxlength="10"
                           name="dataTermino" pattern="dd/MM/yyyy">
                </div>
                <div>
                    <label>Patrocinador:*</label> 
                </div>
                <div>
                    <input type="text" size="100"
                           name="patrocinador">
                </div>
                <div>
                    <label>stakeholders:*</label> <input type="text" size="100"
                                                         name="stakeholders">
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