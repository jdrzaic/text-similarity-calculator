package java.similarity.vector;

import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.IVector;

/**
 * Razred implementira tvornicu promjenjivih matrica/vektora.
 * Sadrzi metode za stvaranje novog vektora,
 * te isto i za matricu.
 * Sluze prvenstveno kao izbor defaultne implementacije.
 * @author Jelena Drzaic
 *
 */
public class LinAlgDefaults {
	
	/**
	 * Metoda vraca novu matricu zadanih velicina
	 * @param rows broj redaka
	 * @param cols broj stupaca
	 * @return novokreirana matrica
	 */
	public static IMatrix defaultMatrix(int rows, int cols) {
		return new Matrix(rows, cols);
	}
	

	/**
	 * Metoda vraca novi vektor zadanih velicina
	 * @return novokreirani vektor
	 * @param dimension dimenzija vektora.
	 */
	public static IVector defaultVector(int dimension) {
		return new Vector(true, true, new double[dimension]);
	}
}
