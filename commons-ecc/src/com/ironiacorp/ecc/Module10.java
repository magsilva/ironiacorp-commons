package com.ironiacorp.ecc;


public final class Module10
{
        /**
         * Calcular um dígito verificador a partir de uma sequência de números
         * enviada.
         *
         * @param fonte Sequência de números para cálculo do DV
         * @return DV gerado.
         */
        public static String obterDV (String fonte) {
                int pesoAlternado = 2;
                int dv = 0;
                String parcela;
                for (int i = fonte.length() - 1; i >= 0; i--) {
                        parcela = String.valueOf(Integer.parseInt(fonte.substring(i , i + 1)) * pesoAlternado);
                        for (int j = 0; j < parcela.length(); j++) {
                                dv += Integer.parseInt(parcela.substring(j,j+1));
                        }
                        if (pesoAlternado == 2) {
                                pesoAlternado--;
                        } else {
                                pesoAlternado++;
                        }
                }
                dv = dv % 10;
                if (dv == 0) {
                        return "0";
                }
                return String.valueOf(10 - dv);
        }

        /**
         * Calcular um dígito verificador com a quantidade de casas indicadas
         * a partir de uma sequência de números enviada.
         *
         * @param fonte Sequência de números para cálculo do DV
         * @param quantidadeDigitos Quantidade de dígitos a serem retornados
         * @return DV gerado.
         */
        public static String obterDV (String fonte, int quantidadeDigitos) {
                if (quantidadeDigitos > 1) {
                        String parcial = obterDV(fonte);
                        return parcial + obterDV(fonte + parcial, --quantidadeDigitos);
                } else {
                        return obterDV(fonte);
                }
        }

        /**
         * Calcular um dígito verificador a partir de uma sequência de números
         * enviada.
         * O maior peso usado atinge a base, retorna a 2
         *
         * @param fonte Sequência de números para cálculo do DV
         * @param base Valor da base que se deseja usar para o cálculo do DV
         * @param subZero Caracter que deve substituir o resultado quando o resto for 0
         * @param subUm Caracter que deve substituir o resultado quando o resto for 1
         * @return DV gerado.
         */
        public static String obterDVBaseParametrizada (String fonte, int base, char subZero, char subUm) {
                int peso = 2;
                int dv = 0;
                for (int i = fonte.length() - 1; i >= 0; i--) {
                        dv += Integer.parseInt(fonte.substring(i, i + 1)) * peso;
                        if (peso == base - 1) {
                                peso = 2;
                        } else {
                                peso++;
                        }
                }
                dv = dv % 10;
                if (dv > 1) {
                        return String.valueOf(10 - dv);
                } else if (dv == 1) {
                        return String.valueOf(subUm);
                }
                return String.valueOf(subZero);

        }

        /**
         * Calcular um dígito verificador usando o módulo 10, com
         * a quantidade de casas indicadas a partir de uma sequência de
         * números enviada.
         *
         * @param fonte Sequência de números para cálculo do DV
         * @param base Valor da base que se deseja usar para o cálculo do DV
         * @param subZero Caracter que deve substituir o resultado quando o resto for 0
         * @param subUm Caracter que deve substituir o resultado quando o resto for 1
         * @param quantidadeDigitos Quantidade de dígitos a serem retornados
         * @return DV gerado.
         */
        public static String obterDVBaseParametrizada (String fonte, int base,
                        char subZero, char subUm, int quantidadeDigitos) {
                if (quantidadeDigitos > 1) {
                        String parcial = obterDVBaseParametrizada(fonte, base, subZero, subUm);
                        return parcial + obterDVBaseParametrizada(fonte + parcial, base, subZero, subUm, --quantidadeDigitos);
                } else {
                        return obterDVBaseParametrizada(fonte, base, subZero, subUm);
                }
        }

        /**
         * Calcular um dígito verificador a partir de uma sequência de números
         * enviada e uma constante a ser acrescida ao somatório.
         * O maior peso usado atinge a base, retorna a 2
         *
         * @param fonte Sequência de números para cálculo do DV
         * @param base Valor da base que se deseja usar para o cálculo do DV
         * @param subZero Caracter que deve substituir o resultado quando o resto for 0
         * @param subUm Caracter que deve substituir o resultado quando o resto for 1
         * @param constante Valor que deve ser acrescido ao somatório durante o cálculo
         * @return DV gerado.
         */
        public static String obterDVBaseParametrizadaComConstante(String fonte, int base, char subZero, char subUm, int constante) {
                int peso = 2;
                int dv = constante;
                for (int i = fonte.length() - 1; i >= 0; i--) {
                        dv += Integer.parseInt(fonte.substring(i, i + 1)) * peso;
                        if (peso == base - 1) {
                                peso = 2;
                        } else {
                                peso++;
                        }
                }
                dv = dv % 10;
                if (dv > 1) {
                        return String.valueOf(10 - dv);
                } else if (dv == 1) {
                        return String.valueOf(subUm);
                }
                return String.valueOf(subZero);
        }

}
