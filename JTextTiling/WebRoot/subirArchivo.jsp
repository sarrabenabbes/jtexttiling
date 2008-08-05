<%@ include file="./cabecera.jsp" %>

	<title>Servicio web para el uso del algoritmo JTextTiling - Subir Archivos</title>
	<c:choose>
	<c:when test="${sessionScope.usuarioActivo == true}">
	<%request.getSession().setAttribute("botonSalir",true); %>
	
	<script type="text/javascript"> 
   		window.setInterval("cambiaDefecto7()",50);
   	</script>
   	
	<div class="table">
		<table>
			<tr>
				<td><a href="./bienvenida.jsp">Perfil de Usuario</a></td>
				<td><a href="./listaArchivos.jsp">Lista de archivos</a></td>
				<td><a href="./verArchivo.jsp">Ver Archivos</a></td>
				<td><a href="./aplicarAlgoritmo.jsp">Aplicar algoritmo</a></td>
				<td class="actual"><a>Subir archivo</a></td>
				<td><a href="./eliminarArchivo.jsp">Eliminar archivos</a><br /></td>
			</tr>
		</table>
	</div>
	
	<div class="divPrincipal">
	 <html:form action="/CargaArchivo" method="post" enctype="multipart/form-data"> 
    	<p>Ruta del archivo:</p>
    	
    	<p>
    		<html:file styleId="nombreArchivo" property="archivo">
    		</html:file>
		</p> 
 		<p class="enviar">
    		<html:submit styleId="botonEnviar" value="Enviar"/>
    	</p>
    </html:form> 
    </div>
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