package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Life extends Entity {
    public OBJ_Life (GamePanel gp) {
        super(gp);
        name = "Life";
        image = setup("/objects/heart_full");
        image2 = setup("/objects/heart_half");
        image3 = setup("/objects/heart_blank");
    }
}
