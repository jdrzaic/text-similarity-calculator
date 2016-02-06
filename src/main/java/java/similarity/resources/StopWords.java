package java.similarity.resources;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/**
 * Razred reprezentira objekte koji sadrze stopwords iz opisa zadatka.
 * Nude se metode za dohvat pohranjenih rijeci, te provjeru je li neka rijec
 * pohranjena u internoj kolekciji razreda.
 * @author jelena
 *
 */
public class StopWords {
	
	/**
	 * Pohranjene rijeci
	 */
	Set<String> stops;
	
	/**
	 * Put do dokumenta iz kojeg citamo rijeci
	 */
	private String path;
	
	/**
	 * Konstruktor razreda.
	 * Puni internu kolekciju rijecima.
	 * @param path put do dokumenta s rijecima
	 * @throws Exception kod problema s citanjem iz filea
	 */
	public StopWords(String path) throws Exception {
		stops = new HashSet<String>();
		this.path = path;
		readAndFill();
	}
	
	/**
	 * Metoda vraca rijeci pohranjene u internoj kolekciji
	 * instance.
	 * @return pohranjene rijeci
	 */
	public Set<String> getStopWords() {
		return stops;
	}
	
	/**
	 * Metoda za neku rijec provjerava nalazi li se u internoj kolekciji instance.
	 * @param toCheck rijec koju provjeravamo
	 * @return true ako rijec postoji, false inace
	 */
	public boolean isStopWord(String toCheck) {
		return stops.contains(toCheck);
	}
	
	/**
	 * Metoda cita rijeci iz tekstualnog filea te
	 * ih pohranjuje u internu kolekciju elemenata.
	 * U slucaju nemogucnosti obavljanja citanja, korisniku 
	 * ispisuje prigodnu poruku na standardni output.
	 * @throws Exception kod problema s citanjem iz filea
	 */
	private void readAndFill() throws Exception {
	
		BufferedReader br = null;
		try {
			br = new BufferedReader(
					 new InputStreamReader(
					 new BufferedInputStream(
					 new FileInputStream(path)),"UTF-8"));
			
			String line;
			while((line = br.readLine()) != null) {
				if(line.trim().endsWith(".")) {
					line = line.trim().substring(0, line.trim().length() - 1);
				}
				line = line.trim();
				if(line.length() > 0) {
					stops.add(line.trim().toLowerCase());
				}
			}
		} catch (IOException e) {
			System.err.println("Error reading from " + path + e.getMessage() + e.getCause());
			return;
		} finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
