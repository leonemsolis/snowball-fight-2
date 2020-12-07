package com.doublew.snowballfight.screens.settingscreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.doublew.snowballfight.main.GameClass;
import com.doublew.snowballfight.main.GrayscaleShader;
import com.doublew.snowballfight.support.AssetHandler;
import com.doublew.snowballfight.support.DataHandler;

/**
 * Created by Leonemsolis on 21/02/2018.
 */

public class RendererSetting {
    private ObjectHandlerSetting handler;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private float r, g, b;

    public RendererSetting(ObjectHandlerSetting handler) {
        if(DataHandler.colorOn) {
            r = .02f;
            g = .18f;
            b = .45f;
        } else {
            r = .21f;
            g = .21f;
            b = .22f;
        }

        this.handler = handler;

        camera = new OrthographicCamera();
        camera.setToOrtho(true, GameClass.GAME_WIDTH, GameClass.GAME_HEIGHT);

        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        if(!DataHandler.colorOn) {
            batch.setShader(GrayscaleShader.grayscaleShader);
        }

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.setColor(Color.WHITE);
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(r, g, b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
            batch.draw(AssetHandler.settingsBackground, 0, GameClass.MID_POINT - AssetHandler.settingsBackground.getRegionHeight() / 2);
            handler.sound.render(batch);
            handler.color.render(batch);
            handler.backButton.render(batch);
        batch.end();

        for (com.doublew.snowballfight.screens.battlescreen.objects.particles.CrashParticleSystem s:handler.clickParticles) {
            s.render(shapeRenderer, batch);
        }

    }

    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
    }
}
