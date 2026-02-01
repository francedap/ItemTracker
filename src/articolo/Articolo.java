package articolo;

import java.io.Serializable;
import java.util.Objects;

import categoria.Categoria;



/**
 * Rappresenta il singolo articolo, ha lo scopo di far ricavare ogni informazione utile dall'oggetto articolo.
 */
public class Articolo implements Serializable {
    private static final long serialVersionUID = 1L;
	private String nome;
	private double prezzo;
	private String nota;
	private Categoria nomeCat;
	
	/**
     * Costruttore dell'articolo senza campi, solo il nome .
     * @param nome Il nome identificativo dell'articolo.
     */
	public Articolo(String nome) {
		this.nome = nome;
		this.prezzo = 0.0;
		this.nota ="nessuna nota";
		this.nomeCat = new Categoria("non categorizzato");
	}
	
	/**
     * Costruttore dell'articolo con tutti i campi .
     * @param nome, prezzo, nota, nomeCat. 
     * Il nome identificativo dell'articolo.
     * il prezzo dell'articolo.
     * la nota descrive l'articolo.
     * il nomecat l'oggetto categoria che identifica l'articolo.
     */
	public Articolo(String nome, Double prezzo, String nota, Categoria nomeCat) {
		this.nome = nome;
		if (prezzo >= 0.0) {
			this.prezzo = prezzo;
		}
		else {
			this.prezzo = 0.0;
		}
		
		if (nota == null) {
			this.nota = "nessuna nota";
		}
		else {
			this.nota = nota;
		}
		
		if (nomeCat == null) {
			this.nomeCat = new Categoria("non categorizzato");
		}
		else {
			this.nomeCat = nomeCat;
		}
		
		
	}
	
	/**
     * ricava il nome dell'articolo.
     * @return il nome.
     */
	public String getNomeArticolo() {
		
		return nome;
	}

	/**
     * ricava il prezzo dell'articolo.
     * @return il prezzo.
     */
	public Double getPrezzo() {
		
		return prezzo;
	}

	/**
     * ricava la nota dell'articolo.
     * @return la nota.
     */
	public Object getNota() {
	
		return nota;
	}

	/**
     * ricava la categoria dell'articolo.
     * @return l'oggetto categoria.
     */
	public Object getCategoria() {
		
		return nomeCat.getNome();
	}

	/**
     * imposta il nuovo prezzo all'articolo.
     * @param d il nuovo prezzo.
     */
	public void setPrezzo(double d) {
		
		this.prezzo = d; 
	}

	/**
     * imposta la nuova nota all'articolo.
     * @param string la nuova nota.
     */
	public void setNota(String string) {
		
		this.nota = string;
	}

	/**
     * imposta la nuova categoria all'articolo.
     * @param nuovaCat oggetto della nuova categoria .
     */
	public void setCategoria(Categoria nuovaCat) {
		
		this.nomeCat = nuovaCat;
	}
	
	
	/**
     * ricava il nome e i parametri dell'articolo in stringa.
     * @return Stringa con parametri che definiscono l'articolo.
     */
	@Override
	public String toString() {
	    return this.nome + " - " + this.prezzo + "€ [" + this.nomeCat.getNome() + "] | Note: " + this.nota;
	}

	/**
     * Confronta questo oggetto con l'oggetto specificato per l'uguaglianza.
     * Il risultato è {@code true} se e solo se l'argomento non è {@code null},
     * è un'istanza della classe {@code Articolo} e ha lo stesso valore nel
     * campo {@code nome} di questo oggetto.
     * @param o l'oggetto da confrontare con questo {@code Articolo}.
     * @return {@code true} se gli oggetti sono uguali; {@code false} altrimenti.
     */
	 @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        Articolo articolo = (Articolo) o;
	        return Objects.equals(nome, articolo.nome);
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
