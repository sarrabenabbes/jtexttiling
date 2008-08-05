<%@ include file="./cabecera.jsp" %>
<%request.getSession().setAttribute("botonSalir",false); %>

<script type="text/javascript">
	window.setInterval("cambiaDefecto2()",50);
</script>
 		
<title>Servicio web para el uso del algoritmo JTextTiling - Nuevo Usuario</title>
	<h1>Dar de alta un usuario</h1>
	
	<div class="divPrincipal">
   	 <html:form action="/AltaUsuario">
    
    	<table>
    	<tr>
    		<td>
    		<p class="nombreUsuario">
    			Nombre de Usuario:<br />
				<html:text styleId="nombre" property="nombreUsuario">
				</html:text> 
			</p>
			</td>
		</tr>
		<tr>
			<td>
			<p class="password">
				Password:<br />
				<html:password styleId="pass1" property="password">
				</html:password>
			</p>
			</td>
			<td>
			<p class="password">
				Repita su password:<br />
				<html:password styleId="pass2" property="password2">
				</html:password><a href="#" onclick="window.open('./avisoPassword.html','','width=400,height=200')">(?)</a>
			</p>
			</td>	
		</tr>
		<tr>
			<td>
			<p class="email">
				Dirección de email:<br />
				<html:text styleId="mail" property="email">
				</html:text><a href="#" onclick="window.open('./avisoMail.html','','width=400,height=200')">(?)</a>
			</p>
			</td>
		</tr>
		</table>
		
		<p class="enviar">
			<html:submit styleId="botonCrear" disabled="true" value="Crear"/>
		</p>
		
   	 </html:form>
    
   	 <a href="./index.jsp">Volver al inicio</a>
    </div>

<%@ include file="./pie.jsp" %>
