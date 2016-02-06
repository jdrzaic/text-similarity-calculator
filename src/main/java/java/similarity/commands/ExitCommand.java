package java.similarity.commands;

import java.similarity.resources.Resources;

/**
 * Razred implementira sucelje {@link ShellCommand}.
 * Naredba sluzi za izlaz iz programa.
 * @author jelena
 *
 */
public class ExitCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(String arguments, Resources res) {
		return ShellStatus.TERMINATE;
	}

	@Override
	public String getName() {
		return "exit";
	}
}
