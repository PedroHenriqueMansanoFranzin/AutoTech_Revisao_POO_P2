package br.com.autotech.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

/*
 * Classe mae de todos os veiculos da frota.
 * Ela eh abstrata porque nao faz sentido criar um "Veiculo" generico,
 * so carro de passeio ou caminhao (as classes filhas).
 */
public abstract class Veiculo {

    // atributos privados (encapsulamento) e final pq nao mudam depois de criados
    private final String placa;
    private final int ano;
    private final BigDecimal taxaBase;

    public Veiculo(String placa, int ano, BigDecimal taxaBase) {
        // se a placa vier nula deixa vazia pra nao dar NullPointerException
        this.placa = (placa == null) ? "" : placa;

        // ano minimo aceito eh 2000
        this.ano = (ano < 2000) ? 2000 : ano;

        // usei BigDecimal em vez de double pq eh dinheiro (evita erro de arredondamento)
        BigDecimal taxa = (taxaBase == null) ? BigDecimal.ZERO : taxaBase;
        this.taxaBase = taxa.setScale(2, RoundingMode.HALF_UP);
    }

    // getters
    public String getPlaca() { return placa; }
    public int getAno() { return ano; }
    public BigDecimal getTaxaBase() { return taxaBase; }

    // metodo abstrato: cada tipo de veiculo calcula a diaria do seu jeito (polimorfismo)
    public abstract BigDecimal calcularDiaria();

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + " [placa=" + placa + ", ano=" + ano
                + ", taxaBase=" + taxaBase
                + ", diaria=" + calcularDiaria() + "]";
    }
}
