package categoria;

import java.io.Serializable;
import java.util.Objects;



/**
 * Rappresenta la categoria.
 * ha lo scopo di dare kl'unica informazione utile della categoria cioè il nome..
 */
public class Categoria implements Serializable {
    private static final long serialVersionUID = 1L;
	private String nome;
	

	/**
     * Costruttore della categoria.
     * @param nome Il nome della categoria.
     */
	public Categoria(String nome) {
		this.nome = nome;
	}

	/**
     * ricava il nome della categoria.
     * @return nome il nome.
     */
	public String getNome() {
		
		return nome;
	}
	
	/**
     * Confronta questo oggetto con l'oggetto specificato per l'uguaglianza.
     * Il risultato è {@code true} se e solo se l'argomento non è {@code null},
     * è un'istanza della classe {@code Categoria} e ha lo stesso valore nel
     * campo {@code nome} di questo oggetto.
     * @param o l'oggetto da confrontare con questo {@code Categoria}.
     * @return {@code true} se gli oggetti sono uguali; {@code false} altrimenti.
     */
	 @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        Categoria categoria = (Categoria) o;
	        return Objects.equals(nome, categoria.nome);
	    }

	/**
     * Restituisce un valore hash per l'oggetto.
     * Questo metodo stabilisce che oggetti uguali
     * devono avere codici hash uguali. Il calcolo è basato sul campo {@code nome}.
     * @return un valore hash per questo oggetto.
	 */
	 @Override
	    public int hashCode() {
	        return Objects.hash(nome);
	    }

}
