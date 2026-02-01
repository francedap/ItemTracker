package eccezioni;

//Eccezione lanciata quando un elemento cercato non è presente.
 
public class ElementoNonTrovatoException extends Exception {
    private static final long serialVersionUID = 1L;

	public ElementoNonTrovatoException(String messaggio) {
        super(messaggio);
    }
}