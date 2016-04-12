<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="./css/estilo.css">
        <link href='http://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700,800' rel='stylesheet' type='text/css'>
        <meta name="viewport" content="width=divice-width, initial-scale=1.0">
        <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

        <jsp:useBean id="projetoSelecionado" scope="session" class="br.ufg.inf.fabrica.pac.dominio.Projeto"/>
    </head>
    <body>
        <div id="containerHeader">
            <div id="box-1" class="box">	
                <h6>Fábrica de Software - INF</h6> 
            </div>
            <div id="box-2" class="box">
                <h2>Controle de Pacotes</h2>
            </div>
            <div id="box-3" class="box">
                <nav>
                    <ul>
                        <li><a href="index.jsp">home</a> <span>|</span></li>
                        <li>
                            <c:choose>
                                <c:when test="${usuarioLogado!=null}">
                                    ${usuarioLogado.nome} <a href="deslogar" class="linkSair">Sair</a>
                                </c:when>
                                <c:otherwise>

                                    <form action="autenticacao" method="post" class="frmLogin">
                                        <div class="frmLogin">
                                            <input type="text" name="edtUsuario" 
                                                   placeholder="Informe usuário" 
                                                   class="frmLogin">
                                            &nbsp; 
                                            <input type="password" name="edtSenha" 
                                                   placeholder="Informe senha" 
                                                   class="frmLogin">
                                            &nbsp; <input type="submit" 
                                                          value="Confirmar"
                                                          class="frmLogin"/>
                                        </div>
                                    </form>

                                </c:otherwise>
                            </c:choose>

                        </li>

                    </ul>
                </nav><!-- fim nav -->
            </div>
        </div>
        <div class="container">
            <c:if test="${projetoSelecionado.id>0}">
                <div>
                    <span>
                        Projeto de trabalho: ${projetoSelecionado.nome}
                    </span>
                </div>
            </c:if>
            <hr class="hrDivisoria">
        </div>
    </body>
</html>