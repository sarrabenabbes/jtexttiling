<%@ page language="java" import="es.project.bd.objetos.Archivo,es.project.bd.objetos.Usuario" pageEncoding="ISO-8859-1"%>
<%@ include file="./cabecera.jsp" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

	<title>Servicio web para el uso del algoritmo JTextTiling - Lista de Archivos</title>
	
	<c:choose>
	<c:when test="${sessionScope.root == true}">
	
	<%	request.getSession().setAttribute("botonSalir",true); 
		EstadisticasArchivos estA = new EstadisticasArchivos();
		List<Archivo> listaRoot = estA.getLista();
		request.setAttribute("lista",listaRoot);
		request.setAttribute("numArchivos", estA.getNum());
	%>
	
		<div class="table">
			<table>
				<tr>
					<td><a href="./bienvenida.jsp">Estadísticas de Usuarios</a></td>
					<td><a href="./listaUsuarios.jsp">Lista de usuarios</a></td>
					<td class="actual"><a>Lista de archivos</a></td>
					<td><a href="./eliminarUsuario.jsp">Eliminar Usuarios</a><br /></td>
					<td><a href="./eliminarArchivo.jsp">Eliminar archivos</a></td>
				</tr>
			</table>
		</div>
		
		<c:choose>
		<c:when test="${numArchivos > 0}">
		<div class="divPrincipalRoot">
			<p id="listArchivosMensaje">
				Lista de archivos:
			</p>	

			<div class="displayTag">
				<display:table name="${lista}" class="displayArchivos"> 
					<display:column property="nombreArchivo" title="Archivo" sortable="true"/>
					<display:column property="nombrePropietario" title="Propietario" sortable="true"/>
					<display:column property="rutaArchivo" title="Ruta" autolink="true" sortable="true"/>
				</display:table>
			</div>

  		</div>
  		</c:when>
  	
  		<c:otherwise>
  			<div class="divPrincipal">
  				<p>No hay archivos en el servidor</p>
  			</div>
  		</c:otherwise>
  	</c:choose>
	</c:when>
	
	<c:when test="${sessionScope.usuarioActivo == true and sessionScope.root == false}">
	<%request.getSession().setAttribute("botonSalir",true); %>
	
		<%
			List<Archivo> lista = (List<Archivo>)request.getSession().getAttribute("listaArchivos"); 
			request.setAttribute("lista",lista);
			request.setAttribute("numArchivos", lista.size());
		%>
		
	<div class="table">
		<table>
			<tr>
				<td><a href="./bienvenida.jsp">Perfil de Usuario</a></td>
				<td class="actual"><a>Lista Archivos</a></td>
				<td><a href="./verArchivo.jsp">Ver Archivos</a></td>
				<td><a href="./aplicarAlgoritmo.jsp">Aplicar algoritmo</a></td>
				<td><a href="./subirArchivo.jsp">Subir archivo</a></td>
				<td><a href="./eliminarArchivo.jsp">Eliminar archivos</a><br /></td>
			</tr>
		</table>
	</div>
	
	<c:choose>
		<c:when test="${numArchivos > 0}">
		<div class="divPrincipal">
			<p id="listArchivosMensaje">
				Lista de archivos:
			</p>	

			<div class="displayTag">
				<display:table name="${lista}" class="displayArchivos"> 
					<display:column property="nombreArchivo" title="Archivo" sortable="true"/>
					<display:column property="rutaArchivo" title="Ruta" autolink="true" sortable="true"/>
				</display:table>
			</div>

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
 	 <%request.getSession().setAttribute("botonSalir",false); %>
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
