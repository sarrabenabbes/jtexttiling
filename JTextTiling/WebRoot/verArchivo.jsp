<%@ page language="java" 
		 import="es.project.bd.objetos.Archivo,es.project.ficheros.configuracion.ConfigFicheros" 
		 pageEncoding="ISO-8859-1"%>
		 
<%@ include file="./cabecera.jsp" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

	<title>Servicio web para el uso del algoritmo JTextTiling - Modificar Archivos</title>
	<c:choose>
		<c:when test="${sessionScope.usuarioActivo == true}">
		<% request.getSession().setAttribute("botonSalir",true); 
		   String programa = ConfigFicheros.getPrograma();
		   boolean esMultiple = programa.compareToIgnoreCase("multiple") == 0;
		   request.setAttribute("multiple",esMultiple);
		%>
		
		<script type="text/javascript">
			window.setInterval("cambiaDefectoCheck('nombreArchivos','botonVer1')",50);
			//window.setInterval("cambiaDefecto()",50);
		</script>
		
			<div class="table">
				<table>
					<tr>
						<td><a href="./bienvenida.jsp">Perfil de Usuario</a></td>
						<td><a href="./listaArchivos.jsp">Lista Archivos</a></td>
						<td class="actual"><a>Ver Archivos</a></td>
						<td><a href="./aplicarAlgoritmo.jsp">Aplicar algoritmo</a></td>
						<td><a href="./subirArchivo.jsp">Subir archivo</a></td>
						<td><a href="./eliminarArchivo.jsp">Eliminar archivos</a><br /></td>
					</tr>
				</table>
			</div>
			
			<% 
				List<Archivo> lista = (List<Archivo>)request.getSession().getAttribute("listaArchivos"); 
				request.setAttribute("lista",lista);
				request.setAttribute("numArchivos", lista.size());
				Archivo a = new Archivo();
				a.setAccesoRoot(false);
			%>
			<c:choose>
			<c:when test="${numArchivos > 0}">
			<div class="divPrincipal">
				<table>
				<tr>
				<td width="450">
				<p>Elija los archivos a visualizar:</p> 
				<c:choose>
					<c:when test="${multiple == true}">
						<html:form action="/VerArchivosMultiple">
							<p>
								<c:forEach items="${lista}" var="item">
		   							<html:checkbox property="nombreArchivos" value="${item}">${item}</html:checkbox><br />
		   						</c:forEach>
							</p> 
					
							<p class="enviar">
								<html:submit styleId="botonVer1" value="Ver"/>
							</p>
						</html:form>
					</c:when>
					<c:otherwise>
						<html:form action="/VerArchivosSimple">
							<p>
								<c:forEach items="${lista}" var="item" >
		   							<html:radio property="nombreArchivo" value="${item}">${item}</html:radio><br />
		   						</c:forEach>
							</p> 
					
							<p class="enviar">
								<html:submit styleId="botonVer2" value="Ver"/>
							</p>
						</html:form>
					</c:otherwise>
				</c:choose>
				</td>
				<td width="350">
				<a href="http://localhost:8080/JTextTiling/EnviaCorreo.do">
				Envíame los archivos al correo</a>
				</td>
				</tr>
				</table>
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