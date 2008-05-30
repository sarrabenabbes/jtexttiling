	<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
	<c:choose>
		<c:when test="${sessionScope.botonSalir == true}">
			<div class="divSalir">
			<html:form action="/Salir">
				<p class="enviar">
					<html:submit value="Salir"/>
				</p>
    		</html:form>
    		</div>
    	</c:when>
    	
    	<c:otherwise>
    		<h1>CIERRE</h1>
    	</c:otherwise>
	</c:choose>
	</body> 
</html>