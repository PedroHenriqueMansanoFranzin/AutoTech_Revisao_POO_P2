package br.com.autotech.controller;

import br.com.autotech.exception.FrotaLotadaException;
import br.com.autotech.exception.TipoInvalidoException;
import br.com.autotech.factory.VeiculoFactory;
import br.com.autotech.model.Frota;
import br.com.autotech.model.Veiculo;
import java.math.BigDecimal;

/*
 * Controller da frota usando o padrao Singleton
 * (so existe uma instancia dele no programa inteiro).
 */
public class FrotaController {

    private static FrotaController instancia;
    private Frota frota;

    // construtor privado -> obriga a usar o getInstance()
    private FrotaController() {
    }

    // se ainda nao tiver instancia cria uma, senao devolve a que ja existe
    public static synchronized FrotaController getInstance() {
        if (instancia == null) {
            instancia = new FrotaController();
        }
        return instancia;
    }

    public void inicializarFrota(int capacidade) {
        this.frota = new Frota(capacidade);
    }

    // cria o veiculo pela factory e ja tenta colocar na frota
    public void registrarVeiculo(int tipo, String placa, int ano, BigDecimal taxaBase, double extra)
            throws TipoInvalidoException, FrotaLotadaException {

        Veiculo veiculo = VeiculoFactory.criarVeiculo(tipo, placa, ano, taxaBase, extra);
        frota.adicionarVeiculo(veiculo);
    }

    public Frota getFrota() {
        return frota;
    }
}
