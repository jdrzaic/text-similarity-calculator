package java.trazilica.vector;

import hr.fer.zemris.linearna.IMatrix;

/**
 * Pogled na matricu-sluzi za dohvat podmatrice originalne matrice
 * @author jelena Drzaic
 *
 */
public class MatrixSubMatrixView extends AbstractMatrix{

	/**
	 * Indeksi redaka
	 */
	private int[] rowIndexes;
	
	/**
	 * Indeksi stupaca
	 */
	private int[] colIndexes;
	
	/**
	 * Originalna matrica
	 */
	IMatrix original;
	
	/**
	 * Konstruktor razreda, stvara pogled na proslijedenu matricu
	 * @param original originalna matrica
	 * @param row izbaceni redak
	 * @param col izbaceni stupac
	 */
	public MatrixSubMatrixView(IMatrix original, int row, int col) {
		this.original = original;
		this.rowIndexes = new int[original.getRowsCount() - 1];
		this.colIndexes = new int[original.getColsCount() - 1];
		for(int i = 0; i < rowIndexes.length + 1; ++i) {
			if(i < row) {
				rowIndexes[i] = i;
			}else if(i > row) {
				rowIndexes[i - 1] = i;
			}
		}
		for(int i = 0; i < colIndexes.length + 1; ++i) {
			if(i < col) {
				colIndexes[i] = i;
			}else if(i > col) {
				colIndexes[i - 1] = i;
			}
		}
	}
	
	/**
	 * Konstruktor razreda
	 * @param original originalna matrica
	 * @param rows broj redaka
	 * @param cols broj stupaca
	 */
	public MatrixSubMatrixView(IMatrix original, int[] rows, int[] cols) {
		this.original = original;
		this.rowIndexes = rows;
		this.colIndexes = cols;
	}
	
	@Override
	public int getRowsCount() {
		return rowIndexes.length;
	}

	@Override
	public int getColsCount() {
		return colIndexes.length;
	}

	@Override
	public double get(int row, int col) {
		return original.get(rowIndexes[row], colIndexes[col]);
	}

	@Override
	public IMatrix set(int row, int col, double value) {
		original.set(rowIndexes[row], colIndexes[col], value);
		return this;
	}

	@Override
	public IMatrix copy() {
		return new MatrixSubMatrixView(original.copy(), 0, 0); 
	}

	@Override
	public IMatrix newInstance(int rows, int cols) {
		return original.newInstance(rows, cols);
	}

}
