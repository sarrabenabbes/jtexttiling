<%@ include file="./cabecera.jsp" %>

	<title>Servicio web para el uso del algoritmo JTextTiling - Aplicar Algoritmo</title>
	<c:choose>
		<c:when test="${sessionScope.usuarioActivo == true}">
		<%request.getSession().setAttribute("botonSalir",true); %>
		
		<script type="text/javascript"> 
   			window.setInterval("cambiaDefectoCheck('nombreArchivo','botonAplicar')",50);
   		</script>	
   		
		<div class="table">
		<table>
			<tr>
				<td><a href="./bienvenida.jsp">Perfil de Usuario</a></td>
				<td><a href="./listaArchivos.jsp">Lista de archivos</a></td>
				<td><a href="./verArchivo.jsp">Ver Archivos</a></td>
				<td class="actual"><a>Aplicar Algoritmo</a></td>
				<td><a href="./subirArchivo.jsp">Subir archivo</a></td>
				<td><a href="./eliminarArchivo.jsp">Eliminar archivos</a><br /></td>
			</tr>
		</table>
		</div>
	
	<%
		List<Archivo> lista = (List<Archivo>)request.getSession().getAttribute("listaArchivos"); 
		request.setAttribute("lista",lista);
		request.setAttribute("numArchivos", lista.size());
	 %>
	
	<c:choose>
	<c:when test="${numArchivos > 0}">
	<div class="divPrincipal">
		<p>Elija los archivos a los que quiere aplicar el algoritmo: </p>

		<html:form action="/AplicarAlgoritmo">
		<p>
			<c:forEach items="${lista}" var="item" >
		 	  	<html:checkbox property="nombreArchivo" value="${item}">${item}</html:checkbox><br />
   	    	</c:forEach>
   	    </p>
   	    
   	    <table>
   	    	<tr>
   	    		<td>
   	    			<p class="window">
   	    			Window:<br />
   	    			<html:text property="window"></html:text>
   	    			</p>
   	    		</td>
   	    		<td>
   	    			<p class="step">
   	    			Step:<br />
   	    			<html:text property="step"></html:text>
   	    			</p>
   	    		</td>
   	    	</tr>
   	    </table>
	
		<p class="enviar">
			<html:submit styleId="botonAplicar" value="Aplicar" disabled="true"/>
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