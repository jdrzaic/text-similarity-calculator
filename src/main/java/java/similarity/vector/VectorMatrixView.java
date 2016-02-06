package java.similarity.vector;

import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.IncompatibleOperandException;
import hr.fer.zemris.linearna.UnmodifiableObjectException;

/**
 * Razred repprezentira pogled na jednostupcanu/jednoretcanu matricu,
 * u obliku vektora
 * @author jelena Drzaic
 *
 */
public class VectorMatrixView extends AbstractVector{

	/**
	 * Dimenzija vektora
	 */
	int dimension;
	
	/**
	 * Retcana/stupcana
	 */
	boolean rowMatrix;
	
	/**
	 * Originalna matrica
	 */
	IMatrix original;
	
	/**
	 * Konstruktor razreda
	 * @param original originalna matrica
	 */
	public VectorMatrixView(IMatrix original) {
		this.original = original;
		if(original.getRowsCount() == 1) {
			rowMatrix = true;
			dimension = original.getColsCount();
		}else if(original.getColsCount() == 1) {
			rowMatrix = false;
			dimension = original.getRowsCount();
		}else {
			throw new IncompatibleOperandException(); 
		}
	}
	
	@Override
	public IVector copy() {
		return new VectorMatrixView(original.copy());
	}

	@Override
	public double get(int pos) {
		if(rowMatrix) {
			return original.get(0, pos);
		}
		return original.get(pos, 0);
	}

	@Override
	public int getDimension() {
		return dimension;
	}

	@Override
	public IVector newInstance(int dimension) {
		return LinAlgDefaults.defaultVector(dimension);
	}

	@Override
	public IVector set(int pos, double value)
			throws UnmodifiableObjectException {
		if(rowMatrix) {
			original.set(0, pos, value);
		}else {
			original.set(pos, 0, value);
		}
		return this;
	}
}
