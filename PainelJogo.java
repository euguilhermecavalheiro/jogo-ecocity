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
    int playerX = 550;
    int playerY = 490;
    int velocidade = 10;

    // controle de teclas
    boolean w, a, s, d;

    // timer para atualizar o jogo
    Timer timer;

    int [][] mapa = {
    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
    {1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1},
    {1,0,1,1,1,1,0,1,1,1,0,1,1,1,1,1,0,1,1,1},
    {1,0,1,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,1},
    {1,0,0,0,0,1,0,1,0,1,0,1,0,1,1,1,0,1,1,1},
    {1,0,1,1,1,1,0,1,0,1,0,1,0,1,0,0,0,0,0,1},
    {1,0,0,0,0,0,0,1,0,0,0,1,0,1,0,0,1,0,0,1},
    {1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,1,1,1,0,1},
    {1,0,0,0,0,0,0,1,0,0,0,1,0,1,0,0,1,0,0,1},
    {1,0,1,0,1,1,0,1,0,1,1,1,0,1,0,0,0,0,0,1},
    {1,0,1,0,0,1,0,1,0,0,0,1,0,1,1,1,1,1,0,1},
    {1,0,1,0,0,0,0,1,0,1,0,0,0,0,0,1,0,0,0,1},
    {1,0,1,1,1,1,0,1,0,1,0,1,1,1,0,1,0,1,1,1},
    {1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1},
    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
    };

    // posição do lixo
    int lixoX;
    int lixoY;
    int tamanho_lixo = 60;

    // controle do lixo
    boolean lixoAtivo = false;
    boolean carregandoLixo = false;

    // posição da lixeira
    int lixeiraX;
    int lixeiraY;
    
    int pontuacao = 0;
    int tempoRestante = 60;
    boolean jogoEncerrado = false;   
    Timer cronometro;

    boolean telaInicial = true;

    // construtor da tela do jogo
    public PainelJogo() {
        setPreferredSize(new Dimension(colunas * tamanho_tela, linhas * tamanho_tela));
        setBackground(Color.gray);
        setFocusable(true);

        addKeyListener(new KeyHandler());

        gerarLixo();

        lixeiraX = 9*tamanho_tela;
        lixeiraY = 7*tamanho_tela;

        // timer para atualizar o jogo (16ms = 60 FPS)
        timer = new Timer(16, this);

        // timer para o cronometro do jogo (1000ms = 1 segundo)
        cronometro = new Timer(1000, e -> {
            if(!jogoEncerrado) {
                tempoRestante--;
                if(tempoRestante <= 0) {
                    jogoEncerrado = true;
                    repaint();
                    timer.stop();
                    cronometro.stop();}
            }
        });  
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
        g.drawString("Tempo: " + tempoRestante, 10, 40);

        // lixo
        if (lixoAtivo) {
            g.setColor(Color.yellow);
            g.fillRect(lixoX, lixoY, tamanho_lixo, tamanho_lixo);
        }

        // lixeira
        g.setColor(Color.blue);
        g.fillRect(lixeiraX, lixeiraY, tamanho_tela, tamanho_tela);

        // tela inicial
        if (telaInicial) {
            g.setColor(new Color(0, 0, 0, 180));
            g.fillRect(0, 0, getWidth(), getHeight());

            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD, 60));
            g.drawString("EcoCity", getWidth() / 2 - 130, getHeight() / 2 - 40);
            g.setFont(new Font("Arial", Font.PLAIN, 30));
            g.drawString("Pressione ENTER para começar", getWidth() / 2 - 220, getHeight() / 2 + 30);
        }

        // tela de fim de jogo
        if (jogoEncerrado) {
            g.setColor(new Color(0, 0, 0, 180));
            g.fillRect(0, 0, getWidth(), getHeight());

            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Tempo Esgotado!", getWidth() / 2 - 160, getHeight() / 2 - 50);
            g.setFont(new Font("Arial", Font.PLAIN, 30));
            g.drawString("Pontuação: " + pontuacao, getWidth() / 2 - 110, getHeight() / 2 + 20);

            g.setFont(new Font("Arial", Font.BOLD, 25));
            g.drawString("Pressione R para reiniciar", getWidth() / 2 - 150, getHeight() / 2 + 100);
        }
    }

    // atualiza o jogo a cada tick do timer
    @Override
    public void actionPerformed(ActionEvent e) {
        update();
        repaint();
    }

    // atualiza logica da movimentação do jogador
    public void update() {
        if (telaInicial || jogoEncerrado) {
            return;
        }

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
    
    // controla as teclas pressionadas
    class KeyHandler extends KeyAdapter {
        
        @Override
        public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();
            
            if (jogoEncerrado && e.getKeyCode() == KeyEvent.VK_R) {
                reiniciarJogo();
            }
            if (telaInicial && code == KeyEvent.VK_ENTER) {
                IniciarJogo();
            }
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

    // inicia o jogo a partir da tela inicial
    public void IniciarJogo() {
        telaInicial = false;
        timer.start();
        cronometro.start();
        requestFocusInWindow();
    }
    
    // gera lixo em uma posição aleatória do mapa
    Random random = new Random();

    public void gerarLixo() {
        int col, row;
        do {
            col = random.nextInt(colunas);
            row = random.nextInt(linhas);
        } while (mapa[row][col] != 0); // garante que o lixo seja gerado na estrada

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

    // verifica se o jogador entregou o lixo na lixeira
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

    // reinicia o jogo para o estado inicial
    public void reiniciarJogo() {
        timer.stop();
        cronometro.stop();
        playerX = 550;
        playerY = 490;
        pontuacao = 0;
        tempoRestante = 60;
        jogoEncerrado = false;
        gerarLixo();
        timer.start();
        cronometro.start();
        repaint();
    }
}