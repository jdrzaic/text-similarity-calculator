package hr.fer.zemris.java.hw12.trazilica.shell;

import java.util.List;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Map.Entry;

import hr.fer.zemris.java.hw12.Konzola;
import hr.fer.zemris.java.hw12.trazilica.commands.QueryCommand;
import hr.fer.zemris.java.hw12.trazilica.commands.ShellCommand;
import hr.fer.zemris.java.hw12.trazilica.commands.ShellStatus;
import hr.fer.zemris.java.hw12.trazilica.resources.Resources;
import hr.fer.zemris.java.hw12.trazilica.shell.environment.IEnvironment;
import hr.fer.zemris.java.hw12.trazilica.shell.environment.SearchEnvironment;

/**
 * Razred implementira korisnicku interakciju s {@link Konzola}.
 * @author jelena
 */
public class Session {

	/**
	 * Lista koja sadrzi slicne dokumente,
	 * sadrzaj joj se generira u metodi doWork()
	 */
	private List<Entry<String, Double>> similars = null;
	
	/**
	 * Put do dokumenta sa stopwords
	 */
	public static final String STOP_WORDS_PATH = "resources/stopwords.txt";
	
	/**
	 * Put do mape s dokumentima
	 */
	String path;
	
	/**
	 * Instanca {@link Resources} koristena u programu
	 */
	private Resources resources;
	
	/**
	 * Instanca implementacije {@link IEnvironment}
	 * koristena u programu
	 */
	IEnvironment env;
	
	/**
	 * Konstruktor razreda
	 * @param string put do mape s dokumentima
	 * @param searchEnvironment instanca {@link SearchEnvironment} 
	 * koristena u programu
	 */
	public Session(String string, SearchEnvironment searchEnvironment) {
		path = string;
		env = searchEnvironment;
		try {
			resources = new Resources(STOP_WORDS_PATH, path);
			System.out.println("Velicina rjecnika je " + resources.getVocabulary().numberOfWords() + " riječi");
		} catch (Exception e) {
			System.err.println("Ne mogu procitati dokumente");
		}
	}

	/**
	 * Metoda koja obavlja obradu korisnickih naredbi, 
	 * zahtjeva. Podrzava naredbe pohranjene u {@link SearchEnvironment}
	 * instanci.
	 */
	public void doWork() {
		
		BufferedReader br = 
                new BufferedReader(new InputStreamReader(System.in));

		ShellStatus status = ShellStatus.CONTINUE;
		
		do {
			System.out.print("Enter command > ");
			ShellCommand toExec;
			
			try {
				String command = br.readLine().trim();
				String[] args = command.split("\\s+");
				//postoji u environmentu
				if(env.contains(args[0])) {
					toExec = env.getByName(args[0]);
					status = toExec.executeCommand(command.trim().substring(toExec.getName().length(), 
							command.trim().length()), resources);
					//dohvacamo slicne dokumente
					if(toExec instanceof QueryCommand) {
						similars = ((QueryCommand) toExec).results();
					}
				}else if(command.startsWith("type")) {
					printValueOf(command);
				}else if(command.trim().equals("results")){
					((QueryCommand)env.getByName("query")).printSimilar();
				}else {
					System.out.println("Nepoznata naredba");
				}
			} catch (IOException e) {
				
			}
			
		}while(status != ShellStatus.TERMINATE);
	}

	/**
	 * Metoda ispisuje dokument koji je medu dokumentima slicnim
	 * queriju. Pozicija dokumenta koji ispisujemo dobiva se iz stringa.
	 * @param string string s informacijom o poziciji dokumenta kojeg ispisujemo
	 */
	private void printValueOf(String string) {
		if(similars == null) {
			System.out.println("naredba se može koristiti tek nakon primjene"
					+ " 'query' naredbe");
			return;
		}else {
			String[] args = string.split("\\s+");
			int which = (int) Double.parseDouble(args[1]);
			if(which >= similars.size()) {
				System.out.println("Ne postoji željeni rezultat");
				return;
			}
			
			System.out.print("Dokument: " + similars.get(which).getKey() + "\n");
			printDocument(similars.get(which).getKey());
		}
	}

	/**
	 * Metoda ispisuje dokument put do kojeg je dan u proslijedenom
	 * stringu. Ispis se obavlja na standardni output.
	 * @param key put do dokumenta koji ispisujemo
	 */
	private void printDocument(String key) {
		BufferedReader br;
		BufferedWriter bw;
		try {
			br = new BufferedReader(
					 new InputStreamReader(
					 new BufferedInputStream(
					 new FileInputStream(key)),"UTF-8"));
			bw = new BufferedWriter(new OutputStreamWriter(System.out));
			bw.write("-----------------------------------------------------------------------------------------");
			bw.newLine();
			String line;
			while((line = br.readLine()) != null) {
				bw.write(line);
				bw.write("\n");
			}
			bw.write("-----------------------------------------------------------------------------------------");
			bw.newLine();
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
