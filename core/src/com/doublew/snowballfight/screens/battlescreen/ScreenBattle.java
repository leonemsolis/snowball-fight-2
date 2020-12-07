package com.doublew.snowballfight.screens.battlescreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.doublew.snowballfight.main.GameClass;
import com.doublew.snowballfight.screens.battlescreen.objects.BATTLE_MODE;

/**
 * Created by Leonemsolis on 12/10/2017.
 * ScreenBattle - screen where main action is happening
 */

public class ScreenBattle implements Screen {
    public GameClass gameClass;
    public ObjectHandlerBattle handler;
    private com.doublew.snowballfight.screens.battlescreen.RendererBattle renderer;
    private com.doublew.snowballfight.screens.battlescreen.InputHandlerBattle input;

    private float SPEED = 1f;

    private float UPDATE_RATE = 1/(100f * SPEED);
    private float updateTimer = 0;

    public int stageLevel, stageID;
    public ScreenBattle(GameClass gameClass, int stageID, int stageLevel, boolean saved) {
        this.gameClass = gameClass;
        this.stageID = stageID;
        this.stageLevel = stageLevel;

        handler = new ObjectHandlerBattle(this, saved);
        renderer = new com.doublew.snowballfight.screens.battlescreen.RendererBattle(handler);
        input = new com.doublew.snowballfight.screens.battlescreen.InputHandlerBattle(handler);

        Gdx.input.setInputProcessor(input);
    }

    @Override
    public void render(float delta) {
        updateTimer+=delta;
        if(updateTimer > UPDATE_RATE) {
            handler.update(delta);
            updateTimer = 0;
        }
        renderer.render(delta);
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        if(handler != null) {
            if(handler.currentMode != BATTLE_MODE.PAUSE) {
                handler.savedBeforePauseMode = handler.currentMode;
                handler.currentMode = BATTLE_MODE.PAUSE;
            }
        }
    }

    @Override
    public void resume() {
//        if(handler != null) {
//            handler.currentMode = handler.savedBeforePauseMode;
//        }
    }

    @Override
    public void hide() {

    }
}
