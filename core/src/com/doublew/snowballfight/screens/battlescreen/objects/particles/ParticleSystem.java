package com.doublew.snowballfight.screens.battlescreen.objects.particles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by Leonemsolis on 16/11/2017.
 */

public interface ParticleSystem {

    public void update(float delta);

    public void render(ShapeRenderer shape, SpriteBatch batch);

    public boolean isComplete();

    public void dispose();
}
