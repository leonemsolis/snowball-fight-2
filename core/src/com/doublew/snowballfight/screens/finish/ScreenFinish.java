package com.doublew.snowballfight.screens.finish;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.doublew.snowballfight.main.GameClass;

/**
 * Created by Leonemsolis on 09/01/2018.
 */

public class ScreenFinish implements Screen {

    public ObjectHandlerFinish objectHandler;
    public RendererFinish renderer;
    public InputHandlerFinish inputHandler;
    private GameClass gameClass;

    public ScreenFinish(GameClass gameClass, int score) {
        this.gameClass = gameClass;
        objectHandler = new ObjectHandlerFinish(this, score != -1);
        renderer = new RendererFinish(objectHandler, score);
        inputHandler = new InputHandlerFinish(objectHandler);

        Gdx.input.setInputProcessor(inputHandler);
    }

    public void goBack() {
        if(objectHandler.gameOver) {
            gameClass.gotoScreenTitle();
        } else {
            gameClass.gotoScreenMap("","");
        }
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
