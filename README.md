# aps-java-jogo-ecocity

# 🌆 EcoCity

EcoCity é um jogo 2D desenvolvido em Java utilizando Swing com foco em educação ambiental em grandes metrópoles.

O jogador deve recolher sacos de lixo espalhados pela cidade e levá-los até a lixeira central antes que o tempo acabe.

O projeto foi desenvolvido como APS (Atividade Prática Supervisionada) para a faculdade.

---

# 🎮 Gameplay

O jogo possui inspiração em clássicos arcade como Pac-Man.

Durante a partida:

* o jogador percorre um mapa estilo labirinto
* sacos de lixo aparecem aleatoriamente pela cidade
* o lixo é coletado automaticamente ao encostar nele
* apenas um saco de lixo pode ser carregado por vez
* o jogador deve levar o lixo até a lixeira central
* cada entrega aumenta a pontuação
* o jogo possui duração de 60 segundos

---

# 🕹️ Controles

| Tecla | Função                   |
| ----- | ------------------------ |
| W     | Mover para cima          |
| A     | Mover para esquerda      |
| S     | Mover para baixo         |
| D     | Mover para direita       |
| ENTER | Iniciar o jogo           |
| R     | Reiniciar o jogo         |

---

# 🧠 Conceitos Aplicados

O projeto utiliza diversos conceitos importantes de programação:

* Programação Orientada a Objetos
* Interface gráfica com Java Swing
* Game Loop
* Matrizes bidimensionais
* Sistema de colisão
* Eventos de teclado
* Renderização gráfica
* Timers
* Spawn aleatório
* Estruturação de jogo 2D

---

# 🗺️ Sistema de Mapa

O mapa funciona através de uma matriz bidimensional.

```java
1 = parede
0 = caminho livre
```

Essa matriz é utilizada para:

* desenhar o cenário
* detectar colisões
* controlar movimentação
* gerar posições válidas para o lixo

---

# 🚧 Sistema de Colisão

A colisão é feita verificando os quatro cantos do jogador dentro da matriz do mapa.

O movimento foi implementado pixel a pixel para evitar bugs em quinas e impedir que o jogador atravesse paredes.

---

# 🗑️ Sistema de Lixo

O lixo é gerado aleatoriamente em posições válidas do mapa.

Quando o jogador encosta no lixo:

* o lixo desaparece
* o jogador passa a carregar o saco
* nenhum outro lixo pode ser coletado até a entrega

---

# 🧺 Sistema de Entrega

A lixeira fica posicionada no centro do mapa.

Quando o jogador encosta nela carregando um saco de lixo:

* o lixo é entregue
* a pontuação aumenta
* um novo lixo é gerado automaticamente

---

# ⏱️ Cronômetro

O jogo possui um cronômetro regressivo de 60 segundos.

Quando o tempo chega em 0:

* o jogo encerra
* a movimentação é interrompida
* a tela de fim de jogo é exibida
* o jogador pode reiniciar pressionando a tecla R

---

# 🖼️ Interface

O jogo possui:

* tela inicial
* HUD com pontuação
* cronômetro em tempo real
* tela de fim de jogo

---

# 🖥️ Tecnologias Utilizadas

* Java
* Java Swing
* Java AWT

---

# 📂 Estrutura do Projeto

```text
aps-java-jogo-ecocity
│
├── src
│   ├── Main.java
│   ├── GameWindow.java
│   ├── GamePanel.java
│   └── KeyHandler.java
│
└── README.md
```

---

# 🚀 Como Executar

## 1. Clone o repositório

```bash
git clone https://github.com/euguilhermecavalheiro/aps-java-jogo-ecocity.git
```

---

## 2. Abra o projeto em uma IDE Java

Exemplos:

* IntelliJ IDEA
* Eclipse
* VS Code

---

## 3. Execute o arquivo principal

```text
Main.java
```

---

# 📌 Melhorias Futuras

Possíveis melhorias futuras:

* ranking de pontuação
* sprites em pixel art
* efeitos sonoros
* múltiplos mapas
* animações
* diferentes tipos de lixo
* sistema de fases
* aumento progressivo de dificuldade

---

# 👨‍💻 Autor

Guilherme Cavalheiro

GitHub:

[https://github.com/euguilhermecavalheiro](https://github.com/euguilhermecavalheiro)

LinkedIn:

[https://www.linkedin.com/in/guilherme-gomes-cavalheiro/](https://www.linkedin.com/in/guilherme-gomes-cavalheiro/)

(Texto gerado por IA)
