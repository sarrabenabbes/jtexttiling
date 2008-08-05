<%@ include file="./cabecera.jsp" %>
<%request.getSession().setAttribute("botonSalir",false); %>
 		
<script type="text/javascript">
	window.setInterval("cambiaDefecto1()",50);
</script>

<title>Servicio web para el uso del algoritmo JTextTiling - Inicio</title>
	<h1>Página de inicio</h1>
	
	<div class="divPrincipal">
		
		<html:form action="/ComprobarUsuario">  
		
			<p class="nombreUsuario">
				Nombre de Usuario:   
				<br />
				<html:text styleId="nombre" property="nombreUsuario">
				</html:text> 
			</p>
			
			<p class="password">
				Password:<br />
				<html:password styleId="pass" property="password">
				</html:password>
			</p>
			
			<p class="enviar">
				<html:submit styleId="botonEnviar" value="Enviar" disabled="true"/>
			</p>
			
		</html:form>
		
		<a href="./nuevoUsuario.jsp">Nuevo Usuario</a>
		
	</div>
	
<%@ include file="./pie.jsp" %>
