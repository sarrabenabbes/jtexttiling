package es.project.blindLight;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * <p>Recibe un texto y lo formatea según las siguientes premisas:
 * <ul>
 * 	<li>Los espacios tales como espacios en blanco, tabuladores, saltos de línea, etc, se
 * sustituyen por espacios en blanco. Esto es: el documento será una única línea cuyas palabras
 * estarán separadas por un espacio en blanco.
 *  </li>
 *  <li>Los separadores (símbolos de puntuación) se sustituyen por el carácter '>'
 *  </li>
 * </ul>
 * </p>
 * @author Daniel Fernández Aller, basado en código de Eugenia Pérez, Saúl González y Miguel Martínez
 */
public class FormateadorTexto {
	
	private String texto;
	private List<NGrama> conjuntoNGramas;
	
	public FormateadorTexto(String texto) {
		this.texto = texto;
		this.inicializarConjunto();
		this.formatearTexto();
	}
	
	private void inicializarConjunto() {
		conjuntoNGramas = new LinkedList<NGrama>();
	}
	
	private void formatearTexto() {
        boolean beginning = true;
        char c = ' ';
        char buffer = 0;
        StringBuffer result = new StringBuffer(texto.length());

        for (int i=0; i<texto.length(); i++) {
            c = texto.charAt(i); 
            if((c & 0x80)== 0){ //ascii
                if(esEspacio(c)){
                    if(buffer!='>')
                        buffer = ' ';
            }else{
                if(esSeparador(c))
                    buffer = '>';
                else{
                    if((buffer!=0) && (beginning==false))
                    {
                        result.append(buffer);
                        buffer = 0;
                    }
                    if (beginning)
                    {
                        beginning = false;
                        buffer = 0;
                    }
                    result.append(c);
                  }
                }
            }else{//extendido
                if((buffer!=0) && (beginning==false))
                {
                   result.append(buffer);
                   buffer = 0;
                }
                if(beginning)
                {
                    beginning = false;
                    buffer = 0;
                }
               result.append(c); 
            }  
          }//end_while
        texto = result.toString();
    }//end formatearTexto

	public String[] getListaFrases() {
		String[] lista = texto.split("\\>");
		return lista;
	}	
	/**
	 * <p>Indica si el carácter recibido es espaciador</p>
	 * @param c Carácter a comprobar
	 * @return Verdadero si el carácter es espaciador
	 */
	private boolean esEspacio(int c) {
		return ((c>=7)&&(c<=13)||(c==32));
    }
	
	/**
	 * <p>Indica si el carácter recibido es separador</p>
	 * @param c Carácter a comprobar
	 * @return Verdadero si el carácter es separador
	 */
	private boolean esSeparador(int c) {
		if(esEspacio(c))
            return false;
        else{
           return ((c<=31)||((c>=33)&&(c<=38))||
           ((c>=40)&&(c<=44))||((c>=46)&&(c<=47))||
           ((c>=58)&&(c<=63))||((c>=91)&&(c<=96))||
           ((c>=123)&&(c<=191))||((c==215)||(c==247)));
        }
	}
	
	private void addNGrama(NGrama ngrama) {
		NGrama aux = modificarEspacios(ngrama);
		if (!estaIncluidoNGrama(ngrama)) 
			conjuntoNGramas.add(aux);
	}
	
	private boolean estaIncluidoNGrama(NGrama ngrama) {
		Iterator<NGrama> i = conjuntoNGramas.iterator();
		
		while (i.hasNext()) 
			if (ngrama.equals(i.next()))
				return true;

		return false;
	}
	
	private NGrama modificarEspacios (NGrama ngrama) {
		String texto = ngrama.getTexto();
		String modificado = texto.replace(' ', '_');
		ngrama.setTexto(modificado);
		return ngrama;
	}

	public void calcularNGramas(String frase, int n) throws NGramaException {
		NGrama.setN(n);
		String textoNGrama;
		NGrama aux;
	
		int inicio;
		int tope;
		
		for (int i = 0; i < frase.length(); i++) {
			textoNGrama = "";
			inicio = i;
			tope = inicio + n;
			
			for (inicio = i; inicio < tope; inicio++) {
				if (tope <= frase.length()) {
					textoNGrama += frase.charAt(inicio);
					
					if (textoNGrama.length() == n) {
						aux = new NGrama(textoNGrama);
						this.addNGrama(aux);
					}
				}
			}
		}
	}
	
	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public List<NGrama> getConjuntoNGramas() {
		return conjuntoNGramas;
	}

	public void setConjuntoNGramas(List<NGrama> conjuntoNGramas) {
		this.conjuntoNGramas = conjuntoNGramas;
	}
}
