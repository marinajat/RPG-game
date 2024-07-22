package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public int maxLife;
    public int life;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y; // guardando os valores default pois irei trocar os valores x e y depois
        solidArea.width = 32;
        solidArea.height = 32;
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 25; // posição default do player.
        worldY = gp.tileSize * 21; // quero que o player sempre esteja no centro da tela para que possa ter um mapa
        // maior
        speed = 4;
        direction = "down";

        // Player Status
        maxLife = 6;
        life = maxLife;
    }

    public void getPlayerImage() {
        up1 = setup("/player/player_man_up_1", gp.tileSize,gp.tileSize);
        up2 = setup("/player/player_man_up_2", gp.tileSize,gp.tileSize);
        down1 = setup("/player/player_man_down_1", gp.tileSize,gp.tileSize);
        down2 = setup("/player/player_man_down_2", gp.tileSize,gp.tileSize);
        left1 = setup("/player/player_man_left_1", gp.tileSize,gp.tileSize);
        left2 = setup("/player/player_man_left_2", gp.tileSize,gp.tileSize);
        right1 = setup("/player/player_man_right_1", gp.tileSize,gp.tileSize);
        right2 = setup("/player/player_man_right_2", gp.tileSize,gp.tileSize);
    }

    public void getPlayerAttackImage() {
        attackUp1 = setup("/player/player_attack_up_1", gp.tileSize,gp.tileSize*2);
        attackUp2 = setup("/player/player_attack_up_2", gp.tileSize,gp.tileSize*2);
        attackDown1 = setup("/player/player_attack_down_1", gp.tileSize,gp.tileSize*2);
        attackDown2 = setup("/player/player_attack_down_2", gp.tileSize,gp.tileSize*2);
        attackLeft1 = setup("/player/player_attack_left_1", gp.tileSize*2,gp.tileSize);
        attackLeft2 = setup("/player/player_attack_left_2", gp.tileSize*2,gp.tileSize);
        attackRight1 = setup("/player/player_attack_right_1", gp.tileSize*2,gp.tileSize);
        attackRight2 = setup("/player/player_attack_right_2", gp.tileSize*2,gp.tileSize);
    }

    public void update() {
        if (keyH.downPressed || keyH.upPressed ||
                keyH.leftPressed || keyH.rightPressed || keyH.enterPressed) {
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
            }
            // checando o Tile Collision
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // checando object collision
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // Checando a colisão do NPC
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            // Checando a colisão dos monstros
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            // Checando os eventos
            gp.eHandler.checkEvent();

            gp.keyH.enterPressed = false;

            // Se collisionOn for false o player pode se mover
            // primeiro checamos a direção do movimento para só agora checar a colisão
            if (!collisionOn && !keyH.enterPressed) {
                switch (direction) {
                    case "up": worldY -= speed;break;
                    case "down": worldY += speed;break;
                    case "left": worldX -= speed;break;
                    case "right": worldX += speed;break;
                }
            }

            gp.keyH.enterPressed = false;

            spriteCounter++;
            if (spriteCounter > 10) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
        if(invincible) {
            invincibleCounter ++;
            if(invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void pickUpObject(int i) {
        if (i != 999) {

        }
    }

    public void interactNPC(int i) {
        if (i != 999) {
            // Se o index não for 999, significa que o player está tocando em um NPC
            if (gp.keyH.enterPressed) {
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
        }
    }

    public void contactMonster(int i) {
        if (i != 999) {
            if(!invincible) {
                life -= 1;
                invincible = true;
            }
        }
    }

    public void draw(Graphics2D g2) {

        // g2.setColor(Color.white);
        // g2.fillRect(x,y,gp.tileSize,gp.tileSize);
        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
                break;
        }

        if(invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        g2.drawImage(image, screenX, screenY, null);

        // Reset Alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    }

}