<%@ page language="java" import="es.project.bd.objetos.Usuario" pageEncoding="ISO-8859-1"%>
<%@ include file="./cabecera.jsp" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

	<title>Servicio web para el uso del algoritmo JTextTiling - Lista de Usuarios</title>
	
	<c:choose>
		<c:when test="${sessionScope.root == true}">
		<%request.getSession().setAttribute("botonSalir",true);%> 
			<div class="table">
				<table>
					<tr>
						<td><a href="./bienvenida.jsp">Estadísticas de Usuarios</a></td>
						<td class="actual"><a>Lista de usuarios</a></td>
						<td><a href="./listaArchivos.jsp">Lista de archivos</a></td>
						<td><a href="./eliminarUsuario.jsp">Eliminar Usuarios</a><br /></td>
						<td><a href="./eliminarArchivo.jsp">Eliminar archivos</a></td>
					</tr>
				</table>
			</div>
			
			<%
				EstadisticasUsuarios estU = new EstadisticasUsuarios();
				request.setAttribute("lista",estU.getLista());
				request.setAttribute("numUsuarios", estU.getNum());
				System.out.println("usuarios: " + estU.getLista().size());
			 %>
			 
			 <c:choose>
				<c:when test="${requestScope.numUsuarios > 0}">
					<div class="divPrincipalRoot">
						<p id="listArchivosMensaje">
							Lista de usuarios:
						</p>	

						<div class="displayTag">
							<display:table name="${lista}" class="displayArchivos"> 
								<display:column property="nombre" title="Usuario" sortable="true"/>
								<display:column property="email" title="Mail" autolink="true" sortable="true"/>
								<display:column property="fecha_alta" title="Fecha de alta" sortable="true"/>
								<display:column property="ultimo_login" title="Último Login" sortable="true"/>
							</display:table>
						</div>

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
 	 	<%request.getSession().setAttribute("botonSalir",false); %>
    	<div class="table">
			<a href="./index.jsp">Inicio</a>
		</div>
    	<div class="divPrincipal">
    		<p>No tiene permisos para acceder a esta página</p> 
    	</div>
    </c:otherwise>
	</c:choose>
	
<%@ include file="./pie.jsp" %>