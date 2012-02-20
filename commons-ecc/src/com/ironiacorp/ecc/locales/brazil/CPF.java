package com.ironiacorp.ecc.locales.brazil;

import com.ironiacorp.ecc.Module11;

public class CPF {
	/**
	 * Verificar se um CPF é válido.
	 * 
	 * @param cpf
	 *            CPF a ser verificado.
	 * @return Verdadeiro caso seja válido. Falso, caso contrário.
	 */
	public static boolean isValido(String cpf) {
		if (cpf.length() != 11)
			return false;
		
		String numDig = cpf.substring(0, 9);
		
		return gerarDigitoVerificador(numDig).equals(cpf.substring(9, 11));
	}
	
    /**
     * Método que calcula o dígito verificador, observando se está correto.
     * Código obtido de http://www.javafree.org/artigo/851371/Validacao-de-CPF.html.
     * Todos os direitos são do autor do código.
     *
     * @param num
     * @return Dígito verificador.
     */
    public static String gerarDigitoVerificador(String num) {
            return Module11.obterDV(num, false, 2);
    }
    
    /**
     * Gerar um CPF arbitrário.
         * Código obtido de http://www.javafree.org/artigo/851371/Validacao-de-CPF.html.
         * Todos os direitos são do autor do código.
         *
     * @return CPF gerado arbitrariamente.
     */
    public static String gerar() {
        StringBuilder iniciais = new StringBuilder();
        Integer numero;
        for (int i = 0; i < 9; i++) {
            numero = Integer.valueOf((int) (Math.random() * 10));
            iniciais.append(numero.toString());
        }
        return iniciais.toString() + gerarDigitoVerificador(iniciais.toString());
    }
}
