package com.ironiacorp.ecc.locales.brazil;

import com.ironiacorp.ecc.Module11;

public class CNPJ {

	/**
	 * <b>Validar um CNPJ.</b><br>
	 * Obtido00 em <a href=
	 * "http://www.javafree.org/artigo/852844/Validacao-de-CNPJ-em-java.html"
	 * >http://www.javafree.org/artigo/852844/Validacao-de-CNPJ-em-java.html</a><br>
	 * 
	 * @param str_cnpj
	 *            CNPJ a ser validado.
	 * @return Verdadeiro caso seja válido. Falso, caso contrário.
	 */
	public static boolean isValido(String str_cnpj) {
		if (str_cnpj.length() != 14)
			return false;

		String cnpj_calc = str_cnpj.substring(0, 12) + gerarDigitoVerificador(str_cnpj.substring(0, 12));
		return str_cnpj.equals(cnpj_calc);
	}
	
    /**
     * Dado um conjunto de 12 números, gerar um dígito verificador.
     *
     * @param cnpj CNPJ com 12 números.
     * @return Dígito verificador.
     */
    public static String gerarDigitoVerificador(String cnpj) {
            return Module11.obterDVBase10(cnpj, false, 2);
    }
    
    /**
     * Gerar um número de CNPJ válido.<br>
     * Um número de CNPJ que é vélido não significa que exista.
     *
     * @return CNPJ gerado.
     */
    public static String gerar() {
            StringBuilder iniciais = new StringBuilder();
            Integer numero;
            for (int i = 0; i < 12; i++) {
                    numero = Integer.valueOf((int) (Math.random() * 10));
                    iniciais.append(numero.toString());
            }
            return iniciais.toString() + gerarDigitoVerificador(iniciais.toString());
    }
}
