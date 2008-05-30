package es.project.bd.finales;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import es.project.bd.abstractos.ArchivoDAO;
import es.project.bd.objetos.Archivo;
import es.project.facade.ConectorBD;

/**
 * <p>Implementaci�n de la clase ArchivoDAO particularizada para una base de datos MySQL</p>
 * @author Daniel Fern�ndez Aller
 */
public class MySqlArchivoDAO extends ArchivoDAO{
	/**
	 * <p>Objeto que encapsula las operaciones que se realizan con los objetos de tipo
	 * Connection</p>
	 */
	private ConectorBD conectorBD = new ConectorBD();
	
	/**
	 * <p>Recorre la tabla que almacena la informaci�n sobre los archivos, guarda sus
	 * propiedades en una variable temporal, y la muestra por pantalla.</p>
	 */
	public void verArchivos() {
		Statement stat;
		ResultSet rs;
		Archivo archivo;
		
		try {
			stat = conectorBD.getStatement();
			rs = stat.executeQuery("select * from archivos");
			
			while (rs.next()){
				archivo = new Archivo();
				archivo.setNombreArchivo(rs.getString("nombreArchivo"));
				archivo.setNombrePropietario(rs.getString("nombrePropietario"));
				archivo.setRutaArchivo(rs.getString("rutaArchivo"));
				
				System.out.println(archivo);
			}
			
			rs.close();
			stat.close();
			
		} catch (SQLException sql) {
			System.err.println("MySqlArchivoDAO/Ver Archivos: " + sql.getMessage());
		}
	}
	
	/**
	 * <p>Calcula el n�mero total de archivos mediante la operaci�n "count", y lo
	 * muestra por pantalla</p>
	 * @return N�mero de archivos contenidos en la base de datos
	 */
	public int numeroArchivos()  {
		int numArchivos = 0;
		Statement stat; 
		ResultSet rs;
		
		try {
			stat = conectorBD.getStatement();
			rs = stat.executeQuery("select count(nombreArchivo) total from archivos");
			
			if (rs.next())
				numArchivos = rs.getInt("total");
			
			rs.close();
			stat.close();
			
		} catch (SQLException sql) {
			System.err.println("MySqlArchivoDAO/numeroArchivos: " + sql.getMessage());
		}
		return numArchivos;
	}
	
	/**
	 * <p>Borra toda la informaci�n de la tabla que contiene el nombre, el propietario y la ruta
	 * de los archivos</p>
	 * @return Verdadero si la operaci�n fue bien
	 */
	public boolean borrarArchivos(){
		Statement stat;
		boolean borrados = true;
		
		try {
			stat = conectorBD.getStatement();
			stat.execute("delete from archivos");
			stat.close();
			
		} catch (SQLException sql) {
			System.err.println("MySqlArchivoDAO/borrarArchivos: " + sql.getMessage());
			borrados = false;
		}
		return borrados;
	}

	/**
	 * <p>M�todo que hace de interfaz de la clase con el resto de la aplicaci�n para borrar
	 * un archivo concreto de la base de datos. Llama al m�todo "borrarArchivo", privado, 
	 * con los par�metros necesarios</p>
	 * @param archivo Objeto de tipo Archivo que representa el archivo que se va a eliminar
	 * @return Verdadero si todo fue bien
	 */
	public boolean borrarArchivo(Archivo archivo) {
		return this.borrarArchivo(archivo.getNombreArchivo(),
				archivo.getNombrePropietario());
	}
	
	/**
	 * <p>Busca en la base de datos el archivo que concuerde con los par�metros especificados,
	 * y lo elimina.</p>
	 * @param nombreArchivo Nombre del archivo a eliminar
	 * @param nombrePropietario Nombre del propietario del archivo a eliminar
	 * @return Verdadero si todo fue bien
	 */
	private boolean borrarArchivo(String nombreArchivo, String nombrePropietario) {
		PreparedStatement ps;
		boolean borrado = true;
		
		try {
			ps = conectorBD.getPreparedStatement("delete from archivos where nombreArchivo = ? and nombrePropietario = ?");
			ps.setString(1, nombreArchivo);
			ps.setString(2, nombrePropietario);
			ps.executeUpdate();
			
			ps.close();
			
		} catch (SQLException sql) {
			borrado = false;
			System.err.println("MySqlArchivoDAO/borrarArchivo: " + sql.getMessage());
		}
		
		return borrado;
	}
	
	/**
	 * <p>M�todo que hace de interfaz de la clase con el resto de la aplicaci�n para
	 * insertar un archivo. Llama al m�todo "insertarArchivo", privado, con los par�metros
	 * necesarios.</p>
	 * @param archivo Objeto de tipo Archivo que representa el archivo a insertar
	 * @return Verdadero si todo fue bien
	 */
	public boolean insertarArchivo (Archivo archivo) {
		return this.insertarArchivo(archivo.getNombreArchivo(), 
				archivo.getNombrePropietario(), archivo.getRutaArchivo());
	}
	
	/**
	 * <p>Inserta en la tabla "archivos" una fila con el nombre del archivo, el nombre de su
	 * propietario y la ruta en la que se encuentra</p>
	 * @param nombreArchivo Nombre del archivo a insertar
	 * @param nombrePropietario Nombre del propietario del archivo a insertar
	 * @param rutaArchivo Ruta del archivo a insertar
	 * @return Verdadero si todo fue bien
	 */
	private boolean insertarArchivo(String nombreArchivo, String nombrePropietario, String rutaArchivo) {
		boolean insertado = true;
		PreparedStatement ps;
		
		try {
			ps = 
				conectorBD.getPreparedStatement("insert into archivos(nombreArchivo,nombrePropietario,rutaArchivo) values(?,?,?)");
			ps.setString(1, nombreArchivo);
			ps.setString(2, nombrePropietario);
			ps.setString(3, rutaArchivo);
			
			ps.executeUpdate();
			ps.close();
			
		} catch (SQLException sql) {
			System.err.println("MySqlArchivoDAO/insertarArchivo: " + sql.getMessage());
			insertado = false;
		}
		return insertado;
	}
	
	/**
	 * <p>M�todo que hace de interfaz con el resto de la aplicaci�n para buscar un archivo. Llama
	 * al m�todo "buscarArchivo", privado, con los par�metros necesarios.</p>
	 * @param archivo Objeto de tipo archivo que representa el archivo a buscar
	 * @return Verdadero si el archivo se encuentra en la tabla
	 */
	public boolean buscarArchivo(Archivo archivo) {
		return this.buscarArchivo(archivo.getNombreArchivo(),
				archivo.getNombrePropietario());
	}
	
	/**
	 * <p>Busca en la tabla "archivos", y comprueba si alguna fila contiene el mismo nombre
	 * de archivo y de propietario que los que se pasan como par�metro. </p>
	 * @param nombreArchivo Nombre del archivo a buscar
	 * @param nombrePropietario Nombre del propietario del archivo a buscar
	 * @return Verdadero si el archivo se encuentra en la tabla
	 */
	private boolean buscarArchivo (String nombreArchivo, String nombrePropietario) {
		boolean almacenado = true;
		PreparedStatement ps;
		ResultSet rs;
		
		try {
			ps = conectorBD.getPreparedStatement("select * from archivos where nombreArchivo = ? and nombrePropietario = ?");
			ps.setString(1, nombreArchivo);
			ps.setString(2, nombrePropietario);
			rs = ps.executeQuery();
			
			if (!rs.next()) 
				almacenado = false;
				
		} catch (SQLException sql) {
			System.err.println("MySqlArchivoDAO/buscarArchivo: " + sql.getMessage());
			almacenado = false;
		}
		return almacenado;
	}
	
	/**
	 * <p>Busca el archivo que coincida con los par�metros especificados y
	 * devuelve un objeto con esta informaci�n.</p>
	 * @param nombreArchivo Nombre del archivo a buscar
	 * @param nombrePropietario Nombre del propietario del archivo a buscar
	 * @return Un objeto de tipo Archivo si hay alguna fila de la tabla que coincida
	 * con los par�metros especificados. Si no, devuelve null.
	 */
	public Archivo getArchivo(String nombreArchivo, String nombrePropietario) {
		Archivo retorno = null;
		PreparedStatement ps;
		ResultSet rs;
		
		try {
			ps = conectorBD.getPreparedStatement("select * from archivos where nombreArchivo = ? and nombrePropietario = ?");
			ps.setString(1, nombreArchivo);
			ps.setString(2, nombrePropietario);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				retorno = new Archivo();
				retorno.setNombreArchivo(rs.getString("nombreArchivo"));
				retorno.setNombrePropietario(rs.getString("nombrePropietario"));
				retorno.setRutaArchivo(rs.getString("rutaArchivo"));
			}
			
			rs.close();
			ps.close();
			
		} catch (SQLException sql) {
			System.err.println("MySqlArchivoDAO/getArchivo: " + sql.getMessage());
		}
		return retorno;
	}
	
	/**
	 * <p>M�todo que hace de interfaz de la clase con el resto de la aplicaci�n para
	 * la operaci�n de actualizar el nombre de un archivo. Llama al m�todo "actualizarArchivo",
	 * privado, con los par�metros necesarios</p>
	 * @param archivo Objeto de tipo archivo que representa el archivo a modificar
	 * @param valor Nuevo valor que recibir� el nombre del archivo
	 * @return Verdadero si la operaci�n result� bien
	 */
	public boolean actualizarNombreArchivo(Archivo archivo, String valor) {
		return this.actualizarArchivo(archivo.getNombreArchivo(), 
				archivo.getNombrePropietario(), "nombreArchivo", valor);
	}
	
	/**
	 * <p>M�todo que hace de interfaz de la clase con el resto de la aplicaci�n para
	 * la operaci�n de actualizar la ruta de un archivo. Llama al m�todo "actualizarArchivo",
	 * privado, con los par�metros necesarios</p>
	 * @param archivo Objeto de tipo archivo que representa el archivo a modificar
	 * @param valor Nuevo valor que recibir� el nombre del archivo
	 * @return Verdadero si la operaci�n result� bien
	 */
	public boolean actualizarRutaArchivo(Archivo archivo, String valor) {
		return this.actualizarArchivo(archivo.getNombreArchivo(), 
				archivo.getNombrePropietario(), "rutaArchivo", valor);
	}
	
	/**
	 * <p>Busca si alguna fila coincide con el nombre del archivo y del propietario especificados.
	 * En caso de que as� sea, modifica el campo requerido con el valor especificado. Se utiliza tanto
	 * para actualizar el nombre como la ruta del archivo.</p>
	 * @param nombreArchivo Nombre del archivo a modificar
	 * @param nombrePropietario Nombre del propietario del archivo a modificar
	 * @param campo Campo a modificar, puede ser el nombre del archivo o su ruta
	 * @param valor Nuevo valor que va a tener el campo modificado
	 * @return Verdadero si se actualiz� alg�n archivo, falso en caso contrario
	 */
	private boolean actualizarArchivo(String nombreArchivo, String nombrePropietario, String campo,
			String valor) {
		
		boolean modificado = false;
		PreparedStatement ps;
		
		try {
			ps = conectorBD.getPreparedStatement("update archivos set " + campo + " = ? where nombreArchivo = ? and nombrePropietario = ?");
			ps.setString(1, valor);
			ps.setString(2, nombreArchivo);
			ps.setString(3, nombrePropietario);
			
			if ((ps.executeUpdate()) > 0) 
				modificado = true;
			
			ps.close();
			
		} catch (SQLException sql) {
			modificado = false;
			System.err.println("MySqlArchivoDAO/actualizarArchivo: " + sql.getMessage());
		}
		return modificado;
		
	}
 }
