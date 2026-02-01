package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import articolo.Articolo;
import categoria.Categoria;

class ArticoloTest {

	@Test
	void testCostruttore() {
		
		
		Articolo a = new Articolo("Pane");
		assertEquals(0.0, a.getPrezzo());
		assertEquals("nessuna nota", a.getNota());
		assertEquals("non categorizzato" , a.getCategoria());
	}
	
	@Test
    void testModificaDati() {
        Articolo a = new Articolo("Pane");
        Categoria nuovaCat = new Categoria("Alimentari");
        
        a.setPrezzo(1.50);
        a.setNota("Integrale");
        a.setCategoria(nuovaCat);
        
        assertEquals(1.50, a.getPrezzo());
        assertEquals("Integrale", a.getNota());
        assertEquals("Alimentari", a.getCategoria());
    }

}
