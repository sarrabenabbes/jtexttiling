package es.project.root.estadisticas;

import java.util.List;

/**
 * <p>Interfaz que van a implementar las clases que permiten al root obtener las estadísticas
 * de los usuarios y los archivos</p>
 * @author Daniel Fernández Aller
 */
public interface Estadisticas {
	
	public abstract List getLista();
	public abstract int getNum();
	
}
