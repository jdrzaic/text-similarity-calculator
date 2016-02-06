package hr.fer.zemris.java.hw12.trazilica.resources;

import hr.fer.zemris.java.hw12.trazilica.vector.HashedVector;

import java.util.List;

/**
 * Razred sluzi za izracunavanje tf-idf vrijednosti rijeci u dokumentu.
 * Posao odraduje metoda calculate() 
 * @author jelena
 *
 */
public class TfidfCalculator {

	/**
	 * Instanca {@link Resources} koja se koristi
	 * u programu
	 */
	Resources resources;
	
	/**
	 * broj pojavljivanja i-te rijeci u raspolozivim dokumentima,
	 * za i od 0 do velicine vokabulara
	 */
	private static HashedVector nOfWords;
	
	/**
	 * Konstruktor razreda
	 * @param resources instanca {@link Resources} koji se koristi u programu.
	 */
	public TfidfCalculator(Resources resources) {
		this.resources = resources;
		nOfWords = null;
	}
	
	/**
	 * Metoda izracunava tf-idf vrijednost za proslijedene dokumente.
	 * @param documents dokumenti za koje racunamo tfidf vektore
	 */
	public void calculate(List<Document> documents) {
		if(nOfWords == null) {
			setNOfWords();
		}
		//int D = documents.size();
		int D = resources.getDocuments().size();
		for(int i = 0, duljina = resources.getVocabulary().numberOfWords(); i < duljina; ++i) {
			for(Document doc : documents) {
				double tfidf = Math.log(D / nOfWords.get(i)) * doc.getFrequency(i);
				doc.setTfidf(i, tfidf);
			}
		}
	}
	
	/**
	 * Metoda inicijalizira clansku varijablu nOfWords.
	 */
	private void setNOfWords() {
		nOfWords = new HashedVector();
		for(int i = 0, duljina = resources.getVocabulary().numberOfWords(); i < duljina; ++i) {
			int numContainers = 0;
			for(Document doc : resources.getDocuments()) {
				if(doc.contains(i)) {
					numContainers++;
				}
			}
			nOfWords.set(i, numContainers);
		}
	}
}
