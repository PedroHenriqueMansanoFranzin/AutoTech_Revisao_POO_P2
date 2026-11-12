package br.com.autotech.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

// Caminhao de carga tambem herda de Veiculo
public class CaminhaoCarga extends Veiculo {

    // constantes do calculo da diaria
    private static final BigDecimal FATOR_TAXA = new BigDecimal("1.50");
    private static final BigDecimal VALOR_POR_TONELADA = new BigDecimal("30.00");

    private final double toneladas;

    public CaminhaoCarga(String placa, int ano, BigDecimal taxaBase, double toneladas) {
        super(placa, ano, taxaBase);

        // minimo de 1 tonelada
        this.toneladas = (toneladas < 1.0) ? 1.0 : toneladas;
    }

    public double getToneladas() { return toneladas; }

    // diaria = (taxa base * 1.50) + (30 * toneladas)
    @Override
    public BigDecimal calcularDiaria() {
        BigDecimal parteTaxa = getTaxaBase().multiply(FATOR_TAXA);
        BigDecimal parteToneladas = VALOR_POR_TONELADA.multiply(BigDecimal.valueOf(toneladas));

        return parteTaxa.add(parteToneladas).setScale(2, RoundingMode.HALF_UP);
    }
}
