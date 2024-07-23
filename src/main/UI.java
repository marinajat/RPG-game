package main;

import entity.Entity;
import object.OBJ_Life;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class UI {
    /* renderiza todas as informações que aparecerão por cima da tela, poderemos usar mensagens de texto
    icones dos itens, etc.
     */
    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_50B;
    BufferedImage heart_full, heart_half, heart_blank;
    public boolean messageOn = false;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int commandNumPause = 0;




    public UI (GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial",Font.PLAIN, 40);
        arial_50B = new Font("Arial",Font.BOLD, 45);

        // Create HUD Object
        Entity life = new OBJ_Life(gp);
        heart_full = life.image;
        heart_half = life.image2;
        heart_blank = life.image3;
    }

    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);
    }
    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.white);

        // Title State
        if(gp.gameState == gp.titleState) {
            drawTitleScreen();
        }

        // Play State
        if(gp.gameState == gp.playState) {
            // fazer o playstate depois
            drawPlayerLife();
            drawMessage();
        }
        // Pause State
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
            drawPlayerLife();

        }

        // Dialogue State
        if(gp.gameState == gp.dialogueState) {
            drawDialogueScreen();
            drawPlayerLife();
        }

        // CharacterState
        if(gp.gameState == gp.characterState) {
            drawCharacterScreen();
        }

    }
    public void drawPlayerLife() {
        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;

        // Draw Max Life
        while(i < gp.player.maxLife/2) {
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gp.tileSize;
        }
        // Reset
        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;

        // Draw current life
        while(i < gp.player.life) {
            g2.drawImage(heart_half, x, y, null);
            i++;
            if (i < gp.player.life) {
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x+= gp.tileSize;
        }

    }

    public void drawMessage() {

        int messageX = gp.tileSize;
        int messageY = gp.tileSize * 4;

        g2.setFont(g2.getFont().deriveFont(Font.ITALIC, 18F));

        for(int i = 0; i < message.size(); i++) {
            if(message.get(i) != null) {
                g2.setColor(Color.black);
                g2.drawString(message.get(i), messageX + 2, messageY + 2);
                g2.setColor(Color.white);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1; //messageCounter ++
                messageCounter.set(i, counter); // set the counter to the array
                messageY += 30;

                if(messageCounter.get(i) > 180) {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    public void drawTitleScreen() {
        // trocando a cor background do title screen
        g2.setColor(new Color(0, 0, 0));
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

        //Tittle Name
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 70F));
        String text = "Guardians' Odyssey";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 3;

        // Sombra do texto
        g2.setColor(new Color(100, 100, 100));
        g2.drawString(text, x + 5, y+ 5);
        // Cor do texto
        g2.setColor(Color.white);
        g2.drawString(text,x,y);

        // Imagem de um personagem
        x = gp.screenWidth / 2- (gp.tileSize);
        y += gp.tileSize*2;
        g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);

        //MENU
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 18F));
        text = "Novo Jogo";
        x = getXforCenteredText(text);
        y += gp.tileSize * 3;
        g2.drawString(text, x, y);
        if(commandNum == 0) {
            g2.drawString(">", x-(gp.tileSize * 1/2), y);
        }

        text = "Continuar";
        x = getXforCenteredText(text);
        y += gp.tileSize * 3/5;
        g2.drawString(text, x, y);
        if(commandNum == 1) {
            g2.drawString(">", x-(gp.tileSize * 1/2), y);
        }

        text = "Configurações";
        x = getXforCenteredText(text);
        y += gp.tileSize * 3/5;
        g2.drawString(text, x, y);
        if(commandNum == 2) {
            g2.drawString(">", x-(gp.tileSize * 1/2), y);
        }

        text = "Sair";
        x = getXforCenteredText(text);
        y += gp.tileSize * 3/5;
        g2.drawString(text, x, y);
        if(commandNum == 3) {
            g2.drawString(">", x-(gp.tileSize * 1/2), y);
        }

        // Créditos Menu
        g2.setFont(g2.getFont().deriveFont(Font.ITALIC, 10F));
        g2.setColor(new Color(117, 117, 117));
        text = "Este jogo foi feito por:\n Marina e Rafael para a disciplina de LPOO";
        x = getXforCenteredText(text);
        y += gp.tileSize * 2;
        g2.drawString(text, x, y);
    }
    public void drawPauseScreen() {
        //trocando o background
        g2.setColor(new Color(0, 0, 0, 100));
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

        //Tela de Pause
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 70F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 3;
        g2.drawString(text, x, y);

        // Continuar jogo
        g2.setFont(g2.getFont().deriveFont(Font.ITALIC, 20F));
        text = "Continuar";
        x = getXforCenteredText(text);
        y += gp.tileSize * 3;
        g2.drawString(text, x, y);
        if(commandNumPause == 0) {
            g2.drawString(">", x-(gp.tileSize * 1/2), y);
        }

        //Configuracoes
        text = "Configurações";
        x = getXforCenteredText(text);
        y += gp.tileSize * 3/5;
        g2.drawString(text, x, y);
        if(commandNumPause == 1) {
            g2.drawString(">", x-(gp.tileSize * 1/2), y);
        }
        // Sair do jogo
        text = "Sair do jogo";
        x = getXforCenteredText(text);
        y += gp.tileSize * 3/5;
        g2.drawString(text, x, y);
        if(commandNumPause == 2) {
            g2.drawString(">", x-(gp.tileSize * 1/2), y);
        }

        g2.drawString(text,x, y);
    }

    public void drawDialogueScreen() {
        // criando a janela de dialogo
        int x = gp.tileSize*2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 4;
        drawSubWindow(x, y,width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        x += gp.tileSize;
        y += gp.tileSize;

        // criando uma forma de quebrar a linha, pois o Graphics 2D não reconhece a quebra de linhas.
        for(String line : currentDialogue.split("/n")) {
            g2.drawString(line, x, y);
            y+= 40;

        }
    }

    public void drawCharacterScreen() {
        // Create a Frame
        final int frameX = gp.tileSize;
        final int frameY = gp.tileSize - 40;
        final int frameWidth = gp.tileSize * 5;
        final int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY,frameWidth,frameHeight);

        // TEXT
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(20F));
        int textX = frameX + 20;
        int textY = gp.tileSize;
        final int lineHeight = 35;

        // Names
        g2.drawString("Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Life", textX, textY);
        textY += lineHeight;
        g2.drawString("Strenght", textX, textY);
        textY += lineHeight;
        g2.drawString("Dexterity", textX, textY);
        textY += lineHeight;
        g2.drawString("Attack", textX, textY);
        textY += lineHeight;
        g2.drawString("Defense", textX, textY);
        textY += lineHeight;
        g2.drawString("Exp", textX, textY);
        textY += lineHeight;
        g2.drawString("Next Leve", textX, textY);
        textY += lineHeight;
        g2.drawString("Coin", textX, textY);
        textY += lineHeight;
        g2.drawString("Weapon", textX, textY);
        textY += lineHeight;
        g2.drawString("Shield", textX, textY);
        textY += lineHeight;

        g2.setFont(g2.getFont().deriveFont(15F));
        textY += lineHeight;
        g2.drawString("Aperte ESCAPE para sair", textX,textY);

        g2.setFont(g2.getFont().deriveFont(20F));


        // VALUES
        int tailX = (frameX + frameWidth) - 30;
        // Reset textY
        textY = frameY + gp.tileSize;
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.dexterity);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.defense);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.exp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.coin);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight + 20;

        g2.drawImage(gp.player.currentWeapon.down1,tailX - gp.tileSize, textY - 50, null);
        textY += gp.tileSize;
        g2.drawImage(gp.player.currentShield.down1,tailX - gp.tileSize, textY - 50, null);

    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0,0,0, 200);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width,height, 35, 35);

        c = new Color(255, 255, 255); //RGB white
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25,25);

    }

    public int getXforCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }

    public int getXforAlignToRightText(String text, int tailX) {
        int lenght = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - lenght;
        return x;
    }
}

