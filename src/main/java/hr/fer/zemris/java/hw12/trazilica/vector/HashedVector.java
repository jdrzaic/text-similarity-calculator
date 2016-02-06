package hr.fer.zemris.java.hw12.trazilica.vector;
import hr.fer.zemris.linearna.IVector;

import java.util.HashMap;
import java.util.Map;

/**
 * Razred naslijeduje apstraktni razred {@link AbstractVector}.
 * Implementacija je korisna kod zahtjeva o velikom vektoru, s
 * mnogo jednakih vrijednosti, npr. kod dokumenata koje reprezentiramo 
 * brojem pojavljivanja neke rijeci. Pretpostavljamo da ce se u pojedinom dokumentu
 * pojaviti samo manjina rijeci iz vokabulara.
 * @author jelena
 *
 */
public class HashedVector extends AbstractVector{

	/**
	 * Inicijalni kapacitet vektora
	 */
	private static final int INITIAL_CAPACITY = 32;

	/**
	 * Elementi vektora
	 * Parovi index, vrijednost
	 */
	Map<Integer, Double> elements;
		
	/**
	 * Konstruktor razreda 
	 * Stvara novi vektor inicijalne velicine INITIAL_CAPACITY
	 */
	public HashedVector() {
		this(INITIAL_CAPACITY);
	}
	
	/**
	 * Konstruktor razreda.
	 * Kopira dobivenu mapu u internu kolekciju.
	 * @param map mapa koju kopiramo u vlastitu kolekciju
	 */
	public HashedVector(Map<Integer, Double> map) {
		this.elements = new HashMap<Integer, Double>(map);
	}
	
	/**
	 * Metoda stvara novi vektor zadane velicine
	 * @param size velicina novog vektora
	 */
	public HashedVector(int size) {
		this.elements = new HashMap<>(size);
	}

	@Override
	public IVector copy() {
		IVector newV  = new HashedVector(elements);
		return newV;
	}

	@Override
	public double get(int arg) {
		if(elements.containsKey(arg)) {
			return elements.get(arg);
		}
		return 0;
	}

	@Override
	public int getDimension() {
		return elements.size();
	}

	@Override
	public IVector newInstance(int size) {
		return new HashedVector(size);
	}

	@Override
	public IVector set(int index, double element) {
		elements.put(index, element);
		return this;
	}

}
