import javax.swing.JFrame;

public class JanelaJogo extends JFrame {

    // inicia a janela do jogo
    public JanelaJogo() {
        setTitle("EcoCity Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // adiciona o painel do jogo
        PainelJogo panel = new PainelJogo();
        add(panel);

        // ajusta o tamanho da janela ao tamanho do painel
        pack();

        // centraliza a janela na tela
        setLocationRelativeTo(null);
        
        // torna a janela visível
        setVisible(true);
    }
}