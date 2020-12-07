package com.doublew.snowballfight.screens.battlescreen.objects.chars;

/**
 * Created by Leonemsolis on 02/01/2018.
 */

public class EnemySave {
    public boolean isBoss;
    public int x, y, skinID, damage;
    public float hp;

    public EnemySave(boolean isBoss, int x, int y, float hp, int skinID, int damage) {
        this.isBoss = isBoss;
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.skinID = skinID;
        this.damage = damage;
    }
}
