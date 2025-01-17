package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import object.OBJ_Key;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Player extends Entity {

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public int maxLife;
    public int life;
    public boolean attackCanceled = false;
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxinventorySize = 20;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 5;
        solidArea.y = 5;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y; // guardando os valores default pois irei trocar os valores x e y depois
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 10; // posição default do player.
        worldY = gp.tileSize * 7; // quero que o player sempre esteja no centro da tela para que possa ter um mapa maior
        worldX = gp.tileSize * 9; // posição default do player.
        //para o mundo 2:
        //worldY = gp.tileSize * 26; 
        // quero que o player sempre esteja no centro da tela para que possa ter um mapa maior
        
        speed = 4;
        direction = "down";

        // Player Status
        level = 1;
        maxLife = 6;
        life = maxLife;
        strength = 1;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 5;
        currentWeapon = new OBJ_Sword_Normal(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        attack = getAttack();
        defense = getDefense();
    }

    public void setItems() {
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJ_Key(gp));
        inventory.add(new OBJ_Key(gp));
    }

    public int getAttack() {
        attackArea = currentWeapon.attackArea;
        return attack = strength * currentWeapon.attackValue;
    }

    public int getDefense () {
        return defense = dexterity * currentShield.defenseValue;
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
        attackUp1 = setup("/player/player_attack_up_1", gp.tileSize*5/4,gp.tileSize*2);
        attackUp2 = setup("/player/player_attack_up_2", gp.tileSize*5/4,gp.tileSize*2);
        attackDown1 = setup("/player/player_attack_down_1", gp.tileSize* 5/4,gp.tileSize*2);
        attackDown2 = setup("/player/player_attack_down_2", gp.tileSize* 5/4,gp.tileSize*2);
        attackLeft1 = setup("/player/player_attack_left_1", gp.tileSize*3,gp.tileSize);
        attackLeft2 = setup("/player/player_attack_left_2", gp.tileSize*3,gp.tileSize);
        attackRight1 = setup("/player/player_attack_right_1", gp.tileSize*3,gp.tileSize);
        attackRight2 = setup("/player/player_attack_right_2", gp.tileSize*3,gp.tileSize);
    }

    public void update() {

        if (attacking) {
            attacking();
        }

        else if (keyH.downPressed || keyH.upPressed ||
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

    public void attacking () {
        spriteCounter++;

        if (spriteCounter <= 5) {
            spriteNum = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 2;

            // Salvando a posição X e Y, solidArea
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // Ajustando a posição do Player X/Y para a posição de ataque da arma
            switch (direction) {
                case "up": worldY -= attackArea.height; break;
                case "down": worldY += attackArea.height; break;
                case "left": worldX -= attackArea.width; break;
                case "right": worldX += attackArea.width; break;
            }

            solidArea.width = attackArea.width;
            solidAreaHeight = attackArea.height;

            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
            damageMonster(monsterIndex);



        }
        if (spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void pickUpObject(int i) {
        if (i != 999) {

            String text;

            if(inventory.size() != maxinventorySize) {
                inventory.add(gp.obj[i]);
                gp.playSE(1);
                text = "Pegou um " + gp.obj[i].name + "!";
            } else {
                text = "Inventário cheio";
            }
            gp.ui.addMessage(text);
            gp.obj[i] = null;
        }
    }

    public void interactNPC(int i) {
        if (gp.keyH.enterPressed) {
            if (i != 999) {
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            } else {
                gp.playSE(7);
                attacking = true;
            }
        }
    }

    public void contactMonster(int i) {
        if (i != 999) {
            if(!invincible) {
                gp.playSE(6);

                int damage = gp.monster[i].attack - defense;
                if(damage < 0) {
                    damage = 0;
                }
                life -= damage;
                invincible = true;
            }
        }
    }

    public void damageMonster (int i) {
        if(i != 999) {
            if (gp.monster[i].invincible == false) {
                gp.playSE(5);

                int damage = attack - gp.monster[i].defense;
                if(damage < 0) {
                    damage = 0;
                }

                gp.monster[i].life -= damage;
                gp.ui.addMessage(damage + " Dano");
                gp.monster[i].invincible = true;
                gp.monster[i].damageReaction();

                if(gp.monster[i].life <= 0) {
                    gp.monster[i].dying = true;
                    gp.ui.addMessage("Matou " + gp.monster[i].name + "!");
                    gp.ui.addMessage("Exp + " + gp.monster[i].exp);
                    exp += gp.monster[i].exp;
                    checkLevelUp();
                }
            }

        }
    }

    public void checkLevelUp () {
        if(exp >= nextLevelExp) {
            level ++;
            nextLevelExp = nextLevelExp * 2;
            maxLife += 2;
            strength++;
            dexterity++;
            life = maxLife;
            attack = getAttack();
            defense = getDefense();

            gp.playSE(8);
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "Subiu para o nível " + " agora!";
        }
    }
    public void selectItem() {
        int itemIndex = gp.ui.getItemIndexOnSlot();

        if(itemIndex < inventory.size()) {
            Entity selectedItem = inventory.get(itemIndex);

            if(selectedItem.entityTipe == type_sword || selectedItem.entityTipe == type_axe) {
                currentWeapon = selectedItem;
                attack = getAttack();
            }
            if(selectedItem.entityTipe == type_shield) {
                currentShield = selectedItem;
                defense = getDefense();
            }
            if (selectedItem.entityTipe == type_consumable) {
                //depois
            }

        }
    }

    public void draw(Graphics2D g2) {

        // g2.setColor(Color.white);
        // g2.fillRect(x,y,gp.tileSize,gp.tileSize);
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
            case "up":
                if(attacking == false) {
                    if (spriteNum == 1) {image = up1;}
                    if (spriteNum == 2) {image = up2;}
                }
                if (attacking == true) {
                    tempScreenY = screenY - gp.tileSize/2;
                    if(spriteNum == 1) {image = attackUp1;}
                    if(spriteNum == 2) {image = attackUp2;}
                }
                break;
            case "down":
                if(attacking == false) {
                    if (spriteNum == 1) {image = down1;}
                    if (spriteNum == 2) {image = down2;}
                }
                if (attacking == true) {
                    if(spriteNum == 1) {image = attackDown1;}
                    if(spriteNum == 2) {image = attackDown2;}
                }
                break;
            case "left":
                if (attacking == false) {
                    if (spriteNum == 1) {image = left1;}
                    if (spriteNum == 2) {image = left2;}
                }
                if (attacking == true) {
                    tempScreenX = screenX - gp.tileSize;
                    if(spriteNum ==1) {image = attackLeft1;}
                    if(spriteNum ==2) {image = attackLeft2;}
                }
                break;
            case "right":
                if(attacking == false) {
                    if (spriteNum == 1) {image = right1;}
                    if (spriteNum == 2) {image = right2;}
                }
                if(attacking == true) {
                    if (spriteNum == 1) {image = attackRight1;}
                    if (spriteNum == 2) {image = attackRight2;}
                }
                break;
        }
        if(invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        g2.drawImage(image, tempScreenX, tempScreenY, null);

        // Reset Alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    }

}
