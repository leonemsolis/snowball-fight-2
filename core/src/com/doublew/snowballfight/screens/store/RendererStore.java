package com.doublew.snowballfight.screens.store;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.doublew.snowballfight.main.GameClass;
import com.doublew.snowballfight.main.GrayscaleShader;
import com.doublew.snowballfight.screens.battlescreen.objects.particles.CrashParticleSystem;
import com.doublew.snowballfight.support.AssetHandler;
import com.doublew.snowballfight.support.DataHandler;

/**
 * Created by Leonemsolis on 09/01/2018.
 */

public class RendererStore {
    private ObjectHandlerStore handler;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;
    private ShapeRenderer shape;

    public RendererStore(ObjectHandlerStore handler) {
        this.handler = handler;

        camera = new OrthographicCamera();
        camera.setToOrtho(true, GameClass.GAME_WIDTH, GameClass.GAME_HEIGHT);

        shape = new ShapeRenderer();
        shape.setProjectionMatrix(camera.combined);
        shape.setColor(Color.WHITE);

        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        if(!DataHandler.colorOn) {
            batch.setShader(GrayscaleShader.grayscaleShader);
        }

        font = new BitmapFont(AssetHandler.yellowFont.getData(), AssetHandler.yellowFont.getRegion(), AssetHandler.yellowFont.usesIntegerPositions());
    }

    public void render() {
        Gdx.gl.glClearColor(0.21f, 0.17f, 0.16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
            drawBackground(batch);
            font.draw(batch, DataHandler.gold+"", 210, GameClass.MID_POINT + 140);
            handler.info.render(batch);

            if(handler.selectedItem != -1) {
                if(handler.isDrugStore) {
                    font.draw(batch, handler.drugPrices[handler.selectedItem]+"", 60, GameClass.MID_POINT + 143);
                } else {
                    font.draw(batch, handler.itemPrices[handler.selectedItem]+"", 60, GameClass.MID_POINT + 143);
                }
            } else {
                font.draw(batch, "-", 60, GameClass.MID_POINT + 140);
            }

            drawSelectionFrame(batch);

            handler.promptExit.render(batch);
            handler.promptBuy.render(batch);
            handler.buyNotification.render(batch);

        batch.end();

        for (CrashParticleSystem c:handler.clickParticles) {
            c.render(shape, batch);
        }
    }

    public void drawSelectionFrame(SpriteBatch batch) {
        if(handler.selectedItem != -1) {
            batch.draw(AssetHandler.itemSelectFrame, handler.items[handler.selectedItem].x, handler.items[handler.selectedItem].y);
        }
    }

    public void drawBackground(SpriteBatch batch) {
        if(handler.isDrugStore) {
            batch.draw(AssetHandler.drugStoreBackground, 0, GameClass.MID_POINT - AssetHandler.drugStoreBackground.getRegionHeight() / 2);
        } else {
            batch.draw(AssetHandler.itemStoreBackground, 0, GameClass.MID_POINT - AssetHandler.itemStoreBackground.getRegionHeight() / 2);
        }
    }

    public void dispose() {
        font.dispose();
        batch.dispose();
        shape.dispose();
    }
}
