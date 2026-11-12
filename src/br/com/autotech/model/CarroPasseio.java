package br.com.autotech.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

// Carro de passeio herda de Veiculo
public class CarroPasseio extends Veiculo {

    // constantes do calculo da diaria
    private static final BigDecimal FATOR_TAXA = new BigDecimal("1.20");
    private static final BigDecimal VALOR_POR_PASSAGEIRO = new BigDecimal("15.00");

    private final int passageiros;

    public CarroPasseio(String placa, int ano, BigDecimal taxaBase, int passageiros) {
        super(placa, ano, taxaBase); // chama o construtor da classe mae

        // minimo de 2 passageiros
        this.passageiros = (passageiros < 2) ? 2 : passageiros;
    }

    public int getPassageiros() { return passageiros; }

    // diaria = (taxa base * 1.20) + (15 * passageiros)
    @Override
    public BigDecimal calcularDiaria() {
        BigDecimal parteTaxa = getTaxaBase().multiply(FATOR_TAXA);
        BigDecimal partePassageiros = VALOR_POR_PASSAGEIRO.multiply(BigDecimal.valueOf(passageiros));

        return parteTaxa.add(partePassageiros).setScale(2, RoundingMode.HALF_UP);
    }
}
