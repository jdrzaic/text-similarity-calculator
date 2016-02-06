package hr.fer.zemris.java.hw12.trazilica.commands;

import hr.fer.zemris.java.hw12.Konzola;
import hr.fer.zemris.java.hw12.trazilica.resources.Resources;

/**
 * Sucelje reprezenira jednu naredbu koja se koristi u ovom projektu,
 * za implementacije operacija koristenih u {@link Konzola}.
 * 
 * @author Jelena Drzaic
 *
 */
public interface ShellCommand {

	/**
	 * Metoda sluzi za izvrsavanje zadace koju obuhvaca zadana naredba.
	 * @param arguments string koji sadrzi argumenti naredbe
	 * @param res instance {@link Resources} koja se koristi
	 * @return TERMINATE u slucaju naredbe exit, CONTINUE inace
	 */
	public ShellStatus executeCommand(String arguments, Resources res);
	
	/**
	 * Metoda vraca ime naredbe, zadano konkretnim implementacijama 
	 * sucelja.
	 * @return ime naredbe
	 */
	public String getName();
}
