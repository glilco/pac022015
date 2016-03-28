<%-- 
    Document   : atribuirEquipe
    Created on : 12/02/2016, 15:29:25
    Author     : Danillo
--%>

<%@page import="br.ufg.inf.fabrica.pac.dominio.Usuario"%>
<%@page import="java.util.List"%>
<%@page import="br.ufg.inf.fabrica.pac.dominio.MembroProjeto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<jsp:useBean id="beanAtribuir" scope="session" class="br.ufg.inf.fabrica.pac.view.beans.BeanAtribuirEquipe" />
<jsp:setProperty name="beanAtribuir" param="usuarioPesquisado" property="usuarioPesquisado" />
<jsp:setProperty name="beanAtribuir" param="usuarioEmAlteracao" property="usuarioEmAlteracao" />

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Atribuir equipe</title>
        <%@include file="/cabecalho.jsp" %>
    </head>
    <body>
        <h3>Atribuição de Equipe ao Projeto</h3>
        <div class="container-atribuicao-equipe">
            
            <div id="container-membros">   
                <div class="container-membros-titulo">
                    <div class="container-membros-usuarios">
                        <label>Membros</label>
                    </div>  
                    <div class="container-membros-papeis"> 
                        <label>Papéis</label>
                    </div>
                    <div class="container-membros-acoes">                         
                    </div>
                </div>

                <c:forEach var="usuario" items="${beanAtribuir.usuariosMembros}">
                    <c:if test="${beanAtribuir.usuarioEmAlteracao!=usuario.id}">
                        <div id="form-membros">
                            <form method="post" action="atribuirEquipe.jsp">
                                <input type="hidden" name="usuarioEmAlteracao" value="${usuario.id}"/>
                                <div class="container-membros-usuarios"> ${usuario.nome} </div>  
                                <div class="container-membros-papeis"> ${usuario.papeis} </div>
                                <div class="container-membros-acoes"> 
                                    <button class="btn-alterar-membro" type="submit" value="Alterar" title="Alterar papel de membro">
                                        <img src="imagens/ic_mode_edit_1x.png" alt="Alterar"/>
                                        Alterar
                                    </button>                                                                                                 
                                </div>                            
                            </form>
                        </div>
                    </c:if>
                    <c:if test="${beanAtribuir.usuarioEmAlteracao==usuario.id}">
                        <form method="post" action="atualizarPermissoesMembro">
                                <div> <!-- Div Container Atualização de papeis. -->
                                    <div> <!-- Título atualização de papeis -->
                                        <h5 style="font-weight: bold; margin-bottom: 0px">
                                            ${usuario.nome}
                                        </h5>                                 
                                    </div> <!-- Fim - Título atualização de papeis -->

                                    <div class="item-lista-nao-membro"> <!-- Div Itens de atualização de papeis. -->
                                        <div class="checkbox-nome-nao-membro">
                                            <input type="checkbox" value="GPR" name="papeis" id="papelGPR"
                                                <c:if test="${usuario.GPR}">checked</c:if> />
                                        </div>
                                        <div class="label-nome-nao-membro">
                                            <label for="papelGPR">GPR</label>
                                        </div>
                                    </div> <!-- Fim - Div Itens de atualização de papeis. -->

                                    <div class="item-lista-nao-membro"> <!-- Div Itens de atualização de papeis. -->
                                        <div class="checkbox-nome-nao-membro">
                                            <input type="checkbox" value="MEG" name="papeis" id="papelMEG"
                                                <c:if test="${usuario.MEG}">checked</c:if> />
                                        </div>
                                        <div class="label-nome-nao-membro">
                                            <label for="papelMEG">MEG</label>
                                        </div>
                                    </div> <!-- Fim - Div Itens de atualização de papeis. -->

                                    <div class="item-lista-nao-membro"> <!-- Div Itens de atualização de papeis. -->
                                        <div class="checkbox-nome-nao-membro">
                                            <input type="checkbox" value="MEM" name="papeis" id="papelMEM"
                                                   <c:if test="${usuario.MEM}">checked</c:if> />
                                        </div>
                                        <div class="label-nome-nao-membro">
                                            <label for="papelMEM">MEM</label>                                        
                                        </div>
                                    </div> <!-- Fim - Div Itens de atualização de papeis. -->

                                    <div style="display: inline">
                                        <button style="margin-top: 2%; width: 20%;" class="submit" type="submit" title="Atualizar papéis">Atualizar papéis</button> 
                                    </div>
                                    <div style="display: inline">
                                        <button style="margin-top: 2%; width: 20%;" class="reset" type="reset" title="Cancelar">Cancelar</button>                                         
                                    </div>
                                </div> <!-- Fim - Div Container Atualização de papeis. -->
                            </form>
                    </c:if>
                </c:forEach>
            </div>


            <div id="container-nao-membros"> <!-- div Container dos Não membros do projeto. -->
                <fieldset>
                    <legend style="font-weight: bold" >Não Membros</legend>
                    <div><label>Pesquisar por usuários:</label></div>
                    <div>                        
                        <form method="post" action="atribuirEquipe.jsp">                            
                            <div>
                                <div style="display: inline-block; width: 60%">
                                    <input style="display: inline-block; width: 90%"
                                        type="text" size="20" name="usuarioPesquisado" 
                                        placeholder="Informe usuário para pesquisa"
                                        value="${beanAtribuir.usuarioPesquisado}"/>
                                </div>
                                <div style="display: inline-block; width: 25%; vertical-align: middle;">                                                                
                                    <button class="btn-pesquisar-nao-membro" type="submit" value="Pesquisar" title="Pesquisar nome de usuário">
                                        <img src="imagens/ic_search_24dp_1x.png" alt="Pesquisar"/>
                                        <span>Pesquisar</span> 
                                    </button>                                                                                                 
                                </div>                                                                
                            </div>
                        </form>
                    </div>
                    <c:if test="${!beanAtribuir.usuarioPesquisado.isEmpty()}">
                        <form method="post" action="adicionarMembro">
                            <c:forEach var="naoMembro" items="${beanAtribuir.naoMembros}">                                
                                <div>
                                    <div class="item-lista-nao-membros">
                                        <div class="checkbox-nome-nao-membro" >                                    
                                            <input type="checkbox" value="${naoMembro.id}" 
                                                   id="nomeNaoMembro" name="nomeNaoMembro"/>                                
                                        </div>
                                        <div class="label-nome-nao-membro"  >
                                            <label for="nomeNaoMembro">${naoMembro.nome}</label>
                                        </div>                                        
                                    </div>
                                </div>
                            </c:forEach>

                            <div>
                                <div>
                                    <h6 style="font-weight: bold; margin-bottom: 0px" >Papéis:</h6>
                                </div>
                                <div class="item-lista-nao-membro">
                                    <div class="checkbox-nome-nao-membro">
                                        <input type="checkbox" value="GPR" name="papeis"
                                               id="papelGPR"/>
                                    </div>
                                    <div class="label-nome-nao-membro">
                                        <label for="papelGPR">GPR</label>
                                    </div>
                                </div>

                                <div class="item-lista-nao-membro">
                                    <div class="checkbox-nome-nao-membro">
                                        <input type="checkbox" value="MEG" name="papeis"
                                               id="papelMEG"/>
                                    </div>
                                    <div class="label-nome-nao-membro">
                                        <label for="papelMEG">MEG</label>
                                    </div>
                                </div>

                                <div class="item-lista-nao-membro">
                                    <div class="checkbox-nome-nao-membro">
                                        <input type="checkbox" value="MEM" name="papeis"
                                               id="papelMEM"/>
                                    </div>
                                    <div class="label-nome-nao-membro">
                                        <label for="papelMEM">MEM</label>                                        
                                    </div>
                                </div>
                            </div>                            
                            <div>                                
                                <button style="margin-top: 2%; width: 20%;" class="button" type="submit" value="Adicionar" title="Adicionar Membro ao Projeto">Adicionar</button> 
                            </div>
                        </form>
                    </c:if>
                </fieldset>
            </div>  <!-- div.container-nao-membros-->
        </div>
    </body>
    <%@include file="/rodape.jsp" %>
</html>
