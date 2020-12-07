package com.doublew.snowballfight.screens.titlescreen;

import com.badlogic.gdx.graphics.Color;
import com.doublew.snowballfight.screens.battlescreen.objects.particles.CrashParticleSystem;
import com.doublew.snowballfight.screens.blueprints.InputHandler;
import com.doublew.snowballfight.support.DataHandler;
import com.doublew.snowballfight.support.Sound;

/**
 * Created by Leonemsolis on 14/09/2017.
 *
 * InputHandler for Title screen
 */

public class InputHandlerTitle extends InputHandler {

    private ObjectHandlerTitle handler;

    public InputHandlerTitle(ObjectHandlerTitle handler) {
        this.handler = handler;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenX = scale(screenX);
        screenY = scale(screenY);

        if(!handler.continuePrompt.isHidden) {
            return handler.continuePrompt.touchDown(screenX, screenY);
        }

        if(!handler.isAnimated) {
            if(     handler.playButton.touchDown(screenX, screenY) ||
                    handler.likeButton.touchDown(screenX, screenY) ||
                    handler.helpButton.touchDown(screenX, screenY) ||
                    handler.settingsButton.touchDown(screenX, screenY)) {
                return false;
            }
            Sound.playClickEmpty();
            if(DataHandler.colorOn) {
                handler.clickParticles.add(new CrashParticleSystem(screenX, screenY, Color.BLUE, Color.CYAN, .1f));
            } else {
                handler.clickParticles.add(new CrashParticleSystem(screenX, screenY, Color.DARK_GRAY, Color.WHITE, .1f));
            }

        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenX = scale(screenX);
        screenY = scale(screenY);

        handler.playButton.touchUp(screenX, screenY);
        handler.likeButton.touchUp(screenX, screenY);
        handler.helpButton.touchUp(screenX, screenY);
        handler.settingsButton.touchUp(screenX, screenY);
        handler.continuePrompt.touchUp(screenX, screenY);

        return false;
    }
}
