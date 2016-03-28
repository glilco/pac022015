<%-- 
    Document   : erro
    Created on : 28/03/2016, 13:06:42
    Author     : Danillo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>
            <c:out value="${sessionScope.errorMessage}"/>
        </h1>
    </body>
</html>
