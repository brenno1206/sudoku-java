package br.com.brenno.service;

import br.com.brenno.util.Espaco;
import br.com.brenno.util.Painel;
import br.com.brenno.util.StatusEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class PainelService {
    private final static int PAINEL_LIMITE = 9;

    private final Painel painel;

    public PainelService(final Map<String, String> jogoConfig ) {
        this.painel = new Painel(iniciarPainel(jogoConfig));
    }

    public  List<List<Espaco>> getEspacos() {
        return this.painel.getEspacos();
    }

    public void resetar() {
        this.painel.resetar();
    }

    public boolean TemErros() {
        return this.painel.temErros();
    }

    public StatusEnum getStatus() {
        return this.painel.getStatus();
    }

    public boolean jogoTemrminou() {
        return this.painel.jogoTemrminou();
    }

    private List<List<Espaco>> iniciarPainel(Map<String, String> jogoConfig) {
        List<List<Espaco>> espacos = new ArrayList<>();
        for (int i = 0; i < PAINEL_LIMITE; i++) {
            espacos.add(new ArrayList<>());
            for (int j = 0; j < PAINEL_LIMITE; j++) {
                var posicaoConfig = jogoConfig.get("%s,%s".formatted(i, j));
                var esperado = Integer.parseInt(posicaoConfig.split(",")[0]);
                var fixo = Boolean.parseBoolean(posicaoConfig.split(",")[1]);
                var espacoAtual = new Espaco(esperado, fixo);
                espacos.get(i).add(espacoAtual);
            }
        }

        return espacos;
    }


}
