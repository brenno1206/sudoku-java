package br.com.brenno.ui.custom.botao;

import javax.swing.*;
import java.awt.event.ActionListener;

public class Resetar extends JButton {
    public Resetar(final ActionListener actionListener) {
        this.setText("Reiniciar Jogo");
        this.addActionListener(actionListener);
    }
}
