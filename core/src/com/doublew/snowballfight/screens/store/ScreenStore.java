package com.doublew.snowballfight.screens.store;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.doublew.snowballfight.main.GameClass;

/**
 * Created by Leonemsolis on 09/01/2018.
 */

public class ScreenStore implements Screen {

    public ObjectHandlerStore objectHandler;
    public RendererStore renderer;
    public InputHandlerStore inputHandler;

    public ScreenStore(boolean isDrugStore, GameClass gameClass) {
        objectHandler = new ObjectHandlerStore(isDrugStore, gameClass);
        renderer = new RendererStore(objectHandler);
        inputHandler = new InputHandlerStore(objectHandler);
        Gdx.input.setInputProcessor(inputHandler);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        objectHandler.update(delta);
        renderer.render();
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

    @Override
    public void dispose() {
        objectHandler.dispose();
        renderer.dispose();
    }
}
