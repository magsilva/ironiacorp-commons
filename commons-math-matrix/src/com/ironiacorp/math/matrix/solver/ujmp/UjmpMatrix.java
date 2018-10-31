package com.ironiacorp.math.matrix.solver.ujmp;

import org.ujmp.core.enums.ValueType;
import org.ujmp.core.DenseMatrix;
import org.ujmp.core.SparseMatrix;
import org.ujmp.core.intmatrix.impl.DefaultDenseIntMatrix2D;
import org.ujmp.core.intmatrix.impl.DefaultSparseIntMatrix;
import org.ujmp.core.doublematrix.impl.DefaultDenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.impl.DefaultSparseDoubleMatrix;

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

		switch (this.matrixType) {
			case SPARSE:
				switch (matrixValueType) {
        		                case INT:
						ujmpMatrix = new DefaultSparseIntMatrix(numRows, numColumns);
                        		        break;
		                        case FLOAT:
					default:
						ujmpMatrix = new DefaultSparseDoubleMatrix(numRows, numColumns);
                		                break;
		                }
				break;
			case DENSE:
			default:
				switch (matrixValueType) {
        		                case INT:
						ujmpMatrix = new DefaultDenseIntMatrix2D((int)numRows, (int)numColumns);
                        		        break;
		                        case FLOAT:
					default:
						ujmpMatrix = new DefaultDenseDoubleMatrix2D((int)numRows, (int)numColumns);
                		                break;
		                }
				break;
		}
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

		if (matrix instanceof DenseMatrix) {
			this.matrixType = MatrixType.DENSE;
		} else if (matrix instanceof SparseMatrix) {
			this.matrixType = MatrixType.SPARSE;
		} else {
			throw new IllegalArgumentException("Unsupported matrix type");
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
