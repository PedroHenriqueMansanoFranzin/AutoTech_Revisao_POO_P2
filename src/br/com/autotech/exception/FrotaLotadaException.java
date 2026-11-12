package br.com.autotech.exception;

// Excecao lancada quando tenta adicionar veiculo e a frota ja esta cheia
public class FrotaLotadaException extends Exception {

    public FrotaLotadaException(String mensagem) {
        super(mensagem);
    }
}
