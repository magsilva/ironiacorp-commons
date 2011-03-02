package com.ironiacorp.io;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;


public class ImprovedLineNumberReader extends LineNumberReader
{
	public ImprovedLineNumberReader(Reader in)
	{
		super(in);
	}

	@Override
	public void setLineNumber(int lineNumber)
	{
		if (lineNumber < getLineNumber()) {
			throw new UnsupportedOperationException();
		}
		
		for (int i = getLineNumber(); i < lineNumber; i++) {
			try {
				readLine();
			} catch (IOException e) {
				throw new IllegalArgumentException();
			}
		}
	}
}
