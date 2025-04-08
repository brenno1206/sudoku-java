package br.com.brenno.ui.custom.botao;

import javax.swing.*;
import java.awt.event.ActionListener;

public class FimJogo extends JButton {
    public FimJogo(final ActionListener actionListener) {
        this.setText("Concluir");
        this.addActionListener(actionListener);
    }
}
