package test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import gestioneListe.GestioneListe;
import eccezioni.*;

class GestioneListeTest {

    @Test
    void testCreazioneListaDuplicataLanciaEccezione() {
    	
        assertDoesNotThrow(() -> GestioneListe.creaLista("Spesa Casa"));
  
        assertThrows(ElementoDupplicatoException.class, () -> {
            GestioneListe.creaLista("ciao");
        }, "Dovrebbe impedire la creazione di liste con nomi identici");
    }

    @Test
    void testEliminazioneListaInesistente() {
        assertThrows(ElementoNonTrovatoException.class, () -> {
            GestioneListe.eliminaLista("ListaCheNonEsiste");
        });
    }
}