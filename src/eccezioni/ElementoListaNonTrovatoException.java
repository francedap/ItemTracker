package eccezioni;

public class ElementoListaNonTrovatoException extends Exception {

	private static final long serialVersionUID = 1L;

	public ElementoListaNonTrovatoException(String messaggio) {
		super(messaggio);
	}

}
