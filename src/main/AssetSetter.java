package main;

import entity.NPC_Et;
import entity.NPC_Gandalf;
import entity.NPC_Menino;
import entity.NPC_OldMan;
import monster.MON_GreenSlime;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter (GamePanel gp) {
        this.gp = gp;
    }

    public void setObject () {

    }

    public void setNPC() {
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize*21;
        gp.npc[0].worldY = gp.tileSize*21;

        gp.npc[1] = new NPC_Et(gp);
        gp.npc[1].worldX = gp.tileSize*11;
        gp.npc[1].worldY = gp.tileSize*21;

        gp.npc[2] = new NPC_Gandalf(gp);
        gp.npc[2].worldX = gp.tileSize*31;
        gp.npc[2].worldY = gp.tileSize*21;

        gp.npc[3] = new NPC_Et(gp);
        gp.npc[3].worldX = gp.tileSize*21;
        gp.npc[3].worldY = gp.tileSize*11;

        gp.npc[4] = new OBJ_Door(gp);
        gp.npc[4].worldX = gp.tileSize*23;
        gp.npc[4].worldY = gp.tileSize*31;

        gp.npc[5] = new NPC_Menino(gp);
        gp.npc[5].worldX = gp.tileSize*40;
        gp.npc[5].worldY = gp.tileSize*35;
    }

    public void setMonster() {


        gp.monster[0] = new MON_GreenSlime(gp);
        gp.monster[0].worldY = gp.tileSize* 23;
        gp.monster[0].worldX = gp.tileSize * 36;

        gp.monster[1] = new MON_GreenSlime(gp);
        gp.monster[1].worldY = gp.tileSize* 23;
        gp.monster[1].worldX = gp.tileSize * 37;
    }
}
