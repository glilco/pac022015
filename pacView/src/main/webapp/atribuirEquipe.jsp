<%-- 
    Document   : atribuirEquipe
    Created on : 12/02/2016, 15:29:25
    Author     : Danillo
--%>

<%@page import="br.ufg.inf.fabrica.pac.negocio.dominio.Usuario"%>
<%@page import="java.util.List"%>
<%@page import="br.ufg.inf.fabrica.pac.negocio.dominio.MembroProjeto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<jsp:useBean id="beanAtribuir" scope="session" class="br.ufg.inf.fabrica.pac.view.servlets.beans.BeanAtribuirEquipe" />
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
        <div class="container">
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
                                    <button class="btn-alterar-membro" type="submit" value="Alterar">
                                        <img src="imagens/ic_mode_edit_1x.png" alt="Alterar"/>
                                        Alterar
                                    </button>                                                                                                 
                                </div>                            
                            </form>
                        </div>
                    </c:if>
                    <c:if test="${beanAtribuir.usuarioEmAlteracao==usuario.id}">
                        <form method="post" action="atualizarPermissoesMembro">
                            <p/>
                            ${usuario.nome}
                            <br/>
                            <label for="papelGPR">GPR</label>
                            <input type="checkbox" value="GPR" name="papeis" id="papelGPR"
                                   <c:if test="${usuario.GPR}">checked</c:if> />
                                   <br/>
                                   <label for="papelMEG">MEG</label>
                                   <input type="checkbox" value="MEG" name="papeis" id="papelMEG"
                                   <c:if test="${usuario.MEG}">checked</c:if> />
                                   <br/>
                                   <label for="papelMEM">MEM</label>
                                   <input type="checkbox" value="MEM" name="papeis" id="papelMEM"
                                   <c:if test="${usuario.MEM}">checked</c:if> />
                                   <br/>
                                   <button class="submit" type="submit" value="Atualizar papéis">Atualizar papéis</button>                                   
                                   <p/>
                            </form>
                    </c:if>
                </c:forEach>
            </div>


            <div id="container-nao-membros">
                <fieldset>
                    <legend style="font-weight: bold" >Não Membros</legend>
                    <div><label>Pesquisar por usuários:</label></div>
                    <div>                        
                        <form method="post" action="atribuirEquipe.jsp">                            
                            <div style="display: inline-block; width: 100%">
                                <div style="display: inline-block; width: 80%">
                                    <input style="width: 90%"
                                           type="text" size="20" name="usuarioPesquisado" 
                                           placeholder="Informe usuário para pesquisa"
                                           value="${beanAtribuir.usuarioPesquisado}"/>
                                </div>

                                <!--                    <input type="submit" value="Pesquisar"/>                    -->                                    
                                <div style="display: inline-block; width: 15%; vertical-align: middle">                                
                                    <button class="submit btn-pesquisar-nao-membro" type="submit" title="Pesquisar" value="Pesquisar" >
                                        <span>&nbsp;</span>
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
                                               id="papelMGG"/>
                                    </div>
                                    <div class="label-nome-nao-membro">
                                        <label for="papelMGG">MEG</label>
                                    </div>
                                </div>

                                <div class="item-lista-nao-membro">
                                    <div class="checkbox-nome-nao-membro">
                                        <input type="checkbox" value="MEM" name="papeis"
                                               id="papelMEM"/>
                                    </div>
                                    <div class="label-nome-nao-membro">
                                        <label for="papelGPR">GPR</label>                                        
                                    </div>
                                </div>
                            </div>                            
                            <div>                                
                                <button style="margin-top: 2%; width: 20%;" class="button" type="submit" value="Adicionar" title="Adicionar">Adicionar</button> 
                            </div>
                        </form>
                    </c:if>
                </fieldset>
            </div>  <!-- div.container-nao-membros-->
        </div>
    </body>
    <%@include file="/rodape.jsp" %>
</html>
