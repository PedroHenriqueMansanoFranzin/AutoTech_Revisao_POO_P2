package br.com.autotech.model;

import br.com.autotech.exception.FrotaLotadaException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

// Classe que guarda a lista de veiculos e faz os calculos do relatorio
public class Frota {

    // a lista eh do tipo Veiculo, entao aceita carro e caminhao (polimorfismo)
    private final List<Veiculo> veiculos = new ArrayList<>();
    private final int capacidade;

    public Frota(int capacidade) {
        this.capacidade = capacidade;
    }

    // adiciona um veiculo, mas antes verifica se ainda tem vaga
    public void adicionarVeiculo(Veiculo veiculo) throws FrotaLotadaException {
        if (veiculo == null) {
            throw new IllegalArgumentException("Veículo não pode ser nulo.");
        }

        if (veiculos.size() >= capacidade) {
            throw new FrotaLotadaException(
                    "Frota lotada! Capacidade máxima: " + capacidade + " veículos.");
        }

        veiculos.add(veiculo);
    }

    public List<Veiculo> getVeiculos() {
        return veiculos;
    }

    public int getCapacidade() {
        return capacidade;
    }

    // soma a diaria de todos os veiculos
    // (usei stream: pega cada veiculo -> calcula a diaria -> vai somando)
    public BigDecimal calcularTotalDiarias() {
        return veiculos.stream()
                .map(Veiculo::calcularDiaria)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }

    // media = total / quantidade de veiculos
    public BigDecimal calcularMediaDiarias() {
        // se a frota estiver vazia retorna 0 pra nao dividir por zero
        if (veiculos.isEmpty()) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }

        return calcularTotalDiarias()
                .divide(BigDecimal.valueOf(veiculos.size()), 2, RoundingMode.HALF_UP);
    }

    // conta quantos veiculos tem de um tipo (ex: CarroPasseio.class)
    public long contarPorTipo(Class<? extends Veiculo> tipo) {
        return veiculos.stream()
                .filter(tipo::isInstance)
                .count();
    }
}
