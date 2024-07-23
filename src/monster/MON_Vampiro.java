package monster;

import entity.Entity;
import main.GamePanel;

import java.util.Random;

public class MON_Vampiro extends Entity {
    GamePanel gp;
    public MON_Vampiro(GamePanel gp) {
        super(gp);

        this.gp = gp;

        entityTipe = 2;
        name = "Vampiro";
        speed = 1;
        maxLife = 4;
        life = maxLife;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage() {
        up1 = setup("/monster/vampiro_down_1", gp.tileSize,gp.tileSize);
        up2 = setup("/monster/vampiro_down_2", gp.tileSize,gp.tileSize);
        down1 = setup("/monster/vampiro_down_1", gp.tileSize,gp.tileSize);
        down2 = setup("/monster/vampiro_down_2", gp.tileSize,gp.tileSize);
        left1 = setup("/monster/vampiro_down_1", gp.tileSize,gp.tileSize);
        left2 = setup("/monster/vampiro_down_2", gp.tileSize,gp.tileSize);
        right1 = setup("/monster/vampiro_down_1", gp.tileSize,gp.tileSize);
        right2 = setup("/monster/vampiro_down_2", gp.tileSize,gp.tileSize);
    }
    public void setAction() {
        actionLockCounter++;

        // damos o override aqui para poder criar diferentes caracteristicas para cada NPC
        Random random = new Random();
        int i = random.nextInt(100) + 1; // pegamos um valor aleatório entre 1 e 100
        /* aqui tenho um modelo simples de IA em que 25% do tempo ele vai para cada direção:
        direita, cima, esquerda, baixo
         */
        if (actionLockCounter == 120) {

            if (i <= 25) {
                direction = "up";
            } else if (i > 25 && i <= 50) {
                direction = "down";
            } else if (i > 50 && i <= 75) {
                direction = "left";
            } else if (i > 75 && i <= 100) {
                direction = "right";
            }
            actionLockCounter = 0;
        }
    }

    public void damageReaction () {

        actionLockCounter = 0;
        direction = gp.player.direction; // está fugindo do player

    }

}
