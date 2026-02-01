package listaDiArticoli;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import articolo.Articolo;

/**
 * Rappresenta una lista di articoli da acquistare.
 * Gestisce l'aggiunta, la rimozione logica e il calcolo del totale degli articoli attivi.
 */


public class ListaDiArticoli implements Iterable<Articolo>, Serializable {
    private static final long serialVersionUID = 1L;
	
	
	private String nome;
	private List<Articolo> articoliRimossi;
	private List<Articolo> articoliAttivi;
	
	
	/**
     * Costruttore della lista.
     * @param nome Il nome identificativo della lista.
     */
	
	public ListaDiArticoli(String nome) {
		
		this.nome = nome;
        this.articoliAttivi = new ArrayList<>();
        this.articoliRimossi = new ArrayList<>();
	}

	/**
     * ricava il nome della lista.
     * @return il nome della lista.
     */
	public String getNomeLista() {
		return nome;
	}
	
	
	/**
     * Calcola il prezzo totale degli articoli attualmente presenti nella lista della spesa.
     * Gli articoli rimossi non vengono conteggiati nel totale.
     * @return Il valore totale in euro.
     */
	public double getPrezzoTotale() {
	    double totale = 0;
	    for (Articolo art : articoliAttivi) {
	        totale += art.getPrezzo();
	    }
	    return totale;
	}
	
	
	/**
     * aggiunge un articolo alla lista attiva.
     * @param a L'articolo da aggiungere.
     */
	public void aggiungiArticolo(Articolo a) {
		articoliAttivi.add(a);
	}
	
	
	/**
     * Sposta un articolo dalla lista attiva alla lista degli articoli rimossi.
     * @param a L'articolo da rimuovere.
     */
	public void rimuoviArticolo(Articolo a) {
		if( articoliAttivi.remove(a)) {
			articoliRimossi.add(a);
		}
		
	}
	
	/**
     * Sposta un articolo dalla lista rimossi alla lista degli articoli attivi.
     * @param a L'articolo da ripristinare.
     */
	public void ripristinaArticolo(Articolo a) {
		if( articoliRimossi.remove(a)) {
			articoliAttivi.add(a);
		}
	}
	
	/**
     * Cerca articoli il cui nome inizia con la stringa specificata.
     * La ricerca viene effettuata sia tra gli articoli attivi che tra quelli rimossi.
     * @param prefisso La stringa da cercare come inizio del nome dell'articolo.
     * @return Una lista di articoli che corrispondono al prefisso cercato.
     */
	public List<Articolo> cercaPerPrefisso(String prefisso) {
		List<Articolo> trovati = new ArrayList<>();
		
		for(Articolo a : articoliAttivi) {
			if(a.getNomeArticolo().startsWith(prefisso)) trovati.add(a);
		}
		for(Articolo a : articoliRimossi) {
			if(a.getNomeArticolo().startsWith(prefisso)) trovati.add(a);
		}
		return trovati;
	}
	
	/**
	 * metodo che ha lo scopo di cancellare gli Articoli all'interno 
	 * della lista degli articoliRimossi.
	 */
	public void svuotaCancellati() {
	    this.articoliRimossi.clear();
	}
	
	/**
     * Fornisce un iteratore che scorre prima tutti gli articoli attivi 
     * e successivamente tutti gli articoli rimossi.
     * @return Un iteratore sulla collezione completa degli articoli.
     */
	@Override
	public Iterator<Articolo> iterator() {
		List<Articolo> tutti = new ArrayList<>(articoliAttivi);
        tutti.addAll(articoliRimossi);
        return tutti.iterator();
	}
	
	/**
	 * ricava la lista degli articoli attivi.
     * @return la lista articoliAttivi.
	 */
	public List<Articolo> getArticoliAttivi() { return articoliAttivi; }
	
	/**
	 * ricava la lista degli articoli rimossi.
     * @return la lista articoliRimossi
	 */
    public List<Articolo> getArticoliRimossi() { return articoliRimossi; }
}

	


