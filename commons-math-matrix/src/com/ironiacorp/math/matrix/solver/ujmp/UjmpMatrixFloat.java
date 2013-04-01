package com.ironiacorp.math.matrix.solver.ujmp;

import org.ujmp.core.Matrix;

import com.ironiacorp.math.matrix.solver.MatrixType;
import com.ironiacorp.math.matrix.solver.MatrixValueType;


public class UjmpMatrixFloat extends UjmpMatrix<Float>
{
	public UjmpMatrixFloat(MatrixType matrixType, long numRows, long numColumns)
	{
		super(MatrixValueType.FLOAT, matrixType, numRows, numColumns);
	}
	
	public UjmpMatrixFloat(Matrix matrix)
	{
		super(matrix);
	}
	
	@Override
	public Float get(long col, long row)
	{
		float fValue = ujmpMatrix.getAsFloat(row, col);
		return Float.valueOf(fValue);
	}

	@Override
	public void set(long col, long row, Float value)
	{
		ujmpMatrix.setAsFloat(value, col, row);
	}

	@Override
	public long getNumCol()
	{
		return ujmpMatrix.getColumnCount();
	}

	@Override
	public long getNumRow()
	{
		return ujmpMatrix.getRowCount();
	}

	@Override
	public Object getRealImplementation()
	{
		return ujmpMatrix;
	}
}