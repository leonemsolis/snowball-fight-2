package com.doublew.snowballfight.screens.settingscreen;

import com.badlogic.gdx.graphics.Color;
import com.doublew.snowballfight.support.DataHandler;
import com.doublew.snowballfight.support.Sound;

/**
 * Created by Leonemsolis on 21/02/2018.
 */

public class InputHandlerSetting extends com.doublew.snowballfight.screens.blueprints.InputHandler {
    private ObjectHandlerSetting handler;

    public InputHandlerSetting(ObjectHandlerSetting handler) {
        this.handler = handler;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenX = scale(screenX);
        screenY = scale(screenY);

        if(handler.backButton.touchDown(screenX, screenY) ||
                handler.sound.touchDown(screenX, screenY) ||
                handler.color.touchDown(screenX, screenY)) {
            return false;
        }

        Sound.playClickEmpty();
        if(DataHandler.colorOn) {
            handler.clickParticles.add(new com.doublew.snowballfight.screens.battlescreen.objects.particles.CrashParticleSystem(screenX, screenY, Color.BLUE, Color.CYAN, .1f));
        } else {
            handler.clickParticles.add(new com.doublew.snowballfight.screens.battlescreen.objects.particles.CrashParticleSystem(screenX, screenY, Color.DARK_GRAY, Color.WHITE, .1f));
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenX = scale(screenX);
        screenY = scale(screenY);

        handler.backButton.touchUp(screenX, screenY);
        handler.sound.touchUp(screenX, screenY);
        handler.color.touchUp(screenX, screenY);

        return false;
    }
}
