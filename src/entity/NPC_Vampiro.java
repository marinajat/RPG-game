package entity;

import main.GamePanel;

import java.util.Random;

public class NPC_Vampiro extends Entity {
    public NPC_Vampiro(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;
        getImage();
        setDialogue();
    }

    public void getImage() {
        up1 = setup("/npc/vampiro_up_1", gp.tileSize,gp.tileSize);
        up2 = setup("/npc/vampiro_up_2", gp.tileSize,gp.tileSize);
        down1 = setup("/npc/vampiro_down_1", gp.tileSize,gp.tileSize);
        down2 = setup("/npc/vampiro_down_2", gp.tileSize,gp.tileSize);
        left1 = setup("/npc/vampiro_left_1", gp.tileSize,gp.tileSize);
        left2 = setup("/npc/vampiro_left_2", gp.tileSize,gp.tileSize);
        right1 = setup("/npc/vampiro_right_1", gp.tileSize,gp.tileSize);
        right2 = setup("/npc/vampiro_right_2", gp.tileSize,gp.tileSize);
    }

    public void setDialogue() {
        // Guardando textos de dialogos
        dialogues[0] = "Você chegou longe, mas seu caminho termina aqui. Prepare-se para lutar!";

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

    public void speak() {
        // Posso fazer uma sobrescrita e usar uma fala especifica para o player qualquer
        super.speak();
    }
}
