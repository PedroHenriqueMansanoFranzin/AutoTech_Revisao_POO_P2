package br.com.autotech.main;

import br.com.autotech.controller.FrotaController;
import br.com.autotech.exception.FrotaLotadaException;
import br.com.autotech.exception.TipoInvalidoException;
import br.com.autotech.model.CaminhaoCarga;
import br.com.autotech.model.CarroPasseio;
import br.com.autotech.model.Frota;
import br.com.autotech.model.Veiculo;
import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

/*
 * Atividade - Programacao Orientada a Objetos (UniCesumar)
 * Sistema AutoTech - controle de frota de veiculos
 *
 * Classe principal: le os veiculos digitados e no final mostra o relatorio.
 */
public class AppSimulado {

    // meu RA
    public static final String MEU_RA = "26003821-2";

    // D = ultimo numero do RA antes do traco
    public static final int D = extrairAlgarismoBase(MEU_RA);

    // taxa base = 50 + (5 * D)
    public static final BigDecimal TAXA_BASE =
            new BigDecimal("50.00").add(new BigDecimal("5.00").multiply(BigDecimal.valueOf(D)));

    // capacidade da frota = 3 + (D % 3)
    public static final int CAPACIDADE = 3 + (D % 3);

    // pega o algarismo que fica logo antes do '-' no RA
    private static int extrairAlgarismoBase(String ra) {
        int indiceTraco = ra.indexOf('-');
        char algarismo = ra.charAt(indiceTraco - 1);
        return Character.getNumericValue(algarismo);
    }

    public static void main(String[] args) {
        // deixei em US pra ler numero com ponto (ex: 2.5) no Scanner
        Locale.setDefault(Locale.US);

        FrotaController controller = FrotaController.getInstance();
        controller.inicializarFrota(CAPACIDADE);

        Scanner sc = new Scanner(System.in);

        // fica lendo ate digitar FIM
        // formato: tipo placa ano extra
        while (sc.hasNext()) {
            String token = sc.next();

            if (token.equalsIgnoreCase("FIM")) {
                break;
            }

            try {
                int tipo = Integer.parseInt(token);
                String placa = sc.next();
                int ano = sc.nextInt();
                double extra = sc.nextDouble();

                controller.registrarVeiculo(tipo, placa, ano, TAXA_BASE, extra);

            } catch (TipoInvalidoException | FrotaLotadaException e) {
                // erros nossos (tipo errado ou frota cheia)
                System.out.println("[ALERTA] " + e.getMessage());

            } catch (NumberFormatException | InputMismatchException e) {
                // digitou alguma coisa que nao eh numero, entao pula a linha
                System.out.println("[ALERTA] Entrada invalida, linha ignorada.");
                sc.nextLine();
            }
        }

        sc.close();

        imprimirRelatorio(controller.getFrota());
    }

    // mostra o relatorio final na tela
    private static void imprimirRelatorio(Frota frota) {
        System.out.println("=== RELATORIO DA FROTA AUTOTECH ===");
        System.out.println("RA OPERADOR: " + MEU_RA);
        System.out.println("TAXA BASE: " + TAXA_BASE);
        System.out.println("TOTAL DIARIAS: " + frota.calcularTotalDiarias());
        System.out.println("MEDIA DIARIAS: " + frota.calcularMediaDiarias());
        System.out.println("QTD VEICULOS: " + frota.getVeiculos().size()
                + " (PASSEIO: " + frota.contarPorTipo(CarroPasseio.class)
                + " | CARGA: " + frota.contarPorTipo(CaminhaoCarga.class) + ")");

        System.out.println("VEICULOS REGISTRADOS:");
        for (Veiculo v : frota.getVeiculos()) {
            System.out.println("- PLACA: " + v.getPlaca() + " | DIARIA: " + v.calcularDiaria());
        }

        System.out.println("===================================");
    }
}
