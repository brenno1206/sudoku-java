package br.com.brenno.ui.custom.botao;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ChecarStatus extends JButton {
    public ChecarStatus(final ActionListener actionListener) {
        this.setText("Verificar Jogo");
        this.addActionListener(actionListener);
    }
}
