package main;

import entity.NPC_Gandalf;
import entity.NPC_Menino;
import entity.NPC_OldMan;
import entity.NPC_Fada;
import entity.NPC_Rei;
import monster.MON_Et;
import monster.MON_Esqueleto;
import monster.MON_GreenSlime;
import monster.MON_Vampiro;
import object.OBJ_Axe;
import object.OBJ_Key;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter (GamePanel gp) {
        this.gp = gp;
    }

    public void setObject () {
        gp.obj[0] = new OBJ_Key(gp);
        gp.obj[0].worldX = gp.tileSize*25;
        gp.obj[0].worldY = gp.tileSize*24;

        gp.obj[1] = new OBJ_Key(gp);
        gp.obj[1].worldX = gp.tileSize*21;
        gp.obj[1].worldY = gp.tileSize*20;

        gp.obj[2] = new OBJ_Axe(gp);
        gp.obj[2].worldX = gp.tileSize*26;
        gp.obj[2].worldY = gp.tileSize*22;
    }

    public void setNPC() {
        gp.npc[0] = new NPC_OldMan(gp);
       gp.npc[0].worldX = gp.tileSize*21;
       gp.npc[0].worldY = gp.tileSize*31;


       gp.npc[1] = new NPC_Fada(gp);
       gp.npc[1].worldX = gp.tileSize*28;
       gp.npc[1].worldY = gp.tileSize*16;


       gp.npc[5] = new NPC_Menino(gp);
       gp.npc[5].worldX = gp.tileSize*40;
       gp.npc[5].worldY = gp.tileSize*35;


       gp.npc[2] = new NPC_Gandalf(gp);
       gp.npc[2].worldX = gp.tileSize*11;
       gp.npc[2].worldY = gp.tileSize*36;


       gp.npc[5] = new NPC_Menino(gp);
       gp.npc[5].worldX = gp.tileSize*10;
       gp.npc[5].worldY = gp.tileSize*15;


       gp.npc[6] = new NPC_Rei(gp);
       gp.npc[6].worldX = gp.tileSize*39;
       gp.npc[6].worldY = gp.tileSize*26;
    }

    public void setMonster() {


        gp.monster[0] = new MON_GreenSlime(gp);
       gp.monster[0].worldY = gp.tileSize* 24;
       gp.monster[0].worldX = gp.tileSize * 19;


//        gp.monster[1] = new MON_GreenSlime(gp);
//        gp.monster[1].worldY = gp.tileSize* 23;
//        gp.monster[1].worldX = gp.tileSize * 37;
//
//        gp.monster[2] = new MON_GreenSlime(gp);
//        gp.monster[2].worldY = gp.tileSize* 25;
//        gp.monster[2].worldX = gp.tileSize * 37;
//
//        gp.monster[3] = new MON_GreenSlime(gp);
//        gp.monster[3].worldY = gp.tileSize* 27;
//        gp.monster[3].worldX = gp.tileSize * 37;


       gp.monster[1] = new MON_Esqueleto(gp);
       gp.monster[1].worldY = gp.tileSize* 8;
       gp.monster[1].worldX = gp.tileSize * 36;


       gp.monster[2] = new MON_Et(gp);
       gp.monster[2].worldY = gp.tileSize* 39;
       gp.monster[2].worldX = gp.tileSize * 17;


       gp.monster[3] = new MON_Vampiro(gp);
       gp.monster[3].worldY = gp.tileSize* 40;
       gp.monster[3].worldX = gp.tileSize * 41;
    }
}
