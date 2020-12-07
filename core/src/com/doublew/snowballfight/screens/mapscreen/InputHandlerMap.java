package com.doublew.snowballfight.screens.mapscreen;

import com.doublew.snowballfight.screens.blueprints.InputHandler;
import com.doublew.snowballfight.screens.mapscreen.objects.MAP_MODE;

/**
 * Created by Leonemsolis on 30/09/2017.
 */

public class InputHandlerMap extends InputHandler {
    private ObjectHandlerMap handler;
    public ScreenMap screen;

    public InputHandlerMap(ObjectHandlerMap handler, ScreenMap screen) {
        this.screen = screen;
        this.handler = handler;
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenX = scale(screenX);
        screenY = scale(screenY);

        if(handler.prompt.touchDown(screenX, screenY)) {
            return false;
        }
        if(handler.notification.touchDown(screenX, screenY)) {
            return false;
        }


        switch (handler.currentMode) {
            case FREE:
                if(handler.backButton.touchDown(screenX, screenY)) {
                    return false;
                }
                handler.mapCharacter.touchDown(screenX, screenY);
                break;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenX = scale(screenX);
        screenY = scale(screenY);

        if(handler.prompt.touchUp(screenX, screenY)) {
            return false;
        }
        if(handler.notification.touchUp(screenX, screenY)) {
            return false;
        }

        if(handler.currentMode == MAP_MODE.FREE) {
            if(handler.backButton.touchUp(screenX, screenY)) {
                return false;
            }
            if(!handler.mapCharacter.isWalking()) {
                handler.mapCharacter.touchUp(screenX, screenY);
            }
        }
        return false;
    }
}
