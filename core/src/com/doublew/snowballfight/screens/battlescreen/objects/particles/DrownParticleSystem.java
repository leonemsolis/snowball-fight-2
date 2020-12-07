package com.doublew.snowballfight.screens.battlescreen.objects.particles;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.doublew.snowballfight.support.AssetHandler;

/**
 * Created by Leonemsolis on 08/01/2018.
 */

public class DrownParticleSystem implements ParticleSystem {
    private boolean complete = false;
    private float initY;
    private float x, y;
    private String text;
    private BitmapFont font;
    private final float distanceUp = 30;
    private float velocity = 100;
    private float acceleration;

    public DrownParticleSystem(float x, float y, String text, boolean falling) {
        initY = y;
        this.x = x;
        this.y = y;
        this.text = text;
        font = new BitmapFont(AssetHandler.redFont.getData(), AssetHandler.redFont.getRegion(), AssetHandler.redFont.usesIntegerPositions());
        if(falling) {
            acceleration = -400f;
        } else {
            acceleration = 100f;
        }
    }

    public void update(float delta) {
        if(Math.abs(initY - y) < distanceUp) {
            velocity += acceleration * delta;
            y -= velocity * delta;
        } else if(!complete){
            complete = true;
        }
    }

    public void render(ShapeRenderer shape, SpriteBatch batch) {
        batch.begin();
        font.draw(batch, text, x, y);
        batch.end();
    }

    public boolean isComplete() {
        return complete;
    }

    @Override
    public void dispose() {
        font.dispose();
    }
}
