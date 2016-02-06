package java.similarity.vector;

import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.IncompatibleOperandException;

/**
 * Razred reprezentira Vektor u obliku jednoretcane/jednostupcane matrice
 * @author jelena Drzaic
 *
 */
public class MatrixVectorView  extends AbstractMatrix{
	
	/**
	 * originalni vektor
	 */
	private IVector original;
	
	/**
	 * kao redak
	 */
	boolean asRowMatrix;
	
	/**
	 * Konstruktor
	 * @param original originalni vektor
	 * @param asRowMatrix stvara retcanu matricu ako je true, stupcanu inace
	 */
	public MatrixVectorView(IVector original, boolean asRowMatrix) {
		this.original = original;
		this.asRowMatrix = asRowMatrix;
	}
	@Override
	public int getRowsCount() {
		if(asRowMatrix) {
			return 1;
		}
		return original.getDimension();
	}

	@Override
	public int getColsCount() {
		if(asRowMatrix) {
			return original.getDimension();
		}
		return 1;
	}

	@Override
	public double get(int row, int col) {
		if(asRowMatrix) {
			if(row > 0) {
				throw new IncompatibleOperandException();
			}else {
				return original.get(col);
			}
		}else {
			if(col > 0) {
				throw new IncompatibleOperandException();
			}else {
				return original.get(row);
			}
		}
	}

	@Override
	public IMatrix set(int row, int col, double value) {
		if(asRowMatrix) {
			if(row > 0) {
				throw new IncompatibleOperandException();
			}else {
				original.set(col, value);
			}
		}else {
			if(col > 0) {
				throw new IncompatibleOperandException();
			}else {
				original.set(row, value);
			}
		}
		return this;
	}

	@Override
	public IMatrix copy() {
		return new MatrixVectorView(original.copy(), asRowMatrix);
	}

	@Override
	public IMatrix newInstance(int rows, int cols) {
		return LinAlgDefaults.defaultMatrix(rows, cols);
	}
}
