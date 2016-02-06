package java.similarity.resources;
import java.similarity.vector.*;

/**
 * Razred reprezentira prikaz dokumenta.
 * Sadrzi informacije o pojavljivanjima rijeci iz rijecnika
 * {@link Vocabulary} u svojoj instanci, te tf-idf vektor.
 * nudi metode za dohvat vrijednosti, i postavljanje vrijednosti na
 * zeljenu vrijednost.
 * @author jelena
 *
 */
public class Document {

	/**
	 * Vektor frekvencija
	 */
	HashedVector frequencies;

	/**
	 * TF-IDF vektor
	 */
	public HashedVector tfidf;
	
	/**
	 * Put do dokumenta
	 */
	String path;
	
	/**
	 * Konstruktor razreda.
	 * @param path put do dokumenta
	 */
	public Document(String path) {
		frequencies = new HashedVector();
		tfidf = new HashedVector();
		this.path = path;
	}
	
	/**
	 * Metoda postavlja vrijednost u vektoru frekvencija na
	 * i-toj poziciji,tj informaciju o i-toj rijeci u rijecniku.
	 * Povecava vrijednost u vektoru za 1.
	 * @param i index rijeci iz vokabulara
	 */
	public void set(int i) {
		frequencies.set(i, frequencies.get(i) + 1);
	}
	
	/**
	 * Metoda sluzi za dohvat vrijednosti pohranjene
	 * na i-toj poziciji u vektoru frekvencija.
	 * @param i pozicija za koju dohvacamo vrijednost
	 * @return vrijednost pohranjena na i-toj poziciji u frekvencijskom 
	 * vektoru
	 */
	public int getFrequency(int i) {
		return (int) frequencies.get(i);
	}
	
	/**
	 * Metoda provjerava postoji li u dokumentu 
	 * i-ta rijec iz {@link Vocabulary}.
	 * @param i pozicija rijeci u rijecniku
	 * @return broj pojavljivanja i-te rijeci rijecnika
	 */
	public boolean contains(int i) {
		return frequencies.get(i) > 0;
	}
	
	/**
	 * Metoda vraca iznos tf-idf komponente za
	 * i-tu rijec {@link Vocabulary}, te dokumenta nad kojim je pozvana.
	 * @param i pozicija rijeci u {@link Vocabulary}
	 * @return vrijednost tf-idf komponente
	 */
	public double getTfidf(int i) {
		return tfidf.get(i);
	}
	
	/**
	 * Metoda postavlja iznos tf-idf komponente za
	 * i-tu rijec {@link Vocabulary}, te dokumenta nad kojim je pozvana.
	 * @param i pozicija rijeci u {@link Vocabulary}
	 * @param value vrijedost na koju postavljamo
	 */
	public void setTfidf(int i, double value) {
		tfidf.set(i, value);
	}
	
	/**
	 * Metoda izracunava slicnost dokumenta this s proslijedenim dokumentom,
	 * koristeci vrijednosti pohranjene u tfidf vektoru.
	 * @param other vektor s kojim racunamo slicnost
	 * @return slicnost dokumenta this s proslijedenim dokumentom
	 */
 	public double calculateSimilarity(Document other) {
 		return this.tfidf.scalarProduct(other.tfidf) / 
 				(this.tfidf.norm() * other.tfidf.norm());
	}

 	/**
 	 * Metoda vraca put do dokumenta this.
 	 * @return put do dokumenta
 	 */
	public String getPath() {
		return path;
	}
}
