package com.doublew.snowballfight.screens.titlescreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.doublew.snowballfight.main.GameClass;

/**
 * Created by Leonemsolis on 14/09/2017.
 *
 * TitleScreen in which user can start playing or go settings, etc.
 */

public class ScreenTitle implements Screen {

    public GameClass gameClass;
    public ObjectHandlerTitle handler;
    public RendererTitle renderer;
    private InputHandlerTitle input;

    public ScreenTitle(final GameClass gameClass) {
        this.gameClass = gameClass;
        handler = new ObjectHandlerTitle(this);
        renderer = new RendererTitle(handler);
        input = new InputHandlerTitle(handler);
        Gdx.input.setInputProcessor(input);
    }

    public void gotoScreenMap(String notification) {
        gameClass.gotoScreenMap(notification, "");
    }

    public void gotoScreenSetting() {
        gameClass.gotoScreenSetting();
    }

    @Override
    public void render(float delta) {
        handler.update(delta);
        renderer.render();
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
