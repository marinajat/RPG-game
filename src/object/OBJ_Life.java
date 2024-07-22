package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Life extends Entity {
    public OBJ_Life (GamePanel gp) {
        super(gp);
        name = "Life";
        image = setup("/objects/heart_full", gp.tileSize,gp.tileSize);
        image2 = setup("/objects/heart_half", gp.tileSize,gp.tileSize);
        image3 = setup("/objects/heart_blank", gp.tileSize,gp.tileSize);
    }
}
