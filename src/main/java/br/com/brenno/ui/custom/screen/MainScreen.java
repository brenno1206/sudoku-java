package br.com.brenno.ui.custom.screen;

import br.com.brenno.service.EventEnum;
import br.com.brenno.service.NotifierService;
import br.com.brenno.service.PainelService;
import br.com.brenno.ui.custom.botao.ChecarStatus;
import br.com.brenno.ui.custom.botao.FimJogo;
import br.com.brenno.ui.custom.botao.Resetar;
import br.com.brenno.ui.custom.frame.MainFrame;
import br.com.brenno.ui.custom.input.NumberText;
import br.com.brenno.ui.custom.panel.MainPanel;
import br.com.brenno.ui.custom.panel.SudokuSector;
import br.com.brenno.util.Espaco;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public class MainScreen {

    private final static Dimension dimension = new Dimension(600, 600);

    private final PainelService painelService;
    private final NotifierService notifierService;

    private JButton checkGameStatusButton;
    private JButton finishGameButton;
    private JButton resetButton;

    public MainScreen(final Map<String, String> gameConfig) {
        this.painelService = new PainelService(gameConfig);
        this.notifierService = new NotifierService();
    }

    public void buildMainScreen() {
        JPanel mainPanel = new MainPanel(dimension);
        JFrame mainFrame = new MainFrame(dimension, mainPanel);
        for (int r = 0; r < 9; r+=3) {
            var endRow = r + 2;
            for (int c = 0; c < 9; c+=3) {
                var endCol = c + 2;
                var espacos = getSpacesFromSector(painelService.getEspacos(), c, endCol, r, endRow);
                JPanel sector = generateSection(espacos);
                mainPanel.add(sector);
            }
        }
        addResetButton(mainPanel);
        addCheckGameStatusButton(mainPanel);
        addFinishGameButton(mainPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private List<Espaco> getSpacesFromSector(final List<List<Espaco>> espacos,
                                             final int initCol, final int endCol,
                                             final int initRow, final int endRow) {
        List<Espaco> spaceSector = new ArrayList<>();
        for (int r = initRow; r <= endRow; r++) {
            for (int c = initCol; c <= endCol; c++) {
                spaceSector.add(espacos.get(c).get(r));
            }
        }
        return spaceSector;
    }

    private JPanel generateSection(final List<Espaco> espacos) {
        List<NumberText>  fields = new ArrayList<>(espacos.stream().map(NumberText::new).toList());
        fields.forEach(t -> notifierService.subscriber(EventEnum.CLEAR_SPACE, t));
        return new SudokuSector(fields);
    }

    private void addFinishGameButton(JPanel mainPanel) {
        finishGameButton = new FimJogo(e -> {
            if (painelService.jogoTemrminou()) {
                JOptionPane.showMessageDialog(null, "Parabens, voce concluiu o jogo");
                resetButton.setEnabled(false);
                checkGameStatusButton.setEnabled(false);
                finishGameButton.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(null, "Seu jogo tem alguma inconsistencia. Ajuste e tente novamente");
            }
        });
        mainPanel.add(finishGameButton);
    }

    private void addCheckGameStatusButton(JPanel mainPanel) {
        checkGameStatusButton = new ChecarStatus(e -> {
            var temErros = painelService.TemErros();
            var gameStatus = painelService.getStatus();
            var message = switch (gameStatus) {
                case NON_STARTED -> "O jogo não foi iniciado";
                case INCOMPLETE -> "O jogo está imcompleto";
                case COMPLETE -> "O jogo esta incompleto";
            };
            message += temErros ? " e contém erros" : " e não contém erros";
            JOptionPane.showMessageDialog(null, message);
        });
        mainPanel.add(checkGameStatusButton);
    }

    private void addResetButton(JPanel mainPanel) {
        resetButton = new Resetar(e -> {
            var dialogResult = JOptionPane.showConfirmDialog(
                    null,
                    "Deseja realmente limpar o jogo?",
                    "Limpar o jogo.",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            if (dialogResult == 0) {
                painelService.resetar();
                notifierService.notify(EventEnum.CLEAR_SPACE);
            }
        });
        mainPanel.add(resetButton);
    }
}
