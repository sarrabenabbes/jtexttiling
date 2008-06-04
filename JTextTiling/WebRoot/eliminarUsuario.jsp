<%@ page language="java" import="es.project.bd.objetos.Usuario" pageEncoding="ISO-8859-1"%>
<%@ include file="./cabecera.jsp" %>

<script type="text/javascript">
	window.setInterval("cambiaDefectoCheck('nombreUsuarios','botonBorrar')",50);
</script>

<title>Servicio web para el uso del algoritmo JTextTiling - Eliminar Usuarios</title>

	<c:choose>
		<c:when test="${sessionScope.root == true}">
			<%request.getSession().setAttribute("botonSalir",true);%> 
			<div class="table">
				<table>
					<tr>
						<td><a href="./bienvenida.jsp">Estadísticas de Usuarios</a></td>
						<td><a href="./listaUsuarios.jsp">Lista de usuarios</a></td>
						<td><a href="./listaArchivos.jsp">Lista de archivos</a></td>
						<td class="actual"><a>Eliminar Usuarios</a><br /></td>
						<td><a href="./eliminarArchivo.jsp">Eliminar archivos</a></td>
					</tr>
				</table>
			</div>
			
			<%
				EstadisticasUsuarios estU = new EstadisticasUsuarios();
				List<Usuario> listaRoot = estU.getLista(); 
				request.setAttribute("lista", listaRoot);
				request.setAttribute("numArchivos", estU.getNum());
			%>
			
			<c:choose>
			<c:when test="${numArchivos > 0}">
				<div class="divPrincipalRoot">
					<p>Elige los usuarios a borrar:</p> 
					<html:form action="/BorrarUsuario">
						<p>
							<c:forEach items="${lista}" var="item" >
		   						<html:checkbox property="nombreUsuarios" value="${item}">${item}</html:checkbox><br />
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
  				<p>No hay usuarios en el servidor</p>
  			</div>
		</c:otherwise>
		</c:choose>
			
		</c:when>
		
		<c:otherwise>
   			 <div class="table">
				<a href="./index.jsp">Inicio</a>
			</div>
    		<div class="divPrincipal">
    			<p>No tiene permisos para acceder a esta página</p> 
    		</div>
		</c:otherwise>	
	</c:choose>
<%@ include file="./pie.jsp" %>