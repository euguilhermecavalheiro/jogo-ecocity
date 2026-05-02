import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;

public class PainelJogo extends JPanel implements ActionListener {

    // configurações da tela
    final int tamanho_tela = 60;
    final int colunas = 20;
    final int fileiras = 15;

    // posição e velocidade do jogador
    int playerX = 100;
    int playerY = 100;
    int velocidade = 10;

    // controle de teclas
    boolean w, a, s, d;

    // timer para atualizar o jogo
    Timer timer;

    int [][] mapa = {
    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
    {1,0,1,1,1,0,1,1,1,1,1,1,0,1,1,1,0,1,0,1},
    {1,0,1,0,0,0,1,0,0,0,0,1,0,0,0,1,0,1,0,1},
    {1,0,1,0,1,1,1,0,1,1,0,1,1,1,0,1,0,1,0,1},
    {1,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,1},
    {1,1,1,1,1,1,1,1,0,1,1,1,0,1,1,1,1,1,0,1},
    {1,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,1,0,1},
    {1,0,1,1,1,1,0,1,1,1,0,1,1,1,1,1,0,1,0,1},
    {1,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,0,1},
    {1,0,1,1,0,1,1,1,0,1,1,1,0,1,0,1,1,1,0,1},
    {1,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1},
    {1,1,0,1,1,1,0,1,1,1,0,1,1,1,0,1,0,1,1,1},
    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
};
    

    // construtor da tela do jogo
    public PainelJogo() {
        setPreferredSize(new Dimension(colunas * tamanho_tela, fileiras * tamanho_tela));
        setBackground(Color.gray);
        setFocusable(true);

        // adiciona o listener de teclado
        addKeyListener(new KeyHandler());

        // inicia o timer para atualizar o jogo (16ms = 60 FPS)
        timer = new Timer(16, this);
        timer.start();
    }

    // atualiza o jogo a cada tick do timer
    @Override
    public void actionPerformed(ActionEvent e) {
        update();
        repaint();
    }

    // atualiza logica da movimentação do jogador
    public void update() {
        if (w) playerY -= velocidade;
        if (a) playerX -= velocidade;
        if (s) playerY += velocidade;
        if (d) playerX += velocidade;
    }

    // renderiza o jogo
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int row = 0; row < fileiras; row++) {
    for (int col = 0; col < colunas; col++) {

        if (mapa[row][col] == 1) {
            g.setColor(Color.darkGray);
            g.fillRect(col * tamanho_tela, row * tamanho_tela, tamanho_tela, tamanho_tela);
        }
    }
}

        // jogador
        g.setColor(Color.green);
        g.fillRect(playerX, playerY, tamanho_tela, tamanho_tela);

        // HUD
        g.setColor(Color.white);
        g.drawString("Pontuação: 0", 10, 20);
        g.drawString("Tempo: 60", 10, 40);
    }

    // controla as teclas pressionadas
    class KeyHandler extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();

            if (code == KeyEvent.VK_W) w = true;
            if (code == KeyEvent.VK_A) a = true;
            if (code == KeyEvent.VK_S) s = true;
            if (code == KeyEvent.VK_D) d = true;
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int code = e.getKeyCode();

            if (code == KeyEvent.VK_W) w = false;
            if (code == KeyEvent.VK_A) a = false;
            if (code == KeyEvent.VK_S) s = false;
            if (code == KeyEvent.VK_D) d = false;
        }
    }
}