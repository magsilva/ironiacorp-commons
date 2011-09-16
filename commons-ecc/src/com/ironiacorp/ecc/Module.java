package com.ironiacorp.ecc;

import java.util.ArrayList;
import java.util.List;


/**
 * Utilitário para Geração de DV pela atribuição dos pesos de acordo com a
 * posição dos números na sequência numérida informada.
 * 
 * @author Rodrigo Moreira Fagundes
 * @since 14/06/2010
 */
public final class Module {

	/**
	 * Calcular um dígito verificador a partir de uma sequência de números
	 * enviada.
	 * 
	 * @param fonte
	 *            Sequência de números para cálculo do DV
	 * @return DV gerado.
	 */
	public static String obterDV(String fonte) {
		int dv = 0;
		for (int i = 0; i < fonte.length(); i++) {
			dv += Integer.parseInt(fonte.substring(i, i + 1)) * (i + 1);
		}
		return String.valueOf(dv % 9);
	}

	
	///////////////// Com peso 
	/**
	 * Calcular um dígito verificador a partir de uma sequência de números
	 * enviada e de uma sequencia de pesos.
	 * 
	 * @param fonte
	 *            Sequência de números para cálculo do DV
	 * @param peso
	 *            Sequência de pesos para cálculo do DV
	 * @return DV gerado.
	 */
	public static String obterDV(String fonte, String peso, String forma) {
		List<String> pesoSplit = new ArrayList<String>();
		StringBuilder sbSplitter = new StringBuilder();
		for (int splitter = 0; splitter < peso.length(); splitter++) {
			if (splitter == peso.length() - 1) {
				sbSplitter.append(peso.charAt(splitter));
				pesoSplit.add(sbSplitter.toString());
				sbSplitter = new StringBuilder();
			} else if (peso.charAt(splitter) == '|') {
				pesoSplit.add(sbSplitter.toString());
				sbSplitter = new StringBuilder();
			} else {
				sbSplitter.append(peso.charAt(splitter));
			}
		}
		validar(fonte, pesoSplit);
		int dv = 0;
		for (int i = 0; i < fonte.length(); i++) {
			dv += Integer.valueOf(String.valueOf(fonte.charAt(i)))
					* Integer.parseInt(String.valueOf(pesoSplit.get(i)));
		}
		dv = dv % 11;
		if (forma.equals("caracterDireito")) {
			if (dv < 10) {
				return String.valueOf(dv);
			}
			return String.valueOf(dv - 10);
		} else if (forma.equals("mod11")) {
			dv = 11 - dv;
			if (dv > 9)
				dv -= 10;
			return String.valueOf(dv);
		} else {
			return null;
		}
	}

	/**
	 * Verifica se a fonte possui apenas números.
	 * 
	 * @param fonte
	 *            Texto a ser validado
	 * @param peso
	 *            Peso a ser validado
	 */
	private static void validar(String fonte, List<String> peso) {
		for (int i = 0; i < peso.size(); i++) {
			try {
				Integer.valueOf(peso.get(i));
			} catch (Exception e) {
				throw new IllegalArgumentException("Peso informado contém caracteres não numéricos!");
			}
		}
	}

}