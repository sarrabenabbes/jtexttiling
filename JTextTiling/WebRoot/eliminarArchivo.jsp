<%@ page language="java" import="es.project.bd.objetos.Archivo" pageEncoding="ISO-8859-1"%>
<%@ include file="./cabecera.jsp" %>

<script type="text/javascript">
	window.setInterval("cambiaDefectoCheck('nombreArchivos','botonBorrar')",50);
</script>
		
<title>Servicio web para el uso del algoritmo JTextTiling - Eliminar Archivos</title>
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
					<td class="actual"><a>Eliminar archivos</a></td>
				</tr>
			</table>
		</div>
		
		<%
		EstadisticasArchivos estA = new EstadisticasArchivos();
		List<Archivo> listaRoot = estA.getLista(); 
		request.getSession().setAttribute("listaArchivos",listaRoot);
		request.setAttribute("lista", listaRoot);
		request.setAttribute("numArchivos", estA.getNum());
		Archivo a = new Archivo();
		a.setAccesoRoot(true);
		%>
		<c:choose>
			<c:when test="${numArchivos > 0}">
				<div class="divPrincipalRoot">
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
			<div class="divPrincipalRoot">
  				<p>No hay archivos en el servidor</p>
  			</div>
		</c:otherwise>
		</c:choose>
	</c:when>

	<c:when test="${sessionScope.usuarioActivo == true and sessionScope.root == false}">
	<%request.getSession().setAttribute("botonSalir",true); %>
	
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
		FacadeBD facadeBD = new FacadeBD();
		Usuario actual = (Usuario)request.getSession().getAttribute("usuarioActual");
		List<Archivo> lista = facadeBD.getArchivosPorUsuario(actual);
		request.setAttribute("lista",lista);
		request.setAttribute("numArchivos",lista.size());
		Archivo b = new Archivo();
		b.setAccesoRoot(false);
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
