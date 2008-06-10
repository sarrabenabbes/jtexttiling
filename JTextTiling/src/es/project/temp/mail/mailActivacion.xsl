<?xml version = "1.0"?>
	<xsl:stylesheet version ="1.0" xmlns:xsl=
	"http://www.w3.org/1999/XSL/Transform">
	
	<xsl:template match = "/">
		<html>
			<body>
				<p>Estos son sus datos de usuario:<br />
				Nombre:<xsl:value-of select="//usuario/nombre"/><br />
				Password:<xsl:value-of select="//usuario/password"/><br /></p>
				Para confirmar su alta 
				<a>
					<xsl:attribute name="href">
					<xsl:value-of select="//usuario/url"/>
					</xsl:attribute>
					siga el enlace
				</a>
			</body>
		</html>
	</xsl:template>
	
</xsl:stylesheet>