package br.com.brenno.util;

public class Espaco {
    private final int valorReal;
    private Integer valor;
    private final boolean fixado;

    public Espaco(int valorReal, boolean fixado) {
        this.valorReal = valorReal;
        this.fixado = fixado;
        if (fixado) {
            valor = valorReal;
        }
    }

    public int getValorReal() {
        return valorReal;
    }

    public Integer getValor() {
        return valor;
    }

    public boolean isFixado() {
        return fixado;
    }

    public void setValor(Integer valor) {
        if (fixado) return;
        this.valor = valor;
    }
    
    public void limparEspaco() {
        setValor(null);
    }
    
    
}
