package com.doublew.snowballfight.screens.blueprints;

import com.badlogic.gdx.InputProcessor;
import com.doublew.snowballfight.main.GameClass;

/**
 * Created by Leonemsolis on 28/09/2017.
 *
 * Input handler get objects that can be interacted,
 * and call their touchDown/touchUp methods.
 */

public abstract class InputHandler implements InputProcessor {
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public int scale(int initial) {
        return (int)(initial / GameClass.SCALE);
    }
}
