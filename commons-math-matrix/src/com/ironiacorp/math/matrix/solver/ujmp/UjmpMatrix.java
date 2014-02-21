package com.ironiacorp.math.matrix.solver.ujmp;

import org.ujmp.core.enums.ValueType;

import com.ironiacorp.math.matrix.solver.Matrix;
import com.ironiacorp.math.matrix.solver.MatrixType;
import com.ironiacorp.math.matrix.solver.MatrixValueType;


public abstract class UjmpMatrix<T extends Number> implements Matrix<T>
{
	private MatrixType matrixType;
	
	private MatrixValueType matrixValueType;

	protected org.ujmp.core.Matrix ujmpMatrix;
	
	protected UjmpMatrix(MatrixValueType matrixValueType, MatrixType matrixType, long numRows, long numColumns)
	{
		// This is a hack, as it must always be the same value as the generics type.
		switch (matrixValueType) {
			case INT:
				this.matrixValueType = MatrixValueType.INT;
				break;
			case FLOAT:
				this.matrixValueType = MatrixValueType.FLOAT;
				break;
			default:
				this.matrixValueType = MatrixValueType.FLOAT;
		}
		
		this.matrixType = matrixType;
		throw new UnsupportedOperationException();
		/*
		switch (this.matrixType) {
			case SPARSE:
				ujmpMatrix = org.ujmp.core.MatrixFactory.sparse(this.matrixValueType, numRows, numColumns);
				break;
			case DENSE:
				ujmpMatrix = org.ujmp.core.MatrixFactory.dense(this.matrixValueType, numRows, numColumns);
				break;
			default:
				ujmpMatrix = org.ujmp.core.MatrixFactory.dense(this.matrixValueType, numRows, numColumns);

		}
		*/
	}
	
	protected UjmpMatrix(org.ujmp.core.Matrix matrix)
	{
		// This is a hack, as it must always be the same value as the generics type.
		switch (matrix.getValueType()) {
			case INT:
				this.matrixValueType = MatrixValueType.INT;
				break;
			case FLOAT:
				this.matrixValueType = MatrixValueType.FLOAT;
				break;
			default:
				this.matrixValueType = MatrixValueType.FLOAT;
		}

		switch (matrix.getStorageType()) {
			case DENSE:
				this.matrixType = MatrixType.DENSE;
				break;
			case SPARSE:
				this.matrixType = MatrixType.SPARSE;
				break;
			default:
				throw new IllegalArgumentException("Unsupported matrix type: " + matrix.getStorageType().toString());
		}

		ujmpMatrix = matrix;
	}
	
	@Override
	public long getCols()
	{
		return ujmpMatrix.getColumnCount();
	}

	@Override
	public long getRows()
	{
		return ujmpMatrix.getRowCount();
	}
}
