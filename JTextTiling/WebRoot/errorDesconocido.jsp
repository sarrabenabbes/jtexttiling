<%@ include file="./cabecera.jsp" %>

	<c:choose>
	<c:when test="${sessionScope.usuarioActivo == true}">
	<%request.getSession().setAttribute("botonSalir",true);%>
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
	
	<div class="divPrincipal">
		<p>- Ha ocurrido un error inesperado: compruebe que los valores que haya introducido
		son correctos y que no está accediendo a una dirección que requiera autenticación</p>
		<p>- La red puede estar congestionada, espere unos minutos y vuelva a intentarlo</p>
	</div>

<%@ include file="./pie.jsp" %>