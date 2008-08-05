<%@ include file="./cabecera.jsp" %>

	<c:choose>
	<c:when test="${sessionScope.root == true}">
	<%request.getSession().setAttribute("botonSalir",true); %>
		<div class="table">
			<table>
				<tr>
					<td><a href="./bienvenida.jsp">Estadísticas de Usuarios</a></td>
					<td><a href="./listaUsuarios.jsp">Lista de usuarios</a></td>
					<td><a href="./listaArchivos.jsp">Lista de archivos</a></td>
					<td><a href="./eliminarUsuario.jsp">Eliminar Usuarios</a><br /></td>
					<td><a href="./eliminarArchivo.jsp">Eliminar archivos</a></td>
				</tr>
			</table>
		</div>
	
	</c:when>
	
	<c:when test="${sessionScope.usuarioActivo == true and sessionScope.root == false}">
	<%request.getSession().setAttribute("botonSalir",true); %>
		<div class="table">
			<table>
				<tr>
					<td><a href="./bienvenida.jsp">Perfil de usuario</a></td>
					<td><a href="./listaArchivos.jsp"/>Lista de Archivos</td>
					<td><a href="./verArchivo.jsp">Ver Archivos</a></td>
					<td><a href="./aplicarAlgoritmo.jsp">Aplicar algoritmo</a></td>
					<td><a href="./subirArchivo.jsp">Subir archivo</a></td>
					<td><a href="./eliminarArchivo.jsp">Eliminar archivos</a><br /></td>
				</tr>
			</table>
		</div>
	</c:when>
	
	<c:otherwise>	
		<%request.getSession().setAttribute("botonSalir",false); %>
		<div class="table">
			<a href="./index.jsp">Inicio</a>
		</div>
	</c:otherwise>
	</c:choose>
	
	<%
		boolean root = (Boolean)request.getSession().getAttribute("root");
		if (!root) {
	 %>
	<div class="divPrincipal">
	<%} else { %>
	<div class="divPrincipalRoot">
	<%} %>
		<p>
			<html:messages id="msg" property="mensajes" message="true">
				<bean:write name="msg"/><br />
			</html:messages>
		</p>
		
		<p>
			<html:messages id="err" property="errores" message="true">
				<bean:write name="err"/><br />
			</html:messages>
		</p>
	</div>

<%@ include file="./pie.jsp" %>