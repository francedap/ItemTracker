package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import articolo.Articolo;
import listaDiArticoli.ListaDiArticoli;



class ListaDiArticoliTest {

	@Test
	void testCreazionePopolamento() {
		ListaDiArticoli lista = new ListaDiArticoli("spesa settimanale");
		Articolo a = new Articolo("mela", 2.65, "Frutto", null);
		
		lista.aggiungiArticolo(a);
		assertTrue(lista.getArticoliAttivi().contains(a));
		
		lista.rimuoviArticolo(a);
		assertFalse(lista.getArticoliAttivi().contains(a));
        assertTrue(lista.getArticoliRimossi().contains(a));
	}

	
	@Test
    void testCalcoloTotale() {
		ListaDiArticoli lista = new ListaDiArticoli("Test Totale");
        lista.aggiungiArticolo(new Articolo("Pane", 2.0, "", null));
        lista.aggiungiArticolo(new Articolo("Latte", 1.5, "", null));
        
        assertEquals(3.5, lista.getPrezzoTotale());
    }
	
	@Test
    void testRicercaPerPrefisso() {
		ListaDiArticoli lista = new ListaDiArticoli("Test Ricerca");
        Articolo a1 = new Articolo("Pasta", 1.0, "", null);
        Articolo a2 = new Articolo("Pasticcini", 5.0, "", null);
        
        
        lista.aggiungiArticolo(a1);
        lista.aggiungiArticolo(a2);
        lista.rimuoviArticolo(a2); 
        
        List<Articolo> risultati = lista.cercaPerPrefisso("Pas");
        assertTrue(risultati.contains(a1));
        assertTrue(risultati.contains(a2));
    }
}
