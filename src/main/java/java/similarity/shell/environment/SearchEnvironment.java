package java.similarity.shell.environment;

import java.similarity.commands.ExitCommand;
import java.similarity.commands.QueryCommand;
import java.similarity.commands.ShellCommand;
import java.util.HashMap;
import java.util.Map;

/**
 * Razred implementira sucelje {@link IEnvironment}.
 * Razred se koristi kao environment u ovom projektu
 * @author jelena
 *
 */
public class SearchEnvironment implements IEnvironment{

	/**
	 * Pohranjene komande
	 */
	Map<String, ShellCommand> commands;
	
	/**
	 * Konstruktor razreda.
	 * Dodaje dostupne naredbe u internu kolekciju.
	 */
	public SearchEnvironment() {
		commands = new HashMap<String, ShellCommand>();
		fillAvailableCommands();
	}
	
	/**
	 * Metoda dodaje u kolekciju naredbi naredbe
	 * query i exit, koje su trenutno dostupne.
	 */
	private void fillAvailableCommands() {
		commands.put("query", new QueryCommand());
		commands.put("exit", new ExitCommand());
	}

	@Override
	public void addCommand(ShellCommand command) {
		commands.put(command.getName(), command);
	}

	@Override
	public ShellCommand getByName(String name) {
		return commands.get(name);
	}
	
	/**
	 * Metoda provjerava postoji li komanda s proslijedenim imenom.
	 * @param name ime naredbe
	 * @return true ako postoji komanda s proslijedenim imenom, false inace
	 */
	public boolean contains(String name) {
		return commands.containsKey(name);
	}
}
