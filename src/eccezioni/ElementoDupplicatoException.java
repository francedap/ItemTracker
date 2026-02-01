package eccezioni;

// Eccezione lanciata quando si tenta di creare una lista o categoria con un nome già esistente nel sistema.

public class ElementoDupplicatoException extends Exception {

	private static final long serialVersionUID = 1L;

	public ElementoDupplicatoException(String messaggio) {
		super(messaggio);
	}

}
