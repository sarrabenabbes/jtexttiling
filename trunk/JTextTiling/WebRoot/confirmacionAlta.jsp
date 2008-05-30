<%@ include file="./cabecera.jsp" %>

   <title>Servicio web para el uso del algoritmo JTextTiling - Bienvenida</title>
   
   <%
     request.getSession().setAttribute("botonSalir",false); 
   	 String uuid = request.getParameter("uuid");
   	 FacadeBD facadeBD = new FacadeBD();
   	 Usuario user = facadeBD.getUsuario(facadeBD.getUUID(),uuid);
   	 facadeBD.activarUsuario(user);
     request.getSession().setAttribute("usuarioActivo", true);
	 request.getSession().setAttribute("usuarioActual", user);
	 List<Archivo> listaArchivos = facadeBD.getArchivosPorUsuario(user);
	 request.getSession().setAttribute("listaArchivos", listaArchivos);
   %>
   
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
	
   <div class="divPrincipal">
  	 <p>Enhorabuena, acaba de darse de alta en el servicio</p>
   </div>
<%@ include file="./pie.jsp" %>