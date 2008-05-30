<%@ page language="java" import="es.project.bd.objetos.Archivo" pageEncoding="ISO-8859-1"%>
<%@ include file="./cabecera.jsp" %>

<title>Servicio web para el uso del algoritmo JTextTiling - Eliminar Archivos</title>
<c:choose>
	<c:when test="${sessionScope.usuarioActivo == true}">
	<%request.getSession().setAttribute("botonSalir",true); %>
	
		<script type="text/javascript">
			window.setInterval("cambiaDefectoCheck('nombreArchivos','botonBorrar')",50);
		</script>
		
	<div class="table">
		<table>
			<tr>
				<td><a href="./bienvenida.jsp">Perfil de Usuario</a></td>
				<td><a href="./listaArchivos.jsp">Lista de archivos</a><br /></td>
				<td><a href="./verArchivo.jsp">Ver Archivos</a></td>
				<td><a href="./aplicarAlgoritmo.jsp">Aplicar algoritmo</a></td>
				<td><a href="./subirArchivo.jsp">Subir archivo</a></td>
				<td class="actual"><a>Eliminar Archivos</a></td>
			</tr>
		</table>
	</div>
	
	<%
		List<Archivo> lista = (List<Archivo>)request.getSession().getAttribute("listaArchivos"); 
		request.setAttribute("lista",lista);
		request.setAttribute("numArchivos",lista.size());
	%>
	<c:choose>
	<c:when test="${numArchivos > 0}">
	<div class="divPrincipal">
		<p>Elige los archivos a borrar:</p> 
		<html:form action="/BorrarArchivo">
			<p>
				<c:forEach items="${lista}" var="item" >
		   			<html:checkbox property="nombreArchivos" value="${item}">${item}</html:checkbox><br />
   	   			</c:forEach>
			</p> 
			<p class="enviar">
				<html:submit styleId="botonBorrar" value="Borrar" disabled="true"/>
			</p>
		</html:form>
	</div>
	</c:when>
	
	<c:otherwise>
		<div class="divPrincipal">
  			<p>No tiene archivos</p>
  		</div>
	</c:otherwise>
	</c:choose>
</c:when>
<c:otherwise>
    <div class="table">
		<a href="./index.jsp">Inicio</a>
	</div>
    <div class="divPrincipal">
    	<p>No hay ningún usuario activo: inicie sesión o regístrese
    	como usuario nuevo</p> 
    </div>
</c:otherwise>
</c:choose>

<%@ include file="./pie.jsp" %>
