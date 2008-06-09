<%@ page language="java" 
		 import="java.util.*,es.project.bd.objetos.Archivo,
		 		es.project.bd.objetos.Usuario,es.project.facade.FacadeBD,
		 		es.project.root.estadisticas.*" 
		 pageEncoding="ISO-8859-1"
		 errorPage="./errorDesconocido.jsp"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head id="cabecera">
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" href="./estilo/estilo.css">
	
	<p class="pImagen"> 
		<html:img src="./includes/images/JTextTiling01.png"  height="120" width="906"/>
    </p>
	<p class="pEnlace"> 
		<a class="aTextTiling" href="http://people.ischool.berkeley.edu/~hearst/tiling-about.html" target="_blank">
			TextTiling
		</a>
		<a class="aTextTiling" href="http://citeseer.ist.psu.edu/hearst93texttiling.html" target="_blank">
			TextTiling 2
		</a>
		<a class="aTextTiling" href="http://portal.acm.org/citation.cfm?id=972687&dl=GUIDE" target="_blank">
			TextTiling 3
		</a>
	</p> 
	
		<%
		boolean activo = false;
		if (request.getSession().getAttribute("usuarioActivo") != null) 
			activo = (Boolean)request.getSession().getAttribute("usuarioActivo");
		%>
		
		<c:if test="<%=activo %>">
		<%
			String mensaje;
			if (request.getSession().getAttribute("usuarioActual") != null) {
	  			Usuario usuario = (Usuario)request.getSession().getAttribute("usuarioActual");
	  			mensaje = "Usuario en sesión: " + usuario.getNombre();
	  		} else mensaje = "Sesión no iniciada";
		%>
			<p class="pHeader"><%=mensaje %></p>
		</c:if>
		
		<c:if test="<%=!activo %>">
			<p class="pHeader">Sesión no iniciada</p>
		</c:if> 
		
		<%@include file="./scripts.jsp" %>
</head>

<body class="bodyClass">
	
	
	
	
 