package com.doublew.snowballfight.screens.finish;

import com.doublew.snowballfight.screens.battlescreen.objects.particles.CrashParticleSystem;
import com.doublew.snowballfight.screens.blueprints.InputHandler;

/**
 * Created by Leonemsolis on 09/01/2018.
 */

public class InputHandlerFinish extends InputHandler {
    private ObjectHandlerFinish objectHandler;
    private final boolean isWon;
    public InputHandlerFinish(ObjectHandlerFinish objectHandler) {
        this.objectHandler = objectHandler;
        isWon = (objectHandler.buyBack == null);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenX = scale(screenX);
        screenY = scale(screenY);

        if(!isWon) {
            if(objectHandler.buyBack == null || objectHandler.buyBack.isHidden) {
                objectHandler.backButton.touchDown(screenX, screenY);
            } else {
                objectHandler.buyBack.touchDown(screenX, screenY);
            }
        } else {
            if(objectHandler.backButton.touchDown(screenX, screenY)) {
                return false;
            }
            objectHandler.fireworks.add(new CrashParticleSystem(screenX, screenY, objectHandler.generateColor(), objectHandler.generateColor(), .4f));
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenX = scale(screenX);
        screenY = scale(screenY);
        objectHandler.backButton.touchUp(screenX, screenY);
        if(objectHandler.buyBack != null) {
            objectHandler.buyBack.touchUp(screenX, screenY);
        }
        return false;
    }
}
