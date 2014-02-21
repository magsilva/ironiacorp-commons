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
	public long getCols()
	{
		return ujmpMatrix.getColumnCount();
	}

	@Override
	public long getRows()
	{
		return ujmpMatrix.getRowCount();
	}

	@Override
	public Object getRealImplementation()
	{
		return ujmpMatrix;
	}

	@Override
	public MatrixType getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSquared() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public com.ironiacorp.math.matrix.solver.Matrix<Float> multiply(
			com.ironiacorp.math.matrix.solver.Matrix<Float> matrix) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public com.ironiacorp.math.matrix.solver.Matrix<Float> multiply(Float scalar) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public com.ironiacorp.math.matrix.solver.Matrix<Float> divide(Float scalar) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public com.ironiacorp.math.matrix.solver.Matrix<Float> sum(
			com.ironiacorp.math.matrix.solver.Matrix<Float> matrix) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public com.ironiacorp.math.matrix.solver.Matrix<Float> subtract(
			com.ironiacorp.math.matrix.solver.Matrix<Float> matrix) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public com.ironiacorp.math.matrix.solver.Matrix<Float> transpose(
			com.ironiacorp.math.matrix.solver.Matrix<Float> matrix) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public com.ironiacorp.math.matrix.solver.Matrix<Float> pow(int e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public com.ironiacorp.math.matrix.solver.Matrix<Float> invert() {
		// TODO Auto-generated method stub
		return null;
	}
}