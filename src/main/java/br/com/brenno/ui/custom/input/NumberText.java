package br.com.brenno.ui.custom.input;

import br.com.brenno.service.EventEnum;
import br.com.brenno.service.EventListener;
import br.com.brenno.util.Espaco;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class NumberText extends JTextField implements EventListener {
    private final Espaco espaco;

    public NumberText(final Espaco espaco) {
        this.espaco = espaco;
        var dimension = new Dimension(50,50);
        this.setSize(dimension);
        this.setPreferredSize(dimension);
        this.setVisible(true);
        this.setFont(new Font("Arial", Font.PLAIN, 20));
        this.setHorizontalAlignment(CENTER);
        this.setDocument(new NumberTextLimit());
        this.setEnabled(!espaco.isFixado());
        if (espaco.isFixado()) {
            this.setText(espaco.getValor().toString());
        }
        this.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                mudarEspaco();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                mudarEspaco();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                mudarEspaco();
            }
            private void mudarEspaco() {
                if (getText().isEmpty()) {
                    espaco.limparEspaco();
                    return;
                } else {
                    espaco.setValor(Integer.parseInt(getText()));
                }
            }
        });
    }

    @Override
    public void update(EventEnum eventType) {
        if (eventType.equals(EventEnum.CLEAR_SPACE) && this.isEnabled()) {
            this.setText("");
        }
    }
}
