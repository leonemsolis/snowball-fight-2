package com.doublew.snowballfight.screens.battlescreen.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.doublew.snowballfight.support.AssetHandler;

/**
 * Created by Leonemsolis on 21/10/2017.
 */

public class Snowball {
    private Rectangle bounds;
    private boolean arrived = false;
    private final float SPEED = 300f;
    private final float SPEED_X, SPEED_Y;
    private float destX, destY;
    private final boolean MOVING_DOWN, MOVING_RIGHT;
    private int damage;
    //// TODO: 22/10/2017 Can make that Enemy can hit the enemy(own ally)?
    private boolean fromEnemy;

    // X coordinate of the target when throwing
    private int sentX;
    // Y coordinate of the target when throwing
    private int sentY;

    // 0 - Boot
    // 1 - Boulder
    // 2 - Frozen Snowball
    // 3 - Icicle
    // 4 - Basic Snowball
    private int modifier;

    public Snowball(float initX, float initY, float destX, float destY, boolean fromEnemy, int damage, int sentX, int sentY, int modifier) {
        this.modifier = modifier;
        this.damage = damage;
        bounds = new Rectangle(initX, initY, 16, 16);
        this.destX = destX;
        this.destY = destY;
        this.fromEnemy = fromEnemy;
        MOVING_RIGHT = destX > initX;
        MOVING_DOWN = destY > initY;
        float dist_x = Math.abs(destX - initX);
        float dist_y = Math.abs(destY - initY);
        float total_dist = (float)(Math.sqrt(Math.pow(dist_x, 2f) + Math.pow(dist_y, 2f)));
        float total_time = total_dist / SPEED;
        SPEED_X = dist_x / total_time;
        SPEED_Y = dist_y / total_time;
        this.sentX = sentX;
        this.sentY = sentY;
    }

    public void render(float delta, float runTime, SpriteBatch batch) {
        batch.draw(AssetHandler.snowballShadow, bounds.x-2, bounds.y+10, 20, 11);
        switch (modifier) {
            case 0:
                batch.draw(AssetHandler.itemsSmall[0], bounds.x, bounds.y, 10, 10, bounds.width, bounds.height, 1, 1, runTime * 1000);
                break;
            case 1:
                batch.draw(AssetHandler.itemsSmall[1], bounds.x, bounds.y, 10, 10, bounds.width, bounds.height, 1, 1, runTime * 1000);
                break;
            case 2:
                batch.draw(AssetHandler.itemsSmall[2], bounds.x, bounds.y, 10, 10, bounds.width, bounds.height, 1, 1, runTime * 1000);
                break;
            case 3:
                batch.draw(AssetHandler.itemsSmall[3], bounds.x, bounds.y, 10, 10, bounds.width, bounds.height, 1, 1, runTime * 1000);
                break;
            case 4:
                batch.draw(AssetHandler.itemsSmall[8], bounds.x, bounds.y, 10, 10, bounds.width, bounds.height, 1, 1, runTime * 1000);
                break;
        }

    }

    public void update(float delta) {
        if(!arrived) {
            if(MOVING_RIGHT) {
                if(bounds.x + SPEED_X * delta > destX) {
                    arrived = true;
                }
                bounds.x += SPEED_X * delta;
            } else {
                if(bounds.x - SPEED_X * delta < destX) {
                    arrived = true;
                }
                bounds.x -= SPEED_X * delta;
            }

            if(MOVING_DOWN) {
                if(bounds.y + SPEED_Y * delta > destY) {
                    arrived = true;
                }
                bounds.y += SPEED_Y * delta;
            } else {
                if(bounds.y - SPEED_Y * delta < destY) {
                    arrived = true;
                }
                bounds.y -= SPEED_Y * delta;
            }
        }
    }

    public boolean isArrived() {
        return arrived;
    }

    public boolean isFromEnemy() {
        return fromEnemy;
    }

    public int getDamage() {
        return damage;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public int getSentX() {
        return sentX;
    }

    public int getSentY() {
        return sentY;
    }

    public int getModifier() {
        return modifier;
    }
}
