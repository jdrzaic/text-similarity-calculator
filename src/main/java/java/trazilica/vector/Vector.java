package java.trazilica.vector;

import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.UnmodifiableObjectException;

/**
 * Konkretna reprezentacija vektora.
 * Komponente su mu tipa double
 * @author jelena Drzaic
 *
 */
public class Vector extends AbstractVector{

	/**
	 * Elementi vektora
	 */
	private  double[] elements;
	
	/**
	 * Promjenjivost vektora
	 */
	boolean modifiable;
	
	/**
	 * Konstruktor razreda
	 * @param args argumenti - komponente za novostvoreni vektor
	 */
	public Vector(double... args) {
		init(args);
		modifiable = true;
	}
	
	/**
	 * Konstruktor
	 * @param dimension dimenzija novostvorenog vektora
	 */
	private Vector(int dimension) {
		elements = new double[dimension];
		modifiable = true;
	}
	
	/**
	 * KOnstruktor
	 * @param nModifiable je li vektor promjenjiv
	 * @param nCopy treba li ga se kopirati
	 * @param args elementi vektora
	 */
	public Vector(boolean nModifiable, boolean nCopy, double[] args) {
		if(nCopy) {
			elements = args;
		}else {
			init(args);
		}
		modifiable = !nModifiable;
	}
	
	/**
	 * Inicijalizira vektor proslijedenim vrijednostima
	 * @param args vrijednosti kojima inicijaliziramo
	 */
	private void init(double... args) {
		elements = new double[args.length];
		for(int i = 0, dim = args.length; i < dim; ++i) {
			elements[i] = args[i];
		}
	}

	@Override
	public IVector copy() {
		IVector newV = new Vector(this.elements);
		return newV;
	}

	@Override
	public double get(int pos) {
		return elements[pos];
	}

	@Override
	public int getDimension() {
		return elements.length;
	}

	@Override
	public IVector newInstance(int dimension) {
		return new Vector(dimension);
	}

	@Override
	public IVector set(int pos, double value)
			throws UnmodifiableObjectException {
		if(modifiable) {
			elements[pos] = value;
		}
		else {
			throw new UnmodifiableObjectException();
		}
		return this;
	}

	/**
	 * Metoda stvara novi vektor iz stringa u kojem su zapisane komponente vektora
	 * @param args String s komponentama vektora
	 * @return novostvoreni vektor
	 */
	public static IVector parseSimple(String args) {
		String[] elemStr = args.trim().split("\\s+");
		double[] elemDou = new double[elemStr.length]; 
		try {
			for(int i = 0; i < elemStr.length; ++i) {
				elemDou[i] = Double.parseDouble(elemStr[i]);
			}
		}catch(NumberFormatException e) {
			throw new IllegalArgumentException("Cannot parse string into vector");
		}
		return new Vector(elemDou);
	}
}
