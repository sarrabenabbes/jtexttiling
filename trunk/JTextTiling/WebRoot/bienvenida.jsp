<%@ include file="./cabecera.jsp" %>
   
    <title>Servicio web para el uso del algoritmo JTextTiling - Bienvenida</title>
    <c:choose>
    	<c:when test="${sessionScope.root == true}">
    	<%request.getSession().setAttribute("botonSalir",true); %>
    	
    	<div class="table">
			<table>
				<tr>
					<td class="actual"><a>Estadísticas de Usuarios</a></td>
					<td><a href="./listaUsuarios.jsp">Lista de usuarios</a></td>
					<td><a href="./listaArchivos.jsp">Lista de archivos</a></td>
					<td><a href="./eliminarUsuario.jsp">Eliminar Usuarios</a><br /></td>
					<td><a href="./eliminarArchivo.jsp">Eliminar archivos</a></td>
				</tr>
			</table>
		</div>
    	
    	<%
    		FacadeBD facade = new FacadeBD();
			EstadisticasUsuarios estU = new EstadisticasUsuarios();
			EstadisticasArchivos estA = new EstadisticasArchivos();
			request.setAttribute("numArchivos",estA.getNum());
			request.setAttribute("numUsuarios",estU.getNum());
			Usuario aux1 = facade.getDatosUltimaAlta();
			Usuario aux2 = facade.getDatosUltimoLogin();
			String ultimaAlta = (aux1 != null)?(aux1.getNombre()):("no ha habido altas");
			String ultimoLogin = (aux2 != null)?(aux2.getNombre()):("no ha habido logins");
			request.setAttribute("ultimaAlta", ultimaAlta);
			request.setAttribute("ultimoLogin", ultimoLogin);
		%>
		<div class="divPrincipalRoot">
			<table>
    			<tr>
    				<td width="420">- Número de archivos totales en el servidor: ${requestScope.numArchivos}</td>
    				<td>- Último usuario que ha utilizado el servicio: ${ultimoLogin}</td>
    			</tr>
    			<tr>
    				<td width="420">- Número de usuarios totales del servicio: ${requestScope.numUsuarios}</td>
    				<td>- Último usuario en darse de alta en el servicio: ${ultimaAlta}</td>
    			</tr>
    			<tr>
    				<td><a href="./servlet/JunitTester" target="_blank">Ejecutar pruebas JUnit</a></td>
    			</tr>
    		</table>
		</div>
    	</c:when>
    	
    	<c:when test="${sessionScope.usuarioActivo == true and sessionScope.root == false}">
    	<%request.getSession().setAttribute("botonSalir",true); %>
    	
    	<div class="table">
		<table>
			<tr>
				<td class="actual"><a>Perfil de Usuario</a></td>
				<td><a href="./listaArchivos.jsp">Lista de archivos</a></td>
				<td><a href="./verArchivo.jsp">Ver Archivos</a></td>
				<td><a href="./aplicarAlgoritmo.jsp">Aplicar Algoritmo</a></td>
				<td><a href="./subirArchivo.jsp">Subir archivo</a></td>
				<td><a href="./eliminarArchivo.jsp">Eliminar archivos</a><br /></td>
			</tr>
		</table>
		</div>
	
		<%
			List<Archivo> lista = (List<Archivo>)request.getSession().getAttribute("listaArchivos"); 
			request.setAttribute("lista",lista);
			request.setAttribute("numArchivos", lista.size());
			Usuario user = (Usuario)request.getSession().getAttribute("usuarioActual");
			FacadeBD facadeBD = new FacadeBD();
			String mail = facadeBD.getEmail(user);
			request.setAttribute("mail",mail);
			String fecha;
			String fechaAlta = facadeBD.getFechaAlta(user);
			request.setAttribute("fechaAlta",fechaAlta);
			try {
				fecha = facadeBD.getUltimoLogin(user);
			} catch (Exception e) {
				fecha = "primer login";
			}
			request.setAttribute("ultimoLogin", fecha);
	 	%>
    	<div class="divPrincipal">
    		<table>
    			<tr>
    				<td width="350">¡Hola ${sessionScope.usuarioActual}!</td>
    				<td><a href="./actualizarUsuario.jsp">Actualice sus datos</a></td>
    			</tr>
    			<tr>
    				<td width="350">- Número de archivos en el servidor: ${requestScope.numArchivos}</td>
    			</tr>
    			<tr>
    				<td width="400">- Cuenta de mail utilizada: ${mail}</td>
    			</tr>
    			<tr>
    				<td width="350">- Fecha de alta: ${fechaAlta}</td>
    			</tr>
    			<tr>
    				<td width="350">- Fecha del último login: ${requestScope.ultimoLogin}</td>
    			</tr>
    		</table>
    	</div>
    	</c:when>
    	
    	<c:otherwise>
    	<%request.getSession().setAttribute("botonSalir",true); %>
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