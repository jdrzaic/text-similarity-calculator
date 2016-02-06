package hr.fer.zemris.java.hw12.trazilica.shell.environment;

import hr.fer.zemris.java.hw12.trazilica.commands.ShellCommand;

/**
 * Sucelje reprezentira okruzenje shell-a implementiranog u ovom 
 * projektu. Kao narede koriste se implementacije sucelja {@link ShellCommand}.
 * @author Jelena Drzaic
 *
 */
public interface IEnvironment {

	/**
	 * Metoda dohvaca {@link ShellCommand} s zadanim imenom.
	 * @param name ime naredbe
	 * @return odgovarajuci {@link ShellCommand}, ako postoji
	 */
	public ShellCommand getByName(String name);
	
	/**
	 * Metoda dodaje implementaciju {@link ShellCommand}
	 * u this
	 * @param command naredba koju ubacujemo
	 */
	public void addCommand(ShellCommand command);
	
	/**
	 * Metoda provjerava postoji li zadana naredba.
	 * @param name ime naredbe
	 * @return true ako naredba postoji, false inace
	 */
	public boolean contains(String name);
}
