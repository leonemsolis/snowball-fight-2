package com.doublew.snowballfight.screens.store;

import com.badlogic.gdx.graphics.Color;
import com.doublew.snowballfight.support.DataHandler;
import com.doublew.snowballfight.support.Sound;

/**
 * Created by Leonemsolis on 09/01/2018.
 */

public class InputHandlerStore extends com.doublew.snowballfight.screens.blueprints.InputHandler {
    private ObjectHandlerStore objectHandler;
    public InputHandlerStore(ObjectHandlerStore objectHandler) {
        this.objectHandler = objectHandler;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenX = scale(screenX);
        screenY = scale(screenY);

        if(objectHandler.promptExit.isHidden && objectHandler.promptBuy.isHidden && objectHandler.buyNotification.isHidden) {
            if(objectHandler.checkInput(screenX, screenY)) {
                return false;
            }
            if(objectHandler.info.touchDown(screenX, screenY)) {
                return false;
            }
        } else {
            if(!objectHandler.promptBuy.isHidden) {
                if(objectHandler.promptBuy.touchDown(screenX, screenY)) {
                    return false;
                }
            } else  if(!objectHandler.promptExit.isHidden){
                if(objectHandler.promptExit.touchDown(screenX, screenY)) {
                    return false;
                }

            } else {
                if(objectHandler.buyNotification.touchDown(screenX, screenY)) {
                    return false;
                }
            }
        }

        Sound.playClickEmpty();
        if(DataHandler.colorOn) {
            objectHandler.clickParticles.add(new com.doublew.snowballfight.screens.battlescreen.objects.particles.CrashParticleSystem(screenX, screenY, Color.BLUE, Color.CYAN, .1f));
        } else {
            objectHandler.clickParticles.add(new com.doublew.snowballfight.screens.battlescreen.objects.particles.CrashParticleSystem(screenX, screenY, Color.DARK_GRAY, Color.WHITE, .1f));
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenX = scale(screenX);
        screenY = scale(screenY);

        if(!objectHandler.promptExit.isHidden) {
            objectHandler.promptExit.touchUp(screenX, screenY);
        }

        if(!objectHandler.promptBuy.isHidden) {
            objectHandler.promptBuy.touchUp(screenX, screenY);
        }

        if(!objectHandler.buyNotification.isHidden) {
            objectHandler.buyNotification.touchUp(screenX, screenY);
        }

        objectHandler.info.touchUp(screenX, screenY);

        return false;
    }
}
