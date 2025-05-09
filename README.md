<h4>Caso de prueba 2:</h4>
<p>En el presente caso se optó por utilizar <b>JAX-RS</b> (javax.ws.rs) como dependencia principal para la construcción del servicio y <b>GlassFish</b> (concretamente la version 5.1) como servidor de aplicaciones, dados los requisitos del desafío: excluir el uso de <b>Spring Boot</b>.</p>
<p>JAX-RS forma parte del estándar <b>Java EE</b> y proporciona una forma clara, modular y robusta de construir servicios web RESTful, utilizando anotaciones como <code>@Path</code>, <code>@GET</code>, <code>@POST</code>, entre otras. Al tratarse de una tecnología nativa del entorno Java EE, evita dependencias externas y facilita una arquitectura limpia.</p>
<h4>Instrucciones:</h4>
<ul>
	<li>
		Se debe contar con Glassfish 5.1 (<code><a href="https://www.eclipse.org/downloads/download.php?file=/glassfish/glassfish-5.1.0.zip">Eclipse GlassFish 5.1.0, Full Profile</a></code>). 
		<ul>
			<li>Glassfish se aloja por defecto en el puerto <code>4848</code>.</li>
		</ul>
	</li>
	<li>Para inicializar Glassfish: navegue a la carpeta <code>[Ruta en la que se guardo Glassfish]\glassfish5\glassfish\config</code> y ejecute: <code>asadmin start-domain</code></li>
	<li>
		Se incluye la aplicacion en formato <code>.war</code> para su despliegue rapido, en la carpeta <code>Outputs</code>. (para empaquetar la applicacion desde el codigo, ejecutar <code>mvn clean package</code>)
	</li>
	<li>
		En el panel de control de Glassfish se debe crear un JDBC connection pool (<code>JDBC > JDBC Connection Pools > New</code>) con el nombre de la conexion que la aplicacion web espera: <code>jdbc/APIDatasource</code> y en Glassfish, se deben definir las propiedades de la base de datos que usualmente se configuran en <code>persistence.xml</code> o <code>application.properties</code> en el caso de Spring.
	</li>
	<li>Para desplegar la aplicacion, ubicado en <code>[Ruta en la que se guardo Glassfish]\glassfish5\glassfish\config</code>, ejecute: <code>asadmin deploy [ruta del archivo .war]</code></li>
	<li>
		Se incluyen los queries como en la ocasion anterior en la carpeta <code>SQLServerQueries</code>.
	</li>	
	<li>
		Finalmente se incluye una coleccion en <b>Postman</b> en la carpeta <code>Outputs</code>.<br>
		<b>Nota:</b> Las direcciones puede variar segun el entorno de ejecucion ya que Glassfish utiliza el formato <code>[SQL Server name]:[port]/[route]</code>, la ruta se encuentra en el panel de control de Glassfish en <code>Applications > [Nombre de la aplicacion] > Lauch</code>
	</li>
</ul>