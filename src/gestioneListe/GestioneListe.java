package gestioneListe;

import java.io.*;
import java.util.*;

import articolo.Articolo;
import categoria.Categoria;
import eccezioni.*;
import listaDiArticoli.ListaDiArticoli;


/**
 * Classe centrale per la gestione del sistema di liste della spesa.
 * Implementa il pattern Singleton/Statico per mantenere l'anagrafica globale di:
 * Liste di articoli
 * Categorie disponibili
 * Articoli salvati in anagrafica
 * Gestisce inoltre la persistenza dei dati su file.
 */
public class GestioneListe {
	
	private static Map<String, ListaDiArticoli> listeArticoli = new HashMap<>();
	private static Set<Categoria> listaCategoria = new HashSet<>();
	private static Set<Articolo> articoliGlobali = new HashSet<>();

	
	/** Percorso del file utilizzato per il salvataggio dei dati. */
	private static final String FILE_DATI = "dati_applicazione.ser";

	/** struttura per salvare i dati su file */
    public static void salvaDati() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_DATI))) {
            Map<String, Object> datiDaSalvare = new HashMap<>();
            datiDaSalvare.put("liste", listeArticoli);
            datiDaSalvare.put("categorie", listaCategoria);
            datiDaSalvare.put("anagrafica", articoliGlobali);
            
            oos.writeObject(datiDaSalvare);
            System.out.println("Dati salvati correttamente su file.");
        }
    }
    /** struttura per caricare i dati salvati da file */
    @SuppressWarnings("unchecked")
    public static void caricaDati() throws IOException, ClassNotFoundException {
        File file = new File(FILE_DATI);
        if (!file.exists()) return; 

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Map<String, Object> datiCaricati = (Map<String, Object>) ois.readObject();
            
            listeArticoli = (Map<String, ListaDiArticoli>) datiCaricati.get("liste");
            listaCategoria = (Set<Categoria>) datiCaricati.get("categorie");
            articoliGlobali = (Set<Articolo>) datiCaricati.get("anagrafica");
            
            System.out.println("Dati caricati correttamente.");
        }
    }
	
	
    /**
	 * Crea una nuova lista.
	 * @param nome Nome identificativo della nuova lista.
	 */
	public static void creaLista(String nome) throws ElementoDupplicatoException{
		if(listeArticoli.containsKey(nome)) {
			throw new ElementoDupplicatoException("Esiste già una lista chiamata: " + nome);
		}
		listeArticoli.put(nome, new ListaDiArticoli(nome));	
	}
	
	/**
	 * Rimuove definitivamente una lista dal sistema.
	 * @param nome Nome della lista da eliminare.
	 */
	public static void eliminaLista(String nome) throws ElementoNonTrovatoException{
		if(listeArticoli.remove(nome) == null) {
			throw new ElementoNonTrovatoException("non esiste nessuna Lista chiamata cosi: " + nome);
		}
		
		
	}

	/**
	 * Recupera una specifica lista tramite il suo nome.
	 * @param nome Nome della lista.
	 * @return L'oggetto ListaDiArticoli corrispondente.
	 */
	public static ListaDiArticoli getLista(String nome) throws ElementoListaNonTrovatoException{
		if(listeArticoli.get(nome) == null) {
			throw new ElementoListaNonTrovatoException("non esiste nessuna lista chiamata cosi: " + nome);
		}
		return listeArticoli.get(nome);
	}
	
	/** 
	 * recupera tuue le liste.
	 * @return Una collezione di tutte le liste attualmente caricate.
	 */
	public static Collection<ListaDiArticoli> getAllListe() {
        return listeArticoli.values();
    }

	/**
	 * Elimina una categoria solo se non è attualmente utilizzata da alcun articolo in anagrafica.
	 * @param cat La categoria da eliminare.
	 */
	public static void eliminaCategoria(Categoria cat) throws ElementoInUtilizzoException, ElementoNonTrovatoException{
		for(Articolo art : articoliGlobali) {
			if(art.getCategoria().equals(cat)) {
				throw new ElementoInUtilizzoException(cat.getNome() + "c'è un articolo che utilizza la categoria " + cat + " che hai cercato di rimuovere.");
			}
		}
		if(!listaCategoria.remove(cat)) {
			throw new ElementoNonTrovatoException("La categoria non esiste.");
		}
		
		
	}

	/**
	 * Aggiunge una nuova categoria all'elenco globale.
	 * @param cat La categoria da aggiungere.
	 */
	public static void aggiungiCategoria(Categoria cat) throws ElementoDupplicatoException{
		if(listaCategoria.contains(cat)){
			throw new ElementoDupplicatoException("Esiste già una categoria chiamata: " + cat.getNome());
		}
		listaCategoria.add(cat);	
	}
	
	/** 
	 * recupera tutte le categorie.
	 * @return L'insieme di tutte le categorie registrate. 
	 */
	public static Set<Categoria> getCategorie() {
	    return listaCategoria;
	}

	/**
	 * Registra un nuovo articolo.
	 * @param art L'articolo da aggiungere.
	 */
	public static void aggiungiArticoloGlobale(Articolo art) throws ElementoDupplicatoException {
		for (Articolo a : articoliGlobali) {
			if (a.getNomeArticolo().equals(art.getNomeArticolo())) {
				throw new ElementoDupplicatoException("Articolo già presente in anagrafica.");
			}
		}
	    articoliGlobali.add(art);
	}
	
	
	public static void eliminaArticolo(Articolo art) throws ElementoNonTrovatoException {
		boolean rimosso = articoliGlobali.remove(art);
	    
	    if (!rimosso) {
	        throw new ElementoNonTrovatoException("non è presente in anagrafica");
	    }
		
		
	}
	
	
	/** 
	 * recupera tutti gli articoli.
	 * @return La lista degli articoli registrati. 
	 */
	public static Set<Articolo> getArticoliGlobali() {
	    return articoliGlobali;
	}

	/**
	 * Cerca una categoria nell'elenco globale partendo dal suo nome testuale.
	 * @param nome Il nome della categoria (case-insensitive).
	 * @return L'oggetto Categoria trovato.
	 */
	public static Categoria cercaCategoriaPerNome(String nome) throws ElementoNonTrovatoException {
	    for (Categoria cat : listaCategoria) {
	        if (((String) cat.getNome()).equalsIgnoreCase(nome)) {
	            return cat;
	        }
	    }
	    throw new ElementoNonTrovatoException("La categoria '" + nome + "' non esiste. Creala prima!");
	}
	
}
