package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {
    //funciona como a janela do jogo

    // CONFIGURAÇÕES DE TELA
    final int originalTileSize = 16; //16 x 16 tile - Corresponde ao tamanho do player character, NPCs e mapas;
    final int scale = 3; // Escalar o tamanho dos bonecos para que eles pareçam maiores hoje em dia

    public final int tileSize = originalTileSize * scale; // 48 x 48 tile
    public final int maxScreenCol = 16; //tamanho final do comprimento da janela no jogo
    public final int maxScreenRow = 12; //tamanho final da altura da janela no jogo
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // Configurações do MAPA
    public final int maxWorldCol = 55; //+3d +3e
    public final int maxWorldRow = 52; //+2i

    //Sistema
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound sEffects = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Thread gameThread; // Faz com que um processo fique ocorrendo n vezes por segundo, atualizando a tela;

    // Entity e Object
    public Player player = new Player(this, keyH);
    public Entity obj[] = new Entity[10];
    public Entity npc[] = new Entity[10];
    public Entity monster[] = new Entity[20];
    ArrayList<Entity> entityList = new ArrayList<>();

    // Game State
    public int gameState;
    public int titleState;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;


    int FPS = 70;


    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // melhora o desempenho da renderização gráfica em aplicações que envolvem animações ou renderizações frequentes de tela
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setObject(); // metodo para adicionar outros objetos do jogo
        aSetter.setNPC();
        aSetter.setMonster();
        //playMusic(0);
        gameState = titleState; // jogo irá iniciar a partir daqui;
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() { // aqui que fica o loop do jogo

        double drawInterval = 1000000000/FPS; //0.01666666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) { //enquanto o gameThread existir, continue o jogo

            // 1 UPDATE: atualizar informação sobre a posição do personagem
            update();
            // 2 DRAW: desenhar na tela com a informação atualizada
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/100000; //transformando em milisegundos

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void update() {
        if(gameState == playState) {
            //PLAYER
            player.update();
            //NPC
            for (int i = 0; i < npc.length; i++) {
                if(npc[i] != null) {
                    npc[i].update();
                }
            }
            // Monsters
            for(int i = 0; i < monster.length; i++) {
                if(monster[i] != null) {
                    monster[i].update();
                }
            }
        }
        if(gameState == pauseState){
            //nada agora
        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // DEBUG, verificando a performance para o desenho dos tiles na tela
        long drawStart = 0;
        if (keyH.checkDrawTime == true) {
            drawStart = System.nanoTime();
        }

        // TITLE SCREEN
        if(gameState == titleState) {
            ui.draw(g2);
        } else {
            //Tile
            tileM.draw(g2);
            entityList.add(player);


            //Adicionando Entidades para a lista
            //NPC
            for(int i = 0; i < npc.length; i++) {
                if(npc[i] != null) {
                    entityList.add(npc[i]);
                }
            }

            //Objetos
            for (int i = 0; i < obj.length; i++) {
                if(obj[i] != null) {
                    entityList.add(obj[i]);
                }
            }

            // Monsters
            for (int i = 0; i < monster.length; i++) {
                if(monster[i] != null) {
                    entityList.add(monster[i]);
                }
            }

            // Criando um z-index de todos os entitys, fazendo com que quem tiver no menor y fique atrás
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {

                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }});

            // Desenhando as Entitys
            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }
            // Esvaziando a lista entity
            for(int i = 0; i < entityList.size(); i++) {
                entityList.remove(i);
            }

            //UI
            ui.draw(g2);

        }

        //Debug
        if (keyH.checkDrawTime == true) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Tempo de desenho: " + passed, 10, 400);
            System.out.println("Tempo de desenho: " + passed);
        }

        g2.dispose(); // metodo essencial para a gestão eficiente dos recursos, usado para liberar qualquer recurso do sistema que o objeto estiver usando;

    }
    public void playMusic(int i) {

        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic(){
        music.stop();
    }
    public void playSE (int i) {
        //efeitos rapidos de som não precisam de loop
        sEffects.setFile(i);
        sEffects.play();
    }
}
