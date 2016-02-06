package hr.fer.zemris.java.hw12.trazilica.commands;

/**
 * enum predstavlja statuse u kojima se moze nalaziti shell
 * Moguca stanja su CONTINUE, te TERMINATE.
 * @author jelena
 *
 */
public enum ShellStatus {
	
	/**
	 * Moguce daljnje unosenje naredbi
	 */
	CONTINUE, 
	
	/**
	 * Signalizacija da korisnik zavrsava interakciju
	 */
	TERMINATE;
}
