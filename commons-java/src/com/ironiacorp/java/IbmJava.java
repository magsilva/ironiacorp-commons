package com.ironiacorp.java;

import java.util.ArrayList;
import java.util.List;

public class IbmJava extends Java
{
	private boolean enableSharing;

	@Override
	public List<String> getJvmParameters() {
		List<String> parameters = new ArrayList<String>();
		
		if (enableSharing) {
			parameters.add("-Xmt");
		}

		return parameters;
	}

}
