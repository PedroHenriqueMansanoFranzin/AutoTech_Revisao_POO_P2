package br.com.autotech.factory;

import br.com.autotech.exception.TipoInvalidoException;
import br.com.autotech.model.CaminhaoCarga;
import br.com.autotech.model.CarroPasseio;
import br.com.autotech.model.Veiculo;
import java.math.BigDecimal;

/*
 * Padrao Factory: essa classe fica responsavel por criar os veiculos.
 * Assim o resto do programa nao precisa ficar dando "new CarroPasseio"
 * ou "new CaminhaoCarga", so passa o tipo.
 */
public class VeiculoFactory {

    // codigos dos tipos de veiculo
    public static final int CARRO_PASSEIO = 1;
    public static final int CAMINHAO_CARGA = 2;

    // construtor privado pra ninguem instanciar a factory (so usa o metodo static)
    private VeiculoFactory() {}

    // o "extra" eh a qtd de passageiros (carro) ou as toneladas (caminhao)
    public static Veiculo criarVeiculo(int tipo, String placa, int ano, BigDecimal taxaBase, double extra)
            throws TipoInvalidoException {

        if (tipo == CARRO_PASSEIO) {
            return new CarroPasseio(placa, ano, taxaBase, (int) extra);
        }

        if (tipo == CAMINHAO_CARGA) {
            return new CaminhaoCarga(placa, ano, taxaBase, extra);
        }

        // se chegou aqui eh pq o tipo nao existe
        throw new TipoInvalidoException("Tipo desconhecido: " + tipo);
    }
}
