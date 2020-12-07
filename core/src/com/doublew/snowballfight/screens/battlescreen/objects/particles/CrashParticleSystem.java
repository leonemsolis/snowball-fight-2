package com.doublew.snowballfight.screens.battlescreen.objects.particles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by Leonemsolis on 05/01/2018.
 */

public class CrashParticleSystem implements ParticleSystem {
    public Vector2 pos;
    public int pps = 300;
    public float particleLife = .2f;
    public float lifeVariation = .09f;

    public float angle = 270f;
    public float angleVariation = 120;

    public float minVelocity = 100f;
    public float maxVelocity = 200f;
    public Vector2 gravity;
    public final int MAX_SIZE = 20;

    public ArrayList<CrashParticle> crashParticles;

    private boolean complete = false;

    public Color startColor, endColor;

    public CrashParticleSystem(float x, float y, Color start, Color end, float particleLife) {
        startColor = start;
        endColor = end;

        pos = new Vector2(x, y);

        gravity = new Vector2(0f, 1000f);
        crashParticles = new ArrayList<CrashParticle>();
        this.particleLife = particleLife;
    }

    public void addParticles(float delta) {
        int spawnNumber = (int)(pps * delta);
        for(int i = 0; i < spawnNumber; ++i) {
            crashParticles.add(new CrashParticle((float)((1.0 + i) / spawnNumber * delta), this));
        }
    }

    public void update(float delta) {
        if(crashParticles.size() < MAX_SIZE) {
            addParticles(delta);
        }
        if(!complete) {
            complete = true;
            for (CrashParticle p: crashParticles) {
                if(!p.isComplete()) {
                    complete = false;
                }
                p.update(delta);
            }
        }
    }

    public void render(ShapeRenderer shape, SpriteBatch batch) {
        if(!complete) {
            shape.begin(ShapeRenderer.ShapeType.Filled);
            for(CrashParticle p: crashParticles) {
                p.render(shape);
            }
            shape.end();
        }
    }

    public boolean isComplete() {
        return complete;
    }

    @Override
    public void dispose() {

    }
}
