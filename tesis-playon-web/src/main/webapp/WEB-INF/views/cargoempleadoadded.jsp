<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cargo de empleado agregado</title>
</head>
<body>

	<h1>Cargos de empleado de playas de estacionamiento</h1>

	<p>Ha agregado un nuevo cargo de empleado de playas de estacionamiento al sistema: <%= new java.util.Date() %></p>
	
	<p><c:out value="${mensaje}" /></p>
	
	<c:url var="mainUrl" value="/welcome" />
	<p>Regresar a la <a href="${mainUrl}">p�gina principal</a></p>

</body>
</html>