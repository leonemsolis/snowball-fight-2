package com.doublew.snowballfight.screens.titlescreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.doublew.snowballfight.main.GameClass;
import com.doublew.snowballfight.main.GrayscaleShader;
import com.doublew.snowballfight.support.AssetHandler;
import com.doublew.snowballfight.support.DataHandler;

/**
 * Created by Leonemsolis on 17/09/2017.
 *
 * Renders objects, taken from Title Screen ObjectHandler
 */

public class RendererTitle {

    private ObjectHandlerTitle handler;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private float titleWidth, titleHeight;
    private BitmapFont font;
    private ShapeRenderer shapeRenderer;

    private String label = "";

    private TextureRegion randomBackground;


    public RendererTitle(ObjectHandlerTitle handler) {
        font = new BitmapFont(AssetHandler.grayFont.getData(), AssetHandler.grayFont.getRegion(), AssetHandler.grayFont.usesIntegerPositions());
        font.getData().setScale(0.85f, 1f);
        this.handler = handler;

        camera = new OrthographicCamera();
        camera.setToOrtho(true, GameClass.GAME_WIDTH, GameClass.GAME_HEIGHT);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.setColor(Color.WHITE);

        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        if(!DataHandler.colorOn) {
            batch.setShader(GrayscaleShader.grayscaleShader);
        }

        changeLabel("Snowball Fight: Remastered");
        randomBackground = AssetHandler.battleBackgrounds[DataHandler.maxStage - 1];
    }

    public void render() {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
            batch.draw(randomBackground, 0, 0);

            font.draw(batch, label, GameClass.GAME_WIDTH / 2 - titleWidth / 2, GameClass.MID_POINT - 90 - titleHeight);
            if(handler.isAnimated) {
                batch.draw(AssetHandler.special[1], handler.animationX, 0);
            }

            handler.playButton.renderAlternative(batch);
            handler.likeButton.render(batch);
            handler.helpButton.render(batch);
            handler.settingsButton.render(batch);

            handler.continuePrompt.render(batch);

        batch.end();

        for (com.doublew.snowballfight.screens.battlescreen.objects.particles.CrashParticleSystem s:handler.clickParticles) {
            s.render(shapeRenderer, batch);
        }
    }

    public void changeLabel(String l) {
        label = l;
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, l);
        titleWidth = layout.width;
        titleHeight = layout.height;
    }

    public void dispose() {
        font.dispose();
        batch.dispose();
        shapeRenderer.dispose();
    }
}
