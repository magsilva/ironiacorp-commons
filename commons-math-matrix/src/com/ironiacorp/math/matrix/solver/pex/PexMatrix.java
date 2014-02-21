package com.ironiacorp.math.matrix.solver.pex;

import com.ironiacorp.math.matrix.solver.Matrix;
import com.ironiacorp.math.matrix.solver.MatrixType;

public class PexMatrix<T extends Number> implements Matrix<T>
{
	private int numcol;

	private int numrow;

	private void setNumCol(int numcol)
	{
		this.numcol = numcol;
	}

	private void setNumRow(int numrow)
	{
		this.numrow = numrow;
	}

	public PexMatrix(int numcol, int numrow)
	{
		setNumCol(numcol);
		setNumRow(numrow);
	}

	/* (non-Javadoc)
	 * @see visualizer.util.solver.PexSolver.Matrix#getNumCol()
	 */
	@Override
	public long getCols()
	{
		return numcol;
	}

	/* (non-Javadoc)
	 * @see visualizer.util.solver.PexSolver.Matrix#getNumRow()
	 */
	@Override
	public long getRows()
	{
		return numrow;
	}

	@Override
	public T get(long col, long row)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void set(long col, long row, T value)
	{
		// TODO Auto-generated method stub

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
	public Matrix<T> multiply(Matrix<T> matrix) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix<T> multiply(T scalar) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix<T> divide(T scalar) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix<T> sum(Matrix<T> matrix) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix<T> subtract(Matrix<T> matrix) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix<T> transpose(Matrix<T> matrix) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix<T> pow(int e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix<T> invert() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getRealImplementation() {
		// TODO Auto-generated method stub
		return this;
	}
}
