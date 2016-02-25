<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="./css/estilo.css">
 	<link href='http://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700,800' rel='stylesheet' type='text/css'>
	<meta name="viewport" content="width=divice-width, initial-scale=1.0">
	<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	
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
		          <li><a href="usuario"></a>
		          	<c:choose>
	    				<c:when test="$(sessaoUsuario!=null)">
	        				$(sessaoUsuario)
	    				</c:when>
	        			<c:otherwise>
	            			<span class="spanUser">Usuário não logado</span>
	        			</c:otherwise>
					</c:choose>
					<span>|</span>
		          </li>
		          <li><a href="sair">Sair</a></li>
		     </ul>
		</nav><!-- fim nav -->
		</div>
	</div>
	<div class="container">
		<hr class="hrDivisoria">
	</div>
</body>
</html>