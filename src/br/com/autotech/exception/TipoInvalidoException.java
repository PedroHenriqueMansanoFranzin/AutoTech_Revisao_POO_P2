package br.com.autotech.exception;

// Excecao lancada quando o tipo do veiculo digitado nao existe (nao eh 1 nem 2)
public class TipoInvalidoException extends Exception {

    public TipoInvalidoException(String mensagem) {
        super(mensagem);
    }
}
