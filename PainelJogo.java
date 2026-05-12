import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class PainelJogo extends JPanel implements ActionListener {

    // configurações da tela
    final int tamanho_tela = 60;
    final int colunas = 20;
    final int linhas = 15;

    // posição e velocidade do jogador
    int tamanho_player = 40;
    int playerX = 300;
    int playerY = 300;
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

    // variáveis para o lixo
    int lixoX, lixoY;

    // controle do lixo
    boolean lixoAtivo = false;
    boolean carregandoLixo = false;

    int lixeiraX;
    int lixeiraY;

    int pontuacao = 0;
    
    // construtor da tela do jogo
    public PainelJogo() {
        setPreferredSize(new Dimension(colunas * tamanho_tela, linhas * tamanho_tela));
        setBackground(Color.gray);
        setFocusable(true);

        // adiciona o listener de teclado
        addKeyListener(new KeyHandler());

        gerarLixo();

        lixeiraX = 9*tamanho_tela;
        lixeiraY = 7*tamanho_tela;

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
        int nextX = playerX;
        int nextY = playerY;

        if (w) nextY -= velocidade;
        if (a) nextX -= velocidade;
        if (s) nextY += velocidade;
        if (d) nextX += velocidade;

        // movimento vertical separado
        if (!colidindo(playerX, nextY)) {
            playerY = nextY;
        }

        // movimento horizontal separado
        if (!colidindo(nextX, playerY)) {
            playerX = nextX;
        }

        verificarColeta();
        verificarEntrega();

        // gera novo lixo se necessário
        if (!lixoAtivo && !carregandoLixo) {
            gerarLixo();
        }
    }

    // verifica colisão com paredes
    public boolean colidindo(int x, int y) {
        int margem = 5;
        int colunaEsquerda = (x + margem) / tamanho_tela;
        int colunaDireita = (x + tamanho_player - margem - 1) / tamanho_tela;
        int linhaTopo = (y + margem) / tamanho_tela;
        int linhaBase = (y + tamanho_player - margem - 1) / tamanho_tela;

        if (mapa[linhaTopo][colunaEsquerda] == 1) return true;
        if (mapa[linhaTopo][colunaDireita] == 1) return true;
        if (mapa[linhaBase][colunaEsquerda] == 1) return true;
        if (mapa[linhaBase][colunaDireita] == 1) return true;

        return false;
    }
    
    // renderiza o jogo
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int row = 0; row < linhas; row++) {
            for (int col = 0; col < colunas; col++) {
                if (mapa[row][col] == 1) {
                    g.setColor(Color.darkGray);
                    g.fillRect(col * tamanho_tela, row * tamanho_tela, tamanho_tela, tamanho_tela);
                }
            }
        }

        // jogador
        g.setColor(Color.green);
        g.fillRect(playerX, playerY, tamanho_player, tamanho_player);

        // HUD
        g.setColor(Color.white);
        g.drawString("Pontuação: " + pontuacao, 10, 20);
        g.drawString("Tempo: 60", 10, 40);

        if (lixoAtivo) {
            g.setColor(Color.yellow);
            g.fillRect(lixoX, lixoY, tamanho_tela, tamanho_tela);
        }

        // lixeira
        g.setColor(Color.blue);
        g.fillRect(lixeiraX, lixeiraY, tamanho_tela, tamanho_tela);
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

    // gera lixo em uma posição aleatória do mapa
    Random random = new Random();

    public void gerarLixo() {
        int col, row;
        do {
            col = random.nextInt(colunas);
            row = random.nextInt(linhas);
        } while (mapa[row][col] != 0); // garante que o lixo seja gerado em um espaço vazio

        lixoX = col * tamanho_tela;
        lixoY = row * tamanho_tela;

        lixoAtivo = true;
    }

    // verifica se o jogador coletou o lixo
    public void verificarColeta() {
        if (!lixoAtivo || carregandoLixo) return;

        Rectangle player = new Rectangle(playerX, playerY, tamanho_player, tamanho_player);
        Rectangle lixo = new Rectangle(lixoX, lixoY, tamanho_tela, tamanho_tela);

        if (player.intersects(lixo)) {
            carregandoLixo = true;
            lixoAtivo = false;
        }
    }

    public void verificarEntrega() {
        if (!carregandoLixo) return;
            
        Rectangle player = new Rectangle(playerX, playerY, tamanho_player, tamanho_player);
        Rectangle lixeira = new Rectangle(lixeiraX, lixeiraY, tamanho_tela, tamanho_tela);

        if (player.intersects(lixeira)) {
            carregandoLixo = false;
            pontuacao += 100;
            gerarLixo();
        }
    }
}