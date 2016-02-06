package java.trazilica.vector;

import hr.fer.zemris.linearna.IMatrix;

/**
 * Razred naslijeduje razred AbstractMatrix
 * Predstavlja implementaciju matrice m x n s elementima tipa double.
 * @author jelena Drzaic
 *
 */
public class Matrix extends AbstractMatrix {

	/**
	 * Elementi pohranjeni u matrici.
	 */
	private double[][] elements;
	
	/**
	 * Broj redaka matrice.
	 */
	private int rows;
	
	/**
	 * Broj stupaca matrice
	 */
	private int cols;
	
	/**
	 * Konstruktor razreda
	 * @param rows broj redaka
	 * @param cols broj stupaca
	 */
	public Matrix(int rows, int cols) {
		this.elements = new double[rows][cols];
		this.rows = rows;
		this.cols = cols;
	}
	
	/**
	 * Konstruktor razreda
	 * @param rows broj redaka
	 * @param cols broj stupaca
	 * @param args elementi matrice
	 * @param useGiven boolean, oznacava je li potrebno kopiranje
	 */
	public Matrix(int rows, int cols, double[][] args, boolean useGiven) {
		this.rows = rows;
		this.cols = cols;
		if(useGiven) {
			elements = args;
		}else {
			init(rows, cols, args);
		}
	}
	
	/**
	 * Metoda inicijalizira matricu proslijedenim vrijednostima
	 * @param rows broj redaka
	 * @param cols broj stupaca
	 * @param args elementi matrice
	 */
	private void init(int rows, int cols,double[][] args) {
		elements = new double[rows][cols];
		for(int i = 0; i < rows; ++i) {
			for(int j = 0; j < cols; ++j) {
				elements[i][j] = args[i][j];
			}
		}
	}
	
	@Override
	public int getRowsCount() {
		return rows;
	}

	@Override
	public int getColsCount() {
		return cols;
	}

	@Override
	public double get(int row, int col) {
		return elements[row][col];
	}

	@Override
	public IMatrix set(int row, int col, double value) {
		elements[row][col] = value;
		return this;
	}

	@Override
	public IMatrix copy() {
		IMatrix newM = new Matrix(rows, cols, elements, false);
		return newM;
	}

	@Override
	public IMatrix newInstance(int rows, int cols) {
		IMatrix newM = new Matrix(rows, cols);
		return newM;
	}

	/**
	 * Metoda stvara novu matricu iz stringa s zapisanim elementima matrice
	 * @param args string s elementima matrice
	 * @return Stvorena matrica
	 */
	public static Matrix parseSimple(String args) {
		try {
			String[] columns = args.trim().split("\\s*\\|\\s*");
			double[][] newA = null;
			String[] elem = null;
			for(int i = 0; i < columns.length; ++i) {
				elem = columns[i].trim().split("\\s+");
				if(i == 0) {
					newA = new double[columns.length][elem.length];
				}
				for(int j = 0; j < elem.length; ++j) {
					newA[i][j] = Double.parseDouble(elem[j]);
				}
			}
			return new Matrix(columns.length, elem.length, newA, true);
		} catch (Exception e) {
			throw new IllegalArgumentException("Cannot parse string into matrix");
		}
	}

}
