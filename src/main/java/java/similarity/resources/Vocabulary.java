package java.similarity.resources;

import java.util.ArrayList;
import java.util.List;

/**
 * Razred reprezentira rijecnik koji se koristi u ovom programu.
 * Rijecnik sadrzi rijeci koje definiramo kao niz alphabetica.
 * @author jelena
 *
 */
public class Vocabulary {

	/**
	 * Pohranjene rijeci
	 */
	List<String> words;
	
	/**
	 * Konstruktor razreda.
	 */
	public Vocabulary() {
		words = new ArrayList<String>();
	}
	
	/**
	 * Metoda vraca broj rijeci pohranjenih u rijecniku.
	 * @return pohranjene rijeci
	 */
	public int numberOfWords() {
		return words.size();
	}
	
	/**
	 * Metoda dodaje rijec u rijecnik.
	 * @param word rijec koju dodajemo
	 */
	public void addWord(String word) {
		if(this.getIndexOf(word) == -1) {
			words.add(word);
		}
	}
	
	/**
	 * Metoda sluzi za dohvat svih rijeci rijecnika.
	 * @return lista rijeci rijecnika
	 */
	public List<String> getAll() {
		return words;
	}
	
	/**
	 * Metoda provjerava postoji li proslijedena rijec u
	 * rijecniku.
	 * @param word rijec za koju provjeravamo
	 * @return true ako rijec postoji u rijecniku, false inace
	 */
	public boolean contains(String word) {
		return getIndexOf(word) > -1;
	}
	
	/**
	 * Metoda vraca indeks rijeci u rijecniku.
	 * @param word rijec ciji index vracamo
	 * @return index rijeci
	 */
	public int getIndexOf(String word) {
		if(words.contains(word)) {
			return words.indexOf(word);
		}
		return -1;
	}
}
