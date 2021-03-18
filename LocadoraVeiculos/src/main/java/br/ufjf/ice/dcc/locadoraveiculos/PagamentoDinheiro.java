/*
Lucca Oliveira Schroder - 201765205AC
Ronan Dos Santos Rosa - 201765026AB
Jaqueline da Silva Amaral Lopes - 201976007
Wendell Guimarães Júnior - 201635032
 */
package br.ufjf.ice.dcc.locadoraveiculos;

import java.util.Date;

public class PagamentoDinheiro implements Pagamento{

    @Override
    public float pagamento(float precoDiaria, int dias) {
        float desconto = (float)95 / 100;
        return (float)(precoDiaria * dias * desconto);
    }
    
}
