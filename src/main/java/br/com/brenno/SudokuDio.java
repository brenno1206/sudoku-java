package br.com.brenno;

import br.com.brenno.util.Espaco;
import br.com.brenno.util.Painel;
import static br.com.brenno.model.PainelTemplate.PAINEL_TEMPLATE;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import java.util.Scanner;
import java.util.stream.Stream;
import static java.util.stream.Collectors.toMap;

public class SudokuDio {
    
    private final static Scanner scanner = new Scanner(System.in);
    
    private static Painel painel = null;
    private final static int PAINEL_LIMITE = 9;

    public static void main(String[] args) {
        final var posicoes = Stream.of(args)
                .collect(toMap(
                        k -> k.split(";")[0],
                        v -> v.split(";")[1]
                ));
        
        var opcao = -1;
        
        while (true) {
            System.out.println("Selecione uma das opções a seguir: ");
            System.out.println("1- Iniciar um novo Jogo");
            System.out.println("2- Colocar um novo numero");
            System.out.println("3- Remover um numero");
            System.out.println("4- Visualizar jogo atual");
            System.out.println("5- Verificar status do Jogo");
            System.out.println("6- Limpar Jogo");
            System.out.println("7- Finalizar Jogo");
            System.out.println("8- Sair");
            
            opcao = scanner.nextInt();
            
            switch (opcao) {
                case 1 -> iniciarJogo(posicoes);
                case 2 -> colocarNumero();
                case 3 -> removerNumero();
                case 4 -> visualizarJogoAtual();
                case 5 -> verificarStatus();
                case 6 -> limparJogo();
                case 7 -> finalizarJogo();
                case 8 -> System.exit(0);
                default -> System.out.println("Opção invalida. Selecione uma das opcoes do menu.");
            }
        }
        
    }


    private static void iniciarJogo(Map<String, String> posicoes) {
        if (nonNull(painel)) {
            System.out.println("O jogo ja foi iniciado");
            return;
        }
        
        List<List<Espaco>> espacos = new ArrayList<>();
        for (int i = 0; i < PAINEL_LIMITE; i++) {
            espacos.add(new ArrayList<>());
            for (int j = 0; j < PAINEL_LIMITE; j++) {
                var posicaoConfig = posicoes.get("%s,%s".formatted(i, j));
                var esperado = Integer.parseInt(posicaoConfig.split(",")[0]);
                var fixo = Boolean.parseBoolean(posicaoConfig.split(",")[1]);
                var espacoAtual = new Espaco(esperado, fixo);
                espacos.get(i).add(espacoAtual);
            }
        }
        
        painel = new Painel(espacos);
        System.out.println("O jogo esta pronto para comecar");
    }

    private static void colocarNumero() {
        if (isNull(painel)) {
            System.out.println("O jogo aina nao foi iniciado");
            return;
        }
        
        System.out.println("Informe a coluna em que sera inserido: ");
        var coluna = continuarAteNumeroValido(0, 8);
        System.out.println("Informe a linha em que sera inserido: ");
        var linha = continuarAteNumeroValido(0, 8);
        
        System.out.printf("Informe o numero que vai entrar em [%s,%s]\n", coluna, linha);
        var valor = continuarAteNumeroValido(1, 9);
        
        if (!painel.mudarValor(coluna, linha, valor)) {
            System.out.printf("A posicao [%s,%s] tem valor fixo\n", coluna, linha);
        }
    }

    private static void removerNumero() {
        if (isNull(painel)) {
            System.out.println("O jogo aina nao foi iniciado");
            return;
        }
        
        System.out.println("Informe a coluna em que sera removido: ");
        var coluna = continuarAteNumeroValido(0, 8);
        System.out.println("Informe a linha em que sera removido: ");
        var linha = continuarAteNumeroValido(0, 8);
        
        if (!painel.limparValor(coluna, linha)) {
            System.out.printf("A posicao [%s,%s] tem valor fixo\n", coluna, linha);
        }

    }

    private static void visualizarJogoAtual() {
        if (isNull(painel)) {
            System.out.println("O jogo aina nao foi iniciado");
            return;
        }
        
        var args = new Object[81];
        var argsPos = 0;
        
        for (int i = 0; i < PAINEL_LIMITE; i++) {
            for (var col : painel.getEspacos()) {
                args[argsPos ++] = " " + (isNull(col.get(i).getValor()) ? " " : col.get(i).getValor());
            }
        }
        
        System.out.println("JOGO ATUAL: ");
        System.out.printf((PAINEL_TEMPLATE) + "\n", args);
    }

    private static void verificarStatus() {
        if (isNull(painel)) {
            System.out.println("O jogo ainda nao foi iniciado");
            return;
        }
        
        System.out.printf("STATUS ATUAL: %s\n", painel.getStatus().getLabel());
        if (painel.temErros()) {
            System.out.println("O JOGO CONTEM ERROS");
        } else {
            System.out.println("O JOGO NAO CONTEM ERROS");
        }
        
    }

    private static void limparJogo() {
        if (isNull(painel)) {
            System.out.println("O jogo ainda nao foi iniciado.");
            return;
        }
        painel.resetar();
    }   

    private static void finalizarJogo() {
        if (isNull(painel)) {
            System.out.println("O jogo aina nao foi iniciado.");
            return;
        }
        
        if (painel.jogoTemrminou()) {
            System.out.println("Parabens! Voce consluiu o jogo.");
            visualizarJogoAtual();
            painel = null;
        } else if(painel.temErros()) {
            System.out.println("Seu jogo contem erros. Verifique e ajuste-o.");
        } else {
            System.out.println("Preencha todas as posicoes.");
        }
    }
    
    private static int continuarAteNumeroValido(final int min, final int max) {
        var atual = scanner.nextInt();
        while (atual < min || atual > max) {
            System.out.println("Informe um numero entre" + min + " e " + max + ":\n");
            atual = scanner.nextInt();
        }
        return atual;
    }

}
