package com.ironiacorp.ecc.locales.brazil;

import com.ironiacorp.ecc.Module10;
import com.ironiacorp.ecc.NumberValidator;

public class PISPASEP
{
	private boolean isValido(String pisPasep)
	{
		NumberValidator validator = new NumberValidator();
		pisPasep = validator.createValidNaturalNumber(pisPasep);
		if (pisPasep.length() != 11) {
			return false;
		}
		
		return  Module10.obterDV(pisPasep.substring(0, 10)).equals(pisPasep.substring(10, 11));
	}
}