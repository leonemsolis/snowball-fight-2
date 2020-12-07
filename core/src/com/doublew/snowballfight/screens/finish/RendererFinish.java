package com.doublew.snowballfight.screens.finish;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.doublew.snowballfight.main.GameClass;
import com.doublew.snowballfight.main.GrayscaleShader;
import com.doublew.snowballfight.screens.battlescreen.objects.particles.CrashParticleSystem;
import com.doublew.snowballfight.support.AssetHandler;
import com.doublew.snowballfight.support.DataHandler;

/**
 * Created by Leonemsolis on 09/01/2018.
 */

public class RendererFinish {
    private ObjectHandlerFinish handler;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font, fontShadow;
    private ShapeRenderer shape;

    private Color batchColor;

    public String []messages;
    public Vector2 []messagesSize;

    private float r, g, b;

    public RendererFinish(ObjectHandlerFinish handler, int score) {
        if(DataHandler.colorOn) {
            r = .45f;
            g = .18f;
            b = .58f;
        } else {
            r = .4f;
            g = .4f;
            b = .4f;
        }

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
        font.getData().setScale(.9f, .9f);

        fontShadow = new BitmapFont(AssetHandler.grayFont.getData(), AssetHandler.grayFont.getRegion(), AssetHandler.grayFont.usesIntegerPositions());
        fontShadow.getData().setScale(.9f, 1f);

        batchColor = batch.getColor();

        if(score != -1) {
            messages = new String[]{"Hi there!", "You are the best!", "Thanks for playing :)"};
        } else {
            messages = new String[]{"Hi there! You lose :(", " But next time You", "will win for sure"};
        }

        messagesSize = new Vector2[]{new Vector2(), new Vector2(), new Vector2()};
        GlyphLayout layout = new GlyphLayout();
        for(int i = 0; i < 3; ++i) {
            layout.setText(font, messages[i]);
            messagesSize[i].x = layout.width;
            messagesSize[i].y = layout.height;
        }
    }

    public void render() {
        Gdx.gl.glClearColor(r, g, b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
            drawOutline(batch);

            batch.draw(AssetHandler.finishBackground, 0, GameClass.MID_POINT - AssetHandler.finishBackground.getRegionHeight() / 2);

            handler.backButton.render(batch);

        batch.end();

        if(handler.isWon) {
            for (CrashParticleSystem f:handler.fireworks) {
                f.render(shape, batch);
            }
        }

        batch.begin();
        batch.draw(AssetHandler.finishFront, 0, GameClass.MID_POINT - AssetHandler.finishBackground.getRegionHeight() / 2);
            drawText(batch);

            if(handler.buyBack != null) {
                handler.buyBack.render(batch);
            }
        batch.end();
    }

    private void drawText(SpriteBatch batch) {
        for(int i = 0; i < 3; ++i) {
            fontShadow.draw(batch, messages[i], GameClass.GAME_WIDTH / 2 - messagesSize[i].x / 2 + 1, GameClass.MID_POINT + 10 + (i * 1.5f * messagesSize[i].y) + 1);
            font.draw(batch, messages[i], GameClass.GAME_WIDTH / 2 - messagesSize[i].x / 2, GameClass.MID_POINT + 10 + (i * 1.5f * messagesSize[i].y));
        }
    }


    private void drawOutline(SpriteBatch batch) {
        batch.setColor(0, 0, 0, .4f);
        batch.draw(AssetHandler.blackSquare, 0, 0, GameClass.DEVICE_WIDTH, GameClass.MID_POINT - 134 - 40);
        batch.draw(AssetHandler.blackSquare, 0, GameClass.MID_POINT + 134 + 40, GameClass.DEVICE_WIDTH, 2000);
        batch.setColor(batchColor);
    }

    public void dispose() {
        batch.dispose();
        shape.dispose();
        font.dispose();
        fontShadow.dispose();
    }
}
