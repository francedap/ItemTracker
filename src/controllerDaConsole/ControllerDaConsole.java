package controllerDaConsole;

import java.util.Scanner;

import articolo.Articolo;
import categoria.Categoria;
import gestioneListe.GestioneListe;
import listaDiArticoli.ListaDiArticoli;
import eccezioni.*;
import controllerGui.*;

/**
 * Controller principale per l'interfaccia a riga di comando (CLI) dell'applicazione.
 * Questa classe gestisce l'interazione con l'utente tramite terminale, occupandosi
 * di leggere gli input e gestire il flusso principale del programma.
 */
public class ControllerDaConsole {

	/**
     * Scanner condiviso per la lettura degli input da tastiera.
     */
	private static Scanner scanner = new Scanner(System.in);

	
	/**
     * Punto di ingresso dell'applicazione console.
     * Il metodo esegue le seguenti operazioni:
     * Tenta di caricare i dati persistenti all'avvio.
     * Avvia un ciclo infinito per mostrare il menu principale.
     * Gestisce la selezione dell'utente invocando i metodi statici di gestione.
     * Gestisce le eccezioni e gli errori di input mostrandoli a video.
     * Salva i dati e termina il programma se l'utente seleziona l'opzione di uscita.
     */
	    public static void main(String[] args) {
	    	   	
	        try {
	            GestioneListe.caricaDati();
	        } catch (Exception e) {
	        }

	        boolean continua = true;
	        while (continua) {
	            System.out.println("\n--- GESTIONE LISTE ---");
	            System.out.println("1. Liste: Crea / Elimina / Visualizza");
	            System.out.println("2. Categorie: Aggiungi / Elimina / Visualizza");
	            System.out.println("3. Articoli: Aggiungi / Visualizza");
	            System.out.println("4. Operazioni su Lista: Aggiungi Articolo / Elimina Articolo / Ripristina Da Rimossi");
	            System.out.println("5. Visualizzazione Lista:");
	            System.out.println("6. Salva Dati");
	            System.out.println("7. Apri Gui");
	            System.out.println("0. Esci");
	            System.out.print("Scelta: ");

	            String scelta = scanner.nextLine();

	            try {
	                switch (scelta) {
	                    case "1": gestisciListe(); break;
	                    case "2": gestisciCategorie(); break;
	                    case "3": gestisciArticoli(); break;
	                    case "4": aggiungiArticoloALista(); break;
	                    case "5": visualizzaLista(); break;
	                    case "6": GestioneListe.salvaDati(); break;
	                    case "7":  new MainGui().setVisible(true); break;
	                    case "0": 
	                        GestioneListe.salvaDati(); 
	                        continua = false; 
	                        break;
	                    default: System.out.println("Scelta non valida.");
	                }
	            } catch (Exception e) {
	                System.err.println("Errore: " + e.getMessage());
	            }
	        }
	    }

	    /**
	     * Gestisce il sottomenu per le operazioni sulle Liste.
	     * Permette di creare, eliminare o visualizzare l'elenco delle liste disponibili.
	     * @throws ElementoDupplicatoException se si tenta di creare una lista con un nome già esistente.
	     * @throws ElementoNonTrovatoException se si tenta di eliminare una lista inesistente.
	     */
	    private static void gestisciListe() throws ElementoDupplicatoException, ElementoNonTrovatoException {
	        System.out.println("1. Crea | 2. Elimina | 3. Mostra tutte");
	        String sub = scanner.nextLine();
	        if (sub.equals("1")) {
	            System.out.print("Nome lista: ");
	            GestioneListe.creaLista(scanner.nextLine());
	        } else if (sub.equals("2")) {
	            System.out.print("Nome lista da eliminare: ");
	            GestioneListe.eliminaLista(scanner.nextLine());
	        } else if (sub.equals("3")) {
	            System.out.println("Liste presenti:");
	            for (ListaDiArticoli lista : GestioneListe.getAllListe()) {
	                System.out.println("- " + lista.getNomeLista()); 
	            }
	        }
	    }

	    /**
	     * Gestisce il sottomenu per le operazioni sulle Categorie.
	     * @throws ElementoDupplicatoException se la categoria esiste già.
	     * @throws ElementoInUtilizzoException se si tenta di eliminare una categoria usata da un articolo.
	     * @throws ElementoNonTrovatoException se la categoria da eliminare non esiste.
	     */
	    private static void gestisciCategorie() throws ElementoDupplicatoException, ElementoInUtilizzoException, ElementoNonTrovatoException {
	        System.out.println("1. Aggiungi | 2. Elimina | 3. Mostra tutte");
	        String sub = scanner.nextLine();
	        if (sub.equals("1")) {
	            System.out.print("Nome categoria: ");
	            GestioneListe.aggiungiCategoria(new Categoria(scanner.nextLine()));
	        } else if (sub.equals("2")) {
	            System.out.print("Nome categoria da eliminare: ");
	            GestioneListe.eliminaCategoria(new Categoria(scanner.nextLine()));
	        } else if (sub.equals("3")) {
	            System.out.println("--- CATEGORIE DISPONIBILI ---");
	            
	            for (Categoria c : GestioneListe.getCategorie()) {
	                System.out.println("- " + c.getNome());
	            }
	        }
	    }

	    /**
	     * Gestisce il sottomenu per la creazione e visualizzazione degli Articoli nel catalogo globale.
	     * Richiede all'utente di inserire nome, prezzo e categoria per i nuovi articoli.
	     * @throws ElementoDupplicatoException se l'articolo esiste già nel catalogo globale.
	     * @throws ElementoNonTrovatoException se la categoria specificata non esiste.
	     */
	    private static void gestisciArticoli() throws ElementoDupplicatoException, ElementoNonTrovatoException {
	        System.out.println("1. Nuovo Articolo | 2. Mostra Articoli");
	        String sub = scanner.nextLine();
	        
	        if (sub.equals("1")) {
	            System.out.print("Nome: "); String n = scanner.nextLine();
	            System.out.print("Prezzo: "); 
	            double p = Double.parseDouble(scanner.nextLine());
	            
	            System.out.print("Categoria: "); 
	            String nomeCat = scanner.nextLine();
	            
	            Categoria catEsistente = GestioneListe.cercaCategoriaPerNome(nomeCat);
	            
	            GestioneListe.aggiungiArticoloGlobale(new Articolo(n, p, "", catEsistente));
	            System.out.println("Articolo creato con successo!");

	        } else if (sub.equals("2")) {
	            System.out.println("--- ARTICOLI ---");
	            for (Articolo a : GestioneListe.getArticoliGlobali()) {
	                System.out.println("- " + a.getNomeArticolo() + "  " + a.getPrezzo() + "€   " + a.getCategoria());
	            }
	        }
	    }

	    /**
	     * Gestisce le operazioni sugli articoli all'interno di una specifica lista.
	     * Le operazioni supportate includono:
	     * Aggiunta di un articolo alla lista.
	     * Rimozione (spostamento nello storico/cestino).
	     * Ripristino di un articolo dallo storico alla lista attiva.
	     * @throws ElementoNonTrovatoException se la lista o l'articolo specificato non esistono.
	     */
	    private static void aggiungiArticoloALista() throws ElementoListaNonTrovatoException {
	        System.out.print("Quale lista vuoi modificare? ");
	        String nomeLista = scanner.nextLine();
	        ListaDiArticoli lista = (ListaDiArticoli) GestioneListe.getLista(nomeLista); 
	        
	        System.out.println("1. Aggiungi Articolo | 2. Sposta in Rimossi | 3. Ripristina da Rimossi");
	        String sub = scanner.nextLine();
	        
	        if (sub.equals("1")) {
	            
	        }else if (sub.equals("2")) {
	         
	            System.out.println("Articoli attivi: " + lista.getArticoliAttivi());
	            System.out.print("Nome articolo da eliminare: ");
	            String nome = scanner.nextLine();
	            
	            Articolo trovato = null;
	            for(Articolo a : lista.getArticoliAttivi()) {
	                if(a.getNomeArticolo().equalsIgnoreCase(nome)) { trovato = a; break; }
	            }
	            if(trovato != null) {
	                lista.rimuoviArticolo(trovato);
	                System.out.println("Articolo spostato nello storico.");
	            } else {
	                System.out.println("Articolo non trovato.");
	            }

	        }else if (sub.equals("3")) {
	            System.out.println("--- ARTICOLI NELLO STORICO (RIMOSSI) ---");
	            if (lista.getArticoliRimossi().isEmpty()) {
	                System.out.println("Lo storico è vuoto.");
	            } else {
	             
	                for (Articolo a : lista.getArticoliRimossi()) {
	                    System.out.println("- " + a.getNomeArticolo());
	                }
	                
	                System.out.print("Quale articolo vuoi riportare nella lista attiva? ");
	                String nomeDaRipristinare = scanner.nextLine();
	                
	               
	                Articolo daRecuperare = null;
	                for (Articolo a : lista.getArticoliRimossi()) {
	                    if (a.getNomeArticolo().equalsIgnoreCase(nomeDaRipristinare)) {
	                        daRecuperare = a;
	                        break;
	                    }
	                }
	                
	                if (daRecuperare != null) {
	                   
	                    lista.ripristinaArticolo(daRecuperare);
	                    System.out.println("Articolo '" + nomeDaRipristinare + "' ripristinato con successo!");
	                } else {
	                    System.out.println("Articolo non trovato nello storico.");
	                }
	            }
	        }
	    }
	    
	    /**
	     * Visualizza il contenuto dettagliato di una lista specifica.
	     * Mostra separatamente:
	     * Gli articoli attivi.
	     * Gli articoli nello storico.
	     * Il costo totale attuale degli articoli attivi.
	     * @throws ElementoNonTrovatoException se la lista richiesta non esiste.
	     */
	    private static void visualizzaLista() throws ElementoListaNonTrovatoException {
	        System.out.print("Nome lista da visualizzare: ");
	        String nomeLista = scanner.nextLine();
	        ListaDiArticoli lista = (ListaDiArticoli) GestioneListe.getLista(nomeLista); 
	        
	        System.out.println("\n--- STATO LISTA: " + nomeLista.toUpperCase() + " ---");
	        System.out.println("\n[ ARTICOLI DA ACQUISTARE ]");
	        if (lista.getArticoliAttivi().isEmpty()) {
	            System.out.println("   (Vuota)");
	        } else {
	            for (Articolo a : lista.getArticoliAttivi()) {
	                System.out.println(" - " + a.getNomeArticolo() + " (" + a.getPrezzo() + "€)");
	            }
	        }

	 
	        System.out.println("\n[ STORICO RIMOSSI ]");
	        if (lista.getArticoliRimossi().isEmpty()) {
	            System.out.println("   (Nessuno)");
	        } else {
	            for (Articolo a : lista.getArticoliRimossi()) {
	                System.out.println(" [X] " + a.getNomeArticolo());
	            }
	        }

	       
	        System.out.println("\n--------------------------------");
	        System.out.printf("TOTALE ATTUALE: %.2f €\n", lista.getPrezzoTotale());
	        System.out.println("--------------------------------");
	    }
}
