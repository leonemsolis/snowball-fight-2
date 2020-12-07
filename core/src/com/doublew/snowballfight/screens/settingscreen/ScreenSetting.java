package com.doublew.snowballfight.screens.settingscreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.doublew.snowballfight.main.GameClass;
import com.doublew.snowballfight.support.DataHandler;

/**
 * Created by Leonemsolis on 21/02/2018.
 */

public class ScreenSetting implements Screen {
    public GameClass gameClass;
    public ObjectHandlerSetting handler;
    public com.doublew.snowballfight.screens.settingscreen.RendererSetting renderer;
    public com.doublew.snowballfight.screens.settingscreen.InputHandlerSetting input;

    public ScreenSetting(final GameClass gameClass) {
        this.gameClass = gameClass;

        handler = new ObjectHandlerSetting(this);
        renderer = new com.doublew.snowballfight.screens.settingscreen.RendererSetting(handler);
        input = new com.doublew.snowballfight.screens.settingscreen.InputHandlerSetting(handler);

        Gdx.input.setInputProcessor(input);
    }

    public void goBack() {
        DataHandler.soundOn = handler.sound.isActivated();
        DataHandler.colorOn = handler.color.isActivated();

        DataHandler.saveSettings();
        gameClass.gotoScreenTitle();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        handler.update(delta);
        renderer.render(delta);
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
        handler.dispose();
        renderer.dispose();
    }
}
