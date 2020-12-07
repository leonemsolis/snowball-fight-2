package com.doublew.snowballfight.screens.mapscreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.doublew.snowballfight.main.GameClass;

/**
 * Created by Leonemsolis on 30/09/2017.
 *
 * ScreenMap - map and it's stuff
 */

public class ScreenMap implements Screen {
    private GameClass gameClass;

    public ObjectHandlerMap handler;

    public RendererMap renderer;
    private InputHandlerMap input;

    public ScreenMap(final GameClass game, String message1, String message2) {
        this.gameClass = game;

        if (message1.isEmpty()) {
            handler = new ObjectHandlerMap(this, com.doublew.snowballfight.screens.mapscreen.objects.MAP_MODE.FREE, message1, message2);
        } else {
            handler = new ObjectHandlerMap(this, com.doublew.snowballfight.screens.mapscreen.objects.MAP_MODE.NOTIFICATION, message1, message2);
        }
        renderer = new RendererMap(handler);
        input = new InputHandlerMap(handler, this);
        Gdx.input.setInputProcessor(input);
    }

    @Override
    public void render(float delta) {
        handler.update(delta);
        renderer.render(delta);
    }

    public void gotoBattle(int stageID, int stageLevel) {
        gameClass.gotoScreenBattle(stageID, stageLevel, false);
    }

    public void gotoShop(boolean isDrugStore) {
        gameClass.gotoScreenStore(isDrugStore);
    }

    public void goBack() {
        gameClass.gotoScreenTitle();
    }

    @Override
    public void dispose() {
        handler.dispose();
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

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }
}
