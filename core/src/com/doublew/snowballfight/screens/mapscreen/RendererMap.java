package com.doublew.snowballfight.screens.mapscreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.doublew.snowballfight.main.GameClass;
import com.doublew.snowballfight.main.GrayscaleShader;
import com.doublew.snowballfight.support.AssetHandler;
import com.doublew.snowballfight.support.DataHandler;

import java.util.ArrayList;

/**
 * Created by Leonemsolis on 30/09/2017.
 */

public class RendererMap {
    private ObjectHandlerMap handler;
    private OrthographicCamera camera;
    private BitmapFont font;
    private SpriteBatch batch;
    private Vector2[] unavailableStages;
    private float runTime = 0;

    private ArrayList<Vector2> completedLevels;
    private Vector2 maxLevel;
    private ArrayList<Vector2> indicatorCoordinates;

    private float r, g, b;

    public RendererMap(ObjectHandlerMap handler) {
        if(DataHandler.colorOn) {
            r = .02f;
            g = .18f;
            b = .45f;
        } else {
            r = .21f;
            g = .21f;
            b = .22f;
        }

        font = new BitmapFont(AssetHandler.blackFont.getData(), AssetHandler.blackFont.getRegion(), AssetHandler.blackFont.usesIntegerPositions());
        this.handler = handler;
        camera = new OrthographicCamera();
        camera.setToOrtho(true, GameClass.GAME_WIDTH, GameClass.GAME_HEIGHT);

        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        if(!DataHandler.colorOn) {
            batch.setShader(GrayscaleShader.grayscaleShader);
        }

        indicatorCoordinates = new ArrayList<Vector2>();
        indicatorCoordinates.add(new Vector2(255, GameClass.MID_POINT + 15));
        indicatorCoordinates.add(new Vector2(181, GameClass.MID_POINT + 87));
        indicatorCoordinates.add(new Vector2(108, GameClass.MID_POINT + 87));
        indicatorCoordinates.add(new Vector2(33, GameClass.MID_POINT + 15));

        completedLevels = new ArrayList<Vector2>();
        initIndicators(DataHandler.maxStage, DataHandler.maxLevel);
        initUnavailableStages(DataHandler.maxStage);
        maxLevel = initMaxLevelIndicator(DataHandler.maxStage, DataHandler.maxLevel);
    }

    public void initUnavailableStages(int maxStage) {
        switch (maxStage) {
            case 4:
                unavailableStages = new Vector2[]{};
                break;
            case 3:
                unavailableStages = new Vector2[]{new Vector2(14, GameClass.MID_POINT - 25)};
                break;
            case 2:
                unavailableStages = new Vector2[]{new Vector2(14, GameClass.MID_POINT - 25), new Vector2(88, GameClass.MID_POINT + 49)};
                break;
            case 1:
                unavailableStages = new Vector2[]{new Vector2(14, GameClass.MID_POINT - 25), new Vector2(88, GameClass.MID_POINT + 49), new Vector2(161, GameClass.MID_POINT + 49)};
                break;
        }
    }

    public Vector2 initMaxLevelIndicator(int maxStage, int maxLevel) {
        switch (maxStage) {
            case 1:
                switch (maxLevel) {
                    case 1:
                        return new Vector2(indicatorCoordinates.get(0).x + 5, indicatorCoordinates.get(0).y + 5);
                    case 2:
                        return new Vector2(indicatorCoordinates.get(0).x + 13, indicatorCoordinates.get(0).y + 5);
                    case 3:
                        return new Vector2(indicatorCoordinates.get(0).x + 21, indicatorCoordinates.get(0).y + 5);
                }
            case 2:
                switch (maxLevel) {
                    case 1:
                        return new Vector2(indicatorCoordinates.get(1).x + 5, indicatorCoordinates.get(1).y + 5);
                    case 2:
                        return new Vector2(indicatorCoordinates.get(1).x + 13, indicatorCoordinates.get(1).y + 5);
                    case 3:
                        return new Vector2(indicatorCoordinates.get(1).x + 21, indicatorCoordinates.get(1).y + 5);
                }
            case 3:
                switch (maxLevel) {
                    case 1:
                        return new Vector2(indicatorCoordinates.get(2).x + 5, indicatorCoordinates.get(2).y + 5);
                    case 2:
                        return new Vector2(indicatorCoordinates.get(2).x + 13, indicatorCoordinates.get(2).y + 5);
                    case 3:
                        return new Vector2(indicatorCoordinates.get(2).x + 21, indicatorCoordinates.get(2).y + 5);
                }
            case 4:
                switch (maxLevel) {
                    case 1:
                        return new Vector2(indicatorCoordinates.get(3).x + 5, indicatorCoordinates.get(3).y + 5);
                    case 2:
                        return new Vector2(indicatorCoordinates.get(3).x + 13, indicatorCoordinates.get(3).y + 5);
                    case 3:
                        return new Vector2(indicatorCoordinates.get(3).x + 21, indicatorCoordinates.get(3).y + 5);
                }
        }
        return null;
    }

    public void initIndicators(int maxStage, int maxLevel) {
        switch (maxStage) {
            case 4:
                switch (maxLevel) {
                    case 4:
                        completedLevels.add(new Vector2(indicatorCoordinates.get(3).x + 21, indicatorCoordinates.get(3).y + 5));
                    case 3:
                        completedLevels.add(new Vector2(indicatorCoordinates.get(3).x + 13, indicatorCoordinates.get(3).y + 5));
                    case 2:
                        completedLevels.add(new Vector2(indicatorCoordinates.get(3).x + 5, indicatorCoordinates.get(3).y + 5));
                    case 1:
                        completedLevels.add(new Vector2(indicatorCoordinates.get(2).x + 21, indicatorCoordinates.get(2).y + 5));
                }
                initIndicators(maxStage - 1, 3);
                break;
            case 3:
                switch (maxLevel) {
                    case 3:
                        completedLevels.add(new Vector2(indicatorCoordinates.get(2).x + 13, indicatorCoordinates.get(2).y + 5));
                    case 2:
                        completedLevels.add(new Vector2(indicatorCoordinates.get(2).x + 5, indicatorCoordinates.get(2).y + 5));
                    case 1:
                        completedLevels.add(new Vector2(indicatorCoordinates.get(1).x + 21, indicatorCoordinates.get(1).y + 5));
                }
                initIndicators(maxStage - 1, 3);
                break;
            case 2:
                switch (maxLevel) {
                    case 3:
                        completedLevels.add(new Vector2(indicatorCoordinates.get(1).x + 13, indicatorCoordinates.get(1).y + 5));
                    case 2:
                        completedLevels.add(new Vector2(indicatorCoordinates.get(1).x + 5, indicatorCoordinates.get(1).y + 5));
                    case 1:
                        completedLevels.add(new Vector2(indicatorCoordinates.get(0).x + 21, indicatorCoordinates.get(0).y + 5));
                }
                initIndicators(maxStage - 1, 3);
                break;
            case 1:
                switch (maxLevel) {
                    case 3:
                        completedLevels.add(new Vector2(indicatorCoordinates.get(0).x + 13, indicatorCoordinates.get(0).y + 5));
                    case 2:
                        completedLevels.add(new Vector2(indicatorCoordinates.get(0).x + 5, indicatorCoordinates.get(0).y + 5));
                }
                break;
        }
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(r, g, b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        runTime += delta;
        batch.begin();
            batch.draw(AssetHandler.map, 0, GameClass.MID_POINT-175);

            handler.backButton.render(batch);

            handler.mapCharacter.render(delta, runTime, batch);

            renderStageLevelStuff();

            handler.notification.render(batch);
            handler.prompt.render(batch);
        batch.end();
    }

    // TODO: 04/01/2018 FIX! 
    public void renderStageLevelStuff() {
        if(maxLevel != null) {
            batch.draw(AssetHandler.redSquare, maxLevel.x, maxLevel.y, 5, 5);
        }
        for (Vector2 v:unavailableStages) {
            batch.draw(AssetHandler.unavailableLevel, v.x, v.y, 71, 48);
        }

        for (Vector2 v:completedLevels) {
            batch.draw(AssetHandler.greenSquare, v.x, v.y, 5, 5);
        }

        batch.draw(AssetHandler.levelIndicator, indicatorCoordinates.get(0).x, indicatorCoordinates.get(0).y);
        batch.draw(AssetHandler.levelIndicator, indicatorCoordinates.get(1).x, indicatorCoordinates.get(1).y);
        batch.draw(AssetHandler.levelIndicator, indicatorCoordinates.get(2).x, indicatorCoordinates.get(2).y);
        batch.draw(AssetHandler.levelIndicator, indicatorCoordinates.get(3).x, indicatorCoordinates.get(3).y);
    }


    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
