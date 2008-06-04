<%@ include file="./cabecera.jsp" %>
   
    <title>Servicio web para el uso del algoritmo JTextTiling - Actualizar sus Datos</title>
    <c:choose>
    	<c:when test="${sessionScope.usuarioActivo == true}">
    	<%request.getSession().setAttribute("botonSalir",true); %>
   		
   		<script type="text/javascript"> 
   			window.setInterval("cambiaDefecto6()",50);
   		</script>	
   
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
		
		<div class="divPrincipal">
			<p>Ayuda <a href="#" onclick="window.open('./ayudaActualizar.html','','width=680,height=280')">(?)</a></p>
			<html:form action="/ActualizarUsuario">
				<table width="576" height="148">
					<tr>
    					<td>
    						<p class="nombreUsuario">
    							Nuevo nombre de Usuario:<br />
								<html:text styleId="nombre" property="nombreUsuario">
								</html:text> 
							</p>
						</td>
					</tr>
					<tr>
						<td>
							<p class="password">
								Nuevo password:<br />
								<html:password styleId="pass1" property="password">
								</html:password>
							</p>
						</td>
						<td>
							<p class="password">
								Repita su nuevo password:<br />
								<html:password styleId="pass2" property="password2">
								</html:password>
							</p>
						</td>
					</tr>
					<tr>
						<td>
							<p class="email">
								Nueva dirección de email:<br />
								<html:text styleId="mail" property="email">
								</html:text>
							</p>
						</td>
					</tr>
				</table>
				
				<p class="enviar">
					<html:submit value="Guardar" styleId="botonGuardar" disabled="true"/>
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