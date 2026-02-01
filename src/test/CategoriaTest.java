package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import categoria.Categoria;

class CategoriaTest {

	@Test
    void testCreazioneCategoria() {
		
        Categoria c = new Categoria("Alimentari");
        assertEquals("Alimentari", c.getNome(), "Il nome della categoria dovrebbe essere Alimentari");
    }

}
