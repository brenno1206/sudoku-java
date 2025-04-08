package br.com.brenno.util;

import static br.com.brenno.util.StatusEnum.COMPLETE;
import static br.com.brenno.util.StatusEnum.INCOMPLETE;
import java.util.Collection;
import java.util.List;
import static java.util.Objects.nonNull;
import static java.util.Objects.isNull;
import static br.com.brenno.util.StatusEnum.NON_STARTED;

public class Painel {
    private final List<List<Espaco>> espacos;

    public Painel(List<List<Espaco>> espacos) {
        this.espacos = espacos;
    }

    public List<List<Espaco>> getEspacos() {
        return espacos;
    }
    
    public StatusEnum getStatus() {
        if (espacos.stream().flatMap(Collection::stream).noneMatch(e -> !e.isFixado() && nonNull(e.getValor()))) {
            return NON_STARTED;
        }
        
        return espacos.stream().flatMap(Collection::stream).anyMatch(e -> isNull(e.getValor())) ? INCOMPLETE : COMPLETE;
    }
    
    public boolean temErros() {
        if (getStatus() == NON_STARTED) {
            return false;
        }
        
        return espacos.stream().flatMap(Collection::stream)
                .anyMatch(e -> nonNull(e.getValor()) && !e.getValor().equals(e.getValorReal()));

    }
    
    public boolean mudarValor(final int col, final int row, final Integer valor) {
        Espaco espaco = espacos.get(col).get(row);
        if (espaco.isFixado()) {
            return false;
        }
        espaco.setValor(valor);
        return true;
    }
    
    public boolean limparValor(final int col, final int row) {
        Espaco espaco = espacos.get(col).get(row);
        if (espaco.isFixado()) {
            return false;
        }
        espaco.limparEspaco();
        return true;
    }
    
    public void resetar() {
        espacos.forEach(c -> c.forEach(Espaco::limparEspaco));
    }
    public boolean jogoTemrminou() {
        return !temErros() && getStatus().equals(COMPLETE);
    }
}
