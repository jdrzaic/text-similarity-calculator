package java.similarity.vector;

import hr.fer.zemris.linearna.IMatrix;

/**
 * Razred reprezentira pogled na matricu, tj
 * njen transponirani oblik.
 * @author jelena Drzaic
 *
 */
public class MatrixTransposeView extends AbstractMatrix {

	/**
	 * Originalna matrica
	 */
	private IMatrix original;
	
	/**
	 * Konstruktor
	 * @param orig motrica na koju stvaramo pogled
	 */
	public MatrixTransposeView(IMatrix orig) {
		original = orig;
	}
	
	@Override
	public int getRowsCount() {
		return original.getColsCount();
	}

	@Override
	public int getColsCount() {
		return original.getRowsCount();
	}

	@Override
	public double get(int row, int col) {
		return original.get(col, row);
	}

	@Override
	public IMatrix set(int row, int col, double value) {
		original.set(col, row, value);
		return this;
	}

	@Override
	public IMatrix copy() {
		return new MatrixTransposeView(original.copy());
	}

	@Override
	public IMatrix newInstance(int rows, int cols) {
		return original.newInstance(rows, cols);
	}

}
