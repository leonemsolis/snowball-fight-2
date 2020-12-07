package com.doublew.snowballfight.screens.battlescreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.doublew.snowballfight.main.GameClass;
import com.doublew.snowballfight.main.GrayscaleShader;
import com.doublew.snowballfight.screens.battlescreen.objects.BATTLE_MODE;
import com.doublew.snowballfight.support.AssetHandler;
import com.doublew.snowballfight.support.DataHandler;

import java.util.Random;

/**
 * Created by Leonemsolis on 12/10/2017.
 */

public class RendererBattle {
    private Animation specialCasting1;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private ObjectHandlerBattle handler;

    private final float STEP = .9f;
    private float cameraOffsetX = 0;
    private float cameraOffsetY = 0;
    private Random random;

    private Color start, end;
    private float current;
    private float runTime = 0;

    public RendererBattle(ObjectHandlerBattle handler) {
        specialCasting1 = new Animation(.42f, AssetHandler.gloves);
        specialCasting1.setPlayMode(Animation.PlayMode.LOOP);

        this.handler = handler;
        random = new Random();
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

        start = new Color(Color.GRAY);
        end = new Color(Color.WHITE);
        current = 0;

    }

    public void render(float delta) {
        Gdx.gl.glClearColor(.27f, .31f, .49f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.rect(0, GameClass.MID_POINT - 151, 320, 300);

//            shapeRenderer.setColor(Color.BLACK);
//            shapeRenderer.rect(0, GameClass.MID_POINT , 20, 20);
//            shapeRenderer.rect(0, GameClass.MID_POINT - 40, 20, 20);
//            shapeRenderer.rect(0, GameClass.MID_POINT - 80, 20, 20);
//            shapeRenderer.rect(0, GameClass.MID_POINT - 120, 20, 20);
//
//            shapeRenderer.setColor(Color.RED);
//            shapeRenderer.rect(0, GameClass.MID_POINT - 20, 20, 20);
//            shapeRenderer.rect(0, GameClass.MID_POINT - 60, 20, 20);
//            shapeRenderer.rect(0, GameClass.MID_POINT - 100, 20, 20);

//            shapeRenderer.setColor(Color.RED);
//            for (Object o: renderingObjects) {
//                if(o.getClass() == Enemy.class) {
//                    ((Enemy)o).renderLine(shapeRenderer);
//                }
//            }
        shapeRenderer.end();
        batch.begin();
            batch.draw(AssetHandler.battleBackgrounds[handler.screen.stageID - 1], 0, GameClass.MID_POINT - 101 - 63, 320, 63);
//            batch.draw(AssetHandler.battleUI, 0, GameClass.MID_POINT - 101 - 63 - 51, 320, 51);
            batch.draw(AssetHandler.battleUI, 0, 0, 320, 51);
            batch.draw(AssetHandler.downTransition, 0, GameClass.MID_POINT + 135, 320, 35);

            handler.hero.render(delta, runTime, batch);
            drawControlButtons(batch, delta);
            drawSmallItems(batch);

        batch.end();

        drawHpBar(shapeRenderer);
        drawMpBar(shapeRenderer);

        switch (handler.currentMode) {
            case BATTLE:
                runTime += delta;
                for (com.doublew.snowballfight.screens.battlescreen.objects.particles.ParticleSystem s: handler.particles) {
                    s.render(shapeRenderer, batch);
                }
                handler.pauseButton.render(batch);
                if(handler.hero.getCurrentMode() == com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.AIMING) {
                    switch (random.nextInt(4)) {
                        case 0:
                            if(cameraOffsetX > -2) {
                                translateLeft();
                            } else {
                                translateRight();
                            }
                            break;
                        case 1:
                            if(cameraOffsetY > -2) {
                                translateUp();
                            } else {
                                translateDown();
                            }
                            break;
                        case 2:
                            if(cameraOffsetX < 2) {
                                translateRight();
                            } else {
                                translateLeft();
                            }
                            break;
                        case 3:
                            if(cameraOffsetY < 2) {
                                translateDown();
                            } else {
                                translateUp();
                            }
                            break;
                    }
                    postUpdateCamera();
                }
                if(handler.hero.getCurrentMode() == com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.THROWING) {
                    camera.translate(-cameraOffsetX, -cameraOffsetY);
                    cameraOffsetX = 0;
                    cameraOffsetY = 0;
                    postUpdateCamera();
                }
                break;
            case SPECIAL_CAST_1:
                runTime+=delta;
                batch.begin();
                    batch.draw(AssetHandler.blackSquare, 0,0, GameClass.DEVICE_WIDTH, GameClass.DEVICE_HEIGHT);
                    batch.draw(AssetHandler.specialCastBackground, 0, GameClass.MID_POINT - AssetHandler.specialCastBackground.getRegionHeight() / 2);
                    batch.draw((TextureRegion) specialCasting1.getKeyFrame(runTime), 147, GameClass.MID_POINT + 98);
                batch.end();
                break;
            case SPECIAL_CAST_2:
                runTime+=delta;
                batch.begin();
                    batch.draw(AssetHandler.special[handler.lastSelectedSpecial], handler.specialSliderX, GameClass.MID_POINT - 101 - 63);
                batch.end();
                break;

            case PAUSE:
                Color saved = batch.getColor();
                batch.setColor(0, 0, 0, .7f);
                batch.begin();
                    batch.draw(AssetHandler.blackSquare, 0, 0, GameClass.DEVICE_WIDTH, GameClass.DEVICE_HEIGHT);
                batch.end();
                batch.setColor(saved);

                handler.pauseButton.render(batch);
                batch.begin();
                    batch.draw(AssetHandler.pauseInventory, 0, GameClass.MID_POINT - 71, GameClass.GAME_WIDTH, 142);
                    handler.specialButton.render(batch);
                    drawBigItems(batch);
                batch.end();

                break;
            case FINAL:
                runTime += delta;
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                    shapeRenderer.setColor(Color.BLACK);
                    shapeRenderer.rect(handler.finalTop.x, handler.finalTop.y, handler.finalTop.width, handler.finalTop.height);
                    shapeRenderer.rect(handler.finalBottom.x, handler.finalBottom.y, handler.finalBottom.width, handler.finalBottom.height);
                shapeRenderer.end();
                break;
        }
    }

    public void drawBigItems(SpriteBatch batch) {
        for(int i = 0; i < DataHandler.items.size(); ++i) {
            batch.draw(AssetHandler.itemsBig[DataHandler.items.get(i).id], 6 + 63 * i, GameClass.MID_POINT - 71 + 5, 56, 56);
        }
    }

    public void drawSmallItems(SpriteBatch batch) {
        for(int i = 0; i < DataHandler.items.size(); ++i) {
            batch.draw(AssetHandler.itemsSmall[DataHandler.items.get(i).id], 92 + 30 * i,  5, 20, 20);
        }
    }

    public void drawHpBar(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(.56f, .56f, .56f, 1f);
        shapeRenderer.rect(12, 9, 23, 30 * (handler.hero.getMAX_HP() - handler.hero.getHP()) / handler.hero.getMAX_HP());
        shapeRenderer.end();
    }

    public void drawMpBar(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(.04f, .29f, .62f, 1f);
        switch (DataHandler.heroMana) {
            case 3:
                shapeRenderer.rect(135,  38, 22, 2);
                shapeRenderer.rect(157, 35, 8, 8);
            case 2:
                shapeRenderer.rect(105, 38, 22, 2);
                shapeRenderer.rect(127, 35, 8, 8);
            case 1:
                shapeRenderer.rect(75, 38, 22, 2);
                shapeRenderer.rect(97, 35, 8, 8);
        }
        shapeRenderer.end();
    }

    public void drawControlButtons(SpriteBatch batch, float delta) {
        if(handler.currentMode != BATTLE_MODE.FINAL) {
            if(!handler.hero.isFrozen()) {
                handler.leftButton.render(batch);
                handler.rightButton.render(batch);
                if(!handler.hero.isStunned()) {
                    handler.centerButton.render(batch);
                }
            }
            for(com.doublew.snowballfight.screens.battlescreen.objects.chars.Enemy e: handler.enemies) {
                e.render(delta, runTime, batch);
            }
            for(com.doublew.snowballfight.screens.battlescreen.objects.Snowball s: handler.snowballs) {
                s.render(delta, runTime, batch);
            }
        }
    }

    private void translateLeft() {
        cameraOffsetX -= STEP;
        camera.translate(-STEP, 0);
    }

    private void translateRight() {
        cameraOffsetX += STEP;
        camera.translate(STEP, 0);
    }

    private void translateUp() {
        cameraOffsetY -= STEP;
        camera.translate(0, -STEP);
    }

    private void translateDown() {
        cameraOffsetY += STEP;
        camera.translate(0, STEP);
    }

    private void postUpdateCamera() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
    }

    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
    }
}
