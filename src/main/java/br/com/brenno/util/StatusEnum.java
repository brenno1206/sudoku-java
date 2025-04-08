
package br.com.brenno.util;

public enum StatusEnum {
    NON_STARTED("NAO INICIADO"),
    INCOMPLETE("IMCOMPLETO"),
    COMPLETE("COMPLETO");
    
    private String label;

    StatusEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
    
}
